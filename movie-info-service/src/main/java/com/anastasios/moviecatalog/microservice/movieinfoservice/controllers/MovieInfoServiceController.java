package com.anastasios.moviecatalog.microservice.movieinfoservice.controllers;

import com.anastasios.moviecatalog.microservice.movieinfoservice.models.Movie;
import com.anastasios.moviecatalog.microservice.movieinfoservice.models.MovieDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movies")
public class MovieInfoServiceController {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") int movieId) {
        MovieDetails movieDetails = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey, MovieDetails.class);
        return new Movie(movieId, movieDetails.getTitle(), movieDetails.getOverview());
    }
}