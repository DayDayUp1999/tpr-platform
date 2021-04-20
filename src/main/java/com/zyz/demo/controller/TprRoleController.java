package com.zyz.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.zyz.demo.entity.TprRole;
import com.zyz.demo.service.TprRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TprRoleController {

    @Autowired
    private TprRoleService tprRoleService;

    @RequestMapping(value = "/role/getTprRoleList" , method = RequestMethod.POST)
    public JSONObject userList(){
        JSONObject data=new JSONObject();
        List<TprRole> rolelist = tprRoleService.findall();
        int listcount=rolelist.size();
        data.put("dataList",rolelist);
        data.put("dataCount",listcount);
        System.out.println(rolelist);
        return data;
    }
    @RequestMapping(value = "/role/addRole" , method = RequestMethod.POST)
    public JSONObject addRole(@RequestParam(value = "roleId",required = false) String roleId,
                              @RequestParam(value = "rolename",required = false) String name,
                              @RequestParam(value = "info",required = false) String info
    ){
        JSONObject data=new JSONObject();
        int flag = tprRoleService.addRole(roleId,name,info);

        data.put("flag",flag);
        return data;
    }

    @RequestMapping(value = "/role/delRole" , method = RequestMethod.POST)
    public JSONObject delRole(@RequestParam(value = "roleId",required = false) String roleId){
        JSONObject data=new JSONObject();
        int flag = tprRoleService.delRole(roleId);
        data.put("flag",flag);
        return data;
    }

    @RequestMapping(value = "/role/findByname" , method = RequestMethod.POST)
    public JSONObject findByname(@RequestParam(value = "name",required = false) String rolename){
        JSONObject data=new JSONObject();
        List<TprRole> rolelist = tprRoleService.findByname(rolename);
        data.put("dataList",rolelist);
        data.put("dataCount",rolelist.size());
        return data;
    }

    @RequestMapping(value = "/menu/checkmenulistpermission" , method = RequestMethod.POST)
    public JSONObject checkmenulistpermission(){
        JSONObject data=new JSONObject();
        data.put("test1",1);
        return data;
    }
}
