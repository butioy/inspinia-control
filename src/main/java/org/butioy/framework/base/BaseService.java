package org.butioy.framework.base;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.butioy.framework.util.StringUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * service接口基类
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-30 18:34
 */
public interface BaseService<PK extends Serializable, T extends Object> {

    /**
     * 把字符串类型的主键转换成对应类型的主键
     * @param id 字符串类型的主键ID
     * @return 对应类型的主键ID
     */
    PK getPrimaryKey( String id );

    /**
     * 保存的对象信息
     * @param entity 保存的对象
     * @return 保存的对象信息
     */
    T save(T entity);

    /**
     * 批量保存对象信息
     * @param entities 批量保存的对象信息集合
     */
    void batchSave(List<T> entities);

    /**
     * 修改对象信息
     * @param entity 需要修改的对象信息
     * @return 修改后的对象信息
     */
    T modify(T entity);

    /**
     * 根据ID产出对象
     * @param id 删除的对象ID值
     */
    void deleteById(PK id);

    /**
     * 根据ID批量删除对象，多个ID之间使用“,”隔开
     * @param ids 批量删除的对象ID
     */
    default void deleteByIds( String ids ) {
        if( StringUtils.isNotBlank(ids) ) {
            ids = StringUtil.subStringComma(ids);
            if( ids.indexOf(",") == -1 ) {
                deleteById(getPrimaryKey(ids.trim()));
            } else {
                Map<String, Object> params = Maps.newHashMap();
                params.put("ids", ids.trim());
                deleteByParam(params);
            }
        }
    }

    /**
     * 根据条件删除对象
     * @param map 删除的对象条件
     */
    void deleteByParam(Map map);

    /**
     * 根据ID获取对象信息
     * @param id 对象的ID值
     * @return 获取的对象信息
     */
    T getById(PK id);

    /**
     * 根据条件获取对象信息集合
     * @param params 查询条件
     * @return 获取的对象信息集合
     */
    List<T> getList( Map params );
}
