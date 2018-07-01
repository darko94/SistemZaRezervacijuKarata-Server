package sistemzarezervacijukarata.cs230.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Darko
 */
@Entity
@Table(name = "sala")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sala.findAll", query = "SELECT s FROM Sala s")
    , @NamedQuery(name = "Sala.findById", query = "SELECT s FROM Sala s WHERE s.id = :id")
    , @NamedQuery(name = "Sala.findByBrojSedista", query = "SELECT s FROM Sala s WHERE s.brojSedista = :brojSedista")
    , @NamedQuery(name = "Sala.findByNaziv", query = "SELECT s FROM Sala s WHERE s.naziv = :naziv")
    , @NamedQuery(name = "Sala.findByTehnologija", query = "SELECT s FROM Sala s WHERE s.tehnologija = :tehnologija")})
public class Sala implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "broj_sedista")
    private Integer brojSedista;
    @Size(max = 255)
    @Column(name = "naziv")
    private String naziv;
    @Size(max = 255)
    @Column(name = "tehnologija")
    private String tehnologija;
    @OneToMany(mappedBy = "salaId")
    private List<Projekcija> projekcijaList;

    public Sala() {
    }

    public Sala(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBrojSedista() {
        return brojSedista;
    }

    public void setBrojSedista(Integer brojSedista) {
        this.brojSedista = brojSedista;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getTehnologija() {
        return tehnologija;
    }

    public void setTehnologija(String tehnologija) {
        this.tehnologija = tehnologija;
    }

    @XmlTransient
    public List<Projekcija> getProjekcijaList() {
        return projekcijaList;
    }

    public void setProjekcijaList(List<Projekcija> projekcijaList) {
        this.projekcijaList = projekcijaList;
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
        if (!(object instanceof Sala)) {
            return false;
        }
        Sala other = (Sala) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return naziv + " - " + tehnologija;
    }
    
}
