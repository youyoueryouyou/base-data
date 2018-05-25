package com.you.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * Created by shicz on 2018/5/18.
 */
public interface BaseMapper<M extends BaseModel, C extends BaseCondition>
{
    public int insert(M model);

    public int insertSelective(M model);

    public int updateByPrimaryKey(M model);
    
    public int updateByPrimaryKeySelective(M model);
    
    public int updateByCondition(@Param("record") M model, @Param("condition") C condition);
    
    public int updateByConditionSelective(@Param("record") M model, @Param("condition") C condition);
    
    public int deleteByPrimaryKey(M model);
    
    public int deleteByCondition(C condition);
    
    public BasePojo selectByPrimaryKey(M model);
    
    public List<BasePojo> selectByCondition(C condition);
    
    public int countByCondition(C condition);

    public List<Map> selectBySql(@Param("sql") String sql);
}
