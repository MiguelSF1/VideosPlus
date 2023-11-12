package com.example.videosplus.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieVersion {
    @SerializedName("version_id")
    @Expose
    private Integer versionId;

    @SerializedName("movie_id")
    @Expose
    private Integer movieId;

    @SerializedName("movie_format")
    @Expose
    private String movieFormat;

    @SerializedName("movie_resolution")
    @Expose
    private String movieResolution;

    @SerializedName("movie_link")
    @Expose
    private String movieLink;

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieFormat() {
        return movieFormat;
    }

    public void setMovieFormat(String movieFormat) {
        this.movieFormat = movieFormat;
    }

    public String getMovieResolution() {
        return movieResolution;
    }

    public void setMovieResolution(String movieResolution) {
        this.movieResolution = movieResolution;
    }

    public String getMovieLink() {
        return movieLink;
    }

    public void setMovieLink(String movieLink) {
        this.movieLink = movieLink;
    }
}