package org.butioy.auth.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 03:12
 */
public class AuthUserRole implements Serializable {

    private static final long serialVersionUID = -187008744761327807L;

    private Integer id;

    //用户ID
    private Integer userId;

    //角色ID
    private Integer roleId;

    private List<AuthUser> users;

    private List<AuthRole> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<AuthUser> getUsers() {
        return users;
    }

    public void setUsers(List<AuthUser> users) {
        this.users = users;
    }

    public List<AuthRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AuthRole> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "AuthUserRole{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}
