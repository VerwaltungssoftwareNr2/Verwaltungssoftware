package com.verwaltungssoftware.database;

import com.verwaltungssoftware.GUI.ConfirmBox;
import com.verwaltungssoftware.objects.Kunde;
import com.verwaltungssoftware.objects.Artikel;
import com.verwaltungssoftware.objects.Angebot;
import com.verwaltungssoftware.objects.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Implementiert alle Methoden zum laden und speichern der Daten aus der
 * Datenbank
 *
 * @author Marcel
 */
public class SqlConnector implements ISql {

    Properties userInfo;
    private boolean authentication;
    private boolean checkUserConfig;
    private String username;
    private String password;
    final private ObservableList<String> dataWarengruppe;
    final private ObservableList<Artikel> dataArtikel;
    final private ObservableList<Kunde> dataKunde;
    final private ObservableList<Angebot> dataAngebot;
    final private ObservableList<Angebot> dataRechnung;
    final private ObservableList<Artikel> dataArtikelInAngebot;
    final private ObservableList<Kunde> dataFilteredKunde;
    final private ObservableList<Angebot> dataFilteredAngebot;
    final private ObservableList<Angebot> dataFilteredRechnung;
    final private ObservableList<Artikel> dataFilteredArtikel;

    public SqlConnector(String username, String password) {
        this.authentication = false;
        this.username = username;
        this.password = password;
        userInfo = new Properties();
        userInfo.put("user", this.username);
        userInfo.put("password", this.password);
        //Test noch in extra private Methode
        try (Connection testConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo)) {
            this.authentication = true;
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Login nicht erfolgreich. Überprüfen sie Username und Passwort!");
            System.out.println(exc.getMessage() + "tryConn");
        }
        dataWarengruppe = FXCollections.observableArrayList();
        dataArtikel = FXCollections.observableArrayList();
        dataKunde = FXCollections.observableArrayList();
        dataAngebot = FXCollections.observableArrayList();
        dataRechnung = FXCollections.observableArrayList();
        dataArtikelInAngebot = FXCollections.observableArrayList();
        dataFilteredKunde = FXCollections.observableArrayList();
        dataFilteredAngebot = FXCollections.observableArrayList();
        dataFilteredRechnung = FXCollections.observableArrayList();
        dataFilteredArtikel = FXCollections.observableArrayList();
    }

    @Override
    public boolean getCheckUserConfig() {
        return this.checkUserConfig;
    }

    @Override
    public boolean getAuthentication() {
        return this.authentication;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public ObservableList<String> getDataWarengruppe() {
        return dataWarengruppe;
    }

    @Override
    public ObservableList<Artikel> getDataArtikel() {
        return dataArtikel;
    }

    @Override
    public ObservableList<Kunde> getDataKunde() {
        return dataKunde;
    }

    @Override
    public ObservableList<Angebot> getDataRechnung() {
        return dataRechnung;
    }

    @Override
    public ObservableList<Angebot> getDataAngebot() {
        return dataAngebot;
    }

    @Override
    public ObservableList<Artikel> getDataArtikelInAngebot() {
        return dataArtikelInAngebot;
    }

    @Override
    public ObservableList<Kunde> getDataFilteredKunde() {
        return dataFilteredKunde;
    }

    @Override
    public ObservableList<Angebot> getDataFilteredAngebot() {
        return dataFilteredAngebot;
    }

    @Override
    public ObservableList<Angebot> getDataFilteredRechnung() {
        return dataFilteredRechnung;
    }

    @Override
    public ObservableList<Artikel> getDataFilteredArtikel() {
        return dataFilteredArtikel;
    }

    @Override
    public void checkUserConfig() throws SQLException {
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                Statement stmtCheck = myConn.createStatement();
                ResultSet rsCheck = stmtCheck.executeQuery("SELECT table_name "
                        + "FROM information_schema.tables "
                        + "WHERE table_schema = 'verwaltungssoftware' "
                        + "AND table_name = 'user_config';")) {

            String tempString = null;
            while (rsCheck.next()) {
                tempString = rsCheck.getString("table_name");
            }
            if (tempString != null) {
                checkUserConfig = true;
            }

        }
    }

    @Override
    public void createUserConfig(User user) throws SQLException {
        String insertString = "insert into User_config(BID, bankName, kontoNr, blz, steuerNummer, ustId, company, street, town, country, companyNo,"
                + "preName, lastName, aStreet, aPlz, aLand, aOrt, aTel, aFax, aBankName, aBic, aIban, aAmt, aHrb) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,? ,?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                Statement stmtCreate = myConn.createStatement();
                PreparedStatement stmtInsert = myConn.prepareStatement(insertString)) {

            System.out.println("vorher");
            stmtCreate.executeUpdate("CREATE TABLE User_config("
                    + "    BID INTEGER PRIMARY KEY,"
                    + "    bankName VARCHAR(50) NOT NULL,"
                    + "    kontoNr VARCHAR(50) NOT NULL,"
                    + "    blz VARCHAR(50) NOT NULL,"
                    + "    steuerNummer VARCHAR(50) NOT NULL,"
                    + "    ustId VARCHAR(50) NOT NULL,"
                    + "    company VARCHAR(50) NOT NULL,"
                    + "    street VARCHAR(50) NOT NULL,"
                    + "    town VARCHAR(50) NOT NULL,"
                    + "    country VARCHAR(50) NOT NULL,"
                    + "    companyNo VARCHAR(50) NOT NULL,"
                    + "    preName VARCHAR(50) NOT NULL,"
                    + "    lastName VARCHAR(50) NOT NULL,"
                    + "    aStreet VARCHAR(50) NOT NULL,"
                    + "    aPlz VARCHAR(50) NOT NULL,"
                    + "    aLand VARCHAR(50) NOT NULL,"
                    + "    aOrt VARCHAR(50) NOT NULL,"
                    + "    aTel VARCHAR(50) NOT NULL,"
                    + "    aFax VARCHAR(50) NOT NULL,"
                    + "    aBankName VARCHAR(50) NOT NULL,"
                    + "    aBic VARCHAR(50) NOT NULL,"
                    + "    aIban VARCHAR(50) NOT NULL,"
                    + "    aAmt VARCHAR(50) NOT NULL,"
                    + "    aHrb VARCHAR(50) NOT NULL);");
            System.out.println("nachher");
            stmtInsert.setInt(1, 1);
            stmtInsert.setString(2, user.getBankName());
            stmtInsert.setString(3, user.getKontoNr());
            stmtInsert.setString(4, user.getBlz());
            stmtInsert.setString(5, user.getSteuerNummer());
            stmtInsert.setString(6, user.getUstId());
            stmtInsert.setString(7, user.getCompany());
            stmtInsert.setString(8, user.getStreet());
            stmtInsert.setString(9, user.getTown());
            stmtInsert.setString(10, user.getCountry());
            stmtInsert.setString(11, user.getCompanyNo());
            stmtInsert.setString(12, user.getPreName());
            stmtInsert.setString(13, user.getLastName());
            stmtInsert.setString(14, user.getaStreet());
            stmtInsert.setString(15, user.getaPlz());
            stmtInsert.setString(16, user.getaLand());
            stmtInsert.setString(17, user.getaOrt());
            stmtInsert.setString(18, user.getaTel());
            stmtInsert.setString(19, user.getaFax());
            stmtInsert.setString(20, user.getaBankName());
            stmtInsert.setString(21, user.getaBic());
            stmtInsert.setString(22, user.getaIban());
            stmtInsert.setString(23, user.getaAmt());
            stmtInsert.setString(24, user.getaHrb());

            stmtInsert.executeUpdate();
        }
    }

    @Override
    public void updateUserConfig(User user) throws SQLException {
        String insertString = "insert into User_config(BID, bankName, kontoNr, blz, steuerNummer, ustId, company, street, town, country, companyNo,"
                + "preName, lastName, aStreet, aPlz, aLand, aOrt, aTel, aFax, aBankName, aBic, aIban, aAmt, aHrb) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,? ,?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                Statement stmtDelete = myConn.createStatement();
                Statement stmtCreate = myConn.createStatement();
                PreparedStatement stmtInsert = myConn.prepareStatement(insertString)) {

            stmtDelete.executeUpdate("drop table User_config;");

            stmtCreate.executeUpdate("CREATE TABLE User_config("
                    + "    BID INTEGER PRIMARY KEY,"
                    + "    bankName VARCHAR(50) NOT NULL,"
                    + "    kontoNr VARCHAR(50) NOT NULL,"
                    + "    blz VARCHAR(50) NOT NULL,"
                    + "    steuerNummer VARCHAR(50) NOT NULL,"
                    + "    ustId VARCHAR(50) NOT NULL,"
                    + "    company VARCHAR(50) NOT NULL,"
                    + "    street VARCHAR(50) NOT NULL,"
                    + "    town VARCHAR(50) NOT NULL,"
                    + "    country VARCHAR(50) NOT NULL,"
                    + "    companyNo VARCHAR(50) NOT NULL,"
                    + "    preName VARCHAR(50) NOT NULL,"
                    + "    lastName VARCHAR(50) NOT NULL,"
                    + "    aStreet VARCHAR(50) NOT NULL,"
                    + "    aPlz VARCHAR(50) NOT NULL,"
                    + "    aLand VARCHAR(50) NOT NULL,"
                    + "    aOrt VARCHAR(50) NOT NULL,"
                    + "    aTel VARCHAR(50) NOT NULL,"
                    + "    aFax VARCHAR(50) NOT NULL,"
                    + "    aBankName VARCHAR(50) NOT NULL,"
                    + "    aBic VARCHAR(50) NOT NULL,"
                    + "    aIban VARCHAR(50) NOT NULL,"
                    + "    aAmt VARCHAR(50) NOT NULL,"
                    + "    aHrb VARCHAR(50) NOT NULL);");
            stmtInsert.setInt(1, 1);
            stmtInsert.setString(2, user.getBankName());
            stmtInsert.setString(3, user.getKontoNr());
            stmtInsert.setString(4, user.getBlz());
            stmtInsert.setString(5, user.getSteuerNummer());
            stmtInsert.setString(6, user.getUstId());
            stmtInsert.setString(7, user.getCompany());
            stmtInsert.setString(8, user.getStreet());
            stmtInsert.setString(9, user.getTown());
            stmtInsert.setString(10, user.getCountry());
            stmtInsert.setString(11, user.getCompanyNo());
            stmtInsert.setString(12, user.getPreName());
            stmtInsert.setString(13, user.getLastName());
            stmtInsert.setString(14, user.getaStreet());
            stmtInsert.setString(15, user.getaPlz());
            stmtInsert.setString(16, user.getaLand());
            stmtInsert.setString(17, user.getaOrt());
            stmtInsert.setString(18, user.getaTel());
            stmtInsert.setString(19, user.getaFax());
            stmtInsert.setString(20, user.getaBankName());
            stmtInsert.setString(21, user.getaBic());
            stmtInsert.setString(22, user.getaIban());
            stmtInsert.setString(23, user.getaAmt());
            stmtInsert.setString(24, user.getaHrb());

            stmtInsert.executeUpdate();
        }
    }

    @Override
    public User loadUser() throws SQLException {
        User user = null;
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                Statement stmtUser = myConn.createStatement();
                ResultSet rsUser = stmtUser.executeQuery("select * from user_config")) {

            while (rsUser.next()) {
                user = new User(rsUser.getString("bankname"),
                        rsUser.getString("kontonr"),
                        rsUser.getString("blz"),
                        rsUser.getString("steuernummer"),
                        rsUser.getString("ustid"),
                        rsUser.getString("company"),
                        rsUser.getString("street"),
                        rsUser.getString("town"),
                        rsUser.getString("country"),
                        rsUser.getString("companyno"),
                        rsUser.getString("prename"),
                        rsUser.getString("lastname"),
                        rsUser.getString("astreet"),
                        rsUser.getString("aplz"),
                        rsUser.getString("aland"),
                        rsUser.getString("aort"),
                        rsUser.getString("atel"),
                        rsUser.getString("afax"),
                        rsUser.getString("abankname"),
                        rsUser.getString("abic"),
                        rsUser.getString("aiban"),
                        rsUser.getString("aamt"),
                        rsUser.getString("ahrb")
                );
            }
        }
        return user;
    }

    @Override
    public ArrayList<ArrayList<String>> loadDataPlz() throws SQLException {
        ArrayList<ArrayList<String>> dataPlz = new ArrayList<>();
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                Statement stmtPlz = myConn.createStatement();
                ResultSet rsPlz = stmtPlz.executeQuery("select * from postleitzahl")) {

            while (rsPlz.next()) {
                ArrayList<String> innerList = new ArrayList<>();
                innerList.add(rsPlz.getString("plz"));
                innerList.add(rsPlz.getString("ort"));
                innerList.add(rsPlz.getString("land"));
                dataPlz.add(innerList);
            }

        }

        return dataPlz;
    }

    @Override
    public void loadDataWarengruppe() throws SQLException {
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                Statement stmtWarengruppe = myConn.createStatement();
                ResultSet rsWarengruppe = stmtWarengruppe.executeQuery("select * from warengruppe")) {

            dataWarengruppe.clear();
            while (rsWarengruppe.next()) {
                dataWarengruppe.add(rsWarengruppe.getString("bezeichnung"));
            }

        }
    }

    @Override
    public void loadDataArtikel() throws SQLException {

        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                Statement stmtArtikel = myConn.createStatement();
                ResultSet rsArtikel = stmtArtikel.executeQuery("select * from artikel left join warengruppe on artikel.warengruppe = warengruppe.wgid;")) {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate ld = null;
            dataArtikel.clear();
            while (rsArtikel.next()) {
                ld = LocalDate.parse(rsArtikel.getString("Datum"));
                dataArtikel.add(new Artikel(
                        rsArtikel.getString("Artikelnummer"),
                        rsArtikel.getString("Artikel.Bezeichnung"),
                        rsArtikel.getString("Zusatztext"),
                        rsArtikel.getString("Einkaufspreis"),
                        rsArtikel.getString("Verkaufspreis"),
                        rsArtikel.getString("Mwst"),
                        rsArtikel.getString("Bestand"),
                        ld.format(dtf),
                        "0",
                        null,
                        rsArtikel.getString("warengruppe.bezeichnung")));
            }

        } catch (SQLException exc) {
            throw exc;
        }
    }

    @Override
    public Artikel loadDataArtikel(String artNummer) throws SQLException {
        Artikel artikel = null;
        String artString = "select * from artikel left join warengruppe on artikel.warengruppe = warengruppe.wgid where artikelnummer = ?;";
        ResultSet rsArtikel = null;
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtArtikel = myConn.prepareStatement(artString)) {

            stmtArtikel.setString(1, artNummer);
            rsArtikel = stmtArtikel.executeQuery();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate ld = null;
            while (rsArtikel.next()) {
                ld = LocalDate.parse(rsArtikel.getString("Datum"));
                artikel = new Artikel(
                        rsArtikel.getString("Artikelnummer"),
                        rsArtikel.getString("Artikel.Bezeichnung"),
                        rsArtikel.getString("Zusatztext"),
                        rsArtikel.getString("Einkaufspreis"),
                        rsArtikel.getString("Verkaufspreis"),
                        rsArtikel.getString("Mwst"),
                        rsArtikel.getString("Bestand"),
                        ld.format(dtf),
                        "0",
                        null,
                        rsArtikel.getString("warengruppe.bezeichnung"));
            }
        }
        return artikel;
    }

    @Override
    public void loadDataKunde() throws SQLException {

        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                Statement stmtKunde = myConn.createStatement();
                ResultSet rsKunde = stmtKunde.executeQuery("select * from kunde join postleitzahl on kunde.postleitzahl = postleitzahl.plz order by kundennummer asc")) {

            dataKunde.clear();

            while (rsKunde.next()) {
                dataKunde.add(new Kunde(
                        rsKunde.getString("kundennummer"),
                        rsKunde.getString("unternehmensname"),
                        rsKunde.getString("anrede"),
                        rsKunde.getString("vorname"),
                        rsKunde.getString("name"),
                        rsKunde.getString("straße"),
                        rsKunde.getString("hausnummer"),
                        rsKunde.getString("zusatz"),
                        rsKunde.getString("plz"),
                        rsKunde.getString("ort"),
                        rsKunde.getString("land")));
            }
        } catch (SQLException exc) {
            throw exc;
        }
    }

    @Override
    public Kunde loadDataKunde(String kNummer) throws SQLException {
        Kunde kunde = null;
        ResultSet rsKunde = null;
        String searchString = "select * from kunde join postleitzahl on kunde.postleitzahl = postleitzahl.plz where kundennummer = ?;";
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtKunde = myConn.prepareStatement(searchString)) {

            stmtKunde.setString(1, kNummer);
            rsKunde = stmtKunde.executeQuery();

            while (rsKunde.next()) {
                kunde = new Kunde(kNummer, rsKunde.getString("unternehmensname"), rsKunde.getString("anrede"), rsKunde.getString("vorname"), rsKunde.getString("name"),
                        rsKunde.getString("straße"), rsKunde.getString("hausnummer"), rsKunde.getString("zusatz"),
                        rsKunde.getString("plz"), rsKunde.getString("ort"), rsKunde.getString("land"));
            }
        } finally {
            if (rsKunde != null) {
                rsKunde.close();
            }
        }
        return kunde;
    }

    @Override
    public void loadDataAngebot(boolean all) throws SQLException {

        String stringAngebot = null;
        if (all) {
            stringAngebot = "select * from angebot order by angebotsnummer asc;";
        } else {
            stringAngebot = "select * from angebot where angebotsnummer like '%A%' order by angebotsnummer asc;";
        }
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                Statement stmtAngebot = myConn.createStatement();
                ResultSet rsAngebot = stmtAngebot.executeQuery(stringAngebot)) {

            dataAngebot.clear();
            while (rsAngebot.next()) {
                if (rsAngebot.getString("akzeptiert").equals("0")) {
                    dataAngebot.add(new Angebot(
                            rsAngebot.getString("angebotsnummer"),
                            rsAngebot.getString("kunde"),
                            rsAngebot.getString("datum"),
                            "noch ausstehend/nein",
                            rsAngebot.getDouble("Nettobetrag"),
                            rsAngebot.getDouble("Bruttobetrag"),
                            rsAngebot.getDouble("Mehrwertsteuer"),
                            rsAngebot.getDouble("Skontobetrag"),
                            rsAngebot.getDouble("SkontoProzent"),
                            rsAngebot.getInt("Zahlungsziel"),
                            rsAngebot.getInt("Skontotage"),
                            rsAngebot.getString("Fakturatext"),
                            rsAngebot.getString("Hinweis")));
                } else {
                    dataAngebot.add(new Angebot(
                            rsAngebot.getString("angebotsnummer"),
                            rsAngebot.getString("kunde"),
                            rsAngebot.getString("datum"),
                            "Ja/Rechnung erstellt",
                            rsAngebot.getDouble("Nettobetrag"),
                            rsAngebot.getDouble("Bruttobetrag"),
                            rsAngebot.getDouble("Mehrwertsteuer"),
                            rsAngebot.getDouble("Skontobetrag"),
                            rsAngebot.getDouble("SkontoProzent"),
                            rsAngebot.getInt("Zahlungsziel"),
                            rsAngebot.getInt("Skontotage"),
                            rsAngebot.getString("Fakturatext"),
                            rsAngebot.getString("Hinweis")));
                }
            }
        }
    }

    @Override
    public Angebot loadDataAngebot(String aNummer) throws SQLException {
        Angebot angebot = null;
        String stringAngebot = "select * from angebot where angebotsnummer = ?;";
        ResultSet rsAngebot = null;
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtAngebot = myConn.prepareStatement(stringAngebot)) {

            stmtAngebot.setString(1, aNummer);
            rsAngebot = stmtAngebot.executeQuery();
            while (rsAngebot.next()) {
                if (rsAngebot.getString("akzeptiert").equals("0")) {
                    angebot = new Angebot(
                            rsAngebot.getString("angebotsnummer"),
                            rsAngebot.getString("kunde"),
                            rsAngebot.getString("datum"),
                            "noch ausstehend/nein",
                            rsAngebot.getDouble("Nettobetrag"),
                            rsAngebot.getDouble("Bruttobetrag"),
                            rsAngebot.getDouble("Mehrwertsteuer"),
                            rsAngebot.getDouble("Skontobetrag"),
                            rsAngebot.getDouble("SkontoProzent"),
                            rsAngebot.getInt("Zahlungsziel"),
                            rsAngebot.getInt("Skontotage"),
                            rsAngebot.getString("Fakturatext"),
                            rsAngebot.getString("Hinweis"));
                } else {
                    angebot = new Angebot(
                            rsAngebot.getString("angebotsnummer"),
                            rsAngebot.getString("kunde"),
                            rsAngebot.getString("datum"),
                            "Ja/Rechnung erstellt",
                            rsAngebot.getDouble("Nettobetrag"),
                            rsAngebot.getDouble("Bruttobetrag"),
                            rsAngebot.getDouble("Mehrwertsteuer"),
                            rsAngebot.getDouble("Skontobetrag"),
                            rsAngebot.getDouble("SkontoProzent"),
                            rsAngebot.getInt("Zahlungsziel"),
                            rsAngebot.getInt("Skontotage"),
                            rsAngebot.getString("Fakturatext"),
                            rsAngebot.getString("Hinweis"));
                }
            }
        } finally {
            if (rsAngebot != null) {
                rsAngebot.close();
            }
        }
        return angebot;
    }

    @Override
    public void loadDataRechnung(boolean all) throws SQLException {
        String stringRechnung = null;
        if (all) {
            stringRechnung = "select * from angebot order by angebotsnummer asc;";
        } else {
            stringRechnung = "select * from angebot where angebotsnummer like '%R%' order by angebotsnummer asc;";
        }
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                Statement stmtRechnung = myConn.createStatement();
                ResultSet rsRechnung = stmtRechnung.executeQuery(stringRechnung)) {

            dataRechnung.clear();
            while (rsRechnung.next()) {
                dataRechnung.add(new Angebot(
                        rsRechnung.getString("angebotsnummer"),
                        rsRechnung.getString("kunde"),
                        rsRechnung.getString("datum"),
                        null,
                        rsRechnung.getDouble("Nettobetrag"),
                        rsRechnung.getDouble("Bruttobetrag"),
                        rsRechnung.getDouble("Mehrwertsteuer"),
                        rsRechnung.getDouble("Skontobetrag"),
                        rsRechnung.getDouble("SkontoProzent"),
                        rsRechnung.getInt("Zahlungsziel"),
                        rsRechnung.getInt("Skontotage"),
                        rsRechnung.getString("Fakturatext"),
                        rsRechnung.getString("Hinweis")));
            }
        } catch (SQLException exc) {
            throw exc;
        }
        /*try {

            loadDataAngebot();
            dataRechnung.clear();
            for (Angebot a : dataAngebot) {
                if (a.getAkzeptiert().equals("ja/Rechnung erstellt")) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    LocalDate aDatum = LocalDate.parse(a.getDatum(), dtf);
                    dataRechnung.add(new Angebot(
                            a.getAngebotsnummer(),
                            a.getKunde(),
                            aDatum.toString(),
                            "ja/Rechnung erstellt"));
                }
            }

        } catch (SQLException exc) {
            throw exc;
        }*/
    }

    @Override
    public void loadArtikelFromAngebot(String nummer) throws SQLException {
        String searchArtikelString = "select * from artikel left  join warengruppe on artikel.warengruppe = warengruppe.wgid join artikelinangebot as aia on artikel.artikelnummer = aia.artikel where aia.angebot = '" + nummer + "' ;";

        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                Statement stmtSearchArtikel = myConn.createStatement();
                ResultSet rsSearchArtikel = stmtSearchArtikel.executeQuery(searchArtikelString);) {

            dataArtikelInAngebot.clear();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate ld = null;
            while (rsSearchArtikel.next()) {
                ld = LocalDate.parse(rsSearchArtikel.getString("Datum"));
                dataArtikelInAngebot.add(new Artikel(
                        rsSearchArtikel.getString("Artikelnummer"),
                        rsSearchArtikel.getString("Artikel.Bezeichnung"),
                        rsSearchArtikel.getString("Zusatztext"),
                        rsSearchArtikel.getString("Einkaufspreis"),
                        rsSearchArtikel.getString("Verkaufspreis"),
                        rsSearchArtikel.getString("Mwst"),
                        rsSearchArtikel.getString("Menge"),
                        ld.format(dtf),
                        rsSearchArtikel.getString("Alternativ"),
                        rsSearchArtikel.getString("aia.rabatt"),
                        rsSearchArtikel.getString("warengruppe.bezeichnung")));
            }

        } catch (SQLException exc) {
            throw exc;
        }
    }

    @Override
    public void loadFilteredKunden(String filter) throws SQLException {
        String searchKundeString = "select * from kunde join postleitzahl on kunde.postleitzahl = postleitzahl.plz where vorname like ? or name like ? or kundennummer like ? or "
                + "straße like ? or kunde.postleitzahl like ? or ort like ? or land like ?;";
        ResultSet rsSearchKunde = null;

        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtSearchKunde = myConn.prepareStatement(searchKundeString)) {

            dataFilteredKunde.clear();

            stmtSearchKunde.setString(1, "%" + filter + "%");
            stmtSearchKunde.setString(2, "%" + filter + "%");
            stmtSearchKunde.setString(3, "%" + filter + "%");
            stmtSearchKunde.setString(4, "%" + filter + "%");
            stmtSearchKunde.setString(5, "%" + filter + "%");
            stmtSearchKunde.setString(6, "%" + filter + "%");
            stmtSearchKunde.setString(7, "%" + filter + "%");
            rsSearchKunde = stmtSearchKunde.executeQuery();

            String temp = null;
            while (rsSearchKunde.next()) {
                if (rsSearchKunde.getString("unternehmensname") != null) {
                    temp = rsSearchKunde.getString("unternehmensname");
                } else {
                    temp = "privat";
                }
                dataFilteredKunde.add(new Kunde(
                        rsSearchKunde.getString("Kundennummer"),
                        temp,
                        rsSearchKunde.getString("Anrede"),
                        rsSearchKunde.getString("Vorname"),
                        rsSearchKunde.getString("Name"),
                        rsSearchKunde.getString("Straße"),
                        rsSearchKunde.getString("Hausnummer"),
                        rsSearchKunde.getString("Zusatz"),
                        rsSearchKunde.getString("postleitzahl"),
                        rsSearchKunde.getString("ort"),
                        rsSearchKunde.getString("land")));
            }

        } catch (SQLException exc) {
            throw exc;
        } finally {
            if (rsSearchKunde != null) {
                rsSearchKunde.close();
            }
        }
    }

    @Override
    public void loadFilteredAngebote(String filter, boolean all) throws SQLException {
        String searchAngebotString = null;
        if (all) {
            searchAngebotString = "select * from angebot where angebotsnummer like ? or kunde like ? or datum like ? or akzeptiert like ?;";
        } else {
            searchAngebotString = "select * from angebot where (angebotsnummer like ? or kunde like ? or datum like ? or akzeptiert like ?) and angebotsnummer like '%A%';";
        }
        ResultSet rsSearchAngebot = null;

        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtSearchAngebot = myConn.prepareStatement(searchAngebotString)) {

            dataFilteredAngebot.clear();

            stmtSearchAngebot.setString(1, "%" + filter + "%");
            stmtSearchAngebot.setString(2, "%" + filter + "%");
            stmtSearchAngebot.setString(3, "%" + filter + "%");
            stmtSearchAngebot.setString(4, "%" + filter + "%");
            rsSearchAngebot = stmtSearchAngebot.executeQuery();

            while (rsSearchAngebot.next()) {
                if (rsSearchAngebot.getString("akzeptiert").equals("0")) {
                    dataFilteredAngebot.add(new Angebot(
                            rsSearchAngebot.getString("angebotsnummer"),
                            rsSearchAngebot.getString("kunde"),
                            rsSearchAngebot.getString("datum"),
                            "noch ausstehend/nein",
                            rsSearchAngebot.getDouble("Nettobetrag"),
                            rsSearchAngebot.getDouble("Bruttobetrag"),
                            rsSearchAngebot.getDouble("Mehrwertsteuer"),
                            rsSearchAngebot.getDouble("Skontobetrag"),
                            rsSearchAngebot.getDouble("SkontoProzent"),
                            rsSearchAngebot.getInt("Zahlungsziel"),
                            rsSearchAngebot.getInt("Skontotage"),
                            rsSearchAngebot.getString("Fakturatext"),
                            rsSearchAngebot.getString("Hinweis")));
                } else {
                    dataFilteredAngebot.add(new Angebot(
                            rsSearchAngebot.getString("angebotsnummer"),
                            rsSearchAngebot.getString("kunde"),
                            rsSearchAngebot.getString("datum"),
                            "ja/Rechnung erstellt",
                            rsSearchAngebot.getDouble("Nettobetrag"),
                            rsSearchAngebot.getDouble("Bruttobetrag"),
                            rsSearchAngebot.getDouble("Mehrwertsteuer"),
                            rsSearchAngebot.getDouble("Skontobetrag"),
                            rsSearchAngebot.getDouble("SkontoProzent"),
                            rsSearchAngebot.getInt("Zahlungsziel"),
                            rsSearchAngebot.getInt("Skontotage"),
                            rsSearchAngebot.getString("Fakturatext"),
                            rsSearchAngebot.getString("Hinweis")));
                }
            }

        } catch (SQLException exc) {
            throw exc;
        } finally {
            if (rsSearchAngebot != null) {
                rsSearchAngebot.close();
            }
        }
    }

    @Override
    public void loadFilteredRechnung(String filter, boolean all) throws SQLException {
        String searchRechnungString = null;
        if (all) {
            searchRechnungString = "select * from angebot where angebotsnummer like ? or kunde like ? or datum like ?;";
        } else {
            searchRechnungString = "select * from angebot where (angebotsnummer like ? or kunde like ? or datum like ?) and angebotsnummer like '%R%';";
        }
        ResultSet rsSearchRechnung = null;

        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtSearchRechnung = myConn.prepareStatement(searchRechnungString)) {

            dataFilteredRechnung.clear();

            stmtSearchRechnung.setString(1, "%" + filter + "%");
            stmtSearchRechnung.setString(2, "%" + filter + "%");
            stmtSearchRechnung.setString(3, "%" + filter + "%");
            rsSearchRechnung = stmtSearchRechnung.executeQuery();

            while (rsSearchRechnung.next()) {
                dataFilteredRechnung.add(new Angebot(
                        rsSearchRechnung.getString("angebotsnummer"),
                        rsSearchRechnung.getString("kunde"),
                        rsSearchRechnung.getString("datum"),
                        null,
                        rsSearchRechnung.getDouble("Nettobetrag"),
                        rsSearchRechnung.getDouble("Bruttobetrag"),
                        rsSearchRechnung.getDouble("Mehrwertsteuer"),
                        rsSearchRechnung.getDouble("Skontobetrag"),
                        rsSearchRechnung.getDouble("SkontoProzent"),
                        rsSearchRechnung.getInt("Zahlungsziel"),
                        rsSearchRechnung.getInt("Skontotage"),
                        rsSearchRechnung.getString("Fakturatext"),
                        rsSearchRechnung.getString("Hinweis")));
            }

        } catch (SQLException exc) {
            throw exc;
        } finally {
            if (rsSearchRechnung != null) {
                rsSearchRechnung.close();
            }
        }
    }

    @Override
    public void loadFilteredArtikel(String filter) throws SQLException {
        String searchArtikelString = "select * from artikel left join warengruppe on artikel.warengruppe = warengruppe.wgid where artikelnummer like ? or artikel.bezeichnung like ? or warengruppe.bezeichnung like ?;";
        ResultSet rsSearchArtikel = null;

        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtSearchArtikel = myConn.prepareStatement(searchArtikelString)) {

            dataFilteredArtikel.clear();

            stmtSearchArtikel.setString(1, "%" + filter + "%");
            stmtSearchArtikel.setString(2, "%" + filter + "%");
            stmtSearchArtikel.setString(3, "%" + filter + "%");
            rsSearchArtikel = stmtSearchArtikel.executeQuery();

            while (rsSearchArtikel.next()) {
                if (rsSearchArtikel.getString("warengruppe.bezeichnung") != null) {
                    dataFilteredArtikel.add(new Artikel(
                            rsSearchArtikel.getString("Artikelnummer"),
                            rsSearchArtikel.getString("Artikel.Bezeichnung"),
                            rsSearchArtikel.getString("Zusatztext"),
                            rsSearchArtikel.getString("Einkaufspreis"),
                            rsSearchArtikel.getString("Verkaufspreis"),
                            rsSearchArtikel.getString("Mwst"),
                            rsSearchArtikel.getString("Bestand"),
                            rsSearchArtikel.getString("Datum"),
                            null,
                            null,
                            rsSearchArtikel.getString("Warengruppe.bezeichnung")));
                } else {
                    dataFilteredArtikel.add(new Artikel(
                            rsSearchArtikel.getString("Artikelnummer"),
                            rsSearchArtikel.getString("Artikel.Bezeichnung"),
                            rsSearchArtikel.getString("Zusatztext"),
                            rsSearchArtikel.getString("Einkaufspreis"),
                            rsSearchArtikel.getString("Verkaufspreis"),
                            rsSearchArtikel.getString("Mwst"),
                            rsSearchArtikel.getString("Bestand"),
                            rsSearchArtikel.getString("Datum"),
                            null,
                            null,
                            null));
                }
            }

        } catch (SQLException exc) {
            throw exc;
        } finally {
            if (rsSearchArtikel != null) {
                rsSearchArtikel.close();
            }
        }
    }

    @Override
    public void deleteKunde(String kNummer) throws SQLException {
        String deleteString = "delete from kunde where kundennummer = ?;";
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement deleteKunde = myConn.prepareStatement(deleteString)) {

            deleteKunde.setString(1, kNummer);
            deleteKunde.executeUpdate();
        }
    }

    @Override
    public void deleteAngebot(String aNummer) throws SQLException {
        String deleteString = "delete from angebot where angebotsnummer = ?;";
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement deleteKunde = myConn.prepareStatement(deleteString)) {

            deleteKunde.setString(1, aNummer);
            deleteKunde.executeUpdate();
        }
    }

    @Override
    public void safeNewWarengruppe(String w) throws SQLException {
        String createString = "insert into warengruppe(bezeichnung) values(?);";
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtW = myConn.prepareStatement(createString)) {

            stmtW.setString(1, w);
            stmtW.executeUpdate();
        }
    }

    @Override
    public void deleteWarengruppe(String w) throws SQLException {
        String deleteString = "delete from warengruppe where bezeichnung = ?;";
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtW = myConn.prepareStatement(deleteString)) {

            stmtW.setString(1, w);
            stmtW.executeUpdate();
        }
    }

    @Override
    public void safeNewKunde(String uName, String a, String vn,
            String n, String s,
            String h, String z,
            String p) throws SQLException {
        String addStringKunde = "insert into kunde(kundennummer, unternehmensname, anrede, vorname, name, straße, hausnummer, zusatz, postleitzahl) values(?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement addKunde = myConn.prepareStatement(addStringKunde)) {

            String kNumber = null;
            loadDataKunde();
            for (Kunde k : dataKunde) {
                kNumber = k.getKundennummer();
            }
            kNumber = generateRandomClientNumber(kNumber);
            String[] parameter = {kNumber, uName, a, vn, n, s, h, z, p};
            for (int i = 0; i < 9; i++) {
                addKunde.setString(i + 1, parameter[i]);
            }
            addKunde.executeUpdate();
        }
    }

    @Override
    public void safeNewPlz(String p, String o, String l) throws SQLException {

        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                Statement stmtExistPlz = myConn.createStatement();
                ResultSet rsExistPlz = stmtExistPlz.executeQuery("select plz from postleitzahl;")) {

            //Überprüfung ob die Postleitzahl bereits existiert
            boolean plzExists = false;
            while (rsExistPlz.next()) {
                String test = rsExistPlz.getString("plz");
                plzExists = test.equals(p);
            }
            if (plzExists == false) {
                String addStringPlz = "insert into postleitzahl(plz, ort, land) values(?, ?, ?);";
                PreparedStatement addPlz = myConn.prepareStatement(addStringPlz);
                addPlz.setString(1, p);
                addPlz.setString(2, o);
                addPlz.setString(3, l);
                addPlz.executeUpdate();
            }
        } catch (SQLException exc) {
            throw exc;
        }
    }

    @Override
    public void updatePlz(String plzAlt, String plzNeu, String ort, String land) throws SQLException {
        String updateString = "update postleitzahl set plz = ?, ort = ?, land = ? where plz = ?;";
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtUpdatePlz = myConn.prepareStatement(updateString)) {

            stmtUpdatePlz.setString(1, plzNeu);
            stmtUpdatePlz.setString(2, ort);
            stmtUpdatePlz.setString(3, land);
            stmtUpdatePlz.setString(4, plzAlt);

            stmtUpdatePlz.executeUpdate();

        }
    }

    @Override
    public void safeNewArtikel(String aN, String bez, String z, String ePreis, String vPreis, String mwst, String m, String d, String w) throws SQLException {
        String[] parameter = {aN, bez, z, ePreis, vPreis, mwst, m, d, w};
        String addStringArtikel = "insert into artikel(artikelnummer, bezeichnung, zusatztext, einkaufspreis, verkaufspreis, mwst, bestand, datum, warengruppe) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?, (select warengruppe.wgid from warengruppe where warengruppe.bezeichnung = ?));";

        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement addArtikel = myConn.prepareStatement(addStringArtikel)) {

            for (int i = 0; i < 9; i++) {
                addArtikel.setString(i + 1, parameter[i]);
            }
            addArtikel.executeUpdate();
        } catch (SQLException exc) {
            throw exc;
        }
    }

    @Override
    public void safeNewAngebot(String aNummer, String kNummer, ObservableList<Artikel> artInAng, double nettoBetrag, double bruttoBetrag, double mwst, double skontoPr, double skontoBetrag, String faktura, String hinweis,
            int zZ, int skontoT) throws SQLException {

        String angebotString = "insert into angebot(angebotsnummer, kunde, datum, akzeptiert, nettobetrag, bruttobetrag, mehrwertsteuer, zahlungsziel, skontotage, skontoprozent, skontobetrag, fakturatext, hinweis) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtNewAngebot = myConn.prepareStatement(angebotString)) {

            LocalDate ld = LocalDate.now();
            stmtNewAngebot.setString(1, aNummer);
            stmtNewAngebot.setString(2, kNummer);
            stmtNewAngebot.setString(3, ld.toString());
            stmtNewAngebot.setString(4, "0");
            stmtNewAngebot.setDouble(5, nettoBetrag);
            stmtNewAngebot.setDouble(6, bruttoBetrag);
            stmtNewAngebot.setDouble(7, mwst);
            stmtNewAngebot.setInt(8, zZ);
            stmtNewAngebot.setInt(9, skontoT);
            stmtNewAngebot.setDouble(10, skontoPr);
            stmtNewAngebot.setDouble(11, skontoBetrag);
            stmtNewAngebot.setString(12, faktura);
            stmtNewAngebot.setString(13, hinweis);

            stmtNewAngebot.executeUpdate();

            for (Artikel art : artInAng) {
                if (art.getRabattTemp() != null) {
                    safeArtikelInAngebot(aNummer, art.getArtikelnummer(),
                            Integer.valueOf(art.getMengeTemp()),
                            Boolean.valueOf(art.getAlternative()),
                            Double.valueOf(art.getRabattTemp()));
                    System.out.println(Boolean.valueOf(art.getAlternative()));
                } else {
                    safeArtikelInAngebot(aNummer, art.getArtikelnummer(),
                            Integer.valueOf(art.getMengeTemp()),
                            Boolean.valueOf(art.getAlternative()));
                    System.out.println(Boolean.valueOf(art.getAlternative()));
                }
            }
        }
    }

    @Override
    public void safeArtikelInAngebot(String angebot, String artikel,
            int menge, boolean alt, double r) throws SQLException {
        String addStringArtikelInAngebot = "insert into artikelinangebot(angebot, artikel, menge, alternativ, rabatt) values(?, ?, ?, ?, ?);";
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtAddArtikelInAngebot = myConn.prepareStatement(addStringArtikelInAngebot)) {

            stmtAddArtikelInAngebot.setString(1, angebot);
            stmtAddArtikelInAngebot.setString(2, artikel);
            stmtAddArtikelInAngebot.setInt(3, menge);
            stmtAddArtikelInAngebot.setBoolean(4, alt);
            stmtAddArtikelInAngebot.setDouble(5, r);

            stmtAddArtikelInAngebot.executeUpdate();

        } catch (SQLException exc) {
            throw exc;
        }
    }

    @Override
    public void safeArtikelInAngebot(String angebot, String artikel,
            int menge, boolean alt) throws SQLException {
        String addStringArtikelInAngebot = "insert into artikelinangebot(angebot, artikel, menge, alternativ) values(?, ?, ?, ?);";
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtAddArtikelInAngebot = myConn.prepareStatement(addStringArtikelInAngebot)) {

            stmtAddArtikelInAngebot.setString(1, angebot);
            stmtAddArtikelInAngebot.setString(2, artikel);
            stmtAddArtikelInAngebot.setInt(3, menge);
            stmtAddArtikelInAngebot.setBoolean(4, alt);

            stmtAddArtikelInAngebot.executeUpdate();

        } catch (SQLException exc) {
            throw exc;
        }
    }

    @Override
    public void safeNewRechnung(String rNummer, String kNummer, ObservableList<Artikel> artInAng, double nettoBetrag, double bruttoBetrag, double mwst, double skontoPr, double skontoBetrag, String faktura, String hinweis,
            int zZ, int skontoT) throws SQLException {

        String rechnungString = "insert into angebot(angebotsnummer, kunde, datum, akzeptiert, nettobetrag, bruttobetrag, mehrwertsteuer, zahlungsziel, skontotage, skontoprozent, skontobetrag, fakturatext, hinweis) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtNewRechnung = myConn.prepareStatement(rechnungString)) {

            LocalDate ld = LocalDate.now();
            stmtNewRechnung.setString(1, rNummer);
            stmtNewRechnung.setString(2, kNummer);
            stmtNewRechnung.setString(3, ld.toString());
            stmtNewRechnung.setString(4, "0");
            stmtNewRechnung.setDouble(5, nettoBetrag);
            stmtNewRechnung.setDouble(6, bruttoBetrag);
            stmtNewRechnung.setDouble(7, mwst);
            stmtNewRechnung.setInt(8, zZ);
            stmtNewRechnung.setInt(9, skontoT);
            stmtNewRechnung.setDouble(10, skontoPr);
            stmtNewRechnung.setDouble(11, skontoBetrag);
            stmtNewRechnung.setString(12, faktura);
            stmtNewRechnung.setString(13, hinweis);

            stmtNewRechnung.executeUpdate();

            for (Artikel art : artInAng) {
                if (art.getRabattTemp() != null) {
                    safeArtikelInRechnung(rNummer, art.getArtikelnummer(),
                            Integer.valueOf(art.getMengeTemp()),
                            Boolean.valueOf(art.getAlternative()),
                            Double.valueOf(art.getRabattTemp()));
                    System.out.println(Boolean.valueOf(art.getAlternative()));
                } else {
                    safeArtikelInRechnung(rNummer, art.getArtikelnummer(),
                            Integer.valueOf(art.getMengeTemp()),
                            Boolean.valueOf(art.getAlternative()));
                    System.out.println(Boolean.valueOf(art.getAlternative()));
                }
            }
        }
    }

    @Override
    public void safeArtikelInRechnung(String rechnung, String artikel,
            int menge, boolean alt, double r) throws SQLException {
        String addStringArtikelInRechnung = "insert into artikelinangebot(angebot, artikel, menge, alternativ, rabatt) values(?, ?, ?, ?, ?);";
        String updateString = "update artikel set bestand = ? where artikelnummer = ?;";
        ResultSet rsCheck = null;
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtCheck = myConn.prepareStatement("select bestand from artikel where artikelnummer = ?;");
                PreparedStatement stmtAddArtikelInRechnung = myConn.prepareStatement(addStringArtikelInRechnung);
                PreparedStatement stmtUpdateBestand = myConn.prepareStatement(updateString)) {

            stmtAddArtikelInRechnung.setString(1, rechnung);
            stmtAddArtikelInRechnung.setString(2, artikel);
            stmtAddArtikelInRechnung.setInt(3, menge);
            stmtAddArtikelInRechnung.setBoolean(4, alt);
            stmtAddArtikelInRechnung.setDouble(5, r);

            stmtAddArtikelInRechnung.executeUpdate();

            int neuerBestand = 0;

            stmtCheck.setString(1, artikel);
            rsCheck = stmtCheck.executeQuery();
            while (rsCheck.next()) {
                neuerBestand = Integer.parseInt(rsCheck.getString("bestand"));
            }
            neuerBestand = neuerBestand - menge;

            stmtUpdateBestand.setInt(1, neuerBestand);
            stmtUpdateBestand.setString(2, artikel);

            stmtUpdateBestand.executeUpdate();

        } finally {
            if (rsCheck != null) {
                rsCheck.close();
            }
        }
    }

    @Override
    public void safeArtikelInRechnung(String rechnung, String artikel,
            int menge, boolean alt) throws SQLException {
        String addStringArtikelInRechnung = "insert into artikelinangebot(angebot, artikel, menge, alternativ) values(?, ?, ?, ?);";
        String updateString = "update artikel set bestand = ? where artikelnummer = ?;";
        ResultSet rsCheck = null;
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtCheck = myConn.prepareStatement("select bestand from artikel where artikelnummer = ?;");
                PreparedStatement stmtAddArtikelInRechnung = myConn.prepareStatement(addStringArtikelInRechnung);
                PreparedStatement stmtUpdateBestand = myConn.prepareStatement(updateString)) {

            stmtAddArtikelInRechnung.setString(1, rechnung);
            stmtAddArtikelInRechnung.setString(2, artikel);
            stmtAddArtikelInRechnung.setInt(3, menge);
            stmtAddArtikelInRechnung.setBoolean(4, alt);

            stmtAddArtikelInRechnung.executeUpdate();

            int neuerBestand = 0;
            stmtCheck.setString(1, artikel);
            rsCheck = stmtCheck.executeQuery();
            while (rsCheck.next()) {
                neuerBestand = Integer.parseInt(rsCheck.getString("bestand"));
            }
            neuerBestand = neuerBestand - menge;

            stmtUpdateBestand.setInt(1, neuerBestand);
            stmtUpdateBestand.setString(2, artikel);

            stmtUpdateBestand.executeUpdate();

        } finally {
            if (rsCheck != null) {
                rsCheck.close();
            }
        }
    }

    @Override
    public void updateKunde(String kNummer, String uName, String a, String vn, String n, String s, String h, String z, String p) throws SQLException {
        String updateString = "update kunde set unternehmensname = ?, anrede = ?, vorname = ?, name = ?, straße = ?, hausnummer = ?, zusatz = ?, postleitzahl = ? where kundennummer = ?";
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement updateKunde = myConn.prepareStatement(updateString)) {

            updateKunde.setString(1, uName);
            updateKunde.setString(2, a);
            updateKunde.setString(3, vn);
            updateKunde.setString(4, n);
            updateKunde.setString(5, s);
            updateKunde.setString(6, h);
            updateKunde.setString(7, z);
            updateKunde.setString(8, p);
            updateKunde.setString(9, kNummer);

            updateKunde.executeUpdate();
        }
    }

    /*
    @Override
    public void updateKunde(String attr, String id,
            String eingabe) throws SQLException {
        String updateKundeString = "update kunde set ? = ? where kundennummer = ?";

        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtUpdateKunde = myConn.prepareStatement(updateKundeString)) {

            stmtUpdateKunde.setString(1, attr);
            stmtUpdateKunde.setString(2, eingabe);
            stmtUpdateKunde.setString(3, id);
            stmtUpdateKunde.executeUpdate();
        } catch (SQLException exc) {
            throw exc;
        }
    }
     */
    @Override
    public void updateArtikelVerkaufsPreis(String id, String eingabe) throws SQLException {
        String updateArtikelString = "update artikel set verkaufspreis = ? where artikelnummer = ?;";

        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtUpdateArtikel = myConn.prepareStatement(updateArtikelString)) {

            stmtUpdateArtikel.setString(1, eingabe);
            stmtUpdateArtikel.setString(2, id);
            stmtUpdateArtikel.executeUpdate();
        } catch (SQLException exc) {
            throw exc;
        }
    }

    @Override
    public void updateArtikelNummer(String oldId, String newId) throws SQLException {
        String updateArtikelString = "update artikel set artikelnummer = ? where artikelnummer = ?;";

        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtUpdateArtikel = myConn.prepareStatement(updateArtikelString)) {

            stmtUpdateArtikel.setString(1, newId);
            stmtUpdateArtikel.setString(2, oldId);
            stmtUpdateArtikel.executeUpdate();
        } catch (SQLException exc) {
            throw exc;
        }
    }

    @Override
    public void updateArtikel(String oldId, String newId, String bez, String z, String ePreis, String vPreis, String mwst, String m, String w) throws SQLException {
        String updateArtikelString = "update artikel set artikelnummer = ?, bezeichnung = ?, zusatztext = ?, einkaufspreis = ?, verkaufspreis = ?,"
                + "mwst = ?, bestand = ?, datum = ?, warengruppe = (select warengruppe.wgid from warengruppe where warengruppe.bezeichnung = ?) where artikelnummer = ?;";

        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                PreparedStatement stmtUpdateArtikel = myConn.prepareStatement(updateArtikelString)) {

            LocalDate ld = LocalDate.now();
            stmtUpdateArtikel.setString(1, newId);
            stmtUpdateArtikel.setString(2, bez);
            stmtUpdateArtikel.setString(3, z);
            stmtUpdateArtikel.setString(4, ePreis);
            stmtUpdateArtikel.setString(5, vPreis);
            stmtUpdateArtikel.setString(6, mwst);
            stmtUpdateArtikel.setString(7, m);
            stmtUpdateArtikel.setString(8, ld.toString());
            stmtUpdateArtikel.setString(9, w);
            stmtUpdateArtikel.setString(10, oldId);
            stmtUpdateArtikel.executeUpdate();
        } catch (SQLException exc) {
            throw exc;
        }
    }

    /**
     * Generiert die chronologisch nächste Rechnungsnummer für einen bestimmten
     * Kunden
     *
     * @param datum - aktuelles Datum
     * @return neue Rechnungsnummer
     * @throws SQLException
     */
    public String generateRandomBillNumber(String datum) throws SQLException {
        String stringLetzteRechnungen = "select * from angebot where angebotsnummer like '%R%' order by angebotsnummer asc;";
        ResultSet rsLetzteRechnungen = null;
        String letzteRechnung = null;
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                Statement stmtLetzteRechnungen = myConn.createStatement()) {

            rsLetzteRechnungen = stmtLetzteRechnungen.executeQuery(stringLetzteRechnungen);

            dataRechnung.clear();
            while (rsLetzteRechnungen.next()) {
                dataRechnung.add(new Angebot(
                        rsLetzteRechnungen.getString("angebotsnummer"),
                        rsLetzteRechnungen.getString("kunde"),
                        rsLetzteRechnungen.getString("datum"),
                        rsLetzteRechnungen.getString("akzeptiert"),
                        rsLetzteRechnungen.getDouble("Nettobetrag"),
                        rsLetzteRechnungen.getDouble("Bruttobetrag"),
                        rsLetzteRechnungen.getDouble("Mehrwertsteuer"),
                        rsLetzteRechnungen.getDouble("Skontobetrag"),
                        rsLetzteRechnungen.getDouble("SkontoProzent"),
                        rsLetzteRechnungen.getInt("Zahlungsziel"),
                        rsLetzteRechnungen.getInt("Skontotage"),
                        rsLetzteRechnungen.getString("Fakturatext"),
                        rsLetzteRechnungen.getString("Hinweis")));
            }
            for (Angebot aIt : dataRechnung) {
                letzteRechnung = aIt.getAngebotsnummer();
            }
        } finally {
            if (rsLetzteRechnungen != null) {
                rsLetzteRechnungen.close();
            }
        }

        String subYear = null;
        String subNumber = null;
        String newNumber = null;
        //DateTimeFormatter dateTf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate ld = LocalDate.parse(datum);
        if (letzteRechnung != null) {
            subYear = letzteRechnung.substring(0, 4);
            if (subYear.equals(String.valueOf(ld.getYear()))) {
                subNumber = letzteRechnung.substring(5, 10);
                subNumber = subNumber.replace("o", "");
                String zeroTemp = "";
                int number = Integer.parseInt(subNumber);
                number++; //Nummer um 1 erhöhen
                subNumber = String.valueOf(number);
                if (subNumber.length() == 5) { //wenn Länge bereits erreicht
                    newNumber = subNumber;
                } else {
                    for (int i = 0; i < 5; i++) { //5-stellige Ziffer mit Nullen auffüllen
                        zeroTemp += "o";
                        newNumber = zeroTemp + subNumber;
                        if (newNumber.length() == 5) { //Abbruch, wenn vorzeitig fertig ausgefüllt
                            break;
                        }
                    }
                }
            } else { //wenn die letzte Rechnung aus dem vorherigen Jahr oder früher stammt
                subYear = String.valueOf(ld.getYear());
                newNumber = "oooo1";
            }
        } else {
            subYear = String.valueOf(ld.getYear());
            newNumber = "oooo1";
        }

        String fullNumber = subYear + "-" + newNumber + "-R";
        loadDataRechnung(true);
        return fullNumber;
    }

    @Override
    public String generateRandomOfferNumber(String datum) throws SQLException {
        String stringLetzteAngebote = "select * from angebot where angebotsnummer like '%A%' order by angebotsnummer asc;";
        ResultSet rsLetzteAngebot = null;
        String letztesAngebot = null;
        try (Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Verwaltungssoftware?useSSL=true", userInfo);
                Statement stmtLetzteAngebote = myConn.createStatement()) {

            rsLetzteAngebot = stmtLetzteAngebote.executeQuery(stringLetzteAngebote);

            dataAngebot.clear();
            while (rsLetzteAngebot.next()) {
                dataAngebot.add(new Angebot(
                        rsLetzteAngebot.getString("angebotsnummer"),
                        rsLetzteAngebot.getString("kunde"),
                        rsLetzteAngebot.getString("datum"),
                        rsLetzteAngebot.getString("akzeptiert"),
                        rsLetzteAngebot.getDouble("Nettobetrag"),
                        rsLetzteAngebot.getDouble("Bruttobetrag"),
                        rsLetzteAngebot.getDouble("Mehrwertsteuer"),
                        rsLetzteAngebot.getDouble("Skontobetrag"),
                        rsLetzteAngebot.getDouble("SkontoProzent"),
                        rsLetzteAngebot.getInt("Zahlungsziel"),
                        rsLetzteAngebot.getInt("Skontotage"),
                        rsLetzteAngebot.getString("Fakturatext"),
                        rsLetzteAngebot.getString("Hinweis")));
            }
            for (Angebot aIt : dataAngebot) {
                letztesAngebot = aIt.getAngebotsnummer();
            }
        } finally {
            if (rsLetzteAngebot != null) {
                rsLetzteAngebot.close();
            }
        }

        String subYear = null;
        String subNumber = null;
        String newNumber = null;
        //DateTimeFormatter dateTf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate ld = LocalDate.parse(datum);
        if (letztesAngebot != null) {
            subYear = letztesAngebot.substring(0, 4);
            if (subYear.equals(String.valueOf(ld.getYear()))) {
                subNumber = letztesAngebot.substring(5, 10);
                subNumber = subNumber.replace("o", "");
                String zeroTemp = "";
                int number = Integer.parseInt(subNumber);
                number++;//Nummer um 1 erhöhen 
                subNumber = String.valueOf(number);
                if (subNumber.length() == 5) {
                    newNumber = subNumber;
                } else {
                    for (int i = 0; i < 5; i++) { //5-stellige Ziffer mit Nullen auffüllen
                        zeroTemp += "o";
                        System.out.println(zeroTemp + "zeroTemP");
                        newNumber = zeroTemp + subNumber;
                        if (newNumber.length() == 5) {
                            System.out.println("länge" + newNumber.length());//Abbruch, wenn vorzeitig fertig ausgefüllt
                            break;
                        }
                    }
                }
            } else { //wenn die letzte Rechnung aus dem vorherigen Jahr oder früher stammt
                subYear = String.valueOf(ld.getYear());
                newNumber = "oooo1";
            }
        } else {
            subYear = String.valueOf(ld.getYear());
            newNumber = "oooo1";
        }

        String fullNumber = subYear + "-" + newNumber + "-A";
        loadDataAngebot(true);
        return fullNumber;
    }

    private String generateRandomClientNumber(String kunde) {
        String fullKunde = null;
        if (kunde != null) {
            String newKunde = kunde.substring(2, 7);
            newKunde = newKunde.replace("o", "");
            int plusKunde = Integer.valueOf(newKunde);
            plusKunde++;
            String newKundeTemp = String.valueOf(plusKunde);
            String zeroTemp = "";
            if (newKundeTemp.length() == 5) {
                newKunde = newKundeTemp;
            } else {
                for (int i = 0; i < 5; i++) {
                    zeroTemp += "o";
                    newKunde = zeroTemp + newKundeTemp;
                    if (newKunde.length() == 5) {
                        break;
                    }
                }
            }
            fullKunde = "K-" + newKunde;
        } else {
            fullKunde = "K-oooo1";
        }
        return fullKunde;
    }
}
