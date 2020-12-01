package com.my.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/17 8:40
 */
@Table(name = "yx_admin")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Admin implements Serializable {
    @Id
    private String id;
    private String username;//管理员姓名
    private String password;//管理员密码

}
