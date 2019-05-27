package com.you.base;

import java.io.Serializable;

/**
 * @author shicz
 */
public class ColumnMap implements Serializable
{
    private String columnName;
    
    private Object columnValue;
    
    private String fieldName;
    
    private Boolean primaryKey;

    public String getColumnName()
    {
        return columnName;
    }
    
    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public Object getColumnValue()
    {
        return columnValue;
    }
    
    public void setColumnValue(Object columnValue)
    {
        this.columnValue = columnValue;
    }
    
    public String getFieldName()
    {
        return fieldName;
    }
    
    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }
    
    public Boolean getPrimaryKey()
    {
        return primaryKey;
    }
    
    public void setPrimaryKey(Boolean primaryKey)
    {
        this.primaryKey = primaryKey;
    }

    @Override
    public String toString() {
        return "ColumnMap{" +
                "columnName='" + columnName + '\'' +
                ", columnValue=" + columnValue +
                ", fieldName='" + fieldName + '\'' +
                ", primaryKey=" + primaryKey +
                '}';
    }
}
