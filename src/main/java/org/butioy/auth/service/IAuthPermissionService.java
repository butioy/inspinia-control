package org.butioy.auth.service;

import org.butioy.auth.domain.AuthPermission;
import org.butioy.framework.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 11:23
 */
public interface IAuthPermissionService extends BaseService<Integer, AuthPermission> {

    List<AuthPermission> getAllInfoList(Map map);

    List<AuthPermission> getListByPermGroup( Map map );

}
