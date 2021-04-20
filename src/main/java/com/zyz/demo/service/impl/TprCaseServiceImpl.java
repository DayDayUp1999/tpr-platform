package com.zyz.demo.service.impl;


import com.zyz.demo.mapper.TprCaseMapper;
import com.zyz.demo.pojo.caseinfo;
import com.zyz.demo.pojo.moudleinfo;
import com.zyz.demo.service.TprCaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TprCaseServiceImpl implements TprCaseService {

    @Resource
    private TprCaseMapper tprCaseMapper;

    @Override
    public List<Map> getcaselist() {
        List<Map> list=tprCaseMapper.getcaselist();
        return list;
    }

    @Override
    public List<Map> getcaselistByname(String projectname) {
        List<Map> list=tprCaseMapper.getcaselistByname(projectname);
        List<String> names=new ArrayList<>();
        //获取所有模块名字
        for (Map item : list){
            names.add((String)item.get("moudlename"));
        }
        //去重
        List<String> realnames=names.stream().distinct().collect(Collectors.toList());
        List<Map> test=new ArrayList<>();
        //重新装入
        for(int i = 0; i<realnames.size(); i++){
          Map temp=new HashMap();
          temp.put("moudlename",realnames.get(i));
          for (int j=0;j<list.size();j++){
              if(list.get(j).get("moudlename").equals(realnames.get(i))){
                  if (list.get(j).get("casestatus").equals("已执行")){
                      temp.put("run",list.get(j).get("number"));
                  }else
                  temp.put("norun",list.get(j).get("number"));
              }
          }
          test.add(temp);
      }
        return test;
    }

    @Override
    public List<Map> getcaserunlist() {
        List<Map> list=tprCaseMapper.getcaserunlist();
        return list;
    }

    @Override
    public List<Map> getcaserunresultlistByname(String projectname) {
        List<Map> list=tprCaseMapper.getcaserunresultlistByname(projectname);
        List<String> names=new ArrayList<>();
        //获取所有模块名字
        for (Map item : list){
            names.add((String)item.get("moudlename"));
        }
        //去重
        List<String> realnames=names.stream().distinct().collect(Collectors.toList());
        List<Map> test=new ArrayList<>();
        //重新装入
        for(int i = 0; i<realnames.size(); i++){
            Map temp=new HashMap();
            temp.put("moudlename",realnames.get(i));
            for (int j=0;j<list.size();j++){
                if(list.get(j).get("moudlename").equals(realnames.get(i))){
                    if (list.get(j).get("lastRunResult").equals("成功")){
                        temp.put("success",list.get(j).get("number"));
                    }else
                        temp.put("fail",list.get(j).get("number"));
                }
            }
            test.add(temp);
        }
        return test;
    }
}
