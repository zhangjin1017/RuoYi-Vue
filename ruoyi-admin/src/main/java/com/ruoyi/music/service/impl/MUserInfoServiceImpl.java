package com.ruoyi.music.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.music.mapper.MUserInfoMapper;
import com.ruoyi.music.domain.MUserInfo;
import com.ruoyi.music.service.IMUserInfoService;

/**
 * 用户信息Service业务层处理
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
@Service
public class MUserInfoServiceImpl extends ServiceImpl<MUserInfoMapper,MUserInfo> implements IMUserInfoService
{
    @Autowired
    private MUserInfoMapper mUserInfoMapper;

    /**
     * 查询用户信息
     * 
     * @param userId 用户信息主键
     * @return 用户信息
     */
    @Override
    public MUserInfo selectMUserInfoByUserId(String userId)
    {
        return mUserInfoMapper.selectMUserInfoByUserId(userId);
    }

    /**
     * 查询用户信息列表
     * 
     * @param mUserInfo 用户信息
     * @return 用户信息
     */
    @Override
    public List<MUserInfo> selectMUserInfoList(MUserInfo mUserInfo)
    {
        return mUserInfoMapper.selectMUserInfoList(mUserInfo);
    }

    /**
     * 新增用户信息
     * 
     * @param mUserInfo 用户信息
     * @return 结果
     */
    @Override
    public int insertMUserInfo(MUserInfo mUserInfo)
    {
        return mUserInfoMapper.insertMUserInfo(mUserInfo);
    }

    /**
     * 修改用户信息
     * 
     * @param mUserInfo 用户信息
     * @return 结果
     */
    @Override
    public int updateMUserInfo(MUserInfo mUserInfo)
    {
        return mUserInfoMapper.updateMUserInfo(mUserInfo);
    }

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户信息主键
     * @return 结果
     */
    @Override
    public int deleteMUserInfoByUserIds(String[] userIds)
    {
        return mUserInfoMapper.deleteMUserInfoByUserIds(userIds);
    }

    /**
     * 删除用户信息信息
     * 
     * @param userId 用户信息主键
     * @return 结果
     */
    @Override
    public int deleteMUserInfoByUserId(String userId)
    {
        return mUserInfoMapper.deleteMUserInfoByUserId(userId);
    }
}
