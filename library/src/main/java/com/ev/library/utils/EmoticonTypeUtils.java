package com.ev.library.utils;

import com.ev.library.bean.group.Group;
import com.ev.library.bean.group.IGroup;

/**
 * utils for emotion type
 * Created by EV on 2018/4/23.
 */
public class EmoticonTypeUtils {

    public static final String PIC_STR = "PIC";
    public static final String EMOJI_STR = "EMOJI";

    public static final int EMOJI = 0x02;
    public static final int PIC = 0x04;
    public static final int ALL_TYPE = EMOJI | PIC;

    /**
     * string type to integer
     * </p>
     * @param pStrValue the p str value
     * @return the int
     */
    public static int strToInt(String pStrValue) {
        if (PIC_STR.equals(pStrValue)) {
            return PIC;
        }
        if (EMOJI_STR.equals(pStrValue)) {
            return EMOJI;
        }
        return 0;
    }

    /**
     * judge if group matches the type or not
     * </p>
     * @param pGroup the p group
     * @param pType  the p type
     * @return
     */
    public static boolean typeMatch(Group pGroup, int pType) {
        return pGroup.getType() == (pType & pGroup.getType());
    }

    /**
     * all types match
     * </p>
     * @param pType the p type
     * @return the boolean
     */
    public static boolean allMatch(int pType) {
        return ALL_TYPE == (pType & ALL_TYPE);
    }

}
