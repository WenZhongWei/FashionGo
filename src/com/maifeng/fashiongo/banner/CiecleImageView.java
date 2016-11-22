package com.maifeng.fashiongo.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.maifeng.fashiongo.R;

public class CiecleImageView extends ImageView{
	//外圆的宽度
    private int outCircleWidth;
    //外圆的颜色
    private int outCircleColor = Color.WHITE;
    //背景画笔
    private Paint paintBorder;

    private int viewWidth;
    private int viewHeight;

    private Bitmap image;
    
    public CiecleImageView(Context context) {
        super(context);
        initAttrs(context, null);
    }

    public CiecleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public CiecleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {

        if (null != attrs) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CiecleImageView);

            int len = array.length();
            for (int i = 0; i < len; i++) {
                int attr = array.getIndex(i);

                switch (attr) {
                    case R.styleable.CiecleImageView_outCiecleColor:
                        this.outCircleColor = array.getColor(attr, Color.WHITE);
                        break;
                    case R.styleable.CiecleImageView_outCiecleWith:
                        this.outCircleWidth = (int) array.getDimension(attr, 5);
                        break;
                }
            }
        }

        paintBorder = new Paint();
        paintBorder.setColor(outCircleColor);
        paintBorder.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);

        viewWidth = width - (outCircleWidth * 2);
        viewHeight = height - (outCircleWidth * 2);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        loadimg();

        if (image != null) {
            int min = Math.min(viewHeight, viewWidth);

            int ciecleCenter = min / 2;

            image = Bitmap.createScaledBitmap(image, min, min, false);

            canvas.drawCircle(ciecleCenter + outCircleWidth, ciecleCenter + outCircleWidth, ciecleCenter + outCircleWidth, paintBorder);
            canvas.drawBitmap(createCiecleBitmap(image, min), outCircleWidth, outCircleWidth, null);
        }
    }

    private Bitmap createCiecleBitmap(Bitmap image, int min) {
        Bitmap bitmap = null;

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        bitmap = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(min/2, min/2, min/2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(image, 0, 0, paint);

        return bitmap;
    }

    private void loadimg() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getDrawable();

        if (bitmapDrawable != null) {
            image = bitmapDrawable.getBitmap();
        }
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = viewWidth;
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = viewHeight;
        }
        return result;
		
    }

    public void setOutCiecleColor(int color){
        if (null != paintBorder){
            paintBorder.setColor(color);
        }
        this.invalidate();
    }

    //设置外圆的宽度
    public void setBorderWidth(int outCircleWitdth){
        this.outCircleWidth=outCircleWitdth;

        this.invalidate();
    }

}
