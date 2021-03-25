package com.example.tweetreview_v2.singletons;

import com.ibm.watson.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.natural_language_understanding.v1.model.EntitiesResult;
import com.ibm.watson.natural_language_understanding.v1.model.KeywordsResult;
import com.ibm.watson.natural_language_understanding.v1.model.SentimentResult;

import java.util.ArrayList;

/**
 * Author: Sufyaan Siddique
 * Singleton class to hold a number of ArrayLists for each NLU feature -
 * Entities, Keywords and Sentiment.
 */

public class NLUSet {
    private static NLUSet instance;
    private ArrayList<AnalysisResults> analysisList = null;
    private ArrayList<SentimentResult> sentList = null;
    private ArrayList<KeywordsResult> posKeyList = null;
    private ArrayList<KeywordsResult> negKeyList = null;
    private ArrayList<EntitiesResult> entList = null;
    private ArrayList<EntitiesResult> personList = null;

    public static NLUSet getInstance() {
        if(instance == null) {
            instance = new NLUSet();
        }
        return instance;
    }

    private NLUSet() {
        analysisList = new ArrayList<>();
        sentList = new ArrayList<>();
        posKeyList = new ArrayList<>();
        negKeyList = new ArrayList<>();
        entList = new ArrayList<>();
        personList = new ArrayList<>();
    }

    public void clearArray() {
        analysisList.clear();
        sentList.clear();
        posKeyList.clear();
        negKeyList.clear();
        entList.clear();
        personList.clear();
    }

    public void addToAnalysisArray(AnalysisResults ar) {
        analysisList.add(ar);
    }

    public void addToSentimentArray (SentimentResult sr) {
        sentList.add(sr);
    }

    public void addToPosKeyArray (KeywordsResult kr) {
        posKeyList.add(kr);
    }

    public void addToNegKeyArray (KeywordsResult kr) {
        negKeyList.add(kr);
    }

    public void addToEntityArray (EntitiesResult er) {
        entList.add(er);
    }

    public void addToPersonArray (EntitiesResult er) {
        personList.add(er);
    }

    public ArrayList<AnalysisResults> getAnalysisArray() {
        return this.analysisList;
    }

    public ArrayList<SentimentResult> getSentArray() {
        return this.sentList;
    }

    public ArrayList<KeywordsResult> getPosKeyArray() {
        return this.posKeyList;
    }

    public ArrayList<KeywordsResult> getNegKeyArray() {
        return this.negKeyList;
    }

    public ArrayList<EntitiesResult> getEntArray() {
        return this.entList;
    }

    public ArrayList<EntitiesResult> getPersonArray() {
        return this.personList;
    }

}
