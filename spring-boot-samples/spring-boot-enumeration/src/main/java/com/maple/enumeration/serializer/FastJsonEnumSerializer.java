package com.maple.enumeration.serializer;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * fast json反序列化器
 *
 * @author maple
 * @version 1.0
 * @since 2020-06-23 02:07
 */
@Slf4j
public class FastJsonEnumSerializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        Method[] methods = ReflectUtil.getMethods(object.getClass(), item -> StrUtil.startWith(item.getName(), "get"));
        Map<String, Object> objectMap = new HashMap<>(methods.length);
        for (Method method : methods) {
            String name = StrUtil.subAfter(method.getName(), "get", false);
            // 首字母小写
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
            objectMap.put(name, ReflectUtil.invoke(object, method));
        }
        serializer.write(objectMap);
    }
}
