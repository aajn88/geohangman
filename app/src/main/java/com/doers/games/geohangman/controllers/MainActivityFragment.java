package com.doers.games.geohangman.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.services.IGeoHangmanService;
import com.google.inject.Inject;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;


/**
 * This is the Main Activity Fragment
 *
 * This fragment represents the main layout for the GeoHangman Game
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class MainActivityFragment extends RoboFragment {

    @InjectView(R.id.startGameBtn)
    private Button mStartGameBtn;

    /** GeoHangman Main Services **/
    @Inject
    private IGeoHangmanService geoHangmanService;

    /**
     * No-parameters constructor
     */
    public MainActivityFragment() {
    }

    /**
     * This method sets up view components
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mStartGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEvent(v);
            }
        });
    }

    /**
     * This method manages events launched by Views
     *
     * @param v
     */
    private void onEvent(View v) {
        switch(v.getId()) {
            case R.id.startGameBtn:
                Intent takePicActivityIntent = new Intent(getActivity(), TakePicActivity.class);
                startActivity(takePicActivityIntent);
                break;
        }
    }

    /**
     * Inflates the layout
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
