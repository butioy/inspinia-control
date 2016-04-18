package org.butioy.framework.base;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * dao层基类
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-30 18:28
 */
public interface BaseDao<PK extends Serializable, T extends Object> {

    /**
     * 插入对象信息
     * @param entity 插入的对象信息
     */
    PK insert( T entity );

    /**
     * 批量插入对象信息
     * @param entities 批量插入的对象信息集合
     */
    void batchInsert( @Param("list") List<T> entities );

    /**
     * 更新对象信息
     * @param entity 需要更新的对象信息
     */
    void update( T entity );

    /**
     * 根据ID删除对象信息
     * @param id 需要删除的ID
     */
    void deleteById( PK id );

    /**
     * 根据ID集合删除对象信息
     * @param ids 需要删除的ID，多个之间使用“,”隔开
     */
    void deleteByIds( String ids );

    /**
     * 根据条件批量删除对象信息
     * @param params 删除对象信息的条件
     */
    void deleteByParam( Map params );

    /**
     * 通过ID查找对象
     * @param id 查找的对象ID
     * @return 查找的对象信息
     */
    T findById( PK id );

    /**
     * 根据条件查找对象信息集合
     * @param params 查找信息的条件
     * @return 查找的对象信息集合
     */
    List<T> findList( Map params );

}
