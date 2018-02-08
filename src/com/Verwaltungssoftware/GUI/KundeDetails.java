/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verwaltungssoftware.GUI;

import com.verwaltungssoftware.database.ISql;
import com.verwaltungssoftware.objects.Kunde;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
    static ArrayList<ArrayList<String>> dataPlz = null;

    public static void display(String kNummer, ISql sql) {
        Stage popupStage = new Stage();

        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Kundeninformationen");

        try {
            kunde = sql.loadDataKunde(kNummer);
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Laden der Daten");
            System.out.println(exc.getMessage());
        }
        boolean testKunde = false;
        try {
            testKunde = sql.checkKundeHatRechnung(kNummer);
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Überprüfen der Daten");
            System.out.println(exc.getMessage());
        }

        Label unternehmensname = new Label("Name des Unternehmens");
        unternehmensname.setPrefWidth(200);
        Label kundennummer = new Label("Kundennummer");
        kundennummer.setPrefWidth(200);
        Label anrede = new Label("Anrede");
        anrede.setPrefWidth(200);
        Label vorname = new Label("Vorname");
        if (!kunde.getUnternehmensname().equals("privat")) {
            vorname.setText("Ansprechpartner - Vorname");
        }
        vorname.setPrefWidth(200);
        Label nachname = new Label("Nachname");
        if (!kunde.getUnternehmensname().equals("privat")) {
            nachname.setText("Ansprechpartner - Nachname");
        }
        nachname.setPrefWidth(200);
        Label strasse = new Label("Straße");
        if (!kunde.getUnternehmensname().equals("privat")) {
            strasse.setText("Hauptsitz - Straße");
        }
        strasse.setPrefWidth(200);
        Label hausnummer = new Label("Hausnummer");
        hausnummer.setPrefWidth(200);
        Label plz = new Label("Postleitzahl");
        plz.setPrefWidth(200);
        Label ort = new Label("Ort");
        ort.setPrefWidth(200);
        Label land = new Label("Land");
        land.setPrefWidth(200);
        Label zusatz = new Label("Zusatz");
        zusatz.setPrefWidth(200);

        String h = "Herr";
        String f = "Frau";
        ChoiceBox anredeChoice = new ChoiceBox();
        anredeChoice.setPrefWidth(300);
        anredeChoice.getItems().addAll(h, f);
        if (kunde.getAnrede().equals(h)) {
            anredeChoice.setValue(h);
        } else {
            anredeChoice.setValue(f);
        }
        TextField kNRT = new TextField(kNummer);
        kNRT.setPrefWidth(300);
        kNRT.setMaxWidth(1000);
        kNRT.setEditable(false);
        TextField uNameT = new TextField(kunde.getUnternehmensname());
        uNameT.setPrefWidth(300);
        uNameT.setMaxWidth(1000);
        TextField vornameT = new TextField(kunde.getVorname());
        vornameT.setPrefWidth(300);
        vornameT.setMaxWidth(1000);
        TextField nachnameT = new TextField(kunde.getName());
        nachnameT.setPrefWidth(300);
        nachnameT.setMaxWidth(1000);
        TextField strasseT = new TextField(kunde.getStraße());
        strasseT.setPrefWidth(300);
        strasseT.setMaxWidth(1000);
        TextField hausnummerT = new TextField(kunde.getHausnummer());
        hausnummerT.setPrefWidth(300);
        hausnummerT.setMaxWidth(1000);
        TextField zusatzT = new TextField(kunde.getZusatz());
        zusatzT.setPrefWidth(300);
        zusatzT.setMaxWidth(1000);
        ChoiceBox plzT = new ChoiceBox();
        plzT.setPrefWidth(300);
        plzT.setMaxWidth(1000);
        TextField ortT = new TextField();
        ortT.setPrefWidth(300);
        ortT.setMaxWidth(1000);
        ortT.setEditable(false);
        TextField landT = new TextField();
        landT.setPrefWidth(300);
        landT.setMaxWidth(1000);
        landT.setEditable(false);

        try {
            dataPlz = sql.loadDataPlz();
            for (ArrayList<String> it : dataPlz) {
                for (String s : it) {
                    plzT.getItems().add(s);
                    break;
                }
            }
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Laden der Postleitzahlen");
        }
        plzT.getSelectionModel().select(kunde.getPlz());
        ortT.setText(kunde.getOrt());
        landT.setText(kunde.getLand());

        plzT.setOnAction(e -> {
            for (int i = 0; i < dataPlz.size(); i++) {
                for (int j = 0; j < dataPlz.get(i).size(); j++) {
                    if (plzT.getSelectionModel().getSelectedItem().toString().equals(dataPlz.get(i).get(j))) {
                        ortT.setText(dataPlz.get(i).get(j + 1));
                        landT.setText(dataPlz.get(i).get(j + 2));
                    }
                }
            }
        });
        Button confirm = new Button("Bestätigen");
        confirm.setOnAction(e -> popupStage.close());
        Button back = new Button("Abbrechen");
        back.setOnAction(e -> popupStage.close());
        Button delete = new Button("Kunden löschen");
        if(testKunde){
            delete.setDisable(true);
        }
        delete.setOnAction(e -> {
            boolean test = ConfirmBox.display("Kunden löschen", "Möchten sie den Kunden wirklich löschen? Dieser Vorgang kann nicht rückgängig gemacht werden!", 600, 100);
            if (test == true) {
                try {
                    sql.deleteKunde(kNummer);
                    sql.loadDataKunde();
                } catch (SQLException exc) {
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
                try {
                    sql.updateKunde(kNummer, uNameT.getText(), anredeChoice.getSelectionModel().getSelectedItem().toString(), vornameT.getText(), nachnameT.getText(),
                            strasseT.getText(), hausnummerT.getText(), zusatzT.getText(), plzT.getSelectionModel().getSelectedItem().toString());
                    sql.loadDataKunde();
                } catch (SQLException exc) {
                    ConfirmBox.display2("Fehler", "Fehler beim Ändern des Kundenstammsatzes");
                    System.out.println(exc.getMessage());
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
        HBox laTe9 = new HBox();
        laTe9.setPadding(new Insets(10));
        laTe9.setSpacing(8);
        HBox laTe10 = new HBox();
        laTe10.setPadding(new Insets(10));
        laTe10.setSpacing(8);
        HBox laTe11 = new HBox();
        laTe11.setPadding(new Insets(10));
        laTe11.setSpacing(8);

        laTe1.getChildren().addAll(anrede, anredeChoice);
        laTe2.getChildren().addAll(vorname, vornameT);
        laTe3.getChildren().addAll(nachname, nachnameT);
        laTe4.getChildren().addAll(strasse, strasseT);
        laTe5.getChildren().addAll(hausnummer, hausnummerT);
        laTe6.getChildren().addAll(zusatz, zusatzT);
        laTe7.getChildren().addAll(plz, plzT);
        laTe8.getChildren().addAll(ort, ortT);
        laTe9.getChildren().addAll(land, landT);
        laTe10.getChildren().addAll(kundennummer, kNRT);
        laTe11.getChildren().addAll(unternehmensname, uNameT);

        VBox sum = new VBox();
        sum.getChildren().add(laTe10);
        if (!kunde.getUnternehmensname().equals("privat")) {
            sum.getChildren().add(laTe11);
        }
        sum.getChildren().addAll(laTe1, laTe2, laTe3, laTe4, laTe5, laTe6, laTe7, laTe8, laTe9);

        BorderPane border = new BorderPane();
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setSpacing(8);
        hbox.setAlignment(Pos.CENTER);

        hbox.getChildren().addAll(back, delete, confirm);
        border.setCenter(sum);
        border.setBottom(hbox);

        details = new Scene(border, 500, 600);

        popupStage.setScene(details);
        popupStage.show();
    }
}
