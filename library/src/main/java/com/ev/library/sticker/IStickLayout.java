package com.ev.library.sticker;

import android.view.View;

import com.ev.library.bean.emotion.Emotion;

/**
 * interface for stick layout
 * Created by EV on 2018/4/28.
 */
public interface IStickLayout {

    /**
     * set max deviation between sticker and stick layout
     * 0 for default
     * @param maxDeviation
     */
    void setMaxDeviation(int maxDeviation);

    /**
     * whether sticker is totally inside the stick layout
     * @param stickerView
     * @return
     */
    boolean stickerInLayout(View stickerView);

    /**
     * add sticker to stick layout
     * @note should invoke {@link #stickerInLayout(View)} first
     * @param stickerView
     * @param emotion
     * @throws StickerOutOfRangeException
     */
    void addSticker(View stickerView, Emotion emotion) throws StickerOutOfRangeException;
}
