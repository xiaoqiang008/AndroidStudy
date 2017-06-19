package com.xiaoqiang.qqmenu.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xiaoqiang.qqmenu.R;

/** 
 * description: 防QQ 7.1.0侧滑菜单
 * autour: xiaoqiang
 * mail:18767164694@126.com
 * qq:773860458
 * date: 2017/6/16 14:28
*/
public class VDHLayout extends FrameLayout {

    private Context context;
    //定义一个ViewDragHelper
    public ViewDragHelper mDragger;
    //主页面
    private RelativeLayout mainView;
    //左侧菜单
    private RelativeLayout leftView;
    //主页面左边阴影
    private LinearLayout left_shade;
    //左侧菜单宽高
    private int width, height;
    //主页面宽度，屏幕宽度
    private int mainWidth;
    //水平拖拽的距离
    private int range;
    //自定义监听事件
    private VDHLayoutListener vdhLayoutListener;
    //页面状态 默认为关闭
    private Status status = Status.CLOSE;
    //主页面close时，逐渐变暗
    private ImageView main_top_bg;
    //滑动进度
    private float percent;

    public VDHLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mDragger = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
    }

    class DragHelperCallback extends ViewDragHelper.Callback {
        /**
         * 进行捕获拦截，那些View可以进行drag操作
         * 传递当前触摸的子View实例，如果当前的子View需要进行拖拽移动返回true
         * @param child
         * @param pointerId
         * @return 直接返回true，拦截所有的VIEW
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        /**
         * 决定拖拽的View在水平方向上面移动到的位置
         * @param child
         * @param left
         * @param dx
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == mainView) {//主页面按住滑动
            }
            if (child == leftView) {//侧边页面按住滑动
                left = mainView.getLeft() + dx;
            }
            if (left < 0) {
                left = 0;
            } else if (left > width) {
                left = width;
            }
            return left;
        }

        /**
         * 决定拖拽的View在垂直方向上面移动到的位置
         * @param child
         * @param top
         * @param dy
         * @return
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            top = 0;
            return top;
        }


        @Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }

        /**
         * 当前被触摸的View位置变化时回调
         * changedView为位置变化的View，left/top变化时新的x左/y顶坐标，dx/dy为从旧到新的偏移量
         * @param changedView
         * @param left
         * @param top
         * @param dx
         * @param dy
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            //设置主页面位置
            leftView.layout(left / 2 - width / 2, 0, width / 2 + left / 2, height);
            //设置侧边页面位置
            mainView.layout(left, 0, mainWidth + left, height);
            //设置主页面左边阴影位子
            left_shade.layout(left - left_shade.getMeasuredWidth(), 0, left, height);
            //记录移动位置
            range = left;
            //滑动时主页面上明暗变化
            percent = range / (float) width;
            main_top_bg.setAlpha(percent / 2f);
            if (percent == 0) {
                main_top_bg.setVisibility(View.GONE);
            } else {
                main_top_bg.setVisibility(View.VISIBLE);
            }
            //滑动进度返回
            if (vdhLayoutListener != null) {
                vdhLayoutListener.onDrag(percent);
            }
        }

        /**
         * 当拖拽的子View，手势释放的时候回调的方法， 然后根据左滑或者右滑的距离进行判断打开或者关闭
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (xvel > 0) {
                open();
            } else if (xvel < 0) {
                close();
            } else if (releasedChild == mainView && range > width / 2) {
                open();
            } else if (releasedChild == leftView && range < width / 2) {
                close();
            } else if (releasedChild == mainView) {
                close();
            } else {
                open();
            }
        }
    }

    /**
     * 拦截触摸事件
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragger.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }

    /**
     * 调用进行left和main 视图进行初始位置布局
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        leftView.layout(-(width / 2), 0, width / 2, height);
        mainView.layout(0, 0, mainWidth, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = leftView.getMeasuredWidth();
        height = leftView.getMeasuredHeight();
        mainWidth = getWidth();
//        width = (int)(mainWidth*0.5);
    }

    /**
     * 当View中所有的子控件均被映射成xml后触发
     * <p>
     * 布局加载完成回调
     * 做一些初始化的操作
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        leftView = (RelativeLayout) getChildAt(0);
        left_shade = (LinearLayout) getChildAt(1);
        mainView = (RelativeLayout) getChildAt(2);
        main_top_bg = (ImageView) mainView.findViewById(R.id.main_top_bg);
        main_top_bg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        main_top_bg.setAlpha(0f);
        main_top_bg.setVisibility(GONE);
    }

    /**
     * 有加速度,当我们停止滑动的时候，该不会立即停止动画效果
     */
    @Override
    public void computeScroll() {
        if (mDragger.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * 页面状态(滑动,打开,关闭)
     */
    public enum Status {
        DRAG, OPEN, CLOSE
    }

    /**
     * 页面状态设置
     *
     * @return
     */
    public Status getStatus() {
        if (range == 0) {
            status = Status.CLOSE;
        } else if (range == width) {
            status = Status.OPEN;
        } else {
            status = Status.DRAG;
        }
        return status;
    }

    /**
     * 关闭侧边菜单
     */
    public void close() {
        //继续滑动
        if (mDragger.smoothSlideViewTo(mainView, 0, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        if (vdhLayoutListener != null) {
            vdhLayoutListener.close();
        }
    }

    /**
     * 打开侧边菜单
     */
    public void open() {
        if (mDragger.smoothSlideViewTo(mainView, width, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        if (vdhLayoutListener != null) {
            vdhLayoutListener.open();
        }
    }

    public interface VDHLayoutListener {
        //打开侧边页面
        void open();
        //关闭侧边页面
        void close();
        //打开关闭侧边页面进度返回，0——关闭，1——打开
        void onDrag(float percent);
    }

    public void setVdhLayoutListener(VDHLayoutListener vdhLayoutListener) {
        this.vdhLayoutListener = vdhLayoutListener;
    }
}
