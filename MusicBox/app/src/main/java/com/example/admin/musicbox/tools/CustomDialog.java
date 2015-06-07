package com.example.admin.musicbox.tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.musicbox.R;

/**
 * 自定义对话框
 * Created by Admin on 2015/5/28.
 */
public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder{
        private Context context;
        private int mIcon = -1;// 提示图标
        private CharSequence mTitle;// 提示标题
        private CharSequence mMessage;// 提示内容
        private CharSequence mPositiveButtonText;// 确定按钮文本
        private CharSequence mNegativeButtonText;// 拒绝按钮文本
        private CharSequence mNeutralButtonText;// 中间按钮文本
        private boolean mCancelable = true;// 是否启用取消键
        private int mViewSpacingLeft;
        private int mViewSpacingTop;
        private int mViewSpacingRight;
        private int mViewSpacingBottom;
        private boolean mViewSpacingSpecified = false;
        private View mView;

        private OnClickListener mPositiveButtonClickListener,
                mNegativeButtonClickListener, mNeutralButtonClickListener;
        private OnCancelListener mCancelListener;// 取消键事件

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(CharSequence message) {
            this.mMessage = message;
            return this;
        }

        public Builder setMessage(int message) {
            this.mMessage = context.getText(message);
            return this;
        }
        public Builder setTitle(int title) {
            this.mTitle = context.getText(title);
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.mTitle = title;
            return this;
        }

        public Builder setIcon(int icon) {
            this.mIcon = icon;
            return this;
        }

        public Builder setView(View view) {
            this.mView = view;
            mViewSpacingSpecified = false;
            return this;
        }

        public Builder setView(View view, int left, int top, int right,
                               int bottom) {
            this.mView = view;
            this.mViewSpacingLeft = left;
            this.mViewSpacingTop = top;
            this.mViewSpacingRight = right;
            this.mViewSpacingBottom = bottom;
            mViewSpacingSpecified = true;
            return this;
        }
        public Builder setPositiveButton(int textId,
                                         final OnClickListener listener) {
            this.mPositiveButtonText = context.getText(textId);
            this.mPositiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String text,
                                         final OnClickListener listener) {
            this.mPositiveButtonText = text;
            this.mPositiveButtonClickListener = listener;
            return this;
        }

        public Builder setNeutralButton(int textId,
                                        final OnClickListener listener) {
            this.mNeutralButtonText = context.getText(textId);
            this.mNeutralButtonClickListener = listener;
            return this;
        }

        public Builder setNeutralButton(String text,
                                        final OnClickListener listener) {
            this.mNeutralButtonText = text;
            this.mNeutralButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int textId,
                                         final OnClickListener listener) {
            this.mNegativeButtonText = context.getText(textId);
            this.mNegativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String text,
                                         final OnClickListener listener) {
            this.mNegativeButtonText = text;
            this.mNegativeButtonClickListener = listener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener listener) {
            this.mCancelListener = listener;
            return this;
        }

        public CustomDialog create(){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog dialog = new CustomDialog(context, R.style.CustomDialog);
            dialog.setCancelable(mCancelable);
            if(mCancelListener != null){
                dialog.setOnCancelListener(mCancelListener);    //设置取消键事件
            }
            View layout = inflater.inflate(R.layout.alert_dialog, (ViewGroup) ((Activity)context).findViewById(R.id.parentPanel));
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            ((TextView)layout.findViewById(R.id.alertTitle)).setText(mTitle);   //设置标题
            if(mIcon != -1){
                ((ImageView)layout.findViewById(R.id.icon)).setBackgroundResource(mIcon);     //设置图标
            }
            int count = 0;
            // 设置确定按钮
            if(setButton(layout, mPositiveButtonText, R.id.button1, dialog, mPositiveButtonClickListener)) count++;
            // 设置拒绝按钮
            if(setButton(layout, mNegativeButtonText, R.id.button2, dialog, mNegativeButtonClickListener)) count++;
            // 设置中间按钮
            if(setButton(layout, mNeutralButtonText, R.id.button3, dialog, mNeutralButtonClickListener)) count++;
            if(count==0){
                layout.findViewById(R.id.buttonPanel).setVisibility(View.GONE);
            }
            if (count == 1) {                            //一个按钮时，显示两边空间
                layout.findViewById(R.id.leftSpacer)
                        .setVisibility(View.INVISIBLE);
                layout.findViewById(R.id.rightSpacer).setVisibility(
                        View.INVISIBLE);
            }
            if (!TextUtils.isEmpty(mMessage)) {
                ((TextView) layout.findViewById(R.id.message))
                        .setText(mMessage);
            } else {
                ((LinearLayout) layout.findViewById(R.id.contentPanel))
                        .setVisibility(View.GONE);
            }
            // 设置提示内容布局
            if (mView != null) {
                final FrameLayout customPanel = (FrameLayout) layout
                        .findViewById(R.id.customPanel);
                if (mViewSpacingSpecified) {
                    customPanel.setPadding(mViewSpacingLeft, mViewSpacingTop,
                            mViewSpacingRight, mViewSpacingBottom);
                }
                customPanel.addView(mView);
            } else {
                ((FrameLayout) layout.findViewById(R.id.customPanel))
                        .setVisibility(View.GONE);
            }
            dialog.setContentView(layout);
            return dialog;
        }

        public CustomDialog show() {
            CustomDialog dialog = create();
            dialog.show();
            return dialog;
        }

        private boolean setButton(View layout, CharSequence mPositiveButtonText2, int id,
                                  final Dialog dialog, final OnClickListener listener) {
            if (!TextUtils.isEmpty(mPositiveButtonText2)) {
                final Button button1 = (Button) layout.findViewById(id);
                button1.setText(mPositiveButtonText2);
                if (listener != null) {
                    button1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            listener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }else{
                    //默认事件为关闭对话框
                    button1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            dialog.cancel();
                            dialog.dismiss();
                        }
                    });
                }
                return true;
            } else {
                layout.findViewById(id).setVisibility(View.GONE);
                return false;
            }
        }
    }


}
