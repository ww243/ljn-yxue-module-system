package com.my;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/16 15:48
 */
@SpringBootApplication
@tk.mybatis.spring.annotation.MapperScan("com.my.dao")
@MapperScan("com.my.dao")
public class YixueAppcation {
    public static void main(String[] args) {
        SpringApplication.run(YixueAppcation.class,args);
    }
}
