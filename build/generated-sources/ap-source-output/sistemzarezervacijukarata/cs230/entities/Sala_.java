package sistemzarezervacijukarata.cs230.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sistemzarezervacijukarata.cs230.entities.Projekcija;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-01T11:57:29")
@StaticMetamodel(Sala.class)
public class Sala_ { 

    public static volatile SingularAttribute<Sala, String> tehnologija;
    public static volatile ListAttribute<Sala, Projekcija> projekcijaList;
    public static volatile SingularAttribute<Sala, Integer> brojSedista;
    public static volatile SingularAttribute<Sala, String> naziv;
    public static volatile SingularAttribute<Sala, Integer> id;

}