package com.ev.emoticonview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.ev.emoticonview.R;
import com.ev.emoticonview.adapter.MessageRecyclerViewAdapter;
import com.ev.emoticonview.bean.Message;
import com.ev.library.IEmotionSendEvent;
import com.ev.library.sticker.StickerRecyclerView;
import com.ev.library.utils.EmoticonTypeUtils;
import com.ev.library.view.EmoticonEditText;
import com.ev.library.view.EmoticonView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private EmoticonEditText mInputView;
    private EmoticonView mEvView;
    private ImageButton mBtnSend;

    private StickerRecyclerView mRvMain;
    private MessageRecyclerViewAdapter mAdapter;
    private ArrayList<Message> mMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initData();
    }

    private void initViews() {
        mEvView =  findViewById(R.id.ev_main);
        mInputView = findViewById(R.id.eet_main);
        mBtnSend = findViewById(R.id.btn_main_send);
        mRvMain = findViewById(R.id.rv_main);

//        mEvView.setStickLayout(mRvMain);
        mInputView.post(new Runnable() {
            @Override
            public void run() {
                mEvView.init(EmoticonTypeUtils.ALL_TYPE, mIEmotionSendEvent, mInputView);
            }
        });

        mBtnSend.setOnClickListener(mOnClickListener);
    }

    private void initData() {
        mMessages = new ArrayList<>();
        mAdapter = new MessageRecyclerViewAdapter(this, mMessages);
        mRvMain.setLayoutManager(new LinearLayoutManager(this));
        mRvMain.setAdapter(mAdapter);
    }

    private IEmotionSendEvent mIEmotionSendEvent = new IEmotionSendEvent() {
        @Override
        public void onEmotionSend(String emotionEncoded, int width, int height, long size) {
            boolean isSend = mMessages.size() % 3 == 0;
            Message msg = new Message();
            msg.setmContent(emotionEncoded);
            msg.setmType(Message.TYPE_MESSAGE_CONTENT_IMG);
            msg.setmSourceType(isSend ? Message.TYPE_MESSAGE_SEND : Message.TYPE_MESSAGE_RECEIVE);
            mMessages.add(msg);
            mAdapter.notifyItemInserted(mMessages.size());
            mRvMain.scrollToPosition(mMessages.size() - 1);
        }
    };

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_main_send:
                    if (mInputView.getText().toString().length() == 0) {
                        return;
                    }
                    boolean isSend = mMessages.size() % 3 == 0;
                    Message msg = new Message();
                    msg.setmContent(mInputView.getText().toString());
                    msg.setmType(Message.TYPE_MESSAGE_CONTENT_TEXT);
                    msg.setmSourceType(isSend ? Message.TYPE_MESSAGE_SEND : Message.TYPE_MESSAGE_RECEIVE);
                    mMessages.add(msg);
                    mAdapter.notifyItemInserted(mMessages.size());
                    mRvMain.scrollToPosition(mMessages.size() - 1);

                    mInputView.setText("");
                    break;
                    default:
                        break;
            }
        }
    };
}
