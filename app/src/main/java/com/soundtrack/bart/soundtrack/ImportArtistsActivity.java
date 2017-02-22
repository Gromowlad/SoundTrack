package com.soundtrack.bart.soundtrack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ImportArtistsActivity extends AppCompatActivity {

    //Facebook SDK variables
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private String facebookUserId;
    private AccessToken facebookAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ParseUser user = ParseUser.getCurrentUser();

        //Facebook SDK initialization
        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_import_artists);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_likes");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
              //  Toast.makeText(getApplicationContext(), "Facebook login success " + loginResult.getAccessToken().getToken(), Toast.LENGTH_LONG).show();

                facebookUserId = loginResult.getAccessToken().getUserId();
                facebookAccessToken = loginResult.getAccessToken();
                String accessTokenString = facebookAccessToken.getToken();

                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("facebookid", facebookUserId);
                params.put("fbtoken", accessTokenString);

                ParseCloud.callFunctionInBackground("parseartists", params, new FunctionCallback<String>() {
                    public void done(String artList, ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "CLOUD CODE SUCCESS \n" + artList, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "CLOUD CODE ERROR", Toast.LENGTH_LONG).show();
                        }

                    }
                });

                //log out automatically
                LoginManager.getInstance().logOut();

                /*
                ParseObject facebookData = new ParseObject("facebookData");
                facebookData.put("user", user);
                facebookData.put("facebookId", facebookUserId);
                facebookData.put("facebookAccessToken", accessTokenString);
                facebookData.saveInBackground();

                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/" + facebookUserId + "/music",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                try {
                                    JSONObject responseObject = new JSONObject(response.getJSONObject().toString());
                                    JSONArray responseArray = responseObject.getJSONArray("data");
                                    String responseString = responseObject.toString();

                                    if (responseObject != null) {
                                        try {
                                            JSONObject jsonObj = new JSONObject(responseString);

                                            // looping through All Contacts
                                            for (int i = 0; i < responseArray.length(); i++) {
                                                JSONObject artistName = responseArray.getJSONObject(i);

                                                String artistNameString = artistName.getString("name");

                                                ParseObject artists = new ParseObject("artists");
                                                artists.put("user", user);
                                                artists.put("artist", artistNameString);
                                                artists.saveInBackground();

                                                //Toast.makeText(getApplicationContext(), "Output:" + artistNameString, Toast.LENGTH_LONG).show();

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                //Toast.makeText(getApplicationContext(), "Output:" + response.getJSONObject().toString(), Toast.LENGTH_LONG).show();;
                            }
                        }
                ).executeAsync();
                */
                //getFacebookMusicLikes(facebookAccessToken);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login attempt cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getApplicationContext(), "Login attempt failed", Toast.LENGTH_LONG).show();
            }
        });
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void backToMainMenu(View view) {
        Intent mainActivityIntent = new Intent(ImportArtistsActivity.this, MenuActivity.class);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivityIntent);
    }

    public void redToSpotifyLogin(View view) {

        Intent spotifyActivityIntent = new Intent(ImportArtistsActivity.this, SpotifyLoginActivity.class);
        spotifyActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        spotifyActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(spotifyActivityIntent);
    }

       /*protected void getFacebookMusicLikes(AccessToken accessToken) {
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            // Application code
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link");
            request.setParameters(parameters);
            request.executeAsync();
        } */
    }
