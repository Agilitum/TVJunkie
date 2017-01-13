package com.tvjunkie.ui.trendingMoviesRecyclerView.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tvjunkie.R;
import com.tvjunkie.ui.trendingMoviesRecyclerView.adapter.model.Movie;

import java.util.ArrayList;

/**
 * Created by ludwig on 11/01/17.
 */

public class TrendingMoviesRecyclerViewAdapter extends RecyclerView
	.Adapter<TrendingMoviesRecyclerViewAdapter.ViewHolder> {

	ArrayList<Movie> movieList = new ArrayList<>();
	Context context;

	public TrendingMoviesRecyclerViewAdapter(ArrayList<Movie> movieList, Context context){
		this.movieList = movieList;
		this.context = context;
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		ImageView movieImage;
		TextView movieTitle;

		public ViewHolder(View itemView){
			super(itemView);

			movieImage = (ImageView) itemView.findViewById(R.id.movie_image);
			movieTitle = (TextView) itemView.findViewById(R.id.movie_title);
		}

		@Override
		public void onClick(View v) {

		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_movies_row_item, parent, false);
		return new ViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(TrendingMoviesRecyclerViewAdapter.ViewHolder holder, int position) {
		holder.movieTitle.setText(movieList.get(position).title);
		//TODO: use picasso?
//		holder.movieImage.setImageResource();
	}

	@Override
	public int getItemCount() {
		return movieList.size();
	}
}
