package com.zhang.szptcircle.mapper;

import com.zhang.szptcircle.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;

@Mapper
public interface UserMapper {
    User findByUsername(String user);
    User findUserById(BigInteger id);
    int register(String id,String username,String password);
}
