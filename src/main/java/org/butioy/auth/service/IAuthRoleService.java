package org.butioy.auth.service;

import org.butioy.auth.domain.AuthRole;
import org.butioy.framework.base.BaseService;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 11:23
 */
public interface IAuthRoleService extends BaseService<Integer, AuthRole> {

    void savePermission(Integer roleId, String menuIds, String tagIds);
}
