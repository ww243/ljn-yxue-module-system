package com.my.service;

import com.my.entity.User;
import com.my.po.CityPo;

import java.util.List;
import java.util.Map;

/**
 * @author:ljn
 * @Description:用户业务层
 * @Date:2020/11/19 15:14
 */
public interface UserService {

    //分页查所有
    List<User> selectAll(Integer page, Integer rowNum);

    //总页数
    Integer count();

    //修改状态
    void modfiyStatus(User user);

    //添加
    void insertUser(User user);

    //修改
    void modfiyUser(User user);

    //导出
    Map<String, Object> easyPOI();

    //用户分布
    List<CityPo> findAllBySex(String sex);

    //用户统计
    Integer findAllByMonth(String sex,Integer month);


}
