package org.butioy.auth.thread;

import com.google.common.collect.Lists;
import org.butioy.auth.domain.AuthUser;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-12-13 01:09
 */
public class AddDataThread implements Runnable {
    private HttpServletResponse response;

    public AddDataThread(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        System.out.println("开始插入1000W数据任务");
        List<AuthUser> list = Lists.newArrayList();
        PrintWriter out = null;
        try {
            out = response.getWriter();
            Date date = new Date();
            for( long i=0; i<10000000; i++ ) {
                AuthUser user = new AuthUser();
                user.setBirthday(date);
                user.setFullName("fullTest" + i);
                user.setUserName("test" + i);
                user.setPassword("quweyiqw");
                user.setSex(1);
                user.setIsDelete(0);
                user.setAddTime(date);
                user.setAddUserId(1);
                user.setHeadImg("test.jpg");
                user.setUserType(1);
                user.setStatus(1);
                user.setRemark("testsss");
                list.add(user);
                if( list.size() == 1000 || i == 10000000-1 ) {
                    System.out.println(list.size());
                    list = Lists.newArrayList();
                    double pec = i/10000000.0*100.0;
                    out.print("一插入" + pec + "%");
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
