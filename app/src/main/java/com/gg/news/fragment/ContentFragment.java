package com.gg.news.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gg.news.R;
import com.gg.news.activity.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends BaseFragment {

    private RadioGroup group;
    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private String q;
    private MyAdapter adapter;
    private ImageButton menuButton;

    public static ContentFragment newInstance(String q) {
        ContentFragment fragment = new ContentFragment();
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
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.vp_fragment_content);
        group = (RadioGroup) view.findViewById(R.id.rg_fragment_content);
        menuButton = (ImageButton) view.findViewById(R.id.ib_fragment_content_menu);

        return view;
    }

    @Override
    public void initData() {
        fragmentList.add(new HomeFragment());
        fragmentList.add(CenterFragment.newInstance(q));
        fragmentList.add(new ServiceFragment());
        fragmentList.add(new GovaFragment());
        fragmentList.add(new SettingFragment());

        adapter = new MyAdapter(getActivity().getSupportFragmentManager());

        viewPager.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_fragment_home:
                        viewPager.setCurrentItem(0, false);// 去掉切换页面的动画
                        break;
                    case R.id.rb_fragment_newscenter:
                        viewPager.setCurrentItem(1, false);// 去掉切换页面的动画
                        break;
                    case R.id.rb_fragment_smartservice:
                        viewPager.setCurrentItem(2, false);// 去掉切换页面的动画
                        break;
                    case R.id.rb_fragment_govaffairs:
                        viewPager.setCurrentItem(3, false);// 去掉切换页面的动画
                        break;
                    case R.id.rb_fragment_setting:
                        viewPager.setCurrentItem(4, false);// 去掉切换页面的动画
                        break;
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    menuButton.setVisibility(View.VISIBLE);//侧滑菜单可见

                    ((HomeActivity) mActivity).setSlidingMenuEnable(true);//侧滑菜单可用
                } else {
                    menuButton.setVisibility(View.INVISIBLE);

                    ((HomeActivity) mActivity).setSlidingMenuEnable(false);
                }
                RadioButton button = (RadioButton) group.getChildAt(position);
                button.setChecked(true);

                // 不想预加载的话，这里可以调用，不过BaseFragment里的initData不能放到onActivityCreate里了
                // ((BaseFragment) fragmentList.get(position)).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) mActivity).toggle();
            }
        });
    }

    private class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        // 和pagerAdapter里的instanceItem一样，会预加载，不想浪费用户流量的话可以在外面调用initData方法
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
