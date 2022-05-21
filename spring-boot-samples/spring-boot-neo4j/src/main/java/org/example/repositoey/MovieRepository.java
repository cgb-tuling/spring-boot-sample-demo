package org.example.repositoey;

import org.example.entity.Movie;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends Neo4jRepository<Movie,Long> {
    /**
     * CQL语法
     * @param name
     * @return
     */
    @Query("match (p:person{person_name:{name}})-[a:ACTED_IN]->(m:movie) return m")
    public List<Movie> findByPerson(@Param("name") String name);

    @Query("match (p:person{person_name:{name}})-[a:ACTED_IN]->(m:movie) return count(m) as count")
    public Integer findCountByPerson(@Param("name") String name);

    @Query("create (m:movie{id:{id},movie_id:{movieId},movie_title:{movieTitle},movie_introduction:{movieIntroduction}}) return m")
    public Movie saveMovie(@Param("id") Long id, @Param("movieTitle") String movieTitle, @Param("movieId") String movieId, @Param("movieIntroduction") String movieIntroduction);
}