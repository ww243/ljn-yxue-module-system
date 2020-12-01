package com.my.dao;

import com.my.entity.Admin;

/**
 * @author:ljn
 * @Description: 管理员Dao层
 * @Date:2020/11/16 15:34
 */
public interface AdminDao {
    //登录
    Admin loginAdmin(String username);
    //添加
    void insertAdmin(Admin admin);

}
