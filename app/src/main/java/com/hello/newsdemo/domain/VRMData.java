package com.hello.newsdemo.domain;

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
 * Package_Name：com.hello.newsdemo.domain
 * Version：1.0
 * time：2017/4/11 21:56
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class VRMData {

    public int code;
    public String           msg;
    public List<DataEntity> data;

    public static class DataEntity {
        public String            address;
        public String            app_config;
        public AuthorEntity      author;
        public int               comment_count;
        public CoordinatesEntity coordinates;
        public String            cutsize;
        public String            default_quality;
        public String            devicename;
        public String            duration;
        public String            height;
        public String            id;
        public String            imagedes;
        public String            imagesize;
        public boolean           is_like;
        public String            iscanview;
        public int               like_count;
        public int               max_quality;
        public String            name;
        public String            original;
        public String            original_l;
        public String            original_m;
        public String            original_offline;
        public String            original_s;
        public String            picmode;
        public String            prealign;
        public String            stars;
        public int               syncstatus;
        public String            thumburl;
        public int               uploadtime;
        public String            viewcount;
        public String            width;
        public String            html5_3dpreview;
        public String            html5_path;
        public List<?>           comments;
        public List<?>           picmark;

        public static class AuthorEntity {
            public String  address;
            public String  domainname;
            public String  headphoto;
            public boolean is_follow;
            public String  nickname;
            public String  personalinfo;
            public String  sex;
        }

        public static class CoordinatesEntity {
            public float latitude;
            public float longitude;
        }
    }
}
