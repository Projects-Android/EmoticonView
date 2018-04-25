package com.ev.library.bean.group;

import android.content.Context;
import android.view.View;

import com.ev.library.bean.emotion.Emotion;

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
     * @param pContext  the p context
     * @param pPosition
     * @param pWidth
     * @return group page view
     */
    View getEmoticonPage(Context pContext, int pPosition, int pWidth, View.OnClickListener pOnClickListener);

    /**
     * notify group changed
     */
    void notifyChange();
}
