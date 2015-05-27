package com.doers.games.geohangman.controllers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.doers.games.geohangman.BuildConfig;
import com.doers.games.geohangman.R;
import com.doers.games.geohangman.constants.Constants;

import java.io.ByteArrayOutputStream;

import roboguice.RoboGuice;
import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;


public class MainActivity extends RoboActionBarActivity {

    @InjectView(R.id.emulateNfcBtn)
    private Button mEmulateNfcBtn;

    static {
        RoboGuice.setUseAnnotationDatabases(false);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Intent startChallengeIntent = new Intent(this, StartChallengeActivity.class);
        startChallengeIntent.putExtra(Constants.CHALLENGE_EXTRA, intent);
        startActivity(startChallengeIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmulateNfcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String args = "MyWord|33.96482810963319|-118.30714412033558|6.589385";
                Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);

                NdefRecord imgRrd = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, ".png".getBytes(), null, stream.toByteArray());
                NdefRecord argsRrd = NdefRecord.createMime("application/" + BuildConfig.APPLICATION_ID, args.getBytes());

                NdefMessage msg = new NdefMessage(new NdefRecord[] {imgRrd, argsRrd});

                Intent i = new Intent();
                i.putExtra(NfcAdapter.EXTRA_NDEF_MESSAGES, new Parcelable[]{msg});

                handleIntent(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
