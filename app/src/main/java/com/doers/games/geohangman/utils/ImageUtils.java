package com.doers.games.geohangman.utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * This is an Image Utils Class
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public final class ImageUtils {

    /** Image rotation constant **/
    private static final float ROTATION_DEGREES = 90.0f;

    /** Private no-parameters constructor **/
    private ImageUtils(){}

    /**
     * This method retrieves full-sized image from temporary storage
     *
     * @return Bitmap of the image
     */
    public static Bitmap grabImage(ContentResolver cr, Uri imageUri) throws IOException {
        cr.notifyChange(imageUri, null);

        ExifInterface ei = new ExifInterface(imageUri.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        Bitmap image = MediaStore.Images.Media.getBitmap(cr, imageUri);
        return ImageUtils.rotateBitmap(image, orientation);
    }

    /**
     *
     * This method rotates an image based on its natural rotation
     *
     * @param image
     * @param orientation
     * @return New Bitmap with rotated image
     */
    private static Bitmap rotateBitmap(Bitmap image, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(ROTATION_DEGREES);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(ROTATION_DEGREES * 2);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(ROTATION_DEGREES * 3);
                break;
        }
        return Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
    }

    public static byte []buildBitmapByteArray(Bitmap image) {
        return buildBitmapByteArray(image, Bitmap.CompressFormat.PNG, 100);
    }

    public static byte []buildBitmapByteArray(Bitmap image, Bitmap.CompressFormat format, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(format, quality, stream);
        return stream.toByteArray();
    }

}
