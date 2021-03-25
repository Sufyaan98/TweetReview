package com.example.tweetreview_v2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tweetreview_v2.R;
import com.ibm.watson.natural_language_understanding.v1.model.SentimentResult;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import twitter4j.Status;

/**
 * Author: Sufyaan Siddique
 * ListView Custom ArrayAdapter for tweets list in the TweetsTab.
 * Displays the tweet text along with its favourite & retweet count,
 * and date of publication.
 * Layout can be seen in tweet_list_adapter_layout.xml
 */

public class TweetListAdapter extends ArrayAdapter<Status> {

    private Context context;
    private int resource;
    private List<SentimentResult> sentList;

    public TweetListAdapter(@NonNull Context context, int resource, @NonNull List<Status> objects, List<SentimentResult> sentList) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.sentList = sentList;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        String tweet = getItem(position).getText().replaceAll("https://[^\\\\s]+", "");

        String favourite = "Favourites: " + getItem(position).getFavoriteCount();
        String retweet = "Retweets: " + getItem(position).getRetweetCount();
        String[] dateArray = getItem(position).getCreatedAt().toGMTString().split(" ");
        String date = dateArray[0] + " " + dateArray[1] + " " + dateArray[2];

        TextView tweet_view = convertView.findViewById(R.id.tweet_text);
        TextView favourite_view = convertView.findViewById(R.id.fav_text);
        TextView retweet_view = convertView.findViewById(R.id.ret_text);
        TextView date_view = convertView.findViewById(R.id.date_text);

        tweet_view.setText(tweet);
        favourite_view.setText(favourite);
        retweet_view.setText(retweet);
        date_view.setText(date);
        return convertView;
    }
}
