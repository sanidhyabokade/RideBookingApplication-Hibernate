package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Driver;
import com.rbm.entity.Rating;
import com.rbm.entity.Ride;

public interface RatingDao {
	
	boolean giveRating(Rating rating, Ride ride, Driver driver);
	
	Rating getRatingById(int ratingId);
	
	List<Rating> getDriverRatings(int driverId);
	
	Double getAverageDriverRating(int driverId);
}
