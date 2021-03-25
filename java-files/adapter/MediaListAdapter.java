package com.example.tweetreview_v2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tweetreview_v2.R;
import com.example.tweetreview_v2.singletons.TweetSet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import twitter4j.MediaEntity;
import twitter4j.Status;

/**
 * Author: Sufyaan Siddique
 *
 * ListView Custom ArrayAdapter for Media list in MediaTab showing any images
 * from tweets as well as the tweets they belong to.
 * Layout can be seen in media_image_adapter_layout.xml
 */

public class MediaListAdapter extends ArrayAdapter<String> {
    private Context context;
    private int resource;

    public MediaListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        ImageView imageView = convertView.findViewById(R.id.media_image_view);
        TextView mediaTweetView = convertView.findViewById(R.id.media_tweet_text);

        String url = getItem(position);
        String tweet = "";
        String originalTweet = "";

        ArrayList<Status> tweetList = TweetSet.getInstance().getArray();
        for (Status s : tweetList) {
            MediaEntity[] mediaList = s.getMediaEntities();
            for (int i = 0; i<mediaList.length; i++) {
                if (url.equals(mediaList[i].getMediaURLHttps())) {
                    tweet = s.getText().replaceAll("https://[^\\\\s]+", "");
                    tweet = tweet.replaceAll("(?m)^[ \t]*\r?\n", "");
                    originalTweet = tweet;
                }
            }
        }

        Picasso.get().load(url).into(imageView);
        mediaTweetView.setText(originalTweet);

        return convertView;
    }
}
