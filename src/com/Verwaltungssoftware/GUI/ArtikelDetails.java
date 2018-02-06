/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verwaltungssoftware.GUI;

import com.verwaltungssoftware.database.ISql;
import com.verwaltungssoftware.objects.Artikel;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Lucas
 */
public class ArtikelDetails {

    static Scene details;
    static Artikel artikel = null;

    public static void display(String artNummer, ISql sql) {
        Stage popupStage = new Stage();

        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Artikelinformationen");

        Label name = new Label("Bezeichnung");
        Label ztext = new Label("Zusatztext");
        Label artNr = new Label("Artikelnummer");
        Label gruppeL = new Label("Warengruppe");
        Label preis = new Label("Einkaufspreis");
        Label preisV = new Label("Verkaufspreis");
        Label bestand = new Label("Bestand");
        Label mehrwertSt = new Label("Mehrwertsteuer in %");

        ChoiceBox gruppe = new ChoiceBox();
        try {
            artikel = sql.loadDataArtikel(artNummer);
            sql.loadDataWarengruppe();
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Laden der Warengruppen");
        }
        for (String s : sql.getDataWarengruppe()) {
            gruppe.getItems().add(s);
        }

        TextField nameT = new TextField(artikel.getBezeichnung());
        nameT.setMaxWidth(1000);
        TextArea ztextT = new TextArea(artikel.getZusatztext());
        ztextT.setMaxWidth(1000);
        TextField artNrT = new TextField(artikel.getArtikelnummer());
        artNrT.setMaxWidth(1000);
        TextField preisET = new TextField(artikel.getEinkaufspreis());
        preisET.setMaxWidth(1000);
        TextField preisVT = new TextField(artikel.getVerkaufspreis());
        preisVT.setMaxWidth(1000);
        TextField bestandT = new TextField(artikel.getBestand());
        bestandT.setMaxWidth(1000);
        TextField mwstT = new TextField(artikel.getMwst());
        mwstT.setMaxWidth(1000);

        preisET.setOnKeyReleased(e -> {
           if(!preisET.getText().matches("\\d*(\\.\\d*)?")){
               if(!preisET.getText().trim().isEmpty()){
                StringBuilder sb = new StringBuilder(preisET.getText());
                sb.deleteCharAt(preisET.getText().length()-1);
                preisET.setText(sb.toString());
                preisET.positionCaret(preisET.getText().length());
                }
           } 
        });
        preisVT.setOnKeyReleased(e -> {
           if(!preisVT.getText().matches("\\d*(\\.\\d*)?")){
               if(!preisVT.getText().trim().isEmpty()){
                StringBuilder sb = new StringBuilder(preisVT.getText());
                sb.deleteCharAt(preisVT.getText().length()-1);
                preisVT.setText(sb.toString());
                preisVT.positionCaret(preisVT.getText().length());
                }
           } 
        });
        bestandT.setOnKeyReleased(e -> {
           if(!bestandT.getText().matches("[0-9]*")){
               if(!bestandT.getText().trim().isEmpty()){
                StringBuilder sb = new StringBuilder(bestandT.getText());
                sb.deleteCharAt(bestandT.getText().length()-1);
                bestandT.setText(sb.toString());
                bestandT.positionCaret(bestandT.getText().length());
                }
           } 
        });
        
        Button cancel = new Button("Abbrechen");
        cancel.setOnAction(e -> popupStage.close());
        Button confirm = new Button("Bestätigen");
        confirm.setOnAction(e -> {
            try {
                if (!gruppe.getSelectionModel().isEmpty()) {
                    sql.updateArtikel(artNummer, artNrT.getText(), nameT.getText(), ztextT.getText(),
                            preisET.getText(), preisVT.getText(), mwstT.getText(),
                            bestandT.getText(), gruppe.getSelectionModel().getSelectedItem().toString());
                } else{
                    sql.updateArtikel(artNummer, artNrT.getText(), nameT.getText(), ztextT.getText(),
                            preisET.getText(), preisVT.getText(), mwstT.getText(),
                            bestandT.getText(), null);
                }
                sql.loadDataArtikel();
            } catch (SQLException exc) {
                ConfirmBox.display2("Fehler", "Fehler beim Speichern der Artikeldetails");
                System.out.println(exc.getMessage());
            }
            popupStage.close();
        });
        Button delete = new Button("Artikel löschen");
        delete.setOnAction(e -> {
            boolean test = ConfirmBox.display("Artikel löschen", "Möchten sie den Artikel wirklich löschen? Dieser Vorgang kann nicht rückgängg gemacht werden!", 600, 100);
            if (test == true) {
                popupStage.close();
            } else {
                e.consume();
            }
        });

        VBox left = new VBox();
        VBox right = new VBox();
        left.setPadding(new Insets(10));
        left.setSpacing(16);
        right.setPadding(new Insets(10));
        right.setSpacing(8);

        left.getChildren().addAll(name, artNr, gruppeL, preis, preisV, bestand, mehrwertSt, ztext);
        right.getChildren().addAll(nameT, artNrT, gruppe, preisET, preisVT, bestandT, mwstT, ztextT);

        HBox bottom = new HBox();
        bottom.getChildren().addAll(cancel, delete, confirm);
        bottom.setPadding(new Insets(10, 10, 10, 10));
        bottom.setSpacing(8);
        bottom.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setLeft(left);
        pane.setCenter(right);
        pane.setBottom(bottom);

        details = new Scene(pane, 450, 450);
        popupStage.setScene(details);
        popupStage.show();
    }
}
