package university.app.Interfaces;

import org.springframework.data.repository.CrudRepository;
import university.app.dao.Artist;
import university.app.dao.Exhibit;

import java.sql.Date;
import java.util.Collection;


public interface exhibitRepository extends CrudRepository<Exhibit, Long> {
    Collection<Exhibit> findExhibitsByDateofcreationBefore(Date date);
    Collection<Exhibit> findAllByArtist(Artist artist);
    Exhibit findById(long id);
    void deleteById(long id);
}
