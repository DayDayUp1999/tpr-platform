package com.zyz.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.zyz.demo.service.TprReportService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TprReportController {

    @Resource
    private TprReportService tprReportService;

    @RequestMapping(value = "/report/getprojectlist" , method = RequestMethod.POST)
    public JSONObject getdayreport(){
        JSONObject data=new JSONObject();
        List<Map> projectlist = tprReportService.getprojectlist();
        data.put("projectlist",projectlist);
        System.out.println("projectlist>>>"+projectlist);
        return data;
    }

    @RequestMapping(value = "/report/getdayreport")
    public void  getdayreport(@RequestParam(value = "projectid",required = false) String projectid,
                                   HttpServletResponse response)  {

        System.out.println(">>>>>>>>>>>>>>>projectid:"+projectid);
        Workbook workbook=tprReportService.exportMoreSheetByTemplateday(projectid);


        // 指定下载的文件名--设置响应头
        response.setHeader("Content-Disposition", "attachment;filename=test.xlsx");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            OutputStream output = response.getOutputStream();
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
            workbook.write(bufferedOutPut);
            bufferedOutPut.flush();
            bufferedOutPut.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/report/getweekreport")
    public void getweekreport(@RequestParam(value = "projectid",required = false) String projectid,
                                    @RequestParam(value = "opentime",required = false) String opentime1,
                                    @RequestParam(value = "endtime",required = false) String endtime1,
                                    HttpServletResponse response) throws ParseException {
        Map info=new HashMap();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1=format.parse(opentime1);
        Date date2=format.parse(endtime1);
        String opentime = format.format(date1);
        String endtime = format.format(date2);
        info.put("projectid",projectid);
        info.put("opentime",opentime);
        info.put("endtime",endtime);

        Workbook workbook=tprReportService.exportMoreSheetByTemplateweek(info);

        String excelName = "测试excel" ;
        // 指定下载的文件名--设置响应头
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            OutputStream output = response.getOutputStream();
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
            workbook.write(bufferedOutPut);
            bufferedOutPut.flush();
            bufferedOutPut.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/report/getinfotest")
    public JSONObject getinfotest(@RequestParam(value = "projectid",required = false) String projectid,
                                  @RequestParam(value = "opentime",required = false) Date opentime1,
                                  @RequestParam(value = "endtime",required = false) Date endtime1
                                  ){
        JSONObject data=new JSONObject();
        Map info=new HashMap();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String opentime = format.format(opentime1);
        String endtime = format.format(endtime1);
        info.put("projectid",projectid);
        info.put("opentime",opentime);
        info.put("endtime",endtime);

        Map test=tprReportService.getinfotest(info);

        data.put("datalist",test);
        return data;
    }

    @RequestMapping(value = "/report/getselectprojectnamebyid" , method = RequestMethod.POST)
    public JSONObject getselectprojectnamebyid(@RequestParam(value = "projectid",required = false) String projectid){
        JSONObject data=new JSONObject();
        String projectname = tprReportService.getprojectnamebyid(projectid);
        data.put("projectname",projectname);
        return data;
    }

    @RequestMapping(value = "/report/checkdayreportpermission" , method = RequestMethod.POST)
    public JSONObject checkdaypermission(){
        JSONObject data=new JSONObject();
        data.put("flag","true");
        return data;
    }

    @RequestMapping(value = "/report/checkweekreportpermission" , method = RequestMethod.POST)
    public JSONObject checkweekpermission(){
        JSONObject data=new JSONObject();
        data.put("flag","true");
        return data;
    }

}
