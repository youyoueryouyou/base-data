package com.you.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.TreeMap;

/**
 * @author shicz
 */
public class BasePojo extends TreeMap<String, Object> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BasePojo setProperty(String key , Object value){
        this.put(key, value);
        return this ;
    }

    public <T extends BaseModel> T getPojo(T model){
        Field[] fields = model.getClass().getDeclaredFields();
        initValue(fields,model);
        Field[] superFields = model.getClass().getSuperclass().getDeclaredFields();
        initValue(superFields,model);
        return model;
    }


    private void initValue(Field[] fields,Object object){
        for (Field field : fields){
            Column column = field.getAnnotation(Column.class);
            String name = field.getName();
            if (column != null){
                String columnKey = column.value();
                if (columnKey != null && !"".equals(columnKey)){
                    name = columnKey;
                }
            }
            if (containsKey(name)){
                Object value = get(name);
                try {
                    field.setAccessible(true);
                    field.set(object,value);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}

