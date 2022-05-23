package com.test.webservice.endpoint;
import com.test.webservice.pda.PdaLoginRequest;
import com.test.webservice.pda.PdaLoginResponse;
import com.test.webservice.servcie.LoginService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
 
import javax.annotation.Resource;
import java.util.List;
 
 
/**
 * PDA接口切点
 * @author
 * @version badao
 * @see
 * @since
 **/
@Endpoint
public class PdaEndpoint {
 
    @Resource
    private LoginService loginService;//引入用户mapper
 
 
 
    private static final String NAMESPACE_URI = "http://test.com/webservice/pda";
 
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PdaLoginRequest")
    @ResponsePayload
    public PdaLoginResponse statusFeedbacke(@RequestPayload PdaLoginRequest request){
        String username = request.getUsername();
        String password = request.getPassword();
        return loginService.loginRequest(username,password);
    }
  
}