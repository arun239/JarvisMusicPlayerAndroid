package com.future.jarvismusicplayerapp.activities;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.jarvismusicplayerapp.Constants;
import com.future.jarvismusicplayerapp.R;
import com.future.jarvismusicplayerapp.pojo.UserRegistrationPojo;
import com.future.jarvismusicplayerapp.utils.Utility;
import com.future.jarvismusicplayerapp.workers.RestHelper;
import com.future.jarvismusicplayerapp.workers.RestResponseInterface;

import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {
    private EditText etUserName;
    private EditText etUserEmail;
    private String enteredUserName;
    private String enteredUserEmail;
    private final RestHelper restHelper = new RestHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(Constants.TAG, "onCreate");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.etUserName = (EditText) findViewById(R.id.username);
        this.etUserEmail = (EditText) findViewById(R.id.useremail);


        final Button welcome = (Button) findViewById(R.id.welcome);

        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(getBaseContext()).getAccountsByType("com.google");
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                String possibleEmail = account.name;
                Log.i(Constants.TAG, "Email = " + possibleEmail);
                //Setting first email:
                this.etUserEmail.setText(possibleEmail);
                break;
            }
        }

        welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcomeButtonClicked();
            }
        });


    }

    public void welcomeButtonClicked() {
        Log.i(Constants.TAG,"onClick");

         this.enteredUserName = this.etUserName.getText().toString();
         this.enteredUserEmail = this.etUserEmail.getText().toString();

        //Checks
        Context context = getBaseContext();

        if (enteredUserEmail == null || enteredUserEmail.length() == 0)
        {

            String empty_email = context.getString(R.string.empty_user_email);
            Toast.makeText(getBaseContext(), empty_email, Toast.LENGTH_LONG).show();
            return;
        }
        if (enteredUserName == null || enteredUserName.length() == 0)
        {
            String empty_name = context.getString(R.string.empty_user_name);
            Toast.makeText(getBaseContext(), empty_name, Toast.LENGTH_LONG).show();
            return;
        }

        UserRegistrationPojo userRegistrationPojo = new UserRegistrationPojo(enteredUserName, enteredUserEmail);


        //POJO to String
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = null;
        try {
            jsonData = objectMapper.writeValueAsString(userRegistrationPojo);
        } catch (JsonProcessingException e) {
            Log.e(Constants.TAG, "Error occurred while serializing object", e);
            // Take Necessary steps.
        }

        //this.restHelper.sendData(jsonData, getBaseContext());  //return analyze and takes steps


        //Anonymous inner Class -- Alternative - Create a concrete class which will implement interface and then object of that class
        // will be used to call interface's function because we can't call interface's function directly

        RestResponseInterface userRegistrationResponse = new RestResponseInterface() {
            @Override
            public void responseReceived(String responseCode) {
                Log.i(Constants.TAG, "Response Received with code: " + responseCode);
                switch (responseCode) {
                    case RestResponseInterface.SUCCESS:
                        Utility.setUserName(getBaseContext(), enteredUserName);
                        Utility.setUserEmailId(getBaseContext(), enteredUserEmail);
                        Toast.makeText(getBaseContext(),"User Registration Completed.", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case RestResponseInterface.FAILURE:
                        Toast.makeText(getBaseContext(), "Error while registering user.", Toast.LENGTH_LONG).show();
                        break;
                    default:

                }
            }
        };

        this.restHelper.sendData(jsonData, getBaseContext(), userRegistrationResponse);

        //Close yourself
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


