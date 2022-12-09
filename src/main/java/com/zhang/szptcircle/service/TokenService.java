package com.zhang.szptcircle.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zhang.szptcircle.config.GlobalParam;
import com.zhang.szptcircle.pojo.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    public String getToken(User user)
    {
        Date start = new Date();
        //token有效期一小时
        long currentTime = System.currentTimeMillis() + GlobalParam.TokenExpireTime;
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(user.getUsername()).withIssuedAt(start).withExpiresAt(end).sign(Algorithm.HMAC256(user.getPassword()));
        return token;

    }
}
