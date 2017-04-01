package com.hello.newsdemo.domain;

import java.util.ArrayList;

/**
 * 页签详情页数据
 * URL:http://192.168.0.100:8080/zhbj//photos/photos_1.json
 */
public class TabData {

    public int retcode;

    public TabDetail data;

    public class TabDetail {
        public String                 title;
        public String                 more;
        public ArrayList<TabNewsData> news;
        public ArrayList<TopNewsData> topnews;

    }

    /**
     * 新闻列表对象
     */
    public class TabNewsData {
        public String id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;
    }

    /**
     * 头条新闻
     */
    public class TopNewsData {
        public String id;
        public String topimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;
    }
}
