package org.butioy.auth.dao;

import org.butioy.auth.domain.AuthPermissionGroup;
import org.butioy.framework.base.BaseDao;
import org.butioy.framework.bean.KeyValue;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 11:15
 */
public interface IAuthPermissionGroupDao extends BaseDao<Integer, AuthPermissionGroup> {

    List<AuthPermissionGroup> findPermissionGroupList(Map params);

    List<KeyValue> findKVByDepartment( Integer departmentId );

}
