package org.example.service;

import org.example.domain.UserDO;

import java.util.List;


public interface UserService {

    List<UserDO> list();
    UserDO get(Integer id);
    UserDO save(UserDO user);
    UserDO update(UserDO user);
    void delete(Integer id);

}