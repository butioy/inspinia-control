package org.butioy.auth.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.butioy.auth.dao.IAuthRoleDao;
import org.butioy.auth.domain.AuthPermission;
import org.butioy.auth.domain.AuthRole;
import org.butioy.auth.domain.AuthRolePermission;
import org.butioy.auth.service.IAuthPermissionService;
import org.butioy.auth.service.IAuthRolePermissionService;
import org.butioy.auth.service.IAuthRoleService;
import org.butioy.auth.service.IAuthUserRoleService;
import org.butioy.framework.cons.CommonEnum;
import org.butioy.framework.exception.PersistenceException;
import org.butioy.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
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
public class AuthRoleServiceImpl implements IAuthRoleService {

    @Autowired
    private IAuthRoleDao authRoleDao;

    @Autowired
    private IAuthUserRoleService authUserRoleService;
    @Autowired
    private IAuthRolePermissionService authRolePermissionService;
    @Autowired
    private IAuthPermissionService authPermissionService;

    @Override
    public Integer getPrimaryKey(String id) {
        if( StringUtils.isNotBlank(id) && id.matches("\\d+$") ) {
            return Integer.valueOf(id);
        }
        return -1;
    }

    @Override
    public AuthRole save(AuthRole authRole) {
        authRole.setAddTime(new Date());
        authRole.setIsDelete(CommonEnum.DELETE.UN_DELETE.getCode());
        authRoleDao.insert(authRole);
        return authRole;
    }

    @Override
    public void batchSave(List<AuthRole> authRoles) {
    }

    @Override
    public AuthRole modify(AuthRole authRole) {
        if( null != authRole && null != authRole.getId() && null != authRole.getName() ) {
            AuthRole dbRole = getById(authRole.getId());
            if( null != dbRole ) {
                dbRole.setName(authRole.getName());
                dbRole.setStatus(authRole.getStatus());
                dbRole.setModifyTime(new Date());
                authRoleDao.update(authRole);
            } else {
                throw new PersistenceException("该角色不存在或已被删除");
            }
        } else {
            throw new PersistenceException("参数错误");
        }
        return authRole;
    }

    @Override
    public void deleteById(Integer id) {
        Map param = Maps.newHashMap();
        param.put("roleId", id);
        authUserRoleService.deleteByParam(param);
        authRolePermissionService.deleteByParam(param);
        authRoleDao.deleteById(id);
    }

    @Override
    public void deleteByIds(String ids) {
        if( StringUtils.isNotBlank(ids) ) {
            ids = StringUtil.subStringComma(ids);
            if( ids.indexOf(",") == -1 ) {
                deleteById(Integer.valueOf(ids.trim()));
            } else {
                Map params = Maps.newHashMap();
                params.put("ids", ids.trim());
                deleteByParam(params);
            }
        }
    }

    @Override
    public void deleteByParam(Map params) {
        if( null != params && !params.isEmpty() ) {
            Map<String, Object> param = Maps.newHashMap();
            if( params.keySet().contains("ids") && params.get("ids") != null ) {
                param.put("roleIds", params.get("ids"));
            } else {
                List<AuthRole> roles= authRoleDao.findList(params);
                String roleIds = "";
                for( AuthRole role : roles ) {
                    roleIds += "," + role.getId();
                }
                if( StringUtils.isNotBlank(roleIds) ) {
                    roleIds = roleIds.substring(1);
                    param.put("roleIds", roleIds);
                }
            }
            if( !param.isEmpty() ) {
                authUserRoleService.deleteByParam(param);
                authRolePermissionService.deleteByParam(param);
            }
            authRoleDao.deleteByParam(params);
        }
    }

    @Override
    public AuthRole getById(Integer id) {
        return authRoleDao.findById(id);
    }

    @Override
    public List<AuthRole> getList(Map map) {
        return authRoleDao.findList(map);
    }

    @Caching(evict = {
        @CacheEvict(value = "authUserDepartmentCache", allEntries = true),
        @CacheEvict(value = "authUserPermissionGroupCache", allEntries = true)
    })
    @Override
    public void savePermission(Integer roleId, String menuIds, String tagIds) {
        if( roleId != null && roleId > 0 ) {
            Map param = Maps.newHashMap();
            param.put("roleId", roleId);
            authRolePermissionService.deleteByParam(param);
            param = Maps.newHashMap();
            param.put("status", CommonEnum.STATUS.ENABLED.getCode());
            param.put("isDelete", CommonEnum.DELETE.UN_DELETE.getCode());
            List<AuthPermission> list = Lists.newArrayList();
            if( StringUtils.isNotBlank(menuIds) ) {
                menuIds = StringUtil.subStringComma(menuIds);
                param = Maps.newHashMap();
                param.put("ids", menuIds);
                param.put("type", CommonEnum.PERMISSION_TYPE.MENU.getCode());
                List<AuthPermission> menuList = authPermissionService.getList(param);
                if( null != menuList && menuList.size() > 0 ) {
                    list.addAll(menuList);
                }
            }
            if( StringUtils.isNotBlank(tagIds) ) {
                tagIds = StringUtil.subStringComma(tagIds);
                param = Maps.newHashMap();
                param.put("ids", tagIds);
                param.put("type", CommonEnum.PERMISSION_TYPE.TAG.getCode());
                List<AuthPermission> tagList = authPermissionService.getList(param);
                if( null != tagList && tagList.size() > 0 ) {
                    list.addAll(tagList);
                }
            }

            for( AuthPermission permission : list ) {
                AuthRolePermission rolePermission = new AuthRolePermission();
                rolePermission.setPermissionId(permission.getId());
                rolePermission.setDepartmentId(permission.getDepId());
                rolePermission.setPermissionGroupId(permission.getPerGroupId());
                rolePermission.setRoleId(roleId);
                authRolePermissionService.save(rolePermission);
            }
        } else {
            throw new PersistenceException("参数错误，请重试");
        }
    }
}
