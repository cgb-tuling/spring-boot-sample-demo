package org.example.service;
import org.example.enums.WebMessageCategory;
import org.example.result.ScResult;
import org.springframework.validation.annotation.Validated;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author admin
 * @date 2021-06-21
 * @description web消息发送
 */
@Validated
public interface WebMessageSendI {


    /**
     * 发送web消息
     *
     * @param title     标题
     * @param context   消息体
     * @param url       消息附带的链接
     * @param username  消息接收方用户名
     * @param category  消息类别
     * @return ScResult
     */
    ScResult send(String title
            , @NotEmpty(message = "消息体不能为空") String context
            , String url
            , @NotEmpty(message = "消息接收方用户名不能为空") Set<String> username
            , @NotNull(message = "消息类别category不能为空") WebMessageCategory category);
}
