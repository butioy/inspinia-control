package org.butioy.auth.dao;

import org.butioy.auth.domain.AuthRolePermission;
import org.butioy.framework.base.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 11:15
 */
public interface IAuthRolePermissionDao extends BaseDao<Integer, AuthRolePermission> {

    List<AuthRolePermission> findDepartmentList(Map map);

    List<AuthRolePermission> findPermissionGroupList(Map map);
}
