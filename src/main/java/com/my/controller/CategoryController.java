package com.my.controller;

import com.alibaba.druid.util.StringUtils;
import com.my.entity.Category;
import com.my.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:ljn
 * @Description:一级类别、二级类别、控制器
 * @Date:2020/11/20 16:47
 */
@Controller
@RequestMapping("category")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("selectAll1")
    @ResponseBody//一级类别查所有
    public Map<String,Object> selectAll1(Integer page,Integer rows){
        HashMap<String, Object> map = new HashMap<>();
        List<Category> categories = categoryService.selectAll1(page, rows);
        log.debug("一级数据:{}",categories);
        Integer count = categoryService.count1();
        log.debug("一级长度:{}",count);
        Integer total= count%rows==0?count/rows:count/rows+1;
        log.debug("一级当前页数:{}",total);
        map.put("page",page);
        map.put("total",total);
        map.put("records",count);
        map.put("rows",categories);
        return map;
    }
    @RequestMapping("selectAll2")
    @ResponseBody//二级类别查所有
    public Map<String,Object> selectAll2(Integer page,Integer rows,String id){
        HashMap<String, Object> map = new HashMap<>();
        List<Category> categories = categoryService.selectAll2(page, rows,id);
        log.debug("二级数据:{}",categories);
        Integer count = categoryService.count2(id);
        log.debug("二级长度:{}",count);
        Integer total= count%rows==0?count/rows:count/rows+1;
        log.debug("二级当前页数:{}",total);
        map.put("page",page);
        map.put("total",total);
        map.put("records",count);
        map.put("rows",categories);
        return map;
    }

    @ResponseBody//一级、二级、增删改
    @RequestMapping("exit")
    public Map<String, Object> exit(Category category,String oper){
        Map<String, Object> map=null;
            if(StringUtils.equals("add",oper)) map = categoryService.insertCategory(category);
            else if(StringUtils.equals("edit",oper)) map = categoryService.modfiyCategory(category);
            else if(StringUtils.equals("del",oper)) map = categoryService.removeCategory(category);
        return map;
    }

    @ResponseBody//查询所有二级类别
    @RequestMapping("findAll")
    public void findAll(HttpServletResponse response)throws Exception{
        List<Category> all = categoryService.findAll();
        StringBuilder builder = new StringBuilder();
        builder.append("<select>");
        for (Category category : all) {
            builder.append("<option value="+category.getId()+">"+category.getCateName()+"</option>");
        }
        builder.append("</select>");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.getWriter().println(builder.toString());
    }
}
