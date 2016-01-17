package com.gg.news.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gg.news.R;
import com.gg.news.activity.HomeActivity;
import com.gg.news.engine.News;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CenterFragment extends BaseFragment {

    private HomeActivity activity;
    private String q;

    public static CenterFragment newInstance(String q) {
        CenterFragment fragment = new CenterFragment();
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
        return inflater.inflate(R.layout.fragment_center, container, false);
    }

    @Override
    public void initData() {
        activity = (HomeActivity) this.mActivity;
        //只能在这里加载
        initFragment();

        // MenuFragment menuFragment = ((HomeActivity) mActivity).getMenuFragment();
        // menuFragment.setMenuData(words);
    }

    private void initFragment() {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_fragment_center, NewsFragment.newInstance(q));
        transaction.commit();
    }
}
