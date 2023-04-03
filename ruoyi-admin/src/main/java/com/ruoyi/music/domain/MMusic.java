package com.ruoyi.music.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.music.util.RandomUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 歌曲信息对象 m_music
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
@TableName("m_music")
public class MMusic extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 歌曲id */
    @TableId
    @Excel(name = "歌曲id")
    private String musicId;

    /** 歌名 */
    @Excel(name = "歌名")
    private String name;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 封面 */
    @Excel(name = "封面")
    private String coverImg;

    /** 作者 */
    @Excel(name = "作者")
    private String artists;

    /** 作者id */
    @Excel(name = "作者id")
    private String artistsId;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publicTime;

    public void setMusicId(String musicId) 
    {
        this.musicId = musicId;
    }

    public String getMusicId() 
    {
        return musicId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setCoverImg(String coverImg) 
    {
        this.coverImg = coverImg;
    }

    public String getCoverImg() 
    {
        return coverImg;
    }
    public void setArtists(String artists) 
    {
        this.artists = artists;
    }

    public String getArtists() 
    {
        return artists;
    }
    public void setArtistsId(String artistsId) 
    {
        this.artistsId = artistsId;
    }

    public String getArtistsId() 
    {
        return artistsId;
    }
    public void setPublicTime(Date publicTime) 
    {
        this.publicTime = publicTime;
    }

    public Date getPublicTime() 
    {
        return publicTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("musicId", getMusicId())
            .append("name", getName())
            .append("description", getDescription())
            .append("coverImg", getCoverImg())
            .append("artists", getArtists())
            .append("artistsId", getArtistsId())
            .append("publicTime", getPublicTime())
            .toString();
    }
}
