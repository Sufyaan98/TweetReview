package com.example.tweetreview_v2.result_tab_fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tweetreview_v2.R;
import com.example.tweetreview_v2.adapter.ReviewListAdapter;
import com.example.tweetreview_v2.singletons.NLUSet;
import com.example.tweetreview_v2.singletons.TweetSet;
import com.ibm.watson.natural_language_understanding.v1.model.EntitiesResult;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import twitter4j.Status;

/**
 * Author: Sufyaan Siddique
 * Responsible for displaying the list of reviews by using the NLU features.
 */

public class ReviewTab extends Fragment {

    private ListView reviewList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_review, container, false);

        reviewList = view.findViewById(R.id.review_list);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<EntitiesResult> entitiesResults = NLUSet.getInstance().getEntArray();
        final ArrayList<EntitiesResult> pos_reviews = new ArrayList<>();
        final ArrayList<EntitiesResult> neg_reviews = new ArrayList<>();
        final ArrayList<EntitiesResult> ratings = new ArrayList<>();
        final ArrayList<EntitiesResult> allReviews = new ArrayList<>();

        for (EntitiesResult er : entitiesResults) {
            if (er.getType().equalsIgnoreCase("positive")) pos_reviews.add(er);
            else if (er.getType().equalsIgnoreCase("negative")) neg_reviews.add(er);
            else if (er.getType().equalsIgnoreCase("rating")) ratings.add(er);
        }

        allReviews.addAll(ratings);
        allReviews.addAll(pos_reviews);
        allReviews.addAll(neg_reviews);

        ReviewListAdapter adapter = new ReviewListAdapter(getContext(), R.layout.keyword_review_list_adapter_layout, allReviews, pos_reviews.size(), ratings.size());
        reviewList.setAdapter(adapter);

        reviewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String keyword = allReviews.get(i).getText();
                String originTweet = "";

                for (Status s : TweetSet.getInstance().getArray()) {
                    if (s.getText().toLowerCase().contains(keyword.toLowerCase())) {
                        originTweet = s.getText();
                        break;
                    }
                }

                int start = originTweet.indexOf(keyword);
                int end = start + keyword.length();

                Dialog dialog = new Dialog(Objects.requireNonNull(getContext()));
                dialog.setContentView(R.layout.dialog);
                TextView textView = dialog.findViewById(R.id.dialog_text);

                SpannableString str = new SpannableString(originTweet);
                str.setSpan(new BackgroundColorSpan(Color.YELLOW), start, end, 0);
                textView.setText(str);
                dialog.show();
            }
        });
    }
}
