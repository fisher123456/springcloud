package com.cloud.role.impl;

import com.cloud.role.RoleApi;
import com.cloud.role.base.AjaxResult;
import com.cloud.role.base.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RequestMapping(value = "/role")
@RestController
public class RoleController  implements RoleApi {

    @Override
    @RequestMapping(value = "/get/{id}")
    public AjaxResult findRoleByUserId(@PathVariable(name="id")Long id, HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String s = parameterNames.nextElement();
            String parameter = request.getParameter(s);
            System.out.println(parameter);
        }
        return AjaxResult.success(id);
    }

    @RequestMapping(value = "test",produces = "application/json;charset=UTF-8")
    public AjaxResult get(@RequestBody User user){
        return AjaxResult.success(user);
    }
}
