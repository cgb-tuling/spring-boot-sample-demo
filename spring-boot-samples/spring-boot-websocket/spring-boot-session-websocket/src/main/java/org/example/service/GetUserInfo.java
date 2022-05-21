package org.example.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.example.config.WebMessageConstant;
import org.example.dto.ScSecurityUserInfoDto;
import org.example.result.ScResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

/**
 * @author admin
 * @date 2021-06-28
 * @description
 */
@Slf4j
@Validated
@Service
public class GetUserInfo {

    private static final String uri = "/admin/users/getusersinfobyids?ids=";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WebMessageConstant constant;

    public List<ScSecurityUserInfoDto> getUserInfoByUserIds(@NotEmpty List<String> userIds){
        String url = "http://" + constant.getHost() + ":15042"  + uri + userIds;
        ScResult result = restTemplate.getForObject(url, ScResult.class);
        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(result.getData()));
        List<ScSecurityUserInfoDto> userInfos = Lists.newArrayList();
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(o);
            userInfos.add(JSON.toJavaObject(jsonObject,ScSecurityUserInfoDto.class));
        }
        return userInfos;
    }

    public ScSecurityUserInfoDto getUserInfoByUserId(@NotEmpty String userId){
        List<ScSecurityUserInfoDto> users = getUserInfoByUserIds(Arrays.asList(userId));
        if(users.size() > 0){
            return users.get(0);
        }
        String errMsg  = String.format("根据用户id【%s】查询用户信息失败，请联系管理员！",userId);
        log.error(errMsg);
        return ScSecurityUserInfoDto.builder().username(userId).realName(userId).build();
        // String errMsg  = String.format("根据用户id【%s】查询用户信息失败，请联系管理员！",userId);
        // log.error(errMsg);
        // throw new WebMessageException(errMsg);
    }
}
