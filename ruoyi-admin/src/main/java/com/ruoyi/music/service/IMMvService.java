package com.ruoyi.music.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.music.domain.MMv;

/**
 * Mv信息Service接口
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
public interface IMMvService  extends IService<MMv>
{
    /**
     * 查询Mv信息
     * 
     * @param mvId Mv信息主键
     * @return Mv信息
     */
    public MMv selectMMvByMvId(String mvId);

    /**
     * 查询Mv信息列表
     * 
     * @param mMv Mv信息
     * @return Mv信息集合
     */
    public List<MMv> selectMMvList(MMv mMv);

    /**
     * 新增Mv信息
     * 
     * @param mMv Mv信息
     * @return 结果
     */
    public int insertMMv(MMv mMv);

    /**
     * 修改Mv信息
     * 
     * @param mMv Mv信息
     * @return 结果
     */
    public int updateMMv(MMv mMv);

    /**
     * 批量删除Mv信息
     * 
     * @param mvIds 需要删除的Mv信息主键集合
     * @return 结果
     */
    public int deleteMMvByMvIds(String[] mvIds);

    /**
     * 删除Mv信息信息
     * 
     * @param mvId Mv信息主键
     * @return 结果
     */
    public int deleteMMvByMvId(String mvId);
}
