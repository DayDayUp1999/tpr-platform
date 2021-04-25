package com.zyz.demo.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.zyz.demo.entity.TprUser;
import com.zyz.demo.mapper.TprUserMapper;
import com.zyz.demo.pojo.Tpruserinfo;
import com.zyz.demo.service.TprUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TprUserServiceImpl implements TprUserService {

    @Resource
    private TprUserMapper tprUserMapper;

    @Override
    public Map loginCheck( Map<String, String> userinfo) {
        String flag="nouser";
        Integer count=tprUserMapper.userisexit(userinfo.get("username"));
        Map map=new HashMap();
        if (count==0){
            map.put("flag",flag);
            return map;
        }else {
            TprUser user = tprUserMapper.loginCheck(userinfo.get("username"));
            if (user.getUserstatus()==2){
                map.put("flag","suoding");
                return map;
            }
            if (user.getPassword().equals(userinfo.get("password"))) {
                map.put("userinfo",user);
                map.put("flag","pass");
                StpUtil.setLoginId(user.getUserId());
                SaSession usersession=StpUtil.getSession(true);
                usersession.setAttribute("loginuserinfo",user);
            } else {
                map.put("flag","error");
            }
        }
        return map;
    }

    @Override
    public List<Tpruserinfo> findall() {
        List<Tpruserinfo> userlist=tprUserMapper.findall();
        return userlist;
    }

    @Override
    public int updateuserinfo(HashMap info) {
        if(!info.isEmpty()){
            try{
                int flag=tprUserMapper.updateuserinfo(info);
            } catch (Exception e) {
                throw new RuntimeException("参数为空");
            }finally {
            }

        }
        int flag = tprUserMapper.updateuserinfo(info);
        return flag;
    }

    @Override
    public List<Tpruserinfo> findByinput(HashMap info) {
        if(!info.isEmpty()){
            try{
                List<Tpruserinfo> list=tprUserMapper.findbyinput(info);
            } catch (Exception e) {
                throw new RuntimeException("参数为空");
            }finally {
            }

        }
        List<Tpruserinfo> list=tprUserMapper.findbyinput(info);
        return list;
    }

    @Override
    public int deletebyid(String id) {
        if(!id.isEmpty()){
            try{
                int flag=tprUserMapper.deletebyid(id);
            } catch (Exception e) {
                throw new RuntimeException("参数为空");
            }finally {
            }

        }
        int flag=tprUserMapper.deletebyid(id);
        return flag;
    }

    @Override
    public int deleteByIds(List<String> ids) {
        if(!ids.isEmpty()){
            try{
                int flag=tprUserMapper.deleteByIds(ids);
            } catch (Exception e) {
                throw new RuntimeException("参数为空");
            }finally {
            }
        }
        int flag=tprUserMapper.deleteByIds(ids);
        return flag;
    }

    @Override
    public TprUser findbyid(int userId) {
        TprUser user=tprUserMapper.findbyid(userId);
        return user;
    }

    @Override
    public String userRegister(Map userinfo) {
        if(!userinfo.isEmpty()){
            try{
                Integer count=tprUserMapper.userisexit((String) userinfo.get("username"));
            } catch (Exception e) {
                throw new RuntimeException("参数为空");
            }finally {
            }
        }
        String flag="success";
        Integer count=tprUserMapper.userisexit((String) userinfo.get("username"));
        if (count!=0){
            flag="Alreadyexists";
            return flag;
        }
        tprUserMapper.userRegister(userinfo);

        return flag;
    }

    @Override
    public int updateloginuser(HashMap info) {
        if(!info.isEmpty()){
            try{
                int flag=tprUserMapper.updateuserinfo(info);
            } catch (Exception e) {
                throw new RuntimeException("参数为空");
            }finally {
            }
        }

        int flag=tprUserMapper.updateuserinfo(info);
        return 0;
    }


}
