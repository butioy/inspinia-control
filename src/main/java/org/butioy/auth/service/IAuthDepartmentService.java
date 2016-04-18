package org.butioy.auth.service;

import org.butioy.auth.domain.AuthDepartment;
import org.butioy.auth.domain.AuthPermissionGroup;
import org.butioy.framework.base.BaseService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 11:23
 */
public interface IAuthDepartmentService extends BaseService<Integer, AuthDepartment> {

    List<AuthDepartment> getListByUserId( Integer userId, boolean isAdmin );

    List<AuthPermissionGroup> getPermissionGroupList(Integer userId, Integer depId, boolean isAdmin);

}
