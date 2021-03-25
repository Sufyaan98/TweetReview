package com.example.tweetreview_v2.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tweetreview_v2.R;
import com.example.tweetreview_v2.movie_list_db.Result;
import com.example.tweetreview_v2.movie_search_db.ResultSearch;
import com.example.tweetreview_v2.youtube.Items;
import com.example.tweetreview_v2.youtube.YoutubeResponse;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.Nullable;

/**
 * Author: Sufyaan Siddique
 *
 * This class is an Activity that displays detailed movie information about
 * any given movie within the Trending movies list or from a search using the
 * Movie Database API. It shows overview, genres, trailer etc.
 */

public class MovieDetailActivity extends YouTubeBaseActivity {

    private TextView movieTitle;
    private TextView summaryInformation;
    private TextView overviewText;
    private ImageView posterView;

    private YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    private String apiKey = "AIzaSyDZ3amPDK63w9agqhlPg--f12WKA4zimAM";
    private String videoId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movie_view);
        int indicator = getIntent().getIntExtra("indicator", 0);
        int position = getIntent().getIntExtra("position", 0);

        if(indicator == 1) {
            ArrayList<Result> movie = getIntent().getParcelableArrayListExtra("movie titles");

            movieTitle = findViewById(R.id.movie_title);
            summaryInformation = findViewById(R.id.summary_information);
            overviewText = findViewById(R.id.overview_text);
            youTubePlayerView = findViewById(R.id.youtube_player_view);
            posterView = findViewById(R.id.poster_image);

            String posterPath = "https://image.tmdb.org/t/p/w500" + movie.get(position).getPoster_path();
            Picasso.get().load(posterPath).into(posterView);

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/octet-stream");
            RequestBody body = RequestBody.create(mediaType, "{}");
            String searchQuery = movie.get(position).getTitle();
            String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&key=AIzaSyDZ3amPDK63w9agqhlPg--f12WKA4zimAM&maxResults=1&type=video&q=" + searchQuery + " trailer";
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            client.newCall(request).enqueue(new Callback() {
                    @Override
                public void onFailure(Request request, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (response.isSuccessful()) {
                        Gson gson = new Gson();
                        YoutubeResponse youtubeResponse = gson.fromJson(response.body().string(), YoutubeResponse.class);
                        ArrayList<Items> items = youtubeResponse.getItems();
                        Items items1 = items.get(0);
                        videoId = items1.getId().getVideoId();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                                    @Override
                                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                                        Log.d("test", "Video loaded successfully");
                                        youTubePlayer.cueVideo(videoId);
                                    }

                                    @Override
                                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                                        Log.d("test", "Error occurred loading video");
                                    }
                                };

                                youTubePlayerView.initialize(apiKey, onInitializedListener);
                            }
                        });
                    }
                }
            });

            String title = movie.get(position).getTitle();
            String[] dates = movie.get(position).getRelease_date().split("-");
            String releaseYear = dates[0];
            String averageVote = movie.get(position).getVote_average() + "/10";
            ArrayList<Integer> genres = movie.get(position).getGenre_ids();
            String genreText = getGenres(genres);
            String summaryText = releaseYear + " | " + averageVote + " | " + genreText;
            String overview = movie.get(position).getOverview();

            movieTitle.setText(title);
            summaryInformation.setText(summaryText);
            overviewText.setText(overview);


        } else if (indicator == 2) {
            ArrayList<ResultSearch> movie = getIntent().getParcelableArrayListExtra("movie titles2");

            movieTitle = findViewById(R.id.movie_title);
            summaryInformation = findViewById(R.id.summary_information);
            overviewText = findViewById(R.id.overview_text);
            youTubePlayerView = findViewById(R.id.youtube_player_view);
            posterView = findViewById(R.id.poster_image);

            String posterPath = "https://image.tmdb.org/t/p/w500" + movie.get(position).getPoster_path();
            Picasso.get().load(posterPath).into(posterView);

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/octet-stream");
            RequestBody body = RequestBody.create(mediaType, "{}");
            String searchQuery = movie.get(position).getTitle();
            String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&key=AIzaSyDZ3amPDK63w9agqhlPg--f12WKA4zimAM&maxResults=1&type=video&q=" + searchQuery + " trailer";
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (response.isSuccessful()) {
                        Gson gson = new Gson();
                        YoutubeResponse youtubeResponse = gson.fromJson(response.body().string(), YoutubeResponse.class);
                        ArrayList<Items> items = youtubeResponse.getItems();
                        Items items1 = items.get(0);
                        videoId = items1.getId().getVideoId();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                                    @Override
                                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                                        Log.d("test", "Video loaded successfully");
                                        youTubePlayer.cueVideo(videoId);
                                    }

                                    @Override
                                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                                        Log.d("test", "Error occurred loading video");
                                    }
                                };

                                youTubePlayerView.initialize(apiKey, onInitializedListener);
                            }
                        });
                    }
                }
            });

            String title = movie.get(position).getTitle();
            String[] dates = movie.get(position).getRelease_date().split("-");
            String releaseYear = dates[0];
            String averageVote = movie.get(position).getVote_average() + "/10";
            ArrayList<Integer> genres = movie.get(position).getGenre_ids();
            String genreText = getGenres(genres);
            String summaryText = releaseYear + " | " + averageVote + " | " + genreText;
            String overview = movie.get(position).getOverview();

            movieTitle.setText(title);
            summaryInformation.setText(summaryText);
            overviewText.setText(overview);
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