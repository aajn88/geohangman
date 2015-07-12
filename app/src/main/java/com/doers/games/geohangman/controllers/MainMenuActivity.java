package com.doers.games.geohangman.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.controllers.challenger_activities.TakePicActivity;
import com.doers.games.geohangman.controllers.opponent_activities.StartChallengeActivity;

import java.util.GregorianCalendar;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

/**
 * This class manages main GeoHangman menu. This is where all starts
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class MainMenuActivity extends RoboActionBarActivity {

    /** This threshold is used to define a limit for back button * */
    private static final Long THRESHOLD = 1000l;

    /** Start Game Button * */
    @InjectView(R.id.startGameBtn)
    private Button mStartGameBtn;

    /** FIXME: Remove this and all Nfc emulation * */
    @InjectView(R.id.emulateNfcBtn)
    private Button mEmulateNfcBtn;

    /** Last time Back button was pressed * */
    private GregorianCalendar lastBackPressed = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mStartGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEvent(v);
            }
        });

        mEmulateNfcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startChallengeIntent = new Intent(MainMenuActivity.this,
                        StartChallengeActivity.class);
                startChallengeIntent.putExtra(StartChallengeActivity.CHALLENGE_ID_EXTRA, 524301);
                startActivity(startChallengeIntent);
            }
        });
    }

    /**
     * This method manages events launched by Views
     *
     * @param v
     */
    private void onEvent(View v) {
        switch (v.getId()) {
            case R.id.startGameBtn:
                Intent takePicActivityIntent = new Intent(MainMenuActivity.this,
                        TakePicActivity.class);
                startActivity(takePicActivityIntent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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

    @Override
    public void onBackPressed() {
        if (lastBackPressed != null && isValidLastBackPressed()) {
            finishAffinity();
        } else {
            lastBackPressed = new GregorianCalendar();
            Toast.makeText(this, R.string.press_again_to_exit, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method checks if last time back button was pressed, was a given THRESHOLD ago
     *
     * @return True if back button was pressed before the THRESHOLD, otherwise returns False
     */
    private Boolean isValidLastBackPressed() {
        GregorianCalendar now = new GregorianCalendar();
        return now.getTimeInMillis() - lastBackPressed.getTimeInMillis() <= THRESHOLD;
    }
}
