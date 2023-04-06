package com.ruoyi.music.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 用户信息对象 m_user_info
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
@TableName("m_user_info")
public class MUserInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户id */
    @Excel(name = "用户id")
    @TableId(value = "user_id")
    private String userId;

    /** 头像 */
    @Excel(name = "头像")
    private String img;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 年龄 */
    @Excel(name = "年龄")
    private Long age;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    /** 简介 */
    @Excel(name = "简介")
    private String introduction;

    /** 听歌时长 */
    @Excel(name = "听歌时长")
    private Long listenTime;

    @TableField(exist = false)
    private String email;

    @TableField(exist = false)
    private int vipType;

    @TableField(exist = false)
    private String cookie;

    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public int getVipType() {
        return vipType;
    }

    public void setVipType(int vipType) {
        this.vipType = vipType;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    public void setImg(String img) 
    {
        this.img = img;
    }

    public String getImg() 
    {
        return img;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setAge(Long age) 
    {
        this.age = age;
    }

    public Long getAge() 
    {
        return age;
    }
    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }
    public void setIntroduction(String introduction) 
    {
        this.introduction = introduction;
    }

    public String getIntroduction() 
    {
        return introduction;
    }
    public void setListenTime(Long listenTime) 
    {
        this.listenTime = listenTime;
    }

    public Long getListenTime() 
    {
        return listenTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("img", getImg())
            .append("name", getName())
            .append("age", getAge())
            .append("gender", getGender())
            .append("introduction", getIntroduction())
            .append("listenTime", getListenTime())
            .toString();
    }
}
