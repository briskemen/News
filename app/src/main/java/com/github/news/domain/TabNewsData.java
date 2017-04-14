package com.github.news.domain;

import com.github.news.adapter.MultiItemEntity;

import java.util.ArrayList;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   AllenIverson
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * GitBook：  https://www.gitbook.com/@alleniverson
 * 博客：     http://blog.csdn.net/axi295309066
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：ZHBJ
 * Package_Name：com.github.news.domain
 * Version：1.0
 * time：2017/4/2 15:07
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class TabNewsData implements MultiItemEntity {

    public static final int TYPE_NORMAL   = 1;
    public static final int TYPE_PHOTOSET = 2;
    public static final int TYPE_SPECIAL  = 3;

    public ArrayList<AdsData>      ads;// 图片新闻
    public ArrayList<ImgextraData> imgextra;//	Array

    public String alias;//Top RecommendNewsData

    public String  boardid;//photoview_bbs
    public String  cid;//C1348646712614
    public String  digest;//
    public String  docid;//9IG74V5H00963VRO_CG2APCCRbjwujingwenupdateDoc
    public String  ename;//androidnews
    public int     hasAD;//1
    public boolean hasCover;//	false
    public int     hasHead;//	1
    public boolean hasIcon;//	false
    public int     hasImg;//	1
    public String  imgsrc;//http://cms-bucket.nosdn.127
    // .net/42c7a71190ef4118be7dc8864a2ea32920170321135414.jpeg
    public int     imgsum;//	6
    public String  lmodify;//	2017-03-21 13:56:55
    public int     order;//	1
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

    public static class AdsData {
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

    @Override
    public int getItemType(int position) {
        if (skipType != null && skipType.equals("photoset") && imgextra != null) {
            return TYPE_PHOTOSET;
        } else if (skipType != null && skipType.equals("special")) {
            return TYPE_SPECIAL;
        } else {
            return TYPE_NORMAL;
        }
    }

}
