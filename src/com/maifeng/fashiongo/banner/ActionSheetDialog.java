package com.maifeng.fashiongo.banner;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.maifeng.fashiongo.R;

public class ActionSheetDialog {
	private Context context;
    private Dialog dialog;
    private TextView txt_title;
    private TextView txt_cancel;
    private LinearLayout lLayout_content;
    private ScrollView sLayout_content;
    private boolean isShowTitle = false;
    private Display display;
    private List<SheetItem> sheetItemList;

    public ActionSheetDialog(Context context) {
        this.context = context;

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public ActionSheetDialog Builder() {
        //获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.view_actionsheet, null);

        //dialog的最小宽，设置屏幕宽度为
        view.setMinimumWidth(display.getWidth());

        //获取xml文件中的控件
        sLayout_content = (ScrollView) view.findViewById(R.id.sLayout_content);
        lLayout_content = (LinearLayout) view.findViewById(R.id.lLayout_content);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //定义dialog的布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

        return this;
    }

    public ActionSheetDialog setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            isShowTitle = true;
            txt_title.setVisibility(View.VISIBLE);
            txt_title.setText(title);
        }
        return this;
    }

    public ActionSheetDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public ActionSheetDialog addSheetItem(String name, SheetItemColor color, OnSheetItemClickListener listener) {
        if (null == sheetItemList) {
            sheetItemList = new ArrayList<SheetItem>();

        }
        sheetItemList.add(new SheetItem(name, color, listener));
        return this;
    }

    public void show() {
        setSheetItems();
        dialog.show();
    }

    private void setSheetItems() {
        if (sheetItemList == null || sheetItemList.size() < 1) {
            return;
        }

        int size = sheetItemList.size();

        //控制高度
        if (size > 6) {
            ViewGroup.LayoutParams params = sLayout_content.getLayoutParams();
            params.height = display.getHeight() / 2;
            sLayout_content.setLayoutParams(params);
        }

        for (int i = 1; i <= size; i++) {
            final int index = i;

            final SheetItem sheetItem = sheetItemList.get(i - 1);

            TextView textView = new TextView(context);
            textView.setText(sheetItem.name);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);

            if (size == 1) {
                if (isShowTitle) {
                    textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                } else {
                    textView.setBackgroundResource(R.drawable.actionsheet_single_pressed);
                }
            } else {
                if (isShowTitle) {
                    if (i >= 1 && i < size) {
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                } else {
                    if (i == 1) {
                        textView.setBackgroundResource(R.drawable.actionsheet_top_selector);
                    } else if (i < size) {
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                }
            }

            //字体颜色
            if (sheetItem.color != null) {
                textView.setTextColor(Color.parseColor(sheetItem.color.getName()));
            } else {
                textView.setTextColor(Color.parseColor(SheetItemColor.BULE.getName()));
            }

            //高度
            float scale = context.getResources().getDisplayMetrics().density;
            int height = (int) (45 * scale + 0.5);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));

            //点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sheetItem.listener.onClick(index);
                    dialog.dismiss();
                }
            });
            lLayout_content.addView(textView);
        }
    }

    private class SheetItem {
        String name;
        OnSheetItemClickListener listener;
        SheetItemColor color;

        public SheetItem(String name, SheetItemColor color, OnSheetItemClickListener listener) {
            this.name = name;
            this.listener = listener;
            this.color = color;
        }
    }

    public interface OnSheetItemClickListener {
        void onClick(int witch);
    }

    public enum SheetItemColor {
        BULE("#037BFF"), RED("#FD4A2E");
        String name;

        private SheetItemColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
