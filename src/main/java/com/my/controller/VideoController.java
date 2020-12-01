package com.my.controller;

import com.alibaba.druid.util.StringUtils;
import com.my.dao.VideoDao;
import com.my.entity.Video;
import com.my.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author:ljn
 * @Description:视频控制器
 * @Date:2020/11/23 19:54
 */
@Controller
@RequestMapping("video")
public class VideoController {

    private static final Logger log = LoggerFactory.getLogger(VideoController.class);
    @Autowired
    private VideoService videoService;

    @RequestMapping("selectAll")
    @ResponseBody
    public Map<String,Object> selectAll(Integer page, Integer rows){
        return videoService.selectAll(page, rows);
    }

    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(Video video,String oper){
        Map<String, Object> map=null;
        if(StringUtils.equals("add",oper)) map = videoService.insertCategory(video);
        else if(StringUtils.equals("edit",oper)) map = videoService.modfiyCategory(video);
        else if(StringUtils.equals("del",oper)) map = videoService.removeCategory(video);
        return map;
    }

    @RequestMapping("insertAliyun")
    public void insertAliyun(MultipartFile videoPath,String id){
        videoService.insertAliyun(videoPath, id);
    }

    @RequestMapping("modfiyAliyun")
    public void modfiyAliyun(MultipartFile videoPath,String id){
        videoService.modfiyAliyun(videoPath, id);
    }


}
