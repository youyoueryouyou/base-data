package com.you.base;

/**
 * Created by shicz on 2018/5/16.
 */
public class BaseResponse {
    private Boolean success;
    private String message;
    private Object result;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
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
        response.setMessage("ok");
        return response;
    }

    public static BaseResponse successMessage(String message){
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setMessage(message);
        return response;
    }

    public static BaseResponse successResult(Object result){
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setMessage("ok");
        response.setResult(result);
        return response;
    }
    public static BaseResponse successResult(Object result,String message){
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setMessage(message);
        response.setResult(result);
        return response;
    }

    public static BaseResponse failureMessage(){
        BaseResponse response = new BaseResponse();
        response.setSuccess(false);
        response.setMessage("failure");
        return response;
    }

    public static BaseResponse failureMessage(String message){
        BaseResponse response = new BaseResponse();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }

}
