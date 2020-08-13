package com.cloud.gateway.context;


import com.cloud.gateway.model.User;

/**
 */
public class Context {
    private String jwtHeader;
    private User user;
//    private Collection<GrantedResource> privileges;
    /**
     * idempotentIdInHeader should not be passed as HTTP Header to other Requests
     */
    private String idempotentIdInHeader;

    /**
     * Set by {@link }
     *
     * @return
     */
    public String getJwtHeader() {
        return jwtHeader;
    }

    public void setJwtHeader(String jwtHeader) {
        this.jwtHeader = jwtHeader;
    }

    /**
     * Set by {@link }
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
//
//    public Collection<GrantedResource> getPrivileges() {
//        return privileges;
//    }
//
//    public void setPrivileges(Collection<GrantedResource> privileges) {
//        this.privileges = privileges;
//    }

//    /**
//     * Set by {@link IdempotentHeaderFilter}
//     *
//     * @return
//     */
//    public String getIdempotentIdInHeader() {
//        return idempotentIdInHeader;
//    }
//
//    public void setIdempotentIdInHeader(String idempotentIdInHeader) {
//        this.idempotentIdInHeader = idempotentIdInHeader;
//    }
}
