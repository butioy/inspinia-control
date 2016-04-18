package org.butioy.auth.domain;

import org.apache.commons.lang3.StringUtils;
import org.butioy.framework.util.DateUtils;

import javax.management.relation.Role;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 02:59
 */
public class AuthUser implements Serializable {

    private static final long serialVersionUID = 2756820212204611649L;

    private Integer id;

    //用户名
    private String userName;

    //真实姓名
    private String fullName;

    //密码
    private String password;

    //性别（0：女，1：男）
    private Integer sex;

    //生日
    private Date birthday;

    //类型（1:系统管理员, 2:用户, 5:超级管理员）
    private Integer userType;

    //头像地址
    private String headImg;

    //皮肤类样式
    private String sysSkin;

    //添加时间
    private Date addTime;

    //修改时间
    private Date modifyTime;

    //添加人ID
    private Integer addUserId;

    //描述
    private String remark;

    //状态（0：禁用，1：启用）
    private Integer status;

    //是否删除（0：否，1：是）
    private Integer isDelete;

    //用户所属角色集合， 查询辅助
    private List<Role> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthday = DateUtils.stringToDate(birthdayStr, DateUtils.DATE_FORMAT);
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getSysSkin() {
        return sysSkin;
    }

    public void setSysSkin(String sysSkin) {
        this.sysSkin = sysSkin;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", userType=" + userType +
                ", headImg='" + headImg + '\'' +
                ", sysSkin='" + sysSkin + '\'' +
                ", addTime=" + addTime +
                ", modifyTime=" + modifyTime +
                ", addUserId=" + addUserId +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", isDelete=" + isDelete +
                '}';
    }
}
