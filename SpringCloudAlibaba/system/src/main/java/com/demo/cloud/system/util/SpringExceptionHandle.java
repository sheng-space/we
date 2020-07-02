package com.demo.cloud.system.util;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.net.ConnectException;
import org.apache.ibatis.binding.BindingException;

/**
 * 全局异常处理
 *
 * @author sheng
 * @date 2020/07/01
 */
@RestControllerAdvice(annotations={RestController.class,Controller.class})
public class SpringExceptionHandle {

    /**
     * 请求参数类型错误异常的捕获
     * @param e
     * @return
     */
    @ExceptionHandler(value={BindException.class})
    @ResponseBody
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ResponseBean<String> badRequest(BindException e){
        return new ResponseBean<>(false, ResponseCode.BAD_REQUEST);
    }

    /**
     * 404错误异常的捕获
     * @param e
     * @return
     */
    @ExceptionHandler(value={NoHandlerFoundException.class})
    @ResponseBody
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public ResponseBean<String> badRequestNotFound(BindException e){
        return new ResponseBean<>(false,null, ResponseCode.NOT_FOUND);
    }

    /**
     * mybatis未绑定异常
     * @param e
     * @return
     */
    @ExceptionHandler(BindingException.class)
    @ResponseBody
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBean<String> mybatis(Exception e){
        return new ResponseBean<>(false, ResponseCode.BOUND_STATEMENT_NOT_FOUNT);
    }
    /**
     * 自定义异常的捕获
     * 自定义抛出异常。统一的在这里捕获返回JSON格式的友好提示。
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(value={ResponseRuntimeException.class})
    @ResponseBody
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public <T> ResponseBean<T> sendError(ResponseRuntimeException exception, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        return new ResponseBean<>(false,exception.getCode(),exception.getMsg());
    }
    /**
     * 数据库操作出现异常
     * @param e
     * @return
     */
    @ExceptionHandler(value={SQLException.class, DataAccessException.class})
    @ResponseBody
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBean<String> systemError(Exception e){
        return new ResponseBean<>(false, ResponseCode.DATABASE_ERROR);
    }
    /**
     * 网络连接失败！
     * @param e
     * @return
     */
    @ExceptionHandler(value={ConnectException.class})
    @ResponseBody
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBean<String> connect(Exception e){
        return new ResponseBean<>(false, ResponseCode.CONNECTION_ERROR);
    }

    @ExceptionHandler(value={Exception.class})
    @ResponseBody
    @ResponseStatus(value=HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseBean<String> notAllowed(Exception e){
        return new ResponseBean<>(false, ResponseCode.METHOD_NOT_ALLOWED);
    }
}
