package com.zyz.demo.mapper;


import com.zyz.demo.entity.TprUser;
import com.zyz.demo.pojo.Tpruserinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TprUserMapper {

    TprUser loginCheck(String username);
    Integer userisexit(String username);
    List<Tpruserinfo> findall();
    List<Tpruserinfo> findbyinput(Map input);
    int updateuserinfo(Map info);
    int deletebyid(String id);
    int deleteByIds(List<String> ids);
    TprUser findbyname(String name);
    TprUser findbyid(int userId);

    int userRegister(Map userinfo);

    Map getPermissionList(String userid);




}
