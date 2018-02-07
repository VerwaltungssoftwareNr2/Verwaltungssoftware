/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verwaltungssoftware.GUI;

import com.verwaltungssoftware.database.ISql;
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
public class ArtikelAdd {

    static Scene artikelInfo;

    public static void display(ISql sql) {
        Stage popupStage = new Stage();

        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Artikel hinzufügen");

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
            sql.loadDataWarengruppe();
            for (String s : sql.getDataWarengruppe()) {
                gruppe.getItems().add(s);
            }
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Laden der Warengruppen");
        }

        TextField nameT = new TextField();
        nameT.setPrefWidth(300);
        nameT.setMaxWidth(1000);
        TextArea ztextT = new TextArea();
        ztextT.setPrefWidth(300);
        ztextT.setMaxWidth(1000);
        TextField artNrT = new TextField();
        artNrT.setPrefWidth(300);
        artNrT.setMaxWidth(1000);
        TextField preisET = new TextField();
        preisET.setPrefWidth(300);
        preisET.setMaxWidth(1000);
        TextField preisVT = new TextField();
        preisVT.setPrefWidth(300);
        preisVT.setMaxWidth(1000);
        TextField bestandT = new TextField();
        bestandT.setPrefWidth(300);
        bestandT.setMaxWidth(1000);

        String n = "19";
        String s = "7";

        ChoiceBox mehrwertC = new ChoiceBox();
        mehrwertC.getItems().addAll(n, s);
        mehrwertC.setValue(n);
        mehrwertC.setPrefWidth(300);
        
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
        laTe2.getChildren().addAll(mehrwertSt, mehrwertC);
        laTe3.getChildren().addAll(artNr, artNrT);
        laTe4.getChildren().addAll(gruppeL, gruppe);
        laTe5.getChildren().addAll(preis, preisET);
        laTe6.getChildren().addAll(preisV, preisVT);
        laTe7.getChildren().addAll(bestand, bestandT);
        laTe8.getChildren().addAll(ztext, ztextT);
        
        
        VBox sum = new VBox();
        
        sum.getChildren().addAll(laTe1, laTe2, laTe3, laTe4, laTe5, laTe6, laTe7, laTe8);
        
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
                LocalDate ld = LocalDate.now();
                sql.safeNewArtikel(artNrT.getText(), nameT.getText(), ztextT.getText(), 
                        preisET.getText(), preisVT.getText(), mehrwertC.getValue().toString(), 
                        bestandT.getText(), ld.toString(), gruppe.getSelectionModel().getSelectedItem().toString());
                sql.loadDataArtikel();
            } catch (SQLException exc) {
                ConfirmBox.display2("Fehler", "Fehler beim Erstellen der Artikel");
                System.out.println(exc.getMessage());
            }
            popupStage.close();
        });

        HBox bottom = new HBox();
        bottom.getChildren().addAll(cancel, confirm);
        bottom.setPadding(new Insets(10, 10, 10, 10));
        bottom.setSpacing(8);
        bottom.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setCenter(sum);
        pane.setBottom(bottom);

        artikelInfo = new Scene(pane, 450, 600);
        popupStage.setScene(artikelInfo);
        popupStage.show();
    }
}
