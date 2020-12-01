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

@Table(name = "yx_log")
@AllArgsConstructor
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Log implements Serializable {
    @Id
    private String id;

    @Column(name = "log_name")
    private String logName;//名称

    @Column(name = "log_times")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date logTimes;//时间

    @Column(name = "log_option")
    private String logOption;//详细操作

    private String status;//状态

}