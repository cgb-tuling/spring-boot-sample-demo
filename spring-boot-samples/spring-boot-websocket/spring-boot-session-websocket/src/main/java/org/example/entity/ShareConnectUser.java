package org.example.entity;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author admin
 * @date 2021-06-18
 * @description 共享的连接用户池
 */
//@ApiModel(value = "共享的连接用户池")
@Accessors(chain = true)//chain 若为true，则setter方法返回当前对象
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "WebShareConnectUser",
        uniqueConstraints = {@UniqueConstraint(name = "SCUUniqueConstraint", columnNames = {"session_id", "tenant_id", "account_id"})})
@JsonIgnoreProperties(ignoreUnknown = true,value = {"creator", "lastUpdater","version"})
@JSONType(ignores = {"creator", "lastUpdater","version"})
public class ShareConnectUser implements Serializable {

    private static final long serialVersionUID = -1786729556131631136L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    protected String id;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant created = Instant.now();

    /**
     * 修改时间
     */
    @LastModifiedDate
    @Column
    private Instant updated = Instant.now();

    @NotEmpty
    @Column(nullable = false)
    private String userId;

    @NotEmpty
    @Column(nullable = false)
    private String realName;

    @NotEmpty
    @Column(nullable = false)
    private String ip;

    @NotEmpty
    @Column(nullable = false)
    private String port;

    @NotEmpty
    @Column(nullable = false,name = "session_id")
    private String sessionId;
}
