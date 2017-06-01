package de.robinschuerer.bank.dkb;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = {
    "buchungstag",
    "wertstellung",
    "buchungstext",
    "auftraggeberBeguenstigter",
    "verwendungszweck",
    "kontonummer",
    "blz",
    "betrag",
    "glaeubigerId",
    "mandatsreferenz",
    "kundenreferenz"
    })
public class DkbAccountExportEntry {

    private String buchungstag;
    private String wertstellung;
    private String buchungstext;
    private String auftraggeberBeguenstigter;
    private String verwendungszweck;
    private String kontonummer;
    private String blz;
    private String betrag;
    private String glaeubigerId;
    private String mandatsreferenz;
    private String kundenreferenz;

    public String getBuchungstag() {
        return buchungstag;
    }

    public void setBuchungstag(final String buchungstag) {
        this.buchungstag = buchungstag;
    }

    public String getWertstellung() {
        return wertstellung;
    }

    public void setWertstellung(final String wertstellung) {
        this.wertstellung = wertstellung;
    }

    public String getBuchungstext() {
        return buchungstext;
    }

    public void setBuchungstext(final String buchungstext) {
        this.buchungstext = buchungstext;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(final String kontonummer) {
        this.kontonummer = kontonummer;
    }

    public String getBlz() {
        return blz;
    }

    public void setBlz(final String blz) {
        this.blz = blz;
    }

    public String getBetrag() {
        return betrag;
    }

    public void setBetrag(final String betrag) {
        this.betrag = betrag;
    }

    public String getGlaeubigerId() {
        return glaeubigerId;
    }

    public void setGlaeubigerId(final String glaeubigerId) {
        this.glaeubigerId = glaeubigerId;
    }

    public String getMandatsreferenz() {
        return mandatsreferenz;
    }

    public void setMandatsreferenz(final String mandatsreferenz) {
        this.mandatsreferenz = mandatsreferenz;
    }

    public String getKundenreferenz() {
        return kundenreferenz;
    }

    public void setKundenreferenz(final String kundenreferenz) {
        this.kundenreferenz = kundenreferenz;
    }

    public String getAuftraggeberBeguenstigter() {
        return auftraggeberBeguenstigter;
    }

    public void setAuftraggeberBeguenstigter(final String auftraggeberBeguenstigter) {
        this.auftraggeberBeguenstigter = auftraggeberBeguenstigter;
    }

    public String getVerwendungszweck() {
        return verwendungszweck;
    }

    public void setVerwendungszweck(final String verwendungszweck) {
        this.verwendungszweck = verwendungszweck;
    }

    @JsonIgnore
    public String getBuchungsBeschreibung(){
        return getAuftraggeberBeguenstigter() + '\n' + getVerwendungszweck();
    }


}
