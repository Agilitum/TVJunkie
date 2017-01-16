package com.tvjunkie.ui.trendingMoviesRecyclerView.adapter.model;

/**
 * Created by ludwig on 12/01/17.
 */

public class Movie {

	public String title;
	public String description;
	public Integer imageId;
	public String imageURL;
	public String year;
	public String watchers;

	public Movie (String title, String description, Integer imageId, String imageURL, String year,
	              String watchers) {
		this.title = title;
		this.description = description;
		this.imageId = imageId;
		this.imageURL = imageURL;
		this.year = year;
		this.watchers = watchers;
	}
}
