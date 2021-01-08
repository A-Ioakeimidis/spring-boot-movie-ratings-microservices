package com.anastasios.moviecatalog.microservice.moviecatalogservice.controllers;

import com.anastasios.moviecatalog.microservice.moviecatalogservice.models.CatalogItem;
import com.anastasios.moviecatalog.microservice.moviecatalogservice.models.Movie;
import com.anastasios.moviecatalog.microservice.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${ratings-data-service-user-url}")
    private String ratingsDataServiceUserUrl;

    @Value("${movie-info-service-movies-url}")
    private String movieInfoServiceMoviesUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;


    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalogForUser(@PathVariable("userId") int userId) {
        //This uses WebClientBuilder(asynchronous) instead of RestTemplate to make the API calls
            /*
                     Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
             */

        UserRating userRatings = restTemplate.getForObject(ratingsDataServiceUserUrl + userId, UserRating.class);

        return userRatings.getUserRatings().stream()
                .map(rating -> {
                    //This uses RestTemplate to make the API calls. For each movie ID, call movie info service and get the details (calls the service name instead of a url)
                    Movie movie = restTemplate.getForObject(movieInfoServiceMoviesUrl + rating.getMovieId(), Movie.class);

                    //Put them all together
                    return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
                }).collect(Collectors.toList());
    }
}