package com.my.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author:ljn
 * @Description:通用缓存
 * @Date:2020/11/27 16:22
 */
@Configuration
@Aspect
public class CacheHashAspect {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    //添加缓存
    //添加缓存
    @Around("@annotation(com.my.annotcation.AddRedis)")
    public Object addCache(ProceedingJoinPoint proceedingJoinPoint){

        System.out.println("==环绕通知:");

        //Redis(Key,Value)
        /*
         * key:类名+方法名+实参
         * value:缓存数据
         * */

        //序列化解决乱
        StringRedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);

        StringBuilder sb = new StringBuilder();

        //获取类名
        String className = proceedingJoinPoint.getTarget().getClass().getName();

        //获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        sb.append(methodName);

        //获取参数
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            sb.append(arg);
        }

        String key = sb.toString();

        //判断key是否存在
        HashOperations opsForHash = redisTemplate.opsForHash();
        Boolean aBoolean = opsForHash.hasKey(className, key);

        Object result =null;

        if(aBoolean){
            //key存在
            result = opsForHash.get(className,key);
            //从redis中取出缓存数据
        }else{
            //key不存在
            //没有缓存数据
            try {
                //放行方法(返回数据)
                result = proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            //加入缓存  放入redis
            opsForHash.put(className,key,result);
        }
        return result;
    }
    //清除缓存
    @After("@annotation(com.my.annotcation.DelRedis)")
    public void delCache(JoinPoint joinPoint){

        System.out.println("后置通知:");

        //获取类的全限定名
        String className = joinPoint.getTarget().getClass().getName();

        //清除缓存
        redisTemplate.delete(className);
    }
}
