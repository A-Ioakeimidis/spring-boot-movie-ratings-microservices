package com.anastasios.moviecatalog.microservice.ratingsdataservice.controllers;

import com.anastasios.moviecatalog.microservice.ratingsdataservice.models.Rating;
import com.anastasios.moviecatalog.microservice.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratings")
public class RatingsDataServiceController {

    @GetMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") int movieId) {
        return new Rating(movieId, 4);
    }

    @GetMapping("user/{userId}")
    public UserRating getUserRatings(@PathVariable("userId") int userId) {
        UserRating userRating = new UserRating();
        userRating.initData(userId);
        return userRating;
    }
}
