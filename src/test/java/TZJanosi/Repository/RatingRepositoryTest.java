package TZJanosi.Repository;

import TZJanosi.Model.Movie;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RatingRepositoryTest {
    private DB db=new DB("movies","moviesUser","moviesPass");
    private MovieRepository movieRepository;
    private RatingRepository ratingRepository;
    @BeforeEach
    void init(){
        Flyway flyway=Flyway.configure().dataSource(db.getDataSource()).load();
        flyway.clean();
        flyway.migrate();
        movieRepository=new MovieRepository(db.getDataSource());
        ratingRepository=new RatingRepository(db.getDataSource());
    }

    @Test
    void saveTest(){
        Movie movie1=new Movie("Roncsfilm", LocalDate.of(1992,9,1));
        Movie movie2=new Movie("Papírkutyák", LocalDate.of(2008,10,30));
        Movie movie3=new Movie("Mielőtt befejezi röptét a denevér", LocalDate.of(1989,1,1));

        movieRepository.saveBasicAndGetGeneratedKey(movie1);
        movieRepository.saveBasicAndGetGeneratedKey(movie2);
        movieRepository.saveBasicAndGetGeneratedKey(movie3);

        Movie getMovie1=movieRepository.findMovie(movie1).get();
        Movie getMovie2=movieRepository.findMovie(movie2).get();
        Movie getMovie3=movieRepository.findMovie(movie3).get();

        for (int i = 1; i <6 ; i++) {
            ratingRepository.save(getMovie1, i);
        }
        for (int i = 5; i <7 ; i++) {
            ratingRepository.save(getMovie3, i);
        }
        for (int i = 1; i <11 ; i+=2) {
            ratingRepository.save(getMovie2, i);
        }

        List<Integer> rating1=ratingRepository.findRatingsForMovie(getMovie1);
        assertThat(rating1)
                .hasSize(5)
                .containsExactly(1,2,3,4,5);
        List<Integer> rating2=ratingRepository.findRatingsForMovie(getMovie2);
        assertThat(rating2)
                .hasSize(5)
                .containsExactly(1,3,5,7,9);
        List<Integer> rating3=ratingRepository.findRatingsForMovie(getMovie3);
        assertThat(rating3)
                .hasSize(2)
                .containsExactly(5,6);

    }
}