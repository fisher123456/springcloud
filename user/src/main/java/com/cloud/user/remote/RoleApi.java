package com.cloud.user.remote;


import com.cloud.user.base.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "role-service",path = "/role")
public interface RoleApi {

    @GetMapping("/get")
    AjaxResult get(@RequestBody Long id);
}
