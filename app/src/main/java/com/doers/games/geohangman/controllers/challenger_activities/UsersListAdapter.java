package com.doers.games.geohangman.controllers.challenger_activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.doers.games.geohangman.R;
import com.doers.games.geohangman.constants.Messages;
import com.doers.games.geohangman.model.UserInfo;
import com.doers.games.geohangman.services.IUsersService;

import java.io.IOException;
import java.util.List;

/**
 * Custom ArrayAdapter for a list of Users
 */
public class UsersListAdapter extends ArrayAdapter<UserInfo> {

    /** Users to be displayed * */
    private List<UserInfo> users;

    /** The Users Service * */
    private IUsersService usersService;

    /**
     * Constructor that receives a specific context, a layout (resource) and a List of users to be
     * displayed
     *
     * @param context Current context
     * @param users   Users to be displayed
     */
    public UsersListAdapter(Context context, List<UserInfo> users, IUsersService usersService) {
        super(context, R.layout.layout_user_item, users);
        this.users = users;
        this.usersService = usersService;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.layout_user_item, parent, false);

        TextView userName = (TextView) rowView.findViewById(R.id.userNameTv);
        UserInfo currentUser = users.get(position);
        userName.setText(currentUser.getName());

        ImageView userPicIv = (ImageView) rowView.findViewById(R.id.userPicIv);
        new LoadProfilePicture(userPicIv, currentUser.getId()).execute();

        return rowView;
    }

    /**
     * This class loads a user's profile picture
     */
    private class LoadProfilePicture extends AsyncTask<Void, Void, Bitmap> {

        /** ImageView where User's profile picture will be placed * */
        private ImageView userPicIv;

        /** User Id to be searched * */
        private String userId;

        /**
         * Constructor that receives the User's Pic ImageView and the User Id
         *
         * @param userPicIv where profile picture will be placed
         * @param userId    The user id
         */
        public LoadProfilePicture(ImageView userPicIv, String userId) {
            this.userPicIv = userPicIv;
            this.userId = userId;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap profilePic = null;

            try {
                profilePic = usersService.retrieveProfilePicture(userId);
            } catch (IOException e) {
                Log.e(Messages.ERROR, "Error retrieving profile picture", e);
            }

            return profilePic;
        }

        @Override
        protected void onPostExecute(Bitmap profilePic) {
            userPicIv.setImageBitmap(profilePic);
        }
    }
}
