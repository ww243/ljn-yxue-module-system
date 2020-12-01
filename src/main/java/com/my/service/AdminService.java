package com.my.service;

import com.my.entity.Admin;

/**
 * @author:ljn
 * @Description:管理员业务层
 * @Date:2020/11/16 15:34
 */
public interface AdminService {
    //登录
    Admin loginAdmin(String username);
    //添加用户
    void insertAdmin(Admin admin);

}
