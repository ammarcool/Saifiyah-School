package com.ammar.saifiyahschool.Gallery;

public class albumNamesData {

    String albumName;
    String albumItemCount;
    String albumImageURL;

    public albumNamesData(String albumName, String albumItemCount, String albumImageURL) {
        this.albumName = albumName;
        this.albumItemCount = albumItemCount;
        this.albumImageURL = albumImageURL;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumItemCount() {
        return albumItemCount;
    }

    public void setAlbumItemCount(String albumItemCount) {
        this.albumItemCount = albumItemCount;
    }

    public String getAlbumImageURL() {
        return albumImageURL;
    }

    public void setAlbumImageURL(String albumImageURL) {
        this.albumImageURL = albumImageURL;
    }
}
