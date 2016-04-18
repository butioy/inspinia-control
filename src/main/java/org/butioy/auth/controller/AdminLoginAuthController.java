package org.butioy.auth.controller;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.butioy.auth.domain.AuthUser;
import org.butioy.auth.service.IAuthDepartmentService;
import org.butioy.auth.service.IAuthUserService;
import org.butioy.framework.cons.Constants;
import org.butioy.framework.resp.RespBean;
import org.butioy.framework.util.DateUtils;
import org.butioy.framework.util.MD5EncryptUtils;
import org.butioy.framework.util.ParamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 11:49
 */
@Controller
@RequestMapping("/admin")
public class AdminLoginAuthController extends BaseAuthController {

    private static Logger LOG = LoggerFactory.getLogger(AdminLoginAuthController.class);

    @Autowired
    private IAuthUserService authUserService;

    @Autowired
    private IAuthDepartmentService authDepartmentService;

    /**
     * 跳转到登录页
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public String login( ModelMap model, HttpServletRequest request ) {
        Cookie[] cookies = request.getCookies();
        String account = "";
        if( null != cookies ) {
            for( Cookie cookie : cookies ) {
                if( cookie.getName().equals(Constants.REMEMBER_ACCOUNT_COOKIE_KEY) ) {
                    account = cookie.getValue();
                    break;
                }
            }
        }
//        account = request.getSession().getId();
//        model.put("account", account);
        return "/admin/login";
    }

    /**
     * 执行登录
     * @param request
     * @return
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean doLogin( HttpServletRequest request ) {
        return login(request);
    }

    /**
     * 跳转到ajax登录页
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/ajaxLogin")
    public String ajaxLogin( ModelMap model, HttpServletRequest request ) {
        Cookie[] cookies = request.getCookies();
        String account = "";
        if( null != cookies ) {
            for( Cookie cookie : cookies ) {
                if( cookie.getName().equals(Constants.REMEMBER_ACCOUNT_COOKIE_KEY) ) {
                    account = cookie.getValue();
                    break;
                }
            }
        }
        model.put("account", account);
        return "/admin/ajaxLogin";
    }

    /**
     * 执行ajax登录
     * @param request
     * @return
     */
    @RequestMapping("/doAjaxLogin")
    @ResponseBody
    public RespBean doAjaxLogin( HttpServletRequest request ) {
        return login(request);
    }

    /**
     * 执行ajax登录
     * @param request
     * @return
     */
    @RequestMapping("/testXmlLogin")
    @ResponseBody
    public RespBean testXmlLogin( HttpServletRequest request ) {
        System.out.println("#################ViewController doXMLJaxb2View##################");
        RespBean bean = new RespBean();
        TestXml xml = new TestXml();
        xml.setId(1);
        xml.setName("testXml");
        xml.setFlag(true);
        bean.setData(xml);
        return bean;
    }

    /**
     * 登录用户
     * @param request
     * @return
     */
    private RespBean login( HttpServletRequest request ) {
        RespBean bean = new RespBean();
        try {
            String account = ParamUtils.getParameter(request, "account", "");
            String password = ParamUtils.getParameter(request, "password", "");
            account = StringEscapeUtils.escapeEcmaScript(account);
            account = StringEscapeUtils.escapeHtml4(account);
            AuthUser user = authUserService.getByUserName(account);
            if( null != user ) {
                String encryptPassword = MD5EncryptUtils.md5(password);
                if( encryptPassword.equals(user.getPassword()) ) {
                    setSession(request, Constants.LOGIN_SESSION_USER_KEY, user, 60*60*24);
                    Map<String, Object> userInfoMap = Maps.newHashMap();
                    userInfoMap.put("id", user.getId());
                    userInfoMap.put("userName", user.getUserName());
                    userInfoMap.put("fullName", user.getFullName());
                    userInfoMap.put("sysSkin", user.getSysSkin());
                    setSession(request, Constants.LOGIN_USER_INFO_MAP_KEY, userInfoMap, 60*60*24);
                    bean.setSuccessMessge("登录成功！");
                    LOG.debug("用户【"+account+"】登录成功。时间：" + DateUtils.dateToString(new Date()));
                } else {
                    LOG.debug("用户【"+account+"】登录失败，密码错误。时间：" + DateUtils.dateToString(new Date()));
                    bean.setStatus(RespBean.FAIL);
                    bean.setMessage("帐号或密码错误");
                }
            } else {
                LOG.debug("用户【"+account+"】登录失败。帐号不存在后禁用。时间：" + DateUtils.dateToString(new Date()));
                bean.setStatus(RespBean.FAIL);
                bean.setMessage("帐号不存在或已禁用");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.debug("登录异常，异常信息："+e.getMessage());
            bean.setStatus(RespBean.ERROR);
            bean.setMessage("系统错误，请重试");
        }
        return bean;
    }

    /**
     * 退出登录
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/loginOut")
    public String loginOut( ModelMap model, HttpServletRequest request ) {
        removeSession(request, Constants.LOGIN_SESSION_USER_KEY);
        return "redirect:/admin/login";
    }

    /**
     * 锁屏
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/lockScreen")
    public String lockScreen( ModelMap model, HttpServletRequest request ) {
        AuthUser user = getLoginUser(request);
        if( null == user ) {
            return "redirect:/admin/login";
        }
//        removeSession(request, AuthCons.LOGIN_SESSION_USER_KEY);
        String from = ParamUtils.getParameter(request, "from", "/admin/index");
        String contextPath = request.getContextPath();
        if( !from.equals("/admin/index") && StringUtils.isNotBlank(contextPath) ) {
            from = from.replace(contextPath,"");
        }
        AuthUser authUser = new AuthUser();
        authUser.setUserName(user.getUserName());
        authUser.setFullName(user.getFullName());
        authUser.setSex(user.getSex());
        authUser.setHeadImg(user.getHeadImg());
        model.put("user", authUser);
        setSession( request, Constants.FROM_URL, from );
        return "/admin/lockScreen";
    }

    /**
     * 跳转到index页面
     * @param request
     * @return
     */
    @RequestMapping("/index")
    public String index(  ModelMap model, HttpServletRequest request ) {
        AuthUser user = (AuthUser) request.getSession().getAttribute(Constants.LOGIN_SESSION_USER_KEY);
        if( null != user ) {
            removeAllAuthSession(request);
        } else {
            return "redirect:/admin/login";
        }
        return "/admin/index";
    }

}