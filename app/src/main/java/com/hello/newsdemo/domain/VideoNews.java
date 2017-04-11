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
 * Project_Name：SmartCity
 * Package_Name：com.google.smartcity.bean
 * Version：1.0
 * time：2017/4/10 9:42
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class VideoNews {

    public List<VideoData> 视频;

    public static class VideoData {

        public String cover;
        public String           description;
        public int              length;
        public String           m3u8_url;
        public String           mp4_url;
        public int              playCount;
        public int              playersize;
        public String           program;
        public String           prompt;
        public String           ptime;
        public String           replyBoard;
        public int              replyCount;
        public String           replyid;
        public String           sectiontitle;
        public int              sizeSD;
        public String           title;
        public String           topicDesc;
        public String           topicImg;
        public String           topicName;
        public String           topicSid;
        public String           vid;
        public VideoTopic videoTopic;
        public String           videosource;

        public static class VideoTopic {

            public String alias;
            public String ename;
            public String tid;
            public String tname;
            public String topic_icons;
        }
    }
}
