package com.verwaltungssoftware.objects;

import javafx.beans.property.SimpleStringProperty;

public class Artikel {

    private final SimpleStringProperty artikelnummer, bezeichnung,
            zusatztext, einkaufspreis, verkaufspreis,
            mwst, bestand, datum, 
            alternative, rabattmenge, warengruppe;

    public Artikel(String aNummer, String bez, String extra, 
            String ePreis, String vPreis, String m, String num, 
            String d, String alt, String rb, String w) {

        artikelnummer = new SimpleStringProperty(aNummer);
        bezeichnung = new SimpleStringProperty(bez);
        zusatztext = new SimpleStringProperty(extra);
        einkaufspreis = new SimpleStringProperty(ePreis);
        verkaufspreis = new SimpleStringProperty(vPreis);
        mwst = new SimpleStringProperty(m);
        bestand = new SimpleStringProperty(num);
        datum = new SimpleStringProperty(d);
        alternative = new SimpleStringProperty(alt);
        rabattmenge = new SimpleStringProperty(rb);
        warengruppe = new SimpleStringProperty(w);
    }

    //getter and setter
    public String getArtikelnummer(){
        return artikelnummer.get();
    }
    
    public void setArtikelnummer(String a){
        artikelnummer.set(a);
    }
    
    public String getBezeichnung(){
        return bezeichnung.get();
    }
    
    public void setBezeichnung(String bez){
        bezeichnung.set(bez);
    }
    
    public String getZusatztext(){
        return zusatztext.get();
    }
    
    public void setZusatzstext(String z){
        zusatztext.set(z);
    }
    
    public String getEinkaufspreis(){
        return einkaufspreis.get();
    }
    
    public void setEinkaufspreis(String e){
        einkaufspreis.set(e);
    }
    
    public String getVerkaufspreis(){
        return verkaufspreis.get();
    }
    
    public void setVerkaufspreis(String v){
        verkaufspreis.set(v);
    }
    
    public String getMwst(){
        return mwst.get();
    }
    
    public void setMwst(String m){
        mwst.set(m);
    }
    
    public String getBestand(){
        return bestand.get();
    }
    
    public void setBestand(String m){
        bestand.set(m);
    }
    
    public String getDatum(){
        return datum.get();
    }
    
    public void setDatum(String d){
        datum.set(d);
    }
    
    public String getAlternative(){
        return alternative.get();
    }
    
    public void setAlternative(String a){
        alternative.set(a);
    }
    
    public String getRabattmenge(){
        return rabattmenge.get();
    }
    
    public void setRabattmenge(String rb){
        rabattmenge.set(rb);
    }
    
    public String getWarengruppe(){
        return warengruppe.get();
    }
    
    public void setWarengruppe(String w){
        warengruppe.set(w);
    }
}