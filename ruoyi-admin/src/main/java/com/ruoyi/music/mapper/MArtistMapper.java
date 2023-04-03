package com.ruoyi.music.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.music.domain.MArtist;

/**
 * 作者信息Mapper接口
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
public interface MArtistMapper  extends BaseMapper<MArtist>
{
    /**
     * 查询作者信息
     * 
     * @param artistId 作者信息主键
     * @return 作者信息
     */
    public MArtist selectMArtistByArtistId(String artistId);

    /**
     * 查询作者信息列表
     * 
     * @param mArtist 作者信息
     * @return 作者信息集合
     */
    public List<MArtist> selectMArtistList(MArtist mArtist);

    /**
     * 新增作者信息
     * 
     * @param mArtist 作者信息
     * @return 结果
     */
    public int insertMArtist(MArtist mArtist);

    /**
     * 修改作者信息
     * 
     * @param mArtist 作者信息
     * @return 结果
     */
    public int updateMArtist(MArtist mArtist);

    /**
     * 删除作者信息
     * 
     * @param artistId 作者信息主键
     * @return 结果
     */
    public int deleteMArtistByArtistId(String artistId);

    /**
     * 批量删除作者信息
     * 
     * @param artistIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMArtistByArtistIds(String[] artistIds);
}
