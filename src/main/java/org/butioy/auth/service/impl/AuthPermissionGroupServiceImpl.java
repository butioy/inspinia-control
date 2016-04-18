package org.butioy.auth.service.impl;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.butioy.auth.dao.IAuthPermissionGroupDao;
import org.butioy.auth.domain.AuthPermissionGroup;
import org.butioy.auth.service.IAuthPermissionGroupService;
import org.butioy.auth.service.IAuthPermissionService;
import org.butioy.framework.bean.KeyValue;
import org.butioy.framework.cons.CommonEnum;
import org.butioy.framework.exception.PersistenceException;
import org.butioy.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
public class AuthPermissionGroupServiceImpl implements IAuthPermissionGroupService {

    @Autowired
    private IAuthPermissionGroupDao authPermissionGroupDao;

    @Autowired
    private IAuthPermissionService authPermissionService;

    @Override
    public Integer getPrimaryKey(String id) {
        if( StringUtils.isNotBlank(id) && id.matches("\\d+$") ) {
            return Integer.valueOf(id);
        }
        return -1;
    }

    @Caching(
            evict = {@CacheEvict(value = "authUserPermissionGroupCache", allEntries = true),
                    @CacheEvict(value = "authUserPermissionGroupCache", allEntries = true)},
            put = {@CachePut(value = "authPermissionGroupCache", key = "#authPermissionGroup.id")}
    )
    @Override
    public AuthPermissionGroup save(AuthPermissionGroup authPermissionGroup) {
        authPermissionGroup.setAddTime(new Date());
        authPermissionGroup.setIsDelete(CommonEnum.DELETE.UN_DELETE.getCode());
        authPermissionGroupDao.insert(authPermissionGroup);
        return authPermissionGroup;
    }

    @Caching(
            evict = {@CacheEvict(value = "authUserPermissionGroupCache", allEntries = true),
                    @CacheEvict(value = "authUserPermissionGroupCache", allEntries = true),
                    @CacheEvict(value = "authPermissionGroupCache", allEntries = true)}
    )
    @Override
    public void batchSave(List<AuthPermissionGroup> authPermissionGroups) {
    }

    @Caching(
        evict = {@CacheEvict(value = "authUserPermissionGroupCache", allEntries = true),
                @CacheEvict(value = "authUserPermissionGroupCache", allEntries = true),
        },
        put = {@CachePut(value = "authPermissionGroupCache", key = "#authPermissionGroup.id")}
    )
    @Override
    public AuthPermissionGroup modify(AuthPermissionGroup authPermissionGroup) {
        if( null != authPermissionGroup && null != authPermissionGroup.getId() ) {
            AuthPermissionGroup dbGroup = getById(authPermissionGroup.getId());
            if( null != dbGroup ) {
                dbGroup.setName(authPermissionGroup.getName());
                dbGroup.setDepId(authPermissionGroup.getDepId());
                dbGroup.setModifyTime(new Date());
                dbGroup.setStatus(authPermissionGroup.getStatus());
                authPermissionGroupDao.update(authPermissionGroup);
            } else {
                throw new PersistenceException("数据库中没有对应记录");
            }
        } else {
            throw new PersistenceException("参数异常");
        }
        return authPermissionGroup;
    }

    @Caching(
        evict = {
            @CacheEvict(value = "authPermissionCache", allEntries = true),
            @CacheEvict(value = "authPermissionGroupCache", allEntries = true),
            @CacheEvict(value = "authUserPermissionGroupCache", allEntries = true)
        }
    )
    @Override
    public void deleteById(Integer id) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("perGroupId", id);
        authPermissionService.deleteByParam(params);
        authPermissionGroupDao.deleteById(id);
    }

    @Caching(
        evict = {
            @CacheEvict(value = "authPermissionCache", allEntries = true),
            @CacheEvict(value = "authPermissionGroupCache", allEntries = true),
            @CacheEvict(value = "authUserPermissionGroupCache", allEntries = true)
        }
    )
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

    @Caching(
        evict = {
            @CacheEvict(value = "authPermissionCache", allEntries = true),
            @CacheEvict(value = "authPermissionGroupCache", allEntries = true),
            @CacheEvict(value = "authUserPermissionGroupCache", allEntries = true)
        }
    )
    @Override
    public void deleteByParam(Map params) {
        if( null != params && !params.isEmpty() ) {
            Map<String, Object> param = Maps.newHashMap();
            if( params.keySet().contains("ids") && params.get("ids") != null ) {
                param.put("perGroupIds", params.get("ids"));
            } else {
                List<AuthPermissionGroup> groups = authPermissionGroupDao.findList(params);
                String perGroupIds = "";
                for( AuthPermissionGroup group : groups ) {
                    perGroupIds += "," + group.getId();
                }
                if( StringUtils.isNotBlank(perGroupIds) ) {
                    perGroupIds = perGroupIds.substring(1);
                    param.put("perGroupIds", perGroupIds);
                }
            }
            if( null != param && !param.isEmpty() ) {
                authPermissionService.deleteByParam(param);
            }
            authPermissionGroupDao.deleteByParam(params);
        }
    }

    @Cacheable(value = "authPermissionGroupCache", key = "#id")
    @Override
    public AuthPermissionGroup getById(Integer id) {
        return authPermissionGroupDao.findById(id);
    }

    @Override
    public List<AuthPermissionGroup> getList(Map map) {
        return authPermissionGroupDao.findList(map);
    }

    @Override
    public List<AuthPermissionGroup> getPermissionGroupList(Map params) {
        return authPermissionGroupDao.findPermissionGroupList(params);
    }

    @Override
    public List<KeyValue> getKVByDepartment(Integer departmentId) {
        return authPermissionGroupDao.findKVByDepartment(departmentId);
    }
}
