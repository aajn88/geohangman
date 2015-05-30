package com.doers.games.geohangman.controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doers.games.geohangman.R;

import roboguice.fragment.RoboFragment;


/**
 * This is the Main Activity Fragment
 *
 * This fragment represents the main layout for the GeoHangman Game
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class LoginFragment extends RoboFragment {

    /**
     * No-parameters constructor
     */
    public LoginFragment() {
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
