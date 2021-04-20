package com.zyz.demo.mapper;

import com.zyz.demo.pojo.caseinfo;

import java.util.List;
import java.util.Map;

public interface TprCaseMapper {
    List<Map> getcaselist();
    List<Map> getcaselistByname(String projectname);
    List<Map> getcaserunlist();//所有执行过的案例
    List<Map> getcaserunresultlistByname(String projectname);//获得单个项目不同模块成功失败案例执行结果（成功/失败）


}
