package com.ev.library.utils;

import android.content.Context;

/**
 * common utils
 * Created by EV on 2018/4/24.
 */
public class CommonUtils {

    /**
     * dip to pixel
     * @param context
     * @param dp
     * @return
     */
    public static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
