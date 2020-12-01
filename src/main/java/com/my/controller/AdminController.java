package com.my.controller;

import com.alibaba.fastjson.parser.JSONToken;
import com.my.entity.Admin;
import com.my.service.AdminService;
import com.my.util.CreateValidateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:ljn
 * @Description:管理员控制器
 * @Date:2020/11/18 17:53
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    private static final Logger  log= LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    @ResponseBody//登录
    public Map<String,String> login(String username, String password, String core, HttpServletRequest request){
        HashMap<String, String> map = new HashMap<>();
        String code = (String) request.getSession().getAttribute("core");
        log.debug("验证码路径:{}",code);
        Admin admin = adminService.loginAdmin(username);
        log.debug("当前管理员:{}",admin);
        System.out.println(core);
        try {
            if(admin==null) throw new RuntimeException("用户名错误！");
            if(!admin.getPassword().equals(password)) throw new RuntimeException("密码错误！");
            if(!code.equals(core)) throw new RuntimeException("验证码错误！");
            request.getSession().setAttribute("admin",admin);
            map.put("status","yes");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",e.getMessage());
            return map;
        }

    }

    @RequestMapping("exit")
    @ResponseBody//退出
    public Map<String,String> exit(HttpServletRequest request){
        HashMap<String, String> map = new HashMap<>();
        HttpSession session = request.getSession();
        //清楚管理员作用域
        session.removeAttribute("admin");
        session.invalidate();
        map.put("status","yes");
        return map;
    }

    @RequestMapping("core")
    @ResponseBody
    public String getCode(HttpServletResponse response, HttpServletRequest request){
        //创建验证码工具
        CreateValidateCode core = new CreateValidateCode(100, 30, 4, 10);
        try {
            request.getSession().setAttribute("core", core.getCode());
            core.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
