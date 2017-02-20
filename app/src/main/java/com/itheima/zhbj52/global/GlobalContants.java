package com.itheima.zhbj52.global;

/**
 * 定义全局参数
 */
public class GlobalContants {

    //public static final String SERVER_URL     = "http://10.0.2.2:8080/zhbj";// 这是模拟器通用地址
    public static final String SERVER_URL     = "http://192.168.0.100:8080/zhbj";
    public static final String CATEGORIES_URL = SERVER_URL + "/categories.json";// 获取分类信息的接口

    public static final String PHOTOS_URL = SERVER_URL + "/photos/photos_1.json";// 获取组图信息的接口
}
