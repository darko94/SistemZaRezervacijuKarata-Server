package sistemzarezervacijukarata.cs230.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sistemzarezervacijukarata.cs230.entities.Film;
import sistemzarezervacijukarata.cs230.entities.Rezervacija;
import sistemzarezervacijukarata.cs230.entities.Sala;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-01T11:57:29")
@StaticMetamodel(Projekcija.class)
public class Projekcija_ { 

    public static volatile SingularAttribute<Projekcija, Date> datum;
    public static volatile SingularAttribute<Projekcija, String> vreme;
    public static volatile ListAttribute<Projekcija, Rezervacija> rezervacijaList;
    public static volatile SingularAttribute<Projekcija, Film> filmId;
    public static volatile SingularAttribute<Projekcija, Sala> salaId;
    public static volatile SingularAttribute<Projekcija, Integer> id;
    public static volatile SingularAttribute<Projekcija, Integer> slobodnoSedista;

}