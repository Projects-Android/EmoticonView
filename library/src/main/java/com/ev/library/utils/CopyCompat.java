package com.ev.library.utils;

import android.text.Editable;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextUtils;

import com.ev.library.span.EmotionSpan;
import com.ev.library.view.EmoticonEditText;

/**
 * copy compatibility
 * Created by EV on 2018/4/23.
 */
public class CopyCompat {

    /**
     * @param pEmoticonEditText the p emotion edit text
     * @return the selection end compbat
     */
    public static int getSelectionEndCompbat(EmoticonEditText pEmoticonEditText) {
        final int selectionStart = pEmoticonEditText.getSelectionStart();
        CharSequence content = pEmoticonEditText.getText();
        int selectionEnd;
        if (content instanceof Spanned) {
            selectionEnd = ((Spanned) content)
                    .getSpanEnd(Selection.SELECTION_END);
            EmotionSpan[] spans = pEmoticonEditText.getEditableText().getSpans(selectionStart,
                    content.length(), EmotionSpan.class);
            if (spans != null
                    && spans.length > 0
                    && selectionStart == selectionEnd - 1) {
                String source;
                for (EmotionSpan span : spans) {
                    source = span.getEmotion().encode();
                    if (selectionStart + source.length() > content.length())
                        break;
                    if (!TextUtils.isEmpty(source)
                            && source.equals(content.subSequence(
                            selectionStart,
                            selectionStart + source.length())
                            .toString())) {
                        return selectionStart + source.length();
                    }
                }
            }
            return selectionEnd;
        } else {
            return pEmoticonEditText.getSelectionEnd();
        }
    }

    /**
     * @param pEmoticonEditText
     * @param selStart
     * @param selEnd
     */
    public static void onSelectionChangeCombat(EmoticonEditText pEmoticonEditText, int selStart, int selEnd) {
        boolean isMultiSelect = (selStart != selEnd);
        final Editable editableText = pEmoticonEditText.getEditableText();
        if (!TextUtils.isEmpty(editableText) && selEnd < editableText.length()) {
            final EmotionSpan[] spans = editableText.getSpans(selStart - 1, selEnd, EmotionSpan.class);
            if (spans.length > 0) {
                final int spanEnd = editableText.getSpanEnd(spans[spans.length - 1]);
                if (selEnd != spanEnd) {
                    selEnd = spanEnd;
                }
                if (!isMultiSelect) {
                    selStart = selEnd;
                } else {
                    final int firstSpanStart = editableText.getSpanStart(spans[0]);
                    final int firstSpanEnd = editableText.getSpanEnd(spans[0]);
                    if (selStart >= firstSpanStart && selStart < firstSpanEnd) {
                        selStart = firstSpanStart;
                    }
                }
                pEmoticonEditText.setSelection(selStart, selEnd);
            }
        }
    }
}
