package com.example.tweetreview_v2.youtube;

/**
 * Author: Sufyaan Siddique
 * GSON parsed class.
 */

public class Snippet {
    public String publishedAt;
    public String channelId;
    public String title;
    public String description;
    public Thumbnails thumbnail;
    public String channelTitle;
    public String liveBroadcastContent;

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Thumbnails getThumbnail() {
        return thumbnail;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getLiveBroadcastContent() {
        return liveBroadcastContent;
    }
}
