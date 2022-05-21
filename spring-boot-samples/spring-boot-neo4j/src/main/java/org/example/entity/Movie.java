package org.example.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

/**
 * @date 2020/1/21 3:42 PM
 **/
@Node(labels = "movie")
@Data
public class Movie {
    @Id
    @GeneratedValue
    Long id;
    @Property("movie_id")
    String movieId;
    @Property("movie_title")
    String movieTitle;
    @Property("movie_release_date")
    String movieReleaseDate;
    @Property("movie_introduction")
    String movieIntroduction;
    @Property("movie_rating")
    String movieRating;
}