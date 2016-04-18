package org.butioy.auth.service.impl;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.butioy.auth.dao.IAuthDepartmentDao;
import org.butioy.auth.domain.AuthDepartment;
import org.butioy.auth.domain.AuthPermissionGroup;
import org.butioy.auth.service.IAuthDepartmentService;
import org.butioy.auth.service.IAuthPermissionGroupService;
import org.butioy.auth.service.IAuthPermissionService;
import org.butioy.framework.cons.CommonEnum;
import org.butioy.framework.exception.PersistenceException;
import org.butioy.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 11:25
 */
@Service
public class AuthDepartmentServiceImpl implements IAuthDepartmentService {

    @Autowired
    private IAuthDepartmentDao authDepartmentDao;

    @Autowired
    private IAuthPermissionGroupService authPermissionGroupService;
    @Autowired
    private IAuthPermissionService authPermissionService;

    @Override
    public Integer getPrimaryKey(String id) {
        if( StringUtils.isNotBlank(id) && id.matches("\\d+$") ) {
            return Integer.valueOf(id);
        }
        return -1;
    }

    @Caching(evict = {@CacheEvict(value = "authUserDepartmentCache", allEntries = true)})
    @Override
    public AuthDepartment save(AuthDepartment authDepartment) {
        authDepartment.setAddTime(new Date());
        authDepartment.setIsDelete(CommonEnum.DELETE.UN_DELETE.getCode());
        authDepartmentDao.insert(authDepartment);
        return authDepartment;
    }

    @Caching(evict = {@CacheEvict(value = "authUserDepartmentCache", allEntries = true)})
    @Override
    public void batchSave( List<AuthDepartment> authDepartments ) {
    }

    @Caching(evict = {@CacheEvict(value = "authUserDepartmentCache", allEntries = true)})
    @Override
    public AuthDepartment modify(AuthDepartment authDepartment) {
        System.out.println("蹦次修改数据================" + authDepartment.getName());
        AuthDepartment dbDepartment = new AuthDepartment();
        if( null != authDepartment && null != authDepartment.getId() && StringUtils.isNotBlank(authDepartment.getName()) && null != authDepartment.getStatus() ) {
            dbDepartment = getById(authDepartment.getId());
            dbDepartment.setName(authDepartment.getName());
            dbDepartment.setRemark(authDepartment.getRemark());
            dbDepartment.setStatus(authDepartment.getStatus());
            dbDepartment.setModifyTime(new Date());
            authDepartmentDao.update(dbDepartment);
        } else {
            throw new PersistenceException("参数错误，请重新操作");
        }
        return dbDepartment;
    }

    @Caching(evict = {@CacheEvict(value = "authUserDepartmentCache", allEntries = true)})
    @Override
    public void deleteById(Integer id) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("depId", id);
        authPermissionGroupService.deleteByParam(params);
        authDepartmentDao.deleteById(id);
    }

    @Caching(evict = {@CacheEvict(value = "authUserDepartmentCache", allEntries = true)})
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

    @Caching(evict = {@CacheEvict(value = "authUserDepartmentCache", allEntries = true)})
    @Override
    public void deleteByParam(Map params) {
        if( null != params && !params.isEmpty() ) {
            Map<String, Object> param = Maps.newHashMap();
            if( params.keySet().contains("ids") && params.get("ids") != null ) {
                param.put("depIds", params.get("ids"));
            } else {
                List<AuthDepartment> departments = authDepartmentDao.findList(params);
                String depIds = "";
                for( AuthDepartment department : departments ) {
                    depIds += "," + department.getId();
                }
                if( StringUtils.isNotBlank(depIds) ) {
                    depIds = depIds.substring(1);
                    param.put("depIds", depIds);
                }
            }
            if( !param.isEmpty() ) {
                authPermissionGroupService.deleteByParam(param);
            }
            authDepartmentDao.deleteByParam(params);
        }
    }

    @Override
    public AuthDepartment getById(Integer id) {
        return authDepartmentDao.findById(id);
    }

    @Override
    public List<AuthDepartment> getList(Map map) {
        return authDepartmentDao.findList(map);
    }

    @Cacheable(value = "authUserDepartmentCache", key = "#userId")
    @Override
    public List<AuthDepartment> getListByUserId(Integer userId, boolean isAdmin) {
        if( isAdmin ) {
            Map<String, Object> param = Maps.newHashMap();
            param.put("status", CommonEnum.STATUS.ENABLED.getCode());
            param.put("isDelete", CommonEnum.DELETE.UN_DELETE.getCode());
            return getList(param);
        }
        return authDepartmentDao.findListByUserId(userId);
    }

    @Cacheable(value = "authUserPermissionGroupCache", key = "#userId+#depId")
    @Override
    public List<AuthPermissionGroup> getPermissionGroupList(Integer userId, Integer depId, boolean isAdmin) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("depId", depId);
        if( isAdmin ) {
            params.put("status", CommonEnum.STATUS.ENABLED.getCode());
            params.put("isDelete", CommonEnum.DELETE.UN_DELETE.getCode());
            return authPermissionGroupService.getList(params);
        } else {
            params.put("userId", userId);
            return authPermissionGroupService.getPermissionGroupList(params);
        }
    }

}
