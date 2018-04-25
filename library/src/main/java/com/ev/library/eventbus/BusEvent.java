package com.ev.library.eventbus;

/**
 * Created by EV on 2018/4/25.
 */

public class BusEvent {

    public static final String BUSEVENT_MSG_REFRESH_HISTORY = "busevent_msg_refresh_history";

    private String mEventContent;

    private Object mData;

    public BusEvent(String event) {
        setEventContent(event);
    }

    public String getEventContent() {
        return mEventContent;
    }

    public void setEventContent(String eventContent) {
        this.mEventContent = eventContent;
    }

    public Object getData() {
        return mData;
    }

    public void setData(Object data) {
        this.mData = data;
    }
}
