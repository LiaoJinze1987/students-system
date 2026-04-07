package com.liao.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JWTUtils {

    private final static String KEY = "liaoStudent1987";

    //生成 token 返回
    public static String genToken(Map<String, Object> claims) {
        //为了测试方便，把过期时间调长一点，一天86400000毫秒
        Date expiresDate = new Date(System.currentTimeMillis() + 86400*1000*7);
        //生成jwt token
        return JWT.create().
                withClaim("claims", claims)//添加载荷
                .withExpiresAt(expiresDate)//添加过期时间
                .sign(Algorithm.HMAC256(KEY));//指定加密算法，配置密钥
    }

    //验证 token，返回数据
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

}
