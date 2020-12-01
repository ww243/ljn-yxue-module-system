package com.my.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.bridge.IMessage;

import java.io.Serializable;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/25 14:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult implements Serializable {

    private String status;//状态
    private String message;//异常信息
    private Object data;//参数

    public CommonResult success(String status,String message,Object data){
        CommonResult result = new CommonResult();
        result.setStatus(status);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public CommonResult success(String message,Object data){
        CommonResult result = new CommonResult();
        result.setStatus("100");
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    public CommonResult success(Object data){
        CommonResult result = new CommonResult();

        result.setStatus("100");
        result.setMessage("请求成功");
        result.setData(data);

        return result;
    }

    public CommonResult failed(String message,Object data){
        CommonResult result = new CommonResult();

        result.setStatus("104");
        result.setMessage(message);
        result.setData(data);

        return result;
    }

    public CommonResult failed(String message){
        CommonResult result = new CommonResult();

        result.setStatus("104");
        result.setMessage(message);
        result.setData(null);
        return result;
    }


    public CommonResult failed(){
        CommonResult result = new CommonResult();
        result.setStatus("104");
        result.setMessage("请求失败");
        result.setData(null);
        return result;
    }

}
