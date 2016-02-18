package com.future.jarvismusicplayerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.future.jarvismusicplayerapp.Constants;
import com.future.jarvismusicplayerapp.R;
import com.future.jarvismusicplayerapp.utils.Utility;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UploadSongActivity extends AppCompatActivity {

    private EditText etSongName;
    private Spinner spSongLanguage;
    private Spinner spSongGenre;
    private TextView tvFileName;
    private Button btBrowse;
    private File selectedFile;
    private Button btUpload;
    private String playlistId;
    private String enteredSongName;
    private String enteredSongLanguage;
    private String enteredSongGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_song);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        etSongName = (EditText) findViewById(R.id.songName);

        spSongLanguage =(Spinner) findViewById(R.id.songLanguageSpinner);

        spSongGenre =(Spinner) findViewById(R.id.songGenreSppiner);

        tvFileName = (TextView)findViewById(R.id.fileName);

        btBrowse = (Button)findViewById(R.id.fileBrowse);

        btBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browseFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                browseFileIntent.setType("*/*");
                startActivityForResult(browseFileIntent, 1);
            }
        });

        btUpload = (Button) findViewById(R.id.fileUpload);

        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btUploadButtonClicked();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String filePath = data.getDataString();
        Log.i(Constants.TAG, "FilePath" + filePath);
        tvFileName.setText(data.getDataString());
        // do somthing...
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void btUploadButtonClicked() {

        enteredSongName = etSongName.getText().toString();
        enteredSongLanguage = spSongLanguage.getSelectedItem().toString();
        enteredSongGenre = spSongGenre.getSelectedItem().toString();
        playlistId = getIntent().getExtras().getString("playlistId");


        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("songName", enteredSongName );
        paramMap.put("songLanguage", enteredSongLanguage );
        paramMap.put("songGenre", enteredSongGenre );
        paramMap.put("userEmail", Utility.getUserEmailId(getBaseContext()));
        paramMap.put("playlistId", playlistId);



    }



}
