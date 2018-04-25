package com.ev.library.bean.group;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.ev.library.R;
import com.ev.library.bean.emotion.Emotion;
import com.ev.library.strategy.files.IFileStrategy;
import com.ev.library.view.adapter.EmotionRecyclerViewAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * abstract class for Group
 * Created by EV on 2018/4/23.
 */
public abstract class Group implements IGroup {

    private final IFileStrategy mFilestrategy;

    protected Map<String, Emotion> mEmotions = new HashMap<String, Emotion>();

    protected Emotion[] mEmotionArrays;

    private String mId;

    private int mType;

    private String mExt;

    private String mThumbExt;

    private String mNormalImg;

    private String mSelecteddImg;

    private int mOrder;

    private String mDirName;

    private EmotionRecyclerViewAdapter mAdapter;

    public Group(IFileStrategy pFilestrategy) {
        mFilestrategy = pFilestrategy;
    }

    public String getThumbExt() {
        return mThumbExt;
    }

    public void setThumbExt(String pThumbExt) {
        mThumbExt = pThumbExt;
    }

    public void setDirName(String pDirName) {
        mDirName = pDirName;
    }

    public Emotion[] getEmotionArrays() {
        return mEmotionArrays;
    }

    public void setEmotionArrays(Emotion[] pEmotionArrays) {
        mEmotionArrays = pEmotionArrays;
    }

    public String getSelecteddImg() {
        return mFilestrategy.getImagePath(mDirName, mSelecteddImg, mThumbExt);
    }

    public void setSelecteddImg(String pSelecteddImg) {
        mSelecteddImg = pSelecteddImg;
    }

    public String getExt() {
        return mExt;
    }

    ;

    public void setExt(String pExt) {
        mExt = pExt;
    }

    public Map<String, Emotion> getEmotions() {
        return mEmotions;
    }

    public void addEmotion(String id, Emotion pEmotions) {
        mEmotions.put(id, pEmotions);
    }

    public String getId() {
        return mId;
    }

    public void setId(String pId) {
        mId = pId;
    }

    public int getType() {
        return mType;
    }

    public void setType(int pType) {
        mType = pType;
    }

    public String getNormalImg() {
        return mFilestrategy.getImagePath(mDirName, mNormalImg, mThumbExt);
    }

    public void setNormalImg(String pNormalImg) {
        mNormalImg = pNormalImg;
    }

    public int getOrder() {
        return mOrder;
    }

    public void setOrder(int pOrder) {
        mOrder = pOrder;
    }

    @Override
    public int getEmotionTotal() {
        return mEmotionArrays.length;
    }

    @Override
    public View getEmoticonPage(Context pContext, int pPosition, int pWidth, View.OnClickListener onClickListener) {
        final LayoutInflater inflater = LayoutInflater.from(pContext);
        FrameLayout view = (FrameLayout) inflater.inflate(R.layout.layout_group_page, null);
        RecyclerView recyclerView = view.findViewById(R.id.rv_group_page);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(pContext, getColumn());
        recyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new EmotionRecyclerViewAdapter(pContext, this, onClickListener);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public Emotion getEmotion(int position) {
        if (position >= 0 && position < getEmotionTotal()) {
            return  mEmotionArrays[position];
        }

        return null;
    }

    @Override
    public void notifyChange() {
        if (null != mAdapter) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
