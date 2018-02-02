package com.verwaltungssoftware.objects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;

public class Angebot {

    private final SimpleStringProperty angebotsnummer, kunde, datum, akzeptiert;
    private double nettoBetrag, bruttoBetrag, mwSt, skontoBetrag, skontoProzent;
    private int zahlungsZiel, skontoTage;
    String fakturaText;
    //private final ArrayList<Artikel> waren;

    public Angebot(String aNummer, String k, String date, String a, double netto, double brutto,
            double mwSt, double skontoB, double skontoPr, int zZ, int skontoT, String fakt) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate aDatum = LocalDate.parse(date);
        angebotsnummer = new SimpleStringProperty(aNummer);
        kunde = new SimpleStringProperty(k);
        datum = new SimpleStringProperty(dtf.format(aDatum));
        akzeptiert = new SimpleStringProperty(a);
        nettoBetrag = netto;
        bruttoBetrag = brutto;
        this.mwSt = mwSt;
        skontoBetrag = skontoB;
        skontoProzent = skontoPr;
        zahlungsZiel = zZ;
        skontoTage = skontoT;
        fakturaText = fakt;
        
        //waren = new ArrayList<>();
    }

    public void setAngebotsnummer(String a) {
        angebotsnummer.set(a);
    }

    public String getAngebotsnummer() {
        return angebotsnummer.get();
    }

    public void setDatum(String d) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate aDatum = LocalDate.parse(d);
        datum.set(aDatum.format(dtf));
    }

    public String getDatum() {
        return datum.get();
    }

    public void setKunde(String k) {
        kunde.set(k);
    }

    public String getKunde() {
        return kunde.get();
    }

    public void setAkzeptiert(String a) {
        akzeptiert.set(a);
    }

    public String getAkzeptiert() {
        return akzeptiert.get();
    }
    
    public double getNettoBetrag() {
        return nettoBetrag;
    }

    public void setNettoBetrag(double nettoBetrag) {
        this.nettoBetrag = nettoBetrag;
    }

    public double getBruttoBetrag() {
        return bruttoBetrag;
    }

    public void setBruttoBetrag(double bruttoBetrag) {
        this.bruttoBetrag = bruttoBetrag;
    }

    public double getMwSt() {
        return mwSt;
    }

    public void setMwSt(double mwSt) {
        this.mwSt = mwSt;
    }

    public double getSkontoBetrag() {
        return skontoBetrag;
    }

    public void setSkontoBetrag(double skontoBetrag) {
        this.skontoBetrag = skontoBetrag;
    }

    public double getSkontoProzent() {
        return skontoProzent;
    }

    public void setSkontoProzent(double skontoProzen) {
        this.skontoProzent = skontoProzen;
    }

    public int getZahlungsZiel() {
        return zahlungsZiel;
    }

    public void setZahlungsZiel(int zahlungsZiel) {
        this.zahlungsZiel = zahlungsZiel;
    }

    public int getSkontoTage() {
        return skontoTage;
    }

    public void setSkontoTage(int skontoTage) {
        this.skontoTage = skontoTage;
    }

    public String getFakturaText() {
        return fakturaText;
    }

    public void setFakturaText(String fakturaText) {
        this.fakturaText = fakturaText;
    }
    /*public void addArtikel(Artikel a){
        waren.add(a);
    }
    
    public Artikel getArtikel(int index){
        return waren.get(index);
    }*/
}
