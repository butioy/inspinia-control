package org.butioy.auth.service.impl;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.butioy.auth.dao.IAuthUserRoleDao;
import org.butioy.auth.domain.AuthUserRole;
import org.butioy.auth.service.IAuthUserRoleService;
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
public class AuthUserRoleServiceImpl implements IAuthUserRoleService {

    @Autowired
    private IAuthUserRoleDao authUserRoleDao;

    @Override
    public Integer getPrimaryKey(String id) {
        if( StringUtils.isNotBlank(id) && id.matches("\\d+$") ) {
            return Integer.valueOf(id);
        }
        return -1;
    }

    @Override
    public AuthUserRole save(AuthUserRole authUserRole) {
        authUserRoleDao.insert(authUserRole);
        return authUserRole;
    }

    @Override
    public void batchSave(List<AuthUserRole> authUserRoles) {
        authUserRoleDao.batchInsert(authUserRoles);
    }

    @Override
    public AuthUserRole modify(AuthUserRole authUserRole) {
        authUserRoleDao.update(authUserRole);
        return authUserRole;
    }

    @Override
    public void deleteById(Integer id) {
        authUserRoleDao.deleteById(id);
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
            authUserRoleDao.deleteByParam(params);
        }
    }

    @Override
    public AuthUserRole getById(Integer id) {
        return authUserRoleDao.findById(id);
    }

    @Override
    public List<AuthUserRole> getList(Map map) {
        return authUserRoleDao.findList(map);
    }
}
