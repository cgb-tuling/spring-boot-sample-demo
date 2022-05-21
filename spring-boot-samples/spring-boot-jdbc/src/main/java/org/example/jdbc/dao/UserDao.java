package org.example.jdbc.dao;


import org.example.jdbc.model.User;

import java.util.List;

/**
 * Description: UserRepository 接口操作User对象
 * User: yuanct
 * Date: 2019/1/9 2:26 PM
 */
public interface UserDao {

    List<User> findByName(String name);
}
