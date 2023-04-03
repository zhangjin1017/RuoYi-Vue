package com.ruoyi.music.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ruoyi.music.util.RandomUtils;
import org.springframework.http.HttpRequest;
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
import com.ruoyi.music.domain.MMusic;
import com.ruoyi.music.service.IMMusicService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 歌曲信息Controller
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
@RestController
@RequestMapping("/music/music")
public class MMusicController extends BaseController
{
    @Autowired
    private IMMusicService mMusicService;

    /**
     * 查询歌曲信息列表
     */
    @PreAuthorize("@ss.hasPermi('music:music:list')")
    @GetMapping("/list")
    public TableDataInfo list(MMusic mMusic)
    {
        startPage();
        List<MMusic> list = mMusicService.selectMMusicList(mMusic);
        return getDataTable(list);
    }

    /**
     * 导出歌曲信息列表
     */
    @PreAuthorize("@ss.hasPermi('music:music:export')")
    @Log(title = "歌曲信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MMusic mMusic)
    {
        List<MMusic> list = mMusicService.selectMMusicList(mMusic);
        ExcelUtil<MMusic> util = new ExcelUtil<MMusic>(MMusic.class);
        util.exportExcel(response, list, "歌曲信息数据");
    }

    /**
     * 获取歌曲信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('music:music:query')")
    @GetMapping(value = "/{musicId}")
    public AjaxResult getInfo(@PathVariable("musicId") String musicId)
    {
        return success(mMusicService.selectMMusicByMusicId(musicId));
    }

    /**
     * 新增歌曲信息
     */
    @PreAuthorize("@ss.hasPermi('music:music:add')")
    @Log(title = "歌曲信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MMusic mMusic)
    {
        //TODO: 添加音乐时生成随机的musicId
        mMusic.setMusicId("m"+ RandomUtils.getGnerateNumberString(10));
        return toAjax(mMusicService.insertMMusic(mMusic));
    }

    /**
     * 修改歌曲信息
     */
    @PreAuthorize("@ss.hasPermi('music:music:edit')")
    @Log(title = "歌曲信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MMusic mMusic)
    {
        return toAjax(mMusicService.updateMMusic(mMusic));
    }

    /**
     * 删除歌曲信息
     */
    @PreAuthorize("@ss.hasPermi('music:music:remove')")
    @Log(title = "歌曲信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{musicIds}")
    public AjaxResult remove(@PathVariable String[] musicIds)
    {
        return toAjax(mMusicService.deleteMMusicByMusicIds(musicIds));
    }

    /**
     * 根据用户id进行个性推荐
     */
    @PreAuthorize("@ss.hasPermi('music:music:recommend')")
    @Log(title = "歌曲信息", businessType = BusinessType.RECOMMEND)
    @GetMapping("/recommend/{userId}")
    public AjaxResult recommend(@PathVariable("userId") String userId)
    {
//        mMusicService.recommend(userId)
        return success("推荐成功");
    }

    /**
     * 保存首页最新音乐
     */
    @PostMapping("/saveNewMusic")
    public AjaxResult saveNewMusic(@RequestBody MMusic mMusic)
    {
//        mMusicService.saveOrUpdate(mMusic);
        if (mMusicService.getById(mMusic.getMusicId())==null){
            mMusicService.save(mMusic);
            return success("保存成功");
        }else {
            return success("已存在");
        }

    }

}
