package com.my.aspect;

import com.my.annotcation.AddLog;
import com.my.dao.LogDao;
import com.my.entity.Admin;
import com.my.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/24 18:18
 */
@Configuration
@Aspect
public class LogAspect {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private LogDao logDao;

    @Around("@annotation(com.my.annotcation.AddLog)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint){
        //获取用户数据
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //获取方法名
        String name = proceedingJoinPoint.getSignature().getName();
        //获取方法
        MethodSignature signature= (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        //获取注解
        String value = method.getAnnotation(AddLog.class).value();
        HashMap<String, Object> map = new HashMap<>();
        map.put("message","操作成功");
        //获取注解对应的方法
        String message=null;
        Object result=null;
        //放行方法
        try {
            result = proceedingJoinPoint.proceed();
            String s = result.toString();
            message = "success";
        } catch (Throwable throwable) {
            map.put("message",throwable.getMessage());
            message = "error";
        }
        Log lo = new Log();
            lo.setId(UUID.randomUUID().toString())
                    .setLogName(admin.getUsername())
                    .setLogTimes(new Date())
                    .setLogOption(name+" ("+value+")")
                    .setStatus(message);
            logDao.insertSelective(lo);
            if (result==null) return map;
            return result;
    }

}
