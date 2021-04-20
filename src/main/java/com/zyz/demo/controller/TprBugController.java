package com.zyz.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.zyz.demo.service.TprBugService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
public class TprBugController {

    @Resource
    private TprBugService tprBugService;

    @RequestMapping(value = "/bug/getbuglist" , method = RequestMethod.POST)
    public JSONObject getbuglist() {
        JSONObject data = new JSONObject();
        List<Map> buglist = tprBugService.getbuglist();
        data.put("buglist", buglist);
        return data;
    }

    @RequestMapping(value = "/bug/getbuglistByname" , method = RequestMethod.POST)
    public JSONObject getbuglistByname(@RequestParam(value = "projectname") String projectname) {
        JSONObject data = new JSONObject();
        List<Map> buglist = tprBugService.getbuglistByname(projectname);
        data.put("buginfolist", buglist);
        return data;
    }

    @RequestMapping(value = "/bug/getbugsolvedlist" , method = RequestMethod.POST)
    public JSONObject getbugsolvedlist(@RequestParam(value = "projectname") String projectname) {
        JSONObject data = new JSONObject();
        List<Map> solvedbuglist = tprBugService.getbugsolvedlist(projectname);
        data.put("solvedbuglist", solvedbuglist);
        return data;
    }

    @RequestMapping(value = "/bug/bugfollow" , method = RequestMethod.POST)
    public JSONObject bugfollow() {
        JSONObject data = new JSONObject();
        List<Map> bugfollow = tprBugService.bugfollow();
        data.put("bugfollowlist", bugfollow);
        return data;
    }

    @RequestMapping(value = "/bug/bugfollowlj" , method = RequestMethod.POST)
    public JSONObject bugfollowlj() throws ParseException {
        JSONObject data = new JSONObject();
        List<Map> bugfollowlj = tprBugService.bugfollowlj();
        data.put("bugfollowljlist", bugfollowlj);
        return data;
    }


}
