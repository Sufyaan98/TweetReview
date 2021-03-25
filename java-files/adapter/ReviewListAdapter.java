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
import com.ibm.watson.natural_language_understanding.v1.model.EntitiesResult;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Author: Sufyaan Siddique
 *
 * ListView Custom ArrayAdapter for the reviews list in ReviewTab, showing
 * the review text, confidence score and either positive/negative/rating
 * Layout can be seen in keyword_review_list_adapter_layout.xml
 */

public class ReviewListAdapter extends ArrayAdapter<EntitiesResult> {

    private Context context;
    private int resource;
    private int posSize;
    private int ratingSize;

    public ReviewListAdapter(@NonNull Context context, int resource, @NonNull List<EntitiesResult> objects, int posSize, int ratingSize) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.posSize = posSize;
        this.ratingSize = ratingSize;
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

        String itemText = getItem(position).getText();
        double relevancy = Math.round(getItem(position).getConfidence() * 100);
        String relevancyText = "Confidence: " + relevancy;
        String posNeg = "";
        if(position < ratingSize) {
            posNeg = "✎ Rating";
            posNegView.setTextColor(Color.MAGENTA);
            posNegView.setText(posNeg);
        } else if (position > (ratingSize -1) && position <= (ratingSize + posSize -1)) {
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
