package com.my.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/26 21:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SexPo implements Serializable {

    private String sex;
    private List<CityPo> cityPos;

}
