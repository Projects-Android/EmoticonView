package com.ev.library.decode;

import com.ev.library.exception.DecodeException;

/**
 * Created by EV on 2018/4/23.
 */
public interface IPicDecoder {

    /**
     * decode to url of picture
     * </p>
     * @param text
     * @return
     */
    String decode(String text) throws DecodeException;

}
