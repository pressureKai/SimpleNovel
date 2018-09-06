package com.simplynovel.zekai.simplynovel.ui.refresh;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Scroller;
import android.widget.Toast;

import static android.support.v4.widget.ViewDragHelper.INVALID_POINTER;

/**
 * Created by 15082 on 2018/6/23.
 */

public class RefreshLayout extends ViewGroup {
    /**
     * 是否自动更新过
     */
    private boolean isAutoRefresh;
    /**
     * 拖动速度
     */
    private static final float DRAG_RATE = .5f;

    /**
     * START_POSITION  原始头部的位置
     */
    private static final int START_POSITION = 0;
    private static final int DURATION = 1000;
    private static final int SHOW_COMPLETE_TIME = 1000;
    /**
     * 设配的目标View
     */
    private View target;
    /**
     * 下拉头部引用自定义RefreshHeader
     */
    private MyRefreshHeader refreshHeader;
    /**
     * 作为refreshHeader的父类，用于设置属性
     */
    private View refreshHead;
    /**
     * 是否测量过Header的布局，防止重复测量
     */
    private boolean hasMeasureHeader;
    /**
     * 屏幕触发ACTION_MOVE 的最小移动距离
     */
    private int scaledTouchSlop;
    private int headerHeight;
    /**
     * 触发刷新的拖拽距离 == headerHeight
     */
    private int totalDragDistance;
    /**
     * 当前目标View的 VIEW.GETTOP()
     */
    private int currentTargetOffsetTop;
    private int lastTargetOffsetTop;
    private boolean mIsBeginDragged;
    /**
     * 活动id 用于区分多指触屏
     */
    private int activePointerId;
    private MotionEvent lastEvent;
    private boolean isTouch;
    /**
     * 是否发送过取消事件
     */
    private boolean hasSendCancelEvent;
    private float initDownY;
    private float lastMotionY;
    private AutoScroll autoScroll;
    private State state = State.RESET;

    private OnRefreshListener refreshListener;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    /**
     *    自定义组件的构造方法，在此初始化一些必须的值
     * @param context
     * @param attrs
     */
    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        autoScroll = new AutoScroll();
        //scaledTouchSlop触发移动事件的最小距离为 24px
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        refreshHeader = new MyRefreshHeader(context);
        setRefreshHead(refreshHeader);
    }

    /**
     * 设置头部布局属性，并添加
     *
     * @param view 添加的布局
     */
    public void setRefreshHead(View view) {
        if (view != null && view != refreshHead) {
            removeView(refreshHead);


            LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(layoutParams);
            }
            //将RefreshHeader 的父类为View  将RefreshHeader(view)子类赋值给父类refreshHead
            refreshHead = view;
            addView(refreshHead);
        }
    }

    /**
     * 布局测量
     *
     * @param widthMeasureSpec  测量后所得长度
     * @param heightMeasureSpec 测量后所得高度
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (target == null) {
            ensureTarget();
        }
        if (target == null) {
            return;
        }
        /**
         *   measure(int widthMeasureSpec, int heightMeasureSpec)
         *   widthMeasureSpec ：Horizontal space requirements as imposed by the parent
         *   heightMeasureSpec ：Vertical space requirements as imposed by the parent
         */
        target.measure(
                //MeasureSpec 是View里面的内部类
                MeasureSpec.makeMeasureSpec(
                        getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                        MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(
                        getMeasuredHeight() - getPaddingTop() - getPaddingBottom(),
                        MeasureSpec.EXACTLY));
        measureChild(refreshHead, widthMeasureSpec, heightMeasureSpec);
        if (!hasMeasureHeader) {
            hasMeasureHeader = true;
            headerHeight = refreshHead.getMeasuredHeight();
            totalDragDistance = headerHeight;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 确保target不为空
     */
    private void ensureTarget() {
        if (target == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (!child.equals(refreshHead)) {
                    target = child;
                }
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (getChildCount() == 0) {
            return;
        }
        if (target == null) {
            ensureTarget();
        }
        if (target == null) {
            return;
        }
        View child = target;
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop() + currentTargetOffsetTop;
        int childWidth = width - getPaddingRight() - getPaddingLeft();
        int childHeight = height - getPaddingTop() - getPaddingBottom();
        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);


        int refreshWidth = refreshHead.getMeasuredWidth();
        refreshHead.layout((width / 2 - refreshWidth / 2),
                -headerHeight + currentTargetOffsetTop,
                (width / 2 + refreshWidth / 2),
                currentTargetOffsetTop);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isEnabled() || target == null) {
            return super.dispatchTouchEvent(ev);
        }
        int actionMasked = ev.getActionMasked(); // support Multi - touch
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                activePointerId = ev.getPointerId(0);
                isTouch = true;
                hasSendCancelEvent = false;
                mIsBeginDragged = false;
                isAutoRefresh = false;
                lastTargetOffsetTop = currentTargetOffsetTop;
                currentTargetOffsetTop = target.getTop();
                initDownY = lastMotionY = ev.getY();
                autoScroll.stop();
                super.dispatchTouchEvent(ev);
                return true;
            case MotionEvent.ACTION_MOVE:
                if (activePointerId == INVALID_POINTER) {
                    //Got ACTION_MOVE event but don't have active pointer id
                    return super.dispatchTouchEvent(ev);
                }
                lastEvent = ev;
                float x = ev.getX(MotionEventCompat.findPointerIndex(ev, activePointerId));
                float y = ev.getY(MotionEventCompat.findPointerIndex(ev, activePointerId));
                float yDiff = y - lastMotionY;
                float offsetY = yDiff * DRAG_RATE;
                lastMotionY = y;
                //Math.abs()返回 int 值的绝对值
                if (!mIsBeginDragged && Math.abs(y - initDownY) > scaledTouchSlop) {
                    mIsBeginDragged = true;
                }
                if (mIsBeginDragged) {
                    boolean moveDown = offsetY > 0;
                    boolean canMoveDown = canChildScrollUp();
                    boolean moveUp = !moveDown;
                    boolean canMoveUp = currentTargetOffsetTop > START_POSITION;
                    if ((moveDown && !canMoveDown) || (moveUp && canMoveUp)) {
                        moveSpinner(offsetY);
                        return true;
                    }

                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                finishSpinner();
                activePointerId = INVALID_POINTER;
                break;
            //代表用户又使用一个手指触摸到屏幕上，也就是说，在已经有一个触摸点的情况下，有新出现了一个触摸点。
            case MotionEvent.ACTION_POINTER_DOWN:
                int pointerIndex = MotionEventCompat.getActionIndex(ev);
                if (pointerIndex < 0) {
                    //Got action_pointer_down but have a invalid action index
                    super.dispatchTouchEvent(ev);
                }
                lastMotionY = ev.getY(pointerIndex);
                activePointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
                break;
            //代表用户的一个手指离开了屏幕
            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                lastMotionY = ev.getY(ev.findPointerIndex(activePointerId));
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * 第二指按下时
     *
     * @param ev
     */
    private void onSecondaryPointerUp(MotionEvent ev) {
        int pointerIndex = MotionEventCompat.getActionIndex(ev);
        int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == activePointerId) {
            int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            lastMotionY = ev.getY(newPointerIndex);
            activePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    /**
     *   改变界面布局
     * @param diff
     */
    private void moveSpinner(float diff) {
        //去就近整数值
        int offset = Math.round(diff);
        if (offset == 0) {
            return;
        }
        int targetY = Math.max(0, currentTargetOffsetTop + offset);
        float extraOS = target.getTop() - totalDragDistance;
        float slingshotDist = totalDragDistance;
        float tensionSlingshotPercent = Math.max(0, Math.min(extraOS, slingshotDist * 2) / slingshotDist);
        float tensionPercent = (float) (tensionSlingshotPercent - Math.pow(tensionSlingshotPercent / 2, 2));

        if (offset > 0) {
            // 下拉的时候才添加阻力
            offset = (int) (offset * (1f - tensionPercent));
            targetY = Math.max(0, currentTargetOffsetTop + offset);
        }
        if (!hasSendCancelEvent && isTouch && currentTargetOffsetTop > START_POSITION) {
            sendCancelEvent();
            hasSendCancelEvent = true;
        }
        offset = targetY - currentTargetOffsetTop;
        //原本没有刷新过的状态，Reset,刷新过还没有来得及reset 就被下拉 仍为Complete状态
        if ((state == State.RESET || state == State.COMPLETE) && currentTargetOffsetTop == START_POSITION && targetY > 0) {
            changeState(State.PULL);
        }
        if (currentTargetOffsetTop > START_POSITION && targetY <= START_POSITION) {
            if (state == State.PULL || state == State.COMPLETE) {
                changeState(State.RESET);
            }
        }
        if (state == State.PULL && currentTargetOffsetTop >= totalDragDistance && !isTouch) {
            autoScroll.scrollTo(totalDragDistance, 1000);
            changeState(State.LOADING);
            if (refreshListener != null) {
                refreshListener.onRefresh();
            }
        }
        if (state == State.LOADING && currentTargetOffsetTop < totalDragDistance && isTouch) {
            autoScroll.scrollTo(START_POSITION, 1000);
            changeState(State.RESET);
            offset = -1;
        }
        //改变界面布局
        setTargetOffsetTopAndBottom(offset);
        if (refreshHeader instanceof RefreshHeader) {
            ((RefreshHeader) refreshHeader).
                    onPositionChange(currentTargetOffsetTop, lastTargetOffsetTop, totalDragDistance, isTouch, state);
        }
    }

    private void setTargetOffsetTopAndBottom(int offset) {
        if (offset == 0) {
            return;
        }
        target.offsetTopAndBottom(offset);
        refreshHead.offsetTopAndBottom(offset);
        lastTargetOffsetTop = currentTargetOffsetTop;
        currentTargetOffsetTop = target.getTop();
        //更新Ui线程界面
        //postInvalidate() 更新非UI线程的界面
        invalidate();
    }

    private void sendCancelEvent() {
        if (lastEvent == null) {
            return;
        }
        //obtain 模拟一次事件
        MotionEvent ev = MotionEvent.obtain(lastEvent);
        ev.setAction(MotionEvent.ACTION_CANCEL);
        super.dispatchTouchEvent(ev);
    }

    private boolean canChildScrollUp() {
        if (Build.VERSION.SDK_INT < 14) {
            if (target instanceof AbsListView) {
                AbsListView absListView = (AbsListView) this.target;
                return absListView.getCount() > 0 && (absListView.getFirstVisiblePosition() > 0 ||
                        absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(target, -1) || target.getScaleY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(target, -1);
        }
    }

    public void finishSpinner() {
        if (state == State.LOADING) {
            autoScroll.scrollTo(totalDragDistance, DURATION);
        } else {
            autoScroll.scrollTo(START_POSITION, DURATION);
        }
    }

    /**
     * 设置一个监听器
     *
     * @param onRefreshListener 监听器
     */
    public void setRefreshListener(OnRefreshListener onRefreshListener) {
        this.refreshListener = onRefreshListener;
    }

    /**
     * 刷新并且让控件返回顶部
     */
    public void refreshComplete() {
        if (state == State.LOADING) {
            changeState(State.COMPLETE);
            if (currentTargetOffsetTop == START_POSITION) {
                changeState(State.RESET);
            } else {
                if (!isTouch) {
                    postDelayed(delayToScrollTopRunable, SHOW_COMPLETE_TIME);
                }
            }
        } else {
            return;
        }


    }

    private Runnable delayToScrollTopRunable = new Runnable() {
        @Override
        public void run() {
            autoScroll.scrollTo(START_POSITION, DURATION);
        }
    };

    /**
     * 改变refreshHeader的状态，执行相应的操作
     *
     * @param state 状态
     */
    private void changeState(State state) {
        this.state = state;
        Toast.makeText(getContext(), state.toString(), Toast.LENGTH_SHORT).show();
        RefreshHeader refreshHeader = this.refreshHeader instanceof RefreshHeader ? ((RefreshHeader) this.refreshHeader) : null;
        if (refreshHeader != null) {
            switch (state) {
                case RESET:
                    refreshHeader.reset();
                    break;
                case PULL:
                    refreshHeader.pull();
                    break;
                case LOADING:
                    refreshHeader.refreshing();
                    break;
                case COMPLETE:
                    refreshHeader.complete();
                    break;
            }
        }
    }

    /**
     * AutoScroll 符合一定条件后向上滑动的内部类
     */
    private class AutoScroll implements Runnable {
        private Scroller scroller;
        private int lastY;

        public AutoScroll() {
            scroller = new Scroller(getContext());
        }

        @Override
        public void run() {
            boolean finished = !scroller.computeScrollOffset() || scroller.isFinished();
            if (!finished) {
                int currY = scroller.getCurrY();
                int offset = currY - lastY;
                lastY = currY;
                moveSpinner(offset);
                post(this);
                //在此调用SCROLL FINISH
                onScrollFinished(false);
            } else {
                stop();
                onScrollFinished(true);
            }
        }

        public void scrollTo(int to, int duration) {
            int from = currentTargetOffsetTop;
            int distance = to - from;
            stop();
            if (distance == 0) {
                return;
            }
            scroller.startScroll(0, 0, 0, distance, duration);
            post(this);
        }

        private void stop() {
            removeCallbacks(this);
            if (!scroller.isFinished()) {
                scroller.forceFinished(true);
            }
            lastY = 0;
        }

        private void onScrollFinished(boolean isForceFinish) {
            if (isAutoRefresh && !isForceFinish) {
                isAutoRefresh = false;
                changeState(State.LOADING);
                if (refreshListener != null) {
                    refreshListener.onRefresh();
                }
                finishSpinner();
            }
        }
    }

    public void autoRefresh(long duration) {
        if (state != State.RESET) {
            return;
        }
        postDelayed(autoRefreshRunnable, duration);
    }

    private Runnable autoRefreshRunnable = new Runnable() {
        @Override
        public void run() {
            // 标记当前是自动刷新状态，finishScroll调用时需要判断
            // 在actionDown事件中重新标记为false
            isAutoRefresh = true;
            changeState(State.PULL);
            autoScroll.scrollTo(totalDragDistance, 1000);
        }
    };
}
