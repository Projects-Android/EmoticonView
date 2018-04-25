package com.ev.library;


import android.content.Context;
import android.content.SharedPreferences;

import com.ev.library.bean.emotion.Emotion;
import com.ev.library.eventbus.BusEvent;
import com.ev.library.utils.ObjectSerializer;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * list for recent emotions
 * Created by EV on 2018/4/23.
 */
public class RecentEmotionsManager extends ArrayList<Emotion> {
    private static final String DELIMITER = ",";
    private static final String PREFERENCE_NAME = "emojicon";
    private static final String PREF_RECENTS = "recent_emojis";
    private static final String PREF_PAGE = "recent_page";


    private static final Object LOCK = new Object();
    private static RecentEmotionsManager sInstance;
    private static int maximumSize = 40;


    private Context mContext;


    private RecentEmotionsManager(Context context) {
        mContext = context.getApplicationContext();
        try {
            loadRecents();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static RecentEmotionsManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new RecentEmotionsManager(context);
                }
            }
        }
        return sInstance;
    }


    public int getRecentPage() {
        return getPreferences().getInt(PREF_PAGE, 0);
    }


    public void setRecentPage(int page) {
        getPreferences().edit().putInt(PREF_PAGE, page).commit();
    }


    public void push(Emotion object) {
        boolean shouldNotify = size() == 0;

        if (contains(object)) {
            super.remove(object);
        }
        add(0, object);

        if (shouldNotify) {
            // notify to hide empty history tip
            EventBus.getDefault().post(new BusEvent(BusEvent.BUSEVENT_MSG_REFRESH_HISTORY));
        }
    }


    @Override
    public boolean add(Emotion object) {
        boolean ret = super.add(object);


        while (this.size() > RecentEmotionsManager.maximumSize) {
            super.remove(0);
        }

        return ret;
    }


    @Override
    public void add(int index, Emotion object) {
        super.add(index, object);

        if (index == 0) {
            while (this.size() > RecentEmotionsManager.maximumSize) {
                super.remove(RecentEmotionsManager.maximumSize);
            }
        } else {
            while (this.size() > RecentEmotionsManager.maximumSize) {
                super.remove(0);
            }
        }
    }


    @Override
    public boolean remove(Object object) {
        boolean ret = super.remove(object);
        return ret;
    }


    private SharedPreferences getPreferences() {
        return mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }


    private void loadRecents() throws IOException, ClassNotFoundException {
        SharedPreferences prefs = getPreferences();
        String str = prefs.getString(PREF_RECENTS, "");
        StringTokenizer tokenizer = new StringTokenizer(str, RecentEmotionsManager.DELIMITER);
        while (tokenizer.hasMoreTokens()) {
            final Emotion deserialize = (Emotion) ObjectSerializer.deserialize(tokenizer.nextToken());
            add(deserialize);
        }
    }


    public void saveRecents() throws IOException {
        StringBuilder str = new StringBuilder();
        int c = size();
        for (int i = 0; i < c; i++) {
            Emotion e = get(i);
            str.append(e.serialize());
            if (i < (c - 1)) {
                str.append(RecentEmotionsManager.DELIMITER);
            }
        }
        SharedPreferences prefs = getPreferences();
        prefs.edit().putString(PREF_RECENTS, str.toString()).commit();
    }


    public static void setMaximumSize(int maximumSize) {
        RecentEmotionsManager.maximumSize = maximumSize;
    }
} 