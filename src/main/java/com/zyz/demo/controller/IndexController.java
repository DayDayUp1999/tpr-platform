package com.zyz.demo.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.zyz.demo.service.TprChartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Controller
public class IndexController {
    @Resource
    private TprChartService tprChartService;

    @RequestMapping("/")
    public String gologin(){
        if ( StpUtil.isLogin()){
            return "/index.html";
        }
        return "login";
    }

    @RequestMapping("/getsta")
    @ResponseBody
    public Map getsta(){
        Map sta=new HashMap();
        sta.put("userCount",tprChartService.getuserCount());
        sta.put("allcaseCount",tprChartService.getallcaseCount());
        sta.put("allbugCount",tprChartService.getallbugrCount());
        sta.put("solvedbugCount",tprChartService.getsolvedbugCount());
        return sta;
    }



}
