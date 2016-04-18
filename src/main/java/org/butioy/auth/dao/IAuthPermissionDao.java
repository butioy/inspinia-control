package org.butioy.auth.dao;

import org.butioy.auth.domain.AuthPermission;
import org.butioy.framework.base.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 11:15
 */
public interface IAuthPermissionDao extends BaseDao<Integer, AuthPermission> {

    List<AuthPermission> findAllInfoList(Map map);

    List<AuthPermission> findListByPermGroup(Map map);

}
