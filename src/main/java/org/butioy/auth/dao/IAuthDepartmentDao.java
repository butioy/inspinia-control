package org.butioy.auth.dao;

import org.butioy.auth.domain.AuthDepartment;
import org.butioy.framework.base.BaseDao;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 11:15
 */
public interface IAuthDepartmentDao extends BaseDao<Integer, AuthDepartment> {

    List<AuthDepartment> findListByUserId(Integer userId);
}
