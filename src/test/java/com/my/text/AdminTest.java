package com.my.text;

import com.my.YixueAppcation;
import com.my.entity.Admin;
import com.my.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/16 15:50
 */
@SpringBootTest(classes = YixueAppcation.class)
public class AdminTest {

    @Autowired
    private AdminService adminService;

    @Test
    public void login(){
        Admin ljn = adminService.loginAdmin("ljn");
        System.out.println(ljn);
    }
    @Test
    public void add(){
        Admin admin = new Admin();
        admin.setUsername("cmy");
        admin.setPassword("123");
        adminService.insertAdmin(admin);
        System.out.println(admin);
    }
}
