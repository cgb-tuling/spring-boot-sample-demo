package org.example.config;

import com.sun.org.apache.xml.internal.utils.URI;
import lombok.Getter;
import org.example.exception.WebMessageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author admin
 * @date 2021-06-07
 * @description 常量池
 */
@Component
@Getter
public class WebMessageConstant {

    @Value("${spring.application.name:undefined}")
    private String applicationName;

    @Value("${security.oauth2.client.client-id:undefined}")
    private String clientId;

    @Value("${server.port:17019}")
    private String port;

    @Value("${scmsa.web.message.online.user.database:db}")
    private String dataBaseType;

    @Value("${spring.application.full-name}")
    private String fullName;

    @Value("${security.oauth2.client.access-token-uri}")
    private String oauth2_client_uri;

    public String getUserId(){
        Authentication authentication = getAuthentication();
        if(authentication == null){
            return "";
        }
        Object principal = authentication.getPrincipal();
        if(principal == null){
            return "";
        }
        return principal.toString();
    }

    public String getFullName(){
        if(StringUtils.isEmpty(fullName)){
            return applicationName;
        }
        return fullName;
    }

    public String getHost(){
        URI uri;
        try {
            uri = new URI(oauth2_client_uri);
        } catch (URI.MalformedURIException e) {
            throw new WebMessageException("读取配置文件security.oauth2.client.access-token-uri的ip错误，请联系管理员。");
        }
        return uri.getHost();
    }

    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getIp() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface =  allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new WebMessageException("IP地址获取失败" + e.toString());
        }
        return "";
    }

    public boolean isRedisCache(){
        if("redis".equals(dataBaseType)){
            return true;
        }
        return false;
    }
}
