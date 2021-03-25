package com.example.tweetreview_v2.youtube;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author: Sufyaan Siddique
 * GSON parsed class.
 */

public class YoutubeResponse implements Serializable {
    public String kind;
    public String etag;
    public String nextPageToken;
    public String regionCode;
    public PageInfo pageInfo;
    public ArrayList<Items> items;

    public String getKind() {
        return kind;
    }

    public String getEtag() {
        return etag;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public ArrayList<Items> getItems() {
        return items;
    }
}
