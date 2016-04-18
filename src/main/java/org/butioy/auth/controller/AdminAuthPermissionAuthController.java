package org.butioy.auth.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.butioy.auth.domain.AuthDepartment;
import org.butioy.auth.domain.AuthPermission;
import org.butioy.auth.domain.AuthUser;
import org.butioy.auth.service.IAuthDepartmentService;
import org.butioy.auth.service.IAuthPermissionService;
import org.butioy.framework.bean.KeyValue;
import org.butioy.framework.cons.CommonEnum;
import org.butioy.framework.exception.PersistenceException;
import org.butioy.framework.resp.RespBean;
import org.butioy.framework.util.ParamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 11:43
 */
@Controller
@RequestMapping("/admin/auth/permission")
public class AdminAuthPermissionAuthController extends BaseAuthController {

    private static Logger LOG = LoggerFactory.getLogger(AdminAuthPermissionAuthController.class);

    @Autowired
    private IAuthPermissionService authPermissionService;
    @Autowired
    private IAuthDepartmentService authDepartmentService;

    /**
     * 部门首页
     * @param model
     * @param request
     * @return
     */
    @RequestMapping
    public String index( ModelMap model, HttpServletRequest request ) {
        AuthUser user = getLoginUser(request);
        setAuthSession(request);
        String result = "/admin/auth/permission/index";
        if( null == user ) {
            result = "/admin/login";
        } else {
            List<AuthDepartment> departments = Lists.newArrayList();
            Map<String, Object> params = Maps.newHashMap();
//            params.put("status", AuthCons.STATUS.ENABLED.getCode());
            params.put("isDelete", CommonEnum.DELETE.UN_DELETE.getCode());
            departments = authDepartmentService.getList(params);
            model.put("departments", departments);
        }
        return result;
    }

    @RequestMapping("/toAdd")
    public String toAdd( ModelMap model, HttpServletRequest request ) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("status", CommonEnum.STATUS.ENABLED.getCode());
        params.put("isDelete", CommonEnum.DELETE.UN_DELETE.getCode());
        List<AuthDepartment> departments = authDepartmentService.getList(params);
        model.put("departments", departments);
        return "/admin/auth/permission/add";
    }

    @RequestMapping("/doAdd")
    @ResponseBody
    public RespBean doAdd( AuthPermission permission, HttpServletRequest request ) {
        RespBean result = new RespBean();
        try {
            AuthUser user = getLoginUser(request);
            if( null != user ) {
                if( null != permission && StringUtils.isNotBlank(permission.getName()) && StringUtils.isNotBlank(permission.getUrl()) ) {
                    permission.setAddUserId(user.getId());
                    authPermissionService.save(permission);
                    result.setStatus(RespBean.SUCCESS);
                } else {
                    result.setFailMessage("参数错误，请重试");
                }
            } else {
                result.setFailMessage("用户未登录或登录已过期，请重新登录");
            }
        } catch (Exception e) {
            LOG.error("新增权限错误， 错误信息 : " + e.getMessage());
            result.setErrorMessage("后台错误，请稍候重试");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/{id}/edit")
    public String edit( @PathVariable Integer id, ModelMap model ) {
        if( null != id ) {
            AuthPermission permission = authPermissionService.getById(id);
            Map<String, Object> params = Maps.newHashMap();
            params.put("status", CommonEnum.STATUS.ENABLED.getCode());
            params.put("isDelete", CommonEnum.DELETE.UN_DELETE.getCode());
            List<AuthDepartment> departments = authDepartmentService.getList(params);

            if( null != permission.getDepId() ) {
                List<KeyValue> permissionGroups = authPermissionGroupService.getKVByDepartment(permission.getDepId());
                model.put("permissionGroups", permissionGroups);
            }
            model.put("permission", permission);
            model.put("departments", departments);
        }
        return "/admin/auth/permission/edit";
    }

    @RequestMapping("/doEdit")
    @ResponseBody
    public RespBean doEdit( AuthPermission permission ) {
        RespBean result = new RespBean();
        try {
            authPermissionService.modify(permission);
            result.setStatus(RespBean.SUCCESS);
        } catch ( PersistenceException e) {
            result.setFailMessage(e.getMessage());
        } catch (Exception e) {
            LOG.error("修改权限错误， 错误信息 : " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/batchRemove")
    @ResponseBody
    public RespBean batchRemove( HttpServletRequest request ) {
        RespBean result = new RespBean();
        boolean isLogin = isLogin(request);
        if( isLogin ) {
            String ids = ParamUtils.getParameter(request, "ids", "");
            if( StringUtils.isNotBlank(ids) ) {
                try {
                    authPermissionService.deleteByIds(ids);
                    result.setStatus(RespBean.SUCCESS);
                } catch (Exception e) {
                    result.setErrorMessage("系统错误，请稍候重试");
                    e.printStackTrace();
                }
            } else {
                result.setFailMessage("请选择需要删除的权限");
            }
        } else {
            result.setFailMessage("用户未登录或登录已过期，请重新登录");
        }
        return result;
    }

}
