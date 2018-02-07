/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verwaltungssoftware.GUI;

import com.verwaltungssoftware.database.ISql;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Lucas
 */
public class Ortsverwaltung {

    static Scene ortsverwaltung;
    static ArrayList<ArrayList<String>> dataPlz = null;

    public static void display(ISql sql) {
        Stage popupStage = new Stage();

        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Orte verwalten");

        TabPane tabs = new TabPane();

        Tab add = new Tab("Ort hinzufügen");
        Tab change = new Tab("Ort ändern");
        add.setClosable(false);
        change.setClosable(false);
        tabs.getTabs().addAll(add, change);

        Label plz = new Label("Postleitzahl");
        plz.setPrefWidth(200);
        Label ort = new Label("Ort");
        ort.setPrefWidth(200);
        Label land = new Label("Land");
        land.setPrefWidth(200);

        TextField plzT = new TextField();
        TextField ortT = new TextField();
        TextField landT = new TextField();

        Button addB = new Button("Hinzufügen");
        Button cancel = new Button("Abbrechen");
        cancel.setOnAction(e -> popupStage.close());
        addB.setOnAction(e -> {
            if (!plzT.getText().trim().isEmpty() && !ortT.getText().trim().isEmpty() && !landT.getText().trim().isEmpty()) {
                try {
                    sql.safeNewPlz(plzT.getText(), ortT.getText(), landT.getText());
                    ConfirmBox.display2("Meldung", "Postleitzahl erfolgreich gespeichert");
                    plzT.clear();
                    ortT.clear();
                    landT.clear();
                } catch (SQLException exc) {
                    ConfirmBox.display2("Fehler", "Fehler beim Erzeugen der Postleitzahl");
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

        laTe1.getChildren().addAll(plz, plzT);
        laTe2.getChildren().addAll(ort, ortT);
        laTe3.getChildren().addAll(land, landT);

        HBox button = new HBox();
        button.getChildren().addAll(addB, cancel);
        button.setPadding(new Insets(10, 10, 10, 10));
        button.setSpacing(8);
        button.setAlignment(Pos.CENTER);

        VBox sum = new VBox();
        sum.getChildren().addAll(laTe1, laTe2, laTe3, button);

        add.setContent(sum);

        Label auswahl = new Label("Auswahl der Postleitzahl");
        auswahl.setPrefWidth(200);
        ChoiceBox plzSum = new ChoiceBox();
        Label plz2 = new Label("Postleitzahl");
        plz2.setPrefWidth(200);
        Label ort2 = new Label("Ort");
        ort2.setPrefWidth(200);
        Label land2 = new Label("Land");
        land2.setPrefWidth(200);

        TextField plzT2 = new TextField();
        TextField ortT2 = new TextField();
        TextField landT2 = new TextField();
        try {
            dataPlz = sql.loadDataPlz();
            for (ArrayList<String> it : dataPlz) {
                for (String s : it) {
                    plzSum.getItems().add(s);
                    break;
                }
            }
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Laden der Postleitzahlen");
        }
        plzSum.setOnAction(e -> {
            for (int i = 0; i < dataPlz.size(); i++) {
                for (int j = 0; j < dataPlz.get(i).size(); j++) {
                    if (plzSum.getSelectionModel().getSelectedItem().toString().equals(dataPlz.get(i).get(j))) {
                        plzT2.setText(dataPlz.get(i).get(j));
                        ortT2.setText(dataPlz.get(i).get(j + 1));
                        landT2.setText(dataPlz.get(i).get(j + 2));
                    }
                }
            }
        });

        Button changeB = new Button("Ändern");
        Button cancel2 = new Button("Abbrechen");
        cancel2.setOnAction(e -> popupStage.close());
        changeB.setOnAction(e -> {
            if (!plzT2.getText().trim().isEmpty() && !ortT2.getText().trim().isEmpty() && !landT2.getText().trim().isEmpty()) {
                try {
                    sql.updatePlz(plzSum.getSelectionModel().getSelectedItem().toString(), plzT2.getText(), ortT2.getText(), landT2.getText());
                    dataPlz.clear();
                    plzSum.getItems().clear();
                    dataPlz = sql.loadDataPlz();
                    for (ArrayList<String> it : dataPlz) {
                        for (String s : it) {
                            plzSum.getItems().add(s);
                            break;
                        }
                    }
                } catch (SQLException exc) {
                    ConfirmBox.display2("Fehler", "Fehler beim Aktualisieren der Postleitzahl");
                }
            }
        });

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

        laTe5.getChildren().addAll(auswahl, plzSum);
        laTe6.getChildren().addAll(plz2, plzT2);
        laTe7.getChildren().addAll(ort2, ortT2);
        laTe8.getChildren().addAll(land2, landT2);

        HBox button2 = new HBox();
        button2.getChildren().addAll(changeB, cancel2);
        button2.setPadding(new Insets(10, 10, 10, 10));
        button2.setSpacing(8);
        button2.setAlignment(Pos.CENTER);

        VBox sum2 = new VBox();
        sum2.getChildren().addAll(laTe5, laTe6, laTe7, laTe8, button2);

        change.setContent(sum2);

        ortsverwaltung = new Scene(tabs, 600, 300);
        popupStage.setScene(ortsverwaltung);
        popupStage.show();
    }

    public void fillChoiceBox(ActionEvent e) {

    }
}
