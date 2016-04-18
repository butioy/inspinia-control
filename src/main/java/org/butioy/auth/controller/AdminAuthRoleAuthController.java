package org.butioy.auth.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.butioy.auth.domain.AuthDepartment;
import org.butioy.auth.domain.AuthPermission;
import org.butioy.auth.domain.AuthPermissionGroup;
import org.butioy.auth.domain.AuthRole;
import org.butioy.auth.domain.AuthRolePermission;
import org.butioy.auth.domain.AuthUser;
import org.butioy.auth.service.IAuthDepartmentService;
import org.butioy.auth.service.IAuthPermissionGroupService;
import org.butioy.auth.service.IAuthPermissionService;
import org.butioy.auth.service.IAuthRolePermissionService;
import org.butioy.auth.service.IAuthRoleService;
import org.butioy.framework.bean.ZTreeBean;
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
 * 角色控制
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 2015-09-28 18:39.
 */
@Controller
@RequestMapping("/admin/auth/role")
public class AdminAuthRoleAuthController extends BaseAuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AdminAuthRoleAuthController.class);

    @Autowired
    private IAuthRoleService authRoleService;
    @Autowired
    private IAuthDepartmentService authDepartmentService;
    @Autowired
    private IAuthPermissionGroupService authPermissionGroupService;
    @Autowired
    private IAuthPermissionService authPermissionService;
    @Autowired
    private IAuthRolePermissionService authRolePermissionService;

    @RequestMapping
    public String index( ModelMap model, HttpServletRequest request ) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("isDelete", CommonEnum.DELETE.UN_DELETE.getCode());
        Integer pageNumber = ParamUtils.getIntParameter(request, "pageNumber", 1);
        Integer pageSize = ParamUtils.getIntParameter(request, "pageSize", 10);
        PageHelper.startPage(pageNumber, pageSize);
        List<AuthRole> roles = authRoleService.getList(params);
        long total = ((Page<AuthRole>)roles).getTotal();

        int pages = ((Page<AuthRole>)roles).getPages();
        model.put("pages", pages);
        model.put("pageNumber", pageNumber);
        model.put("pageSize", pageSize);
        model.put("roles", roles);
        return "/admin/auth/role/index";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "/admin/auth/role/add";
    }

    @RequestMapping("/doAdd")
    @ResponseBody
    public RespBean doAdd( AuthRole role, HttpServletRequest request ) {
        RespBean result = new RespBean();
        AuthUser user = getLoginUser(request);
        try {
            if( null != user ) {
                role.setAddUserId(user.getId());
                authRoleService.save(role);
                result.setStatus(RespBean.SUCCESS);
            } else {
                result.setFailMessage("用户未登录或登录已过期，请重新登录");
            }
        } catch (Exception e) {
            LOG.error("新增角色失败，失败信息 : " + e.getMessage());
            result.setErrorMessage("后台错误，请稍候重试");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/{id}/edit")
    public String edit( @PathVariable Integer id, ModelMap model ) {
        if( null != id ) {
            AuthRole role = authRoleService.getById(id);
            model.put("role", role);
        }
        return "/admin/auth/role/edit";
    }

    @RequestMapping("/doEdit")
    @ResponseBody
    public RespBean doEdit( AuthRole role, HttpServletRequest request ) {
        RespBean result = new RespBean();
        AuthUser user = getLoginUser(request);
        try {
            if( null != user ) {
                authRoleService.save(role);
                result.setStatus(RespBean.SUCCESS);
            } else {
                result.setFailMessage("用户未登录或登录已过期，请重新登录");
            }
        } catch ( PersistenceException e) {
            result.setFailMessage(e.getMessage());
        } catch (Exception e) {
            LOG.error("修改角色信息失败，失败信息 : " + e.getMessage());
            result.setErrorMessage("后台错误，请稍候重试");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/batchRemove")
    @ResponseBody
    public RespBean batchRemove( HttpServletRequest request ) {
        RespBean result = new RespBean();
        AuthUser user = getLoginUser(request);
        try {
            if( null != user ) {
                String ids = ParamUtils.getParameter(request, "ids", "");
                if( StringUtils.isNotBlank(ids) ) {
                    authRoleService.deleteByIds(ids);
                    result.setStatus(RespBean.SUCCESS);
                } else {
                    result.setFailMessage("参数错误，请选择需要删除的角色");
                }
            } else {
                result.setFailMessage("用户未登录或登录已过期，请重新登录");
            }
        } catch ( PersistenceException e) {
            result.setFailMessage(e.getMessage());
        } catch (Exception e) {
            LOG.error("修改角色信息失败，失败信息 : " + e.getMessage());
            result.setErrorMessage("后台错误，请稍候重试");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/{id}/editPermission")
    public String editPermission( @PathVariable Integer id, ModelMap model ) {
        model.put("roleId", id);
        return "/admin/auth/role/editPermission";
    }

    @RequestMapping("/getPermissionTree")
    @ResponseBody
    public List<ZTreeBean> getPermissionTree( HttpServletRequest request ) {
        Integer roleId = ParamUtils.getIntParameter(request, "roleId", 0);
        Integer id = ParamUtils.getIntParameter(request, "id", 0);
        Integer level = ParamUtils.getIntParameter(request, "level", -1);
//        Integer checkedStatus = ParamUtils.getIntParameter(request, "checkedStatus", -1);
        Integer type = ParamUtils.getIntParameter(request, "type", 1);

        List<ZTreeBean> zTreeBeans = Lists.newArrayList();
        Map<Integer, Integer> indexMap = Maps.newHashMap();
        int index = 0;

        boolean checked = false;
//        if(  checkedStatus == 1 ) {
//            checked = true;
//        }

        Map<String, Object> params = Maps.newHashMap();
        params.put("status", CommonEnum.STATUS.ENABLED.getCode());
        params.put("isDelete", CommonEnum.DELETE.UN_DELETE.getCode());
        if( id == 0 && level == -1 ) {
            List<AuthDepartment> departments = authDepartmentService.getList(params);
            for( AuthDepartment department : departments ) {
                if( null != department ) {
                    ZTreeBean root = new ZTreeBean();
                    root.setId(department.getId().toString());
                    root.setName(department.getName());
                    root.setIsParent(true);
                    root.setChecked(checked);
                    zTreeBeans.add(root);
                    indexMap.put(department.getId(), index++);
                }
            }
            params = Maps.newHashMap();
            params.put("roleId", roleId);
            params.put("type", type);
            List<AuthRolePermission> roleDepartments = authRolePermissionService.getDepartmentList(params);
            for( AuthRolePermission department : roleDepartments ) {
                if( indexMap.containsKey(department.getDepartmentId()) ) {
                    int i = indexMap.get(department.getDepartmentId());
                    zTreeBeans.get(i).setChecked(true);
                }
            }
        } else if( id > 0 && level == 0 ) {
            params.put("depId", id);
            List<AuthPermissionGroup> groups = authPermissionGroupService.getList(params);
            for( AuthPermissionGroup group : groups ) {
                if( null != group ) {
                    ZTreeBean secondLevel = new ZTreeBean();
                    secondLevel.setId(group.getId().toString());
                    secondLevel.setpId(id.toString());
                    secondLevel.setName(group.getName());
                    secondLevel.setIsParent(true);
                    secondLevel.setChecked(checked);
                    zTreeBeans.add(secondLevel);
                    indexMap.put(group.getId(), index++);
                }
            }
            params = Maps.newHashMap();
            params.put("roleId", roleId);
            params.put("departmentId", id);
            params.put("type", type);
            List<AuthRolePermission> rolePermissionGroups = authRolePermissionService.getPermissionGroupList(params);
            for( AuthRolePermission group : rolePermissionGroups ) {
                if( indexMap.containsKey(group.getPermissionGroupId()) ) {
                    int i = indexMap.get(group.getPermissionGroupId());
                    zTreeBeans.get(i).setChecked(true);
                }
            }
        } else if( id > 0 && level == 1 ) {
            params.put("perGroupId", id);
            params.put("type", type);
            List<AuthPermission> permissions = authPermissionService.getList(params);
            for( AuthPermission ap : permissions ) {
                if( null != ap ) {
                    ZTreeBean thirdLevel = new ZTreeBean();
                    thirdLevel.setId(ap.getId().toString());
                    thirdLevel.setpId(id.toString());
                    thirdLevel.setName(ap.getName());
                    thirdLevel.setChecked(checked);
                    zTreeBeans.add(thirdLevel);
                    indexMap.put(ap.getId(), index++);
                }
            }
            params = Maps.newHashMap();
            params.put("roleId", roleId);
            params.put("permissionGroupId", id);
            params.put("type", type);
            List<AuthRolePermission> rolePermissions = authRolePermissionService.getList(params);
            for( AuthRolePermission permission : rolePermissions ) {
                if( indexMap.containsKey(permission.getPermissionId()) ) {
                    int i = indexMap.get(permission.getPermissionId());
                    zTreeBeans.get(i).setChecked(true);
                }
            }
        }
        return zTreeBeans;
    }

    @RequestMapping("/{id}/doEditPermission")
    @ResponseBody
    public RespBean doEditPermission( @PathVariable Integer id, HttpServletRequest request ) {
        RespBean result = new RespBean();
        String menuIds = ParamUtils.getParameter(request, "menuIds");
        String tagIds = ParamUtils.getParameter(request, "tagIds");
        try {
            authRoleService.savePermission(id, menuIds, tagIds);
            result.setStatus(RespBean.SUCCESS);
        }  catch ( PersistenceException e) {
            result.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            result.setErrorMessage("系统错误，请稍后重试");
            e.printStackTrace();
        }
        return result;
    }

}
