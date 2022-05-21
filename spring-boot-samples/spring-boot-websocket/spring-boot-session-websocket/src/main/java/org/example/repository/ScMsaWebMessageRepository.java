package org.example.repository;

import org.example.entity.ScMsaWebMessage;
import org.example.enums.WebMessageState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @date 2021-05-27
 * @description
 */
@Repository
public interface ScMsaWebMessageRepository extends JpaRepository<ScMsaWebMessage,String> {

    List<ScMsaWebMessage> findAllByStateAndTargetUserId(WebMessageState state, String targetUserId);
    List<ScMsaWebMessage> findAllByTargetUserId(String targetUserId);

    List<ScMsaWebMessage> findAllByStateAndTargetUserIdAndSourceSystemName(WebMessageState state, String targetUserId, String sourceSystemName);
    List<ScMsaWebMessage> findAllByTargetUserIdAndSourceSystemName(String targetUserId, String sourceSystemName);
}
