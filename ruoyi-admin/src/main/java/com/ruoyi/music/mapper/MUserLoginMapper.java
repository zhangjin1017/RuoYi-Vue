package com.ruoyi.music.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.music.domain.MUserLogin;

/**
 * 用户登录Mapper接口
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
public interface MUserLoginMapper extends BaseMapper<MUserLogin>
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
     * 删除用户登录
     * 
     * @param userId 用户登录主键
     * @return 结果
     */
    public int deleteMUserLoginByUserId(String userId);

    /**
     * 批量删除用户登录
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMUserLoginByUserIds(String[] userIds);
}
