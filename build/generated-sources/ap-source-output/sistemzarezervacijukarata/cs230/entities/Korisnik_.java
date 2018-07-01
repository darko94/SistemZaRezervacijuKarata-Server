package sistemzarezervacijukarata.cs230.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sistemzarezervacijukarata.cs230.entities.Rezervacija;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-01T11:57:29")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile SingularAttribute<Korisnik, String> ime;
    public static volatile SingularAttribute<Korisnik, String> prezime;
    public static volatile SingularAttribute<Korisnik, String> password;
    public static volatile SingularAttribute<Korisnik, Date> datumRodjenja;
    public static volatile ListAttribute<Korisnik, Rezervacija> rezervacijaList;
    public static volatile SingularAttribute<Korisnik, Integer> id;
    public static volatile SingularAttribute<Korisnik, String> pol;
    public static volatile SingularAttribute<Korisnik, String> email;
    public static volatile SingularAttribute<Korisnik, String> username;

}