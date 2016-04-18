package org.butioy.auth.controller;

import org.butioy.auth.domain.AuthPermission;
import org.butioy.auth.domain.AuthPermissionGroup;
import org.butioy.auth.domain.AuthUser;
import org.butioy.auth.service.IAuthPermissionGroupService;
import org.butioy.auth.service.IAuthPermissionService;
import org.butioy.framework.base.BaseController;
import org.butioy.framework.cons.Constants;
import org.butioy.framework.util.ParamUtils;
import org.butioy.framework.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-12 20:40
 */
public class BaseAuthController extends BaseController {

    @Autowired
    protected IAuthPermissionGroupService authPermissionGroupService;

    @Autowired
    protected IAuthPermissionService authPermissionService;

    /**
     * 获取系统当前登录用户
     * @param request
     * @return
     */
    protected AuthUser getLoginUser( HttpServletRequest request ) {
        Object obj = WebUtils.getSessionValue(request, Constants.LOGIN_SESSION_USER_KEY);
        return null != obj ? (AuthUser) obj : null;
    }

    /**
     * 判断用户是否登录系统
     * @param request
     * @return true: 已登录， false: 未登录或已过期
     */
    protected boolean isLogin( HttpServletRequest request ) {
        Boolean result = false;
        AuthUser user = getLoginUser(request);
        if( null != user ) {
            result = true;
        }
        return result;
    }


    /**
     * 移除session中所有与权限相关的属性
     * @param request
     */
    protected void removeAllAuthSession( HttpServletRequest request ) {
        WebUtils.removeAllAuthSession(request);
    }

    protected void setAuthSession( HttpServletRequest request ) {
        Integer permGroupId = ParamUtils.getIntParameter(request, "permGroupId", 0);
        Integer permId = ParamUtils.getIntParameter(request, "permId", 0);
        AuthPermissionGroup curr_menu = authPermissionGroupService.getById(permGroupId);
        AuthPermission curr_perm = authPermissionService.getById(permId);
        setSession(request, Constants.CURRENT_MENU_KEY, curr_menu);
        setSession(request, Constants.CURRENT_PERM_KEY, curr_perm);
    }

}
