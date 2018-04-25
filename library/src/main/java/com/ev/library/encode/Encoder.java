package com.ev.library.encode;

import java.io.Serializable;

/**
 * encode emotion to [groupId : emotionId]
 * eg: [emoticon_cat:00]
 * Created by EV on 2018/4/23.
 */
public class Encoder implements IEncoder, Serializable {

    @Override
    public String encode(String pGroup, String pId) {
        return String.format("[%s:%s]", pGroup, pId);
    }

}
