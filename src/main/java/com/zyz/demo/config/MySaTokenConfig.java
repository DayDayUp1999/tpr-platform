package com.zyz.demo.config;


import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouterUtil;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class MySaTokenConfig implements WebMvcConfigurer {
    // 注册sa-token的拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册路由拦截器，自定义验证规则
        registry.addInterceptor(new SaRouteInterceptor((request, response, handler) -> {
            // 登录验证 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
            SaRouterUtil.match(Arrays.asList("/**"), Arrays.asList("/user/doLogin", "/","/sa-resources/**","/login.html","/favicon.ico","/static/**","/user/userRegister","/user/getRegisterTprRoleList","/userRegister.html"), () -> StpUtil.checkLogin());
            // 权限认证 -- 不同模块, 校验不同权限

            SaRouterUtil.match(Arrays.asList("/user/**"), Arrays.asList("/user/doLogin","/user/userRegister","/user/getRegisterTprRoleList","/user/checkadduserpermission","/user/getloginuserinfo","/user/loginuserupdate"), () -> StpUtil.checkPermission("4-1"));

            SaRouterUtil.match("/user/checkadduserpermission",   () -> StpUtil.checkPermission("4-2"));

            SaRouterUtil.match("/project/**",   () -> StpUtil.checkPermission("5-1"));

            SaRouterUtil.match("/power/**",  () -> StpUtil.checkPermission("3-1"));

            SaRouterUtil.match("/menu/checkmenulistpermission",  () -> StpUtil.checkPermission("3-2"));

            SaRouterUtil.match(Arrays.asList("/role/**"), Arrays.asList("/role/getTprRoleList","/role/getupdateroleoption"), () -> StpUtil.checkPermission("3-1"));


            SaRouterUtil.match("/role/getTprRoleList",  () -> StpUtil.checkPermissionOr("3-1","4-1"));

            //BUG统计页面
            SaRouterUtil.match(Arrays.asList("/bug/getbuglistByname"), () -> StpUtil.checkPermission("2-1"));
            //已解决bug页面
            SaRouterUtil.match(Arrays.asList("/bug/getbugsolvedlist"), () -> StpUtil.checkPermission("2-2"));

            SaRouterUtil.match("/bug/getbuglist",  () -> StpUtil.checkPermissionOr("2-1","2-2"));

            //缺陷跟踪
            SaRouterUtil.match(Arrays.asList("/bug/bugfollow","/bug/bugfollowlj"), () -> StpUtil.checkPermission("2-5"));

            //用例执行结果统计页面
            SaRouterUtil.match(Arrays.asList("/case/getcaserunlist","/case/getcaserunresultlistByname"), () -> StpUtil.checkPermission("2-3"));
            //用例统计页面
            SaRouterUtil.match(Arrays.asList("/case/findallcaseByproject","/case/getcaselistByname"), () -> StpUtil.checkPermission("2-4"));

            //报表页面
            SaRouterUtil.match(Arrays.asList("/report/getprojectlist"), () -> StpUtil.checkPermissionOr("1-1","1-2"));
            SaRouterUtil.match(Arrays.asList("/report/getdayreport"), () -> StpUtil.checkPermission("1-1"));
            SaRouterUtil.match(Arrays.asList("/report/getweekreport"), () -> StpUtil.checkPermission("1-2"));
            SaRouterUtil.match(Arrays.asList("/report/checkdayreportpermission"), () -> StpUtil.checkPermission("1-1"));
            SaRouterUtil.match(Arrays.asList("/report/checkweekreportpermission"), () -> StpUtil.checkPermission("1-2"));



        })).addPathPatterns("/**");
    }
}

