package com.example.tweetreview_v2.async;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.tweetreview_v2.activity.BottomNavActivity;
import com.example.tweetreview_v2.singletons.NLUSet;
import com.example.tweetreview_v2.singletons.TweetSet;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.natural_language_understanding.v1.model.EntitiesResult;
import com.ibm.watson.natural_language_understanding.v1.model.Features;
import com.ibm.watson.natural_language_understanding.v1.model.KeywordsOptions;
import com.ibm.watson.natural_language_understanding.v1.model.KeywordsResult;
import com.ibm.watson.natural_language_understanding.v1.model.SentimentOptions;

/**
 * Author: Sufyaan Siddique
 * Asynchronous Task handling the call to the Watson NLU service
 * Analyses tweets gathered from TwitterSearch and sorts into seperate
 * ArrayLists.
 */

public class NLUWatson extends AsyncTask<Void, Void, Void> {

    Context context;

    public NLUWatson(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        NLUSet.getInstance().clearArray();

        IamAuthenticator authenticator = new IamAuthenticator("Y4UoqOSbe34i93x0TH9sZUCTjQr3V36AT5W6Nu_6WzZ5");
        NaturalLanguageUnderstanding naturalLanguageUnderstanding = new NaturalLanguageUnderstanding("2019-07-12", authenticator);
        naturalLanguageUnderstanding.setServiceUrl("https://api.eu-gb.natural-language-understanding.watson.cloud.ibm.com/instances/b28d49ba-2c2c-4ce2-9531-1a6e045a2479");

        KeywordsOptions keywords = new KeywordsOptions.Builder()
                .emotion(true)
                .sentiment(true)
                .limit(4)
                .build();

        SentimentOptions sentiment = new SentimentOptions.Builder()
                .build();

        EntitiesOptions entitiesCustom = new EntitiesOptions.Builder()
                .model("139db1b9-428e-4067-ab0c-0a0d7cf8f42c")
                .sentiment(true)
                .emotion(true)
                .limit(4)
                .build();

        Features features = new Features.Builder()
                .keywords(keywords)
                .entities(entitiesCustom)
                .sentiment(sentiment)
                .build();

        for (int i = 0; i< TweetSet.getInstance().getArray().size(); i++) {
            Log.d("test", "Watson received data (" + (i+1) + ")");
            String tweet = TweetSet.getInstance().getArray().get(i).getText();
            tweet = tweet.replaceAll("https://[^\\\\s]+", "");
            tweet = tweet.replaceAll("(?m)^[ \t]*\r?\n", "");

            AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                    .text(tweet)
                    .features(features)
                    .returnAnalyzedText(true)
                    .language("en")
                    .build();

            AnalysisResults response = naturalLanguageUnderstanding
                    .analyze(parameters)
                    .execute()
                    .getResult();

            NLUSet.getInstance().addToAnalysisArray(response);
        }
        Log.d("test", "Size of NLU Analysis Array: " + NLUSet.getInstance().getAnalysisArray().size());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.d("test", "Arrived at onPostExecute NLUWatson. Calling NLUWatsonPerson.");
        super.onPostExecute(aVoid);

        String movieName = TweetSet.getInstance().getMovieName().toLowerCase();
        int posEnt = 0;
        int negEnt = 0;
        boolean isPositive = false;

        for (AnalysisResults a : NLUSet.getInstance().getAnalysisArray()) {
            NLUSet.getInstance().addToSentimentArray(a.getSentiment());

            for (EntitiesResult er : a.getEntities()) {
                if (er.getType().equals("POSITIVE")) posEnt++;
                else if (er.getType().equals("NEGATIVE")) negEnt++;
                NLUSet.getInstance().addToEntityArray(er);
            }

            if (posEnt > negEnt) isPositive = true;

            for (KeywordsResult kr : a.getKeywords()) {
                if(kr.getText().toLowerCase().equals(movieName)) {}
                else if (kr.getText().toLowerCase().contains(movieName)) {}
                else if (movieName.contains(kr.getText().toLowerCase())) {}
                else {
                    if (kr.getRelevance() > 0.7) {
                        if (kr.getSentiment().getScore() > 0) NLUSet.getInstance().addToPosKeyArray(kr);
                        else NLUSet.getInstance().addToNegKeyArray(kr);
                    }
                }
            }
        }
        context.startActivity(new Intent(context, BottomNavActivity.class).putExtra("FromWatson", "1"));
    }
}
