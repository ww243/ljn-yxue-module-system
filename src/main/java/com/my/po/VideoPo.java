package com.my.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:ljn
 * @Description:前台视频
 * @Date:2020/11/25 15:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoPo implements Serializable {

    private String id;//视频id
    private String videoTitle;//视频标题
    private String cover;//视频封面
    private String path;//视频路径
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date uploadTime;//视频上传时间
    private String description;//视频描述
    private Integer likeCount; //点赞数

    private String cateName;  //类别名
    private String userPhoto;  //用户头像

    private String categoryId;//类别id
    private String userId;//用户id
    private String userName;//用户Name

}
