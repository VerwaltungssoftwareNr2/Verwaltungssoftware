package com.verwaltungssoftware.database;

import com.verwaltungssoftware.objects.Angebot;
import com.verwaltungssoftware.objects.Artikel;
import com.verwaltungssoftware.objects.Kunde;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.ObservableList;

public interface ISql {

    public ObservableList<Artikel> getDataArtikel();

    public ObservableList<Kunde> getDataKunde();

    public ObservableList<Angebot> getDataRechnung();

    public ObservableList<Angebot> getDataAngebot();

    public ObservableList<Artikel> getDataArtikelInAngebot();

    public ObservableList<Kunde> getDataFilteredKunde();

    public ObservableList<Angebot> getDataFilteredAngebot();

    public ObservableList<Angebot> getDataFilteredRechnung();

    public ObservableList<Artikel> getDataFilteredArtikel();
    
    public void loadDataArtikel() throws SQLException;

    public void loadDataKunde() throws SQLException;

    public void loadDataAngebot() throws SQLException;
    
    public void loadDataRechnung() throws SQLException;

    public void loadArtikelFromAngebot(String nummer) throws SQLException;

    public void safeNewPlz(String p, String o, String l) throws SQLException;

    public void safeNewKunde(String a, String vn, String n, String s, String h, String z, String p, String o, String l) throws SQLException;

    public void safeNewArtikel(String aN, String bez, String z, String ePreis, String vPreis, String mwst, String m, String d, String w) throws SQLException;

    public void safeNewAngebot(String k, String d, String ak, ArrayList<Artikel> art, ArrayList<Integer> m) throws SQLException;
    
    public void safeNewRechnung(String k, String d, String ak, ArrayList<Artikel> art, ArrayList<Integer> m) throws SQLException;

    public void safeArtikelInAngebot(String angebot, String artikel, int menge, boolean alt, double r) throws SQLException;

    public void updateKunde(String attr, String id, String eingabe) throws SQLException;

    public void updateArtikel(String attr, String id, String eingabe) throws SQLException;
    
    public void loadFilteredAngebote(String filter) throws SQLException;
    
    public void loadFilteredRechnung(String filter) throws SQLException;
    
    public void loadFilteredKunden(String filter) throws SQLException;
    
    public void loadFilteredArtikel(String filter) throws SQLException;

}
