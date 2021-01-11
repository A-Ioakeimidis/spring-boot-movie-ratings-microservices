package com.anastasios.moviecatalog.microservice.moviecatalogservice.services;

import com.anastasios.moviecatalog.microservice.moviecatalogservice.models.Rating;
import com.anastasios.moviecatalog.microservice.moviecatalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingsInfo {

    @Value("${ratings-data-service-user-url}")
    private String ratingsDataServiceUserUrl;

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRating",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            })
    public UserRating getUserRating(int userId) {
        return restTemplate.getForObject(ratingsDataServiceUserUrl + userId, UserRating.class);
    }

    //The fallback method for Hystrix(getFallbackUserRating). This could return cached data for the user if it exists. For the example, a hard coded response will be returned.
    public UserRating getFallbackUserRating(@PathVariable("userId") int userId) {
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setUserRatings(Arrays.asList(
                new Rating(0, 0)
        ));
        return userRating;
    }
}
