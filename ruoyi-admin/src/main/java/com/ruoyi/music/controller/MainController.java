package com.ruoyi.music.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.music.domain.MMusic;
import com.ruoyi.music.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/main")
@CrossOrigin
public class MainController extends BaseController {

    @Autowired
    private MUserInfoServiceImpl mUserInfoService;
    @Autowired
    private MMusicServiceImpl mMusicService;
    @Autowired
    private MArtistServiceImpl mArtistService;
    @Autowired
    private MMvServiceImpl mMvService;
    @Autowired
    private MPlayrecordServiceImpl mPlayrecordService;

    /**
     * 获取首页数据
     */
    @RequestMapping("/getMainData")
    public AjaxResult getMainData() {
        //获取总用户数、歌曲数、作者数、mv数
        int users = mUserInfoService.list().size();
        int musics = mMusicService.list().size();
        int artists = mArtistService.list().size();
        int mvs = mMvService.list().size();

        //获取1-12月的发布的歌曲数
        int[] months = new int[12];
        months[0] = 0;
        List<MMusic> musicList = mMusicService.list();
        for (int i = 0; i < musics; i++) {
            //获取每个月的歌曲数
            String releaseDate = dateToString(musicList.get(i).getPublicTime()); // 获取歌曲的发布日期
            System.out.println("------");
            System.out.println(releaseDate);
            String month = releaseDate.substring(5, 7); // 获取歌曲的发布月份
            System.out.println(month);
            int monthInt = Integer.parseInt(month); // 将月份转换为int类型
            months[monthInt-1] += 1;

        }

        return AjaxResult.success().put("users", users).put("musics", musics).put("artists", artists).put("mvs", mvs).put("months", months);
    }

    //将date转换为String类型
    public String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(date);
        return str;
    }

    /*
    *  测试
     */
    @RequestMapping("/test")
    public AjaxResult test() {
        System.out.println( "test");
        return AjaxResult.success().put("test", "test");
    }
}
