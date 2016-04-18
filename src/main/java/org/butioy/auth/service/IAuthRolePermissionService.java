package org.butioy.auth.service;

import org.butioy.auth.domain.AuthRolePermission;
import org.butioy.framework.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 11:23
 */
public interface IAuthRolePermissionService extends BaseService<Integer, AuthRolePermission> {

    List<AuthRolePermission> getDepartmentList( Map map );

    List<AuthRolePermission> getPermissionGroupList( Map map );

}
