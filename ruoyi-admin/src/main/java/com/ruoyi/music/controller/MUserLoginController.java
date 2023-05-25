package com.ruoyi.music.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.music.domain.MPlaylist;
import com.ruoyi.music.domain.MUserInfo;
import com.ruoyi.music.domain.MUserLogin;
import com.ruoyi.music.service.IMPlaylistService;
import com.ruoyi.music.service.IMUserInfoService;
import com.ruoyi.music.service.IMUserLoginService;
import com.ruoyi.music.util.RandomUtils;
import com.ruoyi.music.verify.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 用户登录Controller
 * 
 * @author ZzzjzzZ
 * @date 2023-03-27
 */
@RestController
@RequestMapping("/music/login")
@CrossOrigin
public class MUserLoginController extends BaseController
{
    @Autowired
    private IMUserLoginService mUserLoginService;
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    VerifyService verifyService;
    @Resource
    IMUserLoginService userLoginService;
    @Resource
    IMUserInfoService userInfoService;

    @Resource
    IMPlaylistService playlistService;
    /**
     * 查询用户登录列表
     */
    @PreAuthorize("@ss.hasPermi('music:login:list')")
    @GetMapping("/list")
    public TableDataInfo list(MUserLogin mUserLogin)
    {
        startPage();
        List<MUserLogin> list = mUserLoginService.selectMUserLoginList(mUserLogin);
        return getDataTable(list);
    }

    /**
     * 导出用户登录列表
     */
    @PreAuthorize("@ss.hasPermi('music:login:export')")
    @Log(title = "用户登录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MUserLogin mUserLogin)
    {
        List<MUserLogin> list = mUserLoginService.selectMUserLoginList(mUserLogin);
        ExcelUtil<MUserLogin> util = new ExcelUtil<MUserLogin>(MUserLogin.class);
        util.exportExcel(response, list, "用户登录数据");
    }

    /**
     * 获取用户登录详细信息
     */
    @PreAuthorize("@ss.hasPermi('music:login:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") String userId)
    {
        return success(mUserLoginService.selectMUserLoginByUserId(userId));
    }

