package com.bwie.hm0422.config;

import com.bwie.hm0422.model.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <FileName> LoginInterceptor
 * <Author>   lqz
 * <Time>  2020/4/25 15:25
 * <Desc> 拦截器
 **/
@Controller
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session中的用户
        TUser user = (TUser) request.getSession().getAttribute("user");
        if(null==user){
            //为空则没有登录,返回登录页面
            response.sendRedirect("/user/toL");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
