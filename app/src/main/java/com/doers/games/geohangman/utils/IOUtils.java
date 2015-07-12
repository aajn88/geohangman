package com.doers.games.geohangman.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * This is a Store Utils Class
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public final class IOUtils {

    /** Constant for Geohangman static folder **/
    private static final String GEOHANGMAN_STATIC_FOLDER = "/Geohangman/";

    private static String geohangmanFinalPath;

    /** Constant for temporary folder **/
    public static final String TEMPORARY_FOLDER = "/.temp/";

    static {
        StringBuilder sb = new StringBuilder(Environment.getExternalStorageDirectory().toString());
        sb.append(GEOHANGMAN_STATIC_FOLDER);
        geohangmanFinalPath = sb.toString();
    }

    /** Private no-parameters constructor **/
    private IOUtils(){}

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

    /**
     * This method writes a given content into a file
     * @param filePath File where content will be written
     * @param content The content to be written into file
     */
    public static void writeFile(String filePath, String content, Context context) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(filePath, Context
                .MODE_PRIVATE));
        osw.write(content);
        osw.close();
    }

    /**
     * This method reads a given file and returns its content as a String.
     *
     * @param filePath File name
     * @param context Current Context
     * @return File content as a String
     * @throws IOException
     */
    public static String retrieveFileContentAsString(String filePath, Context context) throws
            IOException {
        FileInputStream fis = context.openFileInput(filePath);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }

        bufferedReader.close();
        isr.close();
        fis.close();

        return line;
    }

    /**
     * This method returns the Geohangman folder path
     *
     * @return Geohangman folder path
     */
    public static String getGeohangmanFolderPath() {
        return geohangmanFinalPath;
    }

}
