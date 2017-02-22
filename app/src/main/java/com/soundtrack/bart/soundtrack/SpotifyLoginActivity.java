package com.soundtrack.bart.soundtrack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

public class SpotifyLoginActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "76cd9dfe23114c1eb7fe6624dbaf0fc3";
    private static final String REDIRECT_URI = "soundtrack://callback";
    private static final int REQUEST_CODE = 1993;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Handling Spotify
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"user-follow-read"});
        AuthenticationRequest request = builder.build();

        //AuthenticationClient.openLoginInBrowser(this, request);
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    /*protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Intent redirectToImportArtistsActivity = new Intent(SpotifyLoginActivity.this, ImportArtistsActivity.class);
        redirectToImportArtistsActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        redirectToImportArtistsActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Uri uri = intent.getData();
        if (uri != null) {
            AuthenticationResponse response = AuthenticationResponse.fromUri(uri);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    spotifyAccessToken = response.getAccessToken();
                    Toast.makeText(getApplicationContext(), "Spotify login successful" + spotifyAccessToken, Toast.LENGTH_LONG).show();
                    startActivity(redirectToImportArtistsActivity);
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    Toast.makeText(getApplicationContext(), "Spotify login error", Toast.LENGTH_LONG).show();
                    startActivity(redirectToImportArtistsActivity);
                    break;

                // Most likely auth flow was cancelled
                default:
                    Toast.makeText(getApplicationContext(), "Spotify login error", Toast.LENGTH_LONG).show();
                    startActivity(redirectToImportArtistsActivity);
                    // Handle other cases


            }
        }
    } */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        Intent redirectToImportArtistsActivity = new Intent(SpotifyLoginActivity.this, ImportArtistsActivity.class);
        redirectToImportArtistsActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        redirectToImportArtistsActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    String spotiAccessToken = response.getAccessToken();
                    Toast.makeText(getApplicationContext(), "Spotify login successful." + spotiAccessToken, Toast.LENGTH_LONG).show();
                    startActivity(redirectToImportArtistsActivity);
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    Toast.makeText(getApplicationContext(), "Spotify login error - log in to the Spotify Android app.", Toast.LENGTH_LONG).show();
                    startActivity(redirectToImportArtistsActivity);
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
                    Toast.makeText(getApplicationContext(), "Spotify login error.", Toast.LENGTH_LONG).show();
                    startActivity(redirectToImportArtistsActivity);
            }
        }
    }

}
