package com.my.dao;


import com.my.entity.User;
import com.my.po.CityPo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {

    //修改状态
    void modfiyStatus(User user);
    //用户分布
    List<CityPo> findAllBySex(String sex);
    //用户统计
    Integer findAllByMonth(@Param("sex") String sex,
                                 @Param("month")Integer month);
}