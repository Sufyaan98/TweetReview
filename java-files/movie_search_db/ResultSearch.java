package com.example.tweetreview_v2.movie_search_db;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Author: Sufyaan Siddique
 * GSON parsed class. Implements Parcelable.
 */

public class ResultSearch implements Parcelable {
    private double popularity;
    private int vote_count;
    private boolean video;
    private String poster_path;
    private int id;
    private boolean adult;
    private String backdrop_path;
    private String original_language;
    private String original_title;
    private ArrayList<Integer> genre_ids;
    private String title;
    private double vote_average;
    private String overview;
    private String release_date;

    protected ResultSearch(Parcel in) {
        popularity = in.readDouble();
        vote_count = in.readInt();
        video = in.readByte() != 0;
        poster_path = in.readString();
        id = in.readInt();
        adult = in.readByte() != 0;
        backdrop_path = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        genre_ids = in.readArrayList(Integer.class.getClassLoader());
        title = in.readString();
        vote_average = in.readDouble();
        overview = in.readString();
        release_date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(popularity);
        dest.writeInt(vote_count);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeString(poster_path);
        dest.writeInt(id);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(backdrop_path);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeList(genre_ids);
        dest.writeString(title);
        dest.writeDouble(vote_average);
        dest.writeString(overview);
        dest.writeString(release_date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResultSearch> CREATOR = new Creator<ResultSearch>() {
        @Override
        public ResultSearch createFromParcel(Parcel in) {
            return new ResultSearch(in);
        }

        @Override
        public ResultSearch[] newArray(int size) {
            return new ResultSearch[size];
        }
    };

    public double getPopularity() {
        return popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public int getId() {
        return id;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
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

    public String getTitle() {
        return title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }
}
