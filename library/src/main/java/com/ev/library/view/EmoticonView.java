package com.ev.library.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ev.library.EmoticonManager;
import com.ev.library.RecentEmotionsManager;
import com.ev.library.IEmotionSendEvent;
import com.ev.library.R;
import com.ev.library.bean.emotion.Emotion;
import com.ev.library.bean.emotion.PicEmotion;
import com.ev.library.bean.group.Group;
import com.ev.library.bean.group.RecentGroup;
import com.ev.library.eventbus.BusEvent;
import com.ev.library.utils.EmoticonTypeUtils;
import com.ev.library.utils.EmoticonImageLoader;
import com.ev.library.view.adapter.GroupPagerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * main view for emoticon
 * Created by EV on 2018/4/23.
 */
public class EmoticonView extends LinearLayout implements ViewPager.OnPageChangeListener {

    private static final int GROUP_WEIGHT_SUM = 5;
    private ViewPager mVpEmotion;
    private GroupPagerAdapter mPagerAdapter;
    private LinearLayout mLlGroup;
    private Context mContext;
    private IEmotionSendEvent mEmotionSendEvent;

    private int mScreenWidth;

    private List<Bitmap> mToRecycleBitmap = new ArrayList<Bitmap>();
    private ArrayList<Group> mEmotionGroups;
    private IInputView mInputView;

    /**
     * public constructor
     * </p>
     * @param context the context
     * @param attrs   the attrs
     */
    public EmoticonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setOrientation(VERTICAL);
        inflate(context, R.layout.layout_emoticon_view, this);

        mVpEmotion = findViewById(R.id.vp_emoticon_main);
        mLlGroup = findViewById(R.id.llGroup);

        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;

        mVpEmotion.setOnPageChangeListener(this);

    }

    /**
     * initial
     * </p>
     * @param pEmotionTypes type for emotion
     *                      @see EmoticonTypeUtils
     * @param pEmotionEvent
     *                      @see IEmotionSendEvent
     * @param pInputView
     *                      @see IInputView
     */
    public void init(final int pEmotionTypes, IEmotionSendEvent pEmotionEvent, IInputView pInputView) {
        init(pEmotionTypes, pEmotionEvent, pInputView, mScreenWidth);
    }

    /**
     * initial
     * </p>
     * @param pEmotionTypes type for emotion
     *                      @see EmoticonTypeUtils
     * @param pEmotionEvent
     *                      @see IEmotionSendEvent
     * @param pInputView
     *                      @see IInputView
     * @param pWidth width of view
     */
    public void init(final int pEmotionTypes, IEmotionSendEvent pEmotionEvent, IInputView pInputView, final int pWidth) {
        mInputView = pInputView;
        mEmotionSendEvent = pEmotionEvent;
        init(pEmotionTypes, pWidth);
    }

    private void init(final int pEmotionTypes, final int pWidth) {
        Observable.create(new ObservableOnSubscribe<ArrayList<Group>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<Group>> emitter) throws Exception {
                EmoticonManager.getInstance(getContext()).initData(mContext);
                final ArrayList<Group> emotionGroups = EmoticonManager.getInstance(getContext()).getGroupList(pEmotionTypes);
                if (emotionGroups != null) {
                    emitter.onNext(emotionGroups);
                } else {
                    emitter.onError(new Throwable(mContext.getString(R.string.error_get_emoticon_config)));
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Group>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ArrayList<Group> list) {
                mEmotionGroups = list;
                mPagerAdapter = new GroupPagerAdapter(mContext, list, pWidth, new OnEmotionClick());
                mVpEmotion.setAdapter(mPagerAdapter);
                EmoticonView.this.initGroupBtn(pWidth);
                EmoticonView.this.setSelectGroupBtn(0);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        EventBus.getDefault().unregister(this);

        try {
            RecentEmotionsManager.getInstance(mContext).saveRecents();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Bitmap bitmap : mToRecycleBitmap) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    private void setSelectGroupBtn(int position) {
        for (int i = 0; i < mEmotionGroups.size(); i ++) {
            mLlGroup.getChildAt(i).setSelected(false);
        }

        mLlGroup.getChildAt(position).setSelected(true);
    }

    /**
     * init group button
     * </p>
     * @param pWdith
     */
    private void initGroupBtn(int pWdith) {
        int padding = mContext.getResources().getDimensionPixelSize(R.dimen.padding_emoticon_group_button);
        final int size = mEmotionGroups.size();
        int groupWeightSum = size > GROUP_WEIGHT_SUM ? GROUP_WEIGHT_SUM : size;
        for (int index = 0; index < size; index ++) {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(pWdith / groupWeightSum, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(padding, padding, padding, padding);
            mLlGroup.addView(imageView, layoutParams);
            int pressed = android.R.attr.state_pressed;
            final Group emotionGroup = mEmotionGroups.get(index);
            final Bitmap normalBitmap = EmoticonImageLoader.getInstance().loadImageSync(emotionGroup.getNormalImg());
            BitmapDrawable normalDrawable = new BitmapDrawable(normalBitmap);
            mToRecycleBitmap.add(normalBitmap);
            final Bitmap pressedBitmap = EmoticonImageLoader.getInstance().loadImageSync(emotionGroup.getSelecteddImg());
            BitmapDrawable pressedDrawable = new BitmapDrawable(pressedBitmap);
            StateListDrawable stateListDrawable = new StateListDrawable();
            mToRecycleBitmap.add(pressedBitmap);

            normalDrawable.setBounds(0, 0, normalBitmap.getWidth(), normalBitmap.getHeight());
            pressedDrawable.setBounds(0, 0, pressedBitmap.getWidth(), pressedBitmap.getHeight());
            stateListDrawable.addState(new int[]{pressed}, pressedDrawable);
            stateListDrawable.addState(new int[]{android.R.attr.state_selected}, pressedDrawable);
            stateListDrawable.addState(new int[]{}, normalDrawable);
            imageView.setImageDrawable(stateListDrawable);
            imageView.setOnClickListener(mOnGroupClick);
            imageView.setBackgroundResource(R.drawable.drawable_bg_group_btn);
            imageView.setTag(index);
            imageView.setContentDescription(String.valueOf(emotionGroup.getOrder()));
        }
    }

    private OnClickListener mOnGroupClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mVpEmotion.setCurrentItem((int) v.getTag(), true);
            final int childCount = mLlGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View view = mLlGroup.getChildAt(i);
                view.setSelected(false);
            }
            v.setSelected(true);
        }
    };


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mEmotionGroups.get(position) instanceof RecentGroup) {
            ((RecentGroup) mEmotionGroups.get(position)).refresh(mContext);
        }
        setSelectGroupBtn(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class OnEmotionClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            final Emotion emotion = (Emotion) v.getTag();
            if ((emotion instanceof PicEmotion)) {
                if (mEmotionSendEvent != null) {
                    int width = 0;
                    int height = 0;
                    final Bitmap bitmap = EmoticonImageLoader.getInstance().loadImageSync(emotion.getFileName());
                    if (bitmap != null) {
                        width = bitmap.getWidth();
                        height = bitmap.getHeight();
                    }
                    mEmotionSendEvent.onEmotionSend(emotion.encode(), width, height, emotion.getFileSize(mContext));
                }
                RecentEmotionsManager.getInstance(mContext).push(emotion);
            } else {
                emotion.click(mContext, mInputView);
            }
        }
    }

    @Subscribe
    public void onBusEventReceived(BusEvent event) {
        if (null != event) {
            String eventContent = event.getEventContent();

            if (TextUtils.isEmpty(eventContent)) {
                return;
            }

            switch (eventContent) {
                case BusEvent.BUSEVENT_MSG_REFRESH_HISTORY:
                    if (null != mPagerAdapter) {
                        if (mEmotionGroups.get(0) instanceof RecentGroup) {
                            ((RecentGroup) mEmotionGroups.get(0)).refresh(mContext);
                        }
                        mPagerAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }
}
