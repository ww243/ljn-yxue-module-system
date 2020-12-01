package com.my.service;

import com.my.entity.Category;
import com.my.entity.Video;
import com.my.po.VideoPo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author:ljn
 * @Description:视频业务层
 * @Date:2020/11/23 19:14
 */
public interface VideoService {
    //分页展示
    Map<String,Object> selectAll(Integer page, Integer rows);
    //增加
    Map<String,Object> insertCategory(Video video);
    //修改
    Map<String,Object> modfiyCategory(Video video);
    //删除
    Map<String,Object> removeCategory(Video video);
    //上传阿里云服务器
    void insertAliyun(MultipartFile videoPath, String id);
    //修改阿里云服务器
    void modfiyAliyun(MultipartFile videoPath, String id);


    /**
     *@Description:前台
    */
    //查所有
    List<VideoPo> queryByReleaseTime();
    //模糊查询
    List<VideoPo> queryByLikeVideoName(String content);



}
