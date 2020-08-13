package com.cloud.gateway.filter;

import com.cloud.gateway.context.ContextHolder;
import com.cloud.gateway.exception.BusinessException;
import com.cloud.gateway.model.ReturnData;
import com.cloud.gateway.model.User;
import com.cloud.gateway.utils.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 描述: JwtToken 过滤器
 */
@Component
@ConfigurationProperties("org.my.jwt")
@Slf4j
@Data
public class JwtTokenFilter implements GlobalFilter, Ordered {

    private String signKey;

    private String[] skipAuthUrls;

    @Autowired
    private ObjectMapper objectMapper ;

    /**
     * 过滤器
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String uri = request.getURI().getPath();
        //是否是忽略认证地址
        if (isSkipUri(uri)) {
            try{
            return chain.filter(exchange);}
            catch ( Exception e){
                e.printStackTrace();
            }
        } else {
            //获取token
            String token = request.getHeaders().getFirst("Authorization");
            ServerHttpResponse resp = exchange.getResponse();
            if (StringUtils.isBlank(token)) {
                return authErro(resp, "认证失败");
            } else {
                try {
                    //校验用户token，并解析token成用户放到headers中
                    return this.getVoidMono(exchange, chain, token);
                } catch (ExpiredJwtException e) {
                    log.error(e.getMessage(), e);
                    if (e.getMessage().contains("Allowed clock skew")) {
                        return authErro(resp, "认证过期");
                    } else {
                        return authErro(resp, "认证失败");
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    return authErro(resp, "认证失败");
                }
            }
        }

        return null;
    }

    private Mono<Void> getVoidMono(ServerWebExchange exchange, GatewayFilterChain chain, String token) {
        //eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhcmF5MSIsInNjb3BlIjpbIkFMTCJdLCJtb2JpbGUiOiIxODkwODM0MDMxOCIsInN0YWZmX3JvbGUiOm51bGwsIm9pZCI6ImQ1MjhmMmYzLWQ4NjAtNDMyMC1hMTM0LWE5MDI3ODdiMGE4NyIsImV4cCI6MTU3ODA2MzA1MCwic3RhZmZfbm8iOm51bGwsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI5NWJlMTNjNC03NTdlLTQ1YTItYjcwYi02MmViMWZmMGU2NTkiLCJlbWFpbCI6bnVsbCwiY2xpZW50X2lkIjoiM2E4ZjZlZTU0ODQ4NDdkYjhiZmNkN2U0MDQxNzJlYzAifQ.R-PKTNzA5EUHv_ZjJ-TRrdkSB9HRURskg3HXFKp_ok0
        log.info("获取到的token====={}",token);
        User user = JwtUtils.getUserFromToken(token);
        ContextHolder.getContext().setUser(user);
        ServerWebExchange build = exchange;
        try {
            //向headers中增加用户信息
            ServerHttpRequest host = exchange.getRequest().mutate().header("user", objectMapper.writeValueAsString(user)).build();
            //将现在的request 变成 change对象
            build = exchange.mutate().request(host).build();
        } catch (JsonProcessingException e) {
            throw new BusinessException("解析user出错");
        }
        return chain.filter(build);
    }

    //是否匹配配置的不需要验证的uri,如果是true,说明需要放行
    private boolean isSkipUri(String uri) {
        AntPathMatcher matcher = new AntPathMatcher();
        boolean skipUri = false;
        for (String skipAuthUrl : skipAuthUrls) {
            skipUri = matcher.match(skipAuthUrl, uri);
            if (skipUri) {
                skipUri = true;
                break;
            }
        }
        return skipUri;
    }

    /**
     * 认证错误输出
     *
     * @param resp 响应对象
     * @param mess 错误信息
     * @return
     */
    private Mono<Void> authErro(ServerHttpResponse resp, String mess) {
//        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        ReturnData<String> returnData = new ReturnData<>();
        returnData.setCode(HttpStatus.UNAUTHORIZED.value());
        returnData.setMass(mess);
        String returnStr = "";
        try {
            returnStr = objectMapper.writeValueAsString(returnData);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        DataBuffer buffer = resp.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return -100;
    }


}
