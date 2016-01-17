package com.gg.news.bean;

import java.util.List;

/**
 * author cipherGG
 * Created by Administrator on 2016/1/2.
 * describe
 */
public class Query {
    public String reason;
    public List<Result> result;
    public String error_code;


    public class Result {
        public String title;
        public String content;
        public String img_width;
        public String full_title;
        public String pdate;
        public String src;
        public String img_length;
        public String img;
        public String url;
        public String pdate_src;
    }
}
