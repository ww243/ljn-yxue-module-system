package com.my.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "yx_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {

    @Id
    @Excel(name = "编号")
    private String id;

    @Excel(name = "姓名")
    private String nickname;//昵称

    @Excel(name = "性别")
    private String sex;//性别

    @Excel(name = "地址")
    private String address;//城市

    @Excel(name = "手机号")
    private String phone;//手机号

    @Column(name = "pic_img")
    @Excel(name = "头像",type = 2)
    private String picImg;//头像

    @Excel(name = "简介")
    private String brief;//简介

    @Excel(name = "学分")
    private String score;//学分

    @Excel(name = "状态")
    private String status;//状态

    @Column(name = "user_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间",exportFormat="yyyy-MM-dd")
    private Date userDate;//创建时间



}