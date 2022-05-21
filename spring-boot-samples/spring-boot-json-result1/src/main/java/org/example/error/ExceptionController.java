package org.example.error;

import org.example.result.Result;
import org.example.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

//@RestController
public class ExceptionController implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    /**
     * 默认错误
     */
    private static final String path_default = "/error";

//    @Override
    public String getErrorPath() {
        return path_default;
    }

    /**
     * JSON格式错误信息
     */
    @RequestMapping(value = path_default, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Result<Map<String, Object>> error(HttpServletRequest request) {
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        Map<String, Object> body =
                this.errorAttributes.getErrorAttributes(servletWebRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE));
        return Result.failure(ResultCode.PARAM_IS_INVALID, body);
    }

}