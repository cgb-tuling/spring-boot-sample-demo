package org.example.dao;

import org.apache.ibatis.annotations.Insert;
import org.example.entity.User;

public interface UserMapper {

    @Insert("insert into user (user_name, password) values (#{username}, #{password})")
    Integer insertUser(User user);
}
