package com.example.tweetreview_v2.search_tab_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.tweetreview_v2.R;
import com.example.tweetreview_v2.adapter.SearchListAdapter;
import com.example.tweetreview_v2.movie_search_db.ResultSearch;
import com.example.tweetreview_v2.movie_search_db.SearchResponse;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Author: Sufyaan Siddique
 * The class is responsible for handling search requests to the Movie Database API.
 * Executes a Callback() to create a background thread for network activity.
 * Displays the search results to the user.
 */

public class SearchTab extends Fragment {

    private EditText searchQueryView;
    private Button searchQueryButton;
    private ListView movieListView;
    private ArrayList<ResultSearch> movies;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_database_layout, container, false);

        searchQueryView = view.findViewById(R.id.search_query_text);
        searchQueryButton = view.findViewById(R.id.search_query_button);
        movieListView = view.findViewById(R.id.search_result_list);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        searchQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchQueryView.getText().toString();
                searchQueryView.setText("");
                String url = "https://api.themoviedb.org/3/search/movie?api_key=e8daa7d69d2e0e68985fc99244452299&language=en-US&page=1&include_adult=false&query=" + query;

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
                            SearchResponse sr = gson.fromJson(response.body().string(), SearchResponse.class);
                            movies = sr.getResults();
//                            movieTitles = new ArrayList<>();

                            final SearchListAdapter adapter1 = new SearchListAdapter(getContext(), R.layout.movie_details_adapter_layout, movies);

                            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    movieListView.setAdapter(adapter1);
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
