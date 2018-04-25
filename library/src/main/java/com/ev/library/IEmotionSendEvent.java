package com.ev.library;

/**
 * event for emotion sending
 * Created by EV on 2018/4/23.
 */
public interface IEmotionSendEvent {

    /**
     * send emotion
     * </p>
     * @param emotionEncoded
     * @param width
     * @param height
     * @param size
     */
    void onEmotionSend(String emotionEncoded, int width, int height, long size);

}
