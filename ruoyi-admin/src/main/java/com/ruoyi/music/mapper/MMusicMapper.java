package com.ruoyi.music.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.music.domain.MMusic;

/**
 * 歌曲信息Mapper接口
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
public interface MMusicMapper extends BaseMapper<MMusic>
{
    /**
     * 查询歌曲信息
     * 
     * @param musicId 歌曲信息主键
     * @return 歌曲信息
     */
    public MMusic selectMMusicByMusicId(String musicId);

    /**
     * 查询歌曲信息列表
     * 
     * @param mMusic 歌曲信息
     * @return 歌曲信息集合
     */
    public List<MMusic> selectMMusicList(MMusic mMusic);

    /**
     * 新增歌曲信息
     * 
     * @param mMusic 歌曲信息
     * @return 结果
     */
    public int insertMMusic(MMusic mMusic);

    /**
     * 修改歌曲信息
     * 
     * @param mMusic 歌曲信息
     * @return 结果
     */
    public int updateMMusic(MMusic mMusic);

    /**
     * 删除歌曲信息
     * 
     * @param musicId 歌曲信息主键
     * @return 结果
     */
    public int deleteMMusicByMusicId(String musicId);

    /**
     * 批量删除歌曲信息
     * 
     * @param musicIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMMusicByMusicIds(String[] musicIds);
}
