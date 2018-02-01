/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verwaltungssoftware.GUI;

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
public class KundeDetails {
    static Scene details;
    public static void display(){
        Stage popupStage = new Stage();

        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Kundeninformationen");
        Label anrede = new Label("Anrede");
        Label vorname = new Label("Vorname");
        Label nachname = new Label("Nachname");
        Label strasse = new Label("Straße");
        Label hausnummer = new Label("Hausnummer");
        Label plz = new Label("Postleitzahl");
        Label ort = new Label("Ort");
        Label land = new Label("Land");
        Label zusatz = new Label("Zusatz");
        
        String h = "Herr";
        String f = "Frau";
        ChoiceBox anredeChoice = new ChoiceBox();
        anredeChoice.getItems().addAll(h, f);
        anredeChoice.setValue(h);
        
         TextField vornameT = new TextField();
        vornameT.setMaxWidth(1000);
        TextField nachnameT = new TextField();
        nachnameT.setMaxWidth(1000);
        TextField strasseT = new TextField();
        strasseT.setMaxWidth(1000);
        TextField hausnummerT = new TextField();
        hausnummerT.setMaxWidth(1000);
        TextField plzT = new TextField();
        plzT.setMaxWidth(1000);
        TextField ortT = new TextField();
        ortT.setMaxWidth(1000);
        TextField landT = new TextField();
        landT.setMaxWidth(1000);
        TextArea zusatzT = new TextArea();
        zusatzT.setMaxWidth(1000);
        
        Button confirm = new Button("Bestätigen");
        confirm.setOnAction(e -> popupStage.close());
        Button back = new Button("Abbrechen");
        back.setOnAction(e -> popupStage.close());
        Button delete = new Button("Kunden löschen");
        delete.setOnAction(e -> {
            boolean test = ConfirmBox.display("Kunden löschen", "Möchten sie den Kunden wirklich löschen? Dieser Vorgang kann nicht rückgängg gemacht werden!", 600, 100);
            if (test == true) {
                popupStage.close();
            } else {
                e.consume();
            }
        });
        
        BorderPane border = new BorderPane();
        VBox left = new VBox();
        left.setPadding(new Insets(10));
        left.setSpacing(16);
        VBox right = new VBox();
        right.setPadding(new Insets(10));
        right.setSpacing(8);
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setSpacing(8);
        hbox.setAlignment(Pos.CENTER);
        
        hbox.getChildren().addAll(back, delete, confirm);
        left.getChildren().addAll(anrede, vorname, nachname, strasse, hausnummer, plz, ort, land, zusatz);
        right.getChildren().addAll(anredeChoice, vornameT, nachnameT, strasseT, hausnummerT, plzT, ortT, landT, zusatzT);
        border.setLeft(left);
        border.setCenter(right);
        border.setBottom(hbox);
        
        details = new Scene(border, 450, 500);
        
        popupStage.setScene(details);
        popupStage.show();
    }
}
