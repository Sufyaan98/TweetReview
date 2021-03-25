package com.example.tweetreview_v2.tweet_media_tabs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tweetreview_v2.R;
import com.example.tweetreview_v2.adapter.TweetListAdapter;
import com.example.tweetreview_v2.singletons.NLUSet;
import com.example.tweetreview_v2.singletons.TweetSet;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import twitter4j.Status;

/**
 * Author: Sufyaan Siddique
 * Displays all tweets gathered about a certain movie.
 */

public class TweetTab extends Fragment {

    private TextView movieTitle;
    private ListView tweetsList;
    private ArrayList<String> tweet_array;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_tweets, container, false);

        movieTitle = view.findViewById(R.id.movie_name_text);
        tweetsList = view.findViewById(R.id.tweet_list);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String movieName = TweetSet.getInstance().getMovieNameRaw() + " - Tweet Reviews:";
        movieTitle.setText(movieName);

        tweet_array = new ArrayList<>();
        for (Status s : TweetSet.getInstance().getArray()) {
            String tweet = s.getText();
            tweet = tweet.replaceAll("https://[^\\\\s]+", "");
            tweet = tweet.replaceAll("(?m)^[ \t]*\r?\n", "");
            tweet_array.add(tweet);
        }

        TweetListAdapter adapter = new TweetListAdapter(getContext(), R.layout.tweet_list_adapter_layout, TweetSet.getInstance().getArray(), NLUSet.getInstance().getSentArray());
        tweetsList.setAdapter(adapter);

        tweetsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String user = TweetSet.getInstance().getArray().get(i).getUser().getScreenName();
                long id = TweetSet.getInstance().getArray().get(i).getId();

                String url = "https://twitter.com/" + user + "/status/" + id;

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                Objects.requireNonNull(getContext()).startActivity(intent);

                return true;
            }
        });
    }
}
