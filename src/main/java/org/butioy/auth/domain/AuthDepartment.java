package org.butioy.auth.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 03:14
 */
public class AuthDepartment implements Serializable {

    private static final long serialVersionUID = 7861454433707297832L;

    private Integer id;

    //部门名称
    private String name;

    //部门描述
    private String remark;

    //状态（0：禁用，1：启用）
    private Integer status;

    //是否删除（0：否，1：是）
    private Integer isDelete;

    //添加时间
    private Date addTime;

    //修改时间
    private Date  modifyTime;

    //添加人ID
    private Integer addUserId;

    //所拥有的权限组数量  查询辅助
    private Integer permissionGroupCount;

    //部门所拥有的权限组集合  查询辅助
    private List<AuthPermissionGroup> permissionGroups;

    //部门所拥有的权限集合  查询辅助
    private List<AuthPermission> permissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Integer addUserId) {
        this.addUserId = addUserId;
    }

    public Integer getPermissionGroupCount() {
        return permissionGroupCount;
    }

    public void setPermissionGroupCount(Integer permissionGroupCount) {
        this.permissionGroupCount = permissionGroupCount;
    }

    public List<AuthPermissionGroup> getPermissionGroups() {
        return permissionGroups;
    }

    public void setPermissionGroups(List<AuthPermissionGroup> permissionGroups) {
        this.permissionGroups = permissionGroups;
    }

    public List<AuthPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<AuthPermission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "AuthDepartment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", isDelete=" + isDelete +
                ", addTime=" + addTime +
                ", modifyTime=" + modifyTime +
                ", addUserId=" + addUserId +
                '}';
    }
}
