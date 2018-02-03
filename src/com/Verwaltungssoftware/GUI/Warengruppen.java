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
public class Warengruppen {
     static Scene warengruppe;

    public static void display() {
        Stage popupStage = new Stage();

        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Warengruppen verwalten");

        Label delete = new Label("Warengruppe löschen");
        Label add = new Label("Warengrupe hinzufügen");

        ChoiceBox gruppe = new ChoiceBox();
        gruppe.getItems().addAll("Marcel suckt dicke Cocks");
        //gruppe.setValue("Steine");

        TextField warengruppeT = new TextField();
        warengruppeT.setMaxWidth(1000);
        
        Button addB = new Button("Hinzufügen");
        Button deleteB = new Button("Löschen");

        Button cancel = new Button("Abbrechen");
        cancel.setOnAction(e -> popupStage.close());
        Button confirm = new Button("Bestätigen");
        confirm.setOnAction(e -> {
          
            popupStage.close();
        });

        HBox left = new HBox();
        HBox right = new HBox();
        left.setPadding(new Insets(10));
        left.setSpacing(8);
        right.setPadding(new Insets(10));
        right.setSpacing(8);
        
        left.getChildren().addAll(add, warengruppeT, addB);
        right.getChildren().addAll(delete, gruppe, deleteB);
        
        VBox sum = new VBox();
        sum.getChildren().addAll(left, right);

        HBox bottom = new HBox();
        bottom.getChildren().addAll(cancel, confirm);
        bottom.setPadding(new Insets(10, 10, 10, 10));
        bottom.setSpacing(8);
        bottom.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setCenter(sum);
        pane.setBottom(bottom);

        warengruppe = new Scene(pane, 450, 200);
        popupStage.setScene(warengruppe);
        popupStage.show();
    }
}
