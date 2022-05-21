
package org.example.error;

import lombok.extern.slf4j.Slf4j;
import org.example.result.Result;
import org.example.result.ResultCode;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理
 *
 * @author Chill
 */
@Slf4j
public class NewhopeErrorAttributes extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
		String requestUri = this.getAttr(webRequest, "javax.servlet.error.request_uri");
		Integer status = this.getAttr(webRequest, "javax.servlet.error.status_code");
		Throwable error = getError(webRequest);
		Result result;
		if (error == null) {
			log.error("URL:{} error status:{}", requestUri, status);
			result = Result.failure(ResultCode.FAILURE, "系统未知异常[HttpStatus]:" + status);
		} else {
			log.error(String.format("URL:%s error status:%d", requestUri, status), error);
			result = Result.failure(status, error.getMessage());
		}
		//发送服务异常事件
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("code",result.getCode());
		hashMap.put("message",result.getMessage());
		hashMap.put("data",null);
		return hashMap;
	}

	@Nullable
	private <T> T getAttr(WebRequest webRequest, String name) {
		return (T) webRequest.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}

}
