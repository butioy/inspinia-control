package org.butioy.auth.domain;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 03:22
 */
public class AuthPermissionGroup implements Serializable {

    private static final long serialVersionUID = 9213931484434352480L;

    private Integer id;

    //权限组名称
    private String name;

    //权限组所属部门ID
    private Integer depId;

    //添加时间
    private Date addTime;

    //修改时间
    private Date modifyTime;

    //添加人ID
    private Integer addUserId;

    //状态（0：禁用，1：启用）
    private Integer status;

    //是否删除（0：否，1：是）
    private Integer isDelete;

    //权限组所属部门对象
    private AuthDepartment department;

    //权限组所拥有的权限集合
    List<AuthPermission> permissions = Lists.newArrayList();

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

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
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

    public AuthDepartment getDepartment() {
        return department;
    }

    public void setDepartment(AuthDepartment department) {
        this.department = department;
    }

    public List<AuthPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<AuthPermission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "AuthPermissionGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", depId=" + depId +
                ", addTime=" + addTime +
                ", modifyTime=" + modifyTime +
                ", addUserId=" + addUserId +
                ", status=" + status +
                ", isDelete=" + isDelete +
                '}';
    }
}
