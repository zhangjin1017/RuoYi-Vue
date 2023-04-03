package com.ruoyi.music.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
import com.ruoyi.music.domain.MArtist;
import com.ruoyi.music.service.IMArtistService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 作者信息Controller
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
@RestController
@RequestMapping("/music/artist")
public class MArtistController extends BaseController
{
    @Autowired
    private IMArtistService mArtistService;

    /**
     * 查询作者信息列表
     */
    @PreAuthorize("@ss.hasPermi('music:artist:list')")
    @GetMapping("/list")
    public TableDataInfo list(MArtist mArtist)
    {
        startPage();
        List<MArtist> list = mArtistService.selectMArtistList(mArtist);
        return getDataTable(list);
    }

    /**
     * 导出作者信息列表
     */
    @PreAuthorize("@ss.hasPermi('music:artist:export')")
    @Log(title = "作者信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MArtist mArtist)
    {
        List<MArtist> list = mArtistService.selectMArtistList(mArtist);
        ExcelUtil<MArtist> util = new ExcelUtil<MArtist>(MArtist.class);
        util.exportExcel(response, list, "作者信息数据");
    }

    /**
     * 获取作者信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('music:artist:query')")
    @GetMapping(value = "/{artistId}")
    public AjaxResult getInfo(@PathVariable("artistId") String artistId)
    {
        return success(mArtistService.selectMArtistByArtistId(artistId));
    }

    /**
     * 新增作者信息
     */
    @PreAuthorize("@ss.hasPermi('music:artist:add')")
    @Log(title = "作者信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MArtist mArtist)
    {
        //TODO: 添加作者时生成随机的artistId
        mArtist.setArtistId("a"+ RandomUtils.getGnerateNumberString(10));
        return toAjax(mArtistService.insertMArtist(mArtist));
    }

    /**
     * 修改作者信息
     */
    @PreAuthorize("@ss.hasPermi('music:artist:edit')")
    @Log(title = "作者信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MArtist mArtist)
    {
        return toAjax(mArtistService.updateMArtist(mArtist));
    }

    /**
     * 删除作者信息
     */
    @PreAuthorize("@ss.hasPermi('music:artist:remove')")
    @Log(title = "作者信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{artistIds}")
    public AjaxResult remove(@PathVariable String[] artistIds)
    {
        return toAjax(mArtistService.deleteMArtistByArtistIds(artistIds));
    }
    /**
     * 保存作者信息
     */
    @PostMapping("/saveNewArtist")
    public AjaxResult saveNewArtist(@RequestBody MArtist mArtist)
    {
        if (mArtistService.getById(mArtist.getArtistId())==null){
            mArtistService.save(mArtist);
            return success("保存成功");
        }else {
            return success("已存在");
        }

    }




}
