package com.itheima.newsdemo.global;

/**
 * 定义全局参数
 */
public class GlobalContants {

    public static final String host         = "http://c.m.163.com/";
    // http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
    public static final String NewsUrl      = host + "nc/article/headline/";
    public static final String endUrl       = "-20.html";
    public static final String endDetailUrl = "/full.html";


    // 新闻详情
    public static final String NewDetail = host + "nc/article/";
    //评论
    public static final String CommonUrl = host + "nc/article/list/";

    //头条
    public static final String TopId = "T1348647909107";


    //public static final String SERVER_URL     = "http://10.0.2.2:8080/zhbj";// 这是模拟器通用地址
    //public static final String SERVER_URL = "http://192.168.0.100:8080/zhbj";//这是电脑Ip
    //public static final String SERVER_URL     = "http://zhihuibj.sinaapp.com/zhbj";//  真机上模拟
    // public static final String CATEGORIES_URL = SERVER_URL + "/categories.json";// 获取分类信息的接口
    // public static final String PHOTOS_URL = SERVER_URL + "/photos/photos_1.json";// 获取组图信息的接口

    public static final String WYTOPNEWS_URL = getTopNewsUrl(0);// 网易头条新闻地址

    // http://c.m.163.com/nc/article/CG3CUH650001875N/full.html
    /*新闻url*/
    public static String getTopNewsUrl(int index) {
        return NewsUrl + TopId + "/" + index + endUrl;
    }
}

