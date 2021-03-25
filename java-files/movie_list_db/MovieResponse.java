package com.example.tweetreview_v2.movie_list_db;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author: Sufyaan Siddique
 * GSON parsed class.
 */

public class MovieResponse implements Serializable {
    public int page;
    public ArrayList<Result> results;
    public int total_pages;
    public int total_results;

    public int getPage() {
        return page;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }
}
