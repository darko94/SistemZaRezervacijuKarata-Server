package sistemzarezervacijukarata.cs230.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darko
 */
@Entity
@Table(name = "rezervacija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rezervacija.findAll", query = "SELECT r FROM Rezervacija r")
    , @NamedQuery(name = "Rezervacija.findById", query = "SELECT r FROM Rezervacija r WHERE r.id = :id")
    , @NamedQuery(name = "Rezervacija.findByBrojKarata", query = "SELECT r FROM Rezervacija r WHERE r.brojKarata = :brojKarata")})
public class Rezervacija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "broj_karata")
    private Integer brojKarata;
    @JoinColumn(name = "korisnik_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Korisnik korisnikId;
    @JoinColumn(name = "projekcija_id", referencedColumnName = "id")
    @ManyToOne
    private Projekcija projekcijaId;

    public Rezervacija() {
    }

    public Rezervacija(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBrojKarata() {
        return brojKarata;
    }

    public void setBrojKarata(Integer brojKarata) {
        this.brojKarata = brojKarata;
    }

    public Korisnik getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Korisnik korisnikId) {
        this.korisnikId = korisnikId;
    }

    public Projekcija getProjekcijaId() {
        return projekcijaId;
    }

    public void setProjekcijaId(Projekcija projekcijaId) {
        this.projekcijaId = projekcijaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rezervacija)) {
            return false;
        }
        Rezervacija other = (Rezervacija) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sistemzarezervacijukarata.cs230.entities.Rezervacija[ id=" + id + " ]";
    }

}
