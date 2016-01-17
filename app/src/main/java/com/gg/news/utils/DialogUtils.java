package com.gg.news.utils;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

public class DialogUtils {

    /**
     * 普通对话框
     */
    public static AlertDialog showAlertDialog(Context context, String title, String message,
                                              View view, final AlertCallBack callBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callBack.positive(dialog);
            }
        });
        builder.setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public interface AlertCallBack {
        void positive(DialogInterface dialog);
    }

    /**
     * 单选对话框
     */
    public static AlertDialog showSingleDialog(Context context, String title, String[] items,
                                               int checkedId, final SingleCallBack singleCallBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setSingleChoiceItems(items, checkedId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                singleCallBack.single(dialog, which);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public interface SingleCallBack {
        void single(DialogInterface dialog, int which);
    }

    /**
     * 多选对话框
     */
    public static AlertDialog showMultiDialog(Context context, String title, String[] items,
                                              final boolean[] checkedState, final MultiCallBack multiCallBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMultiChoiceItems(items, checkedState, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedState[which] = isChecked;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                multiCallBack.multi(dialog, checkedState);
            }
        });
        builder.setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public interface MultiCallBack {
        void multi(DialogInterface dialog, boolean[] checkedState);
    }

    /**
     * 进度对话框
     */
    public static ProgressDialog showProgressDialog(Context context, String title,
                                                    String message, boolean horizontal) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        if (horizontal) {
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(100);
            dialog.setProgress(0);
        }
        dialog.show();
        return dialog;
    }

    /**
     * 时间对话框
     */
    public static void showDaterPicker(Context context, final DateSetCallBack dateSet) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //Calendar转化时和这里的monthOfYear是对应的
                dateSet.onDateSet(view, year, monthOfYear + 1, dayOfMonth);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    public interface DateSetCallBack {
        void onDateSet(DatePicker view, int year, int month, int day);
    }

}
