package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.config.WebMessageConstant;
import org.example.entity.ScMsaWebMessage;
import org.example.entity.ScMsaWebMessageHistory;
import org.example.enums.WebMessageCategory;
import org.example.enums.WebMessageState;
import org.example.repository.ScMsaWebMessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author admin
 * @date 2021-05-27
 * @description
 */
@Slf4j
@Validated
@Service
@Transactional(rollbackFor = Exception.class)
public class ScMsaWebMessageService {

    private final ScMsaWebMessageRepository repository;
    private final ScMsaWebMessageHistoryService scMsaPushMessageHistoryService;
    private final WebMessageConstant constant;

    public ScMsaWebMessageService(ScMsaWebMessageRepository repository
            , ScMsaWebMessageHistoryService scMsaPushMessageHistoryService
            , WebMessageConstant constant) {
        this.repository = repository;
        this.scMsaPushMessageHistoryService = scMsaPushMessageHistoryService;
        this.constant = constant;
    }

    //消息的持久化
    public ScMsaWebMessage save(@NotEmpty String sourceUserName
            , String title
            , @NotEmpty(message = "消息体不能为空") String context
            , String url
            , @NotEmpty(message = "消息接收方用户名不能为空") String targetUserId
            , WebMessageCategory category) {
        String sourceUserId = constant.getUserId();
        return repository.saveAndFlush(new ScMsaWebMessage()
                .setSourceUserId(sourceUserId)
                .setSourceUserName(sourceUserName)
                .setSourceSystemName(constant.getApplicationName())
                .setTitle(title)
                .setContext(context)
                .setUrl(url)
                .setTargetUserId(targetUserId)
                .setCategory(category == null ? WebMessageCategory.OTHER.getCode() : category.getCode())
                .setState(WebMessageState.unread));
    }

    //查询
    public Page<ScMsaWebMessage> findMessage(@NotEmpty String userId, WebMessageCategory category, WebMessageState state, int page, int limit) {
        return null;
    }


    //查询
    public List<ScMsaWebMessage> findMessage(@NotEmpty String userId, WebMessageCategory category, WebMessageState state) {
        return null;
    }

    //删除
    public void removeMessage(@NotEmpty Set<String> ids) {
/*        List<ScMsaWebMessage> all = createConditionsQuery().addInQuery("id", ids).list();
        //删除前写入历史表
        scMsaPushMessageHistoryService.save(
                all.stream().map(m -> ScMsaWebMessageHistory.builder().message(m).build()).collect(Collectors.toSet())
        );*/
        repository.deleteAll();
    }

    //批量已读
    public void ack(@NotEmpty Set<String> ids) {
        List<ScMsaWebMessage> allById = repository.findAllById(ids);
/*        List<ScMsaWebMessage> messages = createConditionsQuery().addInQuery("id", ids).list();
        messages.forEach(m -> m.setState(WebMessageState.read));*/
        repository.saveAll(allById);
    }

    //全部已读
    public void ackAll(@NotEmpty String userId) {
        List<ScMsaWebMessage> list = repository.findAllByStateAndTargetUserId(WebMessageState.unread, userId);
/*        List<ScMsaWebMessage> list = createConditionsQuery()
                .addEqualQuery("state", WebMessageState.unread.getCode())
                .addEqualQuery("targetUserId", userId)
                .list();*/
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(m -> m.setState(WebMessageState.read));
            repository.saveAll(list);
        }
    }

    //全部删除
    public void removeAll(@NotEmpty String userId) {
        List<ScMsaWebMessage> list = repository.findAllByTargetUserId(userId);
   /*     List<ScMsaWebMessage> list = createConditionsQuery()
                .addEqualQuery("targetUserId", userId)
                .list();*/
        if (!CollectionUtils.isEmpty(list)) {
            Set<String> ids = list.stream().map(ScMsaWebMessage::getId).collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(ids)) {
                removeMessage(ids);
            }
        }
    }

}
