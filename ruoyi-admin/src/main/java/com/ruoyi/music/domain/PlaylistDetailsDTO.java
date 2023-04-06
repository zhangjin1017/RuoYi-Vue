package com.ruoyi.music.domain;


public class PlaylistDetailsDTO {
    String userId;
    String musicId;
    String name;
    String artists;
    String artistsId;
    String img;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getArtistsId() {
        return artistsId;
    }

    public void setArtistsId(String artistsId) {
        this.artistsId = artistsId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
