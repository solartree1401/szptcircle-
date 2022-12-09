package com.zhang.szptcircle.service;

import com.zhang.szptcircle.mapper.UserMapper;
import com.zhang.szptcircle.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.UUID;

@Service
public class UserService implements UserMapper{
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String user) {
        return userMapper.findByUsername(user);
    }

    public User findUserById(BigInteger userId) {
        return userMapper.findUserById(userId);
    }

    @Override
    public int register(String id,String username,String password) {
        int res;
        return res =userMapper.register(id,username,password);
    }
}
