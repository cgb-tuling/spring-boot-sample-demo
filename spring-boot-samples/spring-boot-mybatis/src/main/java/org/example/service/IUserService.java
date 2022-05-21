package org.example.service;

import org.example.entity.User;

import java.util.List;

public interface IUserService {

    List<User> findUsers();

    User findUserById(Long id);

    Long insertUer(User user);

}
