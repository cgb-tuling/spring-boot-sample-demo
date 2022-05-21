package org.example.service.impl;

/**
 * @author yct
 */

import org.example.dao.UserDao;
import org.example.entity.User;
import org.example.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDefaultServiceImpl implements IUserService {

    @Resource
    private UserDao userDao;

    @Override
    public List<User> findUsers() {
        return userDao.findUsers();
    }

    @Override
    public User findUserById(Long id) {
        return userDao.findUserById(id);
    }

    @Override
    public Long insertUer(User user) {
        return userDao.insertUer(user);
    }
}

