package com.maple.enumeration.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maple.enumeration.enums.FastJsonStatusEnum;
import com.maple.enumeration.enums.StatusEnum;
import com.maple.enumeration.serializer.FastJsonEnumDeserializer;
import com.maple.enumeration.serializer.JacksonEnumDeserializer;
import com.maple.enumeration.serializer.JacksonEnumSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 测试表 注释部分为mybatis-plus版本
 * </p>
 *
 * @author maple
 * @since 2020-06-21
 */
//@Data
//@Accessors(chain = true)
//@TableName("test")
//public class TestDO {
//
//    private Integer id;
//
//    private String username;
//
//    private StatusEnum status;
//}

@Data
@Accessors(chain = true)
public class TestDO {

    private Integer id;

    private String username;

    @JsonDeserialize(using = JacksonEnumDeserializer.class)
    @JsonSerialize(using = JacksonEnumSerializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private StatusEnum status;

    //        @JSONField(deserializeUsing = FastJsonEnumDeserializer.class)
//    @JSONField(serializeUsing = FastJsonEnumSerializer.class)
    private FastJsonStatusEnum fastJsonStatusEnum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public FastJsonStatusEnum getFastJsonStatusEnum() {
        return fastJsonStatusEnum;
    }

    public void setFastJsonStatusEnum(FastJsonStatusEnum fastJsonStatusEnum) {
        this.fastJsonStatusEnum = fastJsonStatusEnum;
    }
}
