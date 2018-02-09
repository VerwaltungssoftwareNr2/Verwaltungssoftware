/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verwaltungssoftware.GUI;

import com.verwaltungssoftware.objects.Artikel;
import com.verwaltungssoftware.objects.Kunde;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Lucas
 */
public class TablePopup {

    static Scene tablePopup;

    public static void display(GUI_Verwaltungssoftware gui, String title, TableView<Kunde> box) {
        Stage popupStage = new Stage();

        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        box.setOnMouseClicked((MouseEvent me) -> {
            if (me.getClickCount() == 2) {
                if (!box.getSelectionModel().isEmpty()) {
                    gui.tempKunde[0] = box.getSelectionModel().getSelectedItems().get(0).getKundennummer();
                    gui.tempKunde[1] = box.getSelectionModel().getSelectedItems().get(0).getAnrede();
                    gui.tempKunde[2] = box.getSelectionModel().getSelectedItems().get(0).getVorname();
                    gui.tempKunde[3] = box.getSelectionModel().getSelectedItems().get(0).getName();
                    gui.tempKunde[4] = box.getSelectionModel().getSelectedItems().get(0).getStraße();
                    gui.tempKunde[5] = box.getSelectionModel().getSelectedItems().get(0).getPlz();
                    gui.tempKunde[6] = box.getSelectionModel().getSelectedItems().get(0).getOrt();
                    popupStage.close();
                }
            }
        });
        Button cancel = new Button("Abbrechen");
        cancel.setOnAction(e -> popupStage.close());

        HBox buttons = new HBox();
        buttons.getChildren().add(cancel);
        buttons.setPadding(new Insets(10, 10, 10, 10));
        buttons.setSpacing(8);

        buttons.setAlignment(Pos.CENTER);
        BorderPane pane = new BorderPane();

        VBox filterBox = gui.createFilter(box, "Kunde", true);

        pane.setCenter(filterBox);
        pane.setBottom(buttons);

        tablePopup = new Scene(pane, 600, 500);
        popupStage.setScene(tablePopup);
        popupStage.showAndWait();
    }

    public static void displayArtikel(GUI_Verwaltungssoftware gui, String title, TableView<Artikel> box) {
        Stage popupStage = new Stage();

        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        box.setOnMouseClicked((MouseEvent me) -> {
            if (me.getClickCount() == 2) {
                if (!box.getSelectionModel().isEmpty()) {
                    gui.tempArtikel[0] = box.getSelectionModel().getSelectedItems().get(0).getArtikelnummer();
                    gui.tempArtikel[1] = box.getSelectionModel().getSelectedItems().get(0).getBezeichnung();
                    gui.tempArtikel[2] = box.getSelectionModel().getSelectedItems().get(0).getVerkaufspreis();
                    gui.tempArtikel[3] = box.getSelectionModel().getSelectedItems().get(0).getBestand();
                    gui.tempArtikel[4] = box.getSelectionModel().getSelectedItems().get(0).getZusatztext();
                    popupStage.close();
                }
            }
        });
        Button cancel = new Button("Abbrechen");
        cancel.setOnAction(e -> popupStage.close());

        HBox buttons = new HBox();
        buttons.getChildren().add(cancel);
        buttons.setPadding(new Insets(10, 10, 10, 10));
        buttons.setSpacing(8);

        buttons.setAlignment(Pos.CENTER);
        BorderPane pane = new BorderPane();

        VBox filterBox = gui.createFilter(box, "Artikel", true);

        pane.setCenter(filterBox);
        pane.setBottom(buttons);

        tablePopup = new Scene(pane, 600, 500);
        popupStage.setScene(tablePopup);
        popupStage.showAndWait();
    }
}
