package com.ruoyi.music.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.music.domain.MUserInfo;

/**
 * 用户信息Mapper接口
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
public interface MUserInfoMapper extends BaseMapper<MUserInfo>
{
    /**
     * 查询用户信息
     * 
     * @param userId 用户信息主键
     * @return 用户信息
     */
    public MUserInfo selectMUserInfoByUserId(String userId);

    /**
     * 查询用户信息列表
     * 
     * @param mUserInfo 用户信息
     * @return 用户信息集合
     */
    public List<MUserInfo> selectMUserInfoList(MUserInfo mUserInfo);

    /**
     * 新增用户信息
     * 
     * @param mUserInfo 用户信息
     * @return 结果
     */
    public int insertMUserInfo(MUserInfo mUserInfo);

    /**
     * 修改用户信息
     * 
     * @param mUserInfo 用户信息
     * @return 结果
     */
    public int updateMUserInfo(MUserInfo mUserInfo);

    /**
     * 删除用户信息
     * 
     * @param userId 用户信息主键
     * @return 结果
     */
    public int deleteMUserInfoByUserId(String userId);

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMUserInfoByUserIds(String[] userIds);
}
