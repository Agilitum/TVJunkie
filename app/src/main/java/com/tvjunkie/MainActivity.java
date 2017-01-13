package com.tvjunkie;

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

import com.tvjunkie.ui.trendingMoviesRecyclerView.adapter.TrendingMoviesRecyclerViewAdapter;
import com.tvjunkie.ui.trendingMoviesRecyclerView.adapter.model.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView trendingMoviesRecyclerView;
    TrendingMoviesRecyclerViewAdapter trendingMoviesRecyclerViewAdapter;


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
//        ArrayList<Movie> movieList = TVJunkieAPIClientKt.getTrendingMovies();
        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(0, new Movie("Trolls", "", "", "2016", "36"));

        trendingMoviesRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_trending_movies);
        trendingMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        trendingMoviesRecyclerViewAdapter = new TrendingMoviesRecyclerViewAdapter(movieList, getApplication());
        trendingMoviesRecyclerView.setAdapter(trendingMoviesRecyclerViewAdapter);
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

        } else if (id == R.id.nav_trendingSeries) {

        } else if (id == R.id.nav_statistics) {

        } else if (id == R.id.nav_shareWithFriends) {

        } else if (id == R.id.nav_recommendToFriends) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
