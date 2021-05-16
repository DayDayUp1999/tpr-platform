package com.zyz.demo.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.zyz.demo.entity.TprUser;
import com.zyz.demo.mapper.TprReportMapper;
import com.zyz.demo.mytools.getTimesBetweenDates;
import com.zyz.demo.service.TprReportService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TprReportServiceImpl implements TprReportService {
    @Resource
    private TprReportMapper tprReportMapper;

    @Override
    public String getdayreport() {


            return null;
    }

    @Override
    public List<Map> getweekreport() {
        return null;
    }

    @Override
    public List<Map> getprojectlist() {
        List<Map> projectlist=tprReportMapper.getprojectlist();
        return projectlist;
    }

    @Override
    public String getprojectnamebyid(String moudleid) {
        String projectname=tprReportMapper.getprojectnamebyid(moudleid);
        return projectname;
    }

    @Override
    public Workbook exportMoreSheetByTemplateday(String projectId) {
        String projectname=tprReportMapper.getprojectnamebyid(projectId);
        //我要获取当前的日期
        Date date = new Date();
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取String类型的时间
        String time = sdf.format(date);
        String opentime=time+" 00:00:00";
        String endtime=time+" 23:59:59";

        SaSession loginuserinfo= StpUtil.getSession(true);
        TprUser loginuser= (TprUser) loginuserinfo.getAttribute("loginuserinfo");
        String  username=loginuser.getName();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectname",projectname);
        map.put("time",time);
        map.put("username",username);
        //根据项目ID获取所有该项目所有子模块名称
        List<String> namelist=tprReportMapper.getnamelist(projectId);

        //获取各模块总案例数
        Map temp=new HashMap();
        temp.put("projectid",projectId);
        List<Map> allcasenumber=tprReportMapper.getdayallcasenumber(temp);

        temp.put("opentime",opentime);
        temp.put("endtime",endtime);
        //获取各模块本日已执行案例数
        List<Map> runnumberlist=tprReportMapper.getdayrunnumber(temp);

        //获取各模块本日已执行案例成功数
        List<Map> runseccessnumberlist=tprReportMapper.getdayrunseccessnumber(temp);

        //获取各模块本日新增缺陷数
        List<Map> newbuglist=tprReportMapper.getdaynewbugnumber(temp);

        //获取各模块截至目前缺陷总数
        List<Map> allbuglist=tprReportMapper.getdayallbugnumber(temp);

        //获取各模块目前已解决缺陷数
        List<Map> solvedbuglist=tprReportMapper.getdaysolvedbugnumber(temp);

        //获取截止目前已执行的用例数（计算执行进度所用）
        List<Map> runedcaselist=tprReportMapper.getdayrunedcasenumber(temp);

        //获取本日测试案例新增数
        List<Map> newcaselist=tprReportMapper.getdaynewcasenumber(temp);

        //取本日各模块案例执行情况
        List<Map> runstatuslist=tprReportMapper.getdayrunstatusnumber(temp);

        //获取本日各模块新增缺陷情况数（致命，严重，一般）
        List<Map> newbugseveritylist=tprReportMapper.getdaynewbugseveritynumber(temp);

        //获取本日各模块缺陷状态（已解决，解决中，未解决）
        List<Map> newbugstatuslist=tprReportMapper.getdaynewbugstatusnumber(temp);

        //总体情况Sheet填充数据准备
        List<Map<String, String>> overalllistMap = new ArrayList<Map<String, String>>();

        for (int i=0;i<namelist.size();i++){
            Map test = new HashMap();
            test.put("moudlename",namelist.get(i));


            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (allcasenumber.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("allcasenumber", allcasenumber.get(j).get("number"));
                    }
                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("allcasenumber"))
                    {
                        //模块名字不同装入0
                        test.put("allcasenumber",0);
                    }
                }



            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (runnumberlist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("runnumber", runnumberlist.get(j).get("number"));
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("runnumber")){
                        //模块名字不同装入0
                        test.put("runnumber",0);
                    }
                }

            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (runseccessnumberlist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("runseccessnumber", runseccessnumberlist.get(j).get("number"));
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("runseccessnumber"))
                    {
                        //模块名字不同装入0
                        test.put("runseccessnumber",0);
                    }

                }


            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (newbuglist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("newbugnumber", newbuglist.get(j).get("number"));

                    }
                }catch(IndexOutOfBoundsException e){

                }
                finally {
                    if (!test.containsKey("newbugnumber")){
                        test.put("newbugnumber",0);
                    }
                }



            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (allbuglist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("allbugnumber", allbuglist.get(j).get("number"));
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("allbugnumber"))
                    {
                        //模块名字不同装入0
                        test.put("allbugnumber",0);
                    }

                }


            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (solvedbuglist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("solvedbugnumber", solvedbuglist.get(j).get("number"));
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("solvedbugnumber"))
                    {
                        //模块名字不同装入0
                        test.put("solvedbugnumber",0);
                    }

                }

            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (runedcaselist.get(j).get("moudlename").equals(namelist.get(i))){
                        long runednumber= (long) runedcaselist.get(j).get("number");
                        long allnumber=(long)allcasenumber.get(j).get("number");
                        int a = (int)runednumber;
                        int b = (int)allnumber;
                        DecimalFormat df = new DecimalFormat("0.0000");//格式化小数
                        String num = df.format(((float)a/b));//返回的是String类型
                        test.put("runedpercent",num);
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("runedpercent"))
                    {
                        //模块名字不同装入0
                        test.put("runedpercent","0%");
                    }

                }

            }

            overalllistMap.add(test);

        }

        map.put("overalllistMap",overalllistMap);

        //用例Sheet填充数据准备
        List<Map<String, String>> caselistMap = new ArrayList<Map<String, String>>();

        for (int i=0;i<namelist.size();i++){
            Map test = new HashMap();
            test.put("moudlename",namelist.get(i));


            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (newcaselist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("newcasenumber", newcaselist.get(j).get("number"));
                    }
                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("newcasenumber"))
                    {
                        //模块名字不同装入0
                        test.put("newcasenumber",0);
                    }
                }



            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (runnumberlist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("runnumber", runnumberlist.get(j).get("number"));
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("runnumber")){
                        //模块名字不同装入0
                        test.put("runnumber",0);
                    }
                }

            }


            for (int j=0;j<Math.max(runstatuslist.size(),namelist.size());j++){
                //如果模块名字相同，则放入数据
                try{
                    if (runstatuslist.get(j).get("moudlename").equals(namelist.get(i)) ){
                        if (runstatuslist.get(j).get("lastRunResult").equals("成功")){
                            test.put("runsuccessnumber", runstatuslist.get(j).get("number"));
                        }else if (runstatuslist.get(j).get("lastRunResult").equals("失败"))
                        {
                            test.put("runfailnumber", runstatuslist.get(j).get("number"));
                        }
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("runsuccessnumber"))
                    {
                        //模块名字不同装入0
                        test.put("runsuccessnumber",0);
                    }else if (!test.containsKey("runfailnumber")){
                        test.put("runfailnumber",0);
                    }

                }


            }


            caselistMap.add(test);

        }
        map.put("caselistMap",caselistMap);

        //缺陷跟踪Sheet填充数据准备
        List<Map<String, String>> bugfollowlistMap = new ArrayList<Map<String, String>>();
        for (int i=0;i<namelist.size();i++){
            Map test = new HashMap();
            test.put("moudlename",namelist.get(i));

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (newbuglist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("newbugnumber", newbuglist.get(j).get("number"));
                    }
                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("newbugnumber"))
                    {
                        //模块名字不同装入0
                        test.put("newbugnumber",0);
                    }
                }



            }


            for (int j=0;j<Math.max(newbugseveritylist.size(),namelist.size());j++){
                //如果模块名字相同，则放入数据
                try{
                    if (newbugseveritylist.get(j).get("moudlename").equals(namelist.get(i)) ){
                        if (newbugseveritylist.get(j).get("severity").equals("致命")){
                            test.put("zmnumber", newbugseveritylist.get(j).get("number"));
                        }else if (newbugseveritylist.get(j).get("severity").equals("严重"))
                        {
                            test.put("yznumber", newbugseveritylist.get(j).get("number"));
                        }else if (newbugseveritylist.get(j).get("severity").equals("一般"))
                        {
                            test.put("ybnumber", newbugseveritylist.get(j).get("number"));
                        }
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("zmnumber"))
                    {
                        //模块名字不同装入0
                        test.put("zmnumber",0);
                    }else if (!test.containsKey("yznumber")){
                        test.put("yznumber",0);
                    }
                    else if (!test.containsKey("ybnumber")){
                        test.put("ybnumber",0);
                    }

                }


            }

            for (int j=0;j<Math.max(newbugstatuslist.size(),namelist.size());j++){
                //如果模块名字相同，则放入数据
                try{
                    if (newbugstatuslist.get(j).get("moudlename").equals(namelist.get(i)) ){
                        if (newbugstatuslist.get(j).get("bugstatus").equals("已解决")){
                            test.put("yjjnumber", newbugstatuslist.get(j).get("number"));
                        }else if (newbugstatuslist.get(j).get("bugstatus").equals("未解决"))
                        {
                            test.put("wjjnumber", newbugstatuslist.get(j).get("number"));
                        }
                        else if (newbugstatuslist.get(j).get("bugstatus").equals("解决中"))
                        {
                            test.put("jjznumber", newbugstatuslist.get(j).get("number"));
                        }
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("yjjnumber"))
                    {
                        //模块名字不同装入0
                        test.put("yjjnumber",0);
                    }else if (!test.containsKey("wjjnumber")){
                        test.put("wjjnumber",0);
                    }
                    else if (!test.containsKey("jjznumber")){
                        test.put("jjznumber",0);
                    }

                }


            }


            bugfollowlistMap.add(test);

        }
        map.put("bugfollowlistMap",bugfollowlistMap);



        // 获取导出excel指定模版，设置多Sheet模式
        TemplateExportParams params = new TemplateExportParams("src/main/resources/static/测试日报模板.xlsx",true);

        // 设置sheetName，若不设置该参数，则使用得原本得sheet名称
        String[] sheetNameArray = {"封面","目录","项目总体情况","测试案例情况","缺陷跟踪情况"} ;
        params.setSheetName(sheetNameArray);
        // 导出excel
        return ExcelExportUtil.exportExcel(params,map);
    }

    @Override
    public Workbook exportMoreSheetByTemplateweek(Map info) {
        String projectid=(String)info.get("projectid");
        String opentime=(String)info.get("opentime");
        String endtime=(String)info.get("endtime");
        String sqlopentime=opentime+" 00:00:00";
        String sqlendtime=endtime+" 23:59:59";
        SaSession loginuserinfo= StpUtil.getSession(true);
        TprUser loginuser= (TprUser) loginuserinfo.getAttribute("loginuserinfo");
        String  username=loginuser.getName();
        String projectname=tprReportMapper.getprojectnamebyid(projectid);

        //获取开始到结束日期之间所有日期
        List<String> days= getTimesBetweenDates.getDays(opentime,endtime);


        //渲染模板所需数据Map
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectname",projectname);
        map.put("opentime",opentime);
        map.put("endtime",endtime);
        map.put("username",username);

        //mybatis查询参数map
        Map temp=new HashMap();
        temp.put("projectid",projectid);
        temp.put("opentime",sqlopentime);
        temp.put("endtime",sqlendtime);


        //根据项目ID获取所有该项目所有子模块名称
        List<String> namelist=tprReportMapper.getnamelist(projectid);

        //获取各模块总案例数
        List<Map> allcasenumber=tprReportMapper.getweekallcasenumber(temp);

        //获取各模块本周已执行案例数
        List<Map> runnumberlist=tprReportMapper.getweekallbugnumber(temp);

        //获取各模块本周已执行案例成功数
        List<Map> runseccessnumberlist=tprReportMapper.getweekrunseccessnumber(temp);

        //获取各模块本周新增缺陷数
        List<Map> newbuglist=tprReportMapper.getweeknewbugnumber(temp);

        //获取各模块截至目前缺陷总数
        List<Map> allbuglist=tprReportMapper.getweekallbugnumber(temp);

        //获取各模块目前已解决缺陷数
        List<Map> solvedbuglist=tprReportMapper.getweeksolvedbugnumber(temp);

        //获取截止目前已执行的用例数（计算执行进度所用）
        List<Map> runedcaselist=tprReportMapper.getweekrunedcasenumber(temp);
        /////////////////////////////////////////////////////////////////////////分界

        //获取本周测试案例新增数
        List<Map> newcaselist=tprReportMapper.getweeknewcasenumber(temp);

        //取本周各模块案例执行情况
        List<Map> runstatuslist=tprReportMapper.getweekrunstatusnumber(temp);
        ////////////////////////////////////////////////////////////////////////

        //获取本周各模块新增缺陷情况数（致命，严重，一般）
        List<Map> newbugseveritylist=tprReportMapper.getweeknewbugseveritynumber(temp);

        //获取本周各模块缺陷状态（已解决，解决中，未解决）
        List<Map> newbugstatuslist=tprReportMapper.getweeknewbugstatusnumber(temp);

        //获取本周各模块未解决缺陷情况（致命，严重，一般，a-2使用）
        List<Map> nosolvedbugseveritylist=tprReportMapper.getweeknosolvedbugseverity(temp);



        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();

        //总体情况Sheet填充数据准备
        List<Map<String, String>> overalllistMap = new ArrayList<Map<String, String>>();

        for (int i=0;i<namelist.size();i++){
            Map test = new HashMap();
            test.put("moudlename",namelist.get(i));


            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (allcasenumber.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("allcasenumber", allcasenumber.get(j).get("number"));
                    }
                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("allcasenumber"))
                    {
                        //模块名字不同装入0
                        test.put("allcasenumber",0);
                    }
                }
            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (runnumberlist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("runnumber", runnumberlist.get(j).get("number"));
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("runnumber")){
                        //模块名字不同装入0
                        test.put("runnumber",0);
                    }
                }

            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (runseccessnumberlist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("runseccessnumber", runseccessnumberlist.get(j).get("number"));
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("runseccessnumber"))
                    {
                        //模块名字不同装入0
                        test.put("runseccessnumber",0);
                    }

                }


            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (newbuglist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("newbugnumber", newbuglist.get(j).get("number"));

                    }
                }catch(IndexOutOfBoundsException e){

                }
                finally {
                    if (!test.containsKey("newbugnumber")){
                        test.put("newbugnumber",0);
                    }
                }



            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (allbuglist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("allbugnumber", allbuglist.get(j).get("number"));
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("allbugnumber"))
                    {
                        //模块名字不同装入0
                        test.put("allbugnumber",0);
                    }

                }


            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (solvedbuglist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("solvedbugnumber", solvedbuglist.get(j).get("number"));
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("solvedbugnumber"))
                    {
                        //模块名字不同装入0
                        test.put("solvedbugnumber",0);
                    }

                }

            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (runedcaselist.get(j).get("moudlename").equals(namelist.get(i))){
                        long runednumber= (long) runedcaselist.get(j).get("number");
                        long allnumber=(long)allcasenumber.get(j).get("number");
                        int a = (int)runednumber;
                        int b = (int)allnumber;
                        DecimalFormat df = new DecimalFormat("0.0000");//格式化小数
                        String num = df.format(((float)a/b));//返回的是String类型
                        test.put("runedpercent",num);
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("runedpercent"))
                    {
                        //模块名字不同装入0
                        test.put("runedpercent","0%");
                    }

                }

            }

            overalllistMap.add(test);

        }

        map.put("overalllistMap",overalllistMap);

        //用例Sheet填充数据准备
        List<Map<String, String>> caselistMap = new ArrayList<Map<String, String>>();
        //统计数据
        for (int i=0;i<namelist.size();i++){
            Map test = new HashMap();
            test.put("moudlename",namelist.get(i));
            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (newcaselist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("newcasenumber", newcaselist.get(j).get("number"));
                    }
                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("newcasenumber"))
                    {
                        //模块名字不同装入0
                        test.put("newcasenumber",0);
                    }
                }
            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (runnumberlist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("runnumber", runnumberlist.get(j).get("number"));
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("runnumber")){
                        //模块名字不同装入0
                        test.put("runnumber",0);
                    }
                }

            }

            for (int j=0;j<Math.max(runstatuslist.size(),namelist.size());j++){
                //如果模块名字相同，则放入数据
                try{
                    if (runstatuslist.get(j).get("moudlename").equals(namelist.get(i)) ){
                        if (runstatuslist.get(j).get("lastRunResult").equals("成功")){
                            test.put("runsuccessnumber", runstatuslist.get(j).get("number"));
                        }else if (runstatuslist.get(j).get("lastRunResult").equals("失败"))
                        {
                            test.put("runfailnumber", runstatuslist.get(j).get("number"));
                        }
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("runsuccessnumber"))
                    {
                        //模块名字不同装入0
                        test.put("runsuccessnumber",0);
                    }else if (!test.containsKey("runfailnumber")){
                        test.put("runfailnumber",0);
                    }

                }


            }


            caselistMap.add(test);

        }
        map.put("caselistMap",caselistMap);
        //附录数据
        List<Map<String, String>> caselistsupplementMap = new ArrayList<Map<String, String>>();
        for (int i=0;i<days.size();i++){
            //构造查询参数
            Map temp1=new HashMap();
            temp1.put("projectid",projectid);
            String tempopentime=days.get(i)+" 00:00:00";
            String tempendtime=days.get(i)+" 23:59:59";
            temp1.put("opentime",tempopentime);
            temp1.put("endtime",tempendtime);

            //查询截止当日测试案例总数
            int allcase=tprReportMapper.getweekdayallcasenumber(temp1);
            //查询截止当日测试案例已经执行总数
            int runedcase=tprReportMapper.getweekdayrunedcasenumber(temp1);
            //查询截止当日测试案例已执行成功总数
            int runsuccess=tprReportMapper.getweekdayrunsuccesscasenumber(temp1);
            //查询当日执行测试案例数
            int run=tprReportMapper.getweekdayruncasenumber(temp1);

            float ztzxl=0;
            float zttgl=0;
            if (allcase!=0){
                ztzxl=(float) runedcase/(float) allcase;
                zttgl=(float) runsuccess/(float) allcase;
            }
            Map casetemp=new HashMap();
            casetemp.put("day",days.get(i));
            casetemp.put("ztzxl",ztzxl);
            casetemp.put("zttgl",zttgl);
            casetemp.put("mrzxs",run);
            caselistsupplementMap.add(casetemp);
        }
        map.put("caselistsupplementMap",caselistsupplementMap);

        //缺陷跟踪Sheet填充数据准备
        List<Map<String, String>> bugfollowlistMap = new ArrayList<Map<String, String>>();
        //A-1、缺陷总体情况
        for (int i=0;i<namelist.size();i++){
            Map test = new HashMap();
            test.put("moudlename",namelist.get(i));

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (newbuglist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("newbugnumber", newbuglist.get(j).get("number"));
                    }
                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("newbugnumber"))
                    {
                        //模块名字不同装入0
                        test.put("newbugnumber",0);
                    }
                }



            }


            for (int j=0;j<Math.max(newbugseveritylist.size(),namelist.size());j++){
                //如果模块名字相同，则放入数据
                try{
                    if (newbugseveritylist.get(j).get("moudlename").equals(namelist.get(i)) ){
                        if (newbugseveritylist.get(j).get("severity").equals("致命")){
                            test.put("zmnumber", newbugseveritylist.get(j).get("number"));
                        }else if (newbugseveritylist.get(j).get("severity").equals("严重"))
                        {
                            test.put("yznumber", newbugseveritylist.get(j).get("number"));
                        }else if (newbugseveritylist.get(j).get("severity").equals("一般"))
                        {
                            test.put("ybnumber", newbugseveritylist.get(j).get("number"));
                        }
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("zmnumber"))
                    {
                        //模块名字不同装入0
                        test.put("zmnumber",0);
                    }else if (!test.containsKey("yznumber")){
                        test.put("yznumber",0);
                    }
                    else if (!test.containsKey("ybnumber")){
                        test.put("ybnumber",0);
                    }

                }


            }

            for (int j=0;j<Math.max(newbugstatuslist.size(),namelist.size());j++){
                //如果模块名字相同，则放入数据
                try{
                    if (newbugstatuslist.get(j).get("moudlename").equals(namelist.get(i)) ){
                        if (newbugstatuslist.get(j).get("bugstatus").equals("已解决")){
                            test.put("yjjnumber", newbugstatuslist.get(j).get("number"));
                        }else if (newbugstatuslist.get(j).get("bugstatus").equals("未解决"))
                        {
                            test.put("wjjnumber", newbugstatuslist.get(j).get("number"));
                        }
                        else if (newbugstatuslist.get(j).get("bugstatus").equals("解决中"))
                        {
                            test.put("jjznumber", newbugstatuslist.get(j).get("number"));
                        }
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("yjjnumber"))
                    {
                        //模块名字不同装入0
                        test.put("yjjnumber",0);
                    }else if (!test.containsKey("wjjnumber")){
                        test.put("wjjnumber",0);
                    }
                    else if (!test.containsKey("jjznumber")){
                        test.put("jjznumber",0);
                    }

                }


            }


            bugfollowlistMap.add(test);

        }
        map.put("bugfollowlistMap",bugfollowlistMap);

        //A-2、未解决的致命、严重、一般缺陷分布情况
        List<Map<String, String>> nosolvedbugseveritylistMap = new ArrayList<Map<String, String>>();
        for (int i=0;i<namelist.size();i++){
            Map test = new HashMap();
            test.put("moudlename",namelist.get(i));
            for (int j=0;j<Math.max(nosolvedbugseveritylist.size(),namelist.size());j++){
                try{
                    if (nosolvedbugseveritylist.get(j).get("moudlename").equals(namelist.get(i)) ){
                        if (nosolvedbugseveritylist.get(j).get("severity").equals("致命")){
                            test.put("zmnumber", nosolvedbugseveritylist.get(j).get("number"));
                        }else if (nosolvedbugseveritylist.get(j).get("severity").equals("严重"))
                        {
                            test.put("yznumber", nosolvedbugseveritylist.get(j).get("number"));
                        }
                        else if (nosolvedbugseveritylist.get(j).get("severity").equals("一般"))
                        {
                            test.put("ybnumber", nosolvedbugseveritylist.get(j).get("number"));
                        }
                    }
                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("zmnumber"))
                    {
                        //模块名字不同装入0
                        test.put("zmnumber",0);
                    }else if (!test.containsKey("yznumber")){
                        test.put("yznumber",0);
                    }
                    else if (!test.containsKey("ybnumber")){
                        test.put("ybnumber",0);
                    }
                }
            }
            nosolvedbugseveritylistMap.add(test);
        }
        map.put("nosolvedbugseveritylistMap",nosolvedbugseveritylistMap);

        //附录数据
        List<Map<String, String>> bugfollowlistsupplementlistMap = new ArrayList<Map<String, String>>();
        for (int i=0;i<days.size();i++){
            //构造查询参数
            Map temp1=new HashMap();
            temp1.put("projectid",projectid);
            String tempopentime=days.get(i)+" 00:00:00";
            String tempendtime=days.get(i)+" 23:59:59";
            temp1.put("opentime",tempopentime);
            temp1.put("endtime",tempendtime);
            //获取当日新增数
            int number1=tprReportMapper.getweekdaynewbugnumber(temp1);
            //获取当日解决缺陷数
            int number2=tprReportMapper.getweekdaysolvedbugnumber(temp1);
            //获取当日累计总缺陷数
            int number3=tprReportMapper.getweekdayallbugnumber(temp1);
            //获取累计已解决缺陷数
            int number4=tprReportMapper.getweekdayallsolvedbugnumber(temp1);

            Map bugfollowtemp=new HashMap();
            bugfollowtemp.put("day",days.get(i));
            bugfollowtemp.put("drxz",number1);
            bugfollowtemp.put("drjj",number2);
            bugfollowtemp.put("ljzqx",number3);
            bugfollowtemp.put("ljyjj",number4);
            bugfollowlistsupplementlistMap.add(bugfollowtemp);
        }
        map.put("bugfollowlistsupplementlistMap",bugfollowlistsupplementlistMap);

        // 获取导出excel指定模版，设置多Sheet模式
        TemplateExportParams params = new TemplateExportParams("src/main/resources/static/测试周报模板.xlsx",true);

        // 设置sheetName，若不设置该参数，则使用得原本得sheet名称
        String[] sheetNameArray = {"封面","目录","项目总体情况","测试案例情况","案例执行情况","缺陷跟踪情况","未解决缺陷分布","每日缺陷动态情况"} ;
        params.setSheetName(sheetNameArray);
        // 导出excel
        return ExcelExportUtil.exportExcel(params,map);

    }

    @Override
    public Map getinfotest(Map info) {
        String projectid=(String)info.get("projectid");
        String opentime=(String)info.get("opentime");
        String endtime=(String)info.get("endtime");
        String sqlopentime=opentime+" 00:00:00";
        String sqlendtime=endtime+" 23:59:59";
        SaSession loginuserinfo= StpUtil.getSession(true);
        TprUser loginuser= (TprUser) loginuserinfo.getAttribute("loginuserinfo");
        String  username=loginuser.getName();
        String projectname=tprReportMapper.getprojectnamebyid(projectid);

        //获取开始到结束日期之间所有日期
        List<String> days= getTimesBetweenDates.getDays(opentime,endtime);


        //渲染模板所需数据Map
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectname",projectname);
        map.put("opentime",opentime);
        map.put("endtime",endtime);
        map.put("username",username);

        //mybatis查询参数map
        Map temp=new HashMap();
        temp.put("projectid",projectid);
        temp.put("opentime",sqlopentime);
        temp.put("endtime",sqlendtime);


        //根据项目ID获取所有该项目所有子模块名称
        List<String> namelist=tprReportMapper.getnamelist(projectid);

        //获取各模块总案例数
        List<Map> allcasenumber=tprReportMapper.getweekallcasenumber(temp);

        //获取各模块本周已执行案例数
        List<Map> runnumberlist=tprReportMapper.getweekallbugnumber(temp);

        //获取各模块本周已执行案例成功数
        List<Map> runseccessnumberlist=tprReportMapper.getweekrunseccessnumber(temp);

        //获取各模块本周新增缺陷数
        List<Map> newbuglist=tprReportMapper.getweeknewbugnumber(temp);

        //获取各模块截至目前缺陷总数
        List<Map> allbuglist=tprReportMapper.getweekallbugnumber(temp);

        //获取各模块目前已解决缺陷数
        List<Map> solvedbuglist=tprReportMapper.getweeksolvedbugnumber(temp);

        //获取截止目前已执行的用例数（计算执行进度所用）
        List<Map> runedcaselist=tprReportMapper.getweekrunedcasenumber(temp);
        /////////////////////////////////////////////////////////////////////////分界

        //获取本周测试案例新增数
        List<Map> newcaselist=tprReportMapper.getweeknewcasenumber(temp);

        //取本周各模块案例执行情况
        List<Map> runstatuslist=tprReportMapper.getweekrunstatusnumber(temp);
        ////////////////////////////////////////////////////////////////////////

        //获取本周各模块新增缺陷情况数（致命，严重，一般）
        List<Map> newbugseveritylist=tprReportMapper.getweeknewbugseveritynumber(temp);

        //获取本周各模块缺陷状态（已解决，解决中，未解决）
        List<Map> newbugstatuslist=tprReportMapper.getweeknewbugstatusnumber(temp);

        //获取本周各模块未解决缺陷情况（致命，严重，一般，a-2使用）
        List<Map> nosolvedbugseveritylist=tprReportMapper.getweeknosolvedbugseverity(temp);




        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();

        //总体情况Sheet填充数据准备
        List<Map<String, String>> overalllistMap = new ArrayList<Map<String, String>>();

        for (int i=0;i<namelist.size();i++){
            Map test = new HashMap();
            test.put("moudlename",namelist.get(i));


            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (allcasenumber.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("allcasenumber", allcasenumber.get(j).get("number"));
                    }
                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("allcasenumber"))
                    {
                        //模块名字不同装入0
                        test.put("allcasenumber",0);
                    }
                }
            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (runnumberlist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("runnumber", runnumberlist.get(j).get("number"));
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("runnumber")){
                        //模块名字不同装入0
                        test.put("runnumber",0);
                    }
                }

            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (runseccessnumberlist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("runseccessnumber", runseccessnumberlist.get(j).get("number"));
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("runseccessnumber"))
                    {
                        //模块名字不同装入0
                        test.put("runseccessnumber",0);
                    }

                }


            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (newbuglist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("newbugnumber", newbuglist.get(j).get("number"));

                    }
                }catch(IndexOutOfBoundsException e){

                }
                finally {
                    if (!test.containsKey("newbugnumber")){
                        test.put("newbugnumber",0);
                    }
                }



            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (allbuglist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("allbugnumber", allbuglist.get(j).get("number"));
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("allbugnumber"))
                    {
                        //模块名字不同装入0
                        test.put("allbugnumber",0);
                    }

                }


            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (solvedbuglist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("solvedbugnumber", solvedbuglist.get(j).get("number"));
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("solvedbugnumber"))
                    {
                        //模块名字不同装入0
                        test.put("solvedbugnumber",0);
                    }

                }

            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (runedcaselist.get(j).get("moudlename").equals(namelist.get(i))){
                        long runednumber= (long) runedcaselist.get(j).get("number");
                        long allnumber=(long)allcasenumber.get(j).get("number");
                        int a = (int)runednumber;
                        int b = (int)allnumber;
                        DecimalFormat df = new DecimalFormat("0.0000");//格式化小数
                        String num = df.format(((float)a/b));//返回的是String类型
                        test.put("runedpercent",num);
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("runedpercent"))
                    {
                        //模块名字不同装入0
                        test.put("runedpercent","0%");
                    }

                }

            }

            overalllistMap.add(test);

        }

        map.put("overalllistMap",overalllistMap);

        //用例Sheet填充数据准备
        List<Map<String, String>> caselistMap = new ArrayList<Map<String, String>>();
        //统计数据
        for (int i=0;i<namelist.size();i++){
            Map test = new HashMap();
            test.put("moudlename",namelist.get(i));


            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (newcaselist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("newcasenumber", newcaselist.get(j).get("number"));
                    }
                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("newcasenumber"))
                    {
                        //模块名字不同装入0
                        test.put("newcasenumber",0);
                    }
                }



            }

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (runnumberlist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("runnumber", runnumberlist.get(j).get("number"));
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("runnumber")){
                        //模块名字不同装入0
                        test.put("runnumber",0);
                    }
                }

            }

            for (int j=0;j<Math.max(runstatuslist.size(),namelist.size());j++){
                //如果模块名字相同，则放入数据
                try{
                    if (runstatuslist.get(j).get("moudlename").equals(namelist.get(i)) ){
                        if (runstatuslist.get(j).get("lastRunResult").equals("成功")){
                            test.put("runsuccessnumber", runstatuslist.get(j).get("number"));
                        }else if (runstatuslist.get(j).get("lastRunResult").equals("失败"))
                        {
                            test.put("runfailnumber", runstatuslist.get(j).get("number"));
                        }
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("runsuccessnumber"))
                    {
                        //模块名字不同装入0
                        test.put("runsuccessnumber",0);
                    }else if (!test.containsKey("runfailnumber")){
                        test.put("runfailnumber",0);
                    }

                }


            }


            caselistMap.add(test);

        }
        map.put("caselistMap",caselistMap);
        //附录数据
        List<Map<String, String>> caselistsupplementMap = new ArrayList<Map<String, String>>();
        for (int i=0;i<days.size();i++){
            //构造查询参数
            Map temp1=new HashMap();
            temp1.put("projectid",projectid);
            String tempopentime=days.get(i)+" 00:00:00";
            String tempendtime=days.get(i)+" 23:59:59";
            temp1.put("opentime",tempopentime);
            temp1.put("endtime",tempendtime);

            //查询截止当日测试案例总数
            int allcase=tprReportMapper.getweekdayallcasenumber(temp1);
            //查询截止当日测试案例已经执行总数
            int runedcase=tprReportMapper.getweekdayrunedcasenumber(temp1);
            //查询截止当日测试案例已执行成功总数
            int runsuccess=tprReportMapper.getweekdayrunsuccesscasenumber(temp1);
            //查询当日执行测试案例数
            int run=tprReportMapper.getweekdayruncasenumber(temp1);

            float ztzxl=0;
            float zttgl=0;
            if (allcase!=0){
            ztzxl=(float) runedcase/(float) allcase;
            zttgl=(float) runsuccess/(float) allcase;
            }
            Map casetemp=new HashMap();
            casetemp.put("day",days.get(i));
            casetemp.put("ztzxl",ztzxl);
            casetemp.put("zttgl",zttgl);
            casetemp.put("mrzxs",run);
            caselistsupplementMap.add(casetemp);
        }
        map.put("caselistsupplementMap",caselistsupplementMap);

        //缺陷跟踪Sheet填充数据准备
        List<Map<String, String>> bugfollowlistMap = new ArrayList<Map<String, String>>();
        //A-1、缺陷总体情况
        for (int i=0;i<namelist.size();i++){
            Map test = new HashMap();
            test.put("moudlename",namelist.get(i));

            for (int j=0;j<namelist.size();j++){
                //如果模块名字相同，则放入数据
                try{
                    if (newbuglist.get(j).get("moudlename").equals(namelist.get(i))){
                        test.put("newbugnumber", newbuglist.get(j).get("number"));
                    }
                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("newbugnumber"))
                    {
                        //模块名字不同装入0
                        test.put("newbugnumber",0);
                    }
                }



            }


            for (int j=0;j<Math.max(newbugseveritylist.size(),namelist.size());j++){
                //如果模块名字相同，则放入数据
                try{
                    if (newbugseveritylist.get(j).get("moudlename").equals(namelist.get(i)) ){
                        if (newbugseveritylist.get(j).get("severity").equals("致命")){
                            test.put("zmnumber", newbugseveritylist.get(j).get("number"));
                        }else if (newbugseveritylist.get(j).get("severity").equals("严重"))
                        {
                            test.put("yznumber", newbugseveritylist.get(j).get("number"));
                        }else if (newbugseveritylist.get(j).get("severity").equals("一般"))
                        {
                            test.put("ybnumber", newbugseveritylist.get(j).get("number"));
                        }
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("zmnumber"))
                    {
                        //模块名字不同装入0
                        test.put("zmnumber",0);
                    }else if (!test.containsKey("yznumber")){
                        test.put("yznumber",0);
                    }
                    else if (!test.containsKey("ybnumber")){
                        test.put("ybnumber",0);
                    }

                }


            }

            for (int j=0;j<Math.max(newbugstatuslist.size(),namelist.size());j++){
                //如果模块名字相同，则放入数据
                try{
                    if (newbugstatuslist.get(j).get("moudlename").equals(namelist.get(i)) ){
                        if (newbugstatuslist.get(j).get("bugstatus").equals("已解决")){
                            test.put("yjjnumber", newbugstatuslist.get(j).get("number"));
                        }else if (newbugstatuslist.get(j).get("bugstatus").equals("未解决"))
                        {
                            test.put("wjjnumber", newbugstatuslist.get(j).get("number"));
                        }
                        else if (newbugstatuslist.get(j).get("bugstatus").equals("解决中"))
                        {
                            test.put("jjznumber", newbugstatuslist.get(j).get("number"));
                        }
                    }

                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("yjjnumber"))
                    {
                        //模块名字不同装入0
                        test.put("yjjnumber",0);
                    }else if (!test.containsKey("wjjnumber")){
                        test.put("wjjnumber",0);
                    }
                    else if (!test.containsKey("jjznumber")){
                        test.put("jjznumber",0);
                    }

                }


            }


            bugfollowlistMap.add(test);

        }
        map.put("bugfollowlistMap",bugfollowlistMap);

        //A-2、未解决的致命、严重、一般缺陷分布情况
        List<Map<String, String>> nosolvedbugseveritylistMap = new ArrayList<Map<String, String>>();
        for (int i=0;i<namelist.size();i++){
            Map test = new HashMap();
            test.put("moudlename",namelist.get(i));
            for (int j=0;j<Math.max(nosolvedbugseveritylist.size(),namelist.size());j++){
                try{
                    if (nosolvedbugseveritylist.get(j).get("moudlename").equals(namelist.get(i)) ){
                        if (nosolvedbugseveritylist.get(j).get("severity").equals("致命")){
                            test.put("zmnumber", nosolvedbugseveritylist.get(j).get("number"));
                        }else if (nosolvedbugseveritylist.get(j).get("severity").equals("严重"))
                        {
                            test.put("yznumber", nosolvedbugseveritylist.get(j).get("number"));
                        }
                        else if (nosolvedbugseveritylist.get(j).get("severity").equals("一般"))
                        {
                            test.put("ybnumber", nosolvedbugseveritylist.get(j).get("number"));
                        }
                    }
                } catch (IndexOutOfBoundsException e) {

                }finally {
                    if (!test.containsKey("zmnumber"))
                    {
                        //模块名字不同装入0
                        test.put("zmnumber",0);
                    }else if (!test.containsKey("yznumber")){
                        test.put("yznumber",0);
                    }
                    else if (!test.containsKey("ybnumber")){
                        test.put("ybnumber",0);
                    }
                }
            }
            nosolvedbugseveritylistMap.add(test);
        }
        map.put("nosolvedbugseveritylistMap",nosolvedbugseveritylistMap);

        //附录数据
        List<Map<String, String>> bugfollowlistsupplementlistMap = new ArrayList<Map<String, String>>();
        for (int i=0;i<days.size();i++){
            //构造查询参数
            Map temp1=new HashMap();
            temp1.put("projectid",projectid);
            String tempopentime=days.get(i)+" 00:00:00";
            String tempendtime=days.get(i)+" 23:59:59";
            temp1.put("opentime",tempopentime);
            temp1.put("endtime",tempendtime);
            //获取当日新增数
            int number1=tprReportMapper.getweekdaynewbugnumber(temp1);
            //获取当日解决缺陷数
            int number2=tprReportMapper.getweekdaysolvedbugnumber(temp1);
            //获取当日累计总缺陷数
            int number3=tprReportMapper.getweekdayallbugnumber(temp1);
            //获取累计已解决缺陷数
            int number4=tprReportMapper.getweekdayallsolvedbugnumber(temp1);

            Map bugfollowtemp=new HashMap();
            bugfollowtemp.put("day",days.get(i));
            bugfollowtemp.put("drxz",number1);
            bugfollowtemp.put("drjj",number2);
            bugfollowtemp.put("ljzqx",number3);
            bugfollowtemp.put("ljyjj",number4);
            bugfollowlistsupplementlistMap.add(bugfollowtemp);
        }
        map.put("bugfollowlistsupplementlistMap",bugfollowlistsupplementlistMap);



        HashMap<String,Object> testmap=new HashMap();
        testmap.put("overalllistMap",overalllistMap);
        testmap.put("caselistsupplementMap",caselistsupplementMap);
        testmap.put("nosolvedbugseveritylistMap",nosolvedbugseveritylistMap);
        testmap.put("bugfollowlistsupplementlistMap",bugfollowlistsupplementlistMap);
        testmap.put("bugfollowlistMap",bugfollowlistMap);


        return testmap;
    }

}
