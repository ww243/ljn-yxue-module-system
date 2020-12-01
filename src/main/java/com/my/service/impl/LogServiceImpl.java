package com.my.service.impl;

import com.my.annotcation.AddRedis;
import com.my.dao.LogDao;
import com.my.entity.Log;
import com.my.example.LogExample;
import com.my.service.LogService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/24 19:13
 */
@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    /**
     *@Description:日志查所有
    */
    @AddRedis
    public Map<String, Object> findAll(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        LogExample logExample = new LogExample();//设置条件
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);//设置分页参数
        //分页查询数据
        List<Log> logList = logDao.selectByExampleAndRowBounds(logExample, rowBounds);
        int count = logDao.selectCountByExample(logExample);
        Integer total=count%rows==0?count/rows:count/rows+1;
        map.put("page",page);//设置当前页
        map.put("total",total);//设置总页数
        map.put("records",count);//设置总条数
        map.put("rows",logList);//设置分页数据
        return map;
    }
}
