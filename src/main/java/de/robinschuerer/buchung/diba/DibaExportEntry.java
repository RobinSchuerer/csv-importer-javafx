package de.robinschuerer.buchung.diba;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"buchung","valuta","auftraggeber","buchungstext","verwendungszweck","betrag",
"betragWaehrung", "saldo", "saldoWaehrung"})
public class DibaExportEntry {
//    Buchung;Valuta;Auftraggeber/Empf�nger;Buchungstext;Verwendungszweck;Betrag;W�hrung;Saldo;W�hrung

    private String buchung;
    private String valuta;
    private String auftraggeber;
    private String buchungstext;
    private String verwendungszweck;
    private String betrag;
    private String betragWaehrung;
    private String saldo;
    private String saldoWaehrung;

    public String getBuchungstext() {
        return buchungstext;
    }

    public void setBuchungstext(final String buchungstext) {
        this.buchungstext = buchungstext;
    }

    public String getBuchung() {
        return buchung;
    }

    @JsonIgnore
    public LocalDate getBuchungsDatum(){
        return LocalDate.parse(getBuchung(), DateTimeFormatter
            .ofPattern("dd.MM.yyyy"));
    }

    public void setBuchung(final String buchung) {
        this.buchung = buchung;
    }

    public String getValuta() {
        return valuta;
    }

    public void setValuta(final String valuta) {
        this.valuta = valuta;
    }

    @JsonIgnore
    public String getBuchungsBeschreibung() {
        return auftraggeber + '\n' + verwendungszweck;
    }

    public void setAuftraggeber(final String auftraggeber) {
        this.auftraggeber = auftraggeber;
    }

    public String getVerwendungszweck() {
        return verwendungszweck;
    }

    public void setVerwendungszweck(final String verwendungszweck) {
        this.verwendungszweck = verwendungszweck;
    }

    public String getBetrag() {
        return betrag;
    }

    public void setBetrag(final String betrag) {
        this.betrag = betrag;
    }

    public String getBetragWaehrung() {
        return betragWaehrung;
    }

    public void setBetragWaehrung(final String betragWaehrung) {
        this.betragWaehrung = betragWaehrung;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(final String saldo) {
        this.saldo = saldo;
    }

    public String getSaldoWaehrung() {
        return saldoWaehrung;
    }

    public void setSaldoWaehrung(final String saldoWaehrung) {
        this.saldoWaehrung = saldoWaehrung;
    }

    public String getAuftraggeber() {
        return auftraggeber;
    }

    @Override
    public String toString() {
        return "DibaExportEntry{" +
            "buchung='" + buchung + '\'' +
            ", valuta='" + valuta + '\'' +
            ", auftraggeber='" + auftraggeber + '\'' +
            ", buchungstext='" + buchungstext + '\'' +
            ", verwendungszweck='" + verwendungszweck + '\'' +
            ", betrag='" + betrag + '\'' +
            ", betragWaehrung='" + betragWaehrung + '\'' +
            ", saldo='" + saldo + '\'' +
            ", saldoWaehrung='" + saldoWaehrung + '\'' +
            '}';
    }
}
