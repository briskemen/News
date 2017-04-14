package com.github.news.global;

/**
 * 定义全局参数
 */
public class GlobalUrl {
    // http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
    public static final String host    = "http://c.m.163.com/";
    public static final String NewsUrl = host + "nc/article/headline/";
    public static final String endUrl  = "-20.html";
    public static final String TopId   = "T1348647909107";// 头条新闻

    // 页签indication
    public static final String tListUrl = "http://c.m.163" +
            ".com/nc/topicset/android/subscribe/manage/listspecial.html";

    // 美女图片
    public static final String womenPicUrl = "http://image.baidu" +
            ".com/channel/listjson?pn=0&rn=30&tag1=美女&tag2=全部";

    /*新闻url*/
    public static String getTopNewsUrl(int index) {
        return NewsUrl + TopId + "/" + index + endUrl;
    }

    // http://c.m.163.com/nc/article/headline/T1370583240249/20-20.html
    public static String getNewsUrl(String tId, int index) {
        return NewsUrl + tId + "/" + index + endUrl;
    }

    public static String getNewsDetailUrl(String docId) {
        // https://c.m.163.com/news/a/CG3CUH650001875N.html
        return "https://c.m.163.com/news/a/" + docId + ".html";
    }

    public static String getGirlsData(int pn) {
        return "http://image.baidu.com/channel/listjson?pn=" + pn + "&rn=20&tag1=美女&tag2=全部";
    }
}

