package org.example.repository;

import org.example.entity.ShareConnectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author admin
 * @date 2021-06-21
 * @description
 */
@Repository
public interface ShareConnectUserDbRepository extends JpaRepository<ShareConnectUser,String> {

    Set<ShareConnectUser> findAllByUserId(String userId);

    @Modifying
    @Transactional
    void deleteBySessionId(String sessionId);

    @Modifying
    @Transactional
    void deleteByUserId(String userId);
}
