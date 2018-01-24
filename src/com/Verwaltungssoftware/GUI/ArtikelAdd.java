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
        Label ztext = new Label("Zusatztext");
        Label artNr = new Label("Artikelnummer");
        Label gruppeL = new Label("Warengruppe");
        Label preis = new Label("Einkaufspreis");
        Label preisV = new Label("Verkaufspreis");
        Label bestand = new Label("Bestand");
        Label mehrwertSt = new Label("Mehrwertsteuer in %");

        ChoiceBox gruppe = new ChoiceBox();
        gruppe.getItems().addAll("Steine", "Rohre", "Zäune", "Kabel");
        //gruppe.setValue("Steine");

        TextField nameT = new TextField();
        nameT.setMaxWidth(1000);
        TextArea ztextT = new TextArea();
        ztextT.setMaxWidth(1000);
        TextField artNrT = new TextField();
        artNrT.setMaxWidth(1000);
        TextField preisET = new TextField();
        preisET.setMaxWidth(1000);
        TextField preisVT = new TextField();
        preisVT.setMaxWidth(1000);
        TextField bestandT = new TextField();
        bestandT.setMaxWidth(1000);

        String n = "19";
        String s = "7";

        ChoiceBox mehrwertC = new ChoiceBox();
        mehrwertC.getItems().addAll(n, s);
        mehrwertC.setValue(n);

        Button cancel = new Button("Abbrechen");
        cancel.setOnAction(e -> popupStage.close());
        Button confirm = new Button("Bestätigen");
        confirm.setOnAction(e -> {
            try {
                LocalDate ld = LocalDate.now();
                sql.safeNewArtikel(artNrT.getText(), nameT.getText(), ztextT.getText(), preisET.getText(), preisVT.getText(), mehrwertC.getValue().toString(), bestandT.getText(), ld.toString(), null);
                sql.loadDataArtikel();
            } catch (SQLException exc) {
                ConfirmBox.display2("Fehler", "Fehler beim Erstellen der Artikel");
                System.out.println(exc.getMessage());
            }
            popupStage.close();
        });

        VBox left = new VBox();
        VBox right = new VBox();
        left.setPadding(new Insets(10));
        left.setSpacing(16);
        right.setPadding(new Insets(10));
        right.setSpacing(8);

        left.getChildren().addAll(name, artNr, gruppeL, preis, preisV, bestand, mehrwertSt, ztext);
        right.getChildren().addAll(nameT, artNrT, gruppe, preisET, preisVT, bestandT, mehrwertC, ztextT);

        HBox bottom = new HBox();
        bottom.getChildren().addAll(cancel, confirm);
        bottom.setPadding(new Insets(10, 10, 10, 10));
        bottom.setSpacing(8);
        bottom.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setLeft(left);
        pane.setCenter(right);
        pane.setBottom(bottom);

        artikelInfo = new Scene(pane, 450, 450);
        popupStage.setScene(artikelInfo);
        popupStage.show();
    }
}
