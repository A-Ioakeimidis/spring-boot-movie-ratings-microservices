package com.anastasios.moviecatalog.microservice.moviecatalogservice.controllers;

import com.anastasios.moviecatalog.microservice.moviecatalogservice.models.CatalogItem;
import com.anastasios.moviecatalog.microservice.moviecatalogservice.models.UserRating;
import com.anastasios.moviecatalog.microservice.moviecatalogservice.services.CatalogMovieInfo;
import com.anastasios.moviecatalog.microservice.moviecatalogservice.services.UserRatingsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogServiceController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    CatalogMovieInfo catalogMovieInfo;

    @Autowired
    UserRatingsInfo userRatingsInfo;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalogForUser(@PathVariable("userId") int userId) {
        UserRating userRatings = userRatingsInfo.getUserRating(userId);
        return userRatings.getUserRatings().stream()
                .map(rating -> catalogMovieInfo.getCatalogItem(rating))
                .collect(Collectors.toList());
    }
}