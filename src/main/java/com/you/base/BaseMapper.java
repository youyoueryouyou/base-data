package com.you.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * @author shicz
 */
public interface BaseMapper<M extends BaseModel, C extends BaseCondition>
{
    /***
     * 插入数据
     * @param model
     * @return int
     */
    public int insert(M model);

    /***
     * 选择性插入数据
     * @param model
     * @return int
     */
    public int insertSelective(M model);

    /***
     * 根据主键更新数据
     * @param model
     * @return int
     */
    public int updateByPrimaryKey(M model);

    /***
     * 根据主键选择性更新数据
     * @param model
     * @return int
     */
    public int updateByPrimaryKeySelective(M model);

    /***
     * 根据动态条件更新数据
     * @param model
     * @param condition
     * @return int
     */
    public int updateByCondition(@Param("record") M model, @Param("condition") C condition);

    /***
     * 根据动态条件选择性更新数据
     * @param model
     * @param condition
     * @return int
     */
    public int updateByConditionSelective(@Param("record") M model, @Param("condition") C condition);

    /***
     * 根据主键删除数据
     * @param model
     * @return int
     */
    public int deleteByPrimaryKey(M model);

    /***
     * 根据动态条件删除数据
     * @param condition
     * @return int
     */
    public int deleteByCondition(C condition);

    /**
     * 根据主键查询数据
     * @param model
     * @return BasePojo
     */
    public BasePojo selectByPrimaryKey(M model);

    /***
     * 根据动态条件查询数据
     * @param condition
     * @return List<BasePojo>
     */
    public List<BasePojo> selectByCondition(C condition);

    /***
     * 根据动态条件查询数据总数
     * @param condition
     * @return List<BasePojo>
     */
    public int countByCondition(C condition);

    /***
     * 根据sql查询数据
     * @param sql
     * @return List<Map>
     */
    public List<Map> selectBySql(@Param("sql") String sql);
}
