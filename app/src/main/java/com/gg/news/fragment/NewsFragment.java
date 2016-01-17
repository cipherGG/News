package com.gg.news.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gg.news.R;
import com.gg.news.activity.HomeActivity;
import com.viewpagerindicator.TabPageIndicator;

public class NewsFragment extends BaseFragment {
    private static final String[] CONTENT = new String[]{"科技", "娱乐", "军事", "游戏", "数码", "体育"};
    private ViewPager pager;
    private String q;

    public static NewsFragment newInstance(String q) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString("q", q);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            q = getArguments().getString("q");
        }
    }
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        FragmentPagerAdapter adapter = new GoogleMusicAdapter
                (((HomeActivity) mActivity).getSupportFragmentManager());

        pager = (ViewPager) view.findViewById(R.id.pager);

        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator) view.findViewById(R.id.indicator);

        indicator.setViewPager(pager);
        return view;
    }

    class GoogleMusicAdapter extends FragmentPagerAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //实际开发：return list<Fragment>get(position);
            return NewsContentFragment.newInstance(q);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }
}
