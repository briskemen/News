package com.github.news.domain;

import java.util.List;

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
 * Project_Name：News
 * Package_Name：com.github.news.domain
 * Version：1.0
 * time：2017/4/16 20:35
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class PhotoSet {

    public String autoid;
    public String             boardid;
    public String             clientadurl;
    public String             commenturl;
    public String             cover;
    public String             createdate;
    public String             creator;
    public String             datatime;
    public String             desc;
    public String             imgsum;
    public String             neteasecode;
    public String             postid;
    public String             reporter;
    public String             scover;
    public String             series;
    public String             setname;
    public String             settag;
    public String             source;
    public String             tcover;
    public String             url;
    public List<PhotosEntity> photos;
    public List<?>            relatedids;

    public static class PhotosEntity {
        public String cimgurl;
        public String imgtitle;
        public String imgurl;
        public String newsurl;
        public String note;
        public String photohtml;
        public String photoid;
        public String simgurl;
        public String squareimgurl;
        public String timgurl;
    }
}
