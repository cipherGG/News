package com.gg.news.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gg.news.R;
import com.gg.news.activity.HomeActivity;
import com.gg.news.engine.News;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends BaseFragment {

    private ListView listView;
    private List<String> menuItems = new ArrayList<>();
    private MyAdapter adapter;
    private int currentPosition;
    private HomeActivity activity;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    adapter = new MyAdapter();
                    listView.setAdapter(adapter);
                    break;
            }
        }
    };

    public static MenuFragment newInstance(ArrayList<String> menuItems) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("menuItems", menuItems);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            menuItems = getArguments().getStringArrayList("menuItems");
        }
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        listView = (ListView) view.findViewById(R.id.lv_fragment_menu);

        return view;
    }

    @Override
    public void initData() {
        activity = (HomeActivity) this.mActivity;

        new Thread() {
            @Override
            public void run() {
                super.run();
                List<String> words = News.getWords();//新闻titles
                menuItems.addAll(words);//菜单项数据加载
                handler.sendEmptyMessage(1);
            }
        }.start();
    }

    @Override
    public void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                adapter.notifyDataSetChanged();

                initFragment(menuItems.get(position));//获取点击item，并传值

                activity.toggle();
            }
        });
    }

    private void initFragment(String title) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_fragment_center, NewsContentFragment.newInstance(title));
        transaction.commit();
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return menuItems.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_fragment_menu_lv, null);
            TextView textView = (TextView) convertView.findViewById(R.id.tv_item_fragment_menu);

            textView.setText(menuItems.get(position));

            if (currentPosition == position) {
                textView.setEnabled(true);
            } else {
                textView.setEnabled(false);
            }
            return convertView;
        }
    }

}
