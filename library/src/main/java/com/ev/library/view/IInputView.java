package com.ev.library.view;


import android.text.Spannable;
import android.util.Pair;

/**
 * interface for input view
 * Created by EV on 2018/4/23.
 */
public interface IInputView {

    /**
     * get spannable text
     * </p>
     * @return the text
     */
    Spannable getText();

    /**
     * set text
     * </p>
     * @param pText
     */
    void setText(CharSequence pText);

    /**
     * get start index of selection
     * </p>
     * @return the selection start
     */
    int getSelectionStart();

    /**
     * get end index of selection
     * </p>
     * @return the selection end
     */
    int getSelectionEnd();

    /**
     * back space
     */
    void onBackSpace();

    /**
     * set selection
     * </p>
     * @param pSelectionPair the p selection pair
     */
    void setSelection(Pair<Integer, Integer> pSelectionPair);

    /**
     * get text size
     * @return
     */
    float getTextSize();
}
