package com.you.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shicz
 */
public class BaseModel implements Serializable {
    @JSONField(serialize = false)
    private static final long serialVersionUID = 6067283535977178571L;

    @JSONField(serialize = false)
    private Integer pageNo = null;

    @JSONField(serialize = false)
    private Integer pageSize = null;

    @JSONField(serialize = false)
    private String orderKey;

    @JSONField(serialize = false)
    private String orderType;

    @JSONField(serialize = false)
    private String tableName;

    @JSONField(serialize = false)
    private List<ColumnMap> columns;

    @JSONField(serialize = false)
    private List<BaseParam> params;

    @JSONField(serialize = false)
    public String getTableName() {
        if (tableName == null || "".equals(tableName)) {
            try {
                initColumn();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tableName;
    }

    @JSONField(serialize = false)
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @JSONField(serialize = false)
    public List<ColumnMap> getColumns() {
        if (columns == null || columns.isEmpty()) {
            try {
                initColumn();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return columns;
    }

    @JSONField(serialize = false)
    public void setColumns(List<ColumnMap> columns) {
        this.columns = columns;
    }

    @JSONField(serialize = false)
    public List<BaseParam> getParams() {
        return params;
    }

    @JSONField(serialize = false)
    public void setParams(List<BaseParam> params) {
        this.params = params;
    }

    @JSONField(serialize = false)
    public Integer getPageNo() {
        return pageNo;
    }

    @JSONField(serialize = false)
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    @JSONField(serialize = false)
    public Integer getPageSize() {
        return pageSize;
    }

    @JSONField(serialize = false)
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @JSONField(serialize = false)
    public String getOrderKey() {
        return orderKey;
    }

    @JSONField(serialize = false)
    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    @JSONField(serialize = false)
    public String getOrderType() {
        return orderType;
    }

    @JSONField(serialize = false)
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @JSONField(serialize = false)
    public BaseModel initColumn() throws Exception {
        BaseModel model = this;
        String className = model.getClass().getName();
        Table table = model.getClass().getAnnotation(Table.class);
        if (table == null) {
            throw new Exception(className + ": table not exist.");
        }
        String tableName = table.value();
        if (tableName == null || "".equals(tableName)) {
            throw new Exception(className + ": tableName is empty.");
        }
        model.setTableName(table.value());
        List<ColumnMap> list = new ArrayList<ColumnMap>();
        Field[] fields = model.getClass().getDeclaredFields();
        getColumnInfo(fields, list, model);
        Field[] superFields = model.getClass().getSuperclass().getDeclaredFields();
        getColumnInfo(superFields, list, model);
        if (list.size() == 0) {
            throw new Exception(className + ": column is empty.");
        }
        String primaryColumnKey = null, primaryFeildKey = null;
        for (ColumnMap map : list) {
            if (map.getPrimaryKey()) {
                if (primaryColumnKey == null && primaryFeildKey == null) {
                    primaryColumnKey = map.getColumnName();
                    primaryFeildKey = map.getFieldName();
                } else {
                    throw new Exception(className + ": primaryKey is repeat.");
                }
            }
        }
        return model;
    }

    public Object getColumnValue(String name) {
        Object value = null;
        Field[] fields = this.getClass().getDeclaredFields();
        Field[] superFields = this.getClass().getSuperclass().getDeclaredFields();
        for (Field field : fields) {
            if (name.equalsIgnoreCase(field.getName())) {
                try {
                    field.setAccessible(true);
                    value = field.get(this);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (value == null) {
            for (Field field : superFields) {
                if (name.equalsIgnoreCase(field.getName())) {
                    try {
                        field.setAccessible(true);
                        value = field.get(this);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return value;
    }

    @JSONField(serialize = false)
    private void getColumnInfo(Field[] fields, List<ColumnMap> list, Object object) {
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                ColumnMap columnMap = new ColumnMap();
                String name = column.value();
                if (name == null || "".equals(name)) {
                    name = field.getName();
                }
                columnMap.setColumnName(name);
                if (column.key()) {
                    columnMap.setPrimaryKey(true);
                } else {
                    columnMap.setPrimaryKey(false);
                }
                columnMap.setFieldName(field.getName());
                Object value = null;
                try {
                    field.setAccessible(true);
                    value = field.get(object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                columnMap.setColumnValue(value);
                list.add(columnMap);
            }
        }
    }

    @JSONField(serialize = false)
    public void clearThis() {
        this.pageNo = null;
        this.pageSize = null;
        this.columns = null;
        this.tableName = null;
        this.orderKey = null;
        this.orderType = null;
    }
}
