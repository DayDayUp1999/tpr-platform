package com.zyz.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.zyz.demo.entity.TprRole;
import com.zyz.demo.service.TprPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
public class TprPowerController {

    @Resource
    private TprPowerService tprPowerService;


    @RequestMapping(value = "/power/updateRolepowerByRoleId" , method = RequestMethod.POST)
    public JSONObject update(@RequestParam(value = "powerString",required = false) String powerString,
                             @RequestParam(value = "roleId",required = false) String roleId){
        JSONObject data=new JSONObject();
        int flag = tprPowerService.updateRolepowerByRoleId(roleId,powerString);
        data.put("msg",flag);
        System.out.println("updateRolepowerByRoleIdflag:"+flag);
        return data;
    }

    @RequestMapping(value = "/power/findCheckedPowerList" , method = RequestMethod.POST)
    public JSONObject findCheckedPowerList(@RequestParam(value = "roleId",required = false) String roleId){
        JSONObject data=new JSONObject();
        List<String> powerList = tprPowerService.findCheckedPowerList(roleId);
        data.put("datalist",powerList);
        return data;
    }

}
