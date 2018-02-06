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
public class RechnungsAdd {

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
            gui.sql.loadDataRechnung(true);
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Laden der Rechnungen");
            System.out.println("Fehler beim laden der Rechnungen: " + exc.getMessage());
        }
        String titleK = "Rechnung erstellen: Kundendaten";
        String titleÜ = "Rechnung erstellen: Übernahme";
        String titleP = "Rechnung erstellen: Posten hinzufügen";
        String titleS = "Rechnung erstellen: Summen";

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(titleK);

        Label aNr = new Label("Rechnungsnummer");
        Label kNr = new Label("Kundennummer");
        Label anredeL = new Label("Anrede");
        Label vorname = new Label("Vorname");
        Label name = new Label("Nachname");
        Label straße = new Label("Straße");
        Label plz = new Label("PLZ");
        Label ort = new Label("Ort");
        Label datum = new Label("Datum");
        Label zusatz = new Label("Hinweis");

        TextField anredeT = new TextField();
        Label rNRT = new Label();
        rNRT.setText(null);
        TextField kNRT = new TextField();
        TextField vornameT = new TextField();
        TextField nameT = new TextField();
        TextField straßeT = new TextField();
        TextField plzT = new TextField();
        TextField ortT = new TextField();
        DateTimeFormatter dateTf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate ld = LocalDate.now();
        Label datumL = new Label(ld.format(dateTf));
        TextArea zusatzT = new TextArea();

        vornameT.setEditable(false);
        nameT.setEditable(false);
        straßeT.setEditable(false);
        plzT.setEditable(false);
        ortT.setEditable(false);

        Button search2 = new Button("Suchen");
        search2.setOnAction(e -> {
            TablePopup.display(gui, "Rechnung erstellen: Auswahl des Kunden", gui.kundenT);
            try {
                rNRT.setText(gui.sql.generateRandomBillNumber(ld.toString()));
            } catch (SQLException exc) {
                ConfirmBox.display2("Fehler", "Fehler beim Erstellen einer Rechnungsnummer");
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

        TextField space = new TextField();
        space.setVisible(false);
        TextField space2 = new TextField();
        space2.setVisible(false);
        VBox searchV = new VBox();
        searchV.getChildren().addAll(space, space2, search2);
        searchV.setPadding(new Insets(10));
        searchV.setSpacing(8);

        Button cancel = new Button("Abbrechen");
        cancel.setOnAction(e -> popupStage.close());
        Button cont = new Button("Weiter");
        cont.setOnAction(e -> {
            if (rNRT.getText() != null) {
                popupStage.setScene(übernahme);
                popupStage.setTitle(titleÜ);
            }
        });

        VBox sumL = new VBox();
        sumL.getChildren().addAll(aNr, datum, kNr, anredeL, vorname, name, straße, plz, ort, zusatz);
        sumL.setPadding(new Insets(10));
        sumL.setSpacing(16);

        VBox sumT = new VBox();
        sumT.getChildren().addAll(rNRT, datumL, kNRT, anredeT, vornameT, nameT, straßeT, plzT, ortT, zusatzT);
        sumT.setPadding(new Insets(10));
        sumT.setSpacing(8);

        HBox buttons = new HBox();
        buttons.getChildren().addAll(cancel, cont);
        buttons.setPadding(new Insets(10, 10, 10, 10));
        buttons.setSpacing(8);
        buttons.setAlignment(Pos.CENTER);

        HBox sumsum = new HBox();
        sumsum.getChildren().addAll(sumL, sumT, searchV);

        BorderPane pane = new BorderPane();
        pane.setCenter(sumsum);
        pane.setBottom(buttons);
        kundenInfo = new Scene(pane, 800, 450); //KUNDENINFO ENDE

        TableView<Angebot> aAndR = new TableView();
        aAndR.setPrefSize(100000, 100000);
        TableColumn rechnungsnummer = new TableColumn("Rechnungsnummer");
        rechnungsnummer.setCellValueFactory(
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
                String nummer = aAndR.getSelectionModel().getSelectedItems().get(0).getAngebotsnummer();
                System.out.println(nummer);
                try {
                    gui.sql.loadArtikelFromAngebot(nummer);
                } catch (SQLException exc) {
                    ConfirmBox.display2("Fehler", "Fehler beim Laden der Artikel der Rechnung");
                    System.out.println(exc.getMessage());
                }

                aFromAR.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                aFromAR.setItems(gui.sql.getDataArtikelInAngebot());
                if (aFromAR.getColumns().isEmpty()) {
                    aFromAR.getColumns().addAll(artikelnummer, bezeichnung, zusatztextTC, einkaufspreis, verkaufspreis, mwst, menge, datumA);
                }
            }
        });
        aAndR.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        aAndR.setItems(gui.sql.getDataRechnung());
        aAndR.getColumns().addAll(rechnungsnummer, kunde, datumTC, akzeptiert);

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

        VBox aAndRV = gui.createFilterAngebotRechnung(aAndR, "Rechnung", true, add);
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

        ObservableList<Artikel> dataNewRechnung = FXCollections.observableArrayList();
        TableView<Artikel> rechnungEntwurf = new TableView();
        rechnungEntwurf.setPrefSize(100000, 100000);
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
        rechnungEntwurf.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        rechnungEntwurf.setItems(dataNewRechnung);
        rechnungEntwurf.getColumns().addAll(artNumEntwurf, bezEntwurf, zusatzEntwurf, ePreisEntwurf, verPreisEntwurf, mwstEntwurf, mengeEntwurf, datumEntwurf);

        //fügt gesamte Rechnung als Vorlage hinzu
        add.setOnAction((ActionEvent) -> {
            if (!gui.sql.getDataArtikelInAngebot().isEmpty()) {
                dataNewRechnung.clear();
                for (Artikel a : gui.sql.getDataArtikelInAngebot()) {
                    dataNewRechnung.add(a);
                }
            }
        });

        //fügt einzelne Artikel aus bestehenden Rechnungen hinzu
        add2.setOnAction((ActionEvent) -> {
            boolean test = false;
            if (!aFromAR.getItems().isEmpty()) {
                if (!aFromAR.getSelectionModel().getSelectedItems().isEmpty()) {
                    String nummer = aFromAR.getSelectionModel().getSelectedItems().get(0).getArtikelnummer(); //selektiertes Item
                    if (dataNewRechnung.isEmpty()) { //wenn neue Liste leer
                        for (Artikel a : gui.sql.getDataArtikelInAngebot()) {
                            if (nummer.equals(a.getArtikelnummer())) {
                                dataNewRechnung.add(a);
                                break;
                            }
                        }
                    } else {
                        for (Artikel a : gui.sql.getDataArtikelInAngebot()) {
                            if (a.getArtikelnummer().equals(nummer)) {
                                for (Artikel b : dataNewRechnung) {
                                    if (a.getArtikelnummer().equals(b.getArtikelnummer())) {
                                        test = true;
                                        break;
                                    }
                                }
                                if (test == false) {
                                    dataNewRechnung.add(a);
                                }
                            }
                        }
                    }
                }
            }
        });

        VBox entwurfV = gui.createFilter(rechnungEntwurf, dataNewRechnung);

        Label artNr = new Label("Artikelnummer");
        Label anzahl = new Label("Anzahl");
        Label bezeichnung2 = new Label("Bezeichnung");
        Label nettopreis = new Label("Nettopreis");
        Label summe = new Label("Summe");
        Label rabatt2 = new Label("Rabatt in %");
        Label zusatztext = new Label("Zusatztext");

        CheckBox alternativ = new CheckBox("Alternativ");

        TextField artNrT = new TextField();
        TextField anzahlT = new TextField();
        TextField bezeichnungT = new TextField();
        TextField nettopreisT = new TextField();
        TextField summeT = new TextField();
        TextField rabattT = new TextField();
        TextField zusatztextT = new TextField();

        artNrT.setOnKeyReleased((KeyEvent ke) -> {
            if (summeT.getText() != null) {
                for (Artikel art : dataNewRechnung) {
                    if (tempOldId.equals(art.getArtikelnummer())) {
                        try {
                            gui.sql.updateArtikelNummer(art.getArtikelnummer(), artNrT.getText());
                            art.setArtikelnummer(artNrT.getText());
                            tempOldId = artNrT.getText();
                        } catch (SQLException exc) {
                            ConfirmBox.display2("Fehler", "Fehler beim aktualisieren der Artikelnummer");
                        }
                        rechnungEntwurf.refresh();
                        break;
                    }
                }
            }
        });
        alternativ.setOnMouseClicked((MouseEvent me) -> {
            for (Artikel art : dataNewRechnung) {
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
                for (Artikel art : dataNewRechnung) {
                    if (art.getArtikelnummer().equals(artNrT.getText())) {
                        try {
                            gui.sql.updateArtikelVerkaufsPreis(art.getArtikelnummer(), nettopreisT.getText());
                            art.setVerkaufspreis(nettopreisT.getText());
                            rechnungEntwurf.refresh();
                        } catch (SQLException exc) {
                            ConfirmBox.display2("Fehler", "Fehler beim aktualisieren des Artikels");
                            System.out.println(exc.getMessage());
                        }
                        if (art.getMengeTemp() != null) {
                            if (art.getRabattTemp() != null) {
                                summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getMengeTemp()) * (Double.valueOf(art.getRabattTemp()) / 100)));
                            } else {
                                summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getMengeTemp())));
                            }
                        } else {
                            if (art.getRabattTemp() != null) {
                                summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getBestand()) * (Double.valueOf(art.getRabattTemp()) / 100)));
                            } else {
                                summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getBestand())));
                            }
                        }
                    }
                }
            }
        });
        anzahlT.setOnKeyReleased((KeyEvent ke) -> {
            if (!artNrT.getText().isEmpty() && anzahlT.getText().matches("[0-9]")) {
                for (Artikel art : dataNewRechnung) {
                    if (art.getArtikelnummer().equals(artNrT.getText())) {
                        if (Integer.valueOf(anzahlT.getText()) <= Integer.valueOf(art.getBestand())) {
                            art.setMengeTemp(anzahlT.getText());
                            if (art.getRabattTemp() != null) {
                                summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getMengeTemp()) * (Double.valueOf(art.getRabattTemp()) / 100)));
                            } else {
                                summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getMengeTemp())));
                            }
                            rechnungEntwurf.refresh();
                            break;
                        }
                    }
                }
                System.out.println(dataNewRechnung.get(0).getMengeTemp() + "schauen");

            }
        });
        rabattT.setOnKeyReleased((KeyEvent ke) -> {
            if (!artNrT.getText().isEmpty() && !rabattT.getText().isEmpty() && rabattT.getText().matches("\\d*(\\.\\d*)?")) {
                for (Artikel art : dataNewRechnung) {
                    if (art.getArtikelnummer().equals(artNrT.getText())) {
                        art.setRabattTemp(rabattT.getText());
                        if (art.getMengeTemp() == null) {//wenn null dann gesamter Bestand
                            summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getBestand()) * (Double.valueOf(rabattT.getText()) / 100)));
                        } else {
                            summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getMengeTemp()) * (Double.valueOf(rabattT.getText()) / 100)));
                        }
                    }
                }
            } else {
                for (Artikel art : dataNewRechnung) {
                    if (art.getArtikelnummer().equals(artNrT.getText())) {
                        summeT.setText(String.valueOf(Double.valueOf(art.getVerkaufspreis()) * Double.valueOf(art.getBestand())));
                    }
                }
            }
        });
        rechnungEntwurf.setOnMouseClicked((MouseEvent me) -> {
            if (me.getClickCount() == 2) {
                double summeTemp;
                artNrT.setText(rechnungEntwurf.getSelectionModel().getSelectedItems().get(0).getArtikelnummer());
                tempOldId = artNrT.getText();
                bezeichnungT.setText(rechnungEntwurf.getSelectionModel().getSelectedItems().get(0).getBezeichnung());
                nettopreisT.setText(rechnungEntwurf.getSelectionModel().getSelectedItems().get(0).getVerkaufspreis());
                rabattT.setText(rechnungEntwurf.getSelectionModel().getSelectedItems().get(0).getRabattTemp());
                if (rabattT.getText() == null) {
                    summeTemp = Double.valueOf(nettopreisT.getText()) * Double.valueOf(rechnungEntwurf.getSelectionModel().getSelectedItems().get(0).getBestand());
                } else {
                    summeTemp = Double.valueOf(nettopreisT.getText()) * Double.valueOf(rechnungEntwurf.getSelectionModel().getSelectedItems().get(0).getBestand());
                    summeTemp = summeTemp * (Double.valueOf(rabattT.getText()) / 100);
                }
                summeT.setText(String.valueOf(summeTemp));
                anzahlT.setText(rechnungEntwurf.getSelectionModel().getSelectedItems().get(0).getMengeTemp());
                anzahlT.setPromptText("Aktueller Bestand: " + rechnungEntwurf.getSelectionModel().getSelectedItems().get(0).getBestand());
                zusatztextT.setText(rechnungEntwurf.getSelectionModel().getSelectedItems().get(0).getZusatztext());
                for (Artikel art : dataNewRechnung) {
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
        });
        Button search = new Button("Suchen");
        search.setOnAction(e -> {
            TablePopup.displayArtikel(gui, "Rechnung erstellen: Auswahl des Artikels", gui.artikelT);

            boolean test = false;
            try {
                gui.sql.loadDataArtikel();
            } catch (SQLException exc) {
                ConfirmBox.display2("Fehler", "Fehler beim Laden der Artikel");
                System.out.println(exc.getMessage());
            }
            for (Artikel oldA : gui.sql.getDataArtikel()) {
                if (oldA.getArtikelnummer().equals(gui.tempArtikel[0])) {
                    for (Artikel newA : dataNewRechnung) {
                        if (oldA.getArtikelnummer().equals(newA.getArtikelnummer())) {
                            test = true;
                            break;
                        }
                    }
                    if (test == false) {
                        dataNewRechnung.add(oldA);
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
            String item = rechnungEntwurf.getSelectionModel().getSelectedItems().get(0).getArtikelnummer();
            for (Artikel art : dataNewRechnung) {
                if (item != null) {
                    if (item.equals(art.getArtikelnummer())) {
                        dataNewRechnung.remove(art);
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
        TextField fakturatextT = new TextField();
        TextField skontotageT = new TextField();
        TextField skontoT = new TextField();
        TextField nettoT = new TextField();
        TextField skontobetragT = new TextField();
        skontobetragT.setEditable(false);

        gültigT.setOnKeyReleased((KeyEvent ke) -> {
            if (!ke.getText().matches("[0-9]*")) {
                StringBuilder sb = new StringBuilder(gültigT.getText());
                sb.deleteCharAt(gültigT.getText().length()-1);
                gültigT.setText(sb.toString());
                gültigT.positionCaret(gültigT.getText().length());
            }
        });
        
        skontotageT.setOnKeyReleased((KeyEvent ke) -> {
            if (!ke.getText().matches("[0-9]*")) {
                StringBuilder sb = new StringBuilder(skontotageT.getText());
                sb.deleteCharAt(skontotageT.getText().length()-1);
                skontotageT.setText(sb.toString());
                skontotageT.positionCaret(skontotageT.getText().length());
            }
        });
        
        skontoT.setOnKeyReleased((KeyEvent ke) -> {
            if (!skontoT.getText().trim().isEmpty() && skontoT.getText().matches("\\d*(\\.\\d*)?")) {
                skontobetragT.setText(String.valueOf(endpreisBrutto * (1 - (Double.valueOf(skontoT.getText()) / 100))));
            } else{
                if(!skontoT.getText().trim().isEmpty()){
                StringBuilder sb = new StringBuilder(skontoT.getText());
                sb.deleteCharAt(skontoT.getText().length()-1);
                skontoT.setText(sb.toString());
                skontoT.positionCaret(skontoT.getText().length());
                } else{
                    skontobetragT.clear();
                }
            }
        });
        Button con = new Button("Weiter");
        con.setOnAction(e -> {
            boolean test = false;
            for (Artikel aTest : dataNewRechnung) {
                if (aTest.getMengeTemp() == null) {
                    test = true;
                    break;
                }
            }
            if (test != true && !rechnungEntwurf.getItems().isEmpty()) {
                for (Artikel a : dataNewRechnung) {
                    if (a.getAlternative() != null) {
                        if (a.getAlternative().equals("0") || a.getAlternative().equals("false")) {
                            verkaufsPreis = Double.parseDouble(a.getVerkaufspreis());
                            m = Double.valueOf(a.getMengeTemp());
                            if (a.getRabattTemp() == null) {
                                gesamtpreis = verkaufsPreis * m;
                            } else { //wenn Rabatt besteht
                                gesamtpreis = verkaufsPreis * m * (Double.parseDouble(a.getRabattTemp()) / 100);
                            }
                        } else if (a.getAlternative().equals("1") || a.getAlternative().equals("true")) { //wenn Artikel alternativ ist
                            gesamtpreis = 0;
                            verkaufsPreis = Double.parseDouble(a.getVerkaufspreis());
                            m = Double.parseDouble(a.getMengeTemp());
                            if (a.getRabattTemp() == null) {
                                alternativpreis = verkaufsPreis * m;
                            } else { //wenn Rabatt besteht
                                alternativpreis = verkaufsPreis * m * (Double.parseDouble(a.getRabattTemp()) / 100);
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
                nettoT.setText(String.valueOf(endpreisNetto));

                popupStage.setScene(summen);
                popupStage.setTitle(titleS);
            }
        });
        VBox labelsLeft = new VBox();
        labelsLeft.getChildren().addAll(artNr, bezeichnung2, nettopreis, summe);
        labelsLeft.setPadding(new Insets(10));
        labelsLeft.setSpacing(16);

        VBox textLeft = new VBox();
        textLeft.getChildren().addAll(artNrT, bezeichnungT, nettopreisT, summeT);
        textLeft.setPadding(new Insets(10));
        textLeft.setSpacing(8);

        VBox labelsRight = new VBox();
        labelsRight.getChildren().addAll(alternativ, anzahl, rabatt2, zusatztext);
        labelsRight.setPadding(new Insets(10));
        labelsRight.setSpacing(16);

        VBox textRight = new VBox();
        textRight.getChildren().addAll(search, anzahlT, rabattT, zusatztextT);
        textRight.setPadding(new Insets(10));
        textRight.setSpacing(8);

        HBox leftRight = new HBox();
        leftRight.getChildren().addAll(labelsLeft, textLeft, labelsRight, textRight);

        HBox buttons3 = new HBox();
        buttons3.getChildren().addAll(back2, delete, con);
        buttons3.setPadding(new Insets(10, 10, 10, 10));
        buttons3.setSpacing(8);
        buttons3.setAlignment(Pos.CENTER);

        VBox tablePosten = new VBox();
        tablePosten.getChildren().addAll(entwurfV, leftRight);

        BorderPane pane3 = new BorderPane();
        pane3.setCenter(tablePosten);
        pane3.setBottom(buttons3);

        posten = new Scene(pane3, 850, 500); //ENDE POSTEN

        Label summe3 = new Label("Nettobetrag");
        Label mwtStr = new Label("Mehrwertsteuer");
        Label bruttopreis = new Label("Bruttobetrag");
        Label gültig = new Label("Zahlungsziel");
        Label fakturatext = new Label("Fakturatext");
        Label skontotage = new Label("Skontotage");
        Label skonto = new Label("Skonto in Prozent");
        Label netto = new Label("Netto");
        Label skontobetrag = new Label("Skontobetrag");

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
            boolean test = ConfirmBox.display("Rechnungserstellung abschließen", "Möchten sie die Rechnung wirklich erstellen?", 400, 100);
            if (test == true) {
                if (!skontoT.getText().trim().isEmpty() && !skontobetragT.getText().trim().isEmpty() && !gültigT.getText().trim().isEmpty() && !skontotageT.getText().trim().isEmpty()) {
                    try {
                        gui.sql.safeNewRechnung(rNRT.getText(), kNRT.getText(), dataNewRechnung, endpreisNetto, endpreisBrutto, mwstGesamt,
                                Double.valueOf(skontoT.getText()), Double.valueOf(skontobetragT.getText()), fakturatextT.getText(), zusatzT.getText(),
                                Integer.valueOf(gültigT.getText()), Integer.valueOf(skontotageT.getText()));
                        gui.sql.loadDataRechnung(false);
                    } catch (SQLException exc) {
                        ConfirmBox.display2("Fehler", "Fehler beim Erzeugen der neuen Rechnung.");
                        System.out.println(exc.getMessage());
                    }
                    popupStage.close();
                } else{
                    ConfirmBox.display2("Fehler", "Daten sind unvollständig. Bitte alle Textfelder ausfüllen");
                }
            } else {
                e.consume();
            }
        });

        pdfButton.setOnAction(e -> {
            boolean test = ConfirmBox.display("Rechnungserstellung abschließen", "Möchten sie das Angebot wirklich erstellen?", 400, 100);
            if (test == true) {
                if (!skontoT.getText().trim().isEmpty() && !skontobetragT.getText().trim().isEmpty() && !gültigT.getText().trim().isEmpty() && !skontotageT.getText().trim().isEmpty()) {
                    try {
                        gui.sql.safeNewRechnung(rNRT.getText(), kNRT.getText(), dataNewRechnung, endpreisNetto, endpreisBrutto, mwstGesamt,
                                Double.valueOf(skontoT.getText()), Double.valueOf(skontobetragT.getText()), fakturatextT.getText(), zusatzT.getText(),
                                Integer.valueOf(gültigT.getText()), Integer.valueOf(skontotageT.getText()));
                        gui.sql.loadDataRechnung(false);
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
                                        rNRT.getText(),
                                        zusatzT.getText(),
                                        Integer.valueOf(gültigT.getText()),
                                        Integer.valueOf(skontotageT.getText()),
                                        Double.valueOf(skontoT.getText()),
                                        fakturatextT.getText(),
                                        true,
                                        f);
                            } catch (DocumentException | IOException | SQLException exc) {
                                System.out.println(exc.getMessage());
                            }
                        }
                    } catch (SQLException exc) {
                        ConfirmBox.display2("Fehler", "Fehler beim erzeugen der neuen Rechnung.");
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

        VBox labels = new VBox();
        labels.getChildren().addAll(summe3, mwtStr, bruttopreis, gültig, fakturatext);
        labels.setPadding(new Insets(10));
        labels.setSpacing(16);
        VBox labelsSkonto = new VBox();
        labelsSkonto.getChildren().addAll(skontotage, skonto, netto, skontobetrag);
        labelsSkonto.setPadding(new Insets(10));
        labelsSkonto.setSpacing(16);

        VBox tLeft = new VBox();
        tLeft.getChildren().addAll(summe4, mwtStrT, bruttopreisT, gültigT, fakturatextT);
        tLeft.setPadding(new Insets(10));
        tLeft.setSpacing(8);
        VBox tRight = new VBox();
        tRight.getChildren().addAll(skontotageT, skontoT, nettoT, skontobetragT);
        tRight.setPadding(new Insets(10));
        tRight.setSpacing(8);

        HBox leftAndRight = new HBox();
        leftAndRight.getChildren().addAll(labels, tLeft, labelsSkonto, tRight);

        HBox buttons4 = new HBox();
        buttons4.getChildren().addAll(abort, pdfButton, done);
        buttons4.setPadding(new Insets(10, 10, 10, 10));
        buttons4.setSpacing(8);
        buttons4.setAlignment(Pos.CENTER);

        BorderPane pane4 = new BorderPane();
        pane4.setCenter(leftAndRight);
        pane4.setBottom(buttons4);

        summen = new Scene(pane4, 600, 250);

        popupStage.setScene(kundenInfo);
        popupStage.show();

    }
}
