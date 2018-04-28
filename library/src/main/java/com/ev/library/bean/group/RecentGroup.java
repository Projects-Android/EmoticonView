package com.ev.library.bean.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.ev.library.RecentEmotionsManager;
import com.ev.library.R;
import com.ev.library.bean.emotion.Emotion;
import com.ev.library.strategy.files.IFileStrategy;
import com.ev.library.utils.RecyclerViewTouchUtil;

/**
 * group for recent use
 * Created by EV on 2018/4/23.
 */
public class RecentGroup extends PicGroup {

    public static final String TAG = "recent";

    public static final int TAG_REFRESH_PAGE = 0x99;

    public RecentGroup(IFileStrategy pFilestrategy) {
        super(pFilestrategy);
    }

    @Override
    public String getNormalImg() {
        return "drawable://" + R.drawable.ic_emoticon_history_normal;
    }

    @Override
    public String getSelecteddImg() {
        return "drawable://" + R.drawable.ic_emoticon_history_pressed;
    }

    @Override
    public View getEmoticonPage(Context pContext,
                                RecyclerViewTouchUtil.OnItemClickListener onItemClickListener,
                                RecyclerViewTouchUtil.OnItemLongClickListener onItemLongClickListener,
                                RecyclerViewTouchUtil.OnItemLongPressUpListener onItemLongPressUpListener,
                                RecyclerViewTouchUtil.OnStickerEmotionMoveListener onStickerEmotionMoveListener) {
        if (getEmotionTotal() > 0) {
            return super.getEmoticonPage(
                    pContext,
                    onItemClickListener,
                    onItemLongClickListener,
                    onItemLongPressUpListener,
                    onStickerEmotionMoveListener);
        } else {
            final LayoutInflater inflater = LayoutInflater.from(pContext);
            FrameLayout view = (FrameLayout) inflater.inflate(R.layout.layout_group_page, null);
            view.findViewById(R.id.rv_group_page).setVisibility(View.GONE);
            view.findViewById(R.id.tv_group_page_nohistory).setVisibility(View.VISIBLE);
            // set this tag to notify PagerAdapter to refresh item
            view.setTag(TAG_REFRESH_PAGE);
            return view;
        }
    }

    /**
     * refresh data
     * @param pContext the context
     */
    public void refresh(Context pContext) {
        final RecentEmotionsManager recents = RecentEmotionsManager.getInstance(pContext);
        for (Emotion recent : recents) {
            addEmotion(recent.getGroupID() + ":" + recent.getId(), recent);
        }
        setEmotionArrays(recents.toArray(new Emotion[recents.size()]));

        notifyChange();
    }
}
