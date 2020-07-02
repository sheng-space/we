package com.demo.cloud.system.util;
/**
 * 异常处理
 *
 * @author sheng
 * @date 2020/07/01
 */
public class ResponseRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    protected String code;

    protected String msg;

    protected String message;
    public ResponseRuntimeException(ResponseCode enums, String message) {
        super();
        this.code = enums.getCode();
        this.msg = enums.getMsg();
        this.message = message;
    }

    public ResponseRuntimeException(ResponseCode enums) {
        super();
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseRuntimeException() {
        super();
    }

    public ResponseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseRuntimeException(String message) {
        super(message);
    }
}
