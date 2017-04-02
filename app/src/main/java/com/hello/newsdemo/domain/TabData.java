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
 * Project_Name：ZHBJ
 * Package_Name：com.hello.newsdemo.domain
 * Version：1.0
 * time：2017/4/2 16:13
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class TabData {

    public List<TabList> tList;

    public static class TabList {
        public String  template;
        public String  img;
        public int     recommendOrder;
        public String  color;
        public int     ad_type;
        public boolean hasCover;
        public int     hashead;
        public String  tname;
        public String  recommend;
        public int     isNew;
        public String  tid;
        public boolean headLine;
        public int     special;
        public String  topicid;
        public String  ename;
        public boolean hasIcon;
        public int     bannerOrder;
        public String  alias;
        public String  showType;
        public String  subnum;
        public int     hasAD;
        public int     isHot;
        public String  cid;
    }
}
