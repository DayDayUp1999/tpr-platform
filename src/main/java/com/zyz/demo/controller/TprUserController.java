package com.zyz.demo.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;

import com.zyz.demo.entity.TprRole;
import com.zyz.demo.entity.TprUser;
import com.zyz.demo.mytools.EncryptUtil;
import com.zyz.demo.pojo.Tpruserinfo;
import com.zyz.demo.service.TprRoleService;
import com.zyz.demo.service.TprUserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TprUserController {


    @Autowired
    private TprUserService tprUserService;

    @Autowired
    private TprRoleService tprRoleService;

    @RequestMapping(value = "/user/doLogin" , method = RequestMethod.POST)
    public String dologin(@RequestParam(value = "username",required = false) String username,
                       @RequestParam(value = "password",required = false) String password, HttpSession session
    ){
        Map<String, String> logininfo=new HashMap<String, String>();
        logininfo.put("username",username);
        logininfo.put("password", EncryptUtil.md5(password));
        Map map=tprUserService.loginCheck(logininfo);

        session.setAttribute("userinfo",map.get("userinfo"));

        return (String)map.get("flag");
    }

    @RequestMapping(value = "/user/getTprUserList" , method = RequestMethod.POST)
    public JSONObject userList(){
        StpUtil.checkLogin();
        JSONObject data=new JSONObject();
        List<Tpruserinfo> userlist = tprUserService.findall();
        int listcount=userlist.size();
        data.put("dataList",userlist);
        data.put("dataCount",listcount);
        return data;
    }

    @RequestMapping(value = "/user/findByinput" , method = RequestMethod.POST)
    public JSONObject findByinput(
            @RequestParam(value = "username",required = false) String username,
            @RequestParam(value = "rolename",required = false) String rolename
    ){
        JSONObject data=new JSONObject();
        HashMap info=new HashMap();
        info.put("username",username);
        info.put("rolename",rolename);
        List<Tpruserinfo> userlist = tprUserService.findByinput(info);
        int listcount=userlist.size();
        data.put("dataList",userlist);
        data.put("dataCount",listcount);
        return data;
    }

    @RequestMapping(value = "/user/updateuserinfo" , method = RequestMethod.POST)
    public JSONObject updateuserinfo(@RequestParam HashMap info){
        JSONObject data=new JSONObject();
        int flag  = tprUserService.updateuserinfo(info);
        data.put("flag",flag);
        return data;
    }
    @RequestMapping(value = "/user/deletebyid" , method = RequestMethod.POST)
    public JSONObject deletebyid(@RequestParam(value = "userId") String userid){
        JSONObject data=new JSONObject();
        int flag  = tprUserService.deletebyid(userid);
        data.put("flag",flag);
        return data;
    }

    @RequestMapping(value = "/user/deleteByIds" , method = RequestMethod.POST)
    public JSONObject deleteByIds(@RequestParam(value = "ids") List<String> ids){
        JSONObject data=new JSONObject();
        int flag  = tprUserService.deleteByIds(ids);
        System.out.println("ids:"+ids);
        data.put("flag",flag);
        return data;
    }

    @RequestMapping(value = "/user/logout" , method = RequestMethod.POST)
    public JSONObject logout(@RequestParam(value = "username",required = false) String username){
        JSONObject data=new JSONObject();
        StpUtil.logout();
        data.put("flag","flag");
        return data;
    }

    @RequestMapping(value = "/user/getRegisterTprRoleList" , method = RequestMethod.POST)
    public JSONObject getRegisterTprRoleList(){
        JSONObject data=new JSONObject();
        List<TprRole> rolelist = tprRoleService.findall();
        if (rolelist.size()>0){
            for (int i=0;i<rolelist.size();i++){
                if (rolelist.get(i).getRoleId().equals("1")){
                    rolelist.remove(i);
                }
            }
        }
        data.put("dataList",rolelist);
        return data;
    }

    @RequestMapping(value = "/user/userRegister" , method = RequestMethod.POST)
    public JSONObject userRegister(@RequestParam(value = "username",required = false) String username,
                                   @RequestParam(value = "password",required = false) String password,
                                   @RequestParam(value = "name",required = false) String name,
                                   @RequestParam(value = "number",required = false) String number,
                                   @RequestParam(value = "sex",required = false) String sex,
                                   @RequestParam(value = "age",required = false) String age,
                                   @RequestParam(value = "roleId",required = false) String roleId,
                                   @RequestParam(value = "remarks",required = false) String remarks,
                                   @RequestParam(value = "userstatus",required = false) String userstatus
                                   ){
        JSONObject data=new JSONObject();
        Map userinfo=new HashMap();
        userinfo.put("username",username);
        userinfo.put("password",password);
        userinfo.put("name",name);
        userinfo.put("number",number);
        userinfo.put("sex",sex);
        userinfo.put("age",age);
        userinfo.put("roleId",roleId);
        userinfo.put("remarks",remarks);
        userinfo.put("userstatus",userstatus);

        System.out.println("controller层userinfo"+userinfo);
        userinfo.put("password",EncryptUtil.md5(password));
        System.out.println("controller层转换后userinfo"+userinfo);
        String flag=tprUserService.userRegister(userinfo);
        data.put("flag",flag);

        return data;
    }

    @RequestMapping(value = "/user/checkadduserpermission" , method = RequestMethod.POST)
    public JSONObject checkadduserpermission(){
        JSONObject data=new JSONObject();
        List<TprRole> rolelist = tprRoleService.findall();
        data.put("flag","true");
        return data;
    }

    @RequestMapping(value = "/user/getloginuserinfo" , method = RequestMethod.POST)
    public JSONObject getloginuserinfo(){
        JSONObject data=new JSONObject();
        SaSession loginuserinfo=StpUtil.getSession(true);
        TprUser loginuser= (TprUser) loginuserinfo.getAttribute("loginuserinfo");
        int id = Integer.parseInt(loginuser.getUserId());
        TprUser user=tprUserService.findbyid(id);
        data.put("loginuser",user);
        return data;
    }

    @RequestMapping(value = "/role/getupdateroleoption" , method = RequestMethod.POST)
    public JSONObject getupdateroleoption(){
        JSONObject data=new JSONObject();
        List<TprRole> rolelist = tprRoleService.findall();
        data.put("dataList",rolelist);
        return data;
    }

    @RequestMapping(value = "/user/loginuserupdate" , method = RequestMethod.POST)
    public JSONObject loginuserupdate(@RequestParam(value = "password",required = false) String password,
                                      @RequestParam(value = "number",required = false) String number,
                                      @RequestParam(value = "age",required = false) String age,
                                      @RequestParam(value = "remarks",required = false) String remarks
    ){
        JSONObject data=new JSONObject();
        SaSession loginuserinfo=StpUtil.getSession(true);
        TprUser loginuser= (TprUser) loginuserinfo.getAttribute("loginuserinfo");
        int id = Integer.parseInt(loginuser.getUserId());
        TprUser user=tprUserService.findbyid(id);

       HashMap loginuserupdateinfo=new HashMap();
       loginuserupdateinfo.put("password",EncryptUtil.md5(password));
       loginuserupdateinfo.put("number",number);
       loginuserupdateinfo.put("age",age);
       loginuserupdateinfo.put("remarks",remarks);
       loginuserupdateinfo.put("userId",loginuser.getUserId());
       tprUserService.updateuserinfo(loginuserupdateinfo);
       if (!(EncryptUtil.md5(password).equals(user.getPassword()))){
           data.put("flag","passwordupdated");
           StpUtil.logout();
       }else{
           data.put("flag","success");
       }
        return data;
    }
}
