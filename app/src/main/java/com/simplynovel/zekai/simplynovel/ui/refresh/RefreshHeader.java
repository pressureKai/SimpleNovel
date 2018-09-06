package com.simplynovel.zekai.simplynovel.ui.refresh;

/**
 * Created by 15082 on 2018/6/23.
 */

public interface RefreshHeader {
    /**
     * 松手，头部隐藏后调用
     */
    void reset();

    /**
     * 下拉调用
     */
    void pull();

    /**
     * 刷新调用
     */
    void refreshing();

    /**
     * 头部滚动的时候持续调用
     *
     * @param currentPos target current height
     * @param lastPos    last time  target height
     * @param refreshPos refresh height
     * @param isTouch    手指是否按下
     * @param state      current state
     */
    void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, State state);

    /**
     * 刷新成功时调用
     */
    void complete();

}
