package org.butioy.auth.controller;

import org.apache.commons.lang3.StringUtils;
import org.butioy.auth.domain.AuthDepartment;
import org.butioy.auth.domain.AuthUser;
import org.butioy.auth.service.IAuthDepartmentService;
import org.butioy.framework.cons.Constants;
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

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 11:43
 */
@Controller
@RequestMapping("/admin/auth/department")
public class AdminAuthDepartmentAuthController extends BaseAuthController {

    private static Logger LOG = LoggerFactory.getLogger(AdminAuthDepartmentAuthController.class);

    @Autowired
    private IAuthDepartmentService authDepartmentService;

    /**
     * 部门列表页
     * @param request
     * @return
     */
    @RequestMapping("/{id}/index")
    public String index( @PathVariable Integer id, HttpServletRequest request ) {
        AuthUser user = getLoginUser(request);
        String result = "/admin/auth/department/dashboard";
        if( null == id || id <= 0 ) {
            result = "/404";
        } else if( null == user ) {
            result = "redirect:/admin/ajaxLogin";
        } else {
            //TODO 部门首页
            removeAllAuthSession(request);
            AuthDepartment department = authDepartmentService.getById(id);
            setSession(request, Constants.CURRENT_DEPARTMENT_KEY, department);
        }
        return result;
    }

    /**
     * 跳转到添加页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "/admin/auth/department/add";
    }

    /**
     * 添加部门信息
     * @param department
     * @param request
     * @return
     */
    @RequestMapping("/doAdd")
    @ResponseBody
    public RespBean doAdd( AuthDepartment department, HttpServletRequest request ) {
        RespBean result = new RespBean();
        try {
            AuthUser user = getLoginUser(request);
            if( null != user ) {
                if( null != department && StringUtils.isNotBlank(department.getName()) && null != department.getStatus() ) {
                    department.setAddUserId(user.getId());
                    authDepartmentService.save(department);
                    result.setStatus(RespBean.SUCCESS);
                } else {
                    result.setFailMessage("参数错误，请重新操作");
                }
            } else {
                result.setFailMessage("用户未登录或登录已过期，请重新登录");
            }
        } catch (Exception e) {
            LOG.info("添加部门失败，失败原因：" + e.getMessage());
            result.setErrorMessage("系统错误，请稍候重试");
        }
        return result;
    }

    /**
     * 跳转到修改部门信息页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/{id}/edit")
    public String edit( ModelMap model, @PathVariable Integer id ) {
        AuthDepartment department = authDepartmentService.getById(id);
        model.put("department", department);
        return "/admin/auth/department/edit";
    }

    /**
     * 修改部门信息
     * @param department
     * @return
     */
    @RequestMapping(value = "/doEdit")
    @ResponseBody
    public RespBean doEdit( AuthDepartment department ) {
        RespBean result = new RespBean();
        try {
            System.out.println(department.getName());
            authDepartmentService.modify(department);
            result.setStatus(RespBean.SUCCESS);
        } catch ( PersistenceException e) {
            result.setFailMessage(e.getMessage());
        } catch (Exception e) {
            LOG.info("更新部门失败，失败原因：" + e.getMessage());
            result.setErrorMessage("系统错误，请稍候重试");
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
                    authDepartmentService.deleteByIds(ids);
                    result.setStatus(RespBean.SUCCESS);
                } else {
                    result.setFailMessage("参数错误，请选择需要删除的部门");
                }
            } else {
                result.setFailMessage("用户未登录或登录已过期，请重新登录");
            }
        } catch ( PersistenceException e) {
            result.setFailMessage(e.getMessage());
        } catch (Exception e) {
            LOG.error("删除部门信息失败，失败信息 : " + e.getMessage());
            result.setErrorMessage("后台错误，请稍候重试");
            e.printStackTrace();
        }
        return result;
    }

}
