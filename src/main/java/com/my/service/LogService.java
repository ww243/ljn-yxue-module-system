package com.my.service;

import java.util.Map;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/24 19:13
 */
public interface LogService {
    //查看所有日志
    Map<String,Object> findAll(Integer page, Integer rows);
}
