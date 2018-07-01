package sistemzarezervacijukarata.cs230.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sistemzarezervacijukarata.cs230.entities.Korisnik;
import sistemzarezervacijukarata.cs230.entities.Projekcija;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-01T11:57:29")
@StaticMetamodel(Rezervacija.class)
public class Rezervacija_ { 

    public static volatile SingularAttribute<Rezervacija, Projekcija> projekcijaId;
    public static volatile SingularAttribute<Rezervacija, Integer> brojKarata;
    public static volatile SingularAttribute<Rezervacija, Integer> id;
    public static volatile SingularAttribute<Rezervacija, Korisnik> korisnikId;

}