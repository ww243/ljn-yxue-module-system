package com.my.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "yx_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Category implements Serializable {

    @Id
    private String id;
    @Column(name = "cate_name")
    private String cateName;//类别名字
    private Integer levels;//级别

    @Column(name = "parent_id")
    private String parentId;//父类别id

}