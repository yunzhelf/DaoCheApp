package com.yifactory.daocheapp.utils;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.yifactory.daocheapp.R;
import com.yifactory.daocheapp.widget.SDProgressDialog;

public class SDDialogUtil {

    public static Dialog alert(Context context, CharSequence title, CharSequence message) {

        Builder builder = new Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public static Dialog alert(Context context, int title, int message) {

        Builder builder = new Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public static Dialog confirm(Context context, CharSequence title, CharSequence message, DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {
        Builder builder = new Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        try {
            builder.setPositiveButton("确定", confirmListener);
        } catch (Exception e) {
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        try {
            builder.setNegativeButton("取消", cancelListener);
        } catch (Exception e) {
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public static Dialog confirm(Context context, int title, int message, DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {
        Builder builder = new Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        try {
            builder.setPositiveButton("确定", confirmListener);
        } catch (Exception e) {
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        try {
            builder.setNegativeButton("取消", cancelListener);
        } catch (Exception e) {
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    // 弹出自定义的窗体
    public static Dialog showView(Context context, CharSequence title, View view, DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {
        Builder builder = new Builder(context);
        if (title != "")
            builder.setTitle(title);
        builder.setView(view);

        try {
            builder.setPositiveButton("确定", confirmListener);
        } catch (Exception e) {
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        try {
            builder.setNegativeButton("取消", cancelListener);
        } catch (Exception e) {
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public static Dialog showView(Context context, int title, View view, DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {
        Builder builder = new Builder(context);
        if (title != 0)
            builder.setTitle(title);
        builder.setView(view);

        try {
            builder.setPositiveButton("确定", confirmListener);
        } catch (Exception e) {
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        try {
            builder.setNegativeButton("取消", cancelListener);
        } catch (Exception e) {
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    // 弹出自定义的信息
    public static Dialog showMsg(Context context, CharSequence title, CharSequence message) {
        Builder builder = new Builder(context);
        if (title != "")
            builder.setTitle(title);
        builder.setMessage(message);
        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public static Dialog showMsg(Context context, int title, int message) {
        Builder builder = new Builder(context);
        if (title != 0)
            builder.setTitle(title);
        builder.setMessage(message);
        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public static Dialog newLoading(Context context, String message) {
        SDProgressDialog dialog = new SDProgressDialog(context, R.style.selectorDialog);
        TextView txt = dialog.getmTxtMsg();
        if (message != null && txt != null) {
            txt.setText(message);
        }
        dialog.setCancelable(false);
        return dialog;
    }

}
