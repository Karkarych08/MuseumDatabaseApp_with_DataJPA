package university.app.Services;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class UserInterface {

    private final UImethods ui;

    @ShellMethod("Find all persons")
    public void findAll(@ShellOption(defaultValue = "all") String entity) {
        ui.findAllImpl(entity);
    }

    @ShellMethod("Find by parameter")
    public void find(@ShellOption(defaultValue = "artist") String entity,
                     @ShellOption(defaultValue = "id") @NotNull String parameter) {
        ui.findImpl(entity,parameter);
    }

    @ShellMethod("Change Language")
    public void changeLanguage() {
        ui.chLangImpl();
    }

    @ShellMethod("Insert")
    public void insert(@ShellOption(defaultValue = "artist") String entity,
                       @ShellOption(defaultValue = "null") String firstname,
                       @ShellOption(defaultValue = "null") String secondname,
                       @ShellOption(defaultValue = "null") String familyname,
                       @ShellOption(defaultValue = "null") Integer dayofbirth,
                       @ShellOption(defaultValue = "null") Integer mounthofbirth,
                       @ShellOption(defaultValue = "null") Integer yearofbirth,
                       @ShellOption(defaultValue = "null") String country,
                       @ShellOption(defaultValue = "null") Integer dayofdeath,
                       @ShellOption(defaultValue = "null") Integer mounthofdeath,
                       @ShellOption(defaultValue = "null") Integer yearofdeath,
                       @ShellOption(defaultValue = "null") Integer dayofcreation,
                       @ShellOption(defaultValue = "null") Integer mounthofcreation,
                       @ShellOption(defaultValue = "null") String name,
                       @ShellOption(defaultValue = "null") String type,
                       @ShellOption(defaultValue = "null") Integer yearofcreation,
                       @ShellOption(defaultValue = "0") long artist){
        ui.insertImpl(entity, firstname, secondname, familyname, dayofbirth, mounthofbirth, yearofbirth, country, dayofdeath, mounthofdeath, yearofdeath, dayofcreation, mounthofcreation, name, type, yearofcreation, artist);
    }

    @ShellMethod("Insert artist manually")
    public void insertManually(@ShellOption(defaultValue = "artist") String entity){
        ui.insertManuallyImpl(entity);
    }

    @ShellMethod("Update")
    public void update(@ShellOption(defaultValue = "artist") String entity,
                       @ShellOption long id,
                       @ShellOption(defaultValue = ShellOption.NULL) String firstname,
                       @ShellOption(defaultValue = ShellOption.NULL) String secondname,
                       @ShellOption(defaultValue = ShellOption.NULL) String familyname,
                       @ShellOption(defaultValue = "31") Integer dayofbirth,
                       @ShellOption(defaultValue = "0") Integer mounthofbirth,
                       @ShellOption(defaultValue = "9999") Integer yearofbirth,
                       @ShellOption(defaultValue = ShellOption.NULL) String country,
                       @ShellOption(defaultValue = "31") Integer dayofdeath,
                       @ShellOption(defaultValue = "0") Integer mounthofdeath,
                       @ShellOption(defaultValue = "9999") Integer yearofdeath,
                       @ShellOption(defaultValue = "31") Integer dayofcreation,
                       @ShellOption(defaultValue = "0") Integer mounthofcreation,
                       @ShellOption(defaultValue = ShellOption.NULL) String name,
                       @ShellOption(defaultValue = ShellOption.NULL) String type,
                       @ShellOption(defaultValue = "9999") Integer yearofcreation,
                       @ShellOption(defaultValue = "0") long artist){
        ui.updateImpl(entity, id, firstname, secondname, familyname, dayofbirth, mounthofbirth, yearofbirth, country, dayofdeath, mounthofdeath, yearofdeath, dayofcreation, mounthofcreation, name, type, yearofcreation, artist);
    }

    @ShellMethod("Update artist manually")
    public void updateManually(@ShellOption(defaultValue = "artist") String entity){
        ui.updateManuallyImpl(entity);
    }

    @ShellMethod("Delete artist")
    public void delete(@ShellOption(defaultValue = "artist") String entity,
                       @ShellOption long id){
        ui.deleteImpl(entity,id);
    }
}
