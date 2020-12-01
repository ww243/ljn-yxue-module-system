package com.my.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.DataRedundancyType;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.junit.Test;

import java.io.File;

/**
 * @author:lih
 * @Description:
 * @Date:2020/11/20 20:27
 */
public class AliyunOss {

    public static void main(String[] args) {
        // Endpoint以北京为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4GBbR7q7DV9fjvFUdQha";
        String accessKeySecret = "DPQ3z6SQqVJZrF0d3mbwoIJ4BVtuqG";
        String bucketName="yingx-ljn";  //存储空间名  yingx-2005

    }

    /**
     *@Description:创建存储空间
    */
    @Test
    public void text(){
        // Endpoint以北京为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4GBbR7q7DV9fjvFUdQha";
        String accessKeySecret = "DPQ3z6SQqVJZrF0d3mbwoIJ4BVtuqG";
        String name="ying2005";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建存储空间。
        ossClient.createBucket(name);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    /**
     *@Description:删除存储空间
    */
    @Test
    public void text1(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4GBbR7q7DV9fjvFUdQha";
        String accessKeySecret = "DPQ3z6SQqVJZrF0d3mbwoIJ4BVtuqG";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除存储空间。
        ossClient.deleteBucket("yingx-ljn");

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void text3(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4GBbR7q7DV9fjvFUdQha";
        String accessKeySecret = "DPQ3z6SQqVJZrF0d3mbwoIJ4BVtuqG";
        String bucketName="yingx-ljn";  //存储空间名  yingx-2005
        String objectName="donghua.mp4";  //保存的文件名   1.MP4  aaa.mp4
        String localFile="C:\\Users\\25004\\Desktop\\后期项目\\video\\动画.mp4";   //本地文件位置
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new File(localFile));
        // 上传文件。
        ossClient.putObject(putObjectRequest);
        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
