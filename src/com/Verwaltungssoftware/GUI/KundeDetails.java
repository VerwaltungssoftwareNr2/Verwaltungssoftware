/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verwaltungssoftware.GUI;

import com.verwaltungssoftware.database.ISql;
import com.verwaltungssoftware.objects.Kunde;
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
public class KundeDetails {

    static Scene details;
    static Kunde kunde = null;

    public static void display(String kNummer, ISql sql) {
        Stage popupStage = new Stage();

        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Kundeninformationen");
        Label kundennummer = new Label("Kundennummer");
        Label anrede = new Label("Anrede");
        Label vorname = new Label("Vorname");
        Label nachname = new Label("Nachname");
        Label strasse = new Label("Straße");
        Label hausnummer = new Label("Hausnummer");
        Label zusatz = new Label("Zusatz");
        Label plz = new Label("Postleitzahl");
        Label ort = new Label("Ort");
        Label land = new Label("Land");

        try {
            kunde = sql.loadDataKunde(kNummer);
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Laden der Daten");
            System.out.println(exc.getMessage());
        }
        String h = "Herr";
        String f = "Frau";
        ChoiceBox anredeChoice = new ChoiceBox();
        anredeChoice.getItems().addAll(h, f);
        if (kunde.getAnrede().equals(h)) {
            anredeChoice.setValue(h);
        } else{
            anredeChoice.setValue(h);
        }
        TextField kNRT = new TextField(kNummer);
        kNRT.setMaxWidth(1000);
        kNRT.setEditable(false);
        TextField vornameT = new TextField(kunde.getVorname());
        vornameT.setMaxWidth(1000);
        TextField nachnameT = new TextField(kunde.getName());
        nachnameT.setMaxWidth(1000);
        TextField strasseT = new TextField(kunde.getStraße());
        strasseT.setMaxWidth(1000);
        TextField hausnummerT = new TextField(kunde.getHausnummer());
        hausnummerT.setMaxWidth(1000);
        TextField zusatzT = new TextField(kunde.getZusatz());
        zusatzT.setMaxWidth(1000);
        TextField plzT = new TextField(kunde.getPlz());
        plzT.setMaxWidth(1000);
        TextField ortT = new TextField(kunde.getOrt());
        ortT.setMaxWidth(1000);
        TextField landT = new TextField(kunde.getLand());
        landT.setMaxWidth(1000);

        Button confirm = new Button("Bestätigen");
        confirm.setOnAction(e -> popupStage.close());
        Button back = new Button("Abbrechen");
        back.setOnAction(e -> popupStage.close());
        Button delete = new Button("Kunden löschen");
        delete.setOnAction(e -> {
            boolean test = ConfirmBox.display("Kunden löschen", "Möchten sie den Kunden wirklich löschen? Dieser Vorgang kann nicht rückgängig gemacht werden!", 600, 100);
            if (test == true) {
                try{
                    sql.deleteKunde(kNummer);
                } catch(SQLException exc){
                    ConfirmBox.display2("Fehler", "Fehler beim Löschen des Kunden");
                }
                popupStage.close();
            } else {
                e.consume();
            }
        });
        confirm.setOnAction(e -> {
            boolean test = ConfirmBox.display("Kunden ändern", "Möchten sie die Kundendaten wirklich abändern? Dieser Vorgang kann nicht rückgängig gemacht werden!", 600, 100);
            if (test == true) {
                try{
                    sql.updateKunde(kNummer, anredeChoice.getSelectionModel().getSelectedItem().toString(), vornameT.getText(), nachnameT.getText(),
                            strasseT.getText(), hausnummerT.getText(), zusatzT.getText(), plzT.getText(), ortT.getText(), landT.getText());
                } catch(SQLException exc){
                    ConfirmBox.display2("Fehler", "Fehler beim Ändern des Kundenstammsatzes");
                    System.out.println(exc.getMessage());
                }
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
        left.getChildren().addAll(kundennummer, anrede, vorname, nachname, strasse, hausnummer, zusatz, plz, ort, land);
        right.getChildren().addAll(kNRT, anredeChoice, vornameT, nachnameT, strasseT, hausnummerT, zusatzT, plzT, ortT, landT);
        border.setLeft(left);
        border.setCenter(right);
        border.setBottom(hbox);

        details = new Scene(border, 450, 500);

        popupStage.setScene(details);
        popupStage.show();
    }
}
