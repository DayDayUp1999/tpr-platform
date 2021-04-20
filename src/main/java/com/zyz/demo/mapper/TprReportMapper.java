package com.zyz.demo.mapper;

import java.util.List;
import java.util.Map;

public interface TprReportMapper {
    List<Map> getdayreport();
    List<Map> getweekreport();
    List<Map> getprojectlist();
    //根据id获取项目名称
     String getprojectnamebyid(String moudleId);

    //根据项目ID获取所有模块名称
    List<String> getnamelist(String projectid);

    ///////////////////////////////////////分界线，以下为每日报表所用

    //获得每个模块总案例数目
    List<Map> getdayallcasenumber(Map info);

    //获得每个模块本日执行数目
    List<Map> getdayrunnumber(Map info);

    //获取每个模块已执行案例成功数
    List<Map> getdayrunseccessnumber(Map info);

    //获取各模块本日新增缺陷数
    List<Map> getdaynewbugnumber(Map info);

    //获取各模块本日截至目前缺陷总数
    List<Map> getdayallbugnumber(Map info);

    //获取各模块目前已解决缺陷数
    List<Map> getdaysolvedbugnumber(Map info);

    //获取截止目前已执行的用例数（计算执行进度所用）
    List<Map> getdayrunedcasenumber(Map info);

    //获取本日各模块测试案例新增数
    List<Map> getdaynewcasenumber(Map info);

    //获取本日各模块案例执行情况
    List<Map> getdayrunstatusnumber(Map info);

    //获取本日各模块新增缺陷情况数（致命，严重，一般）
    List<Map> getdaynewbugseveritynumber(Map info);

    //获取本日各模块缺陷状态（已解决，解决中，未解决）
    List<Map> getdaynewbugstatusnumber(Map info);

    ////////////////////////////////////////////////////////分界线，以下为每周报表所用

    //获得每个模块总案例数目
    List<Map> getweekallcasenumber(Map info);

    //获得每个模块本周执行数目
    List<Map> getweekrunnumber(Map info);

    //获取每个模块已执行案例成功数
    List<Map> getweekrunseccessnumber(Map info);

    //获取各模块本周新增缺陷数
    List<Map> getweeknewbugnumber(Map info);

    //获取各模块截至目前缺陷总数
    List<Map> getweekallbugnumber(Map info);

    //获取各模块目前已解决缺陷数
    List<Map> getweeksolvedbugnumber(Map info);

    //获取截止目前已执行的用例数（计算执行进度所用）
    List<Map> getweekrunedcasenumber(Map info);

    ///////////////.................................
    //获取本周各模块测试案例新增数
    List<Map> getweeknewcasenumber(Map info);

    //获取本周各模块案例执行情况
    List<Map> getweekrunstatusnumber(Map info);

    //获取一周内截止各日某项目的案例总数(图表生成用)
    int getweekdayallcasenumber(Map info);

    //获取一周内截止各日某项目的已执行案例总数(图表生成用)
    int getweekdayrunedcasenumber(Map info);

    //获取一周内截止各日某项目的已执行案例成功总数(图表生成用)
    int getweekdayrunsuccesscasenumber(Map info);

    //获取一周内截止各日某项目当日执行案例数（图表生成用）
    int getweekdayruncasenumber(Map info);

    ///////////////.................................
    //获取本周各模块新增缺陷情况数（致命，严重，一般）
    List<Map> getweeknewbugseveritynumber(Map info);

    //获取本周各模块缺陷状态（已解决，解决中，未解决）
    List<Map> getweeknewbugstatusnumber(Map info);

    //获取本周各模块未解决缺陷情况（致命，严重，一般，a-2使用）
    List<Map> getweeknosolvedbugseverity(Map info);

    //获取一周内当日新增缺陷数
    int getweekdaynewbugnumber(Map info);

    //获取一周内当日解决缺陷数
    int getweekdaysolvedbugnumber(Map info);

    //获取一周内截止当日累计总缺陷数
    int getweekdayallbugnumber(Map info);

    //获取一周内截止当日累计已解决缺陷数
    int getweekdayallsolvedbugnumber(Map info);






}
