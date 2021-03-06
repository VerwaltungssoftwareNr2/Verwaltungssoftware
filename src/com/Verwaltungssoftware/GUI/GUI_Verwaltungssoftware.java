/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verwaltungssoftware.GUI;

import com.verwaltungssoftware.database.ISql;
import com.verwaltungssoftware.database.SqlConnector;
import com.verwaltungssoftware.objects.Angebot;
import com.verwaltungssoftware.objects.Artikel;
import com.verwaltungssoftware.objects.Kunde;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Lucas
 */
public class GUI_Verwaltungssoftware extends Application {

    Scene loginScreen, mainScreen;
    VBox kundenVB;
    TableView<Kunde> kundenT;
    VBox artikelVB;
    TableView<Artikel> artikelT;
    VBox angebotT;
    VBox rechnungT;
    ISql sql;
    String[] tempKunde = new String[7];
    String[] tempArtikel = new String[5];
    String username;
    String password;

    @Override
    public void start(Stage primaryStage) {

        Label benutzer = new Label("Benutzer:");
        Label passwort = new Label("Passwort:");
        //Label welcome = new Label("Willkommen! Bitte loggen sie sich ein.");

        TextField user = new TextField();
        PasswordField pass = new PasswordField();
        Button submit = new Button("Anmelden");
        submit.setDefaultButton(true);
        user.setPromptText("Benutzername");
        pass.setPromptText("Passwort");

        submit.setOnAction((ActionEvent event) -> {
            this.sql = new SqlConnector(user.getText(), pass.getText());
            if (sql.getAuthentication()) {
                this.username = user.getText();
                this.password = pass.getText();
                primaryStage.setScene(mainScreen);
                pass.clear();
                primaryStage.setMaximized(true);
                try {
                    sql.checkUserConfig();
                } catch (SQLException exc) {
                    ConfirmBox.display2("Fehler", "Fehler beim Überprüfen der Unternehmenskonfiguration");
                }
                try {
                    sql.loadDataKunde();
                } catch (SQLException exc) {
                    ConfirmBox.display2("Fehler", "Fehler beim Laden der Kunden");
                }
                try {
                    sql.loadDataArtikel();
                } catch (SQLException exc) {
                    ConfirmBox.display2("Fehler", "Fehler beim Laden der Artikel");
                }
                try {
                    sql.loadDataAngebot(false);
                } catch (SQLException exc) {
                    ConfirmBox.display2("Fehler", "Fehler beim Laden der Angebote");
                }
                try {
                    sql.loadDataRechnung(false);
                } catch (SQLException exc) {
                    ConfirmBox.display2("Fehler", "Fehler beim Laden der Rechnungen");
                }
                this.rechnungT = createTableRechnung();
                this.angebotT = createTableAngebot();
                this.artikelT = new TableView<>();
                this.artikelVB = createTableArtikel();
                this.kundenT = new TableView<>();
                this.kundenVB = createTableKunde();
                if (!sql.getCheckUserConfig()) {
                    InfoBox.display(sql);
                }
            }
        });
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(8);
        grid.setHgap(8);

        grid.add(benutzer, 1, 1);
        grid.add(passwort, 1, 2);
        grid.add(user, 2, 1);
        grid.add(pass, 2, 2);
        grid.add(submit, 2, 3);

        grid.setAlignment(Pos.CENTER);

        loginScreen = new Scene(grid, 400, 400); //Login screen

        MenuBar menu = new MenuBar();
        Menu allgemein = new Menu("Allgemein");
        Menu kunde = new Menu("Kunde");
        Menu artikel = new Menu("Artikel");
        Menu angebot = new Menu("Angebot");
        Menu rechnung = new Menu("Rechnung");

        MenuItem infoChange = new MenuItem("Informationen ändern");
        MenuItem changeUser = new MenuItem("Ausloggen");
        MenuItem close = new MenuItem("Beenden");
        //MenuItem passChange = new MenuItem("Passwort ändern");
        allgemein.getItems().addAll(infoChange, changeUser, close);

        //Lambda mit "mehreren" Ausdrücken
        infoChange.setOnAction(e -> {
            InfoBox.display(sql);
        });
        //passChange.setOnAction(e -> PasswordChange.display());
        //Lambda mit einem Ausdruck
        changeUser.setOnAction(e -> primaryStage.setScene(loginScreen));
        close.setOnAction(e -> {
            boolean test = ConfirmBox.display("Anwendung schließen", "Wollen Sie die Anwendung wirklich verlassen?", 300, 100);
            if (test == true) {
                primaryStage.close();
            } else {
                e.consume();
            }
        });
        primaryStage.setOnCloseRequest(e -> {
            boolean test = ConfirmBox.display("Anwendung schließen", "Wollen Sie die Anwendung wirklich verlassen?", 300, 100);
            if (test == true) {
                primaryStage.close();
            } else {
                e.consume();
            }
        });

        MenuItem addKunde = new MenuItem("Hinzufügen");
        MenuItem ortsverwaltung = new MenuItem("Ortsverwaltung");
        MenuItem tableKunde = new MenuItem("Übersicht anzeigen");
        kunde.getItems().addAll(addKunde, ortsverwaltung, tableKunde);

        ortsverwaltung.setOnAction(e -> Ortsverwaltung.display(sql));
        addKunde.setOnAction(e -> {
            KundenAdd.display(sql);
            kundenT.refresh();
        });

        MenuItem addArtikel = new MenuItem("Hinzufügen");
        MenuItem addWarengruppe = new MenuItem("Warengruppen verwalten");
        MenuItem tableArtikel = new MenuItem("Übersicht anzeigen");
        artikel.getItems().addAll(addArtikel, addWarengruppe, tableArtikel);

        addArtikel.setOnAction(e -> ArtikelAdd.display(sql));
        addWarengruppe.setOnAction(e -> Warengruppen.display(sql));

        MenuItem createAngebot = new MenuItem("Erstellen");
        MenuItem tableAngebot = new MenuItem("Übersicht anzeigen");
        angebot.getItems().addAll(createAngebot, tableAngebot);

        createAngebot.setOnAction(e -> AngebotAdd.display(this));

        MenuItem createRechnung = new MenuItem("Erstellen");
        createRechnung.setOnAction(e -> RechnungsAdd.display(this));
        MenuItem tableRechnung = new MenuItem("Übersicht anzeigen");
        rechnung.getItems().addAll(createRechnung, tableRechnung);

        menu.getMenus().addAll(allgemein, kunde, artikel, angebot, rechnung);
        BorderPane pane = new BorderPane();
        pane.setTop(menu);
        mainScreen = new Scene(pane, 1000, 600);

        pane.setCenter(kundenVB);
        tableArtikel.setOnAction(e -> {
            try {
                sql.loadDataArtikel();
            } catch (SQLException exc) {
                ConfirmBox.display2("Fehler", "Fehler beim Laden der Artikel");
            }
            pane.setCenter(artikelVB);
        });
        pane.setBottom(null);
        tableKunde.setOnAction(e -> {
            try {
                sql.loadDataKunde();
            } catch (SQLException exc) {
                ConfirmBox.display2("Fehler", "Fehler beim Laden der Kunden");
            }
            pane.setCenter(kundenVB);
        });
        tableAngebot.setOnAction(e -> {
            try {
                sql.loadDataAngebot(false);
            } catch (SQLException exc) {
                ConfirmBox.display2("Fehler", "Fehler beim Laden der Angebote");
                System.out.println(exc.getMessage());
            }
            pane.setCenter(angebotT);
        });
        tableRechnung.setOnAction(e -> {
            try {
                sql.loadDataRechnung(false);
            } catch (SQLException exc) {
                ConfirmBox.display2("Fehler", "Fehler beim Laden der Rechnungen");
                System.out.println(exc.getMessage());
            }
            pane.setCenter(rechnungT);
        });

        primaryStage.setScene(loginScreen);
        primaryStage.setTitle("Verwaltungssoftware ");
        primaryStage.show();
    }

    public VBox createTableRechnung() {
        TableView<Angebot> rechnungT = new TableView();
        rechnungT.setPrefSize(100000, 100000);
        rechnungT.setOnMouseClicked((MouseEvent me) -> {
            if (me.getClickCount() == 2) {
                if (!rechnungT.getSelectionModel().isEmpty()) {
                    RechnungDetails.display(sql, rechnungT.getSelectionModel().getSelectedItems().get(0).getAngebotsnummer(),
                            rechnungT.getSelectionModel().getSelectedItems().get(0).getKunde(),
                            rechnungT.getSelectionModel().getSelectedItems().get(0).getDatum());
                    rechnungT.refresh();
                }
            }
        });

        TableColumn rechnungsnummer = new TableColumn("Rechnungsnummer");
        rechnungsnummer.setCellValueFactory(
                new PropertyValueFactory<>("angebotsnummer"));

        TableColumn kunde = new TableColumn("Kunde");
        kunde.setCellValueFactory(
                new PropertyValueFactory<>("kunde"));

        TableColumn datum = new TableColumn("Datum");
        datum.setCellValueFactory(
                new PropertyValueFactory<>("datum"));

        rechnungT.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        rechnungT.setItems(sql.getDataRechnung());
        rechnungT.getColumns().addAll(rechnungsnummer, kunde, datum);

        VBox fAndT = createFilter(rechnungT, "Rechnung", false);

        return fAndT;
    }

    public VBox createTableAngebot() {
        TableView<Angebot> angebotT = new TableView();
        angebotT.setPrefSize(100000, 100000);

        TableColumn angebotsnummer = new TableColumn("Angebotsnummer");
        angebotsnummer.setCellValueFactory(
                new PropertyValueFactory<>("angebotsnummer"));

        TableColumn kunde = new TableColumn("Kunde");
        kunde.setCellValueFactory(
                new PropertyValueFactory<>("kunde"));

        TableColumn datum = new TableColumn("Datum");
        datum.setCellValueFactory(
                new PropertyValueFactory<>("datum"));

        TableColumn akzeptiert = new TableColumn("Akzeptiert?");
        akzeptiert.setCellValueFactory(
                new PropertyValueFactory<>("akzeptiert"));

        angebotT.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        angebotT.setItems(sql.getDataAngebot());
        angebotT.getColumns().addAll(angebotsnummer, kunde, datum, akzeptiert);

        angebotT.setOnMouseClicked((MouseEvent me) -> {
            if (me.getClickCount() == 2) {
                if (!angebotT.getSelectionModel().isEmpty()) {
                    AngebotDetails.display(sql, angebotT.getSelectionModel().getSelectedItems().get(0).getAngebotsnummer(),
                            angebotT.getSelectionModel().getSelectedItems().get(0).getKunde(),
                            angebotT.getSelectionModel().getSelectedItems().get(0).getDatum());
                    angebotT.refresh();
                }
            }
        });
        VBox fAndT = createFilter(angebotT, "Angebot", false);

        return fAndT;
    }

    public VBox createTableArtikel() {
        artikelT.setPrefSize(100000, 100000);
        artikelT.setOnMouseClicked((MouseEvent me) -> {
            if (me.getClickCount() == 2) {
                if (!artikelT.getSelectionModel().isEmpty()) {
                    ArtikelDetails.display(artikelT.getSelectionModel().getSelectedItems().get(0).getArtikelnummer(), sql);
                    artikelT.refresh();
                }
            }
        });

        TableColumn artikelnummer = new TableColumn("Artikelnummer");
        artikelnummer.setCellValueFactory(
                new PropertyValueFactory<>("artikelnummer"));

        TableColumn bezeichnung = new TableColumn("Bezeichnung");
        bezeichnung.setCellValueFactory(
                new PropertyValueFactory<>("bezeichnung"));

        TableColumn zusatztext = new TableColumn("Zusatztext");
        zusatztext.setCellValueFactory(
                new PropertyValueFactory<>("zusatztext"));

        TableColumn warengruppe = new TableColumn("Warengruppe");
        warengruppe.setCellValueFactory(
                new PropertyValueFactory<>("warengruppe"));

        TableColumn einkaufspreis = new TableColumn("Einkaufspreis");
        einkaufspreis.setCellValueFactory(
                new PropertyValueFactory<>("einkaufspreis"));

        TableColumn verkaufspreis = new TableColumn("Verkaufspreis");
        verkaufspreis.setCellValueFactory(
                new PropertyValueFactory<>("verkaufspreis"));

        TableColumn mwst = new TableColumn("MwSt.");
        mwst.setCellValueFactory(
                new PropertyValueFactory<>("mwst"));

        TableColumn menge = new TableColumn("Bestand");
        menge.setCellValueFactory(
                new PropertyValueFactory<>("bestand"));

        TableColumn datum = new TableColumn("Datum");
        datum.setCellValueFactory(
                new PropertyValueFactory<>("datum"));

        artikelT.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        artikelT.setItems(sql.getDataArtikel());
        artikelT.getColumns().addAll(artikelnummer, bezeichnung, zusatztext, warengruppe, einkaufspreis, verkaufspreis, mwst, menge, datum);

        VBox fAndT = createFilter(artikelT, "Artikel", false);

        return fAndT;
    }

    public VBox createTableKunde() {
        kundenT.setPrefSize(100000, 100000);
        kundenT.setOnMouseClicked((MouseEvent me) -> {
            if (me.getClickCount() == 2) {
                if (!kundenT.getSelectionModel().isEmpty()) {
                    KundeDetails.display(kundenT.getSelectionModel().getSelectedItems().get(0).getKundennummer(), sql);
                    kundenT.refresh();
                }
            }
        });

        TableColumn kundennummer = new TableColumn("Kundennummer");
        kundennummer.setCellValueFactory(
                new PropertyValueFactory<>("kundennummer"));
        //kundennummer.setMaxWidth( 1f * Integer.MAX_VALUE * 12 );

        TableColumn uName = new TableColumn("Name des Unternehmens");
        uName.setCellValueFactory(
                new PropertyValueFactory<>("unternehmensname"));

        TableColumn vorname = new TableColumn("Vorname");
        vorname.setCellValueFactory(
                new PropertyValueFactory<>("vorname"));

        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        TableColumn straße = new TableColumn("Straße");
        straße.setCellValueFactory(
                new PropertyValueFactory<>("straße"));

        TableColumn hausnummer = new TableColumn("Hausnummer");
        hausnummer.setCellValueFactory(
                new PropertyValueFactory<>("hausnummer"));

        TableColumn zusatz = new TableColumn("Adresszusatz");
        zusatz.setCellValueFactory(
                new PropertyValueFactory<>("zusatz"));

        TableColumn plz = new TableColumn("Postleitzahl");
        plz.setCellValueFactory(
                new PropertyValueFactory<>("plz"));

        TableColumn ort = new TableColumn("Ort");
        ort.setCellValueFactory(
                new PropertyValueFactory<>("ort"));

        TableColumn land = new TableColumn("Land");
        land.setCellValueFactory(
                new PropertyValueFactory<>("land"));

        kundenT.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        kundenT.setItems(sql.getDataKunde());
        kundenT.getColumns().addAll(kundennummer, uName, vorname, name, straße, hausnummer, zusatz, plz, ort, land);

        VBox fAndT = createFilter(kundenT, "Kunde", false);

        return fAndT;
    }

    public VBox createFilter(TableView t, String identifier, boolean all) {
        Label filter = new Label("Filter :");
        TextField filterField = new TextField();
        Button test = new Button("Hinzufügen");
        filterField.setOnKeyReleased((KeyEvent ke) -> {
            try {
                if (identifier.equals("Kunde")) {
                    if (filterField.getText().isEmpty()) {
                        sql.loadDataKunde();
                        t.setItems(sql.getDataKunde());
                    } else {
                        sql.loadFilteredKunden(filterField.getText());
                        t.setItems(sql.getDataFilteredKunde());
                    }
                } else if (identifier.equals("Artikel")) {
                    if (filterField.getText().isEmpty()) {
                        sql.loadDataArtikel();
                        t.setItems(sql.getDataArtikel());
                    } else {
                        sql.loadFilteredArtikel(filterField.getText());
                        t.setItems(sql.getDataFilteredArtikel());
                    }
                } else if (identifier.equals("Angebot")) {
                    if (filterField.getText().isEmpty()) {
                        sql.loadDataAngebot(all);
                        t.setItems(sql.getDataAngebot());
                    } else {
                        sql.loadFilteredAngebote(filterField.getText(), all);
                        t.setItems(sql.getDataFilteredAngebot());
                    }
                } else if (identifier.equals("Rechnung")) {
                    if (filterField.getText().isEmpty()) {
                        sql.loadDataRechnung(all);
                        t.setItems(sql.getDataRechnung());
                    } else {
                        sql.loadFilteredRechnung(filterField.getText(), all);
                        t.setItems(sql.getDataFilteredRechnung());
                    }
                }
            } catch (SQLException ex) {
                ConfirmBox.display2("Fehler", "Fehler beim Filtern");
                System.out.println(ex.getMessage() + "käse");
            }
        });
        HBox filterBox = new HBox();
        filterBox.setPadding(new Insets(5, 5, 5, 5));
        filterBox.setSpacing(8);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.getChildren().addAll(filter, filterField);

        VBox fAndT = new VBox();
        fAndT.getChildren().addAll(filterBox, t);
        return fAndT;
    }

    public VBox createFilterAngebotRechnung(TableView t, String identifier, boolean all, Button add) {
        Label filter = new Label("Filter :");
        TextField filterField = new TextField();
        filterField.setOnKeyReleased((KeyEvent ke) -> {
            try {
                if (identifier.equals("Kunde")) {
                    if (filterField.getText().isEmpty()) {
                        sql.loadDataKunde();
                        t.setItems(sql.getDataKunde());
                    } else {
                        sql.loadFilteredKunden(filterField.getText());
                        t.setItems(sql.getDataFilteredKunde());
                    }
                } else if (identifier.equals("Artikel")) {
                    if (filterField.getText().isEmpty()) {
                        sql.loadDataArtikel();
                        t.setItems(sql.getDataArtikel());
                    } else {
                        sql.loadFilteredArtikel(filterField.getText());
                        t.setItems(sql.getDataFilteredArtikel());
                    }
                } else if (identifier.equals("Angebot")) {
                    if (filterField.getText().isEmpty()) {
                        sql.loadDataAngebot(all);
                        t.setItems(sql.getDataAngebot());
                    } else {
                        sql.loadFilteredAngebote(filterField.getText(), all);
                        t.setItems(sql.getDataFilteredAngebot());
                    }
                } else if (identifier.equals("Rechnung")) {
                    if (filterField.getText().isEmpty()) {
                        sql.loadDataRechnung(all);
                        t.setItems(sql.getDataRechnung());
                    } else {
                        sql.loadFilteredRechnung(filterField.getText(), all);
                        t.setItems(sql.getDataFilteredRechnung());
                    }
                }
            } catch (SQLException ex) {
                ConfirmBox.display2("Fehler", "Fehler beim Filtern");
                System.out.println(ex.getMessage() + "käse");
            }
        });
        HBox filterBox = new HBox();
        filterBox.setPadding(new Insets(5, 5, 5, 5));
        filterBox.setSpacing(8);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.getChildren().addAll(filter, filterField, add);

        VBox fAndT = new VBox();
        fAndT.getChildren().addAll(filterBox, t);
        return fAndT;
    }

    public ObservableList<Artikel> filterList(ObservableList<Artikel> inputList, String filter) {
        ObservableList outputList = FXCollections.observableArrayList();
        for (int index = 0; index < inputList.size(); index++) {
            Artikel filteredObject = inputList.get(index);
            if (filteredObject.getArtikelnummer().contains(filter)) {
                outputList.add(filteredObject);
            }
        }
        return outputList;
    }

    public VBox createFilter(TableView t, ObservableList ol) {
        Label filter = new Label("Filter :");
        TextField filterField = new TextField();
        Button test = new Button("Hinzufügen");
        filterField.setOnKeyReleased((KeyEvent ke) -> {
            if (filterField.getText().isEmpty()) {
                t.setItems(ol);
            } else {
                ObservableList<Artikel> newList = filterList(ol, filterField.getText());
                t.setItems(newList);
            }
        });
        HBox filterBox = new HBox();
        filterBox.setPadding(new Insets(5, 5, 5, 5));
        filterBox.setSpacing(8);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.getChildren().addAll(filter, filterField);

        VBox fAndT = new VBox();
        fAndT.getChildren().addAll(filterBox, t);
        return fAndT;
    }

    public VBox createFilterAngebotRechnung(TableView t, ObservableList ol, Button add) {
        Label filter = new Label("Filter :");
        TextField filterField = new TextField();
        filterField.setOnKeyReleased((KeyEvent ke) -> {
            if (filterField.getText().isEmpty()) {
                t.setItems(ol);
            } else {
                ObservableList<Artikel> newList = filterList(ol, filterField.getText());
                t.setItems(newList);
            }
        });
        HBox filterBox = new HBox();
        filterBox.setPadding(new Insets(5, 5, 5, 5));
        filterBox.setSpacing(8);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.getChildren().addAll(filter, filterField, add);

        VBox fAndT = new VBox();
        fAndT.getChildren().addAll(filterBox, t);
        return fAndT;
    }

    public VBox createFilterScroll(ScrollPane s) {
        Label filter = new Label("Filter :");
        TextField filterField = new TextField();

        HBox filterBox = new HBox();
        filterBox.setPadding(new Insets(5, 5, 5, 5));
        filterBox.setSpacing(8);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.getChildren().addAll(filter, filterField);

        VBox fAndT = new VBox();
        fAndT.getChildren().addAll(filterBox, s);
        return fAndT;
    }

    /**
     * @param args the command line arguments
     */
    public VBox getAngebotTable() {
        return angebotT;
    }

    public VBox getRechnungTable() {
        return rechnungT;
    }

    public VBox getKundenTable() {
        return kundenVB;
    }

    public VBox getArtikelTable() {
        return artikelVB;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
