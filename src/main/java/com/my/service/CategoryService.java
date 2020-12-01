package com.my.service;

import com.my.entity.Category;
import com.my.po.CategoryPo;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * @author:ljn
 * @Description:类别业务层
 * @Date:2020/11/20 14:52
 */
public interface CategoryService {

    //一级分页查所有
    List<Category> selectAll1(Integer page, Integer rowNum);
    //二级分页查所有
    List<Category> selectAll2(Integer page, Integer rowNum,String id);
    //一级总页数
    Integer count1();
    //二级总页数
    Integer count2(String id);
    //一级和二级的增加
    Map<String,Object> insertCategory(Category category);
    //一级和二级的修改
    Map<String,Object> modfiyCategory(Category category);
    //一级和二级的删除
    Map<String,Object> removeCategory(Category category);
    //查二级类别所有数据
    List<Category> findAll();

    /**
     *@Description:前台
    */

    List<CategoryPo> queryAllCategory();


}
