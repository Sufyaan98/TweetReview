package com.example.tweetreview_v2.result_tab_fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tweetreview_v2.R;
import com.example.tweetreview_v2.movie_list_db.Result;
import com.example.tweetreview_v2.movie_search_db.ResultSearch;
import com.example.tweetreview_v2.singletons.MovieSet;
import com.example.tweetreview_v2.singletons.NLUSet;
import com.example.tweetreview_v2.singletons.TweetSet;
import com.ibm.watson.natural_language_understanding.v1.model.EntitiesResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Author: Sufyaan Siddique
 * Displays a summary of the chosen movie, shows the movie title, poster, summary information,
 * basic overview of the movie as well as the breakdown of keyword and review results.
 * Has links to other review sites.
 */

public class SummaryTab extends Fragment {

    private TextView movieTitle;
    private ImageView posterImage;
    private TextView summaryInformation;
    private TextView overviewText;
    private TextView posKeyInfo;
    private TextView negKeyInfo;
    private TextView posRevInfo;
    private TextView negRevInfo;
    private TextView ratingRevInfo;
    private Button imdbButton;
    private Button rtButton;
    private Button metaButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_summary, container, false);

        movieTitle = view.findViewById(R.id.movie_title);
        posterImage = view.findViewById(R.id.poster_image);
        summaryInformation = view.findViewById(R.id.summary_text);
        overviewText = view.findViewById(R.id.overview_text);
        posKeyInfo = view.findViewById(R.id.pos_key_info);
        negKeyInfo = view.findViewById(R.id.neg_key_info);
        posRevInfo = view.findViewById(R.id.pos_rev_info);
        negRevInfo = view.findViewById(R.id.neg_rev_info);
        ratingRevInfo = view.findViewById(R.id.rating_rev_info);
        imdbButton = view.findViewById(R.id.imdb_button);
        rtButton = view.findViewById(R.id.rt_button);
        metaButton = view.findViewById(R.id.meta_button);

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(MovieSet.getInstance().getArray().size() > 0) {
            ArrayList<Result> movieList = MovieSet.getInstance().getArray();
            ArrayList<EntitiesResult> entities = NLUSet.getInstance().getEntArray();
            int pos = MovieSet.getInstance().getPosition();
            int posSize = 0, negSize = 0, ratingSize = 0;

            for (EntitiesResult er : entities) {
                if (er.getType().equalsIgnoreCase("positive")) posSize++;
                else if (er.getType().equalsIgnoreCase("negative")) negSize++;
                else ratingSize++;
            }

            String title = movieList.get(pos).getTitle();
            String posterUrl = "https://image.tmdb.org/t/p/w500" + movieList.get(pos).getPoster_path();
            String[] dates = movieList.get(pos).getRelease_date().split("-");
            String releaseYear = dates[0];
            String averageVote = movieList.get(pos).getVote_average() + "/10";
            ArrayList<Integer> genres = movieList.get(pos).getGenre_ids();
            String genreText = getGenres(genres);
            String summaryText = releaseYear + " | " + averageVote + " | " + genreText;
            String overview = movieList.get(pos).getOverview();

            String posKeywords = NLUSet.getInstance().getPosKeyArray().size() + " Positives ✔";
            String posReviews = posSize + " Positives ✔";
            String negKeywords = NLUSet.getInstance().getNegKeyArray().size() + " Negatives ❌";
            String negReviews = negSize + " Negatives ❌";
            String ratings = ratingSize + " Ratings ✎";

            movieTitle.setText(title);
            Picasso.get().load(posterUrl).into(posterImage);
            summaryInformation.setText(summaryText);
            overviewText.setText(overview);
            posKeyInfo.setText(posKeywords);
            posKeyInfo.setTextColor(Color.GREEN);
            negKeyInfo.setText(negKeywords);
            negKeyInfo.setTextColor(Color.RED);
            posRevInfo.setText(posReviews);
            posRevInfo.setTextColor(Color.GREEN);
            negRevInfo.setText(negReviews);
            negKeyInfo.setTextColor(Color.RED);
            ratingRevInfo.setText(ratings);
            ratingRevInfo.setTextColor(Color.MAGENTA);

            imdbButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "https://www.google.com/search?q=" + TweetSet.getInstance().getMovieName() + " imdb reviews";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    Objects.requireNonNull(getContext()).startActivity(i);
                }
            });

            metaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String movieName = TweetSet.getInstance().getMovieName();
                    String url = "https://www.metacritic.com/movie/" + movieName + "metacritic reviews";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    Objects.requireNonNull(getContext()).startActivity(i);
                }
            });

            rtButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "https://www.google.com/search?q=" + TweetSet.getInstance().getMovieName() + " rotten tomatoes reviews";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    Objects.requireNonNull(getContext()).startActivity(i);
                }
            });

        } else if (MovieSet.getInstance().getResultSearchList().size() > 0) {
            ArrayList<ResultSearch> movieList = MovieSet.getInstance().getResultSearchList();
            ArrayList<EntitiesResult> entities = NLUSet.getInstance().getEntArray();
            int pos = MovieSet.getInstance().getPosition();
            int posSize = 0, negSize = 0, ratingSize = 0;

            for (EntitiesResult er : entities) {
                if (er.getType().equalsIgnoreCase("positive")) posSize++;
                else if (er.getType().equalsIgnoreCase("negative")) negSize++;
                else ratingSize++;
            }

            String title = movieList.get(pos).getTitle();
            String posterUrl = "https://image.tmdb.org/t/p/w500" + movieList.get(pos).getPoster_path();
            String[] dates = movieList.get(pos).getRelease_date().split("-");
            String releaseYear = dates[0];
            String averageVote = movieList.get(pos).getVote_average() + "/10";
            ArrayList<Integer> genres = movieList.get(pos).getGenre_ids();
            String genreText = getGenres(genres);
            String summaryText = releaseYear + " | " + averageVote + " | " + genreText;
            String overview = movieList.get(pos).getOverview();

            String posKeywords = NLUSet.getInstance().getPosKeyArray().size() + " Positives ✔";
            String posReviews = posSize + " Positives ✔";
            String negKeywords = NLUSet.getInstance().getNegKeyArray().size() + " Negatives ❌";
            String negReviews = negSize + " Negatives ❌";
            String ratings = ratingSize + " Ratings ✎";

            movieTitle.setText(title);
            Picasso.get().load(posterUrl).into(posterImage);
            summaryInformation.setText(summaryText);
            overviewText.setText(overview);
            posKeyInfo.setText(posKeywords);
            posKeyInfo.setTextColor(Color.GREEN);
            negKeyInfo.setText(negKeywords);
            negKeyInfo.setTextColor(Color.RED);
            posRevInfo.setText(posReviews);
            posRevInfo.setTextColor(Color.GREEN);
            negRevInfo.setText(negReviews);
            negKeyInfo.setTextColor(Color.RED);
            ratingRevInfo.setText(ratings);
            ratingRevInfo.setTextColor(Color.MAGENTA);

            imdbButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "https://www.google.com/search?q=" + TweetSet.getInstance().getMovieName() + " imdb reviews";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    Objects.requireNonNull(getContext()).startActivity(i);
                }
            });

            metaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String movieName = TweetSet.getInstance().getMovieName();
                    String url = "https://www.metacritic.com/movie/" + movieName + "metacritic reviews";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    Objects.requireNonNull(getContext()).startActivity(i);
                }
            });

            rtButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "https://www.google.com/search?q=" + TweetSet.getInstance().getMovieName() + " rotten tomatoes reviews";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    Objects.requireNonNull(getContext()).startActivity(i);
                }
            });
        }
    }

    private String getGenres(ArrayList<Integer> genres) {
        StringBuilder concat = new StringBuilder();
        for (Integer g : genres) {
            if (g == 28) concat.append(" Action");
            if (g == 12) concat.append(" Adventure");
            if (g == 16) concat.append(" Animation");
            if (g == 35) concat.append(" Comedy");
            if (g == 80) concat.append(" Crime");
            if (g == 99) concat.append(" Documentary");
            if (g == 18) concat.append(" Drama");
            if (g == 10751) concat.append(" Family");
            if (g == 36) concat.append(" History");
            if (g == 27) concat.append(" Horror");
            if (g == 10402) concat.append(" Music");
            if (g == 9648) concat.append(" Mystery");
            if (g == 10749) concat.append(" Romance");
            if (g == 878) concat.append(" Science Fiction");
            if (g == 10770) concat.append(" TV Movie");
            if (g == 53) concat.append(" Thriller");
            if (g == 10752) concat.append(" War");
            if (g == 37) concat.append(" Western");
        }

        return concat.toString();
    }
}
