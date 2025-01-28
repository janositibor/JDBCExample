package TZJanosi.Service;

import TZJanosi.Model.Actor;
import TZJanosi.Repository.*;

import java.util.List;

public class Service {
    private DB db;

    private ActorRepository actorRepository;
    private MovieRepository movieRepository;
    private ActorMovieRepository actorMovieRepository;
    private RatingRepository ratingRepository;

    public Service(DB db) {
        this.db = db;
        actorRepository=new ActorRepository(db.getDataSource());
        movieRepository=new MovieRepository(db.getDataSource());
        actorMovieRepository=new ActorMovieRepository(db.getDataSource());
        ratingRepository=new RatingRepository(db.getDataSource());
    }

    public void saveActor(String name, int yob){
        Actor actorToSave=new Actor(name,yob);
        if(!actorRepository.findActor(actorToSave).isPresent()){
            actorRepository.saveBasicAndGetGeneratedKey(actorToSave);
        }
    }
    public Actor findActor(Actor actor){
        return actorRepository.findActor(actor).orElseThrow(() -> new IllegalArgumentException("No actor found: "+actor));
    }

    public List<Actor> findAllActor(){
        return actorRepository.findAllActor();
    }

}
