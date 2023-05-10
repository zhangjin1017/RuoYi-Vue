package com.ruoyi.music.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.music.domain.MUserInfo;
import com.ruoyi.music.domain.MUserLogin;
import com.ruoyi.music.service.IMUserInfoService;
import com.ruoyi.music.service.IMUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 用户信息Controller
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
@RestController
@RequestMapping("/music/info")
@CrossOrigin
public class MUserInfoController extends BaseController
{
    @Autowired
    private IMUserInfoService mUserInfoService;

    @Autowired
    private IMUserLoginService mUserLoginService;

    /**
     * 查询用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('music:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(MUserInfo mUserInfo)
    {
        startPage();
        List<MUserInfo> list = mUserInfoService.selectMUserInfoList(mUserInfo);
        return getDataTable(list);
    }

    /**
     * 导出用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('music:info:export')")
    @Log(title = "用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MUserInfo mUserInfo)
    {
        List<MUserInfo> list = mUserInfoService.selectMUserInfoList(mUserInfo);
        ExcelUtil<MUserInfo> util = new ExcelUtil<MUserInfo>(MUserInfo.class);
        util.exportExcel(response, list, "用户信息数据");
    }

    /**
     * 获取用户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('music:info:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") String userId)
    {
        return success(mUserInfoService.selectMUserInfoByUserId(userId));
    }

    /**
     * 新增用户信息
     */
    @PreAuthorize("@ss.hasPermi('music:info:add')")
    @Log(title = "用户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MUserInfo mUserInfo)
    {
        return toAjax(mUserInfoService.insertMUserInfo(mUserInfo));
    }

    /**
     * 修改用户信息
     */
    @PreAuthorize("@ss.hasPermi('music:info:edit')")
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MUserInfo mUserInfo)
    {
        return toAjax(mUserInfoService.updateMUserInfo(mUserInfo));
    }

    /**
     * 删除用户信息
     */
    @PreAuthorize("@ss.hasPermi('music:info:remove')")
    @Log(title = "用户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable String[] userIds)
    {
        return toAjax(mUserInfoService.deleteMUserInfoByUserIds(userIds));
    }

    /**
     * 修改密码
     * @param userId
     * @param oldpass
     * @param newpass
     * @return
     */
    @RequestMapping("/modifyPassword/{userId}/{oldpass}/{newpass}")
    public AjaxResult modifyPassword(@PathVariable("userId") String userId, @PathVariable("oldpass") String oldpass, @PathVariable("newpass") String newpass) {
        MUserLogin mUserLogin = mUserLoginService.getById(userId);
        if (mUserLogin == null) {
            return error("用户不存在");
        }
        if (!mUserLogin.getPassword().equals(oldpass)) {
            return error("旧密码错误");
        }
        mUserLogin.setPassword(newpass);
        mUserLoginService.updateById(mUserLogin);
        return success("修改成功");
    }


/**
     * 修改信息
     * @param userId
     * @param newName
     * @param newIntroduction
     * @return
     */
    @RequestMapping("/modifyInfo/{userId}/{newName}/{newIntroduction}")
    public AjaxResult modifyInfo(@PathVariable("userId") String userId, @PathVariable("newName") String newName, @PathVariable("newIntroduction") String newIntroduction) {

        MUserInfo mUserInfo = mUserInfoService.getById(userId);
        if (mUserInfo == null) {
            return error("用户不存在");
        }
        mUserInfo.setName(newName);
        mUserInfo.setIntroduction(newIntroduction);
        mUserInfoService.updateById(mUserInfo);
        return success("修改成功");
    }

    @PostMapping("/uploadAvatar")
    public String uploadAvatar(@RequestParam MultipartFile file) throws IOException, InvalidExtensionException {

        if (file.isEmpty())
        {
            return "error";
        }
        return FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
    }

    /**
     * 修改头像
     * @param userId
     * @param img
     * @return
     * @throws IOException
     * @throws InvalidExtensionException
     */
    @RequestMapping("/modifyImg")
    public AjaxResult modifyImg(@RequestParam("userId") String userId, @RequestParam("img") String img){

        MUserInfo mUserInfo = mUserInfoService.getById(userId);
        if (mUserInfo == null) {
            return error("用户不存在");
        }
           mUserInfo.setImg(img);
        if (mUserInfoService.updateById(mUserInfo)){
            return success("修改成功");
        }
       return error("修改失败");
    }
}
