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
public class RechnungDetails {

    static Scene details;
    static Kunde kunde = null;
    static Angebot rechnung = null;

    public static void display(ISql sql, String rNummer, String kNummer, String aDatum) {
        Stage popupStage = new Stage();

        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Rechnungsinformationen");

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
            rechnung = sql.loadDataAngebot(rNummer);
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Laden der Daten");
            System.out.println(exc.getMessage());
        }
        Label aNr = new Label("Rechnungsnummer");
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

        TextField anredeT = new TextField(kunde.getAnrede());
        anredeT.setPrefWidth(300);
        TextField aNRT = new TextField(rNummer);
        aNRT.setPrefWidth(300);
        TextField kNRT = new TextField(kNummer);
        kNRT.setPrefWidth(300);
        TextField vornameT = new TextField(kunde.getVorname());
        vornameT.setPrefWidth(300);
        TextField nameT = new TextField(kunde.getName());
        nameT.setPrefWidth(300);
        TextField straßeT = new TextField(kunde.getStraße());
        straßeT.setPrefWidth(300);
        TextField plzT = new TextField(kunde.getPlz());
        plzT.setPrefWidth(300);
        TextField ortT = new TextField(kunde.getOrt());
        ortT.setPrefWidth(300);
        Label datumL = new Label(aDatum);
        TextArea zusatzT = new TextArea(rechnung.getHinweis());
        zusatzT.setPrefWidth(300);

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
                try {
                    sql.deleteAngebot(rNummer);
                } catch (SQLException exc) {
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
                                rechnung.getZahlungsZiel(),
                                rechnung.getSkontoTage(),
                                rechnung.getSkontoProzent(),
                                rechnung.getFakturaText(),
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
        });

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
        laTe3.getChildren().addAll(kNr, kNRT);
        laTe4.getChildren().addAll(anredeL, anredeT);
        laTe5.getChildren().addAll(vorname, vornameT);
        laTe6.getChildren().addAll(name, nameT);
        laTe7.getChildren().addAll(straße, straßeT);
        laTe8.getChildren().addAll(plz, plzT);
        laTe9.getChildren().addAll(ort, ortT);
        laTe10.getChildren().addAll(zusatz, zusatzT);

        VBox sum = new VBox();
        sum.getChildren().addAll(laTe1, laTe2, laTe3, laTe4, laTe5, laTe6, laTe7, laTe8, laTe9, laTe10);

        HBox buttons = new HBox();
        buttons.getChildren().addAll(cancel, pdf);
        buttons.setPadding(new Insets(10, 10, 10, 10));
        buttons.setSpacing(8);
        buttons.setAlignment(Pos.CENTER);

        VBox sumsumButtons = new VBox();
        sumsumButtons.getChildren().addAll(sum, buttons);

        info.setContent(sumsumButtons); // TAB KUNDENINFORMATIONEN

        TableView artInRech = new TableView();
        artInRech.setPrefSize(100000, 100000);
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

        artInRech.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        try {
            sql.loadArtikelFromAngebot(rNummer);
            artInRech.setItems(sql.getDataArtikelInAngebot());
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Laden der Artikel");
        }
        artInRech.getColumns().addAll(artikelnummer, bezeichnung, zusatztext, warengruppe, einkaufspreis, verkaufspreis, mwst, menge, alternativT);
        artInRech.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button cancel2 = new Button("Abbrechen");
        cancel2.setOnAction(e -> popupStage.close());
        Button pdf2 = new Button("In PDF umwandeln");
        Button delete2 = new Button("Angebot löschen");
        delete2.setOnAction(e -> {
            boolean test = ConfirmBox.display("Rechnung löschen", "Möchten Sie die Rechnung wirklich löschen? Dieser Vorgang kann nicht rückgängig gemacht werden!", 600, 100);
            if (test == true) {
                try {
                    sql.deleteAngebot(rNummer);
                } catch (SQLException exc) {
                    ConfirmBox.display2("Fehler", "Fehler beim Löschen der Rechnung");
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
                                rechnung.getZahlungsZiel(),
                                rechnung.getSkontoTage(),
                                rechnung.getSkontoProzent(),
                                rechnung.getFakturaText(),
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
        });

        HBox buttons2 = new HBox();
        buttons2.getChildren().addAll(cancel2, pdf2);
        buttons2.setPadding(new Insets(10, 10, 10, 10));
        buttons2.setSpacing(8);
        buttons2.setAlignment(Pos.CENTER);

        VBox tableButtons = new VBox();
        tableButtons.getChildren().addAll(artInRech, buttons2);

        posten.setContent(tableButtons); // TAB POSTEN ENDE

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
        Label netto = new Label("Netto");
        netto.setPrefWidth(100);
        Label skontobetrag = new Label("Skontobetrag");
        skontobetrag.setPrefWidth(100);

        TextField summe4 = new TextField(String.valueOf(rechnung.getNettoBetrag()));
        summe4.setEditable(false);
        TextField mwtStrT = new TextField(String.valueOf(rechnung.getMwSt()));
        mwtStrT.setEditable(false);
        TextField bruttopreisT = new TextField(String.valueOf(rechnung.getBruttoBetrag()));
        bruttopreisT.setEditable(false);
        TextField gültigT = new TextField(String.valueOf(rechnung.getZahlungsZiel()));
        TextArea fakturatextT = new TextArea(rechnung.getFakturaText());
        fakturatextT.setEditable(false);
        TextField skontotageT = new TextField(String.valueOf(rechnung.getSkontoTage()));
        skontotageT.setEditable(false);
        TextField skontoT = new TextField(String.valueOf(rechnung.getSkontoProzent()));
        skontoT.setEditable(false);
        TextField skontobetragT = new TextField(String.valueOf(rechnung.getSkontoBetrag()));
        skontobetragT.setEditable(false);

        Button cancel3 = new Button("Abbrechen");
        cancel3.setOnAction(e -> popupStage.close());
        Button pdf3 = new Button("In PDF umwandeln");
        Button delete3 = new Button("Angebot löschen");
        delete3.setOnAction(e -> {
            boolean test = ConfirmBox.display("Rechnung löschen", "Möchten Sie die Rechnung wirklich löschen? Dieser Vorgang kann nicht rückgängig gemacht werden!", 600, 100);
            if (test == true) {
                try {
                    sql.deleteAngebot(rNummer);
                } catch (SQLException exc) {
                    ConfirmBox.display2("Fehler", "Fehler beim Löschen der Rechnung");
                }
                popupStage.close();
            } else {
                e.consume();
            }
        });

        HBox buttons3 = new HBox();
        buttons3.getChildren().addAll(cancel3, pdf3);
        buttons3.setPadding(new Insets(10, 10, 10, 10));
        buttons3.setSpacing(8);
        buttons3.setAlignment(Pos.CENTER);

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

        VBox all = new VBox();
        all.getChildren().addAll(sum3, buttons3);

        summen.setContent(all);

        details = new Scene(tabs, 800, 600);
        popupStage.setScene(details);
        popupStage.show();
    }
}
