package tikape.chat.tietokanta.domain;

import java.sql.Timestamp;

/**
 *
 * @author mcraty
 */
public class Viesti {
    private int id;
    private String sisalto;
    private Timestamp aika;
    private int kayttaja;
    private String kayttajaTeksti;
    
    
    public Viesti(int id, String sisalto, Timestamp aika, int kayttaja) {
        this.id = id;
        this.sisalto = sisalto;
        this.aika = aika;
        this.kayttaja = kayttaja;
    }

    public int getId() {
        return id;
    }

    public String getSisalto() {
        return sisalto;
    }

    public Timestamp getAika() {
        return aika;
    }

    public int getKayttaja() {
        return kayttaja;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }

    public void setAika(Timestamp aika) {
        this.aika = aika;
    }

    public void setKayttaja(int kayttaja) {
        this.kayttaja = kayttaja;
    }

    public String getKayttajaTeksti() {
        return kayttajaTeksti;
    }

    public void setKayttajaTeksti(String kayttajaTeksti) {
        this.kayttajaTeksti = kayttajaTeksti;
    }

    
    
    @Override
    public String toString() {
        return sisalto + "\n" + aika + "\t | \t" + kayttaja + "\n";
    }
    
    
}
