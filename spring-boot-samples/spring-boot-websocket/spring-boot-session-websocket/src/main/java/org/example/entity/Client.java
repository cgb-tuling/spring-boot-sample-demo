package org.example.entity;

import lombok.*;

import javax.websocket.Session;
import java.io.Serializable;

/**
 * @author admin
 * @date 2021-05-25
 * @description Client
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Client implements Serializable {

    private String userId;

    private String realName;

    private Session session;
}
