package com.my.text;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.GetObjectRequest;
import com.my.YixueAppcation;
import com.my.dao.UserDao;
import com.my.entity.User;
import com.my.service.UserService;
import com.my.util.AliyunUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/26 10:44
 */
@SpringBootTest(classes = YixueAppcation.class)
@RunWith(SpringRunner.class)
public class PoiTests {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;


    @Test
    public void t(){
        /*Map<String, Object> map = userService.easyPOI();
        List<CityPo> sex = userService.findAllBySex("女");
        sex.forEach(a-> System.out.println(a));*/
        String format = new SimpleDateFormat("MM").format(new Date());
        Integer integer = Integer.valueOf(format);

    }
    @Test
    public String tet(int i){
        SimpleDateFormat format = new SimpleDateFormat("MM");
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.MONTH,-i);
        Date time = instance.getTime();
        return format.format(time);
    }

    @Test
    public void text(){
        List<User> users = userDao.selectAll();
        //导出设置的参数  参数:大标题,工作表名
        ExportParams exportParams = new ExportParams("用户数据", "用户");
        //导出工具   参数:导出的参数,对应的实体类,导出的集合
        OSS oss = AliyunUtils.aliyun();
        String bucketName = "yingx-ljn";
        String objectName = null;
        for (int i=0;i<users.size();i++) {
            objectName = "user-imgs/" + users.get(i).getPicImg().split("user-imgs/")[1];
            users.get(i).setPicImg("src/main/webapp/bootstrap/cover/" + objectName);
            oss.getObject(new GetObjectRequest(bucketName, objectName), new File(users.get(i).getPicImg()));
        }
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, User.class, users);
        try {
            workbook.write(new FileOutputStream(new File("E:\\userAll.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            oss.shutdown();
        }
    }

    @Test
    public void PoiImport(){
        try {
            //创建Excel文档
            Workbook workbook = new HSSFWorkbook(new FileInputStream(new File("E:\\testpoi.xls")));
            //获取Sheet
            Sheet sheet = workbook.getSheet("学生信息表");
            for (int i = 2; i <=sheet.getLastRowNum(); i++) {
                //获取行
                Row row = sheet.getRow(i);
                //获取单元格
                Cell cell = row.getCell(0);
                //获取单元格内容
                String id = cell.getStringCellValue();
                String a1 = row.getCell(1).getStringCellValue();
                String a2 = row.getCell(2).getStringCellValue();
                String a3 = row.getCell(3).getStringCellValue();
                String a4 = row.getCell(4).getStringCellValue();
                String a5 = row.getCell(5).getStringCellValue();
                String a6 = row.getCell(6).getStringCellValue();
                String a7 = row.getCell(7).getStringCellValue();
                String a8 = row.getCell(8).getStringCellValue();
                Date date = row.getCell(9).getDateCellValue();

                User user = new User(id,a1,a2,a3,a4,a5,a6,a7,a8,date);
                System.out.println(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void PoiExport(){
        List<User> list = userDao.selectAll();
        //创建一个Excel文档
        Workbook workbook = new HSSFWorkbook();

        //创建一个工作表   参数:工作表表明  默认:sheet1,sheet2....
        Sheet sheet1 = workbook.createSheet("学生信息表1");

        //设置列宽  参数:列索引,列宽  单位 1/256
        sheet1.setColumnWidth(10,20*256);

        //创建合并单元格对象  参数:int firstRow(开始行), int lastRow(结束行), int firstCol(开始单元格), int lastCol(结束单元格)
        CellRangeAddress addresses = new CellRangeAddress(0,0,0,10);

        //合并单元格
        sheet1.addMergedRegion(addresses);


        //创建字体对象
        Font font = workbook.createFont();

        font.setFontName("微软雅黑"); //字体
        font.setBold(true); //加粗
        font.setColor(IndexedColors.GREEN.getIndex()); //颜色
        font.setFontHeightInPoints((short)15);  //字号
        //font.setItalic(true);    //斜体
        font.setUnderline(FontFormatting.U_SINGLE);  //下划线


        //创建单元格样式对象
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);  //文字居中
        cellStyle1.setFont(font); //设置字体样式

        //创建标题行
        Row r = sheet1.createRow(0);
        //创建标题单元格
        Cell c = r.createCell(0);
        //设置单元格样式
        c.setCellStyle(cellStyle1);
        //单元格设置数据
        c.setCellValue("学生信息表");

        //目录行
        String [] titles={"编号","姓名","性别","地址","电话","头像","简介","学分","状态","创建时间"};

        //创建一行   参数:行下标(从0开始)
        Row row = sheet1.createRow(1);

        //设置行高  参数:行高  单位 1/20
        row.setHeight((short)(20*20));

        for (int i = 0; i < titles.length; i++) {

            //创建单元格
            Cell cell = row.createCell(i);

            //设置数据
            cell.setCellValue(titles[i]);
        }

        //创建日期样式对象
        DataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy年MM月dd");//设置日期样式
        //创建单元格样式对象
        CellStyle cellStyle = workbook.createCellStyle();
        //将日期样式交给单元格样式对象
        cellStyle.setDataFormat(format);

        //处理数据
        for (int i = 0; i < list.size(); i++) {

            //创建一行
            Row rows = sheet1.createRow(i+2);

            //创建单元格  设置数据
            rows.createCell(0).setCellValue(list.get(i).getId());
            rows.createCell(1).setCellValue(list.get(i).getNickname());
            rows.createCell(2).setCellValue(list.get(i).getSex());
            rows.createCell(3).setCellValue(list.get(i).getAddress());
            rows.createCell(4).setCellValue(list.get(i).getPhone());
            rows.createCell(5).setCellValue(list.get(i).getPicImg());
            rows.createCell(6).setCellValue(list.get(i).getBrief());
            rows.createCell(7).setCellValue(list.get(i).getScore());
            rows.createCell(8).setCellValue(list.get(i).getStatus());
            rows.createCell(9).setCellValue(list.get(i).getUserDate());

        }

        try {
            //导出
            workbook.write(new FileOutputStream(new File("E:\\testpoi.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void textPoi(){


        //创建一个Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建一个工作表  参数：工作表表名   默认：sheet1,sheet2...
        HSSFSheet sheet1 = workbook.createSheet("学生信息表1");
        HSSFSheet sheet2 = workbook.createSheet("学生信息表2");

        //创建一行  参数：行下标（从0开始）
        HSSFRow row = sheet1.createRow(5);

        //创建一个单元格  参数：单元格下标(从0开始)
        HSSFCell cell = row.createCell(2);

        //给单元格设置数据
        cell.setCellValue("这是第六行,第三个表格");

        try {
            workbook.write(new FileOutputStream(new File("E:\\testpoi.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
