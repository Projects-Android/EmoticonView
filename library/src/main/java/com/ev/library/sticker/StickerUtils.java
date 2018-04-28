package com.ev.library.sticker;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by EV on 2018/4/28.
 */

public class StickerUtils {

    public static Rect getGlobalVisibleRect(View stickerView) {
        Rect stickerRect = new Rect();
        stickerView.getGlobalVisibleRect(stickerRect);
        int[] location = new int[2];
        stickerView.getLocationOnScreen(location);
        stickerRect.left = location[0];
        stickerRect.right = stickerRect.left + stickerView.getMeasuredWidth();
        stickerRect.top = location[1];
        stickerRect.bottom = stickerRect.top + stickerView.getMeasuredHeight();
        return stickerRect;
    }

    /**
     * get center coordinate of view
     * @param stickerView
     * @return an array, x coordinate first
     */
    public static int[] getViewCenterCoordinate(@NonNull View stickerView) {
        int[] location = new int[2];
        stickerView.getLocationOnScreen(location);
        int[] coordinate = new int[2];
        coordinate[0] = location[0] + stickerView.getMeasuredWidth() / 2;
        coordinate[1] = location[1] + stickerView.getMeasuredHeight() / 2;
        return coordinate;
    }
}
