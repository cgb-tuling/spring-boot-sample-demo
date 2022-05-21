package org.example.entity;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.example.converter.WebMessageConvert;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author admin
 * @date 2021-05-31
 * @description 历史消息记录(已删除的)
 */
@Data
@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "WebMessageHistory")
@JsonIgnoreProperties(value = {"created", "creator", "lastUpdater", "updated", "id"})
@JSONType(ignores = {"created", "creator", "lastUpdater", "updated", "id"})
public class ScMsaWebMessageHistory{

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

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Convert(converter = WebMessageConvert.class)
    private ScMsaWebMessage message;
}
