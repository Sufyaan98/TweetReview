package com.example.tweetreview_v2.tweet_media_tabs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tweetreview_v2.R;
import com.example.tweetreview_v2.adapter.MediaListAdapter;
import com.example.tweetreview_v2.singletons.TweetSet;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import twitter4j.MediaEntity;
import twitter4j.Status;

/**
 * Author: Sufyaan Siddique
 * This class displays any images from tweets gathered about a given movie.
 */

public class MediaTab extends Fragment {

    private ListView mediaListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        if (TweetSet.getInstance().getMovieName() == null) {
            view = inflater.inflate(R.layout.empty_layout, container, false);
        } else {
            view = inflater.inflate(R.layout.tab_media, container, false);
            mediaListView = view.findViewById(R.id.media_list);
        }

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final String searchQuery = TweetSet.getInstance().getMovieName();

        if (searchQuery == null) {

        } else {
            ArrayList<String> mediaUrls = new ArrayList<>();
            for (Status s : TweetSet.getInstance().getArray()) {
                MediaEntity[] mediaList = s.getMediaEntities();
                for (MediaEntity me : mediaList) {
                    mediaUrls.add(me.getMediaURLHttps());
                }
            }

            MediaListAdapter adapter = new MediaListAdapter(Objects.requireNonNull(getContext()), R.layout.media_image_adapter_layout, mediaUrls);
            mediaListView.setAdapter(adapter);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
