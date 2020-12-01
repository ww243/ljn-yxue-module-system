package com.my.text;

import com.my.YixueAppcation;
import com.my.dao.CategoryDao;
import com.my.entity.Category;
import com.my.example.CategoryExample;
import com.my.service.CategoryService;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/20 15:56
 */
@SpringBootTest(classes = YixueAppcation.class)
@RunWith(SpringRunner.class)
public class CategoryTest {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CategoryService categoryService;

    @Test
    public void text(){
        CategoryExample category = new CategoryExample();
        category.createCriteria().andLevelsEqualTo(1);
        List<Category> list = categoryDao.selectByExample(category);
        list.forEach(a-> System.out.println(a));
    }
    @Test
    public void text1(){
        CategoryExample category = new CategoryExample();
        category.createCriteria().andLevelsEqualTo(2).andParentIdEqualTo("1");
        List<Category> list = categoryDao.selectByExample(category);
        list.forEach(a-> System.out.println(a));
    }
    @Test
    public void text2(){
        CategoryExample category = new CategoryExample();
        category.createCriteria().andLevelsEqualTo(2).andParentIdEqualTo("1");
        List<Category> list = categoryDao.selectByExample(category);
        list.forEach(a-> System.out.println(a));
    }
    @Test
    public void text3(){
        CategoryExample category = new CategoryExample();
        category.createCriteria().andLevelsEqualTo(1);
        RowBounds rowBounds = new RowBounds(0,1);
        List<Category> list = categoryDao.selectByExampleAndRowBounds(category, rowBounds);
        list.forEach(a-> System.out.println(a));
    }
    @Test
    public void text4(){
       //业务判断
        List<Category> list = categoryService.selectAll1(0, 1);
        list.forEach(a-> System.out.println(a));
        List<Category> list1 = categoryService.selectAll2(0, 1,"2");
        for (Category category : list1) {
            System.out.println(category);
        }
    }
    @Test
    public void text5(){
       /* //业务判断
        System.out.println(categoryService.count1());
        System.out.println(categoryService.count2("2"));
        System.out.println(categoryService.count2("1"));*/
        List<Category> all = categoryService.findAll();
        all.forEach(a-> System.out.println(a));

    }

}
