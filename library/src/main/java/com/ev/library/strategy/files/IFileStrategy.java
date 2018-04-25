package com.ev.library.strategy.files;

import android.content.Context;

/**
 * 文件策略
 * Created by EV on 2018/4/23.
 */
public interface IFileStrategy {

    /**
     * 获取文件路径
     *
     * @param pGroupName the group name
     * @param pFileName  the file name
     * @param pExt       the ext
     * @return the image path
     */
    String getImagePath(String pGroupName, String pFileName, String pExt);

    /**
     * 获取文件大小
     *
     * @return the file size
     */
    long getFileSize(Context pContext, String pGroupName, String pFileName, String pExt);

}
