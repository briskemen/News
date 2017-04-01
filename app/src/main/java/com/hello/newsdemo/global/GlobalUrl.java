package com.hello.newsdemo.global;

/**
 * 定义全局参数
 */
public class GlobalUrl {
    // 页签indication
    public static final String tListUrl    = "http://c.m.163" +
            ".com/nc/topicset/android/subscribe/manage/listspecial.html";
    // 美女图片
    public static final String womenPicUrl = "http://image.baidu" +
            ".com/channel/listjson?pn=0&rn=30&tag1=美女&tag2=全部";

    public static final String host         = "http://c.m.163.com/";
    // http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
    public static final String NewsUrl      = host + "nc/article/headline/";
    public static final String endUrl       = "-20.html";

    // 新闻详情
    public static final String NewDetail = host + "nc/article/";
    //评论
    public static final String CommonUrl = host + "nc/article/list/";

    //头条
    public static final String TopId = "T1348647909107";

    /*新闻url*/
    public static String getTopNewsUrl(int index) {
        return NewsUrl + TopId + "/" + index + endUrl;
    }

    // GlobalUrl.NewsUrl + mTabData.tid + "/" + index + GlobalUrl.endUrl
    // http://c.m.163.com/nc/article/headline/T1370583240249/20-20.html
    public static String getNewsUrl(String tId, int index) {
        return NewsUrl + tId + "/" + index + endUrl;
    }

    /**
     * 网易新闻内容详情手机客户端URL
     *
     * @param postId 新闻位置ID CGQQEDGG000187VI
     * @return
     */
    public static String getNewsDetailUrl(String postId) {
        // https://c.m.163.com/news/a/CG3CUH650001875N.html
        return "https://c.m.163.com/news/a/" + postId + ".html";
    }
}

