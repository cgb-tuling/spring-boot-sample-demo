package org.example.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.enums.SexEnum;
import org.example.enums.StatusEnum;

@Data
@Accessors(chain = true)
public class TestDO {
    private Integer id;
    private String username;
    private SexEnum sex;
//    @JsonDeserialize(using = JacksonStatusEnumDeserializer.class)
//    @JsonSerialize(using = JacksonStatusEnumSerializer.class)
    private StatusEnum status;

}
