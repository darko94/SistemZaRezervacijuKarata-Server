package sistemzarezervacijukarata.cs230.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Darko
 */
@Entity
@Table(name = "projekcija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projekcija.findAll", query = "SELECT p FROM Projekcija p")
    , @NamedQuery(name = "Projekcija.findById", query = "SELECT p FROM Projekcija p WHERE p.id = :id")
    , @NamedQuery(name = "Projekcija.findBySlobodnoSedista", query = "SELECT p FROM Projekcija p WHERE p.slobodnoSedista = :slobodnoSedista")
    , @NamedQuery(name = "Projekcija.findByDatum", query = "SELECT p FROM Projekcija p WHERE p.datum = :datum")
    , @NamedQuery(name = "Projekcija.findByVreme", query = "SELECT p FROM Projekcija p WHERE p.vreme = :vreme")})
public class Projekcija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "slobodno_sedista")
    private Integer slobodnoSedista;
    @Column(name = "datum")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @Column(name = "vreme")
    private String vreme;
    @OneToMany(mappedBy = "projekcijaId")
    private List<Rezervacija> rezervacijaList;
    @JoinColumn(name = "sala_id", referencedColumnName = "id")
    @ManyToOne
    private Sala salaId;
    @JoinColumn(name = "film_id", referencedColumnName = "id")
    @ManyToOne
    private Film filmId;

    public Projekcija() {
    }

    public Projekcija(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSlobodnoSedista() {
        return slobodnoSedista;
    }

    public void setSlobodnoSedista(Integer slobodnoSedista) {
        this.slobodnoSedista = slobodnoSedista;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getVreme() {
        return vreme;
    }

    public void setVreme(String vreme) {
        this.vreme = vreme;
    }

    @XmlTransient
    public List<Rezervacija> getRezervacijaList() {
        return rezervacijaList;
    }

    public void setRezervacijaList(List<Rezervacija> rezervacijaList) {
        this.rezervacijaList = rezervacijaList;
    }

    public Sala getSalaId() {
        return salaId;
    }

    public void setSalaId(Sala salaId) {
        this.salaId = salaId;
    }

    public Film getFilmId() {
        return filmId;
    }

    public void setFilmId(Film filmId) {
        this.filmId = filmId;
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
        if (!(object instanceof Projekcija)) {
            return false;
        }
        Projekcija other = (Projekcija) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
        return filmId.getNaslov() + " - " + df.format(datum) + " " + vreme;
    }

}
