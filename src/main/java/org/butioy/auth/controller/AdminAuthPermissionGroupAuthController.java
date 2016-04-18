package org.butioy.auth.controller;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.butioy.auth.domain.AuthDepartment;
import org.butioy.auth.domain.AuthPermission;
import org.butioy.auth.domain.AuthPermissionGroup;
import org.butioy.auth.domain.AuthUser;
import org.butioy.auth.service.IAuthDepartmentService;
import org.butioy.auth.service.IAuthPermissionGroupService;
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
@RequestMapping("/admin/auth/permissionGroup")
public class AdminAuthPermissionGroupAuthController extends BaseAuthController {

    private static Logger LOG = LoggerFactory.getLogger(AdminAuthPermissionGroupAuthController.class);

    @Autowired
    private IAuthPermissionGroupService authPermissionGroupService;
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
        Integer depId = ParamUtils.getIntParameter(request, "depId", 0);
//        Integer permGroupId = ParamUtils.getIntParameter(request, "permGroupId", 0);
//        Integer permId = ParamUtils.getIntParameter(request, "permId", 0);
        AuthUser user = getLoginUser(request);
        String result = "/admin/auth/department/dashboard";
        if( depId == 0 ) {
            result = "/404";
        } else if( null == user ) {
            result = "/admin/loginUser";
        }
        model.put("depId", depId);
        return result;
    }

    @RequestMapping("/getListByDepartment")
    public String getListByDepartment( ModelMap model, HttpServletRequest request ) {
        Integer depId = ParamUtils.getIntParameter(request, "depId", 0);
        if( depId > 0 ) {
            Map<String, Object> params = Maps.newHashMap();
            params.put("depId", depId);
            params.put("status", 1);
            params.put("isDelete", 0);
            List<AuthPermissionGroup> list = authPermissionGroupService.getList(params);
            model.put( "permissionGroups", list);
        }
        return "/admin/auth/permission/permissionGroups";
    }

    @RequestMapping("/toAdd")
    public String toAdd( ModelMap model, HttpServletRequest request ) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("status", CommonEnum.STATUS.ENABLED.getCode());
        params.put("isDelete", CommonEnum.DELETE.UN_DELETE.getCode());
        List<AuthDepartment> departments = authDepartmentService.getList(params);
        model.put("departments", departments);
        return "/admin/auth/permissionGroup/add";
    }

    @RequestMapping("/doAdd")
    @ResponseBody
    public RespBean doAdd( AuthPermissionGroup permissionGroup, HttpServletRequest request ) {
        RespBean result = new RespBean();
        try {
            AuthUser user = getLoginUser(request);
            if( null != user ) {
                if( null != permissionGroup && StringUtils.isNotBlank(permissionGroup.getName()) && null != permissionGroup.getStatus() ) {
                    permissionGroup.setAddUserId(user.getId());
                    authPermissionGroupService.save(permissionGroup);
                    result.setStatus(RespBean.SUCCESS);
                } else {
                    result.setFailMessage("参数错误，请重新操作");
                }
            } else {
                result.setFailMessage("用户未登录或登录已过期，请重新登录");
            }
        } catch (Exception e) {
            LOG.info("添加权限组失败，失败原因：" + e.getMessage());
            result.setErrorMessage("系统错误，请稍候重试");
        }
        return result;
    }

    @RequestMapping("/getKVListByDepartment")
    @ResponseBody
    public RespBean getKVListByDepartment( HttpServletRequest request ) {
        RespBean bean = new RespBean();
        Integer depId = ParamUtils.getIntParameter(request, "depId", 0);
        if( depId > 0 ) {
            Map<String, Object> params = Maps.newHashMap();
            params.put("status", CommonEnum.STATUS.ENABLED.getCode());
            params.put("isDelete", CommonEnum.DELETE.UN_DELETE.getCode());
            params.put("depId", depId);
            List<KeyValue> groups = authPermissionGroupService.getKVByDepartment(depId);
            bean.setStatus(RespBean.SUCCESS);
            bean.setData(groups);
        } else {
            bean.setFailMessage("参数错误！");
        }

        return bean;
    }

    @RequestMapping("/{id}/edit")
    public String edit( @PathVariable Integer id, ModelMap model, HttpServletRequest request ) {
        AuthPermissionGroup permissionGroup = null;
        if( null != id ) {
            permissionGroup = authPermissionGroupService.getById(id);
        }
        Map<String, Object> params = Maps.newHashMap();
        params.put("status", CommonEnum.STATUS.ENABLED.getCode());
        params.put("isDelete", CommonEnum.DELETE.UN_DELETE.getCode());
        List<AuthDepartment> departments = authDepartmentService.getList(params);
        model.put("permissionGroup", permissionGroup);
        model.put("departments", departments);
        return "/admin/auth/permissionGroup/edit";
    }

    @RequestMapping("/doEdit")
    @ResponseBody
    public RespBean doEdit( AuthPermissionGroup group, HttpServletRequest request ) {
        RespBean result = new RespBean();
        try {
            boolean isLogin = isLogin(request);
            if( isLogin ) {
                authPermissionGroupService.modify(group);
                result.setStatus(RespBean.SUCCESS);
            } else {
                result.setFailMessage("用户未登录或登录已过期，请重新登录");
            }
        } catch ( PersistenceException e) {
            result.setFailMessage(e.getMessage());
        } catch (Exception e) {
            LOG.error("更新权限组失败，错误原因：" + e.getMessage());
            result.setErrorMessage("系统错误，请稍候重试");
        }
        return result;
    }

    @RequestMapping("/{id}/showPermissions")
    public String showPermissions( @PathVariable Integer id, ModelMap model ) {
        if( null != id ) {
            Map<String, Object> params = Maps.newHashMap();
            params.put("perGroupId", id);
            params.put("status", CommonEnum.STATUS.ENABLED.getCode());
            params.put("isDelete", CommonEnum.DELETE.UN_DELETE.getCode());
            List<AuthPermission> permissions = authPermissionService.getAllInfoList(params);
            model.put("permissions", permissions);
        }
        return "/admin/auth/permission/list";
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
                    authPermissionGroupService.deleteByIds(ids);
                    result.setStatus(RespBean.SUCCESS);
                } catch (Exception e) {
                    result.setErrorMessage("系统错误，请稍候重试");
                    e.printStackTrace();
                }
            } else {
                result.setFailMessage("请选择需要删除的权限组");
            }
        } else {
            result.setFailMessage("用户未登录或登录已过期，请重新登录");
        }
        return result;
    }

}
