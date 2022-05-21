package org.example.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.enums.SexEnum;
import org.example.enums.StatusEnum;
import org.example.serialize.FastJsonEnumDeserializer;

@Data
@Accessors(chain = true)
public class TestDO {
    private Integer id;
    private String username;
    private SexEnum sex;
//    @JSONField(deserializeUsing = FastJsonEnumDeserializer.class)
    private StatusEnum status;
}
