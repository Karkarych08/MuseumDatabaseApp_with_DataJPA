package university.app.Interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import university.app.dao.Artist;

import java.sql.Date;
import java.util.Collection;

@Repository
public interface artistRepository extends CrudRepository<Artist, Long> {
    Collection<Artist> findArtistsByDateofbirthBeforeOrderByDateofbirth(Date date);
    Collection<Artist> findAllByCountry(String country);
    Artist findById(long id);
    void deleteById(long id);
}
