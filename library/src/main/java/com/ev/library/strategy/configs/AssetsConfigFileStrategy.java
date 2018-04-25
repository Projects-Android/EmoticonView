package com.ev.library.strategy.configs;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * strategy for configuration files in assets
 * Created by EV on 2018/4/23.
 */
public class AssetsConfigFileStrategy implements IConfigFileStrategy {

    private Context mContext;
    private String mGroupName;

    public AssetsConfigFileStrategy(Context context, String groupName) {
        this.mContext = context;
        this.mGroupName = groupName;
    }

    @Override
    public InputStream getConfig() throws IOException {
        String configAssetsFilePath = "emoticon/" + mGroupName + "/config.xml";
        return mContext.getAssets().open(configAssetsFilePath);
    }
}
