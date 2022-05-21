package org.example.h2.dao;

import org.example.h2.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Description: UserRepository 接口操作User对象
 * User: yuanct
 * Date: 2019/1/9 2:26 PM
 */
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByName(String name);
}
