package com.my.app;

import com.my.common.CommonResult;
import com.my.po.CategoryPo;
import com.my.po.VideoPo;
import com.my.service.CategoryService;
import com.my.service.VideoService;
import com.my.util.AliyunUtils;
import com.my.util.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/25 15:00
 */
@RestController
@RequestMapping("app")
public class AppController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private CategoryService categoryService;

    /**
     *
     *@return:验证码
    */
    @RequestMapping("getPhoneCode")
    public Object getPhoneCode(String phonn){
        CreateValidateCode core = new CreateValidateCode(100, 30, 6, 10);
        System.out.println("验证码："+core);
        String message=null;
        try {
            AliyunUtils.aliyunyzm(phonn,core.getCode());
            return new CommonResult().success(message,phonn);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().failed(message);
        }
    }
    /**
     *
     *@return:按时间顺序排序查视频
    */
    @RequestMapping("queryByReleaseTime")
    public CommonResult queryByReleaseTime(){
        try {
            List<VideoPo> all = videoService.queryByReleaseTime();
            return new CommonResult().success(all);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().failed();
        }
    }/**
     *
     *@return:按时间顺序排序模糊查视频
    */
    @RequestMapping("queryByLikeVideoName")
    public CommonResult queryByLikeVideoName(String content){
        try {
            List<VideoPo> all = videoService.queryByLikeVideoName(content);
            return new CommonResult().success(all);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().failed();
        }
    }
    /**
     *
     *@return:类别查询
     */
    @RequestMapping("queryAllCategory")
    public CommonResult queryAllCategory(){
        try {
            List<CategoryPo> all1 = categoryService.queryAllCategory();
            return new CommonResult().success(all1);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().failed();
        }
    }
}
