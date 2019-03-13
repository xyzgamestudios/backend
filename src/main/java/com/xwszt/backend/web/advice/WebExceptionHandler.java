package com.xwszt.backend.web.advice;

import com.xwszt.backend.web.vo.Json;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * web层异常统一处理
 *
 * @author xwszt
 */
//@ControllerAdvice
public class WebExceptionHandler {

    /**
     * 捕捉Exception,并以Json格式返回给客户端
     *
     * @param ex
     * @return
     */
//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
    public Json execptionHandler(Exception ex) {
        return new Json();
    }

    /**
     * 跳转到页面的异常捕获
     *
     * @param ex
     * @return
     */
//    @ExceptionHandler(value = Exception.class)
    public ModelAndView pageExceptionHandler(Exception ex){
        return new ModelAndView();
    }
}
