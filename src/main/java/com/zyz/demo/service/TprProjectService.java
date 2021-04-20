package com.zyz.demo.service;

import com.zyz.demo.pojo.moudleinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TprProjectService {

    public HashMap getmoudleinfo();
    public int addmoudle(Map infomap);
    public int deletemoudle(String moudleid);
    public int updatemoudle(Map map);
    public HashMap findBymoudlename(String moudlename);
}
