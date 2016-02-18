package com.future.jarvismusicplayerapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.jarvismusicplayerapp.Constants;
import com.future.jarvismusicplayerapp.R;
import com.future.jarvismusicplayerapp.pojo.PlaylistAdditionFinalPojo;
import com.future.jarvismusicplayerapp.pojo.PlaylistAdditionPojo;
import com.future.jarvismusicplayerapp.utils.Utility;
import com.future.jarvismusicplayerapp.workers.RestHelper;
import com.future.jarvismusicplayerapp.workers.RestResponseInterface;

/**
 * Created by arungu on 2/5/2016.
 */
public class AddPlaylistActivity extends AppCompatActivity {

    private EditText etPlaylistName;
    private EditText etPlaylistDescription;
    private String enteredPlaylistName;
    private String enteredPlaylistDescription;
    private final RestHelper restHelper = new RestHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_playlist);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        this.etPlaylistName = (EditText) findViewById(R.id.playlist_name);
        this.etPlaylistDescription = (EditText) findViewById(R.id.playlist_desc);

        final Button bAddPlaylist = (Button) findViewById(R.id.add);

        bAddPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playlistAdditionClicked();
            }
        });

    }

    public void playlistAdditionClicked(){

        this.enteredPlaylistName = this.etPlaylistName.getText().toString();
        this.enteredPlaylistDescription = this.etPlaylistDescription.getText().toString();

        Context context = getBaseContext();

        if (enteredPlaylistName == null || enteredPlaylistName.length() == 0)
        {

            String empty_playlist_name = context.getString(R.string.empty_playlist_name);
            Toast.makeText(getBaseContext(), empty_playlist_name, Toast.LENGTH_LONG).show();
            return;
        }
        if (enteredPlaylistDescription == null || enteredPlaylistDescription.length() == 0)
        {
            String empty_playlist_description = context.getString(R.string.empty_playlist_name);
            Toast.makeText(getBaseContext(), empty_playlist_description, Toast.LENGTH_LONG).show();
            return;
        }



        PlaylistAdditionPojo playlistAdditionPojo = new PlaylistAdditionPojo(enteredPlaylistName,enteredPlaylistDescription);
        PlaylistAdditionFinalPojo playlistAdditionFinalPojo = new PlaylistAdditionFinalPojo(playlistAdditionPojo,Utility.getUserEmailId(getBaseContext()));

        //POJO to String
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = null;
        try {
            jsonData = objectMapper.writeValueAsString(playlistAdditionFinalPojo);
        } catch (JsonProcessingException e) {
            Log.e(Constants.TAG, "Error occurred while serializing object", e);
            // Take Necessary steps.
        }

        RestResponseInterface playlistAdditionResponse = new RestResponseInterface() {
            @Override
            public void responseReceived(String responseCode, String data) {
                Log.i(Constants.TAG, "Response Received with code: " + responseCode);
                switch (responseCode) {
                    case RestResponseInterface.SUCCESS:
                        Toast.makeText(getBaseContext(),"Playlist Added Successfully.", Toast.LENGTH_LONG).show();
                        finish();
                       // Toast.makeText(getBaseContext(),"Finish called.", Toast.LENGTH_LONG).show();
                        break;
                    case RestResponseInterface.FAILURE:
                        Toast.makeText(getBaseContext(), "Error while adding playlist.", Toast.LENGTH_LONG).show();
                        break;
                    default:

                }
            }
        };
        this.restHelper.sendData(jsonData, getBaseContext(), playlistAdditionResponse, Constants.ADD_PlAYLIST);
    }
}
