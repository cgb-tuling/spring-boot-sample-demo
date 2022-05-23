package com.test.webservice.servcie.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.webservice.domain.SysUser;
import com.test.webservice.mapper.SysUserMapper;
import com.test.webservice.pda.LoginRequestResult;
import com.test.webservice.pda.Menu;
import com.test.webservice.pda.MenuList;
import com.test.webservice.pda.PdaLoginResponse;
import com.test.webservice.servcie.LoginService;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private SysUserMapper sysUserMapper;//引入用户mapper

    @Override
    public PdaLoginResponse loginRequest(String username, String password) {
        PdaLoginResponse response = new PdaLoginResponse();
        LoginRequestResult result = new LoginRequestResult();
        MenuList menuList = new MenuList();
        List<Menu> menus = menuList.getMenu();
        //MD5加密开始
        //String salt = SequenceUtil.getInst().getRandomCode(6);
        //开始查询数据库中有没有用户名
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<SysUser>();
        sysUserQueryWrapper.eq("account", username);
        sysUserQueryWrapper.eq("is_delete", "0");
        SysUser sysUser = sysUserMapper.selectOne(sysUserQueryWrapper);
        String encryptPassword = "";
        if (sysUser == null) {
            result.setRequestResult(false);
            result.setResultDesc("用户名不存在");
        } else {
            if (sysUser.getSalt() != null) {
                String salt = sysUser.getSalt();
                //加密后的密碼
                //encryptPassword = ShiroHelper.SHA1(password, username + salt);
                encryptPassword = password + username + salt;
                //用户名存在时判断密码是否正确
                sysUserQueryWrapper.eq("password", encryptPassword);
                SysUser sysUser2 = sysUserMapper.selectOne(sysUserQueryWrapper);
                if (sysUser2 == null) {
                    result.setRequestResult(false);
                    result.setResultDesc("密码不正确");
                } else {
                    Menu menu = new Menu();
                    menu.setMenu("生产领料收货");
                    menu.setUrl("/ProduceReceiveRequest");
                    menus.add(menu);
                    Menu m = new Menu();
                    m.setMenu("质检领料收货");
                    m.setUrl("/QualityReceiveRequest");
                    menus.add(m);
                    Menu m2 = new Menu();
                    m2.setMenu("单件补码");
                    m2.setUrl("/SolidCodeRequest");
                    menus.add(m);
                    result.setRequestResult(true);
                    result.setResultDesc("登录成功");
                }
            }
        }
        response.setRequestResult(result);
        response.setMenuResult(menuList);
        return response;
    }

}