package sistemzarezervacijukarata.cs230.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sistemzarezervacijukarata.cs230.entities.Projekcija;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-01T11:57:29")
@StaticMetamodel(Film.class)
public class Film_ { 

    public static volatile SingularAttribute<Film, Date> pocetakPrikazivanja;
    public static volatile SingularAttribute<Film, String> originalniNaslov;
    public static volatile SingularAttribute<Film, String> naslov;
    public static volatile SingularAttribute<Film, String> zanr;
    public static volatile ListAttribute<Film, Projekcija> projekcijaList;
    public static volatile SingularAttribute<Film, String> drzava;
    public static volatile SingularAttribute<Film, Integer> duzinaTrajanja;
    public static volatile SingularAttribute<Film, String> youtubeUrl;
    public static volatile SingularAttribute<Film, Integer> id;
    public static volatile SingularAttribute<Film, String> godina;
    public static volatile SingularAttribute<Film, String> slikaUrl;
    public static volatile SingularAttribute<Film, String> opis;

}