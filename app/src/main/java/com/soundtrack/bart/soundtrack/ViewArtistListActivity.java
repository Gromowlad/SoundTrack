package com.soundtrack.bart.soundtrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ViewArtistListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_artist_list);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {

            Intent mainActivityIntent = new Intent(ViewArtistListActivity.this, ViewArtistListActivity.class);
            mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainActivityIntent);
        }

        String userId = currentUser.getObjectId().toString();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("artists");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> artists, ParseException e) {

                List<String> artistNames = new ArrayList<String>();
                if (e == null) {
                    for (ParseObject artist : artists) {
                        artistNames.add(artist.getString("artist"));
                    }
                } else {

                }

            }
        });






    }


}
