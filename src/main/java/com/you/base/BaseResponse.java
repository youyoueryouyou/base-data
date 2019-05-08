package com.you.base;

/**
 * @author shicz
 */
public class BaseResponse {
    private Boolean success;
    private Integer code;
    private String message;
    private Object result;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public static BaseResponse successMessage(){
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setCode(0);
        response.setMessage("success");
        return response;
    }

    public static BaseResponse successMessage(String message){
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setCode(0);
        response.setMessage(message);
        return response;
    }

    public static BaseResponse successResult(Object result){
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setCode(0);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }
    public static BaseResponse successResult(Object result,String message){
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setCode(0);
        response.setMessage(message);
        response.setResult(result);
        return response;
    }

    public static BaseResponse failureMessage(){
        BaseResponse response = new BaseResponse();
        response.setSuccess(false);
        response.setCode(-1);
        response.setMessage("failure");
        return response;
    }

    public static BaseResponse failureMessage(String message){
        BaseResponse response = new BaseResponse();
        response.setSuccess(false);
        response.setCode(-1);
        response.setMessage(message);
        return response;
    }

    public static BaseResponse failureMessage(Integer code,String message){
        BaseResponse response = new BaseResponse();
        response.setSuccess(false);
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

}
