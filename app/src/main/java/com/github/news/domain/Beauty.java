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
 * time：2017/4/12 11:24
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class Beauty {


    public String gsm;
    public List<DataEntity> data;

    public static class DataEntity {
        public String                 cont_sign;
        public int                    dataId;
        public String                 encodedFromUrl;
        public String                 encodedObjUrl;
        public String                 fromurl;
        public int                    height;
        public String                 hoverurl;
        public int                    id;
        public int                    img_set_count;
        public String                 imgnewsDate;
        public String                 obj_sign;
        public String                 obj_url;
        public int                    ori_height;
        public int                    ori_width;
        public int                    personalized;
        public int                    picture_id;
        public String                 set_sign;
        public String                 set_tag;
        public String                 simid;
        public int                    spn;
        public int                    srctype;
        public String                 starUpLoadType;
        public String                 thumbnail_url;
        public String                 thumburl;
        public String                 title;
        public int                    tn_height;
        public int                    tn_width;
        public int                    tnwidth;
        public String                 videoURL;
        public String                 videoUid;
        public int                    width;
        public FaceInfoEntity         face_info;
        public List<ReplaceUrlEntity> replaceUrl;

        public static class FaceInfoEntity {
            public String simid;
        }

        public static class ReplaceUrlEntity {
            public String FromURL;
            public String ObjURL;
        }
    }
}
