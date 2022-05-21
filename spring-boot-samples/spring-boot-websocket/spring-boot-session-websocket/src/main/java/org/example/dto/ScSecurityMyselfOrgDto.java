package org.example.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.example.enums.ScSecurityOrganizationTypeEnum;
import org.example.enums.ScSecurityOrganizationTypeEnumConvert;
import org.example.serializer.SeSecurityOrganizationTypeEnumDeSerializer;
import org.example.serializer.SeSecurityOrganizationTypeEnumSerializer;


import javax.persistence.Convert;
import java.io.Serializable;

/**
 * 用于Myself服务中OrgDto
 */
@Data
public class ScSecurityMyselfOrgDto implements Serializable {

    private static final long serialVersionUID = -2560175602283664706L;

    private String id;

    private String parentId;

    private String organizationId;

    private String organizationName;

    @Convert(converter = ScSecurityOrganizationTypeEnumConvert.class)
    @JsonSerialize(using = SeSecurityOrganizationTypeEnumSerializer.class)
    @JsonDeserialize(using = SeSecurityOrganizationTypeEnumDeSerializer.class)
    private ScSecurityOrganizationTypeEnum organizationType;
}
