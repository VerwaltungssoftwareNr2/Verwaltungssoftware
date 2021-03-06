/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verwaltungssoftware.GUI;

import com.itextpdf.text.DocumentException;
import com.verwaltungssoftware.database.SqlConnector;
import com.verwaltungssoftware.objects.Angebot;
import com.verwaltungssoftware.objects.Artikel;
import com.verwaltungssoftware.objects.User;
import com.verwaltungssoftware.pdf.PdfCreator;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Lucas
 */
public class AngebotAdd {

    static Scene kundenInfo, übernahme, posten, summen;
    static String tempOldId = null;
    static double verkaufsPreis = 0; //Einzelpreis
    static double m = 0;
    static double mwstEinzeln = 0;
    static double mwstGesamt = 0;
    static double gesamtpreis = 0; //Verkaufspreis * Menge pro Artikel/Zeile
    static double alternativpreis = 0;
    static double endpreisNetto = 0;
    static double endpreisBrutto = 0;

    public static void display(GUI_Verwaltungssoftware mainGui) {
        GUI_Verwaltungssoftware gui = new GUI_Verwaltungssoftware();
        gui.username = mainGui.username;
        gui.password = mainGui.password;
        gui.sql = new SqlConnector(gui.username, gui.password);
        gui.rechnungT = gui.createTableRechnung();
        gui.angebotT = gui.createTableAngebot();
        gui.artikelT = new TableView<>();
        gui.artikelVB = gui.createTableArtikel();
        gui.kundenT = new TableView<>();
        gui.kundenVB = gui.createTableKunde();
        try {
            gui.sql.loadDataKunde();
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Laden der Kunden");
            System.out.println("Fehler beim laden der Kunden: " + exc.getMessage());
        }
        try {
            gui.sql.loadDataArtikel();
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Laden der Artikel");
            System.out.println("Fehler beim laden der Artikel: " + exc.getMessage());
        }
        try {
            gui.sql.loadDataAngebot(true);
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Laden der Angebote");
            System.out.println("Fehler beim laden der Angebote: " + exc.getMessage());
        }
        String titleK = "Angebot erstellen: Kundendaten";
        String titleÜ = "Angebot erstellen: Übernahme";
        String titleP = "Angebot erstellen: Posten hinzufügen";
        String titleS = "Angebot erstellen: Summen";

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(titleK);

        Label aNr = new Label("Angebotsnummer");
        aNr.setPrefWidth(200);
        Label kNr = new Label("Kundennummer");
        kNr.setPrefWidth(200);
        Label anredeL = new Label("Anrede");
        anredeL.setPrefWidth(200);
        Label vorname = new Label("Vorname");
        vorname.setPrefWidth(200);
        Label name = new Label("Nachname");
        name.setPrefWidth(200);
        Label straße = new Label("Straße");
        straße.setPrefWidth(200);
        Label plz = new Label("PLZ");
        plz.setPrefWidth(200);
        Label ort = new Label("Ort");
        ort.setPrefWidth(200);
        Label datum = new Label("Datum");
        datum.setPrefWidth(200);
        Label zusatz = new Label("Hinweis");
        zusatz.setPrefWidth(200);

        TextField anredeT = new TextField();
        anredeT.setPrefWidth(300);
        Label aNRT = new Label();
        aNRT.setText(null);
        TextField kNRT = new TextField();
        kNRT.setPrefWidth(300);
        TextField vornameT = new TextField();
        vornameT.setPrefWidth(300);
        TextField nameT = new TextField();
        nameT.setPrefWidth(300);
        TextField straßeT = new TextField();
        straßeT.setPrefWidth(300);
        TextField plzT = new TextField();
        plzT.setPrefWidth(300);
        TextField ortT = new TextField();
        ortT.setPrefWidth(300);
        DateTimeFormatter dateTf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate ld = LocalDate.now();
        Label datumL = new Label(ld.format(dateTf));
        TextArea zusatzT = new TextArea();
        zusatzT.setPrefWidth(300);

        kNRT.setEditable(false);
        anredeT.setEditable(false);
        vornameT.setEditable(false);
        nameT.setEditable(false);
        straßeT.setEditable(false);
        plzT.setEditable(false);
        ortT.setEditable(false);

        Button search2 = new Button("Suchen");
        Button cancel = new Button("Abbrechen");
        Button cont = new Button("Weiter");

        HBox laTe1 = new HBox();
        laTe1.setPadding(new Insets(10));
        laTe1.setSpacing(8);
        HBox laTe2 = new HBox();
        laTe2.setPadding(new Insets(10));
        laTe2.setSpacing(8);
        HBox laTe3 = new HBox();
        laTe3.setPadding(new Insets(10));
        laTe3.setSpacing(8);
        HBox laTe4 = new HBox();
        laTe4.setPadding(new Insets(10));
        laTe4.setSpacing(8);
        HBox laTe5 = new HBox();
        laTe5.setPadding(new Insets(10));
        laTe5.setSpacing(8);
        HBox laTe6 = new HBox();
        laTe6.setPadding(new Insets(10));
        laTe6.setSpacing(8);
        HBox laTe7 = new HBox();
        laTe7.setPadding(new Insets(10));
        laTe7.setSpacing(8);
        HBox laTe8 = new HBox();
        laTe8.setPadding(new Insets(10));
        laTe8.setSpacing(8);
        HBox laTe9 = new HBox();
        laTe9.setPadding(new Insets(10));
        laTe9.setSpacing(8);
        HBox laTe10 = new HBox();
        laTe10.setPadding(new Insets(10));
        laTe10.setSpacing(8);

        laTe1.getChildren().addAll(aNr, aNRT);
        laTe2.getChildren().addAll(datum, datumL);
        laTe3.getChildren().addAll(kNr, kNRT, search2);
        laTe4.getChildren().addAll(anredeL, anredeT);
        laTe5.getChildren().addAll(vorname, vornameT);
        laTe6.getChildren().addAll(name, nameT);
        laTe7.getChildren().addAll(straße, straßeT);
        laTe8.getChildren().addAll(plz, plzT);
        laTe9.getChildren().addAll(ort, ortT);
        laTe10.getChildren().addAll(zusatz, zusatzT);

        search2.setOnAction(e -> {
            TablePopup.display(gui, "Angebot erstellen: Auswahl des Kunden", gui.kundenT);
            try {
                aNRT.setText(gui.sql.generateNextOfferNumber(ld.toString()));
            } catch (SQLException exc) {
                ConfirmBox.display2("Fehler", "Fehler beim Erstellen einer Angebotsnummer");
                System.out.println(exc.getMessage());
            }
            kNRT.setText(gui.tempKunde[0]);
            anredeT.setText(gui.tempKunde[1]);
            vornameT.setText(gui.tempKunde[2]);
            nameT.setText(gui.tempKunde[3]);
            straßeT.setText(gui.tempKunde[4]);
            plzT.setText(gui.tempKunde[5]);
            ortT.setText(gui.tempKunde[6]);

        });

        cancel.setOnAction(e -> popupStage.close());
        cont.setOnAction(e -> {
            if (aNRT.getText() != null) {
                popupStage.setScene(übernahme);
                popupStage.setTitle(titleÜ);
            }
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(cancel, cont);
        buttons.setPadding(new Insets(10, 10, 10, 10));
        buttons.setSpacing(8);
        buttons.setAlignment(Pos.CENTER);
        VBox sum = new VBox();
        sum.getChildren().addAll(laTe1, laTe2, laTe3, laTe4, laTe5, laTe6, laTe7, laTe8, laTe9, laTe10);
        BorderPane pane = new BorderPane();
        pane.setCenter(sum);
        pane.setBottom(buttons);
        kundenInfo = new Scene(pane, 800, 600); //KUNDENINFO ENDE

        TableView<Angebot> aAndR = new TableView();
        aAndR.setPrefSize(100000, 100000);
        TableColumn angebotsnummer = new TableColumn("Angebotsnummer");
        angebotsnummer.setCellValueFactory(
                new PropertyValueFactory<>("angebotsnummer"));

        TableColumn kunde = new TableColumn("Kunde");
        kunde.setCellValueFactory(
                new PropertyValueFactory<>("kunde"));

        TableColumn datumTC = new TableColumn("Datum");
        datumTC.setCellValueFactory(
                new PropertyValueFactory<>("datum"));

        TableColumn akzeptiert = new TableColumn("Akzeptiert?");
        akzeptiert.setCellValueFactory(
                new PropertyValueFactory<>("akzeptiert"));

        TableView<Artikel> aFromAR = new TableView();
        aFromAR.setPrefSize(100000, 100000);
        TableColumn artikelnummer = new TableColumn("Artikelnummer");
        artikelnummer.setCellValueFactory(
                new PropertyValueFactory<>("artikelnummer"));

        TableColumn bezeichnung = new TableColumn("Bezeichnung");
        bezeichnung.setCellValueFactory(
                new PropertyValueFactory<>("bezeichnung"));

        TableColumn zusatztextTC = new TableColumn("Zusatztext");
        zusatztextTC.setCellValueFactory(
                new PropertyValueFactory<>("zusatztext"));

        TableColumn einkaufspreis = new TableColumn("Einkaufspreis");
        einkaufspreis.setCellValueFactory(
                new PropertyValueFactory<>("einkaufspreis"));

        TableColumn verkaufspreis = new TableColumn("Verkaufspreis");
        verkaufspreis.setCellValueFactory(
                new PropertyValueFactory<>("verkaufspreis"));

        TableColumn mwst = new TableColumn("MwSt.");
        mwst.setCellValueFactory(
                new PropertyValueFactory<>("mwst"));

        TableColumn menge = new TableColumn("Menge");
        menge.setCellValueFactory(
                new PropertyValueFactory<>("bestand"));

        TableColumn datumA = new TableColumn("Datum");
        datumA.setCellValueFactory(
                new PropertyValueFactory<>("datum"));

        aAndR.setOnMouseClicked((MouseEvent me) -> {
            if (me.getClickCount() == 2) {
                if (!aAndR.getSelectionModel().isEmpty()) {
                    String nummer = aAndR.getSelectionModel().getSelectedItems().get(0).getAngebotsnummer();
                    System.out.println(nummer);
                    try {
                        gui.sql.loadArtikelFromAngebot(nummer);
                    } catch (SQLException exc) {
                        ConfirmBox.display2("Fehler", "Fehler beim Laden der Artikel des Angebots");
                        System.out.println(exc.getMessage());
                    }

                    aFromAR.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    aFromAR.setItems(gui.sql.getDataArtikelInAngebot());
                    if (aFromAR.getColumns().isEmpty()) {
                        aFromAR.getColumns().addAll(artikelnummer, bezeichnung, zusatztextTC, einkaufspreis, verkaufspreis, mwst, menge, datumA);
                    }
                }
            }
        });
        aAndR.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        aAndR.setItems(gui.sql.getDataAngebot());
        aAndR.getColumns().addAll(angebotsnummer, kunde, datumTC, akzeptiert);

        Button add = new Button("Hinzufügen");
        Button add2 = new Button("Hinzufügen");
        Button back = new Button("Zurück");
        back.setOnAction(e -> {
            popupStage.setScene(kundenInfo);
            popupStage.setTitle(titleK);

        });
        Button conti = new Button("Weiter");
        conti.setOnAction(e -> {
            popupStage.setScene(posten);
            popupStage.setTitle(titleP);
        });

        VBox aAndRV = gui.createFilterAngebotRechnung(aAndR, "Angebot", true, add);
        VBox aFromARV = gui.createFilterAngebotRechnung(aFromAR, gui.sql.getDataArtikelInAngebot(), add2);

        HBox t1 = new HBox();
        t1.setPadding(new Insets(10));
        t1.setSpacing(8);
        HBox t2 = new HBox();
        t2.setPadding(new Insets(10));
        t2.setSpacing(8);
        t1.getChildren().addAll(aAndRV);
        t2.getChildren().addAll(aFromARV);

        VBox tables = new VBox();
        tables.getChildren().addAll(t1, t2);
        tables.setSpacing(20);

        HBox buttons2 = new HBox();
        buttons2.getChildren().addAll(back, conti);
        buttons2.setPadding(new Insets(10, 10, 10, 10));
        buttons2.setSpacing(8);
        buttons2.setAlignment(Pos.CENTER);

        BorderPane pane2 = new BorderPane();
        pane2.setCenter(tables);
        pane2.setBottom(buttons2);

        übernahme = new Scene(pane2, 850, 500); //ÜBERNAHME ENDE

        ObservableList<Artikel> dataNewAngebot = FXCollections.observableArrayList();
        TableView<Artikel> angebotEntwurf = new TableView();
        angebotEntwurf.setPrefSize(100000, 100000);
        TableColumn artNumEntwurf = new TableColumn("Artikelnummer");
        artNumEntwurf.setCellValueFactory(
                new PropertyValueFactory<>("artikelnummer"));

        TableColumn bezEntwurf = new TableColumn("Bezeichnung");
        bezEntwurf.setCellValueFactory(
                new PropertyValueFactory<>("bezeichnung"));

        TableColumn zusatzEntwurf = new TableColumn("Zusatztext");
        zusatzEntwurf.setCellValueFactory(
                new PropertyValueFactory<>("zusatztext"));

        TableColumn ePreisEntwurf = new TableColumn("Einkaufspreis");
        ePreisEntwurf.setCellValueFactory(
                new PropertyValueFactory<>("einkaufspreis"));

        TableColumn verPreisEntwurf = new TableColumn("Verkaufspreis");
        verPreisEntwurf.setCellValueFactory(
                new PropertyValueFactory<>("verkaufspreis"));

        TableColumn mwstEntwurf = new TableColumn("MwSt.");
        mwstEntwurf.setCellValueFactory(
                new PropertyValueFactory<>("mwst"));

        TableColumn mengeEntwurf = new TableColumn("Menge");
        mengeEntwurf.setCellValueFactory(
                new PropertyValueFactory<>("mengeTemp"));

        TableColumn datumEntwurf = new TableColumn("Datum");
        datumEntwurf.setCellValueFactory(
                new PropertyValueFactory<>("datum"));
        angebotEntwurf.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        angebotEntwurf.setItems(dataNewAngebot);
        angebotEntwurf.getColumns().addAll(artNumEntwurf, bezEntwurf, zusatzEntwurf, ePreisEntwurf, verPreisEntwurf, mwstEntwurf, mengeEntwurf, datumEntwurf);

        //fügt gesamtes Angebot als Vorlage hinzu
        add.setOnAction((ActionEvent) -> {
            if (!gui.sql.getDataArtikelInAngebot().isEmpty()) {
                dataNewAngebot.clear();
                for (Artikel a : gui.sql.getDataArtikelInAngebot()) {
                    dataNewAngebot.add(a);
                }
            }
        });

        //fügt einzelne Artikel aus bestehenden Angeboten hinzu
        add2.setOnAction((ActionEvent) -> {
            boolean test = false;
            if (!aFromAR.getItems().isEmpty()) {
                if (!aFromAR.getSelectionModel().getSelectedItems().isEmpty()) {
                    String nummer = aFromAR.getSelectionModel().getSelectedItems().get(0).getArtikelnummer(); //selektiertes Item
                    if (dataNewAngebot.isEmpty()) { //wenn neue Liste leer
                        for (Artikel a : gui.sql.getDataArtikelInAngebot()) {
                            if (nummer.equals(a.getArtikelnummer())) {
                                dataNewAngebot.add(a);
                                break;
                            }
                        }
                    } else {
                        for (Artikel a : gui.sql.getDataArtikelInAngebot()) {
                            if (a.getArtikelnummer().equals(nummer)) {
                                for (Artikel b : dataNewAngebot) {
                                    if (a.getArtikelnummer().equals(b.getArtikelnummer())) {
                                        test = true;
                                        break;
                                    }
                                }
                                if (test == false) {
                                    dataNewAngebot.add(a);
                                }
                            }
                        }
                    }
                }
            }
        });

        VBox entwurfV = gui.createFilter(angebotEntwurf, dataNewAngebot);

        Label artNr = new Label("Artikelnummer");
        artNr.setPrefWidth(100);
        Label anzahl = new Label("Anzahl");
        anzahl.setPrefWidth(100);
        Label bezeichnung2 = new Label("Bezeichnung");
        bezeichnung2.setPrefWidth(100);
        Label nettopreis = new Label("Nettopreis");
        nettopreis.setPrefWidth(100);
        Label summe = new Label("Summe");
        summe.setPrefWidth(100);
        Label rabatt2 = new Label("Rabatt in %");
        rabatt2.setPrefWidth(100);
        Label zusatztext = new Label("Zusatztext");
        zusatztext.setPrefWidth(100);

        CheckBox alternativ = new CheckBox("Alternativ");
        alternativ.setPrefWidth(100);

        TextField artNrT = new TextField();
        artNrT.setPrefWidth(300);
        TextField anzahlT = new TextField();
        anzahlT.setPrefWidth(300);
        TextField bezeichnungT = new TextField();
        bezeichnungT.setPrefWidth(300);
        bezeichnungT.setEditable(false);
        TextField nettopreisT = new TextField();
        nettopreisT.setPrefWidth(300);
        TextField summeT = new TextField();
        summeT.setPrefWidth(300);
        summeT.setEditable(false);
        TextField rabattT = new TextField();
        rabattT.setPrefWidth(300);
        TextArea zusatztextT = new TextArea();
        zusatztextT.setPrefWidth(300);
        zusatztextT.setEditable(false);

        Button search = new Button("Suchen");

        HBox laTe11 = new HBox();
        laTe11.setPadding(new Insets(10));
        laTe11.setSpacing(8);
        HBox laTe12 = new HBox();
        laTe12.setPadding(new Insets(10));
        laTe12.setSpacing(8);
        HBox laTe13 = new HBox();
        laTe13.setPadding(new Insets(10));
        laTe13.setSpacing(8);
        HBox laTe14 = new HBox();
        laTe14.setPadding(new Insets(10));
        laTe14.setSpacing(8);

        laTe11.getChildren().addAll(artNr, artNrT, alternativ, search);
        laTe12.getChildren().addAll(bezeichnung2, bezeichnungT, anzahl, anzahlT);
        laTe13.getChildren().addAll(nettopreis, nettopreisT, rabatt2, rabattT);
        laTe14.getChildren().addAll(summe, summeT, zusatztext, zusatztextT);

        artNrT.setOnKeyReleased((KeyEvent ke) -> {
            if (summeT.getText() != null) {
                for (Artikel art : dataNewAngebot) {
                    if (tempOldId.equals(art.getArtikelnummer())) {
                        try {
                            gui.sql.updateArtikelNummer(art.getArtikelnummer(), artNrT.getText());
                            art.setArtikelnummer(artNrT.getText());
                            tempOldId = artNrT.getText();
                        } catch (SQLException exc) {
                            ConfirmBox.display2("Fehler", "Fehler beim aktualisieren der Artikelnummer");
                        }
                        angebotEntwurf.refresh();
                        break;
                    }
                }
            }
        });
        alternativ.setOnMouseClicked((MouseEvent me) -> {
            for (Artikel art : dataNewAngebot) {
                if (artNrT.getText().equals(art.getArtikelnummer())) {
                    if (alternativ.isSelected()) {
                        art.setAlternative("true");
                        System.out.println(art.getAlternative());
                    } else {
                        art.setAlternative("false");
                        System.out.println(art.getAlternative());
                    }
                }
            }
        });

        nettopreisT.setOnKeyReleased((KeyEvent ke) -> {
            if (!artNrT.getText().isEmpty() && !nettopreisT.getText().isEmpty() && nettopreisT.getText().matches("\\d*(\\.\\d*)?")) {
                for (Artikel art : dataNewAngebot) {
                    if (art.getArtikelnummer().equals(artNrT.getText())) {
                        try {
                            gui.sql.updateArtikelVerkaufsPreis(art.getArtikelnummer(), nettopreisT.getText());
                            art.setVerkaufspreis(nettopreisT.getText());
                            angebotEntwurf.refresh();
                        } catch (SQLException exc) {
                            ConfirmBox.display2("Fehler", "Fehler beim aktualisieren des Artikels");
                            System.out.println(exc.getMessage());
                        }
                        if (art.getMengeTemp() != null) {
                            if (art.getRabattTemp() != null) {
                                summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getMengeTemp()) * (1 - Double.valueOf(art.getRabattTemp()) / 100)));
                            } else {
                                summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getMengeTemp())));
                            }
                        } else {
                            if (art.getRabattTemp() != null) {
                                summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getBestand()) * (1 - Double.valueOf(art.getRabattTemp()) / 100)));
                            } else {
                                summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getBestand())));
                            }
                        }
                    }
                }
            }
        });
        anzahlT.setOnKeyReleased((KeyEvent ke) -> {
            if (!artNrT.getText().isEmpty() && anzahlT.getText().matches("[0-9]*")) { //nur ganze Zahlen
                for (Artikel art : dataNewAngebot) {
                    if (art.getArtikelnummer().equals(artNrT.getText())) {
                        if (!anzahlT.getText().trim().isEmpty()) {
                            if (Integer.valueOf(anzahlT.getText()) <= Integer.valueOf(art.getBestand())) { //wenn aktuelle Menge kleiner als Bestand
                                art.setMengeTemp(anzahlT.getText()); //Menge zuweisen
                                if (art.getRabattTemp() != null) {
                                    summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getMengeTemp()) * (1 - Double.valueOf(art.getRabattTemp()) / 100)));
                                } else {
                                    summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getMengeTemp())));
                                }
                                angebotEntwurf.refresh();
                                break;
                            }
                        }
                    }
                }
                System.out.println(dataNewAngebot.get(0).getMengeTemp() + "schauen");

            }
        });
        rabattT.setOnKeyReleased((KeyEvent ke) -> {
            if (!artNrT.getText().isEmpty() && rabattT.getText().matches("\\d*(\\.\\d*)?")) {
                for (Artikel art : dataNewAngebot) {
                    if (art.getArtikelnummer().equals(artNrT.getText())) {
                        if (!rabattT.getText().trim().isEmpty()) {
                            art.setRabattTemp(rabattT.getText());
                            if (art.getMengeTemp() == null) {//wenn null dann gesamter Bestand
                                summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getBestand()) * (1 - Double.valueOf(rabattT.getText()) / 100)));
                            } else {
                                summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getMengeTemp()) * (1 - Double.valueOf(rabattT.getText()) / 100)));
                            }
                        } else {
                            art.setRabattTemp(null);
                            if (art.getMengeTemp() == null) {//wenn null dann gesamter Bestand
                                summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getBestand())));
                            } else {
                                summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getMengeTemp())));
                            }
                        }

                    }
                }
            }
        });
        angebotEntwurf.setOnMouseClicked((MouseEvent me) -> {
            if (me.getClickCount() == 2) {
                if (!angebotEntwurf.getSelectionModel().isEmpty()) {
                    double summeTemp;
                    artNrT.setText(angebotEntwurf.getSelectionModel().getSelectedItems().get(0).getArtikelnummer());
                    tempOldId = artNrT.getText();
                    bezeichnungT.setText(angebotEntwurf.getSelectionModel().getSelectedItems().get(0).getBezeichnung());
                    nettopreisT.setText(angebotEntwurf.getSelectionModel().getSelectedItems().get(0).getVerkaufspreis());
                    rabattT.setText(angebotEntwurf.getSelectionModel().getSelectedItems().get(0).getRabattTemp());
                    if (rabattT.getText() == null) {
                        if (angebotEntwurf.getSelectionModel().getSelectedItems().get(0).getMengeTemp() == null) {
                            summeTemp = Double.valueOf(nettopreisT.getText()) * Double.valueOf(angebotEntwurf.getSelectionModel().getSelectedItems().get(0).getBestand());
                        } else {
                            summeTemp = Double.valueOf(nettopreisT.getText()) * Double.valueOf(angebotEntwurf.getSelectionModel().getSelectedItems().get(0).getMengeTemp());
                        }
                    } else {
                        if (angebotEntwurf.getSelectionModel().getSelectedItems().get(0).getMengeTemp() == null) {
                            summeTemp = Double.valueOf(nettopreisT.getText()) * Double.valueOf(angebotEntwurf.getSelectionModel().getSelectedItems().get(0).getBestand());
                            summeTemp = summeTemp * (Double.valueOf(rabattT.getText()) / 100);
                        } else {
                            summeTemp = Double.valueOf(nettopreisT.getText()) * Double.valueOf(angebotEntwurf.getSelectionModel().getSelectedItems().get(0).getMengeTemp());
                            summeTemp = summeTemp * (Double.valueOf(rabattT.getText()) / 100);
                        }
                    }
                    summeT.setText(String.valueOf(summeTemp));
                    anzahlT.setText(angebotEntwurf.getSelectionModel().getSelectedItems().get(0).getMengeTemp());
                    anzahlT.setPromptText("Aktueller Bestand: " + angebotEntwurf.getSelectionModel().getSelectedItems().get(0).getBestand());
                    zusatztextT.setText(angebotEntwurf.getSelectionModel().getSelectedItems().get(0).getZusatztext());
                    for (Artikel art : dataNewAngebot) {
                        if (artNrT.getText().equals(art.getArtikelnummer())) {
                            if (art.getAlternative() != null) {
                                if (art.getAlternative().equals("1")) {
                                    alternativ.setSelected(true);
                                } else {
                                    alternativ.setSelected(false);
                                }
                            }
                        }
                    }
                }
            }
        });

        search.setOnAction(e -> {
            TablePopup.displayArtikel(gui, "Angebot erstellen: Auswahl des Artikels", gui.artikelT);

            boolean test = false;
            try {
                gui.sql.loadDataArtikel();
            } catch (SQLException exc) {
                ConfirmBox.display2("Fehler", "Fehler beim Laden der Artikel");
                System.out.println(exc.getMessage());
            }
            for (Artikel oldA : gui.sql.getDataArtikel()) {
                if (oldA.getArtikelnummer().equals(gui.tempArtikel[0])) {
                    for (Artikel newA : dataNewAngebot) {
                        if (oldA.getArtikelnummer().equals(newA.getArtikelnummer())) {
                            test = true;
                            break;
                        }
                    }
                    if (test == false) {
                        dataNewAngebot.add(oldA);
                    }
                }
            }
        });
        Button back2 = new Button("Zurück");
        back2.setOnAction(e -> {
            artNrT.clear();
            anzahlT.clear();
            bezeichnungT.clear();
            nettopreisT.clear();
            summeT.setText(null);
            rabattT.clear();
            zusatztextT.clear();
            popupStage.setScene(übernahme);
            popupStage.setTitle(titleÜ);
        });
        Button delete = new Button("Entfernen");

        delete.setOnAction(e -> {
            String item = angebotEntwurf.getSelectionModel().getSelectedItems().get(0).getArtikelnummer();
            for (Artikel art : dataNewAngebot) {
                if (item != null) {
                    if (item.equals(art.getArtikelnummer())) {
                        dataNewAngebot.remove(art);
                        break;
                    }
                }
            }
        });

        TextField summe4 = new TextField();
        summe4.setEditable(false);
        TextField mwtStrT = new TextField();
        mwtStrT.setEditable(false);
        TextField bruttopreisT = new TextField();
        bruttopreisT.setEditable(false);
        TextField gültigT = new TextField();
        TextArea fakturatextT = new TextArea();
        TextField skontotageT = new TextField();
        TextField skontoT = new TextField();
        TextField skontobetragT = new TextField();
        skontobetragT.setEditable(false);

        gültigT.setOnKeyReleased((KeyEvent ke) -> {
            if (!ke.getText().matches("[0-9]*")) {
                StringBuilder sb = new StringBuilder(gültigT.getText());
                sb.deleteCharAt(gültigT.getText().length() - 1);
                gültigT.setText(sb.toString());
                gültigT.positionCaret(gültigT.getText().length());
            }
        });

        skontotageT.setOnKeyReleased((KeyEvent ke) -> {
            if (!ke.getText().matches("[0-9]*")) {
                StringBuilder sb = new StringBuilder(skontotageT.getText());
                sb.deleteCharAt(skontotageT.getText().length() - 1);
                skontotageT.setText(sb.toString());
                skontotageT.positionCaret(skontotageT.getText().length());
            }
        });
        skontoT.setOnKeyReleased((KeyEvent ke) -> {
            if (!skontoT.getText().trim().isEmpty() && skontoT.getText().matches("\\d*(\\.\\d*)?")) {
                skontobetragT.setText(String.valueOf(endpreisBrutto * (1 - (Double.valueOf(skontoT.getText()) / 100))));
            } else {
                if (!skontoT.getText().trim().isEmpty()) {
                    StringBuilder sb = new StringBuilder(skontoT.getText());
                    sb.deleteCharAt(skontoT.getText().length() - 1);
                    skontoT.setText(sb.toString());
                    skontoT.positionCaret(skontoT.getText().length());
                } else {
                    skontobetragT.clear();
                }
            }
        });
        Button con = new Button("Weiter");
        con.setOnAction(e -> {
            boolean test = false;
            for (Artikel aTest : dataNewAngebot) {
                if (aTest.getMengeTemp() == null) {
                    test = true;
                    break;
                }
            }
            if (test != true && !angebotEntwurf.getItems().isEmpty()) {
                for (Artikel a : dataNewAngebot) {
                    if (a.getAlternative() != null) {
                        if (a.getAlternative().equals("0") || a.getAlternative().equals("false")) {
                            verkaufsPreis = Double.parseDouble(a.getVerkaufspreis());
                            m = Double.valueOf(a.getMengeTemp());
                            if (a.getRabattTemp() == null) {
                                gesamtpreis = verkaufsPreis * m;
                            } else { //wenn Rabatt besteht
                                gesamtpreis = verkaufsPreis * m * (1 - Double.parseDouble(a.getRabattTemp()) / 100);
                            }
                        } else if (a.getAlternative().equals("1") || a.getAlternative().equals("true")) { //wenn Artikel alternativ ist
                            gesamtpreis = 0;
                            verkaufsPreis = Double.parseDouble(a.getVerkaufspreis());
                            m = Double.parseDouble(a.getMengeTemp());
                            if (a.getRabattTemp() == null) {
                                alternativpreis = verkaufsPreis * m;
                            } else { //wenn Rabatt besteht
                                alternativpreis = verkaufsPreis * m * (1 - Double.parseDouble(a.getRabattTemp()) / 100);
                            }
                        }
                    }
                    mwstEinzeln = gesamtpreis * (Double.parseDouble(a.getMwst()) / 100);
                    mwstGesamt += mwstEinzeln;
                    endpreisNetto += gesamtpreis;
                }
                endpreisBrutto = endpreisNetto + mwstGesamt;

                summe4.setText(String.valueOf(endpreisNetto));
                summe4.setEditable(false);
                mwtStrT.setText(String.valueOf(mwstGesamt));
                mwtStrT.setEditable(false);
                bruttopreisT.setText(String.valueOf(endpreisBrutto));
                bruttopreisT.setEditable(false);

                popupStage.setScene(summen);
                popupStage.setTitle(titleS);
            }
        });

        VBox sum2 = new VBox();
        sum2.getChildren().addAll(laTe11, laTe12, laTe13, laTe14);

        HBox buttons3 = new HBox();
        buttons3.getChildren().addAll(back2, delete, con);
        buttons3.setPadding(new Insets(10, 10, 10, 10));
        buttons3.setSpacing(8);
        buttons3.setAlignment(Pos.CENTER);

        VBox tablePosten = new VBox();
        tablePosten.getChildren().addAll(entwurfV, sum2);

        BorderPane pane3 = new BorderPane();
        pane3.setCenter(tablePosten);
        pane3.setBottom(buttons3);

        posten = new Scene(pane3, 850, 500); //ENDE POSTEN

        Label summe3 = new Label("Nettobetrag");
        summe3.setPrefWidth(100);
        Label mwtStr = new Label("Mehrwertsteuer");
        mwtStr.setPrefWidth(100);
        Label bruttopreis = new Label("Bruttobetrag");
        bruttopreis.setPrefWidth(100);
        Label gültig = new Label("Zahlungsziel");
        gültig.setPrefWidth(100);
        Label fakturatext = new Label("Fakturatext");
        fakturatext.setPrefWidth(100);
        Label skontotage = new Label("Skontotage");
        skontotage.setPrefWidth(100);
        Label skonto = new Label("Skonto in Prozent");
        skonto.setPrefWidth(100);
        Label skontobetrag = new Label("Skontobetrag");
        skontobetrag.setPrefWidth(100);

        Button abort = new Button("Zurück");
        abort.setOnAction(e -> {
            verkaufsPreis = 0; //Einzelpreis
            m = 0;
            mwstEinzeln = 0;
            mwstGesamt = 0;
            gesamtpreis = 0; //Verkaufspreis * Menge pro Artikel/Zeile
            alternativpreis = 0;
            endpreisNetto = 0;
            endpreisBrutto = 0;
            popupStage.setScene(posten);
            popupStage.setTitle(titleP);
        });
        Button pdfButton = new Button("In PDF-Datei umwandeln");
        Button done = new Button("Abschließen");
        done.setOnAction(e -> {
            boolean test = ConfirmBox.display("Angebotserstellung abschließen", "Möchten sie das Angebot wirklich erstellen?", 400, 100);
            if (test == true) {
                if (!skontoT.getText().trim().isEmpty() && !skontobetragT.getText().trim().isEmpty() && !gültigT.getText().trim().isEmpty() && !skontotageT.getText().trim().isEmpty()) {
                    try {
                        gui.sql.safeNewAngebot(aNRT.getText(), kNRT.getText(), dataNewAngebot, endpreisNetto, endpreisBrutto, mwstGesamt,
                                Double.valueOf(skontoT.getText()), Double.valueOf(skontobetragT.getText()), fakturatextT.getText(), zusatzT.getText(),
                                Integer.valueOf(gültigT.getText()), Integer.valueOf(skontotageT.getText()));
                        gui.sql.loadDataAngebot(false);
                    } catch (SQLException exc) {
                        ConfirmBox.display2("Fehler", "Fehler beim erzeugen des neuen Angebots.");
                    }
                    popupStage.close();
                } else {
                    ConfirmBox.display2("Fehler", "Daten sind unvollständig. Bitte alle Textfelder ausfüllen");
                }
            } else {
                e.consume();
            }
        });

        pdfButton.setOnAction(e -> {
            boolean test = ConfirmBox.display("Angebotserstellung abschließen", "Möchten sie das Angebot wirklich erstellen?", 400, 100);
            if (test == true) {
                if (!skontoT.getText().trim().isEmpty() && !skontobetragT.getText().trim().isEmpty() && !gültigT.getText().trim().isEmpty() && !skontotageT.getText().trim().isEmpty()) {
                    try {
                        gui.sql.safeNewAngebot(aNRT.getText(), kNRT.getText(), dataNewAngebot, endpreisNetto, endpreisBrutto, mwstGesamt,
                                Double.valueOf(skontoT.getText()), Double.valueOf(skontobetragT.getText()), fakturatextT.getText(), zusatzT.getText(),
                                Integer.valueOf(gültigT.getText()), Integer.valueOf(skontotageT.getText()));
                        gui.sql.loadDataAngebot(false);
                        User user = gui.sql.loadUser();
                        PdfCreator pdf = new PdfCreator(user, gui.sql);
                        FileChooser fc = new FileChooser();
                        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF (*.pdf)", "*pdf"));
                        File f = fc.showSaveDialog(new Stage());
                        System.out.println(f);
                        if (f != null && !f.getName().contains(".")) {
                            f = new File(f.getAbsolutePath() + ".pdf");
                        }
                        if (f != null) {
                            try {
                                pdf.createDocument(kNRT.getText(),
                                        aNRT.getText(),
                                        zusatzT.getText(),
                                        Integer.valueOf(gültigT.getText()),
                                        Integer.valueOf(skontotageT.getText()),
                                        Double.valueOf(skontoT.getText()),
                                        fakturatextT.getText(),
                                        false,
                                        f);
                            } catch (DocumentException | IOException | SQLException exc) {
                                System.out.println(exc.getMessage());
                            }
                        }
                    } catch (SQLException exc) {
                        ConfirmBox.display2("Fehler", "Fehler beim erzeugen des neuen Angebots.");
                        System.out.println(exc.getMessage());
                    }
                    popupStage.close();
                } else {
                    ConfirmBox.display2("Fehler", "Daten sind unvollständig. Bitte alle Textfelder ausfüllen");
                }
            } else {
                e.consume();
            }
        });

        HBox laTe15 = new HBox();
        laTe15.setPadding(new Insets(10));
        laTe15.setSpacing(8);
        HBox laTe16 = new HBox();
        laTe16.setPadding(new Insets(10));
        laTe16.setSpacing(8);
        HBox laTe17 = new HBox();
        laTe17.setPadding(new Insets(10));
        laTe17.setSpacing(8);
        HBox laTe18 = new HBox();
        laTe18.setPadding(new Insets(10));
        laTe18.setSpacing(8);
        HBox laTe19 = new HBox();
        laTe19.setPadding(new Insets(10));
        laTe19.setSpacing(8);

        laTe15.getChildren().addAll(summe3, summe4, skontotage, skontotageT);
        laTe16.getChildren().addAll(mwtStr, mwtStrT, skonto, skontoT);
        laTe17.getChildren().addAll(bruttopreis, bruttopreisT, skontobetrag, skontobetragT);
        laTe18.getChildren().addAll(gültig, gültigT);
        laTe19.getChildren().addAll(fakturatext, fakturatextT);

        VBox sum3 = new VBox();
        sum3.getChildren().addAll(laTe15, laTe16, laTe17, laTe18, laTe19);

        HBox buttons4 = new HBox();
        buttons4.getChildren().addAll(abort, pdfButton, done);
        buttons4.setPadding(new Insets(10, 10, 10, 10));
        buttons4.setSpacing(8);
        buttons4.setAlignment(Pos.CENTER);

        BorderPane pane4 = new BorderPane();
        pane4.setCenter(sum3);
        pane4.setBottom(buttons4);

        summen = new Scene(pane4, 600, 400);

        popupStage.setScene(kundenInfo);
        popupStage.show();

    }
}
