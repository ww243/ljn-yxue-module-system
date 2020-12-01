package com.my.dao;


import com.my.entity.Video;
import com.my.po.VideoPo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoDao extends Mapper<Video> {

    List<VideoPo> queryByReleaseTime();

    List<VideoPo> queryByLikeVideoName(String content);
}