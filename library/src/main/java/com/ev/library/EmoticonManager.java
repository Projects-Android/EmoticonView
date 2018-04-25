package com.ev.library;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;

import com.ev.library.bean.group.Group;
import com.ev.library.bean.group.RecentGroup;
import com.ev.library.decode.DefaultDecoder;
import com.ev.library.decode.DefaultPictureDecoder;
import com.ev.library.decode.EmojiDecoder;
import com.ev.library.decode.IDecoder;
import com.ev.library.decode.IPicDecoder;
import com.ev.library.exception.DecodeException;
import com.ev.library.getter.AssetsEmoticonGetter;
import com.ev.library.getter.HistoryEmoticonGetter;
import com.ev.library.getter.IEmoticonGetter;
import com.ev.library.getter.SdcardEmoticonGetter;
import com.ev.library.strategy.AssetsStrategyFactory;
import com.ev.library.strategy.IAssetsStrategyFactory;
import com.ev.library.utils.EmoticonImageLoader;
import com.ev.library.utils.EmoticonTypeUtils;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by EV on 2018/4/23.
 */
public class EmoticonManager {

    private Context mContext;
    private static EmoticonManager mInstance;
    private LinkedHashMap<String, Group> mGroups = new LinkedHashMap<>();
    private ArrayList<Group> mGroupList = new ArrayList<>();
    private EmoticonConfig mEmoticonConfig;
    private IAssetsStrategyFactory mAssetsStrategyFactory;

    private EmoticonManager(Context context) {
        mContext = context;
    }

    /**
     * get instance.
     * </p>
     * @return
     */
    public static EmoticonManager getInstance(Context context) {
        if (null == mInstance) {
            mInstance = new EmoticonManager(context);
        }
        return mInstance;
    }

    public IAssetsStrategyFactory getAssetsStrategyFactory() {
        return mAssetsStrategyFactory;
    }

    public LinkedHashMap<String, Group> getGroups() {
        return mGroups;
    }

    private void checkConfig() {
        if (mEmoticonConfig == null) {
            mEmoticonConfig = new EmoticonConfig.Builder()
                    .addDecoders(new DefaultDecoder(mContext))
                    .addDecoders(new EmojiDecoder(mContext))
                    .addGetters(new HistoryEmoticonGetter())
                    .addGetters(new AssetsEmoticonGetter())
                    .addGetters(new SdcardEmoticonGetter())
                    .addPicDecoders(new DefaultPictureDecoder(mContext))
                    .setAssetsStrategy(new AssetsStrategyFactory())
                    .build();
        }
        mAssetsStrategyFactory = mEmoticonConfig.getAssetsStrategyFactory();
    }

    /**
     * init emoticon group data
     * </p>
     * @param pContext
     * @return
     */
    public void initData(Context pContext) {
        checkConfig();
        if (!EmoticonImageLoader.getInstance().isInited()) {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration
                    .Builder(pContext)
                    .denyCacheImageMultipleSizesInMemory()
                    .memoryCache(new LruMemoryCache(10 * 1024 * 1024))
                    .tasksProcessingOrder(QueueProcessingType.LIFO).build();
            EmoticonImageLoader.getInstance().init(config);
        }
        if (mGroups.isEmpty()) {
            refreshData(pContext);
        }
    }

    /**
     * refresh emoticon group data
     * </p>
     * @param pContext
     */
    public void refreshData(Context pContext) {
        checkConfig();
        ArrayList<Group> groups = new ArrayList<Group>();
        ArrayList<IEmoticonGetter> getters = mEmoticonConfig.getGetters();
        for (IEmoticonGetter getter : getters) {
            final List<Group> emotionGroups = getter.getEmotionGroups(pContext);
            groups.addAll(emotionGroups);
        }
        Collections.sort(groups, new Comparator<Group>() {
            @Override
            public int compare(Group lhs, Group rhs) {
                return lhs.getOrder() - rhs.getOrder();
            }
        });
        for (Group group : groups) {
            mGroups.put(group.getId(), group);
            mGroupList.add(group);
        }
    }

    /**
     * decode
     * </p>
     * @param text
     * @param pTextSize
     * @param pDrawableSize
     * @return
     */
    public CharSequence decode(CharSequence text, int pTextSize, int pDrawableSize) {
        checkConfig();
        if (mGroups == null) {
            return null;
        }
        Spannable spannable;
        if (!(text instanceof Spannable)) {
            spannable = new SpannableStringBuilder(text);
        } else {
            spannable = (Spannable) text;
        }
        final ArrayList<IDecoder> decoders = mEmoticonConfig.getDecoders();
        for (IDecoder decoder : decoders) {
            decoder.decode(spannable, pTextSize, pDrawableSize);
        }
        return SpannableString.valueOf(spannable);
    }

    /**
     * decode
     * </p>
     * @param pContext
     * @param text
     * @return
     */
    public String decodeToText(Context pContext, String text) {
        checkConfig();
        if (mGroups == null) {
            return null;
        }
        String result = text;
        final ArrayList<IDecoder> decoders = mEmoticonConfig.getDecoders();
        for (IDecoder decoder : decoders) {
            result = decoder.decodeToText(pContext, result);
        }
        return result;
    }

    /**
     * decode pic emotion to url
     * </p>
     * @param text
     * @return
     */
    public String decodePic(String text) {
        checkConfig();
        final ArrayList<IPicDecoder> decoders = mEmoticonConfig.getPicDecoders();
        for (IPicDecoder decoder : decoders) {
            try {
                return decoder.decode(text);
            } catch (DecodeException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * get groups
     * @param pEmotionTypes
     * @return
     */
    public ArrayList<Group> getGroupList(int pEmotionTypes) {
        if (EmoticonTypeUtils.allMatch(pEmotionTypes)) {
            return mGroupList;
        }
        ArrayList<Group> groupList = new ArrayList<>();
        groupList.add(mGroups.get(RecentGroup.TAG));
        for (Group group : mGroupList) {
            if (EmoticonTypeUtils.typeMatch(group, pEmotionTypes)) {
                groupList.add(group);
            }
        }
        return mGroupList;
    }

    public EmoticonConfig getConfigs() {
        return mEmoticonConfig;
    }
}
