package com.my.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.my.entity.User;
import com.my.po.CityPo;
import com.my.po.SexPo;
import com.my.service.UserService;
import com.my.util.AliyunUtils;
import com.my.util.CreateValidateCode;
import com.my.util.MonthUtils;
import io.goeasy.GoEasy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.my.util.AliyunUtils.aliyun;

/**
 * @author:ljn
 * @Description:用户控制器
 * @Date:2020/11/19 15:24
 */
@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("findAll")
    @ResponseBody//分页查所有
    public Map<String,Object> findAll(Integer page, Integer rows){
        HashMap<String, Object> map = new HashMap<>();
        List<User> users = userService.selectAll(page, rows);
        Integer count = userService.count();
        log.debug("count：{}",count);
        Integer total=count%rows==0?count/rows:count/rows+1;
        map.put("page",page);
        map.put("total",total);
        map.put("records",count);
        map.put("rows",users);
        return map;
    }

    @ResponseBody//修改状态
    @RequestMapping("status")
    public Map<String,String> status(User user){
        HashMap<String, String> map = new HashMap<>();
        try {
            userService.modfiyStatus(user);
            map.put("yes","修改成功");
            map.put("status","200");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("yes","修改失败");
            map.put("status","404");
        }
        return map;
    }

    @ResponseBody//添加
    @RequestMapping("exit")
    public Map<String,Object> exit(User user,String oper){
        HashMap<String, Object> map = new HashMap<>();
        if(StringUtils.equals("add",oper)) userService.insertUser(user);
        map.put("rows",user);
        //分布计算
        HashMap<String, Object> maps = new HashMap<>();
        List<Integer> list= MonthUtils.queryMonths();
        ArrayList<Integer> man = new ArrayList<>();//小男孩
        ArrayList<Integer> woman = new ArrayList<>();//小女孩
        ArrayList<String> months = new ArrayList<>();
        String date = new SimpleDateFormat("MM").format(new Date());
        Integer month = Integer.valueOf(date);
        for (Integer integer : list) {
            man.add(userService.findAllByMonth("男",integer));
            woman.add(userService.findAllByMonth("女",integer));
            months.add(integer+"月");
        }
        maps.put("man",man);
        maps.put("woman",woman);
        maps.put("months",months);
        String jsonString = JSON.toJSONString(maps);
        //创建GoEasy对象  参数:机房地区,appkey
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-df0a45499f274b2bae29ae50a6a12dc9");
        //发送消息   参数:通道名称,消息内容
        goEasy.publish("yingx-ljn", jsonString);


        return map;
    }

    @RequestMapping("insertAliyun")//上传图片
    public void insertAliyun(MultipartFile picImg, String id) {
            String filename = picImg.getOriginalFilename(); //获取文件名
            String newName = new Date().getTime() + "-" + filename;//获取新名字
            String bucketName = "yingx-ljn"; //aliyun存储空间名
            String objectName = "user-imgs/" + newName;//保存的文件名
            OSS oss = AliyunUtils.aliyun();//封装工具类
            byte[] bytes = null;
            try {
                bytes = picImg.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            oss.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));// 上传bytes数组
            User user = new User();
            user.setId(id);
            user.setPicImg("https://yingx-ljn.oss-cn-beijing.aliyuncs.com/" + objectName);
            String img = user.getPicImg().split("user-imgs/")[1];
            String localFile="src/main/webapp/bootstrap/cover/" + img;
            oss.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));
            oss.shutdown();//关闭OSSClient。

        userService.modfiyUser(user);
    }

    @RequestMapping("code")
    @ResponseBody//发送验证码
    public void code(String phonn) throws Exception {
        String signName = "因果App";
        String templateCode = "SMS_205620688";
        CreateValidateCode core = new CreateValidateCode(100, 30, 6, 10);
        String templateParam = core.getCode();
        SendSmsResponse aliyun = aliyun(phonn, signName, templateCode, templateParam);
        System.out.println(aliyun.getMessage());
    }

    @RequestMapping("easyPOI")
    @ResponseBody
    public Map<String, Object> easyPOI(){
        return userService.easyPOI();
    }

    @RequestMapping("findAllBySes")
    @ResponseBody
    public List<SexPo> findAllBySes(){
        List<SexPo> list = new ArrayList<>();
        List<CityPo> sex = userService.findAllBySex("男");
        list.add(new SexPo("小男孩",sex));
        List<CityPo> sex1 = userService.findAllBySex("女");
        list.add(new SexPo("小女孩",sex1));
        return list;
    }
    @RequestMapping("findAllByMonth")
    @ResponseBody
    public Map<String,Object> findAllByMonth(){
        HashMap<String, Object> map = new HashMap<>();
        List<Integer> list= MonthUtils.queryMonths();
        ArrayList<Integer> man = new ArrayList<>();//小男孩
        ArrayList<Integer> woman = new ArrayList<>();//小女孩
        ArrayList<String> months = new ArrayList<>();
        String date = new SimpleDateFormat("MM").format(new Date());
        Integer month = Integer.valueOf(date);
        for (Integer integer : list) {
            man.add(userService.findAllByMonth("男",integer));
            woman.add(userService.findAllByMonth("女",integer));
            months.add(integer+"月");
        }
        map.put("man",man);
        map.put("woman",woman);
        map.put("months",months);

        return map;
    }
}
