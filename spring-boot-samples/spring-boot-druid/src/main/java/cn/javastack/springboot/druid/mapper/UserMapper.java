package cn.javastack.springboot.druid.mapper;

import cn.javastack.springboot.druid.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

/**
 * @author itmuch.com
 */
@Mapper
public interface UserMapper {
    @Select("select * from user where id = #{id}")
    Optional<User> selectById(Long id);


    @Select("select * from user")
    List<User> selectList();
}
