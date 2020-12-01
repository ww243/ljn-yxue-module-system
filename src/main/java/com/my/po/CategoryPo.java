package com.my.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/25 16:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPo implements Serializable {
    private String id;
    private String cateName;
    private Integer levels;
    private String parentId;
    private List<CategoryPo> categoryList;
}
