package com.ruoyi.music.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Mv信息对象 m_mv
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
@TableName("m_mv")
public class MMv extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Mvid */
    @TableId
    @Excel(name = "Mvid")
    private String mvId;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 作者id */
    @Excel(name = "作者id")
    private String artistId;

    /** 作者姓名 */
    @Excel(name = "作者姓名")
    private String artistName;

    /** 分辨率 */
    @Excel(name = "分辨率")
    private String brs;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 时长 */
    @Excel(name = "时长")
    private Long duration;

    /** 播放数 */
    @Excel(name = "播放数")
    private Long playCount;

    /** 转发数 */
    @Excel(name = "转发数")
    private Long shareCount;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishTime;

    public void setMvId(String mvId) 
    {
        this.mvId = mvId;
    }

    public String getMvId() 
    {
        return mvId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setArtistId(String artistId)
    {
        this.artistId = artistId;
    }

    public String getArtistId()
    {
        return artistId;
    }
    public void setArtistName(String artistName) 
    {
        this.artistName = artistName;
    }

    public String getArtistName() 
    {
        return artistName;
    }
    public void setBrs(String brs) 
    {
        this.brs = brs;
    }

    public String getBrs() 
    {
        return brs;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setDuration(Long duration) 
    {
        this.duration = duration;
    }

    public Long getDuration() 
    {
        return duration;
    }
    public void setPlayCount(Long playCount) 
    {
        this.playCount = playCount;
    }

    public Long getPlayCount() 
    {
        return playCount;
    }
    public void setShareCount(Long shareCount) 
    {
        this.shareCount = shareCount;
    }

    public Long getShareCount() 
    {
        return shareCount;
    }
    public void setPublishTime(Date publishTime) 
    {
        this.publishTime = publishTime;
    }

    public Date getPublishTime() 
    {
        return publishTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("mvId", getMvId())
            .append("name", getName())
            .append("artistId", getArtistId())
            .append("artistName", getArtistName())
            .append("brs", getBrs())
            .append("desc", getDescription())
            .append("duration", getDuration())
            .append("playCount", getPlayCount())
            .append("shareCount", getShareCount())
            .append("publishTime", getPublishTime())
            .toString();
    }
}
