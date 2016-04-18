package org.butioy.auth.service;

import org.butioy.auth.domain.AuthUser;
import org.butioy.framework.base.BaseService;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 11:23
 */
public interface IAuthUserService extends BaseService<Integer, AuthUser> {

    AuthUser getByUserName( String userName );

    void modifyFieldsById( AuthUser authUser );

    void modifyPassword(Integer id, String password);
}
