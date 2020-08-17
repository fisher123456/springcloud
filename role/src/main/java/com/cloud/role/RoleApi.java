package com.cloud.role;

import com.cloud.role.base.AjaxResult;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

public interface RoleApi {
    public AjaxResult findRoleByUserId(Long id, HttpServletRequest request);
}
