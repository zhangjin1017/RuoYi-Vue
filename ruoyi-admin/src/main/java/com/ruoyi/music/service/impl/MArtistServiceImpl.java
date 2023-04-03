package com.ruoyi.music.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.music.mapper.MArtistMapper;
import com.ruoyi.music.domain.MArtist;
import com.ruoyi.music.service.IMArtistService;

/**
 * 作者信息Service业务层处理
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
@Service
public class MArtistServiceImpl extends ServiceImpl<MArtistMapper,MArtist> implements IMArtistService
{
    @Autowired
    private MArtistMapper mArtistMapper;

    /**
     * 查询作者信息
     * 
     * @param artistId 作者信息主键
     * @return 作者信息
     */
    @Override
    public MArtist selectMArtistByArtistId(String artistId)
    {
        return mArtistMapper.selectMArtistByArtistId(artistId);
    }

    /**
     * 查询作者信息列表
     * 
     * @param mArtist 作者信息
     * @return 作者信息
     */
    @Override
    public List<MArtist> selectMArtistList(MArtist mArtist)
    {
        return mArtistMapper.selectMArtistList(mArtist);
    }

    /**
     * 新增作者信息
     * 
     * @param mArtist 作者信息
     * @return 结果
     */
    @Override
    public int insertMArtist(MArtist mArtist)
    {
        return mArtistMapper.insertMArtist(mArtist);
    }

    /**
     * 修改作者信息
     * 
     * @param mArtist 作者信息
     * @return 结果
     */
    @Override
    public int updateMArtist(MArtist mArtist)
    {
        return mArtistMapper.updateMArtist(mArtist);
    }

    /**
     * 批量删除作者信息
     * 
     * @param artistIds 需要删除的作者信息主键
     * @return 结果
     */
    @Override
    public int deleteMArtistByArtistIds(String[] artistIds)
    {
        return mArtistMapper.deleteMArtistByArtistIds(artistIds);
    }

    /**
     * 删除作者信息信息
     * 
     * @param artistId 作者信息主键
     * @return 结果
     */
    @Override
    public int deleteMArtistByArtistId(String artistId)
    {
        return mArtistMapper.deleteMArtistByArtistId(artistId);
    }
}
