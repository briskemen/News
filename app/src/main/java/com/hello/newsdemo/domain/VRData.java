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
 * time：2017/4/10 20:09
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class VRData {

    public int code;
    public String           msg;
    public List<DataEntity> data;

    public static class DataEntity {

        public String address;
        public String     channelid;
        public String     id;
        public String     istop;
        public String     name;
        public String     ordernum;
        public PanoEntity pano;
        public String     relationid;
        public String     releasetime;
        public String     summary;
        public String     thumb;
        public UserEntity user;

        public static class PanoEntity {

            public String address;
            public String id;
            public String imagedes;
            public String likecount;
            public String name;
            public String picmode;
            public String syncstatus;
            public String thumburl;
            public String viewcount;
        }

        public static class UserEntity {

            public String authenticationstats;
            public String domainname;
            public String headphoto;
            public String nickname;
            public String personalinfo;
        }
    }
}
