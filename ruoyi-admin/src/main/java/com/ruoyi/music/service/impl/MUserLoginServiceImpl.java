package com.ruoyi.music.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.music.domain.MUserLogin;
import com.ruoyi.music.mapper.MUserLoginMapper;
import com.ruoyi.music.service.IMUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户登录Service业务层处理
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
@Service
public class MUserLoginServiceImpl extends ServiceImpl<MUserLoginMapper,MUserLogin> implements IMUserLoginService
{
    @Autowired
    private MUserLoginMapper mUserLoginMapper;

    /**
     * 查询用户登录
     * 
     * @param userId 用户登录主键
     * @return 用户登录
     */
    @Override
    public MUserLogin selectMUserLoginByUserId(String userId)
    {
        return mUserLoginMapper.selectMUserLoginByUserId(userId);
    }

    /**
     * 查询用户登录列表
     * 
     * @param mUserLogin 用户登录
     * @return 用户登录
     */
    @Override
    public List<MUserLogin> selectMUserLoginList(MUserLogin mUserLogin)
    {
        return mUserLoginMapper.selectMUserLoginList(mUserLogin);
    }

    /**
     * 新增用户登录
     * 
     * @param mUserLogin 用户登录
     * @return 结果
     */
    @Override
    public int insertMUserLogin(MUserLogin mUserLogin)
    {
        mUserLogin.setCreateTime(DateUtils.getNowDate());
        return mUserLoginMapper.insertMUserLogin(mUserLogin);
    }

    /**
     * 修改用户登录
     * 
     * @param mUserLogin 用户登录
     * @return 结果
     */
    @Override
    public int updateMUserLogin(MUserLogin mUserLogin)
    {
        return mUserLoginMapper.updateMUserLogin(mUserLogin);
    }

    /**
     * 批量删除用户登录
     * 
     * @param userIds 需要删除的用户登录主键
     * @return 结果
     */
    @Override
    public int deleteMUserLoginByUserIds(String[] userIds)
    {
        return mUserLoginMapper.deleteMUserLoginByUserIds(userIds);
    }

    /**
     * 删除用户登录信息
     * 
     * @param userId 用户登录主键
     * @return 结果
     */
    @Override
    public int deleteMUserLoginByUserId(String userId)
    {
        return mUserLoginMapper.deleteMUserLoginByUserId(userId);
    }
}
