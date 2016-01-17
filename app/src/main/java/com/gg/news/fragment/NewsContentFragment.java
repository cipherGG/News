package com.gg.news.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gg.news.R;
import com.gg.news.activity.NewsDetailActivity;
import com.gg.news.bean.Query;
import com.gg.news.engine.News;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

public class NewsContentFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private ViewPager viewPager;

    private String title;
    private List<Query.Result> querys;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
                    MyRecycleAdapter recycleAdapter = new MyRecycleAdapter();
                    recyclerView.setAdapter(recycleAdapter);

                    break;
                case 2:
                    int nextItem = viewPager.getCurrentItem() + 1;
                    int total = querys.size() - 1;
                    if (nextItem > total) {
                        nextItem = 0;
                    }
                    viewPager.setCurrentItem(nextItem);

                    handler.sendEmptyMessageDelayed(2, 2000);
                    break;
            }
        }
    };

    public static NewsContentFragment newInstance(String title) {
        NewsContentFragment fragment = new NewsContentFragment();
        Bundle args = new Bundle();
        args.putString("q", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            title = getArguments().getString("q");
        }
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_content, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_fragment_news_content);

        return view;
    }

    @Override
    public void initData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                querys = News.getQuerys(title);

                handler.sendEmptyMessage(1);
            }
        }.start();
    }

    /**
     * 列表新闻适配器................................................
     */
    private class MyRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_HEAD = 1;
        private static final int TYPE_ITEM = 2;

        @Override
        public int getItemCount() {
            return querys.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_HEAD;
            } else {
                return TYPE_ITEM;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_HEAD) {
                View view = LayoutInflater.from(mActivity)
                        .inflate(R.layout.head_fragment_news_content_rv, parent, false);

                return new HeadViewHolder(view);
            } else {
                View view = LayoutInflater.from(mActivity)
                        .inflate(R.layout.item_fragment_news_content_rv, parent, false);

                return new ItemViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (querys.size() == 0) {
                return;
            }
            if (holder instanceof HeadViewHolder) {
                Query.Result result = querys.get(position);//此处position永远为0
                ((HeadViewHolder) holder).result = result;

                viewPager.setAdapter(new MyPagerAdapter());//headViewPager设置适配器
                ((HeadViewHolder) holder).text.setText(result.title);//第一个需要手动加载，后面的监听器加载
                //圆点点
                ((HeadViewHolder) holder).indicator.setViewPager(viewPager);
                ((HeadViewHolder) holder).indicator.setSnap(true);

            } else if (holder instanceof ItemViewHolder) {
                Query.Result result = querys.get(position - 1);
                ((ItemViewHolder) holder).result = result;

                ((ItemViewHolder) holder).tvTitle.setText(result.title);
                ((ItemViewHolder) holder).tvContent.setText(result.content);
                ((ItemViewHolder) holder).tvDate.setText(result.pdate_src);
                if (!TextUtils.isEmpty(result.img)) {
                    Picasso.with(mActivity).load(result.img).placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher).into(((ItemViewHolder) holder).ivLeft);
                }
            }
        }

        /**
         * 头条展示........................................
         */
        private class HeadViewHolder extends RecyclerView.ViewHolder
                implements ViewPager.OnPageChangeListener {
            CirclePageIndicator indicator;
            TextView text;
            Query.Result result;

            public HeadViewHolder(View itemView) {
                super(itemView);
                text = (TextView) itemView.findViewById(R.id.tv_fragment_news_content);
                viewPager = (ViewPager) itemView.findViewById(R.id.vp_fragment_news_content);
                indicator = (CirclePageIndicator) itemView.findViewById(R.id.cpi_fragment_news_content);

                handler.sendEmptyMessage(2);

                viewPager.addOnPageChangeListener(this);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                text.setText(querys.get(position).title);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        }

        /**
         * 列表展示........................................
         */
        private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvTitle;
            TextView tvContent;
            TextView tvDate;
            ImageView ivLeft;

            Query.Result result;

            public ItemViewHolder(View itemView) {
                super(itemView);
                tvTitle = (TextView) itemView.findViewById(R.id.tv_fragment_news_content_title);
                tvContent = (TextView) itemView.findViewById(R.id.tv_fragment_news_content_content);
                tvDate = (TextView) itemView.findViewById(R.id.tv_fragment_news_content_date);
                ivLeft = (ImageView) itemView.findViewById(R.id.iv_item_fragment_news_content_left);
                itemView.findViewById(R.id.cv_fragment_news_content).setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                intent.putExtra("url", result.url);
                startActivity(intent);
            }
        }
    }

    /**
     * 头条适配器...........................................
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return querys.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(mActivity);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            if (querys.size() != 0) {
                String url = querys.get(position).img;

                if (!TextUtils.isEmpty(url)) {
                    Picasso.with(mActivity).load(url).placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher).into(imageView);
                } else {
                    imageView.setImageDrawable(ContextCompat.getDrawable(mActivity, R.mipmap.ic_launcher));
                }
            }

            //头条viewPager触摸监听
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            handler.removeCallbacksAndMessages(null);//按下时清空消息队列

                            break;
                        case MotionEvent.ACTION_CANCEL:
                            handler.sendEmptyMessageDelayed(2, 2000);//动作滑动一半取消后

                            break;
                        case MotionEvent.ACTION_UP:
                            handler.sendEmptyMessageDelayed(2, 2000);//抬起后
                            Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                            intent.putExtra("url", querys.get(0).url);
                            startActivity(intent);

                            break;
                    }
                    //return false;
                    return true;
                }
            });

            container.addView(imageView);//一定不能少，将view加入到viewPager中
            return imageView;
        }
    }
}
