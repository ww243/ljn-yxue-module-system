package com.my.service.impl;

import com.my.annotcation.AddRedis;
import com.my.dao.AdminDao;
import com.my.entity.Admin;
import com.my.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author:ljn
 * @Description:管理员业务实现
 * @Date:2020/11/16 15:35
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    /**
     *@Description:登录
    */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AddRedis
    public Admin loginAdmin(String username) {
        return adminDao.loginAdmin(username);
    }

    /**
     *@Description:添加
    */
    @Override
    public void insertAdmin(Admin admin) {
        admin.setId(UUID.randomUUID().toString());
        adminDao.insertAdmin(admin);
    }
}
