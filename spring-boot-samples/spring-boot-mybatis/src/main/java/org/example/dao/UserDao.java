package org.example.dao;


import org.example.entity.User;

import java.util.List;

public interface UserDao {

    List<User> findUsers();

    User findUserById(Long id);

    Long insertUer(User user);

}
