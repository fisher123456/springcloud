package com.cloud.user.remote;


import com.cloud.user.base.AjaxResult;
import com.cloud.user.base.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "role-service",path = "/role")
public interface RoleApi {

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    AjaxResult get(@PathVariable(name = "id") Integer id);
    @RequestMapping(value = "test",method = RequestMethod.GET)
    AjaxResult test(User user);
}
