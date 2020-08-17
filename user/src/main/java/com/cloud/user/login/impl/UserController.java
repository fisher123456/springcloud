package com.cloud.user.login.impl;

import com.cloud.user.base.AjaxResult;
import com.cloud.user.base.User;
import com.cloud.user.login.UserApi;
import com.cloud.user.remote.RoleApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;


@RestController
@RequestMapping("/user")
public class UserController implements UserApi {
    @Autowired
    private RoleApi roleApi;


    @Override
    @RequestMapping("/login")
    public AjaxResult login(Integer id, String password) {
        AjaxResult ajaxResult = roleApi.get(id);
        return AjaxResult.success(ajaxResult.get("data"));
    }

    @RequestMapping("/test")
    public AjaxResult test(User user){
       return roleApi.test(user);
    }
}
