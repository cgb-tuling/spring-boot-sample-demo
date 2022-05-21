package org.example.repository;

import org.example.entity.ScMsaWebMessageHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author admin
 * @date 2021-05-31
 * @description
 */
@Repository
public interface ScMsaWebMessageHistoryRepository extends JpaRepository<ScMsaWebMessageHistory,String> {
}
