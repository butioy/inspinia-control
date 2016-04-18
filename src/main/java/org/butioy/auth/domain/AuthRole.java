package org.butioy.auth.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author butioy
 * Date 2015-09-05 03:09
 */
public class AuthRole implements Serializable {

    private static final long serialVersionUID = -7942730714985955307L;

    private Integer id;

    //角色名称
    private String name;

    //创建时间
    private Date addTime;

    //修改时间
    private Date modifyTime;

    //添加人ID
    private Integer addUserId;

    //状态（0：禁用，1：启用）
    private Integer status;

    //是否删除（0：否，1：是）
    private Integer isDelete;

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

    @Override
    public String toString() {
        return "AuthRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addTime=" + addTime +
                ", modifyTime=" + modifyTime +
                ", addUserId=" + addUserId +
                ", status=" + status +
                ", isDelete=" + isDelete +
                '}';
    }
}
