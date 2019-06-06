package com.you.base;

public class BaseParam {
    private String cloumn;
    private Object value;
    private Operator operator;

    public String getCloumn() {
        return cloumn;
    }

    public void setCloumn(String cloumn) {
        this.cloumn = cloumn;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

}
