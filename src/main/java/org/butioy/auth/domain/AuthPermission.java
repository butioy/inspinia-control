package org.butioy.auth.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 03:18
 */
public class AuthPermission implements Serializable {

    private static final long serialVersionUID = -5200947411279922193L;

    private Integer id;

    //权限名称
    private String name;

    //权限URL
    private String url;

    //权限类型（1：菜单权限，2：操作权限）
    private Integer type;

    //权限描述
    private String remark;

    //创建时间
    private Date addTime;

    //修改时间
    private Date modifyTime;

    //添加人
    private Integer addUserId;

    //状态（0：禁用，1：启用）
    private Integer status;

    //是否删除（0：否，1：是）
    private Integer isDelete;

    //权限所属部门ID
    private Integer depId;

    //权限所属权限组ID
    private Integer perGroupId;

    //权限所属部门对象
    private AuthDepartment department;

    //权限所属权限组对象
    private AuthPermissionGroup permissionGroup;

    //添加人对象
    private AuthUser user;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    public Integer getPerGroupId() {
        return perGroupId;
    }

    public void setPerGroupId(Integer perGroupId) {
        this.perGroupId = perGroupId;
    }

    public AuthDepartment getDepartment() {
        return department;
    }

    public void setDepartment(AuthDepartment department) {
        this.department = department;
    }

    public AuthPermissionGroup getPermissionGroup() {
        return permissionGroup;
    }

    public void setPermissionGroup(AuthPermissionGroup permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    public AuthUser getUser() {
        return user;
    }

    public void setUser(AuthUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AuthPermission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", remark='" + remark + '\'' +
                ", addTime=" + addTime +
                ", modifyTime=" + modifyTime +
                ", addUserId=" + addUserId +
                ", status=" + status +
                ", isDelete=" + isDelete +
                ", depId=" + depId +
                ", perGroupId=" + perGroupId +
                '}';
    }
}
