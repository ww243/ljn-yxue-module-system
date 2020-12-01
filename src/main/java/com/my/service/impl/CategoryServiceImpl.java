package com.my.service.impl;

import com.my.annotcation.AddLog;
import com.my.annotcation.AddRedis;
import com.my.annotcation.DelRedis;
import com.my.dao.CategoryDao;
import com.my.dao.VideoDao;
import com.my.entity.Category;
import com.my.example.CategoryExample;
import com.my.example.VideoExample;
import com.my.po.CategoryPo;
import com.my.service.CategoryService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author:ljn
 * @Description:类别业务实现
 * @Date:2020/11/20 14:52
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private VideoDao videoDao;

    /**
     *@Description:查询一级类别的分页
    */
    @AddRedis
    public List<Category> selectAll1(Integer page, Integer rowNum) {
        Integer begin=(page-1)*rowNum;
        CategoryExample example = new CategoryExample();
        //查询CategoryExample一级所有对象
        example.createCriteria().andLevelsEqualTo(1);
        //分页对象
        RowBounds rowBounds = new RowBounds(begin,rowNum);
        List<Category> list = categoryDao.selectByExampleAndRowBounds(example, rowBounds);
        return list;
    }

   /**
    *@Description:查询二级类别的分页
   */
   @AddRedis
    public List<Category> selectAll2(Integer page, Integer rowNum,String id) {
        Integer begin=(page-1)*rowNum;
        CategoryExample example = new CategoryExample();
        //查询CategoryExample二级所有对象
        example.createCriteria().andLevelsEqualTo(2).andParentIdEqualTo(id);
        //分页对象
        RowBounds rowBounds = new RowBounds(begin,rowNum);
        List<Category> list = categoryDao.selectByExampleAndRowBounds(example, rowBounds);
        return list;
    }

    /**
     *@Description:查询一级分页总数量
    */
    @AddRedis
    public Integer count1() {
        CategoryExample example = new CategoryExample();
        //查询一级数量
        example.createCriteria().andLevelsEqualTo(1);
        int i = categoryDao.selectCountByExample(example);
        return i;
    }

    /**
     *@Description:查询二级分页总数量
     */
    @AddRedis
    public Integer count2(String id) {
        CategoryExample example = new CategoryExample();
        //查询二级数量
        example.createCriteria().andLevelsEqualTo(2).andParentIdEqualTo(id);
        int i = categoryDao.selectCountByExample(example);
        return i;
    }

    @Override
    /**
     *@Description:添加一级二级类别
     */
    @AddLog("类别添加功能")
    @DelRedis
    public Map<String, Object> insertCategory(Category category) {
        HashMap<String, Object> map = new HashMap<>();
        category.setId(UUID.randomUUID().toString());
        category.setLevels(1);
        if (category.getParentId()!=null) category.setLevels(2);
        try {
            categoryDao.insert(category);
            map.put("message","添加成功!");
        } catch (Exception e) {
            map.put("message","添加失败!");
            e.printStackTrace();
            throw new RuntimeException("添加失败");
        }
        return map;
    }

    @Override
    /**
     *@Description:修改一级二级类别
     */
    @AddLog("类别修改功能")
    @DelRedis
    public Map<String, Object> modfiyCategory(Category category) {
        HashMap<String, Object> map = new HashMap<>();
        //修改
        try {
            categoryDao.updateByPrimaryKeySelective(category);
            map.put("message","修改成功!");
        } catch (Exception e) {
            map.put("message","修改失败!");
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
        return map;
    }

    @Override
    /**
     *@Description:删除一级二级类别
    */
    @AddLog("类别删除功能")
    @DelRedis
    public Map<String, Object> removeCategory(Category category) {
            HashMap<String, Object> map = new HashMap<>();
        //删除
            Category categorys = categoryDao.selectOne(category);
            if (categorys.getLevels() == 1) {
                //判断是否有子类别
                CategoryExample example = new CategoryExample();
                example.createCriteria().andParentIdEqualTo(category.getId());
                //根据以及类别查询对应二级类别的数量
                int count = categoryDao.selectCountByExample(example);
                if (count==0) {
                    //删除一级
                    categoryDao.deleteByPrimaryKey(category);
                    map.put("message", "删除成功");
                } else {
                    map.put("message", "该类别下有子类别,不能删除");
                    throw new RuntimeException("该类别下有子类别,不能删除");
                }
            } else {
                //判断该类别下是否有视频
                VideoExample videoExample = new VideoExample();
                videoExample.createCriteria().andCategoryIdEqualTo(categorys.getId());
                int videocount = videoDao.selectCountByExample(videoExample);
                log.debug("查询视频的数量: {}",videocount);
                if (videocount==0) {
                    categoryDao.deleteByPrimaryKey(category);//没有视频
                    map.put("message", "删除成功");
                } else {
                    map.put("message", "该类别下有视频,不能删除");//有视频 不能删除
                    throw new RuntimeException("该类别下有视频,不能删除");
                }
            }
        return map;
    }

    /**
     *@Description:查询二级类别所有人
    */
    @Override
    @AddRedis
    public List<Category> findAll() {
        CategoryExample example = new CategoryExample();
            example.createCriteria().andLevelsEqualTo(2);
        List<Category> list = categoryDao.selectByExample(example);
        return list;
    }

    /**
     *@Description:前台
    */

    /**
     *@Description:查所有
    */
    @Override
    @AddRedis
    public List<CategoryPo> queryAllCategory() {
        return categoryDao.queryAllCategory();
    }

}
