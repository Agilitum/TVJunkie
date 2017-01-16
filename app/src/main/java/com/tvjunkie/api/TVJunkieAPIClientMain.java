package com.tvjunkie.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tvjunkie.ui.trendingMoviesRecyclerView.adapter.model.Movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Pair;

/**
 * Created by ludwig on 14/01/17.
 */

public class TVJunkieAPIClientMain {

	private String apiKeyTraktTv = "0e7e55d561c7e688868a5ea7d2c82b17e59fde95fbc2437e809b1449850d4162";
	private String apiKeyTMDB = "6fc6ba86f5780acf282eb66547b75cda";

	String urlTraktTv = "https://api.trakt.tv";
	String urlTMDB = "https://api.themoviedb.org/3/movie/";

	String PREFS_FILENAME = "com.tvjunkie.sharedPreferences";
	SharedPreferences tvJunkieSharedPreferences;
	SharedPreferences.Editor editor;

	Context mContext;

	private List<Pair<String, String>> params;


	public TVJunkieAPIClientMain(Context context){
		mContext = context;
	}

	public void getTrendingMovies(){
		RequestQueue queue = Volley.newRequestQueue(mContext);

		StringRequest trendingMoviesRequest = new StringRequest(com.android.volley.Request.Method
			.GET, urlTraktTv + "/movies/trending", new com.android.volley.Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				addToSharedPreferences("KEY_MOVIES_TRENDING", response);
				trendingMoviesListChanged();
			}
		}, new com.android.volley.Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				//TODO: show toast for error message
			}
		})
		{
			@Override
			public Map<String, String> getHeaders() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Content-type", "application/json");
				params.put("trakt-api-key", apiKeyTraktTv);
				params.put("trakt-api-version", "2");
				return params;
			}
		};
		queue.add(trendingMoviesRequest);
	}

	public void getTrendingMovieImage(ArrayList<Movie> movies){

		RequestQueue queue = Volley.newRequestQueue(mContext);

		for (int i = 0; i < movies.size(); i++) {
			final int tmbdID = movies.get(i).imageId;

			StringRequest trendingMovieImageRequest = new StringRequest(com.android.volley.Request.Method
				.GET, urlTMDB + tmbdID + "/images?api_key=" + apiKeyTMDB, new com.android.volley.Response
				.Listener<String>() {
				@Override
				public void onResponse(String response) {
					addToSharedPreferences(Integer.toString(tmbdID), response);
					trendingMoviesImagesChanged();
				}
			}, new com.android.volley.Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					//TODO: show toast for error message
				}
			});
			queue.add(trendingMovieImageRequest);
		}
	}

	public void addToSharedPreferences (String key, String value) {
		tvJunkieSharedPreferences = mContext.getSharedPreferences(PREFS_FILENAME, 0);
		editor = tvJunkieSharedPreferences.edit();

//		editor.clear();
		editor.putString(key, value);
		editor.apply();
	}

	public void trendingMoviesListChanged() {
		Intent intent = new Intent("trendingMoviesUpdated");
		mContext.sendBroadcast(intent);
	}

	public void trendingMoviesImagesChanged(){
		Intent intent = new Intent("trendingMoviesImagesUpdated");
		mContext.sendBroadcast(intent);
	}
}
