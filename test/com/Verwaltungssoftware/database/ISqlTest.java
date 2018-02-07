/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Verwaltungssoftware.database;

import com.verwaltungssoftware.database.SqlConnector;
import com.verwaltungssoftware.database.ISql;
import com.verwaltungssoftware.objects.Angebot;
import com.verwaltungssoftware.objects.Artikel;
import com.verwaltungssoftware.objects.Kunde;
import com.verwaltungssoftware.objects.User;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lucas
 */
public class ISqlTest {
    
    public ISqlTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    /**
     * Test of generateRandomOfferNumber method, of class ISql.
     */
    @Test
    public void testGenerateRandomOfferNumber() throws Exception {
        System.out.println("generateRandomOfferNumber");
        DateTimeFormatter dateTf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate ld = LocalDate.now();
        String datum = ld.toString();
        ISql instance = new SqlConnector("root", "lucas");
        String expResult = "2018-oooo5-A";
        String result = instance.generateRandomOfferNumber(datum);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of generateRandomBillNumber method, of class ISql.
     */
    @Test
    public void testGenerateRandomBillNumber() throws Exception {
        System.out.println("generateRandomBillNumber");
        String datum = "";
        ISql instance = new SqlConnector("root", "lucas");
        String expResult = "";
        String result = instance.generateRandomBillNumber(datum);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadDataWarengruppe method, of class ISql.
     */
    @Test
    public void testLoadDataWarengruppe() throws Exception {
        System.out.println("loadDataWarengruppe");
        ISql instance = new SqlConnector("root", "lucas");
        instance.loadDataWarengruppe();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadDataArtikel method, of class ISql.
     */
    @Test
    public void testLoadDataArtikel_0args() throws Exception {
        System.out.println("loadDataArtikel");
        ISql instance = new SqlConnector("root", "lucas");
        instance.loadDataArtikel();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadDataArtikel method, of class ISql.
     */
    @Test
    public void testLoadDataArtikel_String() throws Exception {
        System.out.println("loadDataArtikel");
        String artNummer = "";
        ISql instance = new SqlConnector("root", "lucas");
        Artikel expResult = null;
        Artikel result = instance.loadDataArtikel(artNummer);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadDataKunde method, of class ISql.
     */
    @Test
    public void testLoadDataKunde_0args() throws Exception {
        System.out.println("loadDataKunde");
        ISql instance = new SqlConnector("root", "lucas");
        instance.loadDataKunde();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadDataKunde method, of class ISql.
     */
    @Test
    public void testLoadDataKunde_String() throws Exception {
        System.out.println("loadDataKunde");
        String kNummer = "";
        ISql instance = new SqlConnector("root", "lucas");
        Kunde expResult = null;
        Kunde result = instance.loadDataKunde(kNummer);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadDataAngebot method, of class ISql.
     */
    @Test
    public void testLoadDataAngebot_boolean() throws Exception {
        System.out.println("loadDataAngebot");
        boolean all = false;
        ISql instance = new SqlConnector("root", "lucas");
        instance.loadDataAngebot(all);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadDataAngebot method, of class ISql.
     */
    @Test
    public void testLoadDataAngebot_String() throws Exception {
        System.out.println("loadDataAngebot");
        String aNummer = "";
        ISql instance = new SqlConnector("root", "lucas");
        Angebot expResult = null;
        Angebot result = instance.loadDataAngebot(aNummer);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadDataRechnung method, of class ISql.
     */
    @Test
    public void testLoadDataRechnung() throws Exception {
        System.out.println("loadDataRechnung");
        boolean all = false;
        ISql instance = new SqlConnector("root", "lucas");
        instance.loadDataRechnung(all);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadArtikelFromAngebot method, of class ISql.
     */
    @Test
    public void testLoadArtikelFromAngebot() throws Exception {
        System.out.println("loadArtikelFromAngebot");
        String nummer = "";
        ISql instance = new SqlConnector("root", "lucas");
        instance.loadArtikelFromAngebot(nummer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteKunde method, of class ISql.
     */
    @Test
    public void testDeleteKunde() throws Exception {
        System.out.println("deleteKunde");
        String kNummer = "";
        ISql instance = new SqlConnector("root", "lucas");
        instance.deleteKunde(kNummer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteAngebot method, of class ISql.
     */
    @Test
    public void testDeleteAngebot() throws Exception {
        System.out.println("deleteAngebot");
        String aNummer = "";
        ISql instance = new SqlConnector("root", "lucas");
        instance.deleteAngebot(aNummer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of safeNewKunde method, of class ISql.
     */
    @Test
    public void testSafeNewKunde() throws Exception {
        System.out.println("safeNewKunde");
        String a = "";
        String vn = "";
        String n = "";
        String s = "";
        String h = "";
        String z = "";
        String p = "";
        String o = "";
        String l = "";
        ISql instance = new SqlConnector("root", "lucas");
        instance.safeNewKunde(a, vn, n, s, h, z, p, o, l);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateKunde method, of class ISql.
     */
    @Test
    public void testUpdateKunde() throws Exception {
        System.out.println("updateKunde");
        String kNummer = "";
        String a = "";
        String vn = "";
        String n = "";
        String s = "";
        String h = "";
        String z = "";
        String p = "";
        String o = "";
        String l = "";
        ISql instance = new SqlConnector("root", "lucas");
        instance.updateKunde(kNummer, a, vn, n, s, h, z, p, o, l);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of safeNewWarengruppe method, of class ISql.
     */
    @Test
    public void testSafeNewWarengruppe() throws Exception {
        System.out.println("safeNewWarengruppe");
        String w = "";
        ISql instance = new SqlConnector("root", "lucas");
        instance.safeNewWarengruppe(w);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteWarengruppe method, of class ISql.
     */
    @Test
    public void testDeleteWarengruppe() throws Exception {
        System.out.println("deleteWarengruppe");
        String w = "";
        ISql instance = new SqlConnector("root", "lucas");
        instance.deleteWarengruppe(w);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of safeNewArtikel method, of class ISql.
     */
    @Test
    public void testSafeNewArtikel() throws Exception {
        System.out.println("safeNewArtikel");
        String aN = "";
        String bez = "";
        String z = "";
        String ePreis = "";
        String vPreis = "";
        String mwst = "";
        String m = "";
        String d = "";
        String w = "";
        ISql instance = new SqlConnector("root", "lucas");
        instance.safeNewArtikel(aN, bez, z, ePreis, vPreis, mwst, m, d, w);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of safeNewAngebot method, of class ISql.
     */
    @Test
    public void testSafeNewAngebot() throws Exception {
        System.out.println("safeNewAngebot");
        String aNummer = "";
        String kNummer = "";
        ObservableList<Artikel> artInAng = null;
        double nettoBetrag = 0.0;
        double bruttoBetrag = 0.0;
        double mwst = 0.0;
        double skontoPr = 0.0;
        double skontoBetrag = 0.0;
        String faktura = "";
        String hinweis = "";
        int zZ = 0;
        int skontoT = 0;
        ISql instance = new SqlConnector("root", "lucas");
        instance.safeNewAngebot(aNummer, kNummer, artInAng, nettoBetrag, bruttoBetrag, mwst, skontoPr, skontoBetrag, faktura, hinweis, zZ, skontoT);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of safeNewRechnung method, of class ISql.
     */
    @Test
    public void testSafeNewRechnung() throws Exception {
        System.out.println("safeNewRechnung");
        String rNummer = "";
        String kNummer = "";
        ObservableList<Artikel> artInAng = null;
        double nettoBetrag = 0.0;
        double bruttoBetrag = 0.0;
        double mwst = 0.0;
        double skontoPr = 0.0;
        double skontoBetrag = 0.0;
        String faktura = "";
        String hinweis = "";
        int zZ = 0;
        int skontoT = 0;
        ISql instance = new SqlConnector("root", "lucas");
        instance.safeNewRechnung(rNummer, kNummer, artInAng, nettoBetrag, bruttoBetrag, mwst, skontoPr, skontoBetrag, faktura, hinweis, zZ, skontoT);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of safeArtikelInAngebot method, of class ISql.
     */
    @Test
    public void testSafeArtikelInAngebot_5args() throws Exception {
        System.out.println("safeArtikelInAngebot");
        String angebot = "";
        String artikel = "";
        int menge = 0;
        boolean alt = false;
        double r = 0.0;
        ISql instance = new SqlConnector("root", "lucas");
        instance.safeArtikelInAngebot(angebot, artikel, menge, alt, r);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of safeArtikelInAngebot method, of class ISql.
     */
    @Test
    public void testSafeArtikelInAngebot_4args() throws Exception {
        System.out.println("safeArtikelInAngebot");
        String angebot = "";
        String artikel = "";
        int menge = 0;
        boolean alt = false;
        ISql instance = new SqlConnector("root", "lucas");
        instance.safeArtikelInAngebot(angebot, artikel, menge, alt);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of safeArtikelInRechnung method, of class ISql.
     */
    @Test
    public void testSafeArtikelInRechnung_5args() throws Exception {
        System.out.println("safeArtikelInRechnung");
        String rechnung = "";
        String artikel = "";
        int menge = 0;
        boolean alt = false;
        double r = 0.0;
        ISql instance = new SqlConnector("root", "lucas");
        instance.safeArtikelInRechnung(rechnung, artikel, menge, alt, r);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of safeArtikelInRechnung method, of class ISql.
     */
    @Test
    public void testSafeArtikelInRechnung_4args() throws Exception {
        System.out.println("safeArtikelInRechnung");
        String rechnung = "";
        String artikel = "";
        int menge = 0;
        boolean alt = false;
        ISql instance = new SqlConnector("root", "lucas");;
        instance.safeArtikelInRechnung(rechnung, artikel, menge, alt);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateArtikelVerkaufsPreis method, of class ISql.
     */
    @Test
    public void testUpdateArtikelVerkaufsPreis() throws Exception {
        System.out.println("updateArtikelVerkaufsPreis");
        String id = "";
        String eingabe = "";
        ISql instance = new SqlConnector("root", "lucas");
        instance.updateArtikelVerkaufsPreis(id, eingabe);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateArtikelNummer method, of class ISql.
     */
    @Test
    public void testUpdateArtikelNummer() throws Exception {
        System.out.println("updateArtikelNummer");
        String oldId = "";
        String newId = "";
        ISql instance = new SqlConnector("root", "lucas");
        instance.updateArtikelNummer(oldId, newId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateArtikel method, of class ISql.
     */
    @Test
    public void testUpdateArtikel() throws Exception {
        System.out.println("updateArtikel");
        String oldId = "";
        String newId = "";
        String bez = "";
        String z = "";
        String ePreis = "";
        String vPreis = "";
        String mwst = "";
        String m = "";
        String w = "";
        ISql instance = new SqlConnector("root", "lucas");
        instance.updateArtikel(oldId, newId, bez, z, ePreis, vPreis, mwst, m, w);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadUser method, of class ISql.
     */
    @Test
    public void testLoadUser() throws Exception {
        System.out.println("loadUser");
        ISql instance = new SqlConnector("root", "lucas");
        User expResult = null;
        User result = instance.loadUser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadFilteredAngebote method, of class ISql.
     */
    @Test
    public void testLoadFilteredAngebote() throws Exception {
        System.out.println("loadFilteredAngebote");
        String filter = "";
        boolean all = false;
        ISql instance = new SqlConnector("root", "lucas");
        instance.loadFilteredAngebote(filter, all);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadFilteredRechnung method, of class ISql.
     */
    @Test
    public void testLoadFilteredRechnung() throws Exception {
        System.out.println("loadFilteredRechnung");
        String filter = "";
        boolean all = false;
        ISql instance = new SqlConnector("root", "lucas");
        instance.loadFilteredRechnung(filter, all);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadFilteredKunden method, of class ISql.
     */
    @Test
    public void testLoadFilteredKunden() throws Exception {
        System.out.println("loadFilteredKunden");
        String filter = "";
        ISql instance = new SqlConnector("root", "lucas");
        instance.loadFilteredKunden(filter);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadFilteredArtikel method, of class ISql.
     */
    @Test
    public void testLoadFilteredArtikel() throws Exception {
        System.out.println("loadFilteredArtikel");
        String filter = "";
        ISql instance = new SqlConnector("root", "lucas");
        instance.loadFilteredArtikel(filter);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkUserConfig method, of class ISql.
     */
    @Test
    public void testCheckUserConfig() throws Exception {
        System.out.println("checkUserConfig");
        ISql instance = new SqlConnector("root", "lucas");
        instance.checkUserConfig();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createUserConfig method, of class ISql.
     */
    @Test
    public void testCreateUserConfig() throws Exception {
        System.out.println("createUserConfig");
        User user = null;
        ISql instance = new SqlConnector("root", "lucas");
        instance.createUserConfig(user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateUserConfig method, of class ISql.
     */
    @Test
    public void testUpdateUserConfig() throws Exception {
        System.out.println("updateUserConfig");
        User user = null;
        ISql instance = new SqlConnector("root", "lucas");
        instance.updateUserConfig(user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}

  