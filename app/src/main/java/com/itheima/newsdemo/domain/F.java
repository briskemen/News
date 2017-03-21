package com.itheima.newsdemo.domain;

import java.util.List;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   卢俊霖
 * Email：    briskemen@163.com
 * GitHub：   https://github.com/briskemen
 * Project_Name：Zhbj
 * Package_Name：com.itheima.zhbj52.domain
 * Version：1.0
 * time：2017/2/21 22:13
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class F {


    /**
     * extend : [10007,10006,10008,10014,10012,10091,10009,10010,10095]
     * data : [{"children":[{"id":10007,"title":"鍖椾含","type":1,"url":"/10007/list_1.json"},
     * {"id":10006,"title":"涓浗","type":1,"url":"/10006/list_1.json"},{"id":10008,"title":"鍥介檯",
     * "type":1,"url":"/10008/list_1.json"},{"id":10010,"title":"浣撹偛","type":1,
     * "url":"/10010/list_1.json"},{"id":10091,"title":"鐢熸椿","type":1,"url":"/10091/list_1
     * .json"},{"id":10012,"title":"鏃呮父","type":1,"url":"/10012/list_1.json"},{"id":10095,
     * "title":"绉戞妧","type":1,"url":"/10095/list_1.json"},{"id":10009,"title":"鍐涗簨","type":1,
     * "url":"/10009/list_1.json"},{"id":10093,"title":"鏃跺皻","type":1,"url":"/10093/list_1
     * .json"},{"id":10011,"title":"璐㈢粡","type":1,"url":"/10011/list_1.json"},{"id":10094,
     * "title":"鑲插効","type":1,"url":"/10094/list_1.json"},{"id":10105,"title":"姹借溅","type":1,
     * "url":"/10105/list_1.json"}],"id":10000,"title":"鏂伴椈","type":1},{"url1":"/10007/list1_1
     * .json","id":10002,"title":"涓撻","type":10,"url":"/10006/list_1.json"},{"id":10003,
     * "title":"缁勫浘","type":2,"url":"/10008/list_1.json"},{"dayurl":"","id":10004,"weekurl":"",
     * "excurl":"","title":"浜掑姩","type":3}]
     * retcode : 200
     */
    //public List<int> extend;
    public List<DataEntity> data;
    public int              retcode;

    public static class DataEntity {
        /**
         * children : [{"id":10007,"title":"鍖椾含","type":1,"url":"/10007/list_1.json"},
         * {"id":10006,"title":"涓浗","type":1,"url":"/10006/list_1.json"},{"id":10008,
         * "title":"鍥介檯","type":1,"url":"/10008/list_1.json"},{"id":10010,"title":"浣撹偛","type":1,
         * "url":"/10010/list_1.json"},{"id":10091,"title":"鐢熸椿","type":1,"url":"/10091/list_1
         * .json"},{"id":10012,"title":"鏃呮父","type":1,"url":"/10012/list_1.json"},{"id":10095,
         * "title":"绉戞妧","type":1,"url":"/10095/list_1.json"},{"id":10009,"title":"鍐涗簨","type":1,
         * "url":"/10009/list_1.json"},{"id":10093,"title":"鏃跺皻","type":1,"url":"/10093/list_1
         * .json"},{"id":10011,"title":"璐㈢粡","type":1,"url":"/10011/list_1.json"},{"id":10094,
         * "title":"鑲插効","type":1,"url":"/10094/list_1.json"},{"id":10105,"title":"姹借溅","type":1,
         * "url":"/10105/list_1.json"}]
         * id : 10000
         * title : 鏂伴椈
         * type : 1
         */
        public List<ChildrenEntity> children;
        public int    id;
        public String title;
        public int    type;

        public static class ChildrenEntity {
            /**
             * id : 10007
             * title : 鍖椾含
             * type : 1
             * url : /10007/list_1.json
             */
            public int id;
            public String title;
            public int    type;
            public String url;
        }
    }
}
