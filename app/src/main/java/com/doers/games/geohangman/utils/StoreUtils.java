package com.doers.games.geohangman.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This is a Store Utils Class
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public final class StoreUtils {

    /** Constant for temporary folder **/
    public static final String TEMPORARY_FOLDER = "/.temp/";

    /**
     * This method creates a temporary file to store the pic
     *
     * @param fileName Name of the file
     * @param ext Type of the file (extension)
     * @return Temporary file
     * @throws IOException
     */
    public static File createTemporaryFile(String fileName, String ext) throws IOException {
        File tempDir = Environment.getExternalStorageDirectory();
        tempDir = new File(tempDir.getAbsolutePath() + TEMPORARY_FOLDER);
        if(!tempDir.exists()) {
            tempDir.mkdir();
        }
        return File.createTempFile(fileName, ext, tempDir);
    }

}
