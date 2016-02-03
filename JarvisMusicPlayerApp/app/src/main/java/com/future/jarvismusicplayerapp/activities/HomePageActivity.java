package com.future.jarvismusicplayerapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.future.jarvismusicplayerapp.R;
import com.future.jarvismusicplayerapp.utils.Utility;

public class HomePageActivity extends AppCompatActivity {

    private TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        refreshContent(getBaseContext());
        getUserIdentificationIfEmpty(getBaseContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.refreshContent(getBaseContext());
    }

    private void getUserIdentificationIfEmpty(Context context) {
        String userEmailId = Utility.getUserEmailId(context);
        if (userEmailId == null) {
           //startActivityForResult Login
            Intent loginActivityIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(loginActivityIntent, 1);
        }
    }

    private void refreshContent(Context context) {
        this.tvUserName =(TextView)findViewById(R.id.usernameText);

        String userName = Utility.getUserName(context);
        if (userName != null) {
            this.tvUserName.setText(userName);
        }

    }
}
