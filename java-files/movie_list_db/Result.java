package com.example.tweetreview_v2.movie_list_db;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Author: Sufyaan Siddique
 * GSON parsed class. Implements Parcelable.
 */

public class Result implements Parcelable {
    private int id;
    private boolean video;
    private int vote_count;
    private double vote_average;
    private String title;
    private String release_date;
    private String original_language;
    private String original_title;
    private ArrayList<Integer> genre_ids;
    private String backdrop_path;
    private boolean adult;
    private String overview;
    private String poster_path;
    private double popularity;
    private String media_type;

    protected Result(Parcel in) {
        id = in.readInt();
        video = in.readByte() != 0;
        vote_count = in.readInt();
        vote_average = in.readDouble();
        title = in.readString();
        release_date = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        genre_ids = in.readArrayList(Integer.class.getClassLoader());
        backdrop_path = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        poster_path = in.readString();
        popularity = in.readDouble();
        media_type = in.readString();
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public int getId() {
        return id;
    }

    public boolean isVideo() {
        return video;
    }

    public int getVote_count() {
        return vote_count;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getMedia_type() {
        return media_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(id);
        parcel.writeByte((byte) (video ? 1 : 0));
        parcel.writeInt(vote_count);
        parcel.writeDouble(vote_average);
        parcel.writeString(title);
        parcel.writeString(release_date);
        parcel.writeString(original_language);
        parcel.writeString(original_title);
        parcel.writeList(genre_ids);
        parcel.writeString(backdrop_path);
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeString(overview);
        parcel.writeString(poster_path);
        parcel.writeDouble(popularity);
        parcel.writeString(media_type);
    }
}
