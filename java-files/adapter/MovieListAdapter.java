package com.example.tweetreview_v2.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tweetreview_v2.R;
import com.example.tweetreview_v2.activity.MovieDetailActivity;
import com.example.tweetreview_v2.async.TwitterSearch;
import com.example.tweetreview_v2.movie_list_db.Result;
import com.example.tweetreview_v2.singletons.MovieSet;
import com.example.tweetreview_v2.singletons.TweetSet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Author: Sufyaan Siddique
 *
 * ListView Custom ArrayAdapter for displaying each movie card as
 * can be seen in movie_details_adapter_layout.xml
 */

public class MovieListAdapter extends ArrayAdapter<String> {
    private Context context;
    private int resource;
    private List<String> movieTitles;
    private List<Result> movies;

    public MovieListAdapter(@NonNull Context context, int resource, @NonNull List<String> movieTitles,
                            List<Result> movies) {
        super(context, resource, movieTitles);
        this.movieTitles = movieTitles;
        this.context = context;
        this.resource = resource;
        this.movies = movies;
    }

    @SuppressLint({"ClickableViewAccessibility", "ViewHolder"})
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        final ImageView posterImageview = convertView.findViewById(R.id.poster_image_view);
        TextView movieView = convertView.findViewById(R.id.movie_title_text);
        TextView releaseDateView = convertView.findViewById(R.id.release_date_text);
        TextView averageVoteView = convertView.findViewById(R.id.average_vote_text);
        Button searchGoogleButton = convertView.findViewById(R.id.search_google_button);
        Button infoButton = convertView.findViewById(R.id.info_button);
        Button findTweetsButton = convertView.findViewById(R.id.find_tweets_button);
        RatingBar ratingBar = convertView.findViewById(R.id.rating_bar);

        final LinearLayout dropdown = convertView.findViewById(R.id.dropdown_layout);
        final Spinner tweetCountSpinner = convertView.findViewById(R.id.tweet_size_spinner);
        final Spinner filterSpinner = convertView.findViewById(R.id.filter_spinner);
        Button goButton = convertView.findViewById(R.id.go_button);

        ArrayList<String> tweetArray = new ArrayList<>();
        tweetArray.add("Tweet Size");
        tweetArray.add("5");
        tweetArray.add("10");
        tweetArray.add("15");
        tweetArray.add("20");
        tweetArray.add("25");
        tweetArray.add("30");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, tweetArray);
        tweetCountSpinner.setAdapter(adapter);

        ArrayList<String> filterArray = new ArrayList<>();
        filterArray.add("Filter explicit?");
        filterArray.add("No");
        filterArray.add("Yes");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), R.layout.spinner_item, filterArray);
        filterSpinner.setAdapter(adapter2);

        String posterUrl = "";
        if (movies.get(position).getPoster_path() == null) {

        } else {
            posterUrl = "https://image.tmdb.org/t/p/w342" + movies.get(position).getPoster_path();
            Picasso.get().load(posterUrl).into(posterImageview);
        }
        String movieName = getItem(position);
        String releaseDate = "Release Date: " + movies.get(position).getRelease_date();
        String averageVote = "Average Vote: " + movies.get(position).getVote_average();

        movieView.setText(movieName);
        releaseDateView.setText(releaseDate);
        averageVoteView.setText(averageVote);
        ratingBar.setMax(5);
        ratingBar.setStepSize((float)0.1);
        ratingBar.setIsIndicator(true);
        ratingBar.setRating((float) movies.get(position).getVote_average() / 2);

        posterImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_image);
                ImageView imageView = dialog.findViewById(R.id.media_image);
                String posterUrl = "https://image.tmdb.org/t/p/original" + movies.get(position).getPoster_path();
                Picasso.get().load(posterUrl).into(imageView);
                dialog.show();
            }
        });

        searchGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.google.com/search?q=" + movieTitles.get(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putParcelableArrayListExtra("movie titles", (ArrayList<? extends Parcelable>) movies);
                intent.putExtra("indicator", 1);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

        findTweetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropdown.setVisibility(View.VISIBLE);
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetCount = tweetCountSpinner.getSelectedItem().toString();
                String filter = filterSpinner.getSelectedItem().toString();

                if (tweetCount.equals("Tweet Size")) {
                    Toast.makeText(getContext(), "Please select number of tweets.", Toast.LENGTH_SHORT).show();
                } else if (filter.equals("Filter?")) {
                    Toast.makeText(getContext(), "Please state filter type.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("test", "Arrived at SearchFragment Listener. Calling Twitter Search");
                    TweetSet.getInstance().setCount(Integer.parseInt(tweetCount));
                    TweetSet.getInstance().setFilterExplicit(filter);

                    String movieName = "";
                    if (movieTitles.get(position).matches(".*\\d.*")) {
                        movieName = movieTitles.get(position).toLowerCase();
                    } else {
                        movieName = movieTitles.get(position).replaceAll("[^a-zA-Z ]", "").toLowerCase();
                    }
                    TweetSet.getInstance().setMovieName(movieName, movieTitles.get(position));

                    MovieSet.getInstance().setArray((ArrayList<Result>) movies, position);
                    new TwitterSearch(context).execute();

                    Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.loading_dialog);
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }
        });

        return convertView;
    }
}
