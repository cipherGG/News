package com.gg.news.utils;

import android.content.Context;

import com.gg.news.R;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

/**
 * author cipherGG
 * Created by Administrator on 2016/1/6.
 * describe
 */
public class ShareUtils {

    public static void showShare(Context context, String title, String url) {
        // ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();

        //默认样式非常丑陋
        oks.setTheme(OnekeyShareTheme.SKYBLUE);

        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);

        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);

        // text是分享文本，所有平台都需要这个字段
        oks.setText("请输入内容");

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(null);//确保SDcard下面存在此张图片

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);

        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("请输入评论");

        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(url);

        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

        // 启动分享GUI
        oks.show(context);
    }
}
