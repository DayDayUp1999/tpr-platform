package com.zyz.demo.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.zyz.demo.TprPlatformApplication;
import com.zyz.demo.entity.Datasource;
import com.zyz.demo.mytools.PropUtil;
import com.zyz.demo.service.TprChartService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Controller
public class IndexController {
    @Resource
    private TprChartService tprChartService;
    @Resource
    Datasource datasource;

    @RequestMapping("/")
    public String gologin(){
        if ( StpUtil.isLogin()){
            return "/index.html";
        }
        return "login";
    }

    @RequestMapping("/getsta")
    @ResponseBody
    public Map getsta(){
        Map sta=new HashMap();
        sta.put("userCount",tprChartService.getuserCount());
        sta.put("allcaseCount",tprChartService.getallcaseCount());
        sta.put("allbugCount",tprChartService.getallbugrCount());
        sta.put("solvedbugCount",tprChartService.getsolvedbugCount());
        return sta;
    }

    @RequestMapping("/testgetdatasource")
    @ResponseBody
    public Map testgetdatasource(@RequestParam("add") String add , @RequestParam("dkh") String dkh,@RequestParam("dbname") String dbname,@RequestParam("name")String name,@RequestParam("pass") String pass){
        Map flag=new HashMap();
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://"+ add+":"+ dkh+ "/"+dbname+"?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true";
        // ????????????????????????????????????????????????????????????
        String USER = name;
        String PASS = pass;
        // MySQL 8.0 ???????????? - JDBC ????????????????????? URL
        Connection conn = null;
        try {
            // ?????? JDBC ??????
            Class.forName(JDBC_DRIVER);
            // ????????????
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.close();
            flag.put("flag","sucess");
            return flag;
        } catch (SQLException se) {
            // ?????? JDBC ??????
            se.printStackTrace();
            flag.put("flag","fail");
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            // ?????? Class.forName ??????
            flag.put("flag","fail");
            return flag;
        } finally {
            // ????????????
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    @ResponseBody
    @RequestMapping("/getdatasource")
    public Map getdatasource(@RequestParam("add") String add , @RequestParam("dkh") String dkh,@RequestParam("dbname") String dbname,@RequestParam("name")String name,@RequestParam("pass") String pass){
        Map flag=new HashMap();
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://"+ add+":"+ dkh+ "/"+dbname+"?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true";
        // ???????????????????????????????????????????????????
        String USER = name;
        String PASS = pass;

        try {
            //????????????properti?????????
            PropUtil instance = PropUtil.getProp("application.properties");
            //???????????????????????????
            instance.setProperty("spring.datasource.url",DB_URL);
            instance.setProperty("spring.datasource.password",PASS);
            instance.setProperty("spring.datasource.username",USER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExecutorService threadPool = new ThreadPoolExecutor(1, 1, 0,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.DiscardOldestPolicy());
        threadPool.execute(() -> {
            TprPlatformApplication.context.close();
            TprPlatformApplication.context = SpringApplication.run(TprPlatformApplication.class,
                    TprPlatformApplication.args);
        });
        threadPool.shutdown();



        flag.put("flag","sucess");


        return flag;
    }



}
