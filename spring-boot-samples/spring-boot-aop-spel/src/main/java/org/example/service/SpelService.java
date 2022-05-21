package org.example.service;

import org.example.annotation.StockWarnCollect;
import org.example.domain.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author yct
 */
@Service
public class SpelService {
    /**
     * 对象取值可以选择  #source.getUsername() 或者 #source.username
     */
    @StockWarnCollect(customerId = "#customerId",username="#source.username", source = "#source", pageType = "2")
    public Map<String, Object> validateCarts(Long customerId, Set<Long> userSelectedIds, User source){
        // 省略
        return new HashMap<>();
    }
}
