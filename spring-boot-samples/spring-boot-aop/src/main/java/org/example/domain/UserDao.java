package org.example.domain;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author yct
 */
public interface UserDao {
    @Select(" select id,name from t_user where id=#{id} ")
    User find(@Param("id") String id);
}