/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verwaltungssoftware.GUI;

import com.verwaltungssoftware.database.ISql;
import com.verwaltungssoftware.objects.Artikel;
import java.sql.SQLException;
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
        name.setPrefWidth(200);
        Label ztext = new Label("Zusatztext");
        ztext.setPrefWidth(200);
        Label artNr = new Label("Artikelnummer");
        artNr.setPrefWidth(200);
        Label gruppeL = new Label("Warengruppe");
        gruppeL.setPrefWidth(200);
        Label preis = new Label("Einkaufspreis");
        preis.setPrefWidth(200);
        Label preisV = new Label("Verkaufspreis");
        preisV.setPrefWidth(200);
        Label bestand = new Label("Bestand");
        bestand.setPrefWidth(200);
        Label mehrwertSt = new Label("Mehrwertsteuer in %");
        mehrwertSt.setPrefWidth(200);

        ChoiceBox gruppe = new ChoiceBox();
        gruppe.setPrefWidth(300);
        try {
            artikel = sql.loadDataArtikel(artNummer);
            sql.loadDataWarengruppe();
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Laden der Warengruppen");
        }
        boolean testArtikel = false;
        try {
            testArtikel = sql.checkArtikelInAngebot(artNummer);
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Überprüfen der Daten");
            System.out.println(exc.getMessage());
        }
        
        for (String s : sql.getDataWarengruppe()) {
            gruppe.getItems().add(s);
        }

        TextField nameT = new TextField(artikel.getBezeichnung());
        nameT.setPrefWidth(300);
        nameT.setMaxWidth(1000);
        TextArea ztextT = new TextArea(artikel.getZusatztext());
        ztextT.setPrefWidth(300);
        ztextT.setMaxWidth(1000);
        TextField artNrT = new TextField(artikel.getArtikelnummer());
        artNrT.setPrefWidth(300);
        artNrT.setMaxWidth(1000);
        TextField preisET = new TextField(artikel.getEinkaufspreis());
        preisET.setPrefWidth(300);
        preisET.setMaxWidth(1000);
        TextField preisVT = new TextField(artikel.getVerkaufspreis());
        preisVT.setPrefWidth(300);
        preisVT.setMaxWidth(1000);
        TextField bestandT = new TextField(artikel.getBestand());
        bestandT.setPrefWidth(300);
        bestandT.setMaxWidth(1000);
        TextField mwstT = new TextField(artikel.getMwst());
        mwstT.setPrefWidth(300);
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
        if(testArtikel){
            delete.setDisable(true);
        }
        delete.setOnAction(e -> {
            boolean test = ConfirmBox.display("Artikel löschen", "Möchten sie den Artikel wirklich löschen? Dieser Vorgang kann nicht rückgängg gemacht werden!", 600, 100);
            if (test == true) {
                try {
                    sql.deleteArtikel(artNummer);
                    sql.loadDataArtikel();
                } catch (SQLException exc) {
                    ConfirmBox.display2("Fehler", "Fehler beim Löschen des Artikels");
                }
                popupStage.close();
            } else {
                e.consume();
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
        
        laTe1.getChildren().addAll(name, nameT);
        laTe2.getChildren().addAll(mehrwertSt, mwstT);
        laTe3.getChildren().addAll(artNr, artNrT);
        laTe4.getChildren().addAll(gruppeL, gruppe);
        laTe5.getChildren().addAll(preis, preisET);
        laTe6.getChildren().addAll(preisV, preisVT);
        laTe7.getChildren().addAll(bestand, bestandT);
        laTe8.getChildren().addAll(ztext, ztextT);
        
        
        VBox sum = new VBox();
        
        sum.getChildren().addAll(laTe1, laTe2, laTe3, laTe4, laTe5, laTe6, laTe7, laTe8);

        HBox bottom = new HBox();
        bottom.getChildren().addAll(cancel, delete, confirm);
        bottom.setPadding(new Insets(10, 10, 10, 10));
        bottom.setSpacing(8);
        bottom.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setCenter(sum);
        pane.setBottom(bottom);

        details = new Scene(pane, 500, 600);
        popupStage.setScene(details);
        popupStage.show();
    }
}
