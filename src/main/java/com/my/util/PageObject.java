package com.my.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/20 19:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PageObject<T> implements Serializable {
    private String page;
    private Integer total;
    private String records;
    private List<T> rows;

    public Integer getTotalPage(Integer count,Integer rows){
        return (count%rows==0)?count/rows:count/rows+1;
    }
}
