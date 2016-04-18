package org.butioy.framework.interceptor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.butioy.auth.domain.AuthDepartment;
import org.butioy.auth.domain.AuthPermission;
import org.butioy.auth.domain.AuthPermissionGroup;
import org.butioy.auth.domain.AuthUser;
import org.butioy.auth.service.IAuthDepartmentService;
import org.butioy.auth.service.IAuthPermissionService;
import org.butioy.framework.cons.CommonEnum;
import org.butioy.framework.cons.Constants;
import org.butioy.framework.util.ParamUtils;
import org.butioy.framework.util.PropertiesUtils;
import org.butioy.framework.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 权限过滤器
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-18 20:26
 */
public class AdminPermissionInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(AdminPermissionInterceptor.class);

    private static PropertiesUtils UTILS = null;

    @Autowired
    private IAuthDepartmentService authDepartmentService;
    @Autowired
    private IAuthPermissionService authPermissionService;

    static {
        try {
            UTILS = new PropertiesUtils("permission.properties");
        } catch (Exception e) {
            LOG.error("加载配置文件【permission.properties】失败", e);
            UTILS = null;
            e.printStackTrace();
        }
    }

    /**
     * 在业务处理器处理请求之前被调用
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
     * @param request
     * @param response
     * @param handler
     * @return
     *      如果返回false
     *          从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     *      如果返回true
     *         执行下一个拦截器,直到所有的拦截器都执行完毕
     *         再执行被拦截的Controller
     *         然后进入拦截器链,
     *         从最后一个拦截器往回执行所有的postHandle()
     *         接着再从最后一个拦截器往回执行所有的afterCompletion()
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Integer authPermId = ParamUtils.getIntParameter(request, Constants.AUTH_PERM_PARAM_KEY, 0);
        if( authPermId > 0 ) {
            request.setAttribute(Constants.AUTH_PERM_PARAM_KEY, authPermId);
        }
        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据，比如当前时间
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之
     * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操
     * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，这跟Struts2里面的拦截器的执行过程有点像，
     * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
     * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，要在Interceptor之后调用的内容都写在调用invoke方法之后。
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Object obj = WebUtils.getSessionValue(request, Constants.LOGIN_SESSION_USER_KEY);
        boolean isExcludeUrl = isExcludeUrl(request);
        if( !isExcludeUrl && null != modelAndView ) {
            if( null == obj ) {
                String loginUrl = getPropertiesValue("redirect_login");
                if( StringUtils.isBlank(loginUrl) ) {
                    loginUrl = "/admin/login";
                }
//                response.sendRedirect(request.getContextPath() + loginUrl);
                modelAndView = new ModelAndView("redirect:"+loginUrl);
//                request.getRequestDispatcher(loginUrl).forward(request, response);
//                response.sendRedirect(loginUrl);
//                return;
            } else {
                AuthUser user = (AuthUser) obj;
                boolean isAdmin = false;
                if( user.getUserType() == CommonEnum.USER_TYPE.SUPER.getCode() ) {
                    isAdmin = true;
                }
                List<AuthDepartment> departments = getDepartments(user.getId(), isAdmin);
                Map<String, Object> map = Maps.newHashMap();
                map.put(Constants.DEPARTMENT_LIST_KEY, departments);
                AuthDepartment currDep = (AuthDepartment) WebUtils.getSessionValue(request, Constants.CURRENT_DEPARTMENT_KEY);
                Integer authPermId = ParamUtils.getIntParameter(request, Constants.AUTH_PERM_PARAM_KEY, 0);
                AuthPermission permission = null;
                if( authPermId > 0 ) {
                    permission = authPermissionService.getById(authPermId);
                } else {
                    permission = getPermissionByURL(request);
                }
                if( null != currDep ) {
                    List<AuthPermissionGroup> permissionGroups = getPermGroups(user.getId(), currDep.getId(), isAdmin);
                    Map<String, Object> params = Maps.newHashMap();
                    params.put("userId", user.getId());
                    params.put("status", CommonEnum.STATUS.ENABLED.getCode());
                    params.put("isDelete", CommonEnum.DELETE.UN_DELETE.getCode());
                    params.put("type", CommonEnum.PERMISSION_TYPE.MENU.getCode());
                    for( AuthPermissionGroup group : permissionGroups ) {
                        if( null != group ) {
                            params.put("perGroupId", group.getId());
                            List<AuthPermission> permissions = Lists.newArrayList();
                            if( isAdmin ) {
                                permissions = authPermissionService.getList(params);
                            } else {
                                permissions = authPermissionService.getListByPermGroup(params);
                            }
                            group.setPermissions(permissions);
                        }
                    }
                    map.put(Constants.MENU_LIST_KEY, permissionGroups);
                    if( null != permission ) {
                        WebUtils.setSession(request, Constants.CURRENT_PERM_KEY, permission);
                        for( AuthPermissionGroup group : permissionGroups ) {
                            if( group.getId().intValue() == permission.getPerGroupId().intValue() ) {
                                WebUtils.setSession(request, Constants.CURRENT_MENU_KEY, group);
                                break;
                            }
                        }
                    }
                }
                modelAndView.addAllObjects(map);
            }
        }
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，
     * 也就是DispatcherServlet渲染了视图执行，
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
     * @param request
     * @param response
     * @param handler
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
    }

    /**
     * 当前请求的URI是否包含在不需要拦截的URL中
     * @param request
     * @return true : 是   false : 否
     */
    private boolean isExcludeUrl( HttpServletRequest request ) {
        boolean result = false;
        String uri = WebUtils.getURI(request);
        String excludeUrlStr = getPropertiesValue("exclude_urls");
        if( StringUtils.isNotBlank(excludeUrlStr) ) {
            String[] excludeUrls = StringUtils.split(excludeUrlStr, ",");
            for( String url : excludeUrls ) {
                if( uri.indexOf(url) != -1 ) {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * 获取属性配置文件中的值
     * @param key 对应的key名称
     * @return
     */
    private String getPropertiesValue( String key ) {
        if( null != UTILS && StringUtils.isNotBlank(key) ) {
            return UTILS.getValue(key);
        }
        return null;
    }

    /**
     * 获取当前登录用户的部门集合
     * @param userId 当前登录用户ID
     * @return
     */
    private List<AuthDepartment> getDepartments( Integer userId, boolean isAdmin ) {
        return authDepartmentService.getListByUserId(userId, isAdmin);
    }

    /**
     * 获取当前登录用户已选择的部门所拥有的权限组集合
     * @param userId 当前登录用户ID
     * @param depId 当前选择的部门ID
     * @return
     */
    private List<AuthPermissionGroup> getPermGroups( Integer userId, Integer depId, boolean isAdmin ) {
        return authDepartmentService.getPermissionGroupList(userId, depId, isAdmin);
    }

    /**
     * 判断请求是否允许匿名访问
     * @param request
     * @return true : 允许   false : 不允许
     */
    private boolean isAnonymous( HttpServletRequest request ) {
        boolean result = false;
        AuthPermission permission = getPermissionByURL(request);
        return result;
    }

    /**
     * 根据当前请求的url获取permission对象
     * @param request
     * @return
     */
    public AuthPermission getPermissionByURL( HttpServletRequest request ) {
        AuthPermission permission = null;
        String uri = WebUtils.getURI(request);
        if( uri.indexOf(Constants.AUTH_PERM_PARAM_KEY) != -1 ) {
            uri = uri.substring(0, uri.lastIndexOf(Constants.AUTH_PERM_PARAM_KEY)-1);
        }
        if( StringUtils.isNotBlank(uri) ) {
            Map<String, Object> params = Maps.newHashMap();
            params.put("url", uri);
            params.put("status", CommonEnum.STATUS.ENABLED.getCode());
            params.put("isDelete", CommonEnum.DELETE.UN_DELETE.getCode());
            List<AuthPermission> list = authPermissionService.getList(params);
            if( null != list && !list.isEmpty() ) {
                permission = list.get(0);
            }
        }
        return permission;
    }

}
