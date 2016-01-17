package com.gg.news.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.gg.news.R;
import com.gg.news.engine.News;
import com.gg.news.fragment.ContentFragment;
import com.gg.news.fragment.MenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends SlidingFragmentActivity {

    private static final String FRAGMENT_CONTENT = "fragment_content";
    private static final String FRAGMENT_MENU = "fragment_left";
    private SlidingMenu slidingMenu;
    public FragmentManager manager;

    private ArrayList<String> words = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initFragment();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initData();
    }

    private void initView() {
        setContentView(R.layout.activity_content);
        setBehindContentView(R.layout.activity_menu);//侧边栏布局

        slidingMenu = getSlidingMenu();
        // slidingMenu.setSecondaryMenu(R.layout.menu_activity_right);//二级侧滑菜单
        slidingMenu.setMode(SlidingMenu.LEFT);//侧滑模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);//触摸模式,默认不可滑
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);//菜单预留主屏宽度
        slidingMenu.setFadeDegree(1f);//侧边栏褪色
        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);//侧边栏阴影宽度
        slidingMenu.setShadowDrawable(R.drawable.shadow);//侧边栏阴影背景
        // slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        // slidingMenu.setMenu(R.layout.activity_menu);

        //initFragment();
    }

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                words = News.getWords();//新闻titles
                handler.sendEmptyMessage(1);
            }
        }.start();
    }

    private void initFragment() {
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //Log.e("words.get(0))", "--->" + words.get(0));
        transaction.replace(R.id.frame_activity_home_content, ContentFragment.newInstance(words.get(0)), FRAGMENT_CONTENT);
        //Log.e("words", "--->" + words.toString());
        transaction.replace(R.id.frame_activity_home_menu, MenuFragment.newInstance(words), FRAGMENT_MENU);
        transaction.commit();
    }

    public void setSlidingMenuEnable(boolean enable) {
        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    public MenuFragment getMenuFragment() {
        return (MenuFragment) manager.findFragmentByTag(FRAGMENT_MENU);
    }

}
