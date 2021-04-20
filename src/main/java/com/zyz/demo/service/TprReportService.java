package com.zyz.demo.service;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

public interface TprReportService {
    public String getdayreport();
    public List<Map> getweekreport();

    public List<Map> getprojectlist();
    public String getprojectnamebyid(String moudleid);

    public Workbook exportMoreSheetByTemplateday(String moudleId);

    public Workbook exportMoreSheetByTemplateweek(Map info);



    public Map getinfotest(Map info);
}
