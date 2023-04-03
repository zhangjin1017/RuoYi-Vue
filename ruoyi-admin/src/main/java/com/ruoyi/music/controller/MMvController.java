package com.ruoyi.music.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.music.domain.MArtist;
import com.ruoyi.music.util.RandomUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.music.domain.MMv;
import com.ruoyi.music.service.IMMvService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * Mv信息Controller
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
@RestController
@RequestMapping("/music/mv")
public class MMvController extends BaseController
{
    @Autowired
    private IMMvService mMvService;

    /**
     * 查询Mv信息列表
     */
    @PreAuthorize("@ss.hasPermi('music:mv:list')")
    @GetMapping("/list")
    public TableDataInfo list(MMv mMv)
    {
        startPage();
        List<MMv> list = mMvService.selectMMvList(mMv);
        return getDataTable(list);
    }

    /**
     * 导出Mv信息列表
     */
    @PreAuthorize("@ss.hasPermi('music:mv:export')")
    @Log(title = "Mv信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MMv mMv)
    {
        List<MMv> list = mMvService.selectMMvList(mMv);
        ExcelUtil<MMv> util = new ExcelUtil<MMv>(MMv.class);
        util.exportExcel(response, list, "Mv信息数据");
    }

    /**
     * 获取Mv信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('music:mv:query')")
    @GetMapping(value = "/{mvId}")
    public AjaxResult getInfo(@PathVariable("mvId") String mvId)
    {
        return success(mMvService.selectMMvByMvId(mvId));
    }

    /**
     * 新增Mv信息
     */
    @PreAuthorize("@ss.hasPermi('music:mv:add')")
    @Log(title = "Mv信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MMv mMv)
    {
        //TODO: 添加Mv时生成随机的MVId
        mMv.setMvId("M"+ RandomUtils.getGnerateNumberString(10));
        return toAjax(mMvService.insertMMv(mMv));
    }

    /**
     * 修改Mv信息
     */
    @PreAuthorize("@ss.hasPermi('music:mv:edit')")
    @Log(title = "Mv信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MMv mMv)
    {
        return toAjax(mMvService.updateMMv(mMv));
    }

    /**
     * 删除Mv信息
     */
    @PreAuthorize("@ss.hasPermi('music:mv:remove')")
    @Log(title = "Mv信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{mvIds}")
    public AjaxResult remove(@PathVariable String[] mvIds)
    {
        return toAjax(mMvService.deleteMMvByMvIds(mvIds));
    }

    /**
     * 保存Mv信息
     */
    @PostMapping("/saveNewMv")
    public AjaxResult saveNewMv(@RequestBody MMv mMv)
    {
        if (mMvService.getById(mMv.getMvId())==null){
            mMvService.save(mMv);
            return success("保存成功");
        }else {
            return success("已存在");
        }

    }

}
