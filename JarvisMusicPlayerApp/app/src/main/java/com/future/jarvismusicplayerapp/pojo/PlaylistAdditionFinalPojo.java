package com.future.jarvismusicplayerapp.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by arungu on 2/5/2016.
 */
public class PlaylistAdditionFinalPojo {
    @JsonProperty("playlist")
    private PlaylistAdditionPojo playlistAdditionPojo;
    private String userEmail;

    public PlaylistAdditionFinalPojo(PlaylistAdditionPojo playlistAdditionPojo, String userEmail) {
        this.playlistAdditionPojo = playlistAdditionPojo;
        this.userEmail = userEmail;
    }

    public PlaylistAdditionPojo getPlaylistAdditionPojo() {
        return playlistAdditionPojo;
    }

    public void setPlaylistAdditionPojo(PlaylistAdditionPojo playlistAdditionPojo) {
        this.playlistAdditionPojo = playlistAdditionPojo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
