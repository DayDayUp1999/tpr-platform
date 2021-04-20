package com.zyz.demo.config;

import com.zyz.demo.entity.TprUser;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;


@Component
public class LoginInterceptor implements HandlerInterceptor {
    //请求处理之前调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {
        SaSession loginusersession=StpUtil.getSession();
        System.out.println("111111>>>>>>>"+loginusersession.get("loginuserinfo"));
        TprUser loginuserinfo= (TprUser) loginusersession.get("loginuserinfo");

        if(null ==loginuserinfo ){
            response.sendRedirect("/");
            return false;
        }else {
            return true;
        }
    }

}

