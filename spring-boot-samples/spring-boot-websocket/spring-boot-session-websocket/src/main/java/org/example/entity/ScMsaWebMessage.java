package org.example.entity;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.example.converter.MessageStateConvert;
import org.example.enums.WebMessageCategory;
import org.example.enums.WebMessageState;
import org.example.exception.WebMessageException;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * @author admin
 * @date 2021-05-25
 * @description
 */
//@ApiModel(value = "消息实体")
@Accessors(chain = true)//chain 若为true，则setter方法返回当前对象
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WebMessage")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"creator", "lastUpdater", "version"})
@JSONType(ignores = {"creator", "lastUpdater", "version"})
public class ScMsaWebMessage {

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

    //消息来源者工号
    @NotEmpty
    @Column(nullable = false)
    private String sourceUserId;

    //消息来源者姓名
    @NotEmpty
    @Column(nullable = false)
    private String sourceUserName;

    //消息接收者
    @NotEmpty
    @Column(nullable = false)
    private String targetUserId;

    //来源系统名称
    @NotEmpty
    @Column(nullable = false)
    private String sourceSystemName;

    //标题
    private String title;

    //消息内容
    @NotEmpty
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private String context;

    //消息附带的链接
    private String url;

    //消息状态
    @NotNull
    @Column(nullable = false)
    @Convert(converter = MessageStateConvert.class)
    private WebMessageState state;

    //消息类别
    @NotNull
    @Column(nullable = false, columnDefinition = "INT default 99")
    private Integer category;

    @Override
    public String toString() {
        return "{" +
                "sourceUserId='" + sourceUserId + '\'' +
                ", targetUserId='" + targetUserId + '\'' +
                ", sourceSystemName='" + sourceSystemName + '\'' +
                ", context='" + context + '\'' +
                ", url='" + url + '\'' +
                ", state=" + state.getCode() +
                '}';
    }

    public ScMsaWebMessage setCategory(Integer category) {
        WebMessageCategory webMessageCategory = WebMessageCategory.getInstance(category);
        if (webMessageCategory == null) {
            throw new WebMessageException("消息类别错误,category：" + category);
        }
        this.category = category;
        return this;
    }
}
