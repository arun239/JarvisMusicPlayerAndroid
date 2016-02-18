package com.future.jarvismusicplayerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.future.jarvismusicplayerapp.R;

public class SongPageActivity extends AppCompatActivity {

    private TextView tvPlaylistName;
    private TextView tvPlaylistDesc;
    private String playlistId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvPlaylistName = (TextView) findViewById(R.id.playlistNameText);
        tvPlaylistName.setText(getIntent().getExtras().getString("playlistName"));

        tvPlaylistDesc = (TextView) findViewById(R.id.playlistDescriptionText);
        tvPlaylistDesc.setText(getIntent().getExtras().getString("playlistDescription"));

        playlistId = getIntent().getExtras().getString("playlistId");

        FloatingActionButton uploadSong = (FloatingActionButton) findViewById(R.id.uploadSong);
        uploadSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SongPageActivity.this, UploadSongActivity.class);
                intent.putExtra("playlistId",playlistId);
                startActivity(intent);
            }
        });


    }

}
