package com.vktrhrsny.uimascot;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MascotView extends LinearLayout {

    private TextView textView;
    private ImageView imageView;

    public MascotView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.obtainStyledAttributes(attrs,
                R.styleable.MascotView, 0, 0);

        @SuppressWarnings("ResourceAsColor")
        int valueColor = attributes.getColor(R.styleable.MascotView_textColor,
                android.R.color.holo_orange_dark);
        attributes.recycle();

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mascot_layout, this, true);

        textView = (TextView)getChildAt(0);
        textView.setBackgroundColor(valueColor);

        imageView = (ImageView) getChildAt(1);
    }

    public MascotView(Context context) {
        this(context, null);
    }

    public void setColorBackground(int color) {
        textView.setBackgroundColor(color);
    }

    public void setTextColor(int color){
        textView.setTextColor(color);
    }

    public void setImageVisible(boolean visible) {
        imageView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

}
