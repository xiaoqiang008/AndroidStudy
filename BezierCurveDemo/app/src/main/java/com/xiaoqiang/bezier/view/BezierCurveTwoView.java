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
 * description: 贝塞尔曲线示例二
 * autour: xiaoqiang
 * mail:18767164694@126.com
 * qq:773860458
 * date: 2017/6/14 16:15
*/
public class BezierCurveTwoView extends View {
    private Point controlPointOne = new Point(200, 200);
    private Point controlPointTwo = new Point(500, 200);

    private int isControlPointTwo = -1 ;

    private Paint paintBezier;
    private Paint paintLine;
    public BezierCurveTwoView(Context context) {
        super(context);
    }

    public BezierCurveTwoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public BezierCurveTwoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void  initPaint(){
        paintBezier = new Paint();
        paintBezier.setStyle(Paint.Style.STROKE);
        paintBezier.setColor(Color.BLACK);
        paintBezier.setStrokeWidth(10);


        paintLine = new Paint();
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setColor(Color.RED);
        paintLine.setStrokeWidth(3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Path path = new Path();
        path.moveTo(20, 500);
        path.cubicTo(controlPointOne.x, controlPointOne.y,controlPointTwo.x, controlPointTwo.y,700, 500);
        //绘制路径
        canvas.drawPath(path, paintBezier);
        //绘制辅助点
        canvas.drawPoint(controlPointOne.x, controlPointOne.y, paintBezier);
        canvas.drawPoint(controlPointTwo.x, controlPointTwo.y, paintBezier);
        //绘制连线
//        canvas.drawLine(100, 500, controlPointOne.x, controlPointOne.y, paintLine);
//        canvas.drawLine(900, 500, controlPointOne.x, controlPointOne.y, paintLine);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("BezierCurve","ACTION_DOWN>>>");
                int x = (int) event.getX();
                int y = (int) event.getY();
                if(Math.abs(controlPointOne.x -x) < 40 && Math.abs(controlPointOne.y -y) < 40){
                    isControlPointTwo = 0;
                }else if(Math.abs(controlPointTwo.x -x) < 40 && Math.abs(controlPointTwo.y -y) < 40){
                    isControlPointTwo = 1;
                }else {
                    isControlPointTwo = -1;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                switch (isControlPointTwo){
                    case 0:
                        controlPointOne.x = (int) event.getX();
                        controlPointOne.y = (int) event.getY();
                        invalidate();
                        break;
                    case 1:
                        controlPointTwo.x = (int) event.getX();
                        controlPointTwo.y = (int) event.getY();
                        invalidate();
                        break;
                    case -1:
                        break;
                }

                break;
        }
        return true;
    }

}
