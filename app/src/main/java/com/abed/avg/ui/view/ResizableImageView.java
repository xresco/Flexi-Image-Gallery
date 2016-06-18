package com.abed.avg.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;


public class ResizableImageView extends ImageView {

    int width, height;

    public ResizableImageView(Context context) {
        super(context);
    }

    public ResizableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResizableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        Drawable d = getDrawable();
//        if (d != null) {
//            int width = MeasureSpec.getSize(widthMeasureSpec);
//            int height = (int) Math.ceil((float) width * (float) d.getIntrinsicHeight() / (float) d.getIntrinsicWidth());
//            setMeasuredDimension(width, height);
//            return;
//        }
        if (this.width == 0) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
        } else {
            setMeasuredDimension(width, height);
        }
    }
}
