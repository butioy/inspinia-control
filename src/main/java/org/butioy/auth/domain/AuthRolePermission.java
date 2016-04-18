package org.butioy.auth.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 03:24
 */
public class AuthRolePermission implements Serializable {

    private static final long serialVersionUID = 6051208786810866581L;

    private Integer id;

    //角色ID
    private Integer roleId;

    //权限ID
    private Integer permissionId;

    //部门ID
    private Integer departmentId;

    //权限组ID
    private Integer permissionGroupId;

    private List<AuthRole> roles;

    private List<AuthPermission> permissions;

    private List<AuthDepartment> departments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getPermissionGroupId() {
        return permissionGroupId;
    }

    public void setPermissionGroupId(Integer permissionGroupId) {
        this.permissionGroupId = permissionGroupId;
    }

    public List<AuthRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AuthRole> roles) {
        this.roles = roles;
    }

    public List<AuthPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<AuthPermission> permissions) {
        this.permissions = permissions;
    }

    public List<AuthDepartment> getDepartments() {
        return departments;
    }

    public void setDepartments(List<AuthDepartment> departments) {
        this.departments = departments;
    }

    @Override
    public String toString() {
        return "AuthRolePermission{" +
                "permissionId=" + permissionId +
                ", roleId=" + roleId +
                ", id=" + id +
                '}';
    }
}
