package com.verwaltungssoftware.database;

import com.verwaltungssoftware.objects.Angebot;
import com.verwaltungssoftware.objects.Artikel;
import com.verwaltungssoftware.objects.Kunde;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.ObservableList;

public interface ISql {

    public boolean getAuthentication();

    public String getUsername();

    public String getPassword();

    public ObservableList<Artikel> getDataArtikel();

    public ObservableList<Kunde> getDataKunde();

    public ObservableList<Angebot> getDataRechnung();

    public ObservableList<Angebot> getDataAngebot();

    public ObservableList<Artikel> getDataArtikelInAngebot();

    public ObservableList<Kunde> getDataFilteredKunde();

    public ObservableList<Angebot> getDataFilteredAngebot();

    public ObservableList<Angebot> getDataFilteredRechnung();

    public ObservableList<Artikel> getDataFilteredArtikel();

    /**
     * Generiert neue Angebotsnummer anhand des Datums
     *
     * @param datum - Das aktuelle Datum
     * @return neue Angebotsnummer
     * @throws SQLException
     */
    public String generateRandomOfferNumber(String datum) throws SQLException;

    /**
     * Generiert neue Rechnungsnummer anhand des Datums
     *
     * @param datum - Das aktuelle Datum
     * @return neue Rechnungsnummer
     * @throws SQLException
     */
    public String generateRandomBillNumber(String datum) throws SQLException;

    /**
     * Lädt alle vorhandenen Artikel der Datenbank
     *
     * @throws SQLException
     */
    public void loadDataArtikel() throws SQLException;

    /**
     * Lädt alle vorhandenen Kunden der Datenbank
     *
     * @throws SQLException
     */
    public void loadDataKunde() throws SQLException;

    /**
     * Lädt alle vorhandenen Angebote der Datenbank
     *
     * @param all - wenn false, dann werden auch Rechnungen geladen
     * @throws SQLException
     */
    public void loadDataAngebot(boolean all) throws SQLException;

    /**
     * Lädt alle vorhandenen Rechnungen der Datenbank
     *
     * @param all - wenn false, dann werden auch Angebote geladen
     * @throws SQLException
     */
    public void loadDataRechnung(boolean all) throws SQLException;

    /**
     * Lädt alle Artikel für ein spezifisches Angebot der Datenbank
     *
     * @param nummer - die Angebotsnummer
     * @throws SQLException
     */
    public void loadArtikelFromAngebot(String nummer) throws SQLException;

    /**
     * Erstellt neuen Kunden in der Datenbank
     * @param a - Anrede
     * @param vn - Vorname
     * @param n - Nachname
     * @param s - Straße
     * @param h - Hausnummer
     * @param z - Zusatz
     * @param p - Postleitzahl
     * @param o - Ort
     * @param l - Land
     * @throws SQLException
     */
    public void safeNewKunde(String a, String vn, String n, String s, String h, String z, String p, String o, String l) throws SQLException;

    /**
     * Erstellt neues Artikel in der Datenbank
     * @param aN - Artikelnummer
     * @param bez - Bezeichnung
     * @param z - Zusatz
     * @param ePreis - Einkaufspreis
     * @param vPreis - Verkaufspreis
     * @param mwst - Mehrwertsteuer
     * @param m - Bestand
     * @param d - Datum
     * @param w - Warengruppe
     * @throws SQLException 
     */
    public void safeNewArtikel(String aN, String bez, String z, String ePreis, String vPreis, String mwst, String m, String d, String w) throws SQLException;

    /**
     * Erstellt neues Angebot in der Datenbank
     * @param k - Kunde
     * @param d - Datum
     * @param ak - akzeptiert
     * @param art - ArrayList mit allen Artikeln
     * @param m - Menge der Artikel im Angebot
     * @throws SQLException 
     */
    public void safeNewAngebot(String k, String d, String ak, ArrayList<Artikel> art, ArrayList<Integer> m) throws SQLException;

    /**
     * Erstellt neue Rechnung in der Datenbank
     * @param k - Kunde
     * @param d - Datum
     * @param ak - akzeptiert
     * @param art - ArrayList mit allen Artikeln
     * @param m - Menge der Artikel im Rechnung
     * @throws SQLException 
     */
    public void safeNewRechnung(String k, String d, String ak, ArrayList<Artikel> art, ArrayList<Integer> m) throws SQLException;

    /**
     * Speichert einen Artikel in ein Angebot
     * @param angebot
     * @param artikel
     * @param menge
     * @param alt
     * @param r
     * @throws SQLException 
     */
    public void safeArtikelInAngebot(String angebot, String artikel, int menge, boolean alt, double r) throws SQLException;

    public void updateKunde(String attr, String id, String eingabe) throws SQLException;

    public void updateArtikel(String attr, String id, String eingabe) throws SQLException;

    /**
     * Lädt alle Angebote die dem Filter entsprechen
     * @param filter - String um Angebote zu filtern
     * @param all - wenn false dann auch Rechnungen mitladen
     * @throws SQLException 
     */
    public void loadFilteredAngebote(String filter, boolean all) throws SQLException;

    /**
     * Lädt alle Rechnungen die dem Filter entsprechen
     * @param filter - String um Angebote zu filtern
     * @param all - wenn false dann auch Angebote mitladen
     * @throws SQLException 
     */
    public void loadFilteredRechnung(String filter, boolean all) throws SQLException;

    /**
     * Lädt alle Kunden die dem Filter entsprechen
     * @param filter - String um Kunden zu filtern
     * @throws SQLException 
     */
    public void loadFilteredKunden(String filter) throws SQLException;

    /**
     * Lädt alle Artikel die dem Filter entsprechen
     * @param filter - String um Artikel zu filtern
     * @throws SQLException 
     */
    public void loadFilteredArtikel(String filter) throws SQLException;

}
