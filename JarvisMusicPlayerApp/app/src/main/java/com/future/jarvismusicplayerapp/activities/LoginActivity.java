package com.future.jarvismusicplayerapp.activities;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
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

import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {
    private EditText etUserName;
    private EditText etUserEmail;
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

        String enteredUserName = this.etUserName.getText().toString();
        String enteredUserEmail = this.etUserEmail.getText().toString();

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

        this.restHelper.sendData(jsonData, getBaseContext());  //return analyze and takes steps


        Utility.setUserName(getBaseContext(), enteredUserName);
        Utility.setUserEmailId(getBaseContext(), enteredUserEmail);
        finish();
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


