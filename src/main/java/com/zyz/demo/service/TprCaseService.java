package com.zyz.demo.service;

import com.zyz.demo.pojo.caseinfo;

import java.util.List;
import java.util.Map;

public interface TprCaseService {
    public List<Map> getcaselist();//所有项目案例
    public List<Map> getcaselistByname(String projectname);//获得单个项目不同模块案例执行情况（已执行/未执行）
    public List<Map> getcaserunlist();//所有执行过的案例
    public List<Map> getcaserunresultlistByname(String projectname);//获得单个项目不同模块成功失败案例执行结果（成功/失败）
}
