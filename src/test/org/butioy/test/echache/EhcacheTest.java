package org.butioy.test.echache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.butioy.auth.domain.AuthUser;
import org.butioy.auth.service.IAuthUserService;
import org.butioy.framework.holder.ApplicationContextHolder;
import org.butioy.framework.util.EhCacheUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by butioy on 2016/4/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:spring/spring-*.xml"}
)
@WebAppConfiguration
public class EhcacheTest {

    @Autowired
    private IAuthUserService authUserService;

    @Before
    public void init() {
        if( null == authUserService ) {
            authUserService = (IAuthUserService)ApplicationContextHolder.getBean("authUserService");
        }
    }

    @Test
    public void cacheTest() {
        AuthUser user = authUserService.getByUserName("admin");
        System.out.println("===============修改前===============");
        Element element = EhCacheUtils.getInstance().getElement("authUserCache", "admin");
        Cache cache = EhCacheUtils.getInstance().getCache("authUserCache");
        List list = cache.getKeys();
        for ( int i=0; i<list.size(); i++ ) {
            System.out.println(list.get(i));
        }
        System.out.println( "缓存数量：" + cache.getSize() );
        System.out.println(element.getObjectValue());
        AuthUser user1 = new AuthUser();
        user1.setId(user.getId());
        user1.setUserName("adminTest");
        authUserService.modifyFieldsById(user1);
        System.out.println("===============修改后===============");
        Element element1 = EhCacheUtils.getInstance().getElement("authUserCache", "admin");
        Cache cache1 = EhCacheUtils.getInstance().getCache("authUserCache");
        List list1 = cache1.getKeys();
        for ( int i=0; i<list1.size(); i++ ) {
            System.out.println(list1.get(i));
        }
        System.out.println( "缓存数量：" + cache1.getSize() );
        System.out.println(element1.getObjectValue());
    }

}
