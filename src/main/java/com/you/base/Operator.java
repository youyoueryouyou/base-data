package com.you.base;

public enum Operator {
    EQ("="),NE("<>"),GT(">"),GET(">="),LT("<"),LET("<="),EP("is null"),NEP("is not null"),LK("like"),NLK("not like"),IN("in"),NIN("not in");
    private String value;
    private Operator(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
}
