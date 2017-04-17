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
 * time：2017/4/11 11:09
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class VRDetail {

    public String             address;
    public String             albumid;
    public String             appversion;
    public AuthorEntity       author;
    public String             categorys;
    public String             commentcount;
    public String             config;
    public String             copyright;
    public String             cutsize;
    public String             devicename;
    public String             direction;
    public String             duration;
    public String             extjson;
    public String             height;
    public String             html5_3dpreview;
    public String             html5_path;
    public String             id;
    public String             identifier;
    public String             imagedes;
    public String             imagesize;
    public String             indepcopyright;
    public String             is_system;
    public String             isblack;
    public String             iscanview;
    public String             isdelete;
    public String             ismobile;
    public String             isrelease;
    public String             lat;
    public String             likecount;
    public String             lng;
    public String             md5file;
    public String             mobiledevice;
    public String             mobilesystem;
    public String             name;
    public String             panomode;
    public String             panopercent;
    public String             picmode;
    public String             prealign;
    public String             qr;
    public String             recommendorder;
    public String             referer;
    public String             reportcount;
    public String             source;
    public String             syncstatus;
    public String             systags;
    public String             tags;
    public String             thumburl;
    public String             uploadtime;
    public String             viewcount;
    public String             width;
    // public Extra              extra_config;
    // public List<?>              extra_config;
    public List<ScenesEntity> scenes;

    public static class Extra {
        public String bgsound;
    }

    public static class AuthorEntity {

        public String address;
        public String domainname;
        public String headphoto;
        public String nickname;
    }

    public static class ScenesEntity {

        public String devicename;
        public String duration;
        public String height;
        public String id;
        public String identifier;
        public String isvideo;
        public String m3u8url1;
        public String m3u8url2;
        public String m3u8url3;
        public String m3u8url4;
        public String m3u8url_org;
        public String mp3;
        public String mp4url1;
        public String mp4url2;
        public String mp4url3;
        public String mp4url4;
        public String mp4url_org;
        public String name;
        public String prealign;
        public String preview;
        public String syncstatus;
        public String thumburl;
        // public VideoExtEntity video_ext;
        // public ViewEntity     view;
        public String width;
        public String original;

        public static class VideoExtEntity {

            public String m3u8;
            public String mp3;
            public String mp4;
        }

        public static class ViewEntity {

            public int fov;
            public int hlookat;
            public int vlookat;
        }

    }
}
