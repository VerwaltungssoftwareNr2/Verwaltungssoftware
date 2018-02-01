/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verwaltungssoftware.GUI;

import com.verwaltungssoftware.database.ISql;
import com.verwaltungssoftware.objects.User;
import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
public class InfoBox {//brauche scrollbox, weil zu viel sachen

    public static void display(ISql sql) {
        Stage popupStage = new Stage();

        //blockieren von aktionen
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Informationen ändern");
        popupStage.setMinWidth(300);

        Label allgemeinheader = new Label("Allgemeine Informationen"); //HEADLINE
        allgemeinheader.setUnderline(true);
        Label nameUnternehmen = new Label("Name des Unternehmens");
        Label vornameUnternehmer = new Label("Vorname");
        Label nachnameUnternehmer = new Label("Nachname");
        Label strasse = new Label("Straße");
        Label ort = new Label("Ort");
        Label plz = new Label("Postleitzahl");
        Label land = new Label("Land");
        Label inhaber = new Label("Inhaber");
        Label telefon = new Label("Telefon");
        Label fax = new Label("Fax");
        Label bankheader = new Label("Bankinformationen");//HEADLINE
        bankheader.setUnderline(true);
        Label bank = new Label("Bankinstitut");
        Label kontonummer = new Label("Kontonummer");
        Label bic = new Label("BIC");
        Label iban = new Label("IBAN");
        Label blz = new Label("Bankleitzahl");
        Label finanz = new Label("Finanzdaten"); // HEADLINE
        finanz.setUnderline(true);
        Label amt = new Label("Steueramt");
        Label hrb = new Label("Handelsregisternummer");
        Label steuernummer = new Label("Steuernummer");
        Label ustID = new Label("UmsatzsteuerID");
        Label gb = new Label("Daten zum Rechtstyp: Limited"); //HEADLINE
        gb.setUnderline(true);
        Label comp = new Label("Company");
        Label street = new Label("Street");
        Label town = new Label("Town");
        Label country = new Label("Country");
        Label companyNo = new Label("CompanyNo");
        Label pw = new Label("Aktuelles Passwort");

        TextField allHeaderT = new TextField(); //PLATZHALTER
        allHeaderT.setVisible(false);
        TextField nameUF = new TextField();
        TextField vornameUF = new TextField();
        TextField nachnameUF = new TextField();
        TextField strasseF = new TextField();
        TextField ortF = new TextField();
        TextField plzF = new TextField();
        TextField landF = new TextField();
        TextField inhaberF = new TextField();
        TextField telefonF = new TextField();
        TextField faxF = new TextField();
        TextField bankHeaderT = new TextField(); //PLATZHALTER
        bankHeaderT.setVisible(false);
        TextField bankF = new TextField();
        TextField kontonrT = new TextField();
        TextField bicF = new TextField();
        TextField ibanF = new TextField();
        TextField blzT = new TextField();
        TextField finanzT = new TextField(); // PLATZHALTER
        finanzT.setVisible(false);
        TextField amtT = new TextField();
        TextField hrbT = new TextField();
        TextField steuernummerT = new TextField();
        TextField ustIDT = new TextField();
        TextField gbT = new TextField(); //PLATZHALTER
        gbT.setVisible(false);
        TextField compT = new TextField();
        TextField streetT = new TextField();
        TextField townT = new TextField();
        TextField countryT = new TextField();
        TextField companyNoT = new TextField();
        TextField pwT = new TextField();

        Button confirm = new Button("Bestätigen");
        confirm.setOnAction(e -> {
            User user = new User(bankF.getText(), kontonrT.getText(), blzT.getText(), steuernummerT.getText(), ustIDT.getText(),
                    compT.getText(), streetT.getText(), townT.getText(), countryT.getText(), companyNoT.getText(),
                    vornameUF.getText(), nachnameUF.getText(), strasseF.getText(), plzF.getText(), landF.getText(), ortF.getText(), telefonF.getText(),
                    faxF.getText(), bankF.getText(), bicF.getText(), ibanF.getText(), amtT.getText(), hrbT.getText());
            try {
                if (!sql.getCheckUserConfig()) {
                    sql.createUserConfig(user);
                    popupStage.close();
                } else{
                    //Update Methode mit User als Variable
                }
            } catch (SQLException exc) {
                ConfirmBox.display2("Fehler", "Fehler beim Erzeugen eines Users");
                System.out.println(exc.getMessage());
            }

        });
        Button back = new Button("Abbrechen");
        back.setOnAction(e -> popupStage.close());

        BorderPane border = new BorderPane();
        VBox left = new VBox();
        left.setPadding(new Insets(10));
        left.setSpacing(16);
        VBox right = new VBox();
        right.setPadding(new Insets(10));
        right.setSpacing(8);
        HBox bottom = new HBox();
        bottom.setPadding(new Insets(10, 10, 10, 10));
        bottom.setSpacing(8);
        bottom.setAlignment(Pos.CENTER);

        left.getChildren().addAll(allgemeinheader, nameUnternehmen, vornameUnternehmer, nachnameUnternehmer, strasse, ort, plz, land, inhaber, telefon, fax, bankheader, bank, kontonummer, bic, iban, blz, finanz, amt, hrb, steuernummer, ustID, gb, comp, street, town, country, companyNo, pw);
        right.getChildren().addAll(allHeaderT, nameUF, vornameUF, nachnameUF, strasseF, ortF, plzF, landF, inhaberF, telefonF, faxF, bankHeaderT, bankF, kontonrT, bicF, ibanF, blzT, finanzT, amtT, hrbT, steuernummerT, ustIDT, gbT, compT, streetT, townT, countryT, companyNoT, pwT);
        bottom.getChildren().addAll(back, confirm);
        border.setLeft(left);
        border.setCenter(right);
        border.setBottom(bottom);

        ScrollPane scroll = new ScrollPane();
        scroll.setContent(border);
        scroll.setPadding(new Insets(10));

        Scene scene = new Scene(scroll, 400, 500);
        popupStage.setScene(scene);
        popupStage.show();

    }

}
