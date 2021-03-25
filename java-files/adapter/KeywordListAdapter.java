package com.example.tweetreview_v2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tweetreview_v2.R;
import com.ibm.watson.natural_language_understanding.v1.model.KeywordsResult;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Author: Sufyaan Siddique
 *
 * ListView custom ArrayAdapter for Keywords list in KeywordTab
 * showing keyword text, relevancy and positive/negative
 * Layout can be seen in keyword_review_list_adapter_layout.xml
 */

public class KeywordListAdapter extends ArrayAdapter<KeywordsResult> {

    private Context context;
    private int resource;
    private int posSize;

    public KeywordListAdapter(@NonNull Context context, int resource, @NonNull List<KeywordsResult> objects, int posSize) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.posSize = posSize;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView itemTextView = convertView.findViewById(R.id.item_text);
        TextView relevancyTextView = convertView.findViewById(R.id.relevancy_text);
        TextView posNegView = convertView.findViewById(R.id.pos_or_neg_text);

        String itemText = Objects.requireNonNull(getItem(position)).getText();
        double relevancy = Math.round(Objects.requireNonNull(getItem(position)).getRelevance() * 100);
        String relevancyText = "Relevancy: " + relevancy;
        String posNeg = "";
        if (position < posSize) {
            posNeg = "✔ Positive";
            posNegView.setTextColor(Color.GREEN);
            posNegView.setText(posNeg);
        } else {
            posNeg = " ❌ Negative";
            posNegView.setTextColor(Color.RED);
            posNegView.setText(posNeg);
        }


        itemTextView.setText(itemText);
        relevancyTextView.setText(relevancyText);

        return convertView;
    }
}
