package com.future.jarvismusicplayerapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.future.jarvismusicplayerapp.R;
import com.future.jarvismusicplayerapp.utils.Utility;
import com.future.jarvismusicplayerapp.workers.RestHelper;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private TextView tvUserName;
    List<String> playlist_name;
    private ListView lplaylists;
    private final RestHelper restHelper = new RestHelper();

 //   Point p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        playlist_name=new ArrayList<String>();
        playlist_name.add("Playlist Name");
        lplaylists = (ListView) findViewById(R.id.playlists);

        refreshContent(getBaseContext());
        getUserIdentificationIfEmpty(getBaseContext());




      //  final Button show=(Button) findViewById(R.id.button1);
        //final EditText et=(EditText) findViewById(R.id.editText1);


        //add();

//        show.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//
//                li.add(et.getText().toString());
//                et.setText(null);
//                add();
//            }
//        });

        final Button btAddPlaylist = (Button) findViewById(R.id.add_playlist);
        btAddPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // btAddPlaylistClicked();
                Intent addPlaylistIntent = new Intent(HomePageActivity.this, AddPlaylist.class);
                HomePageActivity.this.startActivity(addPlaylistIntent);
            }
        });
    }



    public void add()
    {


//        lplaylists.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//                // TODO Auto-generated method stub
//                Toast.makeText(getBaseContext(), li.get(arg2),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void btAddPlaylistClicked(){

        Intent addPlaylistIntent = new Intent(this, AddPlaylist.class);
        startActivity(addPlaylistIntent);

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

       // String response = restHelper.receiveData(getBaseContext(),, Constants.FETCH_PLAYLIST);

        ArrayAdapter<String> adp=new ArrayAdapter<String>
                (getBaseContext(),R.layout.activity_list_plalylist,R.id.playlist_name,playlist_name);

        lplaylists.setAdapter(adp);

    }
}
