package com.cloud.user.login;

import com.cloud.user.base.AjaxResult;
import com.cloud.user.base.User;

public interface UserApi {

 public AjaxResult login(Integer id,String password);
 public AjaxResult test(User user);
}
