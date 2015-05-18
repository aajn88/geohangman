package com.doers.games.geohangman.utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.constants.Messages;

import java.io.IOException;

/**
 * This is an Image Utils Class
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public final class ImageUtils {

    /** Image rotation constant **/
    private static final float ROTATION_DEGREES = 90.0f;

    /**
     * This method retrieves full-sized image from temporary storage
     *
     * @return Bitmap of the image
     */
    public static Bitmap grabImage(ContentResolver cr, Uri imageUri) throws IOException {
        cr.notifyChange(imageUri, null);

        Bitmap image = null;

        ExifInterface ei = new ExifInterface(imageUri.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        image = MediaStore.Images.Media.getBitmap(cr, imageUri);
        image = ImageUtils.rotateBitmap(image, orientation);

        return image;
    }

    /**
     *
     * This method rotates an image based on its natural rotation
     *
     * @param image
     * @param orientation
     * @return New Bitmap with rotated image
     */
    public static Bitmap rotateBitmap(Bitmap image, int orientation) {
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

}
