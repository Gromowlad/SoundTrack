package com.soundtrack.bart.soundtrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.ParseException;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

    }

    public void submitRegInfo(View view) {
        EditText userId = (EditText)findViewById(R.id.userId);
        EditText userPassword = (EditText)findViewById(R.id.userPassword);

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

        //asynchronously creates new app user
        else {

            Parse.enableLocalDatastore(this);
            Parse.initialize(this, "1sftv7BRaPviSonGJHRbqp4lQcWC4v1Yac0KQC8P", "73XeRNJ41nxiPKpnrYivVjxoXxljqDvADDAt9gRU");

            ParseUser user = new ParseUser();
            user.setUsername(userId.getText().toString().trim());
            user.setPassword(userPassword.getText().toString().trim());

            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        Intent menuActivity = new Intent(RegistrationActivity.this, MenuActivity.class);
                        startActivity(menuActivity);
                    } else {
                        // Sign up didn't succeed. Look at the ParseException
                        // to figure out what went wrong
                    }
                }
            });
            }

    }

}
