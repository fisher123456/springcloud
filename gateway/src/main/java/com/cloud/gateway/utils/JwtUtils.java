package com.cloud.gateway.utils;


import com.cloud.gateway.constants.Constants;
import com.cloud.gateway.exception.BusinessException;
import com.cloud.gateway.model.User;
import io.jsonwebtoken.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtUtils {

    private static String signKey;

    @Value("${org.my.jwt.signKey}")
    public void setData(String secret) {
        signKey = secret;
    }

    //解析token 到user
    public static User getUserFromToken(String jwtToke) {
        if(StringUtils.isBlank(jwtToke) || !jwtToke.startsWith(Constants.AUTHORIZATION_TOKEN_PREFIX)){
            throw new BusinessException("jwt解析异常");
        }
        jwtToke = jwtToke.substring(Constants.AUTHORIZATION_TOKEN_PREFIX.length()).trim();
        JwtParser jwtParser = Jwts.parser().setSigningKey(signKey.getBytes());
        Jws<Claims> jwt = jwtParser.parseClaimsJws(jwtToke);
        Claims subject = jwt.getBody();
        User user = new User();
        user.setUserName(subject.get("user_name", String.class));
        user.setMobile(subject.get("mobile", String.class));
        user.setEmail(subject.get("email", String.class));
        user.setUserOid(UUID.fromString(subject.get("oid", String.class)));
        return user;
    }
}
