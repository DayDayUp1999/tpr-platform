package com.zyz.demo.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.zyz.demo.service.TprCaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class IndexController {
    @Resource
    private TprCaseService tprCaseService;

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
        sta.put("userCount",tprCaseService.getuserCount());
        sta.put("allcaseCount",tprCaseService.getallcaseCount());
        sta.put("allbugCount",tprCaseService.getallbugrCount());
        sta.put("solvedbugCount",tprCaseService.getsolvedbugCount());
        return sta;
    }



}
