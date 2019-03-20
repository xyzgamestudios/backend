package com.xwszt.backend.web.advice;

import com.xwszt.backend.exception.CustomException;
import com.xwszt.backend.web.vo.Json;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * web层异常统一处理
 *
 * @author xwszt
 */
@ControllerAdvice
public class WebExceptionHandler {

    /**
     * 捕捉Exception,并以Json格式返回给客户端
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = CustomException.class)
    @ResponseBody
    public Json handlerExceptionResolver(CustomException ex) {
        Json json = new Json();
        json.setCode(ex.getCode());
        json.setMsg(ex.getMessage());
        json.setData(ex);
        return json;
    }
}
