package org.example.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author admin
 * @date 2020-06-05 0:32
 * @description 引入web推送服务
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({WebSocketConfig.class})
@Documented
public @interface EnableScMsaWebMessage {
}
