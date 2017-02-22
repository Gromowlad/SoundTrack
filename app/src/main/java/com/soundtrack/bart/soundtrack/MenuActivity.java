package com.soundtrack.bart.soundtrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseUser;

public class MenuActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //check if user is in session
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {

            Intent mainActivityIntent = new Intent(MenuActivity.this, MainActivity.class);
            mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainActivityIntent);
        }

    }

    public void redToImportArtists(View view) {

        Intent mainActivityIntent = new Intent(MenuActivity.this, ImportArtistsActivity.class);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivityIntent);
    }

    public void redToUpdateLocation(View view) {

        Intent mainActivityIntent = new Intent(MenuActivity.this, GetCurrentLocationActivity.class);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivityIntent);
    }

    public void redToViewArtistList(View view) {

        Intent mainActivityIntent = new Intent(MenuActivity.this, ViewArtistListActivity.class);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivityIntent);
    }



    public void logOut(View view) {
        ParseUser.logOut();
        Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_LONG).show();

        Intent mainActivityIntent = new Intent(MenuActivity.this, MainActivity.class);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivityIntent);
    }

    //LOGOUT WHEN PRESSING BACK BUTTON
    /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            onBackPressed();
            finish();
            ParseUser.logOut();
            Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_LONG).show();
        }
        return super.onKeyDown(keyCode, event);
    } */

}
