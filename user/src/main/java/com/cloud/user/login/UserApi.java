package com.cloud.user.login;

import com.cloud.user.base.AjaxResult;

public interface UserApi {

 public AjaxResult login(String username,String password);
}
