/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verwaltungssoftware.GUI;

import com.itextpdf.text.DocumentException;
import com.verwaltungssoftware.database.ISql;
import com.verwaltungssoftware.objects.Angebot;
import com.verwaltungssoftware.objects.Kunde;
import com.verwaltungssoftware.objects.User;
import com.verwaltungssoftware.pdf.PdfCreator;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Lucas
 */
public class AngebotDetails {

    static Scene details;
    static Kunde kunde = null;
    static Angebot angebot = null;

    public static void display(ISql sql, String aNummer, String kNummer, String aDatum) {
        Stage popupStage = new Stage();

        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Angebotsinformationen");

        TabPane tabs = new TabPane();

        Tab info = new Tab("Kundeninformationen");
        Tab posten = new Tab("Posten");
        Tab summen = new Tab("Summen");
        info.setClosable(false);
        posten.setClosable(false);
        summen.setClosable(false);
        tabs.getTabs().addAll(info, posten, summen);

        try {
            kunde = sql.loadDataKunde(kNummer);
            angebot = sql.loadDataAngebot(aNummer);
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Laden der Daten");
            System.out.println(exc.getMessage());
        }
        Label aNr = new Label("Angebotsnummer");
        Label kNr = new Label("Kundennummer");
        Label anredeL = new Label("Anrede");
        Label vorname = new Label("Vorname");
        Label name = new Label("Nachname");
        Label straße = new Label("Straße");
        Label plz = new Label("PLZ");
        Label ort = new Label("Ort");
        Label datum = new Label("Datum");
        Label zusatz = new Label("Hinweis");

        TextField anredeT = new TextField(kunde.getAnrede());
        TextField aNRT = new TextField(aNummer);
        TextField kNRT = new TextField(kNummer);
        TextField vornameT = new TextField(kunde.getVorname());
        TextField nameT = new TextField(kunde.getName());
        TextField straßeT = new TextField(kunde.getStraße());
        TextField plzT = new TextField(kunde.getPlz());
        TextField ortT = new TextField(kunde.getOrt());
        Label datumL = new Label(aDatum);
        TextArea zusatzT = new TextArea(angebot.getHinweis());

        anredeT.setEditable(false);
        aNRT.setEditable(false);
        kNRT.setEditable(false);
        vornameT.setEditable(false);
        nameT.setEditable(false);
        straßeT.setEditable(false);
        plzT.setEditable(false);
        ortT.setEditable(false);

        Button cancel = new Button("Abbrechen");
        cancel.setOnAction(e -> popupStage.close());
        Button pdf = new Button("In PDF umwandeln");
        Button delete = new Button("Angebot löschen");
        delete.setOnAction(e -> {
            boolean test = ConfirmBox.display("Angebot löschen", "Möchten Sie das Angebot wirklich löschen? Dieser Vorgang kann nicht rückgängig gemacht werden!", 600, 100);
            if (test == true) {
                try{
                    sql.deleteAngebot(aNummer);
                } catch(SQLException exc){
                    ConfirmBox.display2("Fehler", "Fehler beim Löschen des Angebots");
                }
                popupStage.close();
            } else {
                e.consume();
            }
        });

        pdf.setOnAction(e -> {
            try {
                User user = sql.loadUser();
                PdfCreator pdfC = new PdfCreator(user, sql);
                FileChooser fc = new FileChooser();
                fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF (*.pdf)", "*pdf"));
                File f = fc.showSaveDialog(new Stage());
                System.out.println(f);
                if (f != null && !f.getName().contains(".")) {
                    f = new File(f.getAbsolutePath() + ".pdf");
                }
                if (f != null) {
                    try {
                        pdfC.createDocument(kNRT.getText(),
                                aNRT.getText(),
                                zusatzT.getText(),
                                angebot.getZahlungsZiel(),
                                angebot.getSkontoTage(),
                                angebot.getSkontoProzent(),
                                angebot.getFakturaText(),
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
        });

        Button confirm = new Button("Bestätigen");
        confirm.setOnAction(e -> popupStage.close());

        VBox sumL = new VBox();
        sumL.getChildren().addAll(aNr, datum, kNr, anredeL, vorname, name, straße, plz, ort, zusatz);
        sumL.setPadding(new Insets(10));
        sumL.setSpacing(16);

        VBox sumT = new VBox();
        sumT.getChildren().addAll(aNRT, datumL, kNRT, anredeT, vornameT, nameT, straßeT, plzT, ortT, zusatzT);
        sumT.setPadding(new Insets(10));
        sumT.setSpacing(8);

        HBox buttons = new HBox();
        buttons.getChildren().addAll(cancel, pdf, delete, confirm);
        buttons.setPadding(new Insets(10, 10, 10, 10));
        buttons.setSpacing(8);
        buttons.setAlignment(Pos.CENTER);

        HBox sumsum = new HBox();
        sumsum.getChildren().addAll(sumL, sumT);
        VBox sumsumButtons = new VBox();
        sumsumButtons.getChildren().addAll(sumsum, buttons);

        info.setContent(sumsumButtons); // TAB KUNDENINFORMATIONEN

        TableView artInAng = new TableView();
        artInAng.setPrefSize(100000, 100000);
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

        TableColumn menge = new TableColumn("Menge");
        menge.setCellValueFactory(
                new PropertyValueFactory<>("bestand"));

        TableColumn alternativT = new TableColumn("Alternative");
        alternativT.setCellValueFactory(
                new PropertyValueFactory<>("alternativtext"));

        artInAng.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        try {
            sql.loadArtikelFromAngebot(aNummer);
            artInAng.setItems(sql.getDataArtikelInAngebot());
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Laden der Artikel");
        }
        artInAng.getColumns().addAll(artikelnummer, bezeichnung, zusatztext, warengruppe, einkaufspreis, verkaufspreis, mwst, menge, alternativT);
        artInAng.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button cancel2 = new Button("Abbrechen");
        cancel2.setOnAction(e -> popupStage.close());
        Button pdf2 = new Button("In PDF umwandeln");
        Button delete2 = new Button("Angebot löschen");
        delete2.setOnAction(e -> {
            boolean test = ConfirmBox.display("Agebot löschen", "Möchten Sie das Angebot wirklich löschen? Dieser Vorgang kann nicht rückgängig gemacht werden!", 600, 100);
            if (test == true) {
                try{
                    sql.deleteAngebot(aNummer);
                } catch(SQLException exc){
                    ConfirmBox.display2("Fehler", "Fehler beim Löschen des Angebots");
                }
                popupStage.close();
            } else {
                e.consume();
            }
        });

        pdf2.setOnAction(e -> {
            try {
                User user = sql.loadUser();
                PdfCreator pdfC = new PdfCreator(user, sql);
                FileChooser fc = new FileChooser();
                fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF (*.pdf)", "*pdf"));
                File f = fc.showSaveDialog(new Stage());
                System.out.println(f);
                if (f != null && !f.getName().contains(".")) {
                    f = new File(f.getAbsolutePath() + ".pdf");
                }
                if (f != null) {
                    try {
                        pdfC.createDocument(kNRT.getText(),
                                aNRT.getText(),
                                zusatzT.getText(),
                                angebot.getZahlungsZiel(),
                                angebot.getSkontoTage(),
                                angebot.getSkontoProzent(),
                                angebot.getFakturaText(),
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
        });
        Button confirm2 = new Button("Bestätigen");
        confirm2.setOnAction(e -> popupStage.close());

        HBox buttons2 = new HBox();
        buttons2.getChildren().addAll(cancel2, pdf2, delete2, confirm2);
        buttons2.setPadding(new Insets(10, 10, 10, 10));
        buttons2.setSpacing(8);
        buttons2.setAlignment(Pos.CENTER);

        VBox tableButtons = new VBox();
        tableButtons.getChildren().addAll(artInAng, buttons2);

        posten.setContent(tableButtons); // TAB POSTEN ENDE

        Label summe3 = new Label("Nettobetrag");
        Label mwtStr = new Label("Mehrwertsteuer");
        Label bruttopreis = new Label("Bruttobetrag");
        Label gültig = new Label("Zahlungsziel");
        Label fakturatext = new Label("Fakturatext");
        Label skontotage = new Label("Skontotage");
        Label skonto = new Label("Skonto in Prozent");
        Label skontobetrag = new Label("Skontobetrag");

        TextField summe4 = new TextField(String.valueOf(angebot.getNettoBetrag()));
        summe4.setEditable(false);
        TextField mwtStrT = new TextField(String.valueOf(angebot.getMwSt()));
        mwtStrT.setEditable(false);
        TextField bruttopreisT = new TextField(String.valueOf(angebot.getBruttoBetrag()));
        bruttopreisT.setEditable(false);
        TextField gültigT = new TextField(String.valueOf(angebot.getZahlungsZiel()));
        TextField fakturatextT = new TextField(angebot.getFakturaText());
        TextField skontotageT = new TextField(String.valueOf(angebot.getSkontoTage()));
        TextField skontoT = new TextField(String.valueOf(angebot.getSkontoProzent()));
        TextField skontobetragT = new TextField(String.valueOf(angebot.getSkontoBetrag()));
        skontobetragT.setEditable(false);

        Button cancel3 = new Button("Abbrechen");
        cancel3.setOnAction(e -> popupStage.close());
        Button pdf3 = new Button("In PDF umwandeln");
        Button delete3 = new Button("Angebot löschen");
        delete3.setOnAction(e -> {
            boolean test = ConfirmBox.display("Agebot löschen", "Möchten Sie das Angebot wirklich löschen? Dieser Vorgang kann nicht rückgängig gemacht werden!", 600, 100);
            if (test == true) {
                try{
                    sql.deleteAngebot(aNummer);
                } catch(SQLException exc){
                    ConfirmBox.display2("Fehler", "Fehler beim Löschen des Angebots");
                }
                popupStage.close();
            } else {
                e.consume();
            }
        });
        Button confirm3 = new Button("Bestätigen");
        confirm3.setOnAction(e -> popupStage.close());

        HBox buttons3 = new HBox();
        buttons3.getChildren().addAll(cancel3, pdf3, delete3, confirm3);
        buttons3.setPadding(new Insets(10, 10, 10, 10));
        buttons3.setSpacing(8);
        buttons3.setAlignment(Pos.CENTER);

        VBox labels = new VBox();
        labels.setPadding(new Insets(10));
        labels.setSpacing(16);
        labels.getChildren().addAll(summe3, mwtStr, bruttopreis, gültig, fakturatext, skontotage, skonto, skontobetrag);

        VBox text = new VBox();
        text.getChildren().addAll(summe4, mwtStrT, bruttopreisT, gültigT, fakturatextT, skontotageT, skontoT, skontobetragT);
        text.setPadding(new Insets(10));
        text.setSpacing(8);

        HBox labelsText = new HBox();
        labelsText.getChildren().addAll(labels, text);
        VBox all = new VBox();
        all.getChildren().addAll(labelsText, buttons3);

        summen.setContent(all);

        details = new Scene(tabs, 800, 450);
        popupStage.setScene(details);
        popupStage.show();
    }
}
