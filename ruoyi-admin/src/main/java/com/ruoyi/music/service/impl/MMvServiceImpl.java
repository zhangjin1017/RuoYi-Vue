package com.ruoyi.music.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.music.mapper.MMvMapper;
import com.ruoyi.music.domain.MMv;
import com.ruoyi.music.service.IMMvService;

/**
 * Mv信息Service业务层处理
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
@Service
public class MMvServiceImpl extends ServiceImpl<MMvMapper,MMv> implements IMMvService
{
    @Autowired
    private MMvMapper mMvMapper;

    /**
     * 查询Mv信息
     * 
     * @param mvId Mv信息主键
     * @return Mv信息
     */
    @Override
    public MMv selectMMvByMvId(String mvId)
    {
        return mMvMapper.selectMMvByMvId(mvId);
    }

    /**
     * 查询Mv信息列表
     * 
     * @param mMv Mv信息
     * @return Mv信息
     */
    @Override
    public List<MMv> selectMMvList(MMv mMv)
    {
        return mMvMapper.selectMMvList(mMv);
    }

    /**
     * 新增Mv信息
     * 
     * @param mMv Mv信息
     * @return 结果
     */
    @Override
    public int insertMMv(MMv mMv)
    {
        return mMvMapper.insertMMv(mMv);
    }

    /**
     * 修改Mv信息
     * 
     * @param mMv Mv信息
     * @return 结果
     */
    @Override
    public int updateMMv(MMv mMv)
    {
        return mMvMapper.updateMMv(mMv);
    }

    /**
     * 批量删除Mv信息
     * 
     * @param mvIds 需要删除的Mv信息主键
     * @return 结果
     */
    @Override
    public int deleteMMvByMvIds(String[] mvIds)
    {
        return mMvMapper.deleteMMvByMvIds(mvIds);
    }

    /**
     * 删除Mv信息信息
     * 
     * @param mvId Mv信息主键
     * @return 结果
     */
    @Override
    public int deleteMMvByMvId(String mvId)
    {
        return mMvMapper.deleteMMvByMvId(mvId);
    }
}
