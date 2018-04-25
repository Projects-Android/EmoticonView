package com.ev.library.getter;

import android.content.Context;

import com.ev.library.bean.group.Group;

import java.util.List;

/**
 * Created by EV on 2018/4/23.
 */
public interface IEmoticonGetter {

    /**
     * get emotion groups
     * </p>
     * @param pContext
     * @return
     */
    List<Group> getEmotionGroups(Context pContext);

}
