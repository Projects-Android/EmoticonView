package com.ev.library.sticker;

import android.view.View;

/**
 * Created by EV on 2018/4/28.
 */

public class StickerOutOfRangeException extends Exception {

    public StickerOutOfRangeException(View stickLayout) {
        super("Sticker can only be totally inside the parent stick layout \"" + stickLayout.getClass().getSimpleName() + "\"");
    }
}
