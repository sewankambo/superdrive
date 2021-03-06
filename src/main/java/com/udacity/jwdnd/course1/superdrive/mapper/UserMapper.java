package com.udacity.jwdnd.course1.superdrive.mapper;

import com.udacity.jwdnd.course1.superdrive.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE username = #{username}")
    User getUser(String username);
    
    @Insert("INSERT INTO users(username, salt, password, firstname, lastname) " +
            "VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int addUser(User user);
}
