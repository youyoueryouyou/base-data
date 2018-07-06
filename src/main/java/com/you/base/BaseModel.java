package com.you.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shicz
 */
public class BaseModel implements Serializable
{
    @JSONField(serialize = false)
    private static final long serialVersionUID = 6067283535977178571L;

    @JSONField(serialize = false)
    private Integer pageNo = null;
    
    @JSONField(serialize = false)
    private Integer pageSize = null;

    @JSONField(serialize = false)
    private String orderByClause;

    @JSONField(serialize = false)
    private String tableName;
    
    @JSONField(serialize = false)
    private List<ColumnMap> columns;

    @JSONField(serialize = false)
    public String getTableName()
    {
        return tableName;
    }
    
    @JSONField(serialize = false)
    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }
    
    @JSONField(serialize = false)
    public List<ColumnMap> getColumns()
    {
        return columns;
    }
    
    @JSONField(serialize = false)
    public void setColumns(List<ColumnMap> columns)
    {
        this.columns = columns;
    }
    
    @JSONField(serialize = false)
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    @JSONField(serialize = false)
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    @JSONField(serialize = false)
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    @JSONField(serialize = false)
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    @JSONField(serialize = false)
    public String getOrderByClause() {
        return orderByClause;
    }

    @JSONField(serialize = false)
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @JSONField(serialize = false)
    public BaseModel initColumn() throws Exception{
        BaseModel model = this;
        String className = model.getClass().getName();
        Table table = model.getClass().getAnnotation(Table.class);
        if (table == null){
            throw new Exception(className+": table not exist.");
        }
        String tableName = table.value();
        if (tableName == null || "".equals(tableName)){
            throw new Exception(className+": tableName is empty.");
        }
        model.setTableName(table.value());
        List<ColumnMap> list = new ArrayList<ColumnMap>();
        Field[] fields = model.getClass().getDeclaredFields();
        getColumnInfo(fields,list,model);
        Field[] superFields = model.getClass().getSuperclass().getDeclaredFields();
        getColumnInfo(superFields,list,model);
        if (list.size() == 0){
            throw new Exception(className+": column is empty.");
        }
        String primaryColumnKey = null,primaryFeildKey = null;
        for (ColumnMap map : list){
            if (map.getPrimaryKey()){
                if (primaryColumnKey == null && primaryFeildKey == null){
                    primaryColumnKey = map.getColumnName();
                    primaryFeildKey = map.getFieldName();
                }else {
                    throw new Exception(className+": primaryKey is repeat.");
                }
            }
        }
        model.setColumns(list);
        return model;
    }

    @JSONField(serialize = false)
    private void getColumnInfo(Field[] fields, List<ColumnMap> list,Object object){
        for (Field field : fields){
            Column column = field.getAnnotation(Column.class);
            if (column != null){
                ColumnMap columnMap = new ColumnMap();
                String name = column.value();
                if (name == null || "".equals(name)){
                    name = field.getName();
                }
                columnMap.setColumnName(name);
                if (column.key()){
                    columnMap.setPrimaryKey(true);
                }else {
                    columnMap.setPrimaryKey(false);
                }
                columnMap.setFieldName(field.getName());
                Object value = null;
                try {
                    field.setAccessible(true);
                    value = field.get(object);
                }catch (Exception e){
                    e.printStackTrace();
                }
                columnMap.setColumnValue(value);
                list.add(columnMap);
            }
        }
    }
}
