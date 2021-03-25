package com.example.tweetreview_v2.singletons;

import java.util.ArrayList;

import twitter4j.MediaEntity;
import twitter4j.Status;

/**
 * Author: Sufyaan Siddique
 * Singleton class to hold ArrayList of tweets, as well as count of how many tweets gathered,
 * filtered explicit string etc. Is used throughout the project.
 */

public class TweetSet {
    private static TweetSet instance;
    private ArrayList<Status> tweetList = null;
    private ArrayList<Status> tweetList2 = null;
    private ArrayList<MediaEntity> media = null;
    private String movieName;
    private String movieNameRaw;
    private int count;
    private String resultType;
    private String filterExplicit;

    public static TweetSet getInstance() {
        if (instance == null) {
            instance = new TweetSet();
        }
        return instance;
    }

    private TweetSet() {
        tweetList = new ArrayList<>();
        tweetList2 = new ArrayList<>();
        media = new ArrayList<>();
    }

    public ArrayList<Status> getArray() {
        return this.tweetList;
    }

    public void addToArray (Status s) {
        tweetList.add(s);
    }

    public ArrayList<Status> getArray2() {
        return this.tweetList2;
    }

    public void addToArray2 (Status s) {
        tweetList2.add(s);
    }

    public ArrayList<MediaEntity> getMediaArray() {
        return this.media;
    }

    public void addToMedia (MediaEntity me) {
        media.add(me);
    }

    public void setMovieName (String movie, String movieNameRaw) {
        this.movieName = movie;
        this.movieNameRaw = movieNameRaw;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getMovieNameRaw() {
        return this.movieNameRaw;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFilterExplicit() {
        return filterExplicit;
    }

    public void setFilterExplicit(String filterExplicit) {
        this.filterExplicit = filterExplicit;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
}
