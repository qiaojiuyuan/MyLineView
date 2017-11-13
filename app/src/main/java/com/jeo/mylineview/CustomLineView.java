package com.jeo.mylineview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义线性图View
 * Created by qiaojy on 2017/10/31.
 */

public class CustomLineView extends View {

    private int mHeight = 0;
    private int mWidth = 0;
    private Paint mPaint = new Paint();
    /**左右边距*/
    private final static int offset = 100;
    /**此值越大，整体坐标位置越向屏幕下方*/
    private final static int offsetY = 100;
    /**文字的x轴偏移量*/
    private final static int offsetValueX = 6;
    /**活跃值最大值*/
    private final static int MAX_VALUE = 600;

    private Bitmap smallPoint = BitmapFactory.decodeResource(getResources(), R.drawable.small_point);
    private Bitmap bigPoint = BitmapFactory.decodeResource(getResources(),R.drawable.big_point);
    private Bitmap onePlaces = BitmapFactory.decodeResource(getResources(),R.drawable.one_places);
    private Bitmap threePlaces = BitmapFactory.decodeResource(getResources(),R.drawable.three_places);
    private Bitmap fourPlaces = BitmapFactory.decodeResource(getResources(),R.drawable.four_places);
    //四个点的值
    private int point1Value = 0;
    private int point2Value = 0;
    private int point3Value = 0;
    private int point4Value = 0;

    //四个日期
    private String date1 = "";
    private String date2 = "";
    private String date3 = "";
    private String date4 = "";

    public CustomLineView(Context context) {
        super(context);
    }

    public CustomLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec) - 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.reset();
        mPaint.setAntiAlias(true);
        //画四个圆点
        mPaint.setStrokeWidth(25);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        int smallBitmapWidth = smallPoint.getWidth();
        int smallBitmapHeight = smallPoint.getHeight();
        int bigBitmapWidth = bigPoint.getWidth();
        int bigBitmapHeight = bigPoint.getHeight();
        //内部矩形的宽
        int insideRectWidth = mWidth -offset*2;
        //内部矩形的宽的三分之一
        int blockDistance = insideRectWidth / 3;
        //画第一个点
        int point1X = offset - smallBitmapWidth/2;
        canvas.drawBitmap(smallPoint,point1X,pointY(point1Value),mPaint);
        //画第二个点
        int point2X = blockDistance + offset - (smallBitmapWidth / 2);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.small_point),point2X,pointY(point2Value),mPaint);
        //画第三个点
        int point3X = offset + blockDistance * 2 - (smallBitmapWidth / 2);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.small_point),point3X,pointY(point3Value),mPaint);
        //确定第四个点的X坐标，因为第四个点是空心的，先画第四个点会被线给盖住，所以先画线后画第四个点
        int point4X = offset + blockDistance * 3 - (bigBitmapWidth / 2);

        //画每个点的连接线
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#7fffffff"));
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPaint.setStrokeWidth(2);
        int offsetSmallPic = smallBitmapHeight /2;
        int offsetBigPic = bigBitmapHeight /2;
        canvas.drawLine(point1X + offsetSmallPic,pointY(point1Value) + offsetSmallPic,point2X + offsetSmallPic,pointY(point2Value) + offsetSmallPic,mPaint);
        canvas.drawLine(point2X + offsetSmallPic,pointY(point2Value) + offsetSmallPic,point3X + offsetSmallPic,pointY(point3Value) + offsetSmallPic,mPaint);
        canvas.drawLine(point3X + offsetSmallPic,pointY(point3Value) + offsetSmallPic,point4X,pointY(point4Value),mPaint);

        //画第四个点
        mPaint.setColor(Color.WHITE);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.big_point),point4X,pointY(point4Value) - offsetBigPic,mPaint);

        //画白线
        mPaint.setColor(Color.parseColor("#7fffffff"));
        mPaint.setStrokeWidth(1);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(0,mHeight + 20,mWidth,mHeight + 20,mPaint);

        //计算文字所在矩形，可以得到宽高
        mPaint.setColor(Color.parseColor("#7fffffff"));
        mPaint.setTextSize(30);
        //注意，这行代码一定要在设置字体大小后调用才能得到正确的字的宽度
        int textWidth = (int)mPaint.measureText(date1);
        //画日期
        canvas.drawText(date1,offset - textWidth / 2,mHeight + 90,mPaint);
        canvas.drawText(date2,blockDistance + offset - textWidth / 2,mHeight + 90,mPaint);
        canvas.drawText(date3,offset + blockDistance * 2 - textWidth / 2,mHeight + 90,mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText(date4,offset + blockDistance * 3 - textWidth / 2,mHeight + 90,mPaint);

        //画点上的数值
        mPaint.setColor(Color.parseColor("#7fffffff"));
        mPaint.setTextSize(30);

        String sPoint1 = Integer.toString(point1Value);
        Rect valueRect1 = new Rect();
        mPaint.getTextBounds(sPoint1, 0, sPoint1.length(), valueRect1);
        int valueWidth1 = valueRect1.width();
        canvas.drawText(sPoint1,sPoint1.length() == 1 ? point1X : point1X - valueWidth1 / 2 + offsetValueX,pointY(point1Value) - 20,mPaint);

        String sPoint2 = Integer.toString(point2Value);
        Rect valueRect2 = new Rect();
        mPaint.getTextBounds(Integer.toString(point2Value), 0, Integer.toString(point2Value).length(), valueRect2);
        int valueWidth2 = valueRect2.width();
        canvas.drawText(sPoint2,sPoint2.length() == 1 ? point2X : point2X - valueWidth2 / 2 + offsetValueX,pointY(point2Value) - 20,mPaint);

        String sPoint3 = Integer.toString(point3Value);
        Rect valueRect3 = new Rect();
        mPaint.getTextBounds(sPoint3, 0, sPoint3.length(), valueRect3);
        int valueWidth3 = valueRect3.width();
        canvas.drawText(sPoint3,sPoint3.length() == 1 ? point3X : point3X - valueWidth3 / 2 + offsetValueX,pointY(point3Value) - 20,mPaint);

        mPaint.setColor(Color.parseColor("#ffffff"));
        String sPoint4 = Integer.toString(point4Value);
        Rect valueRect4 = new Rect();
        mPaint.getTextBounds(sPoint4, 0, sPoint4.length(), valueRect4);
        int valueWidth4 = valueRect4.width();
        canvas.drawText(sPoint4,sPoint4.length() == 1 ? offset + blockDistance * 3 -8 : offset + blockDistance * 3 - valueWidth4 / 2,pointY(point4Value) - 60,mPaint);
        //画第四个点的白色汽泡背景
        if (sPoint4.length() == 1) {
            canvas.drawBitmap(onePlaces,offset + blockDistance * 3 - onePlaces.getWidth() / 2,pointY(point4Value) - 60 - threePlaces.getHeight() / 2 - 5,mPaint);
        } else if (sPoint4.length() == 2 ) {
            canvas.drawBitmap(threePlaces,offset + blockDistance * 3 - threePlaces.getWidth() / 2,pointY(point4Value) - 60 - threePlaces.getHeight() / 2 - 5,mPaint);
        }else if (sPoint4.length() == 3) {
            canvas.drawBitmap(threePlaces,offset + blockDistance * 3 - threePlaces.getWidth() / 2,pointY(point4Value) - 60 - threePlaces.getHeight() / 2 - 5,mPaint);
        } else if (sPoint4.length() == 4) {
            canvas.drawBitmap(fourPlaces,offset + blockDistance * 3 - valueWidth4,pointY(point4Value) - 100,mPaint);
        }

        //画第四个点下面的白柱
        if (point4Value > 0) {
            LinearGradient linearGradient = new LinearGradient(mWidth - offset,mHeight + 40,point4X,pointY(point4Value), Color.parseColor("#7fffffff"), Color.parseColor("#00ffffff"), Shader.TileMode.CLAMP);
            mPaint.setShader(linearGradient);
            mPaint.setStrokeWidth(bigBitmapWidth);
            canvas.drawLine(mWidth - offset,mHeight + 20,mWidth - offset,pointY(point4Value) + bigBitmapHeight / 4,mPaint);
            mPaint.setShader(null);
        }
    }

    private int pointY(int current) {
        int y = (mHeight - offsetY) - ((mHeight - offsetY) * current / MAX_VALUE) + offsetY;
        return y < 0 ? 0 : y;
    }

    public int getPoint1Value() {
        return point1Value;
    }

    public void setPoint1Value(int point1Value) {
        if(point1Value < 0) {
            point1Value = 0;
        } else if (point1Value > MAX_VALUE) {
            point1Value = MAX_VALUE;
        }
        this.point1Value = point1Value;
    }

    public int getPoint2Value() {
        return point2Value;
    }

    public void setPoint2Value(int point2Value) {
        if(point2Value < 0) {
            point2Value = 0;
        } else if (point2Value > MAX_VALUE) {
            point2Value = MAX_VALUE;
        }
        this.point2Value = point2Value;
    }

    public int getPoint3Value() {
        return point3Value;
    }

    public void setPoint3Value(int point3Value) {
        if(point3Value < 0) {
            point3Value = 0;
        } else if (point3Value > MAX_VALUE) {
            point3Value = MAX_VALUE;
        }
        this.point3Value = point3Value;
    }

    public int getPoint4Value() {
        return point4Value;
    }

    public void setPoint4Value(int point4Value) {
        if(point4Value < 0) {
            point4Value = 0;
        } else if (point4Value > MAX_VALUE) {
            point4Value = MAX_VALUE;
        }
        this.point4Value = point4Value;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getDate3() {
        return date3;
    }

    public void setDate3(String date3) {
        this.date3 = date3;
    }

    public String getDate4() {
        return date4;
    }

    public void setDate4(String date4) {
        this.date4 = date4;
    }
}
