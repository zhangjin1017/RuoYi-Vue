package com.ruoyi.music.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.music.domain.MUserLogin;

/**
 * 用户登录Service接口
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
public interface IMUserLoginService extends IService<MUserLogin>
{
    /**
     * 查询用户登录
     * 
     * @param userId 用户登录主键
     * @return 用户登录
     */
    public MUserLogin selectMUserLoginByUserId(String userId);

    /**
     * 查询用户登录列表
     * 
     * @param mUserLogin 用户登录
     * @return 用户登录集合
     */
    public List<MUserLogin> selectMUserLoginList(MUserLogin mUserLogin);

    /**
     * 新增用户登录
     * 
     * @param mUserLogin 用户登录
     * @return 结果
     */
    public int insertMUserLogin(MUserLogin mUserLogin);

    /**
     * 修改用户登录
     * 
     * @param mUserLogin 用户登录
     * @return 结果
     */
    public int updateMUserLogin(MUserLogin mUserLogin);

    /**
     * 批量删除用户登录
     * 
     * @param userIds 需要删除的用户登录主键集合
     * @return 结果
     */
    public int deleteMUserLoginByUserIds(String[] userIds);

    /**
     * 删除用户登录信息
     * 
     * @param userId 用户登录主键
     * @return 结果
     */
    public int deleteMUserLoginByUserId(String userId);
}
