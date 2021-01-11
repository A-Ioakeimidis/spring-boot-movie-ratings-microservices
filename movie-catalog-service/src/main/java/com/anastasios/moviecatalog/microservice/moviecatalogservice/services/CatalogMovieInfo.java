package com.anastasios.moviecatalog.microservice.moviecatalogservice.services;

import com.anastasios.moviecatalog.microservice.moviecatalogservice.models.CatalogItem;
import com.anastasios.moviecatalog.microservice.moviecatalogservice.models.Movie;
import com.anastasios.moviecatalog.microservice.moviecatalogservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CatalogMovieInfo {

    @Value("${movie-info-service-movies-url}")
    private String movieInfoServiceMoviesUrl;

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
            threadPoolKey = "movieInfoPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            })
    public CatalogItem getCatalogItem(Rating rating) {
        //This uses WebClientBuilder(asynchronous) instead of RestTemplate to make the API calls
        //Movie movie = webClientBuilder.build()
        //.get()
        //.uri("http://localhost:8082/movies/" + rating.getMovieId())
        //.retrieve()
        //.bodyToMono(Movie.class)
        //.block();

        //This uses RestTemplate to make the API calls. For each movie ID, call movie info service and get the details (calls the service name instead of a url)
        Movie movie = restTemplate.getForObject(movieInfoServiceMoviesUrl + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    //The fallback method for Hystrix(getFallbackCatalogItem). This could return cached data for the user if it exists. For the example, a hard coded response will be returned.
    public CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("Movie not found", "", rating.getRating());
    }
}
