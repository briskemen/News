package com.hello.newsdemo.domain;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   卢俊霖
 * Email：    briskemen@163.com
 * GitHub：   https://github.com/briskemen
 * Project_Name：Zhbj
 * Package_Name：com.itheima.zhbj52.domain
 * Version：1.0
 * time：2017/2/20 9:05
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/

import java.util.ArrayList;

/**
 * json的组图信息封装
 */
public class PhotoData {

    public int        retcode;
    public PhotosInfo data;

    public class PhotosInfo {
        public String               title;
        public ArrayList<PhotoInfo> news;
    }

    public class PhotoInfo {
        public String id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;
    }
}
