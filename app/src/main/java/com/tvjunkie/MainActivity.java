package com.tvjunkie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tvjunkie.api.TVJunkieAPIClientMain;
import com.tvjunkie.ui.trendingMoviesRecyclerView.adapter.TrendingMoviesRecyclerViewAdapter;
import com.tvjunkie.ui.trendingMoviesRecyclerView.adapter.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String PREFS_FILENAME = "com.tvjunkie.sharedPreferences";

    RecyclerView trendingMoviesRecyclerView;
    TrendingMoviesRecyclerViewAdapter trendingMoviesRecyclerViewAdapter;

    ArrayList<Movie> movieList;

    SharedPreferences tvJunkieSharedPreferences;

    TVJunkieAPIClientMain tvJunkieAPIClientMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // TrendingMoviesRecyclerViewAdapter
        movieList = new ArrayList<Movie>();

        trendingMoviesRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_trending_movies);
        trendingMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        trendingMoviesRecyclerViewAdapter = new TrendingMoviesRecyclerViewAdapter(movieList, this);
        trendingMoviesRecyclerView.setAdapter(trendingMoviesRecyclerViewAdapter);

        //SharedPreferences TVJunkie
        tvJunkieSharedPreferences = getSharedPreferences(PREFS_FILENAME, MODE_PRIVATE);

        //BroadcastReceiver registration
        getApplicationContext().registerReceiver(trendingMoviesListUpdated, new IntentFilter
          ("trendingMoviesUpdated"));
        getApplicationContext().registerReceiver(trendingMoviesImagesUpdated, new IntentFilter
          ("trendingMoviesImagesUpdated"));

        //TVJunkieAPIClient Init
        tvJunkieAPIClientMain = new TVJunkieAPIClientMain(getApplicationContext());
        tvJunkieAPIClientMain.getTrendingMovies();

        //setOnItemClickListener for Adapter
        trendingMoviesRecyclerViewAdapter.SetOnItemClickListener(new TrendingMoviesRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent movieDetail = new Intent(getApplicationContext(), MovieDetails.class);
                movieDetail.putExtra("movieTitle", movieList.get(position).title);
                movieDetail.putExtra("movieYear", movieList.get(position).year);
                movieDetail.putExtra("movieImageURL", movieList.get(position).imageURL);
                MainActivity.this.startActivity(movieDetail);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        getApplicationContext().registerReceiver(trendingMoviesListUpdated, new IntentFilter
          ("trendingMoviesUpdated"));
        getApplicationContext().registerReceiver(trendingMoviesImagesUpdated, new IntentFilter
          ("trendingMoviesImagesUpdated"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        getApplicationContext().unregisterReceiver(trendingMoviesListUpdated);
        getApplicationContext().unregisterReceiver(trendingMoviesImagesUpdated);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_trendingMovies) {
            tvJunkieAPIClientMain.getTrendingMovies();

        } else if (id == R.id.nav_trendingSeries) {

        } else if (id == R.id.nav_statistics) {

        } else if (id == R.id.nav_shareWithFriends) {

        } else if (id == R.id.nav_recommendToFriends) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateData() throws JSONException {

        String movieListTrending = getSharedPreferencesString("KEY_MOVIES_TRENDING");
//        tvJunkieSharedPreferences.getString("KEY_MOVIES_TRENDING", "");

        JSONArray jsonArray = new JSONArray(movieListTrending);
        ArrayList<Movie> list = new ArrayList<>();
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            list.add(new Movie(jsonObject.getJSONObject("movie").optString("title",
              "default"), jsonObject.getJSONObject("movie").optString("description", "No description"),
              jsonObject.getJSONObject("movie").getJSONObject("ids").optInt("tmdb"), null,
              jsonObject.getJSONObject("movie").optString("year", ""), jsonObject.optString("watchers", "")));
        }
        movieList.clear();
        movieList.addAll(list);

        getImagesForMovies(movieList);
        sortMovieList(movieList);
        updateDatasetAdapter();
    }

    private void sortMovieList(ArrayList<Movie> movieList){
        Collections.sort(movieList, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return lhs.watchers.compareTo(rhs.watchers);
            }
        });
    }

    private void updateDatasetAdapter(){
        trendingMoviesRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void getImagesForMovies(ArrayList<Movie> movies){
            tvJunkieAPIClientMain.getTrendingMovieImage(movieList);
    }

    private void updateImagesForMovies() throws JSONException{
        for (int t = 0; t < movieList.size(); t++){
            int movieID = movieList.get(t).imageId;
            String imageURL = getSharedPreferencesString(Integer.toString(movieID));

            String tmpFilePath = new JSONObject(imageURL).getJSONArray("backdrops").getJSONObject(0)
              .optString("file_path");

           movieList.get(t).imageURL = "https://image.tmdb.org/t/p/w1000" + tmpFilePath;
        }
    }

    private String getSharedPreferencesString(String key){
        return tvJunkieSharedPreferences.getString(key, "");
    }


    private BroadcastReceiver trendingMoviesListUpdated = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                updateData();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private BroadcastReceiver trendingMoviesImagesUpdated = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                updateImagesForMovies();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
