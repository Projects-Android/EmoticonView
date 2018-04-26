package com.ev.library.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by EV on 2018/4/26.
 */
public class RecyclerViewTouchUtil {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private GestureDetector mGestureDetector;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private OnItemLongPressUpListener mOnItemLongPressUpListener;

    private int mLastLongPressPosition;
    private boolean mOnLongPressMode;

    public RecyclerViewTouchUtil(Context context, RecyclerView recyclerView) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;
        mOnLongPressMode = false;
        mLastLongPressPosition = -1;

        mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (null != mOnItemClickListener && null != mRecyclerView) {
                    View itemView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (null != itemView) {
                        int position = mRecyclerView.getChildLayoutPosition(itemView);
                        mOnItemClickListener.onItemClick(position, itemView);
                        return true;
                    }
                }
                return super.onSingleTapUp(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);

                if (null != mOnItemLongClickListener && null != mRecyclerView) {
                    mOnLongPressMode = true;
                    View itemView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (null != itemView) {
                        mLastLongPressPosition = mRecyclerView.getChildLayoutPosition(itemView);
                        mOnItemLongClickListener.onItemLongClick(mLastLongPressPosition, itemView);
                    }
                }
            }
        });

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (null != mGestureDetector) {
                    mGestureDetector.onTouchEvent(event);
                }

                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mOnLongPressMode = false;
                        if (null != mOnItemLongPressUpListener) {
                            mOnItemLongPressUpListener.onItemLongPressUp();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (null != mOnItemClickListener && null != mRecyclerView && mOnLongPressMode) {
                            mRecyclerView.requestDisallowInterceptTouchEvent(true);
                            View itemView = mRecyclerView.findChildViewUnder(event.getX(), event.getY());
                            if (null != itemView) {
                                int position = mRecyclerView.getChildLayoutPosition(itemView);
                                if (position != mLastLongPressPosition) {
                                    mLastLongPressPosition = position;
                                    mOnItemLongClickListener.onItemLongClick(mLastLongPressPosition, itemView);
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
                return mOnLongPressMode;
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemLongPressUpListener(OnItemLongPressUpListener onItemLongPressUpListener) {
        this.mOnItemLongPressUpListener = onItemLongPressUpListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View itemView);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position, View itemView);
    }

    public interface OnItemLongPressUpListener {
        void onItemLongPressUp();
    }
}
