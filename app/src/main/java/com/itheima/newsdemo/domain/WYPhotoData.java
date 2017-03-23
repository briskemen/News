package com.itheima.newsdemo.domain;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   卢俊霖
 * Email：    briskemen@163.com
 * GitHub：   https://github.com/briskemen
 * Project_Name：Zhbj
 * Package_Name：com.itheima.newsdemo.domain
 * Version：1.0
 * time：2017/3/22 12:15
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/

import java.util.ArrayList;

/**
 * 网易新闻图片
 * URL:http://c.m.163.com/photo/api/set/0001/2244044.json
 */
public class WYPhotoData {
    public String                 autoid; //
    public String                 boardid;//photoview_bbs
    public String                 clientadurl;//
    public String                 commenturl;//http://comment.news.163.com/photoview_bbs/PHOT24FEC000100A.html
    public String                 cover;//http://img3.cache.netease.com/photo/0001/2017-03-22/CG46GH9G00AP0001.jpg
    public String                 createdate;//2017-03-22 07:11:53
    public String                 creator;//耿旭娜
    public String                 datatime;//2017-03-22 07:19:37
    public String                 desc;
    //2017年03月16日，山西省运城市，农历二月二十九，相传当天是观世音菩萨的诞辰，河津市十多名市民购买5000
    // 多元膘肥体大的鲤鱼到黄河岸边放生，分十多框从汽车上抬下来，放入黄河，祈福祝愿，消灾延寿。杨静龙/视觉中国
    public int                 imgsum; //7
    public String                 neteasecode;//
    public ArrayList<PhotosEntry> photos;//	Array
    public String                 postid;//	PHOT24FEC000100A
    public ArrayList              relatedids;//	Array
    public String                 reporter; //
    public String                 scover;//http://img4.cache.netease.com/photo/0001/2017-03-22/s_CG46GH9G00AP0001.jpg
    public String                 series; //
    public String                 setname;//市民黄河岸边放生鲤鱼 后脚便被人捞走售卖
    public String                 settag;//黄河，岸边，放生
    public String                 source;//网易综合
    public String                 tcover;//http://img4.cache.netease.com/photo/0001/2017-03-22/t_CG46GH9G00AP0001.jpg 显示在tab的图片
    public String                    url;//http://news.163.com/photoview/00AP0001/2244044.html  图片URL

    /**
     * 网易头条新闻图片详情页面
     */
    public class PhotosEntry {
        public String    cimgurl;//http://img3.cache.netease.com/photo/0001/2017-03-22/c_CG46GH9G00AP0001.jpg
        public String imgtitle;//
        public String    imgurl;//http://img4.cache.netease.com/photo/0001/2017-03-22/CG46GH9G00AP0001.jpg
        public String    newsurl;//#
        // 图片新闻简介
        public String    note; //2017年03月16日，山西省运城市，农历二月二十九，相传当天是观世音菩萨的诞辰，河津市十多名市民购买5000

        // 图片新闻URl
        public String    photohtml;//http://news.163.com/photoview/00AP0001/2244044.html#p=CG46GH9G00AP0001

        public String    photoid;//CG46GH9G00AP0001
        public String    simgurl;//http://img4.cache.netease.com/photo/0001/2017-03-22/s_CG46GH9G00AP0001.jpg
        public String    squareimgurl;//http://img4.cache.netease.com/photo/0001/2017-03-22/400x400_CG46GH9G00AP0001.jpg
        public String    timgurl;//http://img3.cache.netease.com/photo/0001/2017-03-22/t_CG46GH9G00AP0001.jpg
    }
}
