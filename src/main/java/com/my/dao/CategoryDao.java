package com.my.dao;


import com.my.entity.Category;
import com.my.po.CategoryPo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryDao extends Mapper<Category> {

    List<CategoryPo> queryAllCategory();

}