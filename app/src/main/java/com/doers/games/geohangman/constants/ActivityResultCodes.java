package com.doers.games.geohangman.constants;

/**
 *
 * This enum represents Activities Result Codes
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public enum ActivityResultCodes {
    TAKE_PIC_CODE(1);

    /** This code represents the code for result in activity when Take A Pic **/
    private int code;

    /**
     *
     * @param code to be used for result activity
     */
    ActivityResultCodes(int code) {
        this.code = code;
    }

    /**
     * getCode
     * @return code
     */
    public int getCode() {
        return code;
    }
}
