package com.ev.library.strategy.configs;

import android.content.Context;

import com.ev.library.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * strategy for configuration files in SD card
 * Created by EV on 2018/4/23.
 */
public class SdcardConfigsFilesStrategy implements IConfigFileStrategy {

    private String mGroupName;
    private Context mContext;

    public SdcardConfigsFilesStrategy(Context pContext, String pGroupName) {
        mGroupName = pGroupName;
        mContext = pContext;
    }

    @Override
    public InputStream getConfig() throws IOException {
        final File file = new File(FileUtils.getSdcardEmotionDir(mContext).getAbsolutePath() + "/" + mGroupName + "/config.xml");
        InputStream inputStream = new FileInputStream(file);
        return inputStream;
    }
}
