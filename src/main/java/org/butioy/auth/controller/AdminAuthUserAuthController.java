package org.butioy.auth.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.butioy.auth.domain.AuthRole;
import org.butioy.auth.domain.AuthUser;
import org.butioy.auth.domain.AuthUserRole;
import org.butioy.auth.service.IAuthRoleService;
import org.butioy.auth.service.IAuthUserRoleService;
import org.butioy.auth.service.IAuthUserService;
import org.butioy.framework.cons.CommonEnum;
import org.butioy.framework.exception.PersistenceException;
import org.butioy.framework.resp.RespBean;
import org.butioy.framework.util.ParamUtils;
import org.butioy.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 11:43
 */
@Controller
@RequestMapping("/admin/auth/user")
public class AdminAuthUserAuthController extends BaseAuthController {

    private static Logger LOG = LoggerFactory.getLogger(AdminAuthUserAuthController.class);

    @Autowired
    private IAuthUserService authUserService;
    @Autowired
    private IAuthRoleService authRoleService;
    @Autowired
    private IAuthUserRoleService authUserRoleService;

    /**
     * 用户列表页
     * @param model
     * @param request
     * @return
     */
    @RequestMapping
    public String index( ModelMap model, HttpServletRequest request ) {
        Map params = new HashMap();
        params.put("isDelete", CommonEnum.DELETE.UN_DELETE.getCode());
        Integer pageNumber = ParamUtils.getIntParameter(request, "pageNumber", 1);
        Integer pageSize = ParamUtils.getIntParameter(request, "pageSize", 10);
        PageHelper.startPage(pageNumber, pageSize);
        List<AuthUser> users = authUserService.getList(params);
        model.put("users", users);

        long total = ((Page<AuthUser>)users).getTotal();
        int pages = ((Page<AuthUser>)users).getPages();
        model.put("pages", pages);
        model.put("pageNumber", pageNumber);
        model.put("pageSize", pageSize);

        return "/admin/auth/user/index";
    }

