package org.butioy.auth.service.impl;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.butioy.auth.dao.IAuthPermissionDao;
import org.butioy.auth.domain.AuthPermission;
import org.butioy.auth.service.IAuthPermissionService;
import org.butioy.auth.service.IAuthRolePermissionService;
import org.butioy.framework.exception.PersistenceException;
import org.butioy.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 11:25
 */
@Service
public class AuthPermissionServiceImpl implements IAuthPermissionService {

    @Autowired
    private IAuthPermissionDao authPermissionDao;

    @Autowired
    private IAuthRolePermissionService authRolePermissionService;

    @Override
    public Integer getPrimaryKey(String id) {
        if( StringUtils.isNotBlank(id) && id.matches("\\d+$") ) {
            return Integer.valueOf(id);
        }
        return -1;
    }

    @Override
    public AuthPermission save(AuthPermission authPermission) {
        authPermission.setAddTime(new Date());
        authPermissionDao.insert(authPermission);
        return authPermission;
    }

    @Override
    public void batchSave(List<AuthPermission> authPermissions) {
    }

    @CachePut(value = "authPermissionCache", key = "#authPermission.id")
    @Override
    public AuthPermission modify(AuthPermission authPermission) {
        AuthPermission dbPermission = new AuthPermission();
        if( null != authPermission && null != authPermission.getId() ) {
            dbPermission = getById(authPermission.getId());
            dbPermission.setName(authPermission.getName());
            dbPermission.setUrl(authPermission.getUrl());
            dbPermission.setStatus(authPermission.getStatus());
            dbPermission.setDepId(authPermission.getDepId());
            dbPermission.setPerGroupId(authPermission.getPerGroupId());
            dbPermission.setType(authPermission.getType());
            dbPermission.setRemark(authPermission.getRemark());
            dbPermission.setModifyTime(new Date());
            authPermissionDao.update(dbPermission);
        } else {
            throw new PersistenceException("参数错误");
        }
        return dbPermission;
    }

    @CacheEvict(value = "authPermissionCache", key = "#id")
    @Override
    public void deleteById(Integer id) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("permissionId", id);
        authRolePermissionService.deleteByParam(params);
        authPermissionDao.deleteById(id);
    }

    @CacheEvict(value = "authPermissionCache", allEntries = true)
    @Override
    public void deleteByIds(String ids) {
        ids = StringUtil.subStringComma(ids);
        if( StringUtils.isNotBlank(ids) ) {
            if( ids.indexOf(",") == -1 ) {
                deleteById(Integer.valueOf(ids.trim()));
            } else {
                Map<String, Object> params = Maps.newHashMap();
                params.put("ids", ids.trim());
                deleteByParam(params);
            }
        }
    }

    @CacheEvict(value = "authPermissionCache", allEntries = true)
    @Override
    public void deleteByParam(Map params) {
        if( null != params && !params.isEmpty() ) {
            Map<String, Object> param = Maps.newHashMap();
            if( params.keySet().contains("ids") && params.get("ids") != null ) {
                param.put("permissionIds", params.get("ids"));
            } else {
                List<AuthPermission> permissions = authPermissionDao.findList(params);
                String permissionIds = "";
                for( AuthPermission permission : permissions ) {
                    permissionIds += "," + permission.getId();
                }
                if( StringUtils.isNotBlank(permissionIds) ) {
                    permissionIds = permissionIds.substring(1);
                    param.put("permissionIds", permissionIds);
                }
            }
            if( !param.isEmpty() ) {
                authRolePermissionService.deleteByParam(param);
            }
            authPermissionDao.deleteByParam(params);
        }
    }

    @Cacheable(value = "authPermissionCache", key = "#id")
    @Override
    public AuthPermission getById(Integer id) {
        return authPermissionDao.findById(id);
    }

    @Override
    public List<AuthPermission> getList(Map map) {
        return authPermissionDao.findList(map);
    }

    @Override
    public List<AuthPermission> getAllInfoList(Map map) {
        return authPermissionDao.findAllInfoList(map);
    }

    @Override
    public List<AuthPermission> getListByPermGroup( Map map ) {
        return authPermissionDao.findListByPermGroup(map);
    }
}
