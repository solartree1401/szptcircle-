package com.zhang.szptcircle.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhang.szptcircle.annotation.UserLoginToken;
import com.zhang.szptcircle.config.GlobalParam;
import com.zhang.szptcircle.pojo.APPUser;
import com.zhang.szptcircle.pojo.User;
import com.zhang.szptcircle.service.TokenService;
import com.zhang.szptcircle.service.UserService;
import com.zhang.szptcircle.util.TokenUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.UUID;

@RestController
public class UserApi {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    // 登录
    @ApiOperation(value = "登陆", notes = "登陆")
    @PostMapping("szptcircle/login")
    public Object login(@RequestBody APPUser appUser, HttpServletResponse response) {
        //先判断用户名是否存在
        JSONObject jsonObject = new JSONObject();
        if(userService.findByUsername(appUser.getUsername())==null)
        {
            jsonObject.put("code",401);
            jsonObject.put("message", "登录失败,用户名或密码错误");
        }
        else {
            User userForBase = new User();
            userForBase.setId(userService.findByUsername(appUser.getUsername()).getId());
            userForBase.setUsername(userService.findByUsername(appUser.getUsername()).getUsername());
            userForBase.setPassword(userService.findByUsername(appUser.getUsername()).getPassword());
            if (!(userForBase.getPassword().equals(appUser.getPassword()))) {
                jsonObject.put("code",401);
                jsonObject.put("message", "登录失败,用户名或密码错误");
            } else {
                String token = tokenService.getToken(userForBase);
                jsonObject.put("msg","success");
                jsonObject.put("code",0);
                jsonObject.put("expire", GlobalParam.TokenExpireTime);
                jsonObject.put("token", token);

                Cookie cookie = new Cookie("token", token);
                cookie.setPath("/");
                response.addCookie(cookie);

            }
        }

        return jsonObject;


    }

    // 注册
    @ApiOperation(value = "注册", notes = "注册")
    @PostMapping("szptcircle/register")
    public String  register(@RequestBody APPUser appUser, HttpServletResponse response)
    {

        String id = UUID.randomUUID().toString().replaceAll("-", "");
        if(userService.findByUsername(appUser.getUsername())!=null)
        {
            return "重复用户名";
        }
        //判断是否用户重复
        int res = userService.register(id,appUser.getUsername(),appUser.getPassword());
        if(res==1)
        {
            return  "注册成功，返回登录";
        }else
        {
            return  "注册失败，重新注册";
        }



    }



    @UserLoginToken
    @ApiOperation(value = "获取信息", notes = "获取信息")
    @RequestMapping(value = "/getMessage" ,method = RequestMethod.POST)
    public String getMessage() {

        // 取出token中带的用户id 进行操作
        System.out.println(TokenUtil.getTokenUserName());

        return "您已通过验证";
    }

    @ApiOperation(value = "测试",notes = "测试")
    @GetMapping("/hello/{username}/{password}")
    public int hello(@PathVariable String username, @PathVariable String password)
    {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return userService.register(uuid,username,password);
    }

}
