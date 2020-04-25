package com.bwie.hm0422.service;

import cn.hutool.crypto.SecureUtil;
import com.bwie.hm0422.dao.TUserMapper;
import com.bwie.hm0422.model.TUser;
import com.bwie.hm0422.model.TUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <FileName> UserService
 * <Author>   lqz
 * <Time>  2020/4/22 19:41
 * <Desc> 业务逻辑层
 **/
@Service
public class UserService {
    @Autowired
    TUserMapper tUserMapper;

    public int register(TUser tUser) {
        return tUserMapper.insert(tUser);
    }

    public String checkPhone(String phone) {

        TUserExample tUserExample = new TUserExample();
        tUserExample.createCriteria().andPhoneEqualTo(phone);
        List<TUser> tUsers = tUserMapper.selectByExample(tUserExample);
        if(null!=tUsers && tUsers.size()>0){
            return "yes";
        }else{
            return "no";
        }
    }

    public TUser checkLogin(TUser tUser) {
        TUserExample tUserExample = new TUserExample();
        String md5Password = SecureUtil.md5(tUser.getPwd());

        tUserExample.createCriteria().andUserNameEqualTo(tUser.getUserName()).andPwdEqualTo(md5Password);
        List<TUser> tUsers = tUserMapper.selectByExample(tUserExample);
        if(null!=tUsers && tUsers.size()>0){
            return  tUsers.get(0);
        }
        return null;
    }
}
