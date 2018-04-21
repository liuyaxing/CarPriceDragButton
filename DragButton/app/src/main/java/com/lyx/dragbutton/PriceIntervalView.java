package com.lyx.dragbutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by liuyaxing on 2018/4/8.
 */

public class PriceIntervalView extends View {


    public String PriceName = "自定义价格(万)";
    public String Miriade = "万";


    public String Zero = "0";
    public String Ten = "10";
    public String Twenty = "20";
    public String Thirty = "30";
    public String Forty = "40";
    public String NoLimit = "不限";

    int screenwidth;
    int TotalWidth;


    private Bitmap mDragBmp;
    int paddingleft;
    Paint bmpPaint;
    Paint grayPaint;
    Paint blackPaint;
    Paint titlePaint;


    int lineHeight;
    int titleHeight;
    int upperCenterX;
    private int lineStart;

    private boolean isLowerMoving = false;
    private boolean isUpperMoving = false;

    private int lowerCenterX;


    private int bmpWidth;
    private int bmpHeight;
    private int lineEnd;

    Paint textPaint;

    /**
     * 结果回调
     */
    private OnPriceListener onpriceListener;


    public PriceIntervalView(Context context) {
        super(context);
        Init();
    }

    public PriceIntervalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init();
    }

    public PriceIntervalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }


    public void setOnpriceListener(OnPriceListener onpriceListener) {
        this.onpriceListener = onpriceListener;
    }

    private void Init() {

        screenwidth = ScreenUtil.getScreenWidth(getContext());


        mDragBmp = BitmapFactory.decodeResource(getResources(),
                R.mipmap.icon_drag_btn);


        bmpWidth = mDragBmp.getWidth();
        bmpHeight = mDragBmp.getHeight();

        lineHeight = mDragBmp.getHeight() / 2;


        paddingleft = SizeUtil.dp2px(getContext(), 30);

        TotalWidth = screenwidth - paddingleft * 2;

        lowerCenterX = paddingleft;
//        upperCenterX = TotalWidth+ paddingleft;


        upperCenterX = TotalWidth + paddingleft;

        lineEnd = upperCenterX;

        lineStart = paddingleft - bmpWidth / 2;

        titleHeight = 150;
        InitPaint();


    }

    private void InitPaint() {

        bmpPaint = new Paint();

        grayPaint = new Paint();
        grayPaint.setColor(getResources().getColor(R.color.line_homepage2));
        grayPaint.setAntiAlias(true);
        grayPaint.setStrokeWidth(10);


        blackPaint = new Paint();
        blackPaint.setColor(getResources().getColor(R.color.black));
        blackPaint.setAntiAlias(true);
        blackPaint.setStrokeWidth(10);


        textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#777A85"));
        textPaint.setTextSize(SizeUtil.dp2px(getContext(), 14));
        textPaint.setAntiAlias(true);

        titlePaint = new Paint();
        titlePaint.setColor(getResources().getColor(R.color.black));
        int siz = SizeUtil.dp2px(getContext(), 18);
        titlePaint.setTextSize(siz);
        titlePaint.setAntiAlias(true);

    }


    public void computeScrollTo(int minprice, int maxprice) {


//        computeScale = minprice;
        lowerCenterX = ComputeWidth(minprice);
        upperCenterX = ComputeWidth(maxprice);
        invalidate();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawLine(paddingleft, lineHeight + titleHeight, screenwidth - paddingleft, lineHeight + titleHeight,
                grayPaint);

        canvas.drawLine(lowerCenterX, lineHeight + titleHeight, upperCenterX, lineHeight + titleHeight,
                blackPaint);


        canvas.drawBitmap(mDragBmp, lowerCenterX - bmpWidth / 2, titleHeight
                , bmpPaint);

        canvas.drawBitmap(mDragBmp, upperCenterX - bmpWidth / 2, titleHeight
                , bmpPaint);


        float zeroWidth = textPaint.measureText(Zero);
        canvas.drawText(Zero, paddingleft - zeroWidth / 2, lineHeight * 3 + titleHeight, textPaint);

        float tenWidth = textPaint.measureText(Ten);
        canvas.drawText(Ten, TotalWidth / 5 + paddingleft - tenWidth / 2, lineHeight * 3 + titleHeight, textPaint);

        float twentyWidth = textPaint.measureText(Twenty);
        canvas.drawText(Twenty, TotalWidth / 5 * 2 + paddingleft - twentyWidth / 2, lineHeight * 3 + titleHeight, textPaint);

        float thirtyWidth = textPaint.measureText(Thirty);
        canvas.drawText(Thirty, TotalWidth / 5 * 3 + paddingleft - thirtyWidth / 2, lineHeight * 3 + titleHeight, textPaint);

        float fortyWidth = textPaint.measureText(Forty);
        canvas.drawText(Forty, TotalWidth / 5 * 4 + paddingleft - fortyWidth / 2, lineHeight * 3 + titleHeight, textPaint);

        float textlimitWidth = textPaint.measureText(NoLimit);

        canvas.drawText(NoLimit, TotalWidth + paddingleft - textlimitWidth / 2, lineHeight * 3 + titleHeight, textPaint);


        canvas.drawText(PriceName, paddingleft, 100, titlePaint);

        int minprice = calculation(lowerCenterX);
        int maxprice = calculation(upperCenterX);

        canvas.drawText(minprice + " - " + maxprice + Miriade, screenwidth - paddingleft * 3, 100, titlePaint);


        if (onpriceListener != null) {
            onpriceListener.onUploadSuccess(minprice, maxprice); //接口不断回调给使用者结果值
        }

    }


    private int calculation(int currentwidth) {


        int tep = currentwidth - paddingleft;
        int resultprice = 50 * tep / TotalWidth;


        return resultprice;

    }

    private int ComputeWidth(int price) {

        int resultwidth = price * TotalWidth / 50;

        return resultwidth + paddingleft;


    }


    // 滑动滑块的过程中，更新滑块上方的范围标识
    private void updateRange() {
//        smallRange = computRange(lowerCenterX);
//        bigRange = computRange(upperCenterX);
//
//        if (null != onRangeChangedListener) {
//            onRangeChangedListener.onRangeChanged(smallRange, bigRange);
//        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);


        float xPos = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 如果按下的位置在垂直方向没有与图片接触，则不会滑动滑块
                float yPos = event.getY();
                if (Math.abs(yPos - titleHeight - lineHeight) > bmpHeight / 2) {
                    return false;
                }

                // 表示当前按下的滑块是左边的滑块
                if (Math.abs(xPos - lowerCenterX) < bmpWidth / 2) {
                    isLowerMoving = true;
                }

                // //表示当前按下的滑块是右边的滑块
                if (Math.abs(xPos - upperCenterX) < bmpWidth / 2) {
                    isUpperMoving = true;
                }

                // 单击左边滑块的左边线条时，左边滑块滑动到对应的位置
                if (xPos >= lineStart && xPos <= upperCenterX - bmpWidth / 2) {
                    if (xPos > paddingleft) {
                        lowerCenterX = (int) xPos;
                        updateRange();
//                    postInvalidate();
                        invalidate();
                    }
                }

                // 单击右边滑块的右边线条时， 右边滑块滑动到对应的位置
                if (xPos <= lineEnd && xPos >= lowerCenterX + bmpWidth / 2) {
                    upperCenterX = (int) xPos;
                    updateRange();
//                    postInvalidate();
                    invalidate();
                } else if (xPos > lineEnd) {
                    upperCenterX = lineEnd;
                    invalidate();
                }


//                if (Math.abs(yPos-lowerCenterX)>Math.abs(yPos-upperCenterX)){
//                    lowerCenterX = (int) xPos;
//                    invalidate();
//                }else {
//                    upperCenterX = (int) xPos;
//                    invalidate();
//                }


                break;
            case MotionEvent.ACTION_MOVE:
                // 滑动左边滑块时
                if (isLowerMoving) {
                    if (xPos >= lineStart && xPos < upperCenterX - bmpWidth / 6) {
                        if (xPos > paddingleft) {
                            lowerCenterX = (int) xPos;

                            updateRange();
//                        postInvalidate();
                            invalidate();
                        }
                    }
                }

                // 滑动右边滑块时
                if (isUpperMoving) {
                    if (xPos > lowerCenterX + bmpWidth / 4 && xPos < lineEnd) {
                        upperCenterX = (int) xPos;
                        updateRange();
//                        postInvalidate();
                        invalidate();
                    } else if (xPos > lineEnd) {
                        upperCenterX = lineEnd;
                        invalidate();
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                // 修改滑块的滑动状态为不再滑动
                isLowerMoving = false;
                isUpperMoving = false;
                break;
            default:
                break;
        }

        return true;
    }


}
