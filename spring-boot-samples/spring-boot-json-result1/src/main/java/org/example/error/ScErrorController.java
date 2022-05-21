package org.example.error;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.BizException;
import org.example.result.Result;
import org.example.result.ResultCode;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RestControllerAdvice
public class ScErrorController extends BasicErrorController {

    private final static String RESOURCE_NOT_FOUND_CODE = "404";
    private final static String RESOURCE_NOT_FOUND_PATH = "/404";
    private final static String FORBIDDEN_CODE = "403";
    private final static String FORBIDDEN_PATH = "/403";
    private final static String STATUS_CODE = "status";

    public ScErrorController(ServerProperties serverProperties) {
        super(new DefaultErrorAttributes(), serverProperties.getError());
    }

    /**
     * 覆盖默认的Json响应
     */
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        super.error(request);
        Map<String, Object> body = getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE));
        HttpStatus status = getStatus(request);
        ResponseEntity<Map<String, Object>> ret = new ResponseEntity<>(body, status);
        ret.getBody().put("code", "-1");
        ret.getBody().put("message", body.get("message"));
        ret.getBody().put("data", "error");
        log.error(ret.toString());
        return ret;
    }

    /**
     * 覆盖默认的HTML响应
     * 主要是分发到自定义异常页面中。
     */
    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        //请求的状态
        HttpStatus status = getStatus(request);
        response.setStatus(getStatus(request).value());
        Map<String, Object> model = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.TEXT_HTML));
        log.error(model.toString());
        ModelAndView modelAndView = resolveErrorView(request, response, status, model);

        String statusCode = null;
        String errPagePath;
        if (model.get(STATUS_CODE) != null) {
            statusCode = model.get(STATUS_CODE).toString();
        }
        if (RESOURCE_NOT_FOUND_CODE.equals(statusCode)) {
            errPagePath = RESOURCE_NOT_FOUND_PATH;
        } else if (FORBIDDEN_CODE.equals(statusCode)) {
            errPagePath = FORBIDDEN_PATH;
        } else {
            errPagePath = "err";
        }
        errPagePath = "redirect:" + errPagePath + ".html";
        if (modelAndView == null) {
            modelAndView = new ModelAndView(errPagePath, model);
        } else {
            modelAndView.setViewName(errPagePath);

        }
        return modelAndView;
    }

    /**
     * 微服务平台异常
     */
    @ExceptionHandler(BizException.class)
    @ResponseBody
    public <T> Result<T> scBaseExceptionHandler(HttpServletRequest request, Exception exception) {
        return parseValidException(exception, ResultCode.RUNTIME, exception.getMessage());
    }

    /**
     * 此处的BindException 是 Spring 框架抛出的Validation异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public <T> Result<T> jsrExceptionHandler(HttpServletRequest request, Exception exception) {
        BindException ex = (BindException) exception;
        // 抛出异常可能不止一个 输出为一个List集合
        List<ObjectError> errors = ex.getAllErrors();
        return parseValidException(exception, ResultCode.PARAM_IS_INVALID, errors.get(0).getDefaultMessage());
    }

    /**
     * Spring 框架抛出的Validation异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public <T> Result<T> methodArgumentNotValidExceptionHandler(HttpServletRequest request, Exception exception) {
        // 框架抛出的Validation异常
        MethodArgumentNotValidException ex = (MethodArgumentNotValidException) exception;
        // 抛出异常可能不止一个 输出为一个List集合
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        return parseValidException(exception, ResultCode.PARAM_IS_INVALID, errors);
    }

    /**
     * 其他系统异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public <T> Result<T> exceptionHandler(HttpServletRequest request, Exception exception) {
        return parseValidException(exception, ResultCode.RUNTIME, exception.getMessage());
    }

    /**
     * 返回异常结果集
     */
    private <T> Result<T> parseValidException(Exception exception, ResultCode runtime, String defaultMessage) {
        exception.printStackTrace();
        Result<T> rs = new Result<>();
        rs.setCode(runtime.getCode());
        rs.setMessage(runtime.getMessage() + ":" + defaultMessage);
        log.error(exception.getMessage(), exception.getCause());
        return rs;
    }

    /**
     * 返回异常结果集
     */
    private <T> Result<T> parseValidException(Exception exception, ResultCode code, List<ObjectError> errors) {
        if (errors == null) {
            return Result.failure(code);
        } else if (errors.size() == 1) {
            ObjectError error = errors.get(0);
            String errInfo = parseFieldError(error);
            return parseValidException(exception, code, errInfo);
        } else {
            StringBuilder sb = new StringBuilder();
            for (ObjectError error : errors) {
                sb.append(parseFieldError(error)).append(" ");
            }
            return parseValidException(exception, code, sb.toString());
        }
    }
    public <T> Result<T> getValidateError(BindingResult bindingResult) {
        if(!bindingResult.hasErrors()) {
            return null;
        }
        Map<String,String> fieldErrors = new HashMap<String, String>();

        for(FieldError error : bindingResult.getFieldErrors()){
            fieldErrors.put(error.getField(), error.getCode() + "|" + error.getDefaultMessage());
        }
        Result ret=new Result();
//        Result ret = new Result(422, "输入错误"); //rfc4918 - 11.2. 422: Unprocessable Entity
//        ret.putData("fieldErrors", fieldErrors);
        return ret;
    }

    /**
     * 解析 FieldError
     */
    private String parseFieldError(ObjectError error) {
        String errInfo;
        if (error instanceof FieldError) {
            FieldError fieldError = (FieldError) error;
            errInfo = String.format("【%s】- %s", fieldError.getField(), fieldError.getDefaultMessage());
        } else {
            errInfo = error.getDefaultMessage();
        }
        return errInfo;
    }
}