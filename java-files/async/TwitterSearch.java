package com.example.tweetreview_v2.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.tweetreview_v2.singletons.TweetSet;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Author: Sufyaan Siddique
 * Asynchronous Task handling the call to the Twitter search engine using Twitter4J
 * Stores list of tweets gathered based on some search query.
 * Filters explicit results where required.
 */

public class TwitterSearch extends AsyncTask<Void, Void, Void> {

    private Context context;
    public TwitterSearch(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        TweetSet.getInstance().getArray().clear();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("XAeGOt908DqvQFTsJUUprUX2W")
                .setOAuthConsumerSecret("UCZ6dy99CfRxuAAA5Ys5pc2aF2yXy3Xxr3T3Q2tpk8a8tdVLFI")
                .setOAuthAccessToken("1400185506-YgWCMHk7orPEsb00s1FLjOGQouYgG3c6lOhkejZ")
                .setOAuthAccessTokenSecret("SzPjkKISduZGR7yEnGNsToeRtQjcpsOIYf8I6WBef748P");

        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        Log.d("test", "Arrived in Twitter Search. Fetching Data...");

        List<twitter4j.Status> tweets = new ArrayList<>();
        String movieName = TweetSet.getInstance().getMovieName();
        Log.d("test", "Movie name: " + movieName);
        String sQuery = movieName + " movie was";
        Query query = new Query(sQuery + " -filter:retweets");
        query.setLang("en");
        query.setResultType(Query.RECENT);

        query.setCount(TweetSet.getInstance().getCount());

        QueryResult qr = null;

        try {
            qr = twitter.search(query);
            tweets = qr.getTweets();
        } catch (TwitterException te) {
            te.printStackTrace();
        }

        String filter = TweetSet.getInstance().getFilterExplicit();

        for (twitter4j.Status s : tweets) {
            if (filter.equalsIgnoreCase("yes")) {
                if (!checkTweet(s.getText())) {
                    TweetSet.getInstance().addToArray(s);
                }
            } else {
                TweetSet.getInstance().addToArray(s);
            }
        }
        Log.d("test", "Data Fetched. Size of Tweet Array: " + TweetSet.getInstance().getArray().size());

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("test", "Arrived at onPostExecute of TwitterSearch. Calling NLUWatson2.");
        new NLUWatson(context).execute();
    }

    private boolean checkTweet(String text) {
        String[] badWords = new String[] {"fuck", "fucking", "pissed", "shit"};
        if (StringUtils.indexOfAny(text, badWords) == -1) {
            return false;
        } else {
            return true;
        }
    }
}
