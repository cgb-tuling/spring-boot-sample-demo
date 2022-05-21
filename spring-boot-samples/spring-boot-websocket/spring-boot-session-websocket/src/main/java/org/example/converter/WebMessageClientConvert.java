package org.example.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.example.entity.Client;
import org.springframework.util.CollectionUtils;

import javax.persistence.AttributeConverter;
import java.util.HashSet;
import java.util.Set;

/**
 * @author admin
 * @date 2020-11-23
 * @description Client实体字段转换器,存入时按String格式，取出时为Client
 */
public class WebMessageClientConvert implements AttributeConverter<Set<Client>, String> {

    @Override
    public String convertToDatabaseColumn(Set<Client> attribute) {
        if(CollectionUtils.isEmpty(attribute)){
            return "";
        }
        return JSON.toJSONString(attribute);
    }

    @Override
    public Set<Client> convertToEntityAttribute(String dbData) {
        JSONArray jsonArray = JSON.parseArray(dbData);
        if(jsonArray == null){
            return null;
        }
        return new HashSet(jsonArray.toJavaList(Client.class));
    }
}
