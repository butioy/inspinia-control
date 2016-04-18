package org.butioy.auth.dao;

import org.butioy.auth.domain.AuthUser;
import org.butioy.framework.base.BaseDao;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 11:15
 */
public interface IAuthUserDao extends BaseDao<Integer, AuthUser> {

    List<AuthUser> findByUserName(String userName);
}
