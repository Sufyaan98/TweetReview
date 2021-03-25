package com.example.tweetreview_v2.search_tab_fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tweetreview_v2.R;
import com.example.tweetreview_v2.adapter.MovieListAdapter;
import com.example.tweetreview_v2.movie_list_db.MovieResponse;
import com.example.tweetreview_v2.movie_list_db.Result;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Author: Sufyaan Siddique
 * This class is responsible for displaying the trending movies list,
 * uses Callback() to run network activity on a background thread and load
 * the list.
 */

public class MovieTab extends Fragment {

    private TextView title;
    private ListView movieList;
    private Switch listSwitch;
    private ArrayList<Result> movieNames;
    private ArrayList<String> movieTitles;

    Context context;

    public MovieTab(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_search, container, false);

        title = view.findViewById(R.id.page_title);
        movieList = view.findViewById(R.id.movie_list);
        listSwitch = view.findViewById(R.id.list_switch);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);

        updateMovieList("day");

        listSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    listSwitch.setText("Week");
                    updateMovieList("week");
                } else {
                    listSwitch.setText("Day");
                    updateMovieList("day");
                }
            }
        });
    }

    private void updateMovieList(String type) {
        String url = "";
        if (type.equalsIgnoreCase("day")) {
            title.setText("Today's Top Movies");
            url = "https://api.themoviedb.org/3/trending/movie/day?api_key=e8daa7d69d2e0e68985fc99244452299";
        }
        else {
            title.setText("This Week's Top Movies");
            url = "https://api.themoviedb.org/3/trending/movie/week?api_key=e8daa7d69d2e0e68985fc99244452299";
        }

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, "{}");
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
                    MovieResponse mr = gson.fromJson(response.body().string(), MovieResponse.class);
                    movieNames = mr.getResults();
                    movieTitles = new ArrayList<>();
                    for (Result r : movieNames) movieTitles.add(r.getTitle());

                    final MovieListAdapter adapter = new MovieListAdapter(getContext(), R.layout.movie_details_adapter_layout, movieTitles,
                            movieNames);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            movieList.setAdapter(adapter);
                        }
                    });
                }
            }
        });
    }
}
