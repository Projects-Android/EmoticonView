# EmoticonView
**A view to show emoticons. Support decoding and encoding emotions. The same as QQ.**
It supports users to add emoticons manually. Emoticons can locate at assets and SDCard. 

![image](/screenshot/Screenshot_emoticonview_small.png)


![image](/screenshot/Screenshot_emoticonview_small.gif)

# How to use++
**1、Define in xml**

###### View for input:

```xml
        <com.ev.library.view.EmoticonEditText
            android:id="@+id/eet_main"
            android:layout_width="match_parent"
            android:layout_height="32dp"
            android:layout_marginTop="8dp"
            android:layout_marginLeft="8dp"
            android:paddingLeft="8dp"
            android:paddingRight="8dp"
            android:layout_toLeftOf="@+id/btn_main_send"
            android:gravity="center_vertical"
            android:background="@drawable/shape_bg_input"
            android:textSize="13sp"
            android:textColor="#333333"/>
```

###### View for emoticon:
```xml
        <com.ev.library.view.EmoticonView
            android:id="@+id/ev_main"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:layout_below="@id/view_divider_main"></com.ev.library.view.EmoticonView>
```

**2、Initial in code**
```java
        mEvView =  findViewById(R.id.ev_main);
        mInputView = findViewById(R.id.eet_main);

        mInputView.post(new Runnable() {
            @Override
            public void run() {
                mEvView.init(EmoticonTypeUtils.ALL_TYPE, mIEmotionSendEvent, mInputView);
            }
        });
        
        private IEmotionSendEvent mIEmotionSendEvent = new IEmotionSendEvent() {
        @Override
        public void onEmotionSend(String emotionEncoded, int width, int height, long size) {
            // update your message list
        }
    };
```

**3、decode**
```java
    // decode emoji emotions
    EmoticonManager.getInstance(mContext).decode(msg.getmContent(), size, size));
    
    // decode picture emotions
    EmoticonManager.getInstance(mContext).decodePic(msg.getmContent());
```



