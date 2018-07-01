package sistemzarezervacijukarata.cs230.entities;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "film")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Film.findAll", query = "SELECT f FROM Film f")
    , @NamedQuery(name = "Film.findById", query = "SELECT f FROM Film f WHERE f.id = :id")
    , @NamedQuery(name = "Film.findByDrzava", query = "SELECT f FROM Film f WHERE f.drzava = :drzava")
    , @NamedQuery(name = "Film.findByDuzinaTrajanja", query = "SELECT f FROM Film f WHERE f.duzinaTrajanja = :duzinaTrajanja")
    , @NamedQuery(name = "Film.findByGodina", query = "SELECT f FROM Film f WHERE f.godina = :godina")
    , @NamedQuery(name = "Film.findByNaslov", query = "SELECT f FROM Film f WHERE f.naslov = :naslov")
    , @NamedQuery(name = "Film.findByOpis", query = "SELECT f FROM Film f WHERE f.opis = :opis")
    , @NamedQuery(name = "Film.findByOriginalniNaslov", query = "SELECT f FROM Film f WHERE f.originalniNaslov = :originalniNaslov")
    , @NamedQuery(name = "Film.findByPocetakPrikazivanja", query = "SELECT f FROM Film f WHERE f.pocetakPrikazivanja = :pocetakPrikazivanja")
    , @NamedQuery(name = "Film.findByYoutubeUrl", query = "SELECT f FROM Film f WHERE f.youtubeUrl = :youtubeUrl")
    , @NamedQuery(name = "Film.findBySlikaUrl", query = "SELECT f FROM Film f WHERE f.slikaUrl = :slikaUrl")
    , @NamedQuery(name = "Film.findByZanr", query = "SELECT f FROM Film f WHERE f.zanr = :zanr")})
public class Film implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "drzava")
    private String drzava;
    @Column(name = "duzina_trajanja")
    private Integer duzinaTrajanja;
    @Column(name = "godina")
    private String godina;
    @Column(name = "naslov")
    private String naslov;
    @Column(name = "opis")
    private String opis;
    @Column(name = "originalni_naslov")
    private String originalniNaslov;
    @Column(name = "pocetak_prikazivanja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pocetakPrikazivanja;
    @Column(name = "youtube_url")
    private String youtubeUrl;
    @Column(name = "slika_url")
    private String slikaUrl;
    @Column(name = "zanr")
    private String zanr;
    @OneToMany(mappedBy = "filmId")
    private List<Projekcija> projekcijaList;

    public Film() {
    }

    public Film(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public Integer getDuzinaTrajanja() {
        return duzinaTrajanja;
    }

    public void setDuzinaTrajanja(Integer duzinaTrajanja) {
        this.duzinaTrajanja = duzinaTrajanja;
    }

    public String getGodina() {
        return godina;
    }

    public void setGodina(String godina) {
        this.godina = godina;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getOriginalniNaslov() {
        return originalniNaslov;
    }

    public void setOriginalniNaslov(String originalniNaslov) {
        this.originalniNaslov = originalniNaslov;
    }

    public Date getPocetakPrikazivanja() {
        return pocetakPrikazivanja;
    }

    public void setPocetakPrikazivanja(Date pocetakPrikazivanja) {
        this.pocetakPrikazivanja = pocetakPrikazivanja;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getSlikaUrl() {
        return slikaUrl;
    }

    public void setSlikaUrl(String slikaUrl) {
        this.slikaUrl = slikaUrl;
    }

    public String getZanr() {
        return zanr;
    }

    public void setZanr(String zanr) {
        this.zanr = zanr;
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
        if (!(object instanceof Film)) {
            return false;
        }
        Film other = (Film) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return naslov;
    }
    
}
