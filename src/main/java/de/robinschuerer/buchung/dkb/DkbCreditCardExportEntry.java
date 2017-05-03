package de.robinschuerer.buchung.dkb;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "umsatzAbgerechnet",
    "wertstellung",
    "belegdatum",
    "beschreibung",
    "betrag",
    "urspruenglicherBetrag"
})
public class DkbCreditCardExportEntry {

    private String umsatzAbgerechnet;
    private String wertstellung;
    private String belegdatum;
    private String beschreibung;
    private String betrag;
    private String urspruenglicherBetrag;

    public String getUmsatzAbgerechnet() {
        return umsatzAbgerechnet;
    }

    public void setUmsatzAbgerechnet(final String umsatzAbgerechnet) {
        this.umsatzAbgerechnet = umsatzAbgerechnet;
    }

    public String getWertstellung() {
        return wertstellung;
    }

    public void setWertstellung(final String wertstellung) {
        this.wertstellung = wertstellung;
    }

    public String getBelegdatum() {
        return belegdatum;
    }

    public void setBelegdatum(final String belegdatum) {
        this.belegdatum = belegdatum;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(final String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getBetrag() {
        return betrag;
    }

    public void setBetrag(final String betrag) {
        this.betrag = betrag;
    }

    public String getUrspruenglicherBetrag() {
        return urspruenglicherBetrag;
    }

    public void setUrspruenglicherBetrag(final String urspruenglicherBetrag) {
        this.urspruenglicherBetrag = urspruenglicherBetrag;
    }
}
