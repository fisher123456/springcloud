package com.cloud.role.impl;

import com.cloud.role.RoleApi;
import com.cloud.role.base.AjaxResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/role",produces ={ MediaType.APPLICATION_JSON_UTF8_VALUE})
@RestController
public class RoleController  implements RoleApi {

    @Override
    @RequestMapping("/get")
    public AjaxResult findRoleByUserId(Long id) {
        return AjaxResult.success(id);
    }
}
