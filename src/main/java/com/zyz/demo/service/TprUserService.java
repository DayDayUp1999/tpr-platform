package com.zyz.demo.service;

import com.zyz.demo.entity.TprUser;
import com.zyz.demo.pojo.Tpruserinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface TprUserService {

    public Map loginCheck(Map<String, String> userinfo);


    public List<Tpruserinfo> findall();
    public int updateuserinfo(HashMap info);
    public List<Tpruserinfo> findByinput(HashMap info);
    public int deletebyid(String id);
    public int deleteByIds(List<String> ids);

    public TprUser findbyid(int userId);

    public String userRegister(Map userinfo);

    public int updateloginuser(HashMap info);


}
