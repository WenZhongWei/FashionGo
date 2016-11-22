package com.maifeng.fashiongo.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {
    
    private OnOverScrolledListener mOnOverScrolledListener = null;
    /**
     * @param context
     */
    public CustomScrollView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     */
    public CustomScrollView(Context context, AttributeSet attrs) {
            super(context, attrs);
            // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see android.widget.ScrollView#arrowScroll(int)
     */
    @Override
    public boolean arrowScroll(int arg0) {
            // TODO Auto-generated method stub
            return super.arrowScroll(arg0);
    }

    /* (non-Javadoc)
     * @see android.widget.ScrollView#computeScroll()
     */
    @Override
    public void computeScroll() {
            // TODO Auto-generated method stub
            super.computeScroll();
    }

/**
     * @return the mOnOverScrolledListener
     */
    public OnOverScrolledListener getOnOverScrolledListener() {
            return mOnOverScrolledListener;
    }

    /**
     * @param mOnOverScrolledListener the mOnOverScrolledListener to set
     */
    public void setOnOverScrolledListener(OnOverScrolledListener mOnOverScrolledListener) {
            this.mOnOverScrolledListener = mOnOverScrolledListener;
    }

public interface OnOverScrolledListener{
    public abstract void onOverScrolled(View view, int scrollX, int scrollY, boolean clampedX, boolean clampedY);
}

/* (non-Javadoc)
 * @see android.widget.ScrollView#onOverScrolled(int, int, boolean, boolean)
 */
@Override
protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
    // TODO Auto-generated method stub
    super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    if(mOnOverScrolledListener != null) {
        mOnOverScrolledListener.onOverScrolled(this, scrollX, scrollY, clampedX, clampedY);
    }
    Log.i("CustomScrollView", "===DBG:onOverScrolled");
}

}
