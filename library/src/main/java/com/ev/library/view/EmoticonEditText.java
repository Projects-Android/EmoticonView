package com.ev.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewDebug;
import android.widget.EditText;

import com.ev.library.EmoticonHandlers;
import com.ev.library.utils.CopyCompat;

/**
 * Created by EV on 2018/4/23.
 */
public class EmoticonEditText extends EditText implements IInputView, View.OnCreateContextMenuListener {

    private boolean mIsPaste;

    public EmoticonEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnCreateContextMenuListener(this);
    }

    public EmoticonEditText(Context context) {
        super(context);
    }

    @Override
    public void onBackSpace() {
        dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
    }

    @Override
    public void setSelection(Pair<Integer, Integer> pSelectionPair) {
        try {
            setSelection(pSelectionPair.first, pSelectionPair.second);
        } catch (IndexOutOfBoundsException e) {
            setSelection(getText().toString().length());
        }
    }

    @Override
    @ViewDebug.ExportedProperty(category = "text")
    public int getSelectionEnd() {
        return CopyCompat.getSelectionEndCompbat(this);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        CopyCompat.onSelectionChangeCombat(this, selStart, selEnd);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (mIsPaste) {
            EmoticonHandlers.updateEmotions(getContext(), getEditableText(), (int) getTextSize(), (int) getTextSize());
            mIsPaste = false;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        mIsPaste = true;
    }

    public interface OnKeyPreIme{
        boolean onKeyPreIme(int keyCode, KeyEvent event);
    }

    private OnKeyPreIme mOnKeyPreIme;

    public void setOnKeyPreIme(OnKeyPreIme onKeyPreIme){
        mOnKeyPreIme = onKeyPreIme;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (mOnKeyPreIme != null) {
            if (mOnKeyPreIme.onKeyPreIme(keyCode, event)) {
                return true;
            }
        }
        return super.onKeyPreIme(keyCode, event);
    }
}
