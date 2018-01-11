package com.Verwaltungssoftware.tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Lucas
 */
import com.verwaltungssoftware.database.ISql;
import com.verwaltungssoftware.database.SqlConnector;
import com.verwaltungssoftware.objects.Kunde;
import com.verwaltungssoftware.objects.Artikel;
import com.verwaltungssoftware.objects.Angebot;
import com.verwaltungssoftware.objects.User;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
public class SQLTests{
   static ISql sql = new SqlConnector("root", "lucas");
   static int counterR = 0;
   static int counterF = 0;
   static DateTimeFormatter dateTf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
   static LocalDate ld = LocalDate.now();
   static Label datumL = new Label(ld.format(dateTf));
    
        public static void main(String[] args) {
            testString("root", sql.getUsername(), "getUsername"); //getUsername()
            testString("lucas", sql.getPassword(), "getPassword"); //getPassword()
            try{
            testString("", sql.generateRandomOfferNumber(ld.toString()),"RandomOfferNumber"); //generateRandomOfferNumber() CHECK WIE EINE NUMMER AUSSIEHT
            }catch(Exception ex){
                System.out.println("Test fehlgeschlagen. Fehler: "+ ex.getMessage());
            }
            try{
            testString("", sql.generateRandomBillNumber(ld.toString()),"RandomBillNumber"); //generateRandomBillNumber() CHECK WIE EINE NUMMER AUSSIEHT
            }catch(Exception ex){
                System.out.println("Test fehlgeschlagen. Fehler: "+ ex.getMessage());
            }
            
            /*sql.safeNewArtikel(aN, bez, z, ePreis, vPreis, mwst, m, d, w);
            sql.loadDataArtikel();
            safeNewArtikelTest(sql.getDataArtikel().size(), 1);
            */
        }
    
    public static void testString(String haben, String soll, String typ){
        if(haben.equals(soll)){
            System.out.println(typ+"-Test erfolgreich");
            counterR++;
        }else{
            System.out.println(typ+"-Test fehlgeschlagen");
            counterF++;
        }
    }
    
    public static void getterTestBool(Boolean haben, Boolean soll, String typ){
        if(haben.equals(soll)){
            System.out.println(typ+"-Test erfolgreich");
            counterR++;
        }else{
            System.out.println(typ+"-Test fehlgeschlagen");
            counterF++;
        }
    }
    
    public static void getDataArtikelTest(ObservableList<Artikel> haben, ObservableList<Artikel> soll){ //vor dem test muss haben liste erstellt werden und artikel hinzugefügt werden(egal was drin ist), hier geht es nur ums zurückgeben
        
        //soll wird hier manuell alle artikel eingefügt, die in haben sind (copy für alle getData-Methoden) , unterschied zu loaddataartikel --> ich überprüfe nur die stellen, beim anderen auch was spezifisch in den stellen ist
        
        if(haben.equals(soll)){
            //
            System.out.println("getDataArtikel-Test erfolgreich");
            counterR++;
        }else{
            System.out.println("getDataArtikel-Test fehlgeschlagen");
            counterF++;
        }
        
    }
    public static void loadDataArtikelTest(){
        ObservableList<Artikel> haben = null;
        ObservableList<Artikel> soll = null; //beschreibung oben
        
        if(haben.equals(soll)){
            //
            System.out.println("getDataArtikel-Test erfolgreich");
            counterR++;
        }else{
            System.out.println("getDataArtikel-Test fehlgeschlagen");
            counterF++;
        }
        
    }
    public static void safeNewArtikel(String a, String vn, String n, String s, String h, String z, String p, String o, String l){
        //ich erstelle einen neuen artikel, packe ihn in die datenbank und checke dann ob er auch tatsächlich dort ist, wenn ja --> test erfolgreich
    }
    
    public static void updateArtikelTest(String attr, String id, String eingabe){
        //eingaben werden in die datenbank getragen, wenn es richtig geupdatet ist --> test erfolgreich
    }
    
    public static void loadFilteredArtikelTest(){
        //??????????????????????????????????????
    }
}
