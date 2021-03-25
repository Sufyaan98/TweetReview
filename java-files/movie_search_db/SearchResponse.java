package com.example.tweetreview_v2.movie_search_db;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author: Sufyaan Siddique
 * GSON parsed class.
 */

public class SearchResponse implements Serializable {
    private int page;
    private int total_results;
    private int total_pages;
    private ArrayList<ResultSearch> results;

    public int getPage() {
        return page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public ArrayList<ResultSearch> getResults() {
        return results;
    }
}
