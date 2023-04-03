package com.ruoyi.music.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 作者信息对象 m_artist
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
@TableName("m_artist")
public class MArtist extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 作者id */
    @TableId
    @Excel(name = "作者id")
    private String artistId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 别名 */
    @Excel(name = "别名")
    private String alias;

    /** 封面 */
    @Excel(name = "封面")
    private String coverImg;

    /** 专辑数 */
    @Excel(name = "专辑数")
    private Long albumSize;

    /** 身份 */
    @Excel(name = "身份")
    private String identities;

    /** 歌曲数 */
    @Excel(name = "歌曲数")
    private Long musicSize;

    /** Mv数 */
    @Excel(name = "Mv数")
    private Long mvSize;

    /** 主要描述 */
    @Excel(name = "主要描述")
    private String briefDesc;

    /** 介绍 */
    @Excel(name = "介绍")
    private String introduction;

    public void setArtistId(String artistId) 
    {
        this.artistId = artistId;
    }

    public String getArtistId() 
    {
        return artistId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setAlias(String alias) 
    {
        this.alias = alias;
    }

    public String getAlias() 
    {
        return alias;
    }
    public void setCoverImg(String coverImg) 
    {
        this.coverImg = coverImg;
    }

    public String getCoverImg() 
    {
        return coverImg;
    }
    public void setAlbumSize(Long albumSize) 
    {
        this.albumSize = albumSize;
    }

    public Long getAlbumSize() 
    {
        return albumSize;
    }
    public void setIdentities(String identities) 
    {
        this.identities = identities;
    }

    public String getIdentities() 
    {
        return identities;
    }
    public void setMusicSize(Long musicSize) 
    {
        this.musicSize = musicSize;
    }

    public Long getMusicSize() 
    {
        return musicSize;
    }
    public void setMvSize(Long mvSize) 
    {
        this.mvSize = mvSize;
    }

    public Long getMvSize() 
    {
        return mvSize;
    }
    public void setBriefDesc(String briefDesc) 
    {
        this.briefDesc = briefDesc;
    }

    public String getBriefDesc() 
    {
        return briefDesc;
    }
    public void setIntroduction(String introduction) 
    {
        this.introduction = introduction;
    }

    public String getIntroduction() 
    {
        return introduction;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("artistId", getArtistId())
            .append("name", getName())
            .append("alias", getAlias())
            .append("coverImg", getCoverImg())
            .append("albumSize", getAlbumSize())
            .append("identities", getIdentities())
            .append("musicSize", getMusicSize())
            .append("mvSize", getMvSize())
            .append("briefDesc", getBriefDesc())
            .append("introduction", getIntroduction())
            .toString();
    }
}
