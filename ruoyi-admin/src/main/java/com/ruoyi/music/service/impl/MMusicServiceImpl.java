package com.ruoyi.music.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.music.mapper.MMusicMapper;
import com.ruoyi.music.domain.MMusic;
import com.ruoyi.music.service.IMMusicService;

/**
 * 歌曲信息Service业务层处理
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
@Service
public class MMusicServiceImpl extends ServiceImpl<MMusicMapper,MMusic> implements IMMusicService
{
    @Autowired
    private MMusicMapper mMusicMapper;

    /**
     * 查询歌曲信息
     * 
     * @param musicId 歌曲信息主键
     * @return 歌曲信息
     */
    @Override
    public MMusic selectMMusicByMusicId(String musicId)
    {
        return mMusicMapper.selectMMusicByMusicId(musicId);
    }

    /**
     * 查询歌曲信息列表
     * 
     * @param mMusic 歌曲信息
     * @return 歌曲信息
     */
    @Override
    public List<MMusic> selectMMusicList(MMusic mMusic)
    {
        return mMusicMapper.selectMMusicList(mMusic);
    }

    /**
     * 新增歌曲信息
     * 
     * @param mMusic 歌曲信息
     * @return 结果
     */
    @Override
    public int insertMMusic(MMusic mMusic)
    {
        return mMusicMapper.insertMMusic(mMusic);
    }

    /**
     * 修改歌曲信息
     * 
     * @param mMusic 歌曲信息
     * @return 结果
     */
    @Override
    public int updateMMusic(MMusic mMusic)
    {
        return mMusicMapper.updateMMusic(mMusic);
    }

    /**
     * 批量删除歌曲信息
     * 
     * @param musicIds 需要删除的歌曲信息主键
     * @return 结果
     */
    @Override
    public int deleteMMusicByMusicIds(String[] musicIds)
    {
        return mMusicMapper.deleteMMusicByMusicIds(musicIds);
    }

    /**
     * 删除歌曲信息信息
     * 
     * @param musicId 歌曲信息主键
     * @return 结果
     */
    @Override
    public int deleteMMusicByMusicId(String musicId)
    {
        return mMusicMapper.deleteMMusicByMusicId(musicId);
    }
}
