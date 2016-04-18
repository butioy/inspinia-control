package org.butioy.auth.service.impl;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.butioy.auth.dao.IAuthRolePermissionDao;
import org.butioy.auth.domain.AuthRolePermission;
import org.butioy.auth.service.IAuthRolePermissionService;
import org.butioy.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 11:25
 */
@Service
public class AuthRolePermissionServiceImpl implements IAuthRolePermissionService {

    @Autowired
    private IAuthRolePermissionDao authRolePermissionDao;

    @Override
    public Integer getPrimaryKey(String id) {
        if( StringUtils.isNotBlank(id) && id.matches("\\d+$") ) {
            return Integer.valueOf(id);
        }
        return -1;
    }

    @Override
    public AuthRolePermission save(AuthRolePermission authRolePermission) {
        authRolePermissionDao.insert(authRolePermission);
        return authRolePermission;
    }

    @Override
    public void batchSave(List<AuthRolePermission> authRolePermissions) {
    }

    @Override
    public AuthRolePermission modify(AuthRolePermission authRolePermission) {
        authRolePermissionDao.update(authRolePermission);
        return authRolePermission;
    }

    @Override
    public void deleteById(Integer id) {
        authRolePermissionDao.deleteById(id);
    }

    @Override
    public void deleteByIds(String ids) {
        if( StringUtils.isNotBlank(ids) ) {
            ids = StringUtil.subStringComma(ids);
            if( ids.indexOf(",") == -1 ) {
                deleteById(Integer.valueOf(ids.trim()));
            } else {
                Map<String, Object> params = Maps.newHashMap();
                params.put("ids", ids.trim());
                deleteByParam(params);
            }
        }
    }

    @Override
    public void deleteByParam(Map params) {
        if( null != params && !params.isEmpty() ) {
            authRolePermissionDao.deleteByParam(params);
        }
    }

    @Override
    public AuthRolePermission getById(Integer id) {
        return authRolePermissionDao.findById(id);
    }

    @Override
    public List<AuthRolePermission> getList(Map map) {
        return authRolePermissionDao.findList(map);
    }

    @Override
    public List<AuthRolePermission> getDepartmentList(Map map) {
        return authRolePermissionDao.findDepartmentList(map);
    }

    @Override
    public List<AuthRolePermission> getPermissionGroupList(Map map) {
        return authRolePermissionDao.findPermissionGroupList(map);
    }
}
