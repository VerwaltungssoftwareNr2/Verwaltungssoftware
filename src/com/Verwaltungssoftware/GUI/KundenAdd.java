/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verwaltungssoftware.GUI;

import com.verwaltungssoftware.database.ISql;
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
public class KundenAdd {

    static Scene kundenauswahl, kundeninfoP, kundeninfoG;
    static ArrayList<ArrayList<String>> dataPlz = null;

    public static void display(ISql sql) {
        Stage popupStage = new Stage();

        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Kunde hinzufügen");
        popupStage.setMinWidth(300);

        Button privatkunde = new Button("Privatkunde");
        Button geschKunde = new Button("Geschäftskunde");
        privatkunde.setOnAction(e -> popupStage.setScene(kundeninfoP));
        geschKunde.setOnAction(e -> popupStage.setScene(kundeninfoG));
        privatkunde.setPrefSize(150, 75);
        geschKunde.setPrefSize(150, 75);

        HBox box = new HBox();
        box.setPadding(new Insets(10, 10, 10, 10));
        box.setSpacing(8);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(privatkunde, geschKunde);

        kundenauswahl = new Scene(box, 300, 150);

        Label anrede = new Label("Anrede");
        anrede.setPrefWidth(200);
        Label vorname = new Label("Vorname");
        vorname.setPrefWidth(200);
        Label nachname = new Label("Nachname");
        nachname.setPrefWidth(200);
        Label strasse = new Label("Straße");
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
        anredeChoice.setValue(h);

        TextField vornameT = new TextField();
        vornameT.setPrefWidth(300);
        vornameT.setMaxWidth(1000);
        TextField nachnameT = new TextField();
        nachnameT.setPrefWidth(300);
        nachnameT.setMaxWidth(1000);
        TextField strasseT = new TextField();
        strasseT.setPrefWidth(300);
        strasseT.setMaxWidth(1000);
        TextField hausnummerT = new TextField();
        hausnummerT.setPrefWidth(300);
        hausnummerT.setMaxWidth(1000);
        TextField zusatzT = new TextField();
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

        laTe1.getChildren().addAll(anrede, anredeChoice);
        laTe2.getChildren().addAll(vorname, vornameT);
        laTe3.getChildren().addAll(nachname, nachnameT);
        laTe4.getChildren().addAll(strasse, strasseT);
        laTe5.getChildren().addAll(hausnummer, hausnummerT);
        laTe6.getChildren().addAll(zusatz, zusatzT);
        laTe7.getChildren().addAll(plz, plzT);
        laTe8.getChildren().addAll(ort, ortT);
        laTe9.getChildren().addAll(land, landT);

        VBox sum = new VBox();
        sum.getChildren().addAll(laTe1, laTe2, laTe3, laTe4, laTe5, laTe6, laTe7, laTe8, laTe9);

        Button confirm = new Button("Bestätigen");
        confirm.setOnAction(e -> {
            if (!vornameT.getText().trim().isEmpty() && !nachnameT.getText().trim().isEmpty()
                    && !strasseT.getText().trim().isEmpty() && !hausnummerT.getText().trim().isEmpty()) {
                try {
                    sql.safeNewKunde("privat", anredeChoice.getSelectionModel().getSelectedItem().toString(), vornameT.getText(), nachnameT.getText(),
                            strasseT.getText(), hausnummerT.getText(), zusatzT.getText(), plzT.getSelectionModel().getSelectedItem().toString());
                    sql.loadDataKunde();
                    popupStage.close();
                } catch (SQLException exc) {
                    ConfirmBox.display2("Fehler", "Fehler beim Erzeugen des Kunden");
                    System.out.println(exc.getMessage());
                }
            }
        });
        Button back = new Button("Abbrechen");
        back.setOnAction(e -> popupStage.setScene(kundenauswahl));

        BorderPane border = new BorderPane();
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setSpacing(8);
        hbox.setAlignment(Pos.CENTER);

        hbox.getChildren().addAll(back, confirm);
        border.setCenter(sum);
        border.setBottom(hbox);

        kundeninfoP = new Scene(border, 450, 500); //ENDE PRIVATKUNDE

        Label unternehmen = new Label("Name des Unternehmen");
        unternehmen.setPrefWidth(200);
        Label anrede2 = new Label("Anrede");
        anrede2.setPrefWidth(200);
        Label personV = new Label("Ansprechpartner - Vorname");
        personV.setPrefWidth(200);
        Label personN = new Label("Ansprechpartner - Nachname");
        personN.setPrefWidth(200);
        Label strasseHaupt = new Label("Straße des Hauptsitz");
        strasseHaupt.setPrefWidth(200);
        Label hausnummer2 = new Label("Hausnummer");
        hausnummer2.setPrefWidth(200);
        Label plz2 = new Label("Postleitzahl");
        plz2.setPrefWidth(200);
        Label ort2 = new Label("Ort");
        ort2.setPrefWidth(200);
        Label land2 = new Label("Land");
        land2.setPrefWidth(200);
        Label zusatz2 = new Label("Zusatz");
        zusatz2.setPrefWidth(200);

        ChoiceBox anrede2C = new ChoiceBox();
        anrede2C.setPrefWidth(300);
        anrede2C.getItems().addAll(h, f);
        anrede2C.setValue(h);

        TextField unternehmenT = new TextField();
        unternehmenT.setPrefWidth(300);
        unternehmenT.setMaxWidth(1000);
        TextField strasseHauptT = new TextField();
        strasseHauptT.setPrefWidth(300);
        strasseHauptT.setMaxWidth(1000);
        TextField personVT = new TextField();
        personVT.setPrefWidth(300);
        personVT.setMaxWidth(1000);
        TextField personNT = new TextField();
        personNT.setPrefWidth(300);
        personNT.setMaxWidth(1000);
        TextField hausnummer2T = new TextField();
        hausnummer2T.setPrefWidth(300);
        hausnummer2T.setMaxWidth(1000);
        TextField zusatz2T = new TextField();
        zusatz2T.setPrefWidth(300);
        zusatz2T.setMaxWidth(1000);
        ChoiceBox plz2T = new ChoiceBox();
        plz2T.setPrefWidth(300);
        plz2T.setMaxWidth(1000);
        TextField ort2T = new TextField();
        ort2T.setPrefWidth(300);
        ort2T.setMaxWidth(1000);
        ort2T.setEditable(false);
        TextField land2T = new TextField();
        land2T.setPrefWidth(300);
        land2T.setMaxWidth(1000);
        land2T.setEditable(false);

        try {
            dataPlz = sql.loadDataPlz();
            for (ArrayList<String> it : dataPlz) {
                for (String s : it) {
                    plz2T.getItems().add(s);
                    break;
                }
            }
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Laden der Postleitzahlen");
        }
        plz2T.setOnAction(e -> {
            for (int i = 0; i < dataPlz.size(); i++) {
                for (int j = 0; j < dataPlz.get(i).size(); j++) {
                    if (plz2T.getSelectionModel().getSelectedItem().toString().equals(dataPlz.get(i).get(j))) {
                        ort2T.setText(dataPlz.get(i).get(j + 1));
                        land2T.setText(dataPlz.get(i).get(j + 2));
                    }
                }
            }
        });

        HBox laTe10 = new HBox();
        laTe10.setPadding(new Insets(10));
        laTe10.setSpacing(8);
        HBox laTe11 = new HBox();
        laTe11.setPadding(new Insets(10));
        laTe11.setSpacing(8);
        HBox laTe12 = new HBox();
        laTe12.setPadding(new Insets(10));
        laTe12.setSpacing(8);
        HBox laTe13 = new HBox();
        laTe13.setPadding(new Insets(10));
        laTe13.setSpacing(8);
        HBox laTe14 = new HBox();
        laTe14.setPadding(new Insets(10));
        laTe14.setSpacing(8);
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

        laTe10.getChildren().addAll(unternehmen, unternehmenT);
        laTe11.getChildren().addAll(anrede2, anrede2C);
        laTe12.getChildren().addAll(personV, personVT);
        laTe13.getChildren().addAll(personN, personNT);
        laTe14.getChildren().addAll(strasseHaupt, strasseHauptT);
        laTe15.getChildren().addAll(hausnummer2, hausnummer2T);
        laTe16.getChildren().addAll(zusatz2, zusatz2T);
        laTe18.getChildren().addAll(plz2, plz2T);
        laTe19.getChildren().addAll(ort2, ort2T);
        laTe19.getChildren().addAll(land2, land2T);

        VBox sum2 = new VBox();
        sum2.getChildren().addAll(laTe10, laTe11, laTe12, laTe13, laTe14, laTe15, laTe16, laTe17, laTe18, laTe19);

        Button confirm2 = new Button("Bestätigen");
        confirm2.setOnAction(e -> {
            if (!unternehmenT.getText().trim().isEmpty() && !personVT.getText().trim().isEmpty() && !personNT.getText().trim().isEmpty()
                    && !strasseHauptT.getText().trim().isEmpty() && !hausnummer2T.getText().trim().isEmpty()) {
                try {
                    sql.safeNewKunde(unternehmenT.getText(), anrede2C.getSelectionModel().getSelectedItem().toString(), personVT.getText(), personNT.getText(),
                            strasseHauptT.getText(), hausnummer2T.getText(), zusatz2T.getText(), plz2T.getSelectionModel().getSelectedItem().toString());
                    sql.loadDataKunde();
                    popupStage.close();
                } catch (SQLException exc) {
                    ConfirmBox.display2("Fehler", "Fehler beim Erzeugen des Kunden");
                    System.out.println(exc.getMessage());
                }
            }
        });
        Button back2 = new Button("Abbrechen");
        back2.setOnAction(e -> popupStage.setScene(kundenauswahl));

        BorderPane border2 = new BorderPane();
        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10, 10, 10, 10));
        hbox2.setSpacing(8);
        hbox2.setAlignment(Pos.CENTER);

        hbox2.getChildren().addAll(back2, confirm2);
        border2.setCenter(sum2);
        border2.setBottom(hbox2);

        kundeninfoG = new Scene(border2, 500, 600);

        popupStage.setScene(kundenauswahl);
        popupStage.show();
    }
}
