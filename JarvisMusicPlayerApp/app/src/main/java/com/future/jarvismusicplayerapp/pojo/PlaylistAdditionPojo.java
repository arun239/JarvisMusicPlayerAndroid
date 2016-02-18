package com.future.jarvismusicplayerapp.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by arungu on 2/5/2016.
 */
public class PlaylistAdditionPojo {

    private String playlistName;
    private String playlistDescription;
    @JsonProperty("id")
    private String playlistId;

    public PlaylistAdditionPojo() {
    }

    public PlaylistAdditionPojo(String playlistName, String playlistDescription) {
        this.playlistName = playlistName;
        this.playlistDescription = playlistDescription;
    }


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

    @JsonIgnore                                                                 //or we can remove Setter for Id as we don't want to initialize it
    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }
}
