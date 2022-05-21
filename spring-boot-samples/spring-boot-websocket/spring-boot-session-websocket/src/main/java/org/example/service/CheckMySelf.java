package org.example.service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Sets;
import org.example.dto.ScSecurityMyselfOrgDto;
import org.example.dto.ScSecurityMyselfRoleDto;
import org.example.dto.ScSecurityMyselfUserDto;
import org.example.entity.Client;
import org.example.exception.WebMessageException;
import org.example.result.ScResult;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * @author admin
 * @date 2021-06-15
 * @description
 */
@Component
@Slf4j
public class CheckMySelf {

    //获取用户信息接口地址
    private final static String GET_USER_URL = "http://scmsa-oauth2-admin/admin/users/myself";

    @Resource(name = "webMessageRestTemplate")
    private RestTemplate restTemplate;

    public Client getClient(String token){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization",token);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity httpEntity = new HttpEntity(headers);
            ScResult scResult = restTemplate.exchange(GET_USER_URL, HttpMethod.GET, httpEntity, ScResult.class).getBody();
            HashMap<String, Object> map = (HashMap<String, Object>) scResult.getData();
            String userId = map.get("username").toString();
            String realName = map.get("realName").toString();
            return Client.builder().userId(userId).realName(realName).build();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new WebMessageException("token错误,请检查：" + token);
        }
    }

    /**
     * 获取登录人用户信息
     */
    public ScSecurityMyselfUserDto getUserInfoByUsername() {
        ScResult scResult = restTemplate.getForObject(GET_USER_URL, ScResult.class);
        HashMap<String, Object> map = (HashMap<String, Object>) scResult.getData();
        Object roles = map.get("roles");
        Set<ScSecurityMyselfRoleDto> scSecurityRoleEntitySet = new HashSet<>();
        JSONArray jsonArray1 = JSON.parseArray(JSON.toJSONString(roles));
        for (Object o1 : jsonArray1) {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(o1);
            ScSecurityMyselfRoleDto scSecurityRoleEntity = new ScSecurityMyselfRoleDto();
            scSecurityRoleEntity.setRoleId(jsonObject.get("roleId").toString());
            scSecurityRoleEntity.setRoleName(jsonObject.get("roleName").toString());
            scSecurityRoleEntity.setId(jsonObject.get("id").toString());
            scSecurityRoleEntitySet.add(scSecurityRoleEntity);
        }

        Object organizations = map.get("organizations");
        Set<ScSecurityMyselfOrgDto> scSecurityOrganizationEntitySet = Sets.newHashSet();
        JSONArray jsonArray2 = JSON.parseArray(JSON.toJSONString(organizations));
        for (Object o1 : jsonArray2) {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(o1);
            ScSecurityMyselfOrgDto scSecurityOrganizationEntity = new ScSecurityMyselfOrgDto();
            scSecurityOrganizationEntity.setOrganizationId(jsonObject.get("organizationId").toString());
            scSecurityOrganizationEntity.setId(jsonObject.get("id").toString());
            scSecurityOrganizationEntity.setOrganizationName(jsonObject.get("organizationName").toString());
            scSecurityOrganizationEntity.setParentId(jsonObject.get("parentId").toString());
            scSecurityOrganizationEntitySet.add(scSecurityOrganizationEntity);
        }

        ScSecurityMyselfUserDto scSecurityUserEntityResult = new ScSecurityMyselfUserDto();
        scSecurityUserEntityResult.setUsername(map.get("username").toString());
        scSecurityUserEntityResult.setRealName(map.get("realName").toString());
        scSecurityUserEntityResult.setMail(map.get("mail").toString());
        scSecurityUserEntityResult.setTelephoneNumber(map.get("telephoneNumber").toString());
        scSecurityUserEntityResult.setRoles(scSecurityRoleEntitySet);
        scSecurityUserEntityResult.setOrganizations(scSecurityOrganizationEntitySet);
        log.info(">>>>>>远程调用成功，返回结果：\n{}", scSecurityUserEntityResult.toString());
        return scSecurityUserEntityResult;
    }
}
