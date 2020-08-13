package com.cloud.role;

import com.cloud.role.base.AjaxResult;
import org.springframework.web.bind.annotation.PathVariable;

public interface RoleApi {
    public AjaxResult findRoleByUserId(Long id);
}
