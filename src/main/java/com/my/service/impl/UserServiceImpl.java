package com.my.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.my.annotcation.AddLog;
import com.my.annotcation.AddRedis;
import com.my.annotcation.DelRedis;
import com.my.dao.UserDao;
import com.my.entity.User;
import com.my.example.UserExample;
import com.my.po.CityPo;
import com.my.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author:ljn
 * @Description:用户业务实现
 * @Date:2020/11/19 15:14
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    /**
     *@Description:分页查所有
     */
    @Override
    @AddRedis
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> selectAll(Integer page, Integer rowNum) {
        Integer begin=(page-1)*rowNum;
        UserExample example = new UserExample();
        RowBounds rowBounds = new RowBounds(begin,rowNum);
        List<User> list = userDao.selectByExampleAndRowBounds(example, rowBounds);
        return list;
    }

    /**
     *@Description:分页总条数
     */
    @Override
    @AddRedis
    public Integer count() {
        int i = userDao.selectCount(new User());
        return i;
    }

    /**
     *@Description:修改状态
    */
    @Override
    @AddLog("用户修改状态功能")
    @DelRedis
    public void modfiyStatus(User user) {
        if (user.getStatus().equals("正常")) user.setStatus("冻结");
        else user.setStatus("正常");
        userDao.modfiyStatus(user);
    }

    /**
     *@Description:添加
    */
    @Override
    @AddLog("用户添加功能")
    @DelRedis
    public void insertUser(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setUserDate(new Date());
        userDao.insertSelective(user);
    }
    /**
     *@Description:修改
    */
    @Override
    @AddLog("用户修改功能")
    @DelRedis
    public void modfiyUser(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    /**
     *@Description:导出
    */
    @Override
    @AddRedis
    public Map<String, Object> easyPOI() {
        HashMap<String, Object> map = new HashMap<>();
        List<User> users = userDao.selectAll();
        //导出设置的参数  参数:大标题,工作表名
        ExportParams exportParams = new ExportParams("用户数据", "用户");
        //导出工具   参数:导出的参数,对应的实体类,导出的集合
        for (User user : users) {
            String picName = user.getPicImg().split("user-imgs/")[1];
            user.setPicImg("src/main/webapp/bootstrap/cover/" + picName);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, User.class, users);
        try {
            workbook.write(new FileOutputStream(new File("C:\\Users\\25004\\Desktop\\userAll.xls")));
            map.put("message", "导出成功!请在桌面查看");
            map.put("status", "200");
        } catch (IOException e) {
            e.printStackTrace();
            map.put("message", "导出失败!");
            map.put("status", "201");
        }
        return map;
    }

    @Override
    @AddRedis
    public List<CityPo> findAllBySex(String sex) {
        return userDao.findAllBySex(sex);
    }

    @Override
    @AddRedis
    public Integer findAllByMonth(String sex,Integer month) {
        return userDao.findAllByMonth(sex, month);
    }
}
