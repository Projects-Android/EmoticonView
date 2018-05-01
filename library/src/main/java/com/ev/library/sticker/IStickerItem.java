package com.ev.library.sticker;

import java.util.ArrayList;

/**
 * interface for stick item
 * Created by EV on 2018/4/28.
 */

public interface IStickerItem {

    void addStickers(ArrayList<Sticker> stickers);

    // reset canvas
    void reset();
}
