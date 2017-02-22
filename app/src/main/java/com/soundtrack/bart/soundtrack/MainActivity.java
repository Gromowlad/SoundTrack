package com.soundtrack.bart.soundtrack;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.LogInCallback;

public class MainActivity extends AppCompatActivity {

    private Button registrationButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        //check if user is in session
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {

            Intent menuActivityIntent = new Intent(MainActivity.this, MenuActivity.class);
            menuActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            menuActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(menuActivityIntent);

        }
    }

    public void submitLoginInfo(View view) {
        EditText idField = (EditText) findViewById(R.id.userId);
        EditText passwordField = (EditText) findViewById(R.id.userPassword);

        final String userId = idField.getText().toString().trim();
        final String userPassword = passwordField.getText().toString().trim();

        //sets error message if user ID field is empty
        if (userId.equals("")) {

            idField.setError("Please enter your user ID.");
        }

        //sets error message if password field is empty
        else if (userPassword.equals("")) {
            passwordField.setError("Please enter your password.");
        }

        //asynchronously creates new app user
        else {

            ParseUser.logInInBackground(userId, userPassword, new LogInCallback() {
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {

                        Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_LONG).show();

                        Intent menuActivityIntent = new Intent(MainActivity.this, MenuActivity.class);
                        menuActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        menuActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(menuActivityIntent);
                        finish();
                    } else {
                        // Signup failed. Look at the ParseException to see what happened.
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(e.getMessage()).setTitle("Login error").setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
        }
    }

    public void submitRegistrationInfo(View view) {
        EditText userId = (EditText)findViewById(R.id.userId);
        EditText userPassword = (EditText)findViewById(R.id.userPassword);
        EditText userEmail = (EditText)findViewById(R.id.userEmail);

        //sets error message if user ID field is empty
        if(userId.getText().toString().trim().equals("")){

            /**
             *   You can Toast a message here that the Username is Empty
             **/

            userId.setError("Please enter desired user ID.");

        }

        //sets error message if password field is empty
        else if(userPassword.getText().toString().trim().equals("")) {
            userPassword.setError("Please enter desired password.");
        }

        //asynchronously creates new app user and redirect to main menu if successful
        else {

            ParseUser user = new ParseUser();
            user.setUsername(userId.getText().toString().trim());
            user.setPassword(userPassword.getText().toString().trim());
            user.setEmail(userEmail.getText().toString().trim());

            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_LONG).show();

                        Intent menuActivityIntent = new Intent(MainActivity.this, MenuActivity.class);
                        menuActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        menuActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(menuActivityIntent);
                        finish();
                    } else {
                        // Sign up didn't succeed. Look at the ParseExceptio to figure out what went wrong
                        // Signup failed. Look at the ParseException to see what happened.
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(e.getMessage()).setTitle("Registration error").setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
        }
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
