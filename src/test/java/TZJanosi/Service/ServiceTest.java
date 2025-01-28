package TZJanosi.Service;

import TZJanosi.Model.Actor;
import TZJanosi.Repository.ActorMovieRepository;
import TZJanosi.Repository.ActorRepository;
import TZJanosi.Repository.DB;
import TZJanosi.Repository.MovieRepository;
import org.assertj.core.groups.Tuple;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {
    private DB db=new DB("movies","moviesUser","moviesPass");
    Service service=new Service(db);
    @BeforeEach
    void init(){
        Flyway flyway=Flyway.configure().dataSource(db.getDataSource()).load();
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void saveActorTest(){
        service.saveActor("Scherer Péter",1961);
        service.saveActor("Mucsi Zoltán",1957);
    }

    @Test
    void findActor(){
        service.saveActor("Scherer Péter",1961);
        service.saveActor("Mucsi Zoltán",1957);
        Actor getActor= service.findActor(new Actor("Mucsi Zoltán",1957));
        assertThat(getActor)
                .hasFieldOrPropertyWithValue("name","Mucsi Zoltán")
                .hasFieldOrPropertyWithValue("yob",1957);
    }
    @Test
    void notFoundActor(){
        service.saveActor("Scherer Péter",1961);
        service.saveActor("Mucsi Zoltán",1957);
        IllegalArgumentException iae=assertThrows(IllegalArgumentException.class,()->service.findActor(new Actor("Schererr Péter",1961)));
        assertEquals("No actor found: Actor{id=0, name='Schererr Péter', yob=1961}",iae.getMessage());

    }
    @Test
    void findAllActor(){
        service.saveActor("Scherer Péter",1961);
        service.saveActor("Mucsi Zoltán",1957);
        List<Actor> actors= service.findAllActor();
        assertThat(actors)
                .extracting(Actor::getName,Actor::getYob)
                .containsExactly(new Tuple("Scherer Péter",1961), new Tuple("Mucsi Zoltán",1957));
    }

}