package com.my.text;

import com.my.YixueAppcation;
import com.my.dao.UserDao;
import com.my.entity.User;
import com.my.example.UserExample;
import com.my.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/16 15:50
 */
@SpringBootTest(classes = YixueAppcation.class)
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @Test
    public void text(){
        //userDao.selectAll().forEach(a-> System.out.println(a));
        userService.selectAll(0,1).forEach(a-> System.out.println(a));
        System.out.println(userService.count());
    }
    @Test
    public void fenye(){
        UserExample example = new UserExample();
        RowBounds rowBounds = new RowBounds(0,1);
        List<User> users = userDao.selectByExampleAndRowBounds(example, rowBounds);
        users.forEach(a-> System.out.println(a));
    }
    @Test
    public void add(){
        User user = new User();
        user.setNickname("皮卡丘");
        user.setPhone("12345678101");
        user.setPicImg("/imgs/皮卡丘6.jpg");
        user.setBrief("你好,皮卡丘");
        user.setScore("10");
        user.setStatus("冻结");
        user.setSex("女");
        user.setAddress("山西");
        userService.insertUser(user);
    }
    @Test
    public void ceshi(){
        UserExample example = new UserExample();
        example.createCriteria().andPicImgLike("%皮卡丘%");
        List<User> list = userDao.selectByExample(example);
        list.forEach(a-> System.out.println(a));
    }

}
