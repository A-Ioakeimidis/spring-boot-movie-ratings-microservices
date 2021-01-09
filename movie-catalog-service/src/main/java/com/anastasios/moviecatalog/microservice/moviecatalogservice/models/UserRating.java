package com.anastasios.moviecatalog.microservice.moviecatalogservice.models;

import java.util.List;

public class UserRating {

    private int userId;
    private List<Rating> userRatings;

    public UserRating() {
    }

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
}
