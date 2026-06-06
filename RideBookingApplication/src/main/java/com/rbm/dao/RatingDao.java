package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Rating;

public interface RatingDao {
	
	void addRating(Rating rating);
	
	Rating getRatingById(int ratingId);
	
	List<Rating> getDriverRatings(int driverId);
	
	Double getAverageDriverRating(int driverId);
}
