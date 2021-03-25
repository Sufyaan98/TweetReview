package com.example.tweetreview_v2.result_tab_fragments;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tweetreview_v2.R;
import com.example.tweetreview_v2.adapter.KeywordListAdapter;
import com.example.tweetreview_v2.singletons.NLUSet;
import com.example.tweetreview_v2.singletons.TweetSet;
import com.ibm.watson.natural_language_understanding.v1.model.KeywordsResult;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import twitter4j.Status;

/**
 * Author: Sufyaan Siddique
 * Responsible for displaying the list of Keywords to the user as well as Emotion breakdown
 * by using the NLU features.
 */

public class KeywordTab extends Fragment {

    private ListView keywordList;
    private TextView joyTextView, fearTextView, sadnessTextView;
    private ProgressBar joyProgressBar, fearProgressBar, sadnessProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_keyword, container, false);

        keywordList = view.findViewById(R.id.keyword_review_list);
        joyTextView = view.findViewById(R.id.joy_text);
        fearTextView = view.findViewById(R.id.fear_text);
        sadnessTextView = view.findViewById(R.id.sadness_text);
        joyProgressBar = view.findViewById(R.id.joy_progress_bar);
        fearProgressBar = view.findViewById(R.id.anger_progress_bar);
        sadnessProgressBar = view.findViewById(R.id.sadness_progress_bar);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ArrayList<KeywordsResult> posKeys = NLUSet.getInstance().getPosKeyArray();
        final ArrayList<KeywordsResult> negKeys = NLUSet.getInstance().getNegKeyArray();

        final ArrayList<KeywordsResult> allKeys = new ArrayList<>();
        allKeys.addAll(posKeys);
        allKeys.addAll(negKeys);

        KeywordListAdapter adapter = new KeywordListAdapter(getContext(), R.layout.keyword_review_list_adapter_layout, allKeys, posKeys.size());
        keywordList.setAdapter(adapter);

        double joy = 0, fear = 0, sadness = 0;
        for (KeywordsResult kr : allKeys) {
            joy+=kr.getEmotion().getJoy();
            fear+=kr.getEmotion().getFear();
            sadness+=kr.getEmotion().getSadness();
        }
        Log.d("test", ""+allKeys.size());
        joy = Math.round(joy / allKeys.size() * 100);
        fear = Math.round(fear / allKeys.size() * 100);
        sadness = Math.round(sadness / allKeys.size() * 100);


        joyProgressBar.setProgress((int) joy);
        if (joy >= 65) joyProgressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        else if (joy < 65 && joy > 25) joyProgressBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        else joyProgressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));

        fearProgressBar.setProgress((int) fear);
        if (fear > 65) fearProgressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        else if (fear < 65 && fear > 25) fearProgressBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        else fearProgressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));

        sadnessProgressBar.setProgress((int) sadness);
        if (sadness > 65) sadnessProgressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        else if (sadness < 65 && sadness > 25) sadnessProgressBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        else sadnessProgressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));

        joyTextView.setText(joy + "%");
        fearTextView.setText(fear + "%");
        sadnessTextView.setText(sadness + "%");

        keywordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String keyword = allKeys.get(i).getText();
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
