package com.ev.library.strategy.configs;

import android.content.Context;
import android.os.Environment;

import com.ev.library.utils.FileUtils;

import java.io.File;

/**
 * strategy for files in SD card
 * Created by EV on 2018/4/23.
 */
public class SdcardConfigsStrategy implements IConfigsStrategy {

    @Override
    public String[] getConfigs(Context pContext) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        File dir = FileUtils.getSdcardEmotionDir(pContext);
        if (!dir.exists() || !dir.isDirectory()) {
            return null;
        }
        return dir.list();
    }
}
