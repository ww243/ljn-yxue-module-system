package com.my.text;

import com.my.YixueAppcation;
import com.my.dao.VideoDao;
import com.my.po.VideoPo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/25 19:42
 */
@SpringBootTest(classes = YixueAppcation.class)
@RunWith(SpringRunner.class)
public class VideoTest {

    @Autowired
    private VideoDao videoDao;

    @Test
    public void text(){
        List<VideoPo> list = videoDao.queryByLikeVideoName("年");
        list.forEach(a-> System.out.println(a));
    }
}
