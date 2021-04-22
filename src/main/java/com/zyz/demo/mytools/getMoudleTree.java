package com.zyz.demo.mytools;

import com.zyz.demo.pojo.moudleinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class getMoudleTree {

    public static List getDepartmentTree(List list) {
        Map<String,List<moudleinfo>> groupMap = (Map<String, List<moudleinfo>>) list.stream().collect(Collectors.groupingBy(moudleinfo::getType));
        List<moudleinfo> projectList = (List) groupMap.get("project");
        List<moudleinfo> moudleList = (List)groupMap.get("moudle");
        if (projectList != null && !projectList.isEmpty()) {
            if (moudleList != null && !moudleList.isEmpty()) {
                Map<String,List<moudleinfo>> projectGroupMap = (Map) moudleList.stream().collect(Collectors.groupingBy(moudleinfo::getParentid));
                for (moudleinfo moudle : projectList) {
                    moudle.setChildren((List<moudleinfo>) projectGroupMap.get(moudle.getMoudleid()+""));
                }
            }
            return projectList;
        }
        return new ArrayList<>();
    }

}
