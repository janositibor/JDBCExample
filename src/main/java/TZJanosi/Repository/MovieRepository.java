package TZJanosi.Repository;

import TZJanosi.Model.Actor;
import TZJanosi.Model.Movie;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class MovieRepository implements Repository{
    private MariaDbDataSource dataSource;

    public MovieRepository(MariaDbDataSource dataSource) {
        this.dataSource = dataSource;
    }
    public Optional<Long> saveBasicAndGetGeneratedKey(Movie movie) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "insert into movies(title,release_date) values (?,?)",
                     Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, movie.getTitle());
            stmt.setDate(2, Date.valueOf(movie.getReleaseDate()));
            stmt.executeUpdate();
            return executeAndGetGeneratedKey(stmt);
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error by insert movie: "+movie, sqle);
        }
    }
    public Optional<Movie> findMovie(Movie movie) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement("select id, title,release_date from movies WHERE title LIKE ? AND release_date=?")){
            stmt.setString(1, movie.getTitle());
            stmt.setDate(2, Date.valueOf(movie.getReleaseDate()));
            return movieFromStatement(stmt);
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error in findMovie: "+movie, sqle);
        }
    }
    private Optional<Movie> movieFromStatement(PreparedStatement stmt) throws SQLException{
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                long id= rs.getLong("id");
                String title = rs.getString("title");
                LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
                return Optional.of(new Movie(id, title, releaseDate));
            }
            return Optional.empty();
        }
    }


}
