package org.example.service;

import org.example.entity.ScMsaWebMessageHistory;
import org.example.repository.ScMsaWebMessageHistoryRepository;
import org.springframework.stereotype.Service;


/**
 * @author admin
 * @date 2021-05-31
 * @description
 */
@Service
public class ScMsaWebMessageHistoryService{

    private ScMsaWebMessageHistoryRepository repository;

    public ScMsaWebMessageHistoryService(ScMsaWebMessageHistoryRepository repository) {
        this.repository = repository;
    }
}
