package com.bwie.hm0422.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.bwie.hm0422.model.TUser;
import com.bwie.hm0422.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;


/**
 * <FileName> UserController
 * <Author>   lqz
 * <Time>  2020/4/22 19:40
 * <Desc> 控制层
 **/
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 去注册界面
     * @return
     */
    @RequestMapping("/toRegister")
    public String  toRegister(){
        return "register";
    }


    /**
     * 注册功能
     * @return
     */
    @RequestMapping("/registerUser")
    public String registerToUser(TUser tUser){

            //设置主键的id
            tUser.setuId(RandomUtil.randomUUID().replace("-",""));
            //添加创建时间
            tUser.setCreateTime(new Date());
            //密码md5 加密
            String md5Password = SecureUtil.md5(tUser.getPwd());
            tUser.setPwd(md5Password);

            int flag = userService.register(tUser);
             return flag>0?"login":"register"; //三目判断
    }

    /**
     * 校验手机号
     * @param phone
     * @return
     */
    @RequestMapping("/checkPhone")
    @ResponseBody
    public String checkPhone(String phone){

        return userService.checkPhone(phone);

    }


    /**
     * 登录
     * @param tUser
     * @param request
     * @return
     */
    @RequestMapping("/checkLogin")
    public ModelAndView checkUserNameAndPassword(TUser tUser, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        if(!StringUtils.isEmpty(tUser.getUserName()) && !StringUtils.isEmpty(tUser.getPwd())){
            TUser user =  userService.checkLogin(tUser);
            if (null==user){ //用户名或者密码错误
                modelAndView.addObject("msg","用户名或者密码错误");
                modelAndView.setViewName("login");
            }else{
                //放入session
                request.getSession().setAttribute("user",user);
                modelAndView.addObject("msg"," ");
                modelAndView.setViewName("redirect:/book/queryBookList");
            }
        }else{
            modelAndView.addObject("msg","用户名密码不能为空");
            //如果用户名或者密码为空，则返回到登录页面
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    /**
     * 去登陆界面
     * @return
     */
    @RequestMapping("/toL")
    public String toL(){
        return "login";
    }

    @RequestMapping("/removeSession")
    public String removeSession(HttpSession session){
        //清除session
        session.removeAttribute("user");
        //返回登录界面
        return "redirect:/user/toL";
    }
}
