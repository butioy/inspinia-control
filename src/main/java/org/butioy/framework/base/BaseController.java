package org.butioy.framework.base;

import org.butioy.framework.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by LSL on 2016/1/28.
 */
public class BaseController {

    /**
     * 获取会话
     * @param request
     * @return
     */
    protected HttpSession getSession(HttpServletRequest request ) {
        return request.getSession();
    }

    /**
     * 删除session中 <code>key</code> 对应的属性值
     * @param request
     * @param key
     */
    protected void removeSession( HttpServletRequest request, String key ) {
        WebUtils.removeSession(request, key);
    }

    /**
     * 向session中添加属性
     * @param request
     * @param key
     * @param value
     */
    protected void setSession( HttpServletRequest request, String key, Object value ) {
        WebUtils.setSession(request, key, value);
    }

    /**
     * 向session中添加属性
     * @param request
     * @param key
     * @param value
     */
    protected void setSession( HttpServletRequest request, String key, Object value, Integer time ) {
        WebUtils.setSession(request, key, value, time);
    }

}
