package university.app.Services;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import university.app.Interfaces.Locale_If;
import university.app.Interfaces.artistRepository;
import university.app.Interfaces.exhibitRepository;
import university.app.Services.Exceptions.LocaleNotSupportedException;
import university.app.dao.Artist;
import university.app.dao.Exhibit;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class UImethods {

    private final artistRepository artistService;
    private final exhibitRepository exhibitService;

    private final Massage_localization message;
    private final Locale_If locale;
    private final Scanner in = new Scanner(System.in);

    public void findAllImpl(String entity){
        switch (entity) {
            case "artist" -> {
                for (Artist artist : artistService.findAll()) {
                    System.out.println(artist);
                }
            }
            case "exhibit" -> {
                for (Exhibit exhibit : exhibitService.findAll()) {
                    System.out.println(exhibit);
                }
            }
            case "all" -> {
                for (Artist artist : artistService.findAll()) {
                    System.out.println(artist);
                }
                for (Exhibit exhibit : exhibitService.findAll()) {
                    System.out.println(exhibit);
                }
            }
            default -> System.out.println(message.localize("defaultFindAllMSG"));
        }
    }

    public void findImpl(String entity,String parameter){
        switch (entity) {
            case "artist" -> {
                switch (parameter) {
                    case "country" -> {
                        System.out.print(message.localize("countryENTER"));
                        String country = in.next();
                        if (!Objects.equals(artistService.findAllByCountry(country).toString(), "[]"))
                            for (Artist artist : artistService.findAllByCountry((country))) {
                                System.out.println(artist);
                            }
                        else System.out.println(message.localize("NoMatchingData"));
                    }
                    case "date" -> {
                        System.out.print(message.localize("dateENTER"));
                        Date date = new Date(new GregorianCalendar(in.nextInt(), in.nextInt(), in.nextInt()).getTimeInMillis());
                        if (!Objects.equals(artistService.findArtistsByDateofbirthBeforeOrderByDateofbirth(date).toString(), "[]"))
                            for (Artist artist : artistService.findArtistsByDateofbirthBeforeOrderByDateofbirth(date)) {
                                System.out.println(artist);
                            }
                        else System.out.println(message.localize("NoMatchingData"));
                    }
                    case "id" -> {
                        System.out.print(message.localize("EnterID"));
                        long id = in.nextLong();
                        if (!Objects.equals(artistService.findById(id).toString(), "[]"))
                            System.out.println(artistService.findById(id).toString());
                        else System.out.println(message.localize("NoMatchingData"));
                    }
                    default -> System.out.println(message.localize("defaultFindByMSG"));
                }
            }
            case "exhibit" -> {
                switch (parameter) {
                    case "artist" -> {
                        System.out.print(message.localize("EnterArtistID"));
                        long id = in.nextLong();
                        Artist artist = artistService.findById(id);
                        exhibitService.findAllByArtist(artist);
                        if (!Objects.equals(exhibitService.findAllByArtist(artist).toString(), "[]"))
                            for (Exhibit exhibit : exhibitService.findAllByArtist(artist)) {
                                System.out.println(exhibit);
                            }
                        else System.out.println(message.localize("NoMatchingData"));
                    }
                    case "date" -> {
                        System.out.print(message.localize("dateENTER"));
                        Date date = new Date(new GregorianCalendar(in.nextInt(), in.nextInt(), in.nextInt()).getTimeInMillis());
                        if (!Objects.equals(exhibitService.findExhibitsByDateofcreationBefore(date).toString(), "[]"))
                            for (Exhibit exhibit : exhibitService.findExhibitsByDateofcreationBefore(date)) {
                                System.out.println(exhibit);
                            }
                        else System.out.println(message.localize("NoMatchingData"));
                    }
                    case "id" -> {
                        System.out.print(message.localize("EnterID"));
                        long id = in.nextLong();
                        if (!Objects.equals(exhibitService.findById(id).toString(), "[]"))
                            System.out.println(exhibitService.findById(id).toString());
                        else System.out.println(message.localize("NoMatchingData"));
                    }
                    default -> System.out.println(message.localize("defaultFindByMSG"));
                }
            }
            default -> System.out.println(message.localize("defaultFindAllMSG"));
        }
    }

    public void chLangImpl(){
        System.out.print(message.localize("ChangeLanguageMSG"));
        try {
            locale.set(in.next().toLowerCase());
        } catch (LocaleNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void insertImpl(String entity,
                           String firstname,
                           String secondname,
                           String familyname,
                           Integer dayofbirth,
                           Integer mounthofbirth,
                           Integer yearofbirth,
                           String country,
                           Integer dayofdeath,
                           Integer mounthofdeath,
                           Integer yearofdeath,
                           Integer dayofcreation,
                           Integer mounthofcreation,
                           String name,
                           String type,
                           Integer yearofcreation,
                           long artist){
        switch (entity) {
            case "artist" -> {
                Date datebirth = new Date(new GregorianCalendar(yearofbirth, mounthofbirth, dayofbirth).getTimeInMillis());
                Date datedeath = new Date(new GregorianCalendar(yearofdeath, mounthofdeath, dayofdeath).getTimeInMillis());
                try {
                    Artist artist_for_save = new Artist(firstname, secondname, familyname, datebirth, country, datedeath);
                    artistService.save(artist_for_save);
                    System.out.println(message.localize("InsertComplete"));
                } catch (Exception e) {
                    System.out.println(message.localize("InsertError"));
                }
            }
            case "exhibit" -> {
                Date datecreation = new Date(new GregorianCalendar(yearofcreation, mounthofcreation, dayofcreation).getTimeInMillis());
                Artist artist_for_insert = null;
                if (artist!=0)
                    artist_for_insert = artistService.findById(artist);
                try {
                    Exhibit exhibit = new Exhibit(name,datecreation,type,artist_for_insert);
                    exhibitService.save(exhibit);
                    System.out.println(message.localize("InsertComplete"));
                } catch (Exception e) {
                    System.out.println(message.localize("InsertError"));
                }
            }
            default -> System.out.println(message.localize("defaultFindAllMSG"));
        }
    }

    public void insertManuallyImpl(String entity){
        switch (entity) {
            case "exhibit" ->{
                System.out.println(message.localize("EnterName"));
                String name = in.next();
                System.out.println(message.localize("EnterType"));
                String type = in.next();
                System.out.println(message.localize("dateENTER"));
                Date datecreation = new Date(new GregorianCalendar(in.nextInt(), in.nextInt(), in.nextInt()).getTimeInMillis());
                System.out.print(message.localize("EnterArtistID"));
                Artist artist_for_insert = artistService.findById(in.nextLong());
                try {
                    Exhibit exhibit = new Exhibit(name,datecreation,type,artist_for_insert);
                    exhibitService.save(exhibit);
                    System.out.println(message.localize("InsertComplete"));
                } catch (Exception e) {
                    System.out.println(message.localize("InsertError"));
                    e.printStackTrace();
                }
            }
            case "artist" -> {
                System.out.println(message.localize("firstnameEnter"));
                String firstname = in.next();
                System.out.println(message.localize("secondnameEnter"));
                String secondname = in.next();
                System.out.println(message.localize("familynameEnter"));
                String familyname = in.next();
                System.out.println(message.localize("dateofbirthENTER"));
                Date dateofbirth =  new Date(new GregorianCalendar(in.nextInt(), in.nextInt(), in.nextInt()).getTimeInMillis());
                System.out.println(message.localize("dateofdeathENTER"));
                Date dateofdeath =  new Date(new GregorianCalendar(in.nextInt(), in.nextInt(), in.nextInt()).getTimeInMillis());
                System.out.println(message.localize("countryENTER"));
                String country = in.next();
                try {
                    Artist artist_for_save = new Artist(firstname, secondname, familyname, dateofbirth, country, dateofdeath);
                    artistService.save(artist_for_save);
                    System.out.println(message.localize("InsertComplete"));
                }catch (Exception e){
                    System.out.println(message.localize("InsertError"));
                    e.printStackTrace();
                }
            }
            default -> System.out.println(message.localize("defaultFindAllMSG"));
        }
    }

    public void updateImpl(String entity,
                       long id,
                       String firstname,
                       String secondname,
                       String familyname,
                       Integer dayofbirth,
                       Integer mounthofbirth,
                       Integer yearofbirth,
                       String country,
                       Integer dayofdeath,
                       Integer mounthofdeath,
                       Integer yearofdeath,
                       Integer dayofcreation,
                       Integer mounthofcreation,
                       String name,
                       String type,
                       Integer yearofcreation,
                       long artist){
        switch (entity) {
            case "artist" -> {
                try {
                    Date dateofbirth =new Date(new GregorianCalendar(yearofbirth,mounthofbirth,dayofbirth).getTimeInMillis());
                    Date dateofdeath =new Date(new GregorianCalendar(yearofdeath,mounthofdeath,dayofdeath).getTimeInMillis());
                    Artist artist_for_update = new Artist(firstname,secondname,familyname,dateofbirth,country,dateofdeath);
                    if ((artist_for_update.getFirstname()== null)){
                        artist_for_update.setFirstname(artistService.findById(id).getFirstname());
                    }
                    if (artist_for_update.getSecondname() == null){
                        artist_for_update.setSecondname(artistService.findById(id).getSecondname());
                    }
                    if (artist_for_update.getFamilyname() == null){
                        artist_for_update.setFamilyname(artistService.findById(id).getFamilyname());
                    }
                    if (artist_for_update.getDateofbirth().equals(dateofbirth)){
                        artist_for_update.setDateofbirth(artistService.findById(id).getDateofbirth());
                    }
                    if (artist_for_update.getCountry() == null)
                        artist_for_update.setCountry(artistService.findById(id).getCountry());
                    if (artist_for_update.getDateofdeath().equals(new Date(new GregorianCalendar(9999, 0,31).getTimeInMillis()))){
                        artist_for_update.setDateofdeath(artistService.findById(id).getDateofdeath());
                    }
                    Artist artist_for_save = artistService.findById(id);
                    artist_for_save.setFirstname(artist_for_update.getFirstname());
                    artist_for_save.setSecondname(artist_for_update.getSecondname());
                    artist_for_save.setFamilyname(artist_for_update.getFamilyname());
                    artist_for_save.setDateofbirth(artist_for_update.getDateofbirth());
                    artist_for_save.setCountry(artist_for_update.getCountry());
                    artist_for_save.setDateofdeath(artist_for_update.getDateofdeath());
                    artistService.save(artist_for_save);
                    System.out.println(message.localize("UpdateComplete"));
                } catch (Exception e) {
                    System.out.println(message.localize("UpdateError"));
                    e.printStackTrace();
                }
            }
            case "exhibit" -> {
                try {
                    long id_for_insert = id;
                    if (artist!=0)
                        id_for_insert = artist;
                    Artist artist_for_insert = artistService.findById(id_for_insert);
                    Date dateofcreation = new Date(new GregorianCalendar(yearofcreation, mounthofcreation, dayofcreation).getTimeInMillis());
                    Exhibit exhibit = new Exhibit(name,dateofcreation,type,artist_for_insert);
                    if ((exhibit.getName()== null)){
                        exhibit.setName(exhibitService.findById(id).getName());
                    }
                    if (exhibit.getDateofcreation().equals((new Date(new GregorianCalendar(9999, 0,31).getTimeInMillis())))){
                        exhibit.setDateofcreation(exhibitService.findById(id).getDateofcreation());
                    }
                    if (exhibit.getType() == null)
                        exhibit.setType(exhibitService.findById(id).getType());
                    if (exhibit.getArtist() == null){
                        exhibit.setArtist(exhibitService.findById(id).getArtist());
                    }
                    Exhibit exhibit_for_save = exhibitService.findById(id);
                    exhibit_for_save.setName(exhibit.getName());
                    exhibit_for_save.setDateofcreation(exhibit.getDateofcreation());
                    exhibit_for_save.setType(exhibit.getType());
                    exhibit_for_save.setArtist(exhibit.getArtist());
                    exhibitService.save(exhibit_for_save);
                    System.out.println(message.localize("UpdateComplete"));
                } catch (Exception e) {
                    System.out.println(message.localize("UpdateError"));
                    e.printStackTrace();
                }
            }
            default -> System.out.println(message.localize("defaultFindAllMSG"));
        }
    }
    public void updateManuallyImpl(String entity){
        switch (entity) {
            case "artist" -> {
                System.out.println(message.localize("EnterID"));
                long id = in.nextLong();
                System.out.println(message.localize("firstnameEnter"));
                String firstname = in.next();
                System.out.println(message.localize("secondnameEnter"));
                String secondname = in.next();
                System.out.println(message.localize("familynameEnter"));
                String familyname = in.next();
                System.out.println(message.localize("dateofbirthENTER"));
                Calendar datebirth = new GregorianCalendar(in.nextInt(), in.nextInt(), in.nextInt());
                Date dateofbirth = new Date(datebirth.getTimeInMillis());
                System.out.println(message.localize("dateofdeathENTER"));
                Calendar datedeath = new GregorianCalendar(in.nextInt(), in.nextInt(), in.nextInt());
                Date dateofdeath = new Date(datedeath.getTimeInMillis());
                System.out.println(message.localize("countryENTER"));
                String country = in.next();
                try {
                    Artist artist_for_save = artistService.findById(id);
                    artist_for_save.setFirstname(firstname);
                    artist_for_save.setSecondname(secondname);
                    artist_for_save.setFamilyname(familyname);
                    artist_for_save.setDateofbirth(dateofbirth);
                    artist_for_save.setCountry(country);
                    artist_for_save.setDateofdeath(dateofdeath);
                    artistService.save(artist_for_save);
                    System.out.println(message.localize("UpdateComplete"));
                } catch (Exception e) {
                    System.out.println(message.localize("UpdateError"));
                    e.printStackTrace();
                }
            }
            case "exhibit" ->{
                System.out.println(message.localize("EnterID"));
                long id = in.nextLong();
                System.out.println(message.localize("EnterName"));
                String name = in.next();
                System.out.println(message.localize("EnterType"));
                String type = in.next();
                System.out.println(message.localize("dateENTER"));
                Date datecreation = new Date(new GregorianCalendar(in.nextInt(), in.nextInt(), in.nextInt()).getTimeInMillis());
                System.out.print(message.localize("EnterArtistID"));
                Artist artist_for_insert = artistService.findById(in.nextLong());
                try {
                    Exhibit exhibit = exhibitService.findById(id);
                    exhibit.setName(name);
                    exhibit.setDateofcreation(datecreation);
                    exhibit.setType(type);
                    exhibit.setArtist(artist_for_insert);
                    exhibitService.save(exhibit);
                    System.out.println(message.localize("UpdateComplete"));
                } catch (Exception e) {
                    System.out.println(message.localize("UpdateError"));
                    e.printStackTrace();
                }
            }
            default -> System.out.println(message.localize("defaultFindAllMSG"));
        }
    }

    public void deleteImpl(String entity,
                           long id){
        switch(entity) {
            case "artist" -> {
                try {
                    artistService.deleteById(id);
                    System.out.println(message.localize("DeleteComplete"));
                } catch (Exception e) {
                    System.out.println(message.localize("DeleteError"));
                    e.printStackTrace();
                }
            }
            case "exhibit" -> {
                try {
                    exhibitService.deleteById(id);
                    System.out.println(message.localize("DeleteComplete"));
                } catch (Exception e) {
                    System.out.println(message.localize("DeleteError"));
                    e.printStackTrace();
                }
            }
            default -> System.out.println(message.localize("defaultFindAllMSG"));
        }
    }


}
