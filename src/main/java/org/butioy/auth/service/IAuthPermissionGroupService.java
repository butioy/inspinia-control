package org.butioy.auth.service;

import org.butioy.auth.domain.AuthPermissionGroup;
import org.butioy.framework.base.BaseService;
import org.butioy.framework.bean.KeyValue;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 11:23
 */
public interface IAuthPermissionGroupService extends BaseService<Integer,AuthPermissionGroup> {

    List<AuthPermissionGroup> getPermissionGroupList( Map params );

    List<KeyValue> getKVByDepartment( Integer departmentId );

}
