package org.butioy.auth.controller;

import com.google.common.collect.Lists;
import org.butioy.auth.domain.AuthUser;
import org.butioy.auth.service.IAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-12-13 01:04
 */
@Controller
@RequestMapping("/test")
public class TestAuthController extends BaseAuthController {

    @Autowired
    private IAuthUserService authUserService;

    /**
     * 返回xml格式测试方法
     * @param request
     * @return
     */
    @RequestMapping("/testXml")
    @ResponseBody
    public TestXml testXml( HttpServletRequest request ) {
        TestXml xml = new TestXml();
        xml.setId(1);
        xml.setName("testXml");
        xml.setFlag(true);
        return xml;
    }

    /**
     * 插入1000W数据方法
     * @param request
     */
    @RequestMapping("/addData")
    public void addData( HttpServletRequest request, HttpServletResponse response) {
//        new Thread(new AddDataThread(response)).start();
        long startTime = System.currentTimeMillis();
        System.out.println("开始插入1000W数据任务");
        List<AuthUser> list = Lists.newArrayList();
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            Date date = new Date();
            for( long i=1; i<10000001; i++ ) {
                AuthUser user = new AuthUser();
                user.setUserName("test" + i);
                user.setFullName("fullTest" + i);
                user.setPassword("quweyiqw");
                user.setSex(1);
                user.setBirthday(date);
                user.setUserType(1);
                user.setHeadImg("test.jpg");
                user.setAddTime(date);
                user.setModifyTime(date);
                user.setAddUserId(1);
                user.setRemark("testsss");
                user.setStatus(1);
                user.setIsDelete(0);
                list.add(user);
                if( list.size() == 1000 || i == 10000000 ) {
                    authUserService.batchSave(list);
                    list = Lists.newArrayList();
                    System.out.println("1000条数据插入成功！");
//                    double pec = i/10000000.0*100.0;
//                    out.print("已插入" + pec + "%");
//                    out.flush();
                }
            }
            long endTime = System.currentTimeMillis();
            out.print("插入1000W条数据完成，耗时：" + ( (endTime-startTime)/1000.0 ));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if( null != out ) {
                out.close();
            }
        }
    }

}

@XmlRootElement
class TestXml {
    private int id;

    private String name;

    private boolean flag;

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "TestXml{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", flag=" + flag +
                '}';
    }
}