package com.future.jarvismusicplayerapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.jarvismusicplayerapp.Constants;
import com.future.jarvismusicplayerapp.R;
import com.future.jarvismusicplayerapp.pojo.PlaylistAdditionPojo;
import com.future.jarvismusicplayerapp.utils.Utility;
import com.future.jarvismusicplayerapp.workers.RestHelper;
import com.future.jarvismusicplayerapp.workers.RestResponseInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePageActivity extends AppCompatActivity {

    private TextView tvUserName;
    List<String> playlist_name;
    private ListView lvPlaylist;
    private final RestHelper restHelper = new RestHelper();

    //   Point p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        playlist_name = new ArrayList<String>();
        playlist_name.add("Playlist Name");
        lvPlaylist = (ListView) findViewById(R.id.playlists);

        refreshContent(getBaseContext());

        final Button btAddPlaylist = (Button) findViewById(R.id.add_playlist);
        btAddPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btAddPlaylistClicked();
                Intent addPlaylistIntent = new Intent(HomePageActivity.this, AddPlaylist.class);
                HomePageActivity.this.startActivity(addPlaylistIntent);
            }
        });
    }


    public void btAddPlaylistClicked() {

        Intent addPlaylistIntent = new Intent(this, AddPlaylist.class);
        startActivityForResult(addPlaylistIntent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.refreshContent(getBaseContext());
    }


    private void refreshContent(Context context) {
        this.tvUserName = (TextView) findViewById(R.id.usernameText);

        String userName = Utility.getUserName(context);
        if (userName != null) {
            this.tvUserName.setText(userName);
        } else {
            //startActivityForResult Login
            Intent loginActivityIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(loginActivityIntent, 1);
            return;
        }

        final RestResponseInterface playlistRetrieveResponse = new RestResponseInterface() {
            @Override
            public void responseReceived(String responseCode, String data) {
                Log.i(Constants.TAG, "Response Received with code: " + responseCode);
                switch (responseCode) {
                    case RestResponseInterface.SUCCESS:
                        Log.i(Constants.TAG, "Jarvis_Data: " + data);
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            PlaylistAdditionPojo[] playlistAdditionPojoArray = (PlaylistAdditionPojo[])mapper.readValue(data, PlaylistAdditionPojo[].class);
                            List<String> playListNameList = new ArrayList<>();
                            for (PlaylistAdditionPojo playlistAdditionPojo : playlistAdditionPojoArray) {
                                playListNameList.add(playlistAdditionPojo.getPlaylistName());
                            }
                            ArrayAdapter<String> adp = new ArrayAdapter<String>(getBaseContext(), R.layout.activity_list_plalylist, R.id.Playlist_name, playListNameList);
                            lvPlaylist.setAdapter(adp);
                        } catch (IOException e) {
                            Log.e(Constants.TAG,"Error in deserializing in received obj", e);
                        }

                        // Toast.makeText(getBaseContext(), "All playlists retrived.", Toast.LENGTH_LONG).show();
                        // finish();                 //Close Activity
                        break;
                    case RestResponseInterface.FAILURE:
                        Toast.makeText(getBaseContext(), "Error while retriving playlists.", Toast.LENGTH_LONG).show();
                        break;
                    default:
                }
            }

        };

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userEmail", Utility.getUserEmailId(getBaseContext()));
        this.restHelper.receiveData(getBaseContext(), playlistRetrieveResponse, Constants.FETCH_PLAYLIST, paramMap);
//        ArrayAdapter<String> adp = new ArrayAdapter<String>
//                (getBaseContext(), R.layout.activity_list_plalylist, R.id.playlist_name, playlist_name);
//        lvPlaylist.setAdapter(adp);
    }
}
