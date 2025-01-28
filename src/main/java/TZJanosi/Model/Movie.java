package TZJanosi.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private LocalDate releaseDate;
    private List<Actor> actors=new ArrayList<>();
    private List<Integer> evaluation=new ArrayList<>();

    public Movie(String title, LocalDate releaseDate) {
        this.title = title;
        this.releaseDate = releaseDate;
    }

    public Movie(String title, LocalDate releaseDate, List<Integer> evaluation) {
        this(title,releaseDate);
        this.evaluation = evaluation;
    }

    public Movie(String title, LocalDate releaseDate, List<Actor> actors, List<Integer> evaluation) {
        this(title,releaseDate,evaluation);
        this.actors = actors;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<Integer> getEvaluation() {
        return evaluation;
    }
}
