package com.xiaoqiang.bezier.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * description: 贝塞尔曲线示例一
 * autour: xiaoqiang
 * mail:18767164694@126.com
 * qq:773860458
 * date: 2017/6/14 13:45
*/
public class BezierCurveOneView extends View {

    private Point controlPoint = new Point(200, 200);

    public BezierCurveOneView(Context context) {
        super(context);
    }

    public BezierCurveOneView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BezierCurveOneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("BezierCurve","widthsize>>>");
        int widthsize  = MeasureSpec.getSize(widthMeasureSpec);      //取出宽度的确切数值
        int widthmode = MeasureSpec.getMode(widthMeasureSpec);      //取出宽度的测量模式

        int heightsize = MeasureSpec.getSize(heightMeasureSpec);    //取出高度的确切数值
        int heightmode = MeasureSpec.getMode(heightMeasureSpec);    //取出高度的测量模式
        Log.i("BezierCurve","widthsize>>>"+widthsize);
        Log.i("BezierCurve","widthmode>>>"+widthmode);
        Log.i("BezierCurve","heightsize>>>"+heightsize);
        Log.i("BezierCurve","heightmode>>>"+heightmode);
        //如果对View的宽高进行修改了，不要调用 super.onMeasure( widthMeasureSpec, heightMeasureSpec);
        // 要调用 setMeasuredDimension( widthsize, heightsize); 这个函数。

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension( widthsize, 1000);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i("BezierCurve","onSizeChanged>>>"+w+">?>"+h);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        STROKE                //描边
//        FILL                  //填充
//        FILL_AND_STROKE       //描边加填充
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);

        Path path = new Path();
        path.moveTo(100, 500);
        path.quadTo(controlPoint.x, controlPoint.y, 700, 500);
        //绘制路径
        canvas.drawPath(path, paint);
        //绘制辅助点
        canvas.drawPoint(controlPoint.x,controlPoint.y,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                controlPoint.x = (int) event.getX();
                controlPoint.y = (int) event.getY();
                Log.i("BezierCurve","onSizeChanged>>>"+controlPoint.x+">?>"+controlPoint.y);
                invalidate();
                break;
        }
        return true;
    }
}
