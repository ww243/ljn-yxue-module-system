package com.my.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.my.annotcation.AddLog;
import com.my.annotcation.AddRedis;
import com.my.annotcation.DelRedis;
import com.my.dao.VideoDao;
import com.my.entity.Video;
import com.my.example.VideoExample;
import com.my.po.VideoPo;
import com.my.service.VideoService;
import com.my.util.AliyunUtils;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.session.RowBounds;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author:ljn
 * @Description:视频业务实现
 * @Date:2020/11/23 19:15
 */
@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     *@Description:分页查询
    */
    @Override
    @AddRedis
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,Object> selectAll(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        VideoExample videoExample = new VideoExample();//设置条件
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);//设置分页参数
        //分页查询数据
        List<Video> videos = videoDao.selectByExampleAndRowBounds(videoExample, rowBounds);
        int count = videoDao.selectCountByExample(videoExample);
        Integer total=count%rows==0?count/rows:count/rows+1;
        map.put("page",page);//设置当前页
        map.put("total",total);//设置总页数
        map.put("records",count);//设置总条数
        map.put("rows",videos);//设置分页数据
        return map;
    }

    /**
     *@Description:添加
     */
    @Override
    @AddLog("视频添加功能")
    @DelRedis
    public Map<String, Object> insertCategory(Video video) {
        HashMap<String, Object> map = new HashMap<>();
        video.setId(UUID.randomUUID().toString());
        video.setUploadTime(new Date());
        try {
            //进入索引库进行添加
            IndexRequest indexRequest = new IndexRequest("yingx","yingxs",video.getId());
            indexRequest.source(JSONObject.toJSONStringWithDateFormat(video,"yyyy-MM-dd"), XContentType.JSON);
            IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println(index.status());
            videoDao.insert(video);
            map.put("message","添加成功!");
            map.put("rows",video);
        } catch (Exception e) {
            map.put("message","添加失败!");
            e.printStackTrace();
            throw new RuntimeException("添加失败");
        }
        return map;
    }

    /**
     *@Description:修改
     */
    @DelRedis
    @AddLog("视频修改功能")
    @Override
    public Map<String, Object> modfiyCategory(Video video) {
        HashMap<String, Object> map = new HashMap<>();
        video.setVideoPath(null);
        try {
            //进行es索引修改数据
            UpdateRequest updateRequest = new UpdateRequest("yingx", "yingxs", video.getId());
            updateRequest.doc(JSONObject.toJSONStringWithDateFormat(video,"yyyy-MM-dd"),XContentType.JSON);
            UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            System.out.println(update.status());
            videoDao.updateByPrimaryKeySelective(video);
            map.put("rows",video);
            map.put("message","修改成功!");
        } catch (Exception e) {
            map.put("message","修改失败!");
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
        return map;
    }

    /**
     *@Description:文件删除
     */
    @Override
    @DelRedis
    @AddLog("视频删除功能")
    public Map<String, Object> removeCategory(Video video) {
        HashMap<String, Object> map = new HashMap<>();
        OSS oss=AliyunUtils.aliyun();//封装工具类
        String bucketName="yingx-ljn";  //存储空间名
        Video one = videoDao.selectOne(video);//查一条数据
        String objectName="video/"+one.getVideoPath().split("video/")[1];//分割
        oss.deleteObject(bucketName,objectName);//删除aliyun的数据
        oss.shutdown();//关闭OSSClient
        String id = one.getId();//查一条数据id
        try {
            //进行es索引删除数据
            DeleteResponse delete = restHighLevelClient.delete(new DeleteRequest("yingx", "yingxs", video.getId()), RequestOptions.DEFAULT);
            videoDao.delete(video);//删除
            map.put("message","删除成功!");
        } catch (Exception e) {
            map.put("message","删除失败!");
            e.printStackTrace();
            throw new RuntimeException("删除失败");
        }
        return map;
    }

    /**
     *@Description:上传文件
    */
    @Override
    @DelRedis
    public void insertAliyun(MultipartFile videoPath, String id) {
            String filename = videoPath.getOriginalFilename();//获取文件名
            String newName = new Date().getTime()+"-"+filename;
            String bucketName="yingx-ljn";  //存储空间名
            String objectName="video/"+newName;  //保存的文件名
            OSS oss= AliyunUtils.aliyun();//封装工具类
            byte[] bytes = null;
            try {
                bytes = videoPath.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            oss.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));// 上传bytes数组
            oss.shutdown();//关闭OSSClient。
            VideoExample example = new VideoExample();
            example.createCriteria().andIdEqualTo(id);
            Video video = new Video();
            String videoPath1="https://yingx-ljn.oss-cn-beijing.aliyuncs.com/video/"+newName;
            String coverPath=videoPath1+"?x-oss-process=video/snapshot,t_0,f_jpg,w_0,h_0,m_fast,ar_auto";
            video.setVideoPath(videoPath1).setCoverPath(coverPath);
         try {
             //进行es索引修改数据
             UpdateRequest updateRequest = new UpdateRequest("yingx","yingxs",id);
             updateRequest.doc(JSONObject.toJSONStringWithDateFormat(video,"yyyy-MM-dd"),XContentType.JSON);
             UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
             System.out.println(update.status());
         } catch (Exception e) {
            e.printStackTrace();
        }
        videoDao.updateByExampleSelective(video,example);
        }

    /**
     *@Description:文件修改
    */
    @Override
    @DelRedis
    public void modfiyAliyun(MultipartFile videoPath, String id) {
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(id);
        Video video = videoDao.selectOneByExample(example);
        System.out.println(video);
        if(!videoPath.isEmpty()&&videoPath!=null&&videoPath.getOriginalFilename()!=""){
            OSS oss=AliyunUtils.aliyun();
            String bucketName="yingx-ljn";  //存储空间名
            Video one = videoDao.selectOne(video);//查一条数据
            String objectName="video/"+one.getVideoPath().split("video/")[1];//分割
            oss.deleteObject(bucketName,objectName);//删除aliyun的数据
            //重新上传
            String filename = videoPath.getOriginalFilename();//获取文件名
            String newName = new Date().getTime()+"-"+filename;
            String objectName1="video/"+newName;  //保存的文件名
            byte[] bytes = null;
            try {
                bytes = videoPath.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            oss.putObject(bucketName, objectName1, new ByteArrayInputStream(bytes));// 上传bytes数组
            oss.shutdown();//关闭OSSClient。
            String videoPath1="https://yingx-ljn.oss-cn-beijing.aliyuncs.com/"+objectName1;
            String coverPath=videoPath1+"?x-oss-process=video/snapshot,t_0,f_jpg,w_0,h_0,m_fast,ar_auto";
            video.setId(id).setVideoPath(videoPath1).setCoverPath(coverPath);
            try {
                //进行es索引修改数据
                UpdateRequest updateRequest = new UpdateRequest("yingx","yingxs",id);
                updateRequest.doc(JSONObject.toJSONStringWithDateFormat(video,"yyyy-MM-dd"),XContentType.JSON);
                UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
                System.out.println(update.status());
            } catch (Exception e) {
                e.printStackTrace();
            }
            videoDao.updateByExampleSelective(video,example);
        }
    }
    /**
     *@Description:es检索模糊查询
    */
    @Override
    public List<Video> selectHighlight(String content) {
        List<Video> list = new ArrayList();
            try{
                SearchRequest searchRequest = new SearchRequest();
                SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
                HighlightBuilder highlightBuilder = new HighlightBuilder();
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
                //设置高亮
                highlightBuilder.field("title").field("brief").requireFieldMatch(false).preTags("<font color='red'>").postTags("</font>");
                //模糊查类型
                queryBuilder.should(QueryBuilders.fuzzyQuery("title",content))
                        .should(QueryBuilders.fuzzyQuery("brief",content))
                        .should(QueryBuilders.wildcardQuery("title",content+"*"))
                        .should(QueryBuilders.wildcardQuery("brief",content+"*"))
                        .should(QueryBuilders.queryStringQuery(content).field("title").field("brief"));
                //添加配置分类、高亮、模糊查类型
                searchSourceBuilder.query(queryBuilder).highlighter(highlightBuilder);
                //配置索引、类型
                searchRequest.indices("yingx").types("yingxs").source(searchSourceBuilder);
                SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
                System.out.println(search);

                //遍历数据
                SearchHit[] hits = search.getHits().getHits();
                for (SearchHit hit : hits) {
                    Map<String, Object> asMap = hit.getSourceAsMap();
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    Video video = new Video();
                        video.setId(hit.getId());
                        video.setTitle(String.valueOf(asMap.get("title")));
                        if (highlightFields.containsKey("title")){
                            video.setTitle(String.valueOf(highlightFields.get("title").fragments()[0]));
                        }
                        video.setBrief(String.valueOf(asMap.get("brief")));
                        if (highlightFields.containsKey("brief")){
                            video.setBrief(String.valueOf(highlightFields.get("brief").fragments()[0]));
                        }
                    video.setCoverPath(String.valueOf(asMap.get("coverPath")));
                    String uploadTime = String.valueOf(asMap.get("uploadTime"));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = format.parse(uploadTime);
                    video.setUploadTime(date);
                    list.add(video);
                }
            }catch (Exception e){
             e.printStackTrace();
            }
        return list;
    }



    /**
     *@Description:前台
    */
    /**
     *
     *
     *@Description:查所有
    */
    @Override
    @AddRedis
    public List<VideoPo> queryByReleaseTime() {
        List<VideoPo> all = videoDao.queryByReleaseTime();
        for (VideoPo po : all) {
            String id = po.getId();
            po.setLikeCount(8);
        }
        return all;
    }

    @Override
    @AddRedis
    public List<VideoPo> queryByLikeVideoName(String content) {
        return videoDao.queryByLikeVideoName(content);
    }
}
