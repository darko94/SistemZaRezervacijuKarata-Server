package sistemzarezervacijukarata.cs230.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Darko
 */
@javax.ws.rs.ApplicationPath("rest")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(sistemzarezervacijukarata.cs230.service.FilmFacadeREST.class);
        resources.add(sistemzarezervacijukarata.cs230.service.KorisnikFacadeREST.class);
        resources.add(sistemzarezervacijukarata.cs230.service.ProjekcijaFacadeREST.class);
        resources.add(sistemzarezervacijukarata.cs230.service.RezervacijaFacadeREST.class);
        resources.add(sistemzarezervacijukarata.cs230.service.SalaFacadeREST.class);
        resources.add(sistemzarezervacijukarata.cs230.service.UserRolesFacadeREST.class);
        resources.add(sistemzarezervacijukarata.cs230.service.UsersFacadeREST.class);
    }
    
}
