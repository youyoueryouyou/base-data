package com.you.base;

public enum ErrorCode {
    COMMAN_ERROR(-1),PARAM_EMPTY(1),PARAM_ERROR(2),NO_ACCESS(3),NO_LOGIN(4);
    private int code;
    ErrorCode(int code){
     this.code = code;
    }
    public int value(){
        return code;
    }
}
