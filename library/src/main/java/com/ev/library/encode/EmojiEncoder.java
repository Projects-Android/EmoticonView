package com.ev.library.encode;

import java.io.Serializable;

/**
 * encoder for Emoji
 * Created by EV on 2018/4/23.
 */
public class EmojiEncoder implements IEncoder, Serializable {
    @Override
    public String encode(String pGroup, String pId) {
        int codePoint = Integer.parseInt(pId);
        return newString(codePoint);
    }

    public static final String newString(int codePoint) {
        return new String(Character.toChars(codePoint));
    }
}
