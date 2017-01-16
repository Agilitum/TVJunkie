package com.tvjunkie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_details);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		final Intent movieDetailIntent = getIntent();
		String movieTitle = movieDetailIntent.getStringExtra("movieTitle");
		String movieYear = movieDetailIntent.getStringExtra("movieYear");
		String imageURL = movieDetailIntent.getStringExtra("movieImageURL");

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		TextView title = (TextView) findViewById(R.id.title_movie_details);
		title.setText(movieTitle);

		TextView year = (TextView) findViewById(R.id.year_movie_details);
		year.setText(movieYear);

		ImageView image = (ImageView) findViewById(R.id.image_movie_details);

		Picasso.with(this)
			.load(imageURL)
			.placeholder(R.drawable.tv_junkie)
			.error(R.drawable.tv_junkie)
			.into(image);
	}

}