    @RequestMapping("/toAdd")
    public String toAdd( ModelMap model ) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("status", CommonEnum.STATUS.ENABLED.getCode());
        params.put("isDelete", CommonEnum.DELETE.UN_DELETE.getCode());
        List<AuthRole> roles = authRoleService.getList(params);
        model.put("roles", roles);
        return "/admin/auth/user/add";
    }

    @RequestMapping("/doAdd")
    @ResponseBody
    public RespBean doAdd( AuthUser user, HttpServletRequest request ) {
        RespBean result = new RespBean();
        AuthUser loginUser = getLoginUser(request);
        if( null != loginUser ) {
            try {
                user.setAddUserId(loginUser.getId());
                String password = StringUtil.randomPasswordFor8();
                user.setPassword(password);
                user = authUserService.save(user);
                Integer roleId = ParamUtils.getIntParameter(request, "roleId", 0);
                if( roleId > 0 ) {
                    AuthUserRole userRole = new AuthUserRole();
                    userRole.setRoleId(roleId);
                    userRole.setUserId(user.getId());
                    authUserRoleService.save(userRole);
                }
                result.setSuccessData(password);
            } catch (Exception e) {
                e.printStackTrace();
                result.setErrorMessage("系统错误，请稍候重试");
            }
        } else {
            result.setFailMessage("用户未登录或登录已过期，请重新登录");
        }
        return result;
    }

    @RequestMapping("/{id}/toEdit")
    public String toEdit( @PathVariable Integer id, ModelMap model ) {
        if( null != id && id > 0 ) {
            AuthUser user = authUserService.getById(id);

            Map<String, Object> params = Maps.newHashMap();
            params.put("status", CommonEnum.STATUS.ENABLED.getCode());
            params.put("isDelete", CommonEnum.DELETE.UN_DELETE.getCode());
            List<AuthRole> roles = authRoleService.getList(params);
            model.put("roles", roles);

            params = Maps.newHashMap();
            params.put("userId", user.getId());
            List<AuthUserRole> userRoles = authUserRoleService.getList(params);

            model.put("user", user);
            model.put("roles", roles);
            model.put("hasRoleId", (userRoles != null && userRoles.size() > 0) ? userRoles.get(0).getRoleId() : null);
        }
        return "/admin/auth/user/edit";
    }

    @RequestMapping("/doEdit")
    @ResponseBody
    public RespBean doEdit( AuthUser user, HttpServletRequest request ) {
        RespBean result = new RespBean();
        AuthUser loginUser = getLoginUser(request);
        if( null != loginUser ) {
            try {
                authUserService.modify(user);
                Integer roleId = ParamUtils.getIntParameter(request, "roleId", 0);
                Map<String, Object> params = Maps.newHashMap();
                params.put("userId;", user.getId());
                if( roleId > 0 ) {
                    List<AuthUserRole> userRoles = authUserRoleService.getList(params);
                    if( null != userRoles && userRoles.size() > 0 ) {
                        AuthUserRole userRole = userRoles.get(0);
                        userRole.setRoleId(roleId);
                        authUserRoleService.modify(userRole);
                    } else {
                        AuthUserRole userRole = new AuthUserRole();
                        userRole.setUserId(user.getId());
                        userRole.setRoleId(roleId);
                        authUserRoleService.save(userRole);
                    }
                } else {
                    authUserRoleService.deleteByParam(params);
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.setErrorMessage("系统错误，请稍候重试");
            }
        } else {
            result.setFailMessage("用户未登录或登录已过期，请重新登录");
        }
        return result;
    }

    @RequestMapping("/{id}/toEditRole")
    public String toEditRole( @PathVariable Integer id, ModelMap model ) {
        if( null != id && id > 0 ) {
            Map<String, Object> params = Maps.newHashMap();
            params.put("status", CommonEnum.STATUS.ENABLED.getCode());
            params.put("isDelete", CommonEnum.DELETE.UN_DELETE.getCode());
            List<AuthRole> roles = authRoleService.getList(params);
            model.put("roles", roles);

            params = Maps.newHashMap();
            params.put("userId", id);
            List<AuthUserRole> userRoles = authUserRoleService.getList(params);

            model.put("id",id);
            model.put("roles", roles);
            model.put("hasRoleId", (userRoles != null && userRoles.size() > 0) ? userRoles.get(0).getRoleId() : null);
        }
        return "/admin/auth/user/editRole";
    }

    @RequestMapping("/doEditRole")
    @ResponseBody
    public RespBean doEditRole( HttpServletRequest request ) {
        RespBean result = new RespBean();
        AuthUser loginUser = getLoginUser(request);
        if( null != loginUser ) {
            Integer id = ParamUtils.getIntParameter(request, "id", 0);
            String roleIds = ParamUtils.getParameter(request, "roleIds", "");
            if( id != 0 ) {
                try {
                    Map<String, Object> params = Maps.newHashMap();
                    params.put("userId", id);
                    authUserRoleService.deleteByParam(params);
                    if( StringUtils.isNotBlank(roleIds) ) {
                        roleIds = StringUtil.subStringComma(roleIds);
                        String[] roleIdArr = roleIds.split(",");
                        if( roleIdArr.length == 1 ) {
                            AuthUserRole userRole = new AuthUserRole();
                            userRole.setUserId(id);
                            userRole.setRoleId(Integer.valueOf(roleIdArr[0]));
                            authUserRoleService.save(userRole);
                        } else {
                            List<AuthUserRole> list = Lists.newArrayList();
                            for( String roleId : roleIdArr ) {
                                AuthUserRole userRole = new AuthUserRole();
                                userRole.setUserId(id);
                                userRole.setRoleId(Integer.valueOf(roleId));
                                list.add(userRole);
                            }
                            authUserRoleService.batchSave(list);
                        }
                    }
                    result.setStatus(RespBean.SUCCESS);
                } catch(NumberFormatException e) {
                    result.setFailMessage("参数错误，请重试");
                } catch(Exception e) {
                    result.setErrorMessage("系统错误，请稍后重试");
                }
            } else {
                result.setFailMessage("参数错误，请重试");
            }
        } else {
            result.setFailMessage("用户未登录或登录已过期，请重新登录");
        }
        return result;
    }

    @RequestMapping("/batchRemove")
    @ResponseBody
    public RespBean batchRemove( HttpServletRequest request ) {
        RespBean result = new RespBean();
        AuthUser loginUser = getLoginUser(request);
        if( null != loginUser ) {
            String ids = ParamUtils.getParameter(request, "ids", "");
            if( StringUtils.isNotBlank(ids) ) {
                try {
                    authUserService.deleteByIds(ids);
                    result.setStatus(RespBean.SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                    result.setErrorMessage("系统错误，请稍候重试");
                }
            } else {
                result.setFailMessage("请选择需要删除的用户");
            }
        } else {
            result.setFailMessage("用户未登录或登录已过期，请重新登录");
        }
        return result;
    }

    /**
     * 跳转到用户注册页
     * @return
     */
    @RequestMapping("/register")
    public String register() {
        return "/admin/auth/user/register";
    }

    /**
     * 执行用户注册
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("/doRegister")
    @ResponseBody
    public RespBean doRegister( AuthUser user, HttpServletRequest request ) {
        RespBean bean = new RespBean();
        if( null != user ) {
            try {
                authUserService.save(user);
                bean.setStatus(RespBean.SUCCESS);
            } catch (Exception e) {
                bean.setStatus(RespBean.ERROR);
                bean.setMessage("系统错误");
                e.printStackTrace();
                LOG.info("注册失败， 错误信息" + e.getMessage());
            }
        } else {
            bean.setStatus(RespBean.FAIL);
            bean.setMessage("参数错误");
        }
        return bean;
    }

    /**
     * 重置密码
     * @param request
     * @return
     */
    @RequestMapping("/resetPassword")
    @ResponseBody
    public RespBean resetPassword( HttpServletRequest request ) {
        RespBean result = new RespBean();
        AuthUser loginUser = getLoginUser(request);
        if( null != loginUser ) {
            String password = ParamUtils.getParameter(request, "password", "");
            Integer id = ParamUtils.getIntParameter(request, "id", 0);
            if( StringUtils.isNotBlank(password) && id > 0 ) {
                try {
                    authUserService.modifyPassword(id, password);
                    result.setSuccessData(password);
                } catch (PersistenceException e) {
                    result.setFailMessage(e.getMessage());
                } catch (Exception e) {
                    result.setErrorMessage("系统错误，请稍候重试");
                    e.printStackTrace();
                }
            } else {
                result.setFailMessage("参数错误，请重试");
            }
        } else {
            result.setFailMessage("用户未登录或登录已过期，请重新登录");
        }
        return result;
    }

    @RequestMapping("/resetHead")
    public String resetHead( HttpServletRequest request ) {
        AuthUser loginUser = getLoginUser(request);
        if( null != loginUser ) {
            return "/admin/auth/user/uploadImg";
        } else {
            return "redirect:/admin/ajaxLogin";
        }
    }

    /**
     * 修改样式
     */
    @RequestMapping("/resetSkin")
    @ResponseBody
    public RespBean resetSkin( HttpServletRequest request ) {
        RespBean result = new RespBean();
        AuthUser loginUser = getLoginUser(request);
        if( null != loginUser ) {
            String sysSkin = ParamUtils.getParameter(request, "sysSkin", "");
            String oldSkin = ParamUtils.getParameter(request, "oldSkin", "");
            String dbSkin = loginUser.getSysSkin();
            if( sysSkin.indexOf(dbSkin) != -1 ) {
                return result;
            } else if( StringUtils.isNotBlank(oldSkin) ) {
                if( oldSkin.indexOf(dbSkin) != -1 ) {
                    dbSkin = dbSkin.replace(oldSkin, sysSkin);
                } else {
                    dbSkin = dbSkin + " " + sysSkin;
                }
            } else if( StringUtils.isNotBlank(sysSkin) ) {
                dbSkin += " " + sysSkin;
            }
            if( !dbSkin.equals(loginUser.getSysSkin()) ) {
                loginUser.setSysSkin(dbSkin);
                try {
                    authUserService.modify(loginUser);
                    result.setSuccessMessge("皮肤更新成功！");
                } catch (PersistenceException e) {
                    result.setFailMessage(e.getMessage());
                } catch (Exception e) {
                    result.setErrorMessage("系统错误，请稍候重试");
                    e.printStackTrace();
                }
            }
        } else {
            result.setFailMessage("用户未登录或登录已过期，请重新登录");
        }
        return result;
    }

}
