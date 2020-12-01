package com.my.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "yx_video")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Video implements Serializable {
    @Id
    private String id;
    private String title;//标题
    private String brief;//简介
    @Column(name = "cover_path")
    private String coverPath;//封面

    @Column(name = "video_path")
    private String videoPath;//视频

    @Column(name = "upload_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date uploadTime;//上传时间

    @Column(name = "category_id")
    private String categoryId;//类别id

    @Column(name = "user_id")
    private String userId;//用户id

    @Column(name = "group_id")
    private String groupId;//分组id

}