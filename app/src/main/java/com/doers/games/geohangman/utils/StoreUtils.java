package com.doers.games.geohangman.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * This is a Store Utils Class
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public final class StoreUtils {

    /** Constant for temporary folder **/
    private static final String TEMPORARY_FOLDER = "/.temp/";

    /**
     * This method creates a temporary file to store the pic
     *
     * @param part Name of the file
     * @param ext Type of the file (extension)
     * @return Temporary file
     * @throws IOException
     */
    public static File createTemporaryFile(String part, String ext) throws IOException {
        File tempDir = Environment.getExternalStorageDirectory();
        tempDir = new File(tempDir.getAbsolutePath() + TEMPORARY_FOLDER);
        if(!tempDir.exists()) {
            tempDir.mkdir();
        }
        return File.createTempFile(part, ext, tempDir);
    }
}
