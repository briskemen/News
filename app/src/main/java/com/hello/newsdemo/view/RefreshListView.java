package com.hello.newsdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hello.zhbj52.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 下拉刷新的ListView
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener,
        AdapterView.OnItemClickListener {
    private static final int STATUS_PULL_REFRESH    = 0;// 向下刷新
    private static final int STATUS_RELEASE_REFRESH = 1;// 松开刷新
    private static final int STATUS_REFRESHING      = 2;// 正在刷新

    private int mCurrentStatus = STATUS_PULL_REFRESH;//初始状态为向下刷新状态

    private View        mHeaderView;
    private TextView    tvTitle;
    private TextView    tvTime;
    private ProgressBar pbProgress;
    private ImageView   ivArr;

    private int startY = -1;// 滑动时y坐标
    private int mHeaderViewHeight;

    private RotateAnimation mAnimUp;
    private RotateAnimation mAnimDown;

    private OnRefreshListener mListener;
    private View              mFooterView;
    private int               mFooterViewHeight;

    public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initHeaderView();
        initFooterView();
    }


    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        initFooterView();
    }

    public RefreshListView(Context context) {
        super(context);
        initHeaderView();
        initFooterView();
    }

    /**
     * 初始化头布局
     */
    private void initHeaderView() {
        mHeaderView = View.inflate(getContext(), R.layout.refresh_header, null);
        this.addHeaderView(mHeaderView);

        tvTitle = (TextView) mHeaderView.findViewById(R.id.tv_title);
        tvTime = (TextView) mHeaderView.findViewById(R.id.tv_time);
        ivArr = (ImageView) mHeaderView.findViewById(R.id.iv_arr);
        pbProgress = (ProgressBar) mHeaderView.findViewById(R.id.pb_progress);

        mHeaderView.measure(0, 0);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);//隐藏头布局

        initArrowAnim();// 初始化动画

        tvTime.setText("最后刷新时间：" + getCurrentTime());
    }

    /**
     * 初始化脚布局
     */
    private void initFooterView() {
        mFooterView = View.inflate(getContext(), R.layout.refresh_listview_footer, null);
        this.addFooterView(mFooterView);

        mFooterView.measure(0, 0);
        mFooterViewHeight = mFooterView.getMeasuredHeight();
        mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);

        this.setOnScrollListener(this);
    }

    /**
     * 获取系统当前时间
     *
     * @return
     */
    private String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * 3.触摸事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {// 确保startY有效
                    startY = (int) ev.getRawY();
                }
                int endY = (int) ev.getRawY();
                int dy = endY - startY;

                if (dy > 0 && getFirstVisiblePosition() == 0) {//只有下拉并且是第一个item才允许下拉
                    int padding = dy - mHeaderViewHeight;// 计算padding
                    mHeaderView.setPadding(0, padding, 0, 0);

                    if (padding > 0 && mCurrentStatus != STATUS_RELEASE_REFRESH) {
                        // 向下刷新状态变为松开刷新状态
                        mCurrentStatus = STATUS_RELEASE_REFRESH;
                        refreshState();
                    } else if (padding < 0 && mCurrentStatus != STATUS_PULL_REFRESH) {
                        // 改为下拉刷新状态
                        mCurrentStatus = STATUS_PULL_REFRESH;
                        refreshState();
                    }
                    return true;//这里有点费解
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;// 重置startY位置
                if (mCurrentStatus == STATUS_RELEASE_REFRESH) {
                    mCurrentStatus = STATUS_REFRESHING;// 正在刷新
                    mHeaderView.setPadding(0, 0, 0, 0);// 显示
                    refreshState();
                } else if (mCurrentStatus == STATUS_PULL_REFRESH) {
                    mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);// 隐藏
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 刷新下拉控件布局
     */
    private void refreshState() {
        switch (mCurrentStatus) {
            case STATUS_PULL_REFRESH:
                ivArr.setVisibility(VISIBLE);
                ivArr.startAnimation(mAnimDown);
                pbProgress.setVisibility(INVISIBLE);
                tvTitle.setText("下拉刷新");
                break;
            case STATUS_RELEASE_REFRESH:
                ivArr.setVisibility(VISIBLE);
                pbProgress.setVisibility(INVISIBLE);
                tvTitle.setText("松开刷新");
                ivArr.startAnimation(mAnimUp);
                break;
            case STATUS_REFRESHING:
                ivArr.setVisibility(INVISIBLE);
                ivArr.clearAnimation();// 必须先清除动画,才能隐藏
                pbProgress.setVisibility(VISIBLE);
                tvTitle.setText("正在刷新...");
                tvTime.setText("最后刷新时间：" + getCurrentTime());

                if (mListener != null) {
                    mListener.onRefresh();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 初始化箭头动画
     */
    private void initArrowAnim() {
        // 箭头向上的动画
        mAnimUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimUp.setDuration(200);
        mAnimUp.setFillAfter(true);

        // 箭头向下的动画
        mAnimDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimDown.setDuration(200);
        mAnimDown.setFillAfter(true);
    }


    private boolean isLoadingMore;

    /**
     * 收起下拉刷新的控件
     *
     * @param success
     */
    public void onRefreshComplete(boolean success) {
        if (isLoadingMore) {
            // 正在加载更多
            mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);// 隐藏脚布局
            isLoadingMore = false;
        } else {
            mCurrentStatus = STATUS_PULL_REFRESH;
            tvTitle.setText("下拉刷新");
            ivArr.setVisibility(VISIBLE);
            pbProgress.setVisibility(INVISIBLE);

            mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);

            if (success) {
                tvTime.setText("最后刷新时间：" + getCurrentTime());
            }
        }
    }

    public void SetOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    public interface OnRefreshListener {
        void onRefresh();

        void onLoadMore();// 加载更多
    }

    OnItemClickListener mItemClickListener;

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
        mItemClickListener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mItemClickListener != null) {
            //处理position位置问题
            mItemClickListener.onItemClick(parent, view, position - getHeaderViewsCount(), id);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) {
            if (getLastVisiblePosition() == getCount() - 1 && !isLoadingMore) {// 加载到最后
                mFooterView.setPadding(0, 0, 0, 0);// 显示
                setSelection(getCount() - 1);//改变listView位置
                isLoadingMore = true;
                if (mListener != null) {
                    mListener.onLoadMore();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int
            totalItemCount) {
    }
}
