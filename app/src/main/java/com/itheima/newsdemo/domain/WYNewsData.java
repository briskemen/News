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
 * time：2017/3/21 15:51
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/

import java.util.ArrayList;

/**
 * 网易新闻内容数据
 * URL:http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
 */
public class WYNewsData {
    public ArrayList<TopNews> T1348647909107;

    // ui activity

    // volley

    public class TopNews {
        public ArrayList<AdsData>      ads;// 图片新闻
        public ArrayList<ImgextraData> imgextra;//	Array

        public String alias;//Top News

        public String  boardid;//photoview_bbs
        public String  cid;//C1348646712614
        public String  digest;//
        public String  docid;//9IG74V5H00963VRO_CG2APCCRbjwujingwenupdateDoc
        public String  ename;//androidnews
        public String  hasAD;//1
        public String  hasCover;//	false
        public String  hasHead;//	1
        public boolean hasIcon;//	false
        public String  hasImg;//	1
        public String  imgsrc;//http://cms-bucket.nosdn.127
        // .net/42c7a71190ef4118be7dc8864a2ea32920170321135414.jpeg
        public String  imgsum;//	6
        public String  lmodify;//	2017-03-21 13:56:55
        public String  order;//	1
        public String  photosetID;//	00AP0001|2243902
        public String  postid;//	PHOT24F9U000100A
        public String  priority;//	356
        public String  ptime;//	2017-03-21 13:45:56
        public String  replyCount;//	29302
        public String  skipID;//	00AP0001|2243902
        public String  skipType;//	photoset
        public String  source;//	半岛晨报
        public String  template;//	normal1
        public String  title;//	女子给跪地乞丐一袋麻花 被扔进垃圾桶
        public String  tname;//	头条  这个很重要，相当于是标题分栏的名字。比如：头条，体育，财经等等
    }

    public class AdsData {
        public String imgsrc;
        public String skipID;   //3R710001|2243723
        public String skipType;     //photoset
        public String subtitle;
        public String tag;      //photoset
        public String title;    //看客：疲惫的中国人 "破烂王"睡瓶子上
        public String url;      //3R710001|2243723

    }

    public class ImgextraData {
        public String imgsrc;
    }
}
