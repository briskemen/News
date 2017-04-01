package com.hello.newsdemo.json;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   卢俊霖
 * Email：    briskemen@163.com
 * GitHub：   https://github.com/briskemen
 * Project_Name：Zhbj
 * Package_Name：com.itheima.newsdemo.domain
 * Version：1.0
 * time：2017/3/22 9:38
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/

import java.net.URL;
import java.util.ArrayList;

/**
 * 网易（头条）新闻详情页面
 * URL:http://c.m.163.com/nc/article/CG3CUH650001875N/full.html
 */
public class WYDetailJson {
    public DetailsEntry CG3CUH650001875N;

    public class DetailsEntry {
        public String    docid;//CG3CUH650001875N

        public ArrayList boboList;//Array
        public String    body;// 新闻内容　　新京报快讯
        public ArrayList book;//Array
        public String    digest;//
        public String    dkeys;//工行,建行,北京银行,贷款,房贷,首套房贷款利率
        public String    ec;//	李天奕_NN7528
        public String    hasNext;//	false
        public ArrayList img;//	Array
        public ArrayList link;//Array
        public boolean   picnews;//	true
        public String    ptime;//2017-03-21 23:42:56
        public ArrayList relative_sys;//Array
        public String    replyBoard;//	news_guonei8_bbs
        public String    replyCount;//	0

        /**
         * 分享链接
         */
        public URL shareLink;//https://c.m.163.com/news/a/CG3CUH650001875N.html?spss=newsapp&spsw=1

        public String                        source;//	新京报即时新闻 （新闻来源）
        public ArrayList<SpinfoEntry>        spinfo;//	Array
        public String                        template;//normal1
        public int                           threadAgainst;//3
        public int                           threadVote;//	22
        public String                        tid;//
        public String                        title;//北京多家银行已取消首房9折优惠 执行95折利率
        public ArrayList<TopiclistEntry>     topiclist;//Array
        public ArrayList<TopiclistNewsEntry> topiclist_news;//	Array
        public String                        voicecomment;//off
        public ArrayList                     votes;//Array
        public ArrayList                     ydbaike;//	Array
    }

    public class TopiclistEntry {
        public String alias;//最快速最全面的国内政经资讯
        public String cid;//	C1378977941637
        public String ename;//guonei
        public String hasCover;//false
        public int    subnum;//	934.5万
        public String tid;//	T1348648101594
        public String tname;//网易国内
    }

    private class TopiclistNewsEntry {
        public String alias;//Top News
        public String cid;//C1348646712614
        public String ename;//androidnews
        public String hasCover;//false
        public String subnum;//3.2万
        public String tid;//T1348647909107
        public String tname;//头条
    }

    /**
     * 推荐新闻
     */
    private class SpinfoEntry {
        public String ref;//<!--SPINFO#0-->
        public String spcontent;//推荐新闻的链接，标题以及部分内容
        public String sptype;//推荐
    }
}
