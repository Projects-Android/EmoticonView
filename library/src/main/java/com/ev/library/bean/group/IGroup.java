package com.ev.library.bean.group;

import android.content.Context;
import android.view.View;

import com.ev.library.bean.emotion.Emotion;
import com.ev.library.utils.RecyclerViewTouchUtil;

/**
 * Created by EV on 2018/4/23.
 */
public interface IGroup {

    /**
     * column for grid
     * @return the column
     */
    int getColumn();

    /**
     * get emotion
     * @param position
     * @return
     */
    Emotion getEmotion(int position);

    /**
     * get total count of emotions
     * @return
     */
    int getEmotionTotal();

    /**
     * get page view for emoticon
     * </p>
     * @param pContext
     * @param onItemClickListener
     * @param onItemLongClickListener
     * @param onItemLongPressUpListener
     * @return
     */
    View getEmoticonPage(Context pContext,
                         RecyclerViewTouchUtil.OnItemClickListener onItemClickListener,
                         RecyclerViewTouchUtil.OnItemLongClickListener onItemLongClickListener,
                         RecyclerViewTouchUtil.OnItemLongPressUpListener onItemLongPressUpListener,
                         RecyclerViewTouchUtil.OnStickerEmotionMoveListener onStickerEmotionMoveListener,
                         boolean onStickDragMode);

    /**
     * notify group changed
     */
    void notifyChange();
}
