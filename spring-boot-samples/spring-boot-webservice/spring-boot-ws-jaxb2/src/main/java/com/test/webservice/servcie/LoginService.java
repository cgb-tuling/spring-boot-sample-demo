package com.test.webservice.servcie;
 
import com.test.webservice.pda.PdaLoginResponse;
import org.springframework.stereotype.Service;
 
@Service
public interface LoginService {
    PdaLoginResponse loginRequest(String username, String password);
}