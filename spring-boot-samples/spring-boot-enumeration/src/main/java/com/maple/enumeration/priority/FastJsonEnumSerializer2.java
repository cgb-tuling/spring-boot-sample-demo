package com.maple.enumeration.priority;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * fast json 优先级测试序列化器
 *
 * @author maple
 * @version 1.0
 * @since 2020-06-22 00:25
 */
@Slf4j
public class FastJsonEnumSerializer2 implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        serializer.write("优先2");
    }
}
