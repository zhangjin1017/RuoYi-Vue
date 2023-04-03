package com.ruoyi.music.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.music.domain.MArtist;

/**
 * 作者信息Service接口
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
public interface IMArtistService extends IService<MArtist>
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
     * 批量删除作者信息
     * 
     * @param artistIds 需要删除的作者信息主键集合
     * @return 结果
     */
    public int deleteMArtistByArtistIds(String[] artistIds);

    /**
     * 删除作者信息信息
     * 
     * @param artistId 作者信息主键
     * @return 结果
     */
    public int deleteMArtistByArtistId(String artistId);
}
