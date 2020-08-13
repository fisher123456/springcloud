package com.cloud.gateway.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;



@Data
public class User implements Serializable {
    /**
     * 用户oid
     */
    private UUID userOid;

    /**
     * 用户邮箱
     */
    private String email;
    /**
    /**
     * 用户手机号
     */
    private String mobile;
    /**
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 员工工号
     */
    private String staffCode;

}
