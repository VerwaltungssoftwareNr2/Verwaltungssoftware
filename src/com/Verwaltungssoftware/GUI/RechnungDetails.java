/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verwaltungssoftware.GUI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Lucas
 */
public class RechnungDetails {
        static Scene details;
    
    public static void display() {
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
        
        Label aNr = new Label("Rechnungsnummer");
        Label kNr = new Label("Kundennummer");
        Label anredeL = new Label("Anrede");
        Label vorname = new Label("Vorname");
        Label name = new Label("Nachname");
        Label straße = new Label("Straße");
        Label plz = new Label("PLZ");
        Label ort = new Label("Ort");
        Label datum = new Label("Datum");
        Label zusatz = new Label("Zusatztext");

        TextField anredeT = new TextField();
        TextField aNRT = new TextField();
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

        aNRT.setEditable(false);
        vornameT.setEditable(false);
        nameT.setEditable(false);
        straßeT.setEditable(false);
        plzT.setEditable(false);
        ortT.setEditable(false);
        
        Button cancel = new Button("Abbrechen");
        cancel.setOnAction(e -> popupStage.close());
        Button pdf = new Button("In PDF umwandeln");
        Button delete = new Button("Rechnung löschen");
        delete.setOnAction(e -> {
            boolean test = com.verwaltungssoftware.GUI.ConfirmBox.display("Rechnung löschen", "Möchten Sie die Rechnung wirklich löschen? Dieser Vorgang kann nicht rückgängig gemacht werden!", 600, 100);
            if (test == true) {
                popupStage.close();
            } else {
                e.consume();
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
        artInAng.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        

        
        Button cancel2 = new Button("Abbrechen");
        cancel2.setOnAction(e -> popupStage.close());
        Button pdf2 = new Button("In PDF umwandeln");
        Button delete2 = new Button("Rechnung löschen");
        delete2.setOnAction(e -> {
            boolean test = com.verwaltungssoftware.GUI.ConfirmBox.display("Rechnung löschen", "Möchten Sie die Rechnung wirklich löschen? Dieser Vorgang kann nicht rückgängig gemacht werden!", 600, 100);
            if (test == true) {
                popupStage.close();
            } else {
                e.consume();
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

        Button cancel3 = new Button("Abbrechen");
        cancel3.setOnAction(e -> popupStage.close());
        Button pdf3 = new Button("In PDF umwandeln");
        Button delete3 = new Button("Rechnung löschen");
        delete3.setOnAction(e -> {
            boolean test = com.verwaltungssoftware.GUI.ConfirmBox.display("Rechnung löschen", "Möchten Sie die Rechnung wirklich löschen? Dieser Vorgang kann nicht rückgängig gemacht werden!", 600, 100);
            if (test == true) {
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
        labels.getChildren().addAll(summe3, mwtStr, bruttopreis, gültig, fakturatext,skontotage, skonto, netto, skontobetrag);
        
        VBox text = new VBox();
        text.getChildren().addAll(summe4, mwtStrT, bruttopreisT, gültigT, fakturatextT, skontotageT, skontoT, nettoT, skontobetragT);
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
