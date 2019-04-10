package com.you.base;

public enum ErrorCode {
    COMMAN_ERROR(-1),PARAM_EMPTY(1),PARAM_ERROR(2);
    private int code;
    ErrorCode(int code){
     this.code = code;
    }
    public int value(){
        return code;
    }
}
