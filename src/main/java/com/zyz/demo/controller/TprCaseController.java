package com.zyz.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.zyz.demo.pojo.caseinfo;
import com.zyz.demo.service.TprCaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class TprCaseController {

    @Resource
    private TprCaseService tprCaseService;

    //获取所有项目和项目用例总数
    @RequestMapping(value = "/case/findallcaseByproject" , method = RequestMethod.POST)
    public JSONObject findallcase(){
        JSONObject data=new JSONObject();
        List<Map> caselist=tprCaseService.getcaselist();
        data.put("caselist",caselist);
        return data;
    }

    //获取单个项目的模块和模块用例总数
    @RequestMapping(value = "/case/getcaselistByname" , method = RequestMethod.POST)
    public JSONObject getcaselistByname(@RequestParam(value = "projectname") String projectname){
        JSONObject data=new JSONObject();
        List<Map> caselist=tprCaseService.getcaselistByname(projectname);
        data.put("moudleinfolist",caselist);
        return data;
    }

    //所有执行过的案例
    @RequestMapping(value = "/case/getcaserunlist" , method = RequestMethod.POST)
    public JSONObject getcaserunlist(){
        JSONObject data=new JSONObject();
        List<Map> caselist=tprCaseService.getcaserunlist();
        data.put("caserunlist",caselist);
        return data;
    }

    //获得单个项目不同模块成功失败案例执行结果（成功/失败）
    @RequestMapping(value = "/case/getcaserunresultlistByname" , method = RequestMethod.POST)
    public JSONObject getcaserunresultlistByname(@RequestParam(value = "projectname") String projectname){
        JSONObject data=new JSONObject();
        List<Map> caselist=tprCaseService.getcaserunresultlistByname(projectname);
        data.put("moudleinfolist",caselist);
        return data;
    }

}
