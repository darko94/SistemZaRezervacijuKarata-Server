package sistemzarezervacijukarata.cs230.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import sistemzarezervacijukarata.cs230.entities.Film;
import sistemzarezervacijukarata.cs230.entities.Projekcija;

/**
 *
 * @author Darko
 */
@Stateless
@Path("filmovi")
public class FilmFacadeREST extends AbstractFacade<Film> {

    @PersistenceContext(unitName = "SistemZaRezervacijuKarata-CS230PU")
    private EntityManager em;

    public FilmFacadeREST() {
        super(Film.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Film entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Film entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Film find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Film> findAll() {
        return super.findAll();
    }
    
    @GET
    @Path("projekcijeZaFilm/{filmId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Projekcija> listaProjekcijaZaFilm(@PathParam("filmId") Integer filmId) {
        
        
        Query query = getEntityManager().createQuery("SELECT p FROM Projekcija p JOIN p.filmId f WHERE f.id = :filmId");
        query.setParameter("filmId", filmId);

        List<Projekcija> listaProjekcija = query.getResultList();
                
        return listaProjekcija;
    }
    

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Film> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
