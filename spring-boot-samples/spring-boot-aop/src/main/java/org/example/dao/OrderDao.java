package org.example.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.domain.Order;

import java.util.List;

/**
 * @author yct
 */
public interface OrderDao {
    @Select("select * from t_order where customerId=#{customerId}")
    List<Order> query(@Param("customerId") String customerId);
}