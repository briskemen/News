package com.hello.newsdemo.domain;

import com.hello.newsdemo.adapter.MultiItemEntity;

import java.util.List;

public class PictureNews implements MultiItemEntity {
    public static final int TYPE1 = 1;
    public static final int TYPE2 = 2;
    public static final int TYPE3 = 3;

    public String       clientcover;
    public String       clientcover1;
    public String       cover;
    public String       createdate;
    public String       datetime;
    public String       desc;
    public String       imgsum;
    public String       pvnum;
    public String       replynum;
    public String       scover;
    public String       setid;
    public String       setname;
    public String       seturl;
    public String       tcover;
    public String       topicname;
    public List<String> pics;

    @Override
    public int getItemType(int position) {
        if (position % 4 == 0) {
            return TYPE1;
        } else if (position % 4 == 1) {
            return TYPE2;
        } else if (position % 4 == 2) {
            return TYPE1;
        } else {
            return TYPE3;
        }
    }
}
