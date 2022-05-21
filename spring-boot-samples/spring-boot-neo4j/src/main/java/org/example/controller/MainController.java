package org.example.controller;

import org.example.entity.Movie;
import org.example.repositoey.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
/**
 * Spring Mvc的根路径、健康检查等。
 */
@RestController
public class MainController {
    @Autowired
    MovieRepository movieRepository;
    @GetMapping("/movie/{movieId}")
    public Movie findMovie(@PathVariable("movieId") Long movieId){
        return movieRepository.findById(movieId).orElse(null);
    }
    @GetMapping("/movie/save/{id}")
    public Movie findMovie(@PathVariable("id")Long id,@RequestParam("movieId") String movieId,@RequestParam("movieId") String movieTitle){
        Movie moviet = movieRepository.saveMovie(id,movieTitle,movieId,movieTitle);
        return movieRepository.findById(id).orElse(null);
    }
    @GetMapping("/movie/author/{name}")
    public List<Movie> findMovieByPerson(@PathVariable("name") String name){
        return movieRepository.findByPerson(name);
    }
    @GetMapping("/movie/count/{name}")
    public Integer findMovieCountByPerson(@PathVariable("name") String name){
        return movieRepository.findCountByPerson(name);
    }
}