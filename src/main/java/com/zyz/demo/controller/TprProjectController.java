package com.zyz.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.zyz.demo.pojo.Tpruserinfo;
import com.zyz.demo.pojo.moudleinfo;
import com.zyz.demo.service.TprProjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TprProjectController {

    @Resource
    private TprProjectService tprProjectService;

    @RequestMapping(value = "/project/getmoudleinfo" , method = RequestMethod.POST)
    public JSONObject getmoudleinfo(){
        JSONObject data=new JSONObject();
        HashMap map = tprProjectService.getmoudleinfo();

        data.put("dataList",map.get("treelist"));
        data.put("dataCount",map.get("size"));
        return data;
    }
    @RequestMapping(value = "/project/addmoudle" , method = RequestMethod.POST)
    public JSONObject addmoudle(@RequestParam Map infomap){
        System.out.println("初始infomap"+infomap);
        JSONObject data=new JSONObject();
        int flag = tprProjectService.addmoudle(infomap);
        data.put("flag",flag);
        return data;
    }
    @RequestMapping(value = "/project/deletebymoudleid" , method = RequestMethod.POST)
    public JSONObject deletebymoudleid(@RequestParam(value = "moudleid",required = false) String moudleid){
        JSONObject data=new JSONObject();
        int flag = tprProjectService.deletemoudle(moudleid);
        data.put("flag",flag);
        return data;
    }

    @RequestMapping(value = "/project/updatemoudle" , method = RequestMethod.POST)
    public JSONObject updatemoudle(@RequestParam Map infomap){
        JSONObject data=new JSONObject();
        int flag = tprProjectService.updatemoudle(infomap);
        data.put("flag",flag);
        return data;
    }
    @RequestMapping(value = "/project/findBymoudlename" , method = RequestMethod.POST)
    public JSONObject findBymoudlename(@RequestParam(value = "moudlenname",required = false) String moudlenname){
        JSONObject data=new JSONObject();
        HashMap map = tprProjectService.findBymoudlename(moudlenname);
        data.put("dataList",map.get("treelist"));
        data.put("dataCount",map.get("size"));
        return data;
    }

}
