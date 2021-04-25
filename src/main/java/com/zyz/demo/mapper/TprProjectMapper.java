package com.zyz.demo.mapper;


import com.zyz.demo.pojo.moudleinfo;

import java.util.List;
import java.util.Map;

public interface TprProjectMapper {
    List<moudleinfo> getmoudleinfo();
    int addmoudle(Map map);
    int deletemoudle(String moudleid);
    int updatemoudle(Map infomap);
    List<moudleinfo> findtypeBymoudlename(String moudlename);

    List<String> findBymoudlenameid(String moudlename);

    moudleinfo getsingleproject(String moudleid);


}
