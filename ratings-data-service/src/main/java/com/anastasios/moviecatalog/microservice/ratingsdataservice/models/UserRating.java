package com.anastasios.moviecatalog.microservice.ratingsdataservice.models;

import java.util.Arrays;
import java.util.List;

public class UserRating {

    private int userId;

    private List<Rating> userRatings;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public List<Rating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(List<Rating> userRatings) {
        this.userRatings = userRatings;
    }

    public void initData(int userId) {
        this.setUserId(userId);
        this.setUserRatings(Arrays.asList(
                new Rating(100, 4),
                new Rating(500, 9)
        ));
    }
}
