package org.butioy.auth.service.impl;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.butioy.auth.dao.IAuthUserDao;
import org.butioy.auth.domain.AuthUser;
import org.butioy.auth.service.IAuthUserService;
import org.butioy.framework.cons.CommonEnum;
import org.butioy.framework.exception.PersistenceException;
import org.butioy.framework.util.MD5EncryptUtils;
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
public class AuthUserServiceImpl implements IAuthUserService {

    @Autowired
    private IAuthUserDao authUserDao;

    @Override
    public Integer getPrimaryKey(String id) {
        if( StringUtils.isNotBlank(id) && id.matches("\\d+$") ) {
            return Integer.valueOf(id);
        }
        return -1;
    }

    @CachePut(value = "authUserCache", key = "#authUser.userName")
    @Override
    public AuthUser save(AuthUser authUser) {
        //密码加密
        if( StringUtils.isNotBlank( authUser.getPassword() ) ) {
            String password = authUser.getPassword();
            password = MD5EncryptUtils.md5(password);
            authUser.setPassword(password);
        }
        authUser.setAddTime(new Date());
        authUser.setIsDelete(CommonEnum.DELETE.UN_DELETE.getCode());
        authUserDao.insert(authUser);
        return authUser;
    }

    @Override
    public void batchSave(List<AuthUser> authUsers) {
        authUserDao.batchInsert(authUsers);
    }

    @CacheEvict(value = "authUserCache", key = "#authUser.userName")
    @Override
    public AuthUser modify(AuthUser authUser) {
        if( null != authUser && null != authUser.getId() ) {
            AuthUser dbUser = getById(authUser.getId());
            if( null != dbUser ) {
                dbUser.setUserName(authUser.getUserName());
                dbUser.setFullName(authUser.getFullName());
                dbUser.setSex(authUser.getSex());
                dbUser.setBirthday(authUser.getBirthday());
                dbUser.setUserType(authUser.getUserType());
                dbUser.setHeadImg(authUser.getHeadImg());
                dbUser.setStatus(authUser.getStatus());
                dbUser.setRemark(authUser.getRemark());
                dbUser.setModifyTime(new Date());
                authUserDao.update(dbUser);
                return dbUser;
            } else {
                throw new PersistenceException("该用户不存在或已被删除");
            }
        } else {
            throw new PersistenceException("参数错误，请重试");
        }
    }

    @CacheEvict(value = "authUserCache", key = "#authUser.userName")
    @Override
    public void modifyFieldsById( AuthUser authUser ) {
        if( null != authUser && null != authUser.getId() ) {
            AuthUser dbUser = getById(authUser.getId());
            if( null == dbUser ) {
                throw new PersistenceException("该用户不存在或已被删除");
            }
            authUserDao.update(authUser);
            authUser.setUserName(dbUser.getUserName());
//            return authUser;
        } else {
            throw new PersistenceException("参数错误，请重试");
        }
    }

    @CacheEvict(value = "authUserCache", allEntries = true)
    @Override
    public void deleteById(Integer id) {
        authUserDao.deleteById(id);
    }

//    @CacheEvict(value = "authUserCache", allEntries = true)
//    @Override
//    public void deleteByIds(String ids) {
//    }

    @CacheEvict(value = "authUserCache", allEntries = true)
    @Override
    public void deleteByParam(Map params) {
        if( null != params && !params.isEmpty() ) {

        }
    }

    @Override
    public AuthUser getById(Integer id) {
        return authUserDao.findById(id);
    }

    @Override
    public List<AuthUser> getList(Map map) {
        return authUserDao.findList(map);
    }

    @Cacheable(value = "authUserCache", key = "#userName")
    @Override
    public AuthUser getByUserName(String userName) {
        List<AuthUser> list = authUserDao.findByUserName(userName);
        if( list.size() == 1 ) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void modifyPassword(Integer id, String password) {
        AuthUser user = new AuthUser();
        user.setId(id);
        String encryptPassword = MD5EncryptUtils.md5(password);
        user.setPassword(encryptPassword);
        modifyFieldsById( user );
    }
}
