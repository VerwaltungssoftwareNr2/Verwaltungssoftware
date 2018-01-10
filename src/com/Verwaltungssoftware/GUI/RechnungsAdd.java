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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Lucas
 */
public class RechnungsAdd {

    static Scene kundenInfo, übernahme, posten, summen;

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
            System.out.println("Fehler beim laden der Kunden: " + exc.getMessage());
        }
        try {
            gui.sql.loadDataArtikel();
        } catch (SQLException exc) {
            System.out.println("Fehler beim laden der Artikel: " + exc.getMessage());
        }
        try {
            gui.sql.loadDataRechnung(true);
        } catch (SQLException exc) {
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
        Label rabatt = new Label("Rabatt");
        Label datum = new Label("Datum");

        TextField anredeT = new TextField();
        TextField aNRT = new TextField();
        TextField kNRT = new TextField();
        TextField vornameT = new TextField();
        TextField nameT = new TextField();
        TextField straßeT = new TextField();
        TextField plzT = new TextField();
        TextField ortT = new TextField();
        TextField rabattT = new TextField();
        DateTimeFormatter dateTf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate ld = LocalDate.now();
        Label datumL = new Label(ld.format(dateTf));

        aNRT.setEditable(false);
        vornameT.setEditable(false);
        nameT.setEditable(false);
        straßeT.setEditable(false);
        plzT.setEditable(false);
        ortT.setEditable(false);
        rabattT.setEditable(false);

        Button search2 = new Button("Suchen");
        search2.setOnAction(e -> {
            TablePopup.display(gui, "Rechnung erstellen: Auswahl des Kunden", gui.kundenT);
            try {
                aNRT.setText(gui.sql.generateRandomBillNumber(ld.toString()));
            } catch (SQLException exc) {
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
        VBox searchV = new VBox();
        searchV.getChildren().addAll(space, search2);
        searchV.setPadding(new Insets(10));
        searchV.setSpacing(8);

        Button cancel = new Button("Abbrechen");
        cancel.setOnAction(e -> popupStage.close());
        Button cont = new Button("Weiter");
        cont.setOnAction(e -> {
            popupStage.setScene(übernahme);
            popupStage.setTitle(titleÜ);
        });

        VBox sumL = new VBox();
        sumL.getChildren().addAll(aNr, kNr, anredeL, vorname, name, straße, plz, ort, rabatt, datum);
        sumL.setPadding(new Insets(10));
        sumL.setSpacing(16);

        VBox sumT = new VBox();
        sumT.getChildren().addAll(aNRT, kNRT, anredeT, vornameT, nameT, straßeT, plzT, ortT, rabattT, datumL);
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
        // pane.setLeft(sumL);
        pane.setCenter(sumsum);
        pane.setBottom(buttons);
        kundenInfo = new Scene(pane, 500, 450); //KUNDENINFO ENDE
        
        TableView<Angebot> aAndR = new TableView();
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

        ScrollPane scroll = new ScrollPane();
        scroll.setContent(aAndR);
        scroll.setPrefSize(320, 180);
        ScrollPane scroll2 = new ScrollPane();
        scroll2.setContent(aFromAR);
        scroll2.setPrefSize(320, 180);

        VBox aAndRV = gui.createFilter(aAndR, "Rechnung", true);
        VBox aFromARV = gui.createFilter(aFromAR, gui.sql.getDataArtikelInAngebot());

        HBox t1 = new HBox();
        t1.setPadding(new Insets(10));
        t1.setSpacing(8);
        HBox t2 = new HBox();
        t2.setPadding(new Insets(10));
        t2.setSpacing(8);
        t1.getChildren().addAll(aAndRV, add);
        t2.getChildren().addAll(aFromARV, add2);

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

        übernahme = new Scene(pane2, 500, 500); //ÜBERNAHME ENDE

        ObservableList<Artikel> dataNewRechnung = FXCollections.observableArrayList();
        TableView<Artikel> rechnungEntwurf = new TableView();
        TableColumn billNumEntwurf = new TableColumn("Artikelnummer");
        billNumEntwurf.setCellValueFactory(
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
                new PropertyValueFactory<>("bestand"));

        TableColumn datumEntwurf = new TableColumn("Datum");
        datumEntwurf.setCellValueFactory(
                new PropertyValueFactory<>("datum"));
        rechnungEntwurf.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        rechnungEntwurf.setItems(dataNewRechnung);
        rechnungEntwurf.getColumns().addAll(billNumEntwurf, bezEntwurf, zusatzEntwurf, ePreisEntwurf, verPreisEntwurf, mwstEntwurf, mengeEntwurf, datumEntwurf);

        add.setOnAction((ActionEvent) -> {
            dataNewRechnung.clear();
            for (Artikel a : gui.sql.getDataArtikelInAngebot()) {
                dataNewRechnung.add(a);
            }
        });

        add2.setOnAction((ActionEvent) -> {
            boolean test = false;
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
        });

        ScrollPane entwurfScroll = new ScrollPane();
        entwurfScroll.setContent(rechnungEntwurf);

        VBox entwurfScrollV = gui.createFilter(rechnungEntwurf, dataNewRechnung);

        Label artNr = new Label("Artikelnummer");
        Label anzahl = new Label("Anzahl");
        Label bezeichnungL = new Label("Bezeichnung");
        Label nettopreis = new Label("Nettopreis");
        Label summe = new Label("Summe");
        Label rabatt2 = new Label("Rabatt in %");
        Label zusatztext = new Label("Zusatztext");

        CheckBox alternativ = new CheckBox("Alternativ");

        TextField artNr2 = new TextField();
        TextField anzahl2 = new TextField();
        TextField bezeichnung2 = new TextField();
        TextField nettopreis2 = new TextField();
        TextField summe2 = new TextField();
        TextField rabatt3 = new TextField();
        TextField zusatztext2 = new TextField();

        Button search = new Button("Suchen");
        search.setOnAction(e -> {
            TablePopup.displayArtikel(gui, "Rechnung erstellen: Auswahl des Artikels", gui.artikelT);
            artNr2.setText(gui.tempArtikel[0]);
            bezeichnung2.setText(gui.tempArtikel[1]);
            nettopreis2.setText(gui.tempArtikel[2]);
            anzahl2.setPromptText("Aktueller Bestand: " + gui.tempArtikel[3]);
            zusatztext2.setText(gui.tempArtikel[4]);
        });
        Button back2 = new Button("Zurück");
        back2.setOnAction(e -> {
            popupStage.setScene(übernahme);
            popupStage.setTitle(titleÜ);
        });
        Button add3 = new Button("Hinzufügen");
        add3.setOnAction(e -> {
            boolean test = false;
                try {
                    gui.sql.loadDataArtikel();
                } catch (SQLException exc) {
                    System.out.println(exc.getMessage());
                }
                for (Artikel oldR : gui.sql.getDataArtikel()) {
                    if (oldR.getArtikelnummer().equals(artNr2.getText())) {
                        for (Artikel newR : dataNewRechnung) {
                            if(oldR.getArtikelnummer().equals(newR.getArtikelnummer())){
                                test = true;
                                break;
                            }
                        }
                        if (test == false) {
                            dataNewRechnung.add(oldR);
                        }
                    }
                }
        });
        Button con = new Button("Weiter");
        con.setOnAction(e -> {
            popupStage.setScene(summen);
            popupStage.setTitle(titleS);
        });

        VBox labelsLeft = new VBox();
        labelsLeft.getChildren().addAll(artNr, bezeichnungL, nettopreis, summe);
        labelsLeft.setPadding(new Insets(10));
        labelsLeft.setSpacing(16);

        VBox textLeft = new VBox();
        textLeft.getChildren().addAll(artNr2, bezeichnung2, nettopreis2, summe2);
        textLeft.setPadding(new Insets(10));
        textLeft.setSpacing(8);

        VBox labelsRight = new VBox();
        labelsRight.getChildren().addAll(alternativ, anzahl, rabatt2, zusatztext);
        labelsRight.setPadding(new Insets(10));
        labelsRight.setSpacing(16);

        VBox textRight = new VBox();
        textRight.getChildren().addAll(search, anzahl2, rabatt3, zusatztext2);
        textRight.setPadding(new Insets(10));
        textRight.setSpacing(8);

        HBox leftRight = new HBox();
        leftRight.getChildren().addAll(labelsLeft, textLeft, labelsRight, textRight);

        HBox buttons3 = new HBox();
        buttons3.getChildren().addAll(back2, add3, con);
        buttons3.setPadding(new Insets(10, 10, 10, 10));
        buttons3.setSpacing(8);
        buttons3.setAlignment(Pos.CENTER);

        VBox tablePosten = new VBox();
        tablePosten.getChildren().addAll(entwurfScrollV, leftRight);

        BorderPane pane3 = new BorderPane();
        pane3.setCenter(tablePosten);
        pane3.setBottom(buttons3);

        posten = new Scene(pane3, 700, 500); //ENDE POSTEN

        Label summe3 = new Label("Summe");
        Label mwtStr = new Label("Mehrwertsteuer");
        Label bruttopreis = new Label("Bruttopreis");
        Label gültig = new Label("Gültig bis");
        Label fakturatext = new Label("Fakturatext");
        Label skontotage = new Label("Skontotage");
        Label skonto = new Label("Skonto in Prozent");
        Label netto = new Label("Netto");
        Label skontobetrag = new Label("Skontobetrag");

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

        Button abort = new Button("Zurück");
        abort.setOnAction(e -> {
            popupStage.setScene(posten);
            popupStage.setTitle(titleP);
        });
        Button pdf = new Button("In PDF-Datei umwandeln");
        Button done = new Button("Abschließen");
        done.setOnAction(e -> {
            boolean test = ConfirmBox.display("Rechnungserstellung abschließen", "Möchten sie die Rechnung wirklich erstellen?");
            if (test == true) {
                popupStage.close();
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
        buttons4.getChildren().addAll(abort, pdf, done);
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