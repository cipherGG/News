package com.gg.news.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gg.news.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity {

    private ViewPager viewPager;

    private static final int[] mImageIds = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    private List<ImageView> list;
    private Button button;
    private LinearLayout layout;
    private View redPoint;
    private int pointBetween;
    private RelativeLayout.LayoutParams redParams;

    @Override
    public void initView() {
        setContentView(R.layout.activity_guide);
        viewPager = (ViewPager) findViewById(R.id.vp_activity_guide);
        button = (Button) findViewById(R.id.btn_activity_guide);
        layout = (LinearLayout) findViewById(R.id.linear_activity_guide_point);
        redPoint = findViewById(R.id.view_activity_guide_red_point);

        list = new ArrayList<>();
        for (int mImageId : mImageIds) {
            ImageView image = new ImageView(this);
            // image.setBackgroundResource(mImageIds[i]);原来的图片会失真
            image.setImageBitmap(BitmapFactory.decodeResource(getResources(), mImageId));
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            list.add(image);
        }

        initPoint();

        Adapter adapter = new Adapter();
        viewPager.setAdapter(adapter);
    }

    private void initPoint() {
        //要cast一下，要不然下面设置不了margin
        redParams = (RelativeLayout.LayoutParams) redPoint.getLayoutParams();

        for (int i = 0; i < mImageIds.length; i++) {
            View grayPoint = new View(this);
            grayPoint.setBackgroundResource(R.drawable.shape_point_gray);
            // 下面两个方法都不行
            // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
            // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(redPoint.getWidth(), redPoint.getHeight());
            LinearLayout.LayoutParams grayParams = new LinearLayout
                    .LayoutParams(redParams.width, redParams.height);//此行不能放置循环外
            if (i > 0) {
                grayParams.leftMargin = 20;
            }
            grayPoint.setLayoutParams(grayParams);
            layout.addView(grayPoint);
        }
    }

    @Override
    public void initListener() {
        // 获取视图树，监听onLayout方法执行完成后
        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 获取两个点之间的距离，放在外面值是0，因为onLayout方法还没执行完成
                pointBetween = layout.getChildAt(1).getLeft() - layout.getChildAt(0).getLeft();

                // 会执行很多次，监听成功后就可以删除了
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // positionOffset是百分比，跳转到2页的时候会回归0
                redParams.leftMargin = (int) (pointBetween * positionOffset) + position * pointBetween;
                //这一行别忘了加
                redPoint.setLayoutParams(redParams);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == list.size() - 1) {
                    button.setVisibility(View.VISIBLE);
                } else {
                    button.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this, HomeActivity.class));
                finish();
            }
        });
    }

    private class Adapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));

            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
