package com.example.tweetreview_v2.singletons;

import com.example.tweetreview_v2.movie_list_db.Result;
import com.example.tweetreview_v2.movie_search_db.ResultSearch;

import java.util.ArrayList;

/**
 * Author: Sufyaan Siddique
 * Singleton class which holds an ArrayList of type Result or ResultSearch (trending movies list
 * or search list). The ArrayList is used for the SummaryTab to display movie information.
 */

public class MovieSet {
    private static MovieSet instance;
    private ArrayList<Result> resultList;
    private ArrayList<ResultSearch> resultSearchList;
    private int position;

    public static MovieSet getInstance() {
        if (instance == null) {
            instance = new MovieSet();
        }
        return instance;
    }

    private MovieSet() {
        resultList = new ArrayList<>();
        resultSearchList = new ArrayList<>();
    }

    public void clear() {
        resultSearchList.clear();
        resultList.clear();
    }

    public ArrayList<Result> getArray() {
        return this.resultList;
    }

    public void setArray (ArrayList<Result> l, int pos) {
        resultList = l;
        position = pos;
    }

    public ArrayList<ResultSearch> getResultSearchList() {
        return resultSearchList;
    }

    public void setResultSearchList(ArrayList<ResultSearch> resultSearchList, int pos) {
        this.resultSearchList = resultSearchList;
        position = pos;
    }

    public int getPosition() {
        return this.position;
    }
}
