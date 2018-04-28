package com.ev.library.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ev.library.bean.group.Group;
import com.ev.library.bean.group.RecentGroup;
import com.ev.library.utils.RecyclerViewTouchUtil;

import java.util.ArrayList;

/**
 * Created by EV on 2018/4/24.
 */

public class GroupPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<Group> mGroupList;
    private RecyclerViewTouchUtil.OnItemClickListener mOnItemClickListener;
    private RecyclerViewTouchUtil.OnItemLongClickListener mOnItemLongClickListener;
    private RecyclerViewTouchUtil.OnItemLongPressUpListener mOnItemLongPressUpListener;
    private RecyclerViewTouchUtil.OnStickerEmotionMoveListener mOnStickerEmotionMoveListener;

    public GroupPagerAdapter(Context context,
                             ArrayList<Group> groupList,
                             RecyclerViewTouchUtil.OnItemClickListener onItemClickListener,
                             RecyclerViewTouchUtil.OnItemLongClickListener onItemLongClickListener,
                             RecyclerViewTouchUtil.OnItemLongPressUpListener onItemLongPressUpListener,
                             RecyclerViewTouchUtil.OnStickerEmotionMoveListener onStickerEmotionMoveListener) {
        mContext = context;
        mGroupList = groupList;
        mOnItemClickListener = onItemClickListener;
        mOnItemLongClickListener = onItemLongClickListener;
        mOnItemLongPressUpListener = onItemLongPressUpListener;
        mOnStickerEmotionMoveListener = onStickerEmotionMoveListener;

        if (null != mGroupList && !mGroupList.isEmpty() && mGroupList.get(0) instanceof RecentGroup) {
            ((RecentGroup) mGroupList.get(0)).refresh(mContext);
        }
    }

    @Override
    public int getCount() {
        return null == mGroupList ? 0 : mGroupList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        if (object instanceof View) {
            Object oTag = ((View) object).getTag();
            if (oTag instanceof Integer) {
                int tag = (int) oTag;
                if (tag == RecentGroup.TAG_REFRESH_PAGE) {
                    return POSITION_NONE;
                }
            }
        }
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mGroupList.get(position).getEmoticonPage(
                mContext,
                mOnItemClickListener,
                mOnItemLongClickListener,
                mOnItemLongPressUpListener,
                mOnStickerEmotionMoveListener);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
