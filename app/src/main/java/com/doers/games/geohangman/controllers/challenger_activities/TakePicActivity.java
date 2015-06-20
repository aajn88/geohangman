package com.doers.games.geohangman.controllers.challenger_activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.constants.ActivityResultCodes;
import com.doers.games.geohangman.constants.Messages;
import com.doers.games.geohangman.services.IGeoHangmanService;
import com.doers.games.geohangman.utils.ImageUtils;
import com.doers.games.geohangman.utils.StoreUtils;
import com.google.inject.Inject;

import java.io.File;
import java.io.IOException;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

/**
 * This is the Take Pic Activity, where the user has to take a pic to be sent to his opponent
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class TakePicActivity extends RoboActionBarActivity {

    /** Constant for temporary picture file **/
    private static final String TEMPORARY_PICTURE_FILE_NAME = "picture";

    /** Constant for .jpg extension **/
    private static final String JPG_EXTENSION = ".jpg";

    /** Current Image Uri **/
    private Uri mImageUri;

    /** Take Pic Button **/
    @InjectView(R.id.takePicBtn)
    private Button mTakePicBtn;

    /** Next Button in TakePic Activity **/
    @InjectView(R.id.picNextBtn)
    private Button mPicNextBtn;

    /** Took Pic **/
    @InjectView(R.id.resultPicIv)
    private ImageView mResultPicIv;

    /** GeoHangman Main Service **/
    @Inject
    private IGeoHangmanService geoHangmanService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_pic);

        loadPic();
        mTakePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEvent(v);
            }
        });
        mPicNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEvent(v);
            }
        });
    }

    /**
     * This method manages the events launched by Views
     *
     * @param v
     */
    private void onEvent(View v) {
        switch (v.getId()) {
            case R.id.takePicBtn:
                startCamera();
                break;
            case R.id.picNextBtn:
                // TODO: Event when next button is clicked
                Intent i = new Intent(this, SelectLocationHintActivity.class);
                startActivity(i);
                break;
        }
    }

    /**
     * This method sends Camera intent to be started
     */
    private void startCamera() {
        File photo;

        try {
            photo = StoreUtils.createTemporaryFile(TEMPORARY_PICTURE_FILE_NAME, JPG_EXTENSION);
            photo.delete();
        } catch (IOException e) {
            Log.e(Messages.ERROR, Messages.CREATING_FILE_ERROR);
            Toast.makeText(this, R.string.check_sd_card_error, Toast.LENGTH_LONG).show();
            return;
        }

        mImageUri = Uri.fromFile(photo);
        Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);

        if(takePicIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicIntent, ActivityResultCodes.TAKE_PIC_CODE.getCode());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ActivityResultCodes.TAKE_PIC_CODE.getCode() && resultCode == RESULT_OK) {
            Bitmap newPic = null;
            try {
                newPic = ImageUtils.grabImage(this.getContentResolver(), mImageUri);
            } catch (IOException e) {
                Log.e(Messages.ERROR, Messages.FAIL_TO_LOAD, e);
                Toast.makeText(this, R.string.failed_to_load, Toast.LENGTH_LONG).show();
            }

            loadPic(newPic);
        }
    }

    /**
     * This method loads and store a new Pic
     *
     * @param pic to be loaded and stored
     */
    private void loadPic(Bitmap pic) {
        geoHangmanService.storePic(pic);
        loadPic();
    }

    /**
     * This method retrieves the current stored pic and load it into mResultPicIv.
     * Also, this method manages mPicNextBtn visibility
     */
    private void loadPic() {
        Bitmap currentImage = geoHangmanService.getStoredPic();
        if(currentImage != null) {
            mResultPicIv.setImageBitmap(currentImage);
            mPicNextBtn.setEnabled(Boolean.TRUE);
        } else {
            mPicNextBtn.setEnabled(Boolean.FALSE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_take_pic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