    /**
     * 新增用户登录
     */
    @Transactional
    @PreAuthorize("@ss.hasPermi('music:login:add')")
    @Log(title = "用户登录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MUserLogin mUserLogin)
    {
        //生成用户id, "u" + 10位随机数
        String userId = "u" + RandomUtils.getGnerateNumberString(10);
        mUserLogin.setUserId(userId);
        mUserLogin.setCreateTime(new Date());

        //注册成功之后，向userinfo表中插入一条数据
        MUserInfo userInfo = new MUserInfo();
        userInfo.setUserId(userId);
        userInfo.setName("用户" + RandomUtils.getGnerateNumberString(6));
        userInfo.setIntroduction("这个人很懒，什么都没有留下！");
        userInfo.setListenTime(0L);
        userInfoService.insertMUserInfo(userInfo);
        return toAjax(mUserLoginService.insertMUserLogin(mUserLogin));
    }

    /**
     * 修改用户登录
     */
    @PreAuthorize("@ss.hasPermi('music:login:edit')")
    @Log(title = "用户登录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MUserLogin mUserLogin)
    {
        return toAjax(mUserLoginService.updateMUserLogin(mUserLogin));
    }

    /**
     * 删除用户登录
     */
    @PreAuthorize("@ss.hasPermi('music:login:remove')")
    @Log(title = "用户登录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable String[] userIds)
    {
        return toAjax(mUserLoginService.deleteMUserLoginByUserIds(userIds));
    }

    /**
     * 使用密码登录
     */
    @RequestMapping("/loginByPassword/{account}/{password}")
    public AjaxResult loginByPassword(@PathVariable String account,@PathVariable String password) {
        LambdaQueryWrapper<MUserLogin> lqw = new LambdaQueryWrapper<>();
        lqw.eq(MUserLogin::getEmail, account);
        List<MUserLogin> list = userLoginService.list(lqw);
        if (list.size() == 1) {
            MUserLogin userLogin = list.get(0);
            if (userLogin.getPassword().equals(password)) {
                //向redis的list中加入该用户的id

                redisTemplate.opsForSet().add("ZzzjzzZ-Music:online_users", userLogin.getUserId());
                MUserInfo userInfo = userInfoService.getById(userLogin.getUserId());
                userInfo.setEmail(userLogin.getEmail());
                userInfo.setCreateTime(userLogin.getCreateTime());
                userInfo.setVipType(0);
                //生成cookie
                userInfo.setCookie(RandomUtils.getGenerateString(32));
                return new AjaxResult(200, "登录成功！", userInfo);
            } else {
                return new AjaxResult(500, "登录失败！密码错误！");
            }
        } else {
            return new AjaxResult(500, "登录失败！该邮箱不存在，请先注册！");
        }
    }
    /**
     * 发送邮箱验证码
     */
    @RequestMapping("/sendVerifyCodeForLogin/{email}")
    public AjaxResult sendVerifyCodeForLogin(@PathVariable String email) {
        LambdaQueryWrapper<MUserLogin> lqw = new LambdaQueryWrapper<>();
        lqw.eq(MUserLogin::getEmail, email);
        List<MUserLogin> list = userLoginService.list(lqw);
        if (list.size() == 1) {
            //先判断是否发送过验证码
            if (redisTemplate.hasKey("ZzzjzzZ-Music:"+email)) {
                //如果发送过验证码
                return new AjaxResult(500, "邮件已发送！请查看QQ邮箱！");
            } else {
                int code = verifyService.sendVerifyCode(email);
                //将code存到redis中，超时时间为3分钟
                redisTemplate.opsForValue().set("ZzzjzzZ-Music:"+email, code+"", 60, TimeUnit.SECONDS);
            }
            return new AjaxResult(200, "邮件发送成功！");
        } else {
            return new AjaxResult(500, "邮件发送失败！该邮箱不存在，请先注册！");
        }

    }

    @RequestMapping("/sendVerifyCodeForRegister/{email}")
    public AjaxResult sendVerifyCodeForRegister(@PathVariable String email) {


            //先判断是否发送过验证码
            if (redisTemplate.hasKey("ZzzjzzZ-Music:"+email)) {
                //如果发送过验证码
                return new AjaxResult(500, "邮件已发送！请查看QQ邮箱！");
            } else {
                int code = verifyService.sendVerifyCode(email);
                //将code存到redis中，超时时间为3分钟
                redisTemplate.opsForValue().set("ZzzjzzZ-Music:"+email, code+"", 60, TimeUnit.SECONDS);
            }
            return new AjaxResult(200, "邮件发送成功！");


    }

    /**
     * 验证邮箱验证码
     */
    @RequestMapping("/doVerifyCodeForLogin/{email}/{code}")
    public AjaxResult doVerifyCodeForLogin(@PathVariable String email, @PathVariable String code) {

        if (redisTemplate.opsForValue().get("ZzzjzzZ-Music:"+email).equals(code)) {
            //验证码正确
            //删除redis中的验证码
            redisTemplate.delete("ZzzjzzZ-Music:"+email);
            //根据email查询用户
            LambdaQueryWrapper<MUserLogin> lqw = new LambdaQueryWrapper<>();
            lqw.eq(MUserLogin::getEmail, email);
            List<MUserLogin> list = userLoginService.list(lqw);
            MUserLogin userLogin = list.get(0);

            //向redis的list中加入该用户的id
            redisTemplate.opsForSet().add("ZzzjzzZ-Music:online_users", userLogin.getUserId());
            MUserInfo userInfo = userInfoService.getById(userLogin.getUserId());
            userInfo.setEmail(userLogin.getEmail());
            userInfo.setCreateTime(userLogin.getCreateTime());
            userInfo.setVipType(0);
            //生成cookie
            userInfo.setCookie(RandomUtils.getGenerateString(32));
            return new AjaxResult(200, "验证码正确！", userInfo);

        } else {
            //验证码错误
            return new AjaxResult(500, "验证码错误！");
        }

    }
    @Transactional
    @RequestMapping("/doVerifyCodeForRegister/{email}/{code}/{password}")
    public AjaxResult doVerifyCodeForRegister(@PathVariable String email, @PathVariable String code, @PathVariable String password) {

        if (code.equals(redisTemplate.opsForValue().get("ZzzjzzZ-Music:"+email))) {
            //验证码正确
            //删除redis中的验证码
            redisTemplate.delete("ZzzjzzZ-Music:"+email);

            //根据邮箱查询用户是否存在
            LambdaQueryWrapper<MUserLogin> lqw = new LambdaQueryWrapper<>();
            lqw.eq(MUserLogin::getEmail, email);
            List<MUserLogin> list = userLoginService.list(lqw);
            if (list.size() == 1) {
                //用户已存在
                return new AjaxResult(500, "该邮箱已注册！");
            }

            //注册
            MUserLogin userLogin = new MUserLogin();
            //生成用户id, "u" + 10位随机数
            String userId = "u" + RandomUtils.getGnerateNumberString(10);
            userLogin.setUserId(userId);
            userLogin.setEmail(email);
            userLogin.setPassword(password);
            userLogin.setCreateTime(new Date());

            //注册成功之后，向userinfo表中插入一条数据
            MUserInfo userInfo = new MUserInfo();
            userInfo.setUserId(userId);
//            userInfo.setImg("/profile/upload/2023/04/04/morenimg_20230404155159A001.jpg");
            userInfo.setName("用户" + RandomUtils.getGnerateNumberString(6));
            userInfo.setIntroduction("这个人很懒，什么都没有留下！");
            userInfo.setGender("2");
            userInfo.setAge(0L);
            userInfo.setListenTime(0L);
            //注册成功之后，向歌单表中插入一条数据
            MPlaylist playlist = new MPlaylist();
            playlist.setId("pl" + RandomUtils.getGnerateNumberString(10));
            playlist.setUserId(userId);
            playlist.setUserName(userInfo.getName());
            playlist.setDescription("这个人很懒，什么都没有留下！");
            playlist.setCreateTime(new Date());


            return toAjax(userLoginService.save(userLogin)&&userInfoService.save(userInfo)&&playlistService.save(playlist));
//            return new AjaxResult(200, "注册成功！");
        } else {
            //验证码错误
            return new AjaxResult(500, "验证码错误！");
        }

    }
    //退出登录
    @RequestMapping("/logout/{userId}")
    public AjaxResult logout(@PathVariable String userId) {
            //删除redis中的用户id
            redisTemplate.opsForSet().remove("ZzzjzzZ-Music:online_users", userId);
            return new AjaxResult(200, "退出成功！");
    }
    //获取在线用户
    @RequestMapping("/getOnlineUsers")
    public AjaxResult getOnlineUsers() {

        Set<String> onlineUsers = redisTemplate.opsForSet().members("ZzzjzzZ-Music:online_users");
        List<MUserInfo> list = new ArrayList<>();
        for (String userId : onlineUsers) {
            MUserInfo userInfo = userInfoService.getById(userId);
            list.add(userInfo);
        }
        return new AjaxResult(200, "获取在线用户成功！", list);
    }

}
