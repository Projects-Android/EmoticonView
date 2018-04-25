package com.ev.library.utils;

import android.content.Context;

import java.io.File;

/**
 * file utils
 * Created by EV on 2018/4/23.
 */
public class FileUtils {

    public static final String SDCARD_DIR = "emoticon";

    /**
     * get sdcard emotion direction
     * </p>
     * @param pContext the p context
     * @return the sdcard emotion
     */
    public static final File getSdcardEmotionDir(Context pContext) {
        return new File(pContext.getExternalCacheDir(), SDCARD_DIR);
    }

}
