package org.example.validation.model;

import lombok.experimental.Delegate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 集合校验
 * 如果请求体直接传递了json数组给后台，并希望对数组中的每一项都进行参数校验。
 * 此时，如果我们直接使用java.util.Collection下的list或者set来接收数据，
 * 参数校验并不会生效！我们可以使用自定义list集合来接收参数：
 * 包装List类型，并声明@Valid注解
 *
 * @author yuancetian
 */
public class ValidationList<E> implements List<E> {
  
    @Delegate
    @Valid
    public List<E> list = new ArrayList<>();
  
    @Override  
    public String toString() {  
        return list.toString();  
    }  
}