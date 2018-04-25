package com.ev.library.getter;

import android.content.Context;

import com.ev.library.bean.group.Group;
import com.ev.library.bean.group.RecentGroup;
import com.ev.library.strategy.files.AssetsFileStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * emotion getter for history
 * Created by EV on 2018/4/23.
 */
public class HistoryEmoticonGetter implements IEmoticonGetter {
    @Override
    public List<Group> getEmotionGroups(Context pContext) {
        ArrayList<Group> groupList = new ArrayList<>();

        final Group recentGroup = new RecentGroup(new AssetsFileStrategy());
        recentGroup.setId(RecentGroup.TAG);
        recentGroup.setOrder(0);
        groupList.add(recentGroup);
        return groupList;
    }
}
