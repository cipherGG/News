package com.gg.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gg.news.R;
import com.gg.news.engine.News;
import com.gg.news.utils.AnimUtils;
import com.gg.news.utils.PackageUtils;
import com.gg.news.utils.PrefUtils;

import java.util.List;

import cn.sharesdk.framework.ShareSDK;

/**
 * 注意这个activity继承的类，因为要设置成fullscreen
 */
public class SplashActivity extends Activity {

    private RelativeLayout layout;
    private TextView version;
    private AnimationSet set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        initView();

        initAnim();

        initData();

        initListener();
    }

    private void initView() {
        setContentView(R.layout.activity_splash);
        layout = (RelativeLayout) findViewById(R.id.rl_activity_splash);
        version = (TextView) findViewById(R.id.tv_activity_splash_version);
    }

    private void initData() {
        PackageInfo packageInfo = PackageUtils.getPackageInfo(this);
        if (packageInfo != null) {
            version.setText("版本：" + packageInfo.versionName);
        }
        ShareSDK.initSDK(this);
    }

    private void initAnim() {
        AlphaAnimation alpha = AnimUtils.getAlpha(1, 0.7f);
        ScaleAnimation scale = AnimUtils.getScale(1, 1.1f, 1, 1.1f, 0.5f, 0.5f);
        set = AnimUtils.getSet(1000, 2000, 0, true, Animation.RESTART, alpha, scale);

        layout.startAnimation(set);
    }

    private void initListener() {
        // 动画结束后跳转
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (PrefUtils.isFirstTime(SplashActivity.this, "isFirstTime")) {
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }


}
