package com.gg.news.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initData();

        initListener();
    }

    public void initView() {
    }

    public void initData() {
    }

    public void initListener() {
    }

    public BaseActivity getActivity() {
        return this;
    }

    public void showToast(String show) {
        Toast.makeText(BaseActivity.this, show, Toast.LENGTH_SHORT).show();
    }

    public void showSnaker(View view, String show, String action, View.OnClickListener listener) {
        Snackbar.make(view, show, Snackbar.LENGTH_SHORT).setAction(action, listener).show();
    }

    public void openActivity(Class mClass) {
        Intent intent = new Intent(this, mClass);
        startActivity(intent);
    }

    public void openActivityResult(Class mClass, int requestCode) {
        Intent intent = new Intent(this, mClass);
        startActivityForResult(intent, requestCode);
    }

    public void openService(Class mClass) {
        Intent intent = new Intent(this, mClass);
        startService(intent);
    }

    public void stopService(Class mClass) {
        Intent intent = new Intent(this, mClass);
        stopService(intent);
    }

}
