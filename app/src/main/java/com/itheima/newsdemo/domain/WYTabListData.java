package com.itheima.newsdemo.domain;

import java.util.List;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   卢俊霖
 * Email：    briskemen@163.com
 * GitHub：   https://github.com/briskemen
 * Project_Name：Zhbj
 * Package_Name：com.itheima.newsdemo.domain
 * Version：1.0
 * time：2017/3/23 13:53
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class WYTabListData {
    public List<TListEntity> tList;

    public static class TListEntity {
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
