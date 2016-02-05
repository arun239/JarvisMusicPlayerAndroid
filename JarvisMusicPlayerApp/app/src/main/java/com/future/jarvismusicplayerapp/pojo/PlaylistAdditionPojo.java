package com.future.jarvismusicplayerapp.pojo;

/**
 * Created by arungu on 2/5/2016.
 */
public class PlaylistAdditionPojo {

    public PlaylistAdditionPojo() {
    }

    public PlaylistAdditionPojo(String playlistName, String playlistDescription) {
        this.playlistName = playlistName;
        this.playlistDescription = playlistDescription;
    }

    private String playlistName;
    private String playlistDescription;

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getPlaylistDescription() {
        return playlistDescription;
    }

    public void setPlaylistDescription(String playlistDescription) {
        this.playlistDescription = playlistDescription;
    }
}
