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

    static User user = null;

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
        Label street = new Label("Street");
        Label town = new Label("Town");
        Label country = new Label("Country");
        Label companyNo = new Label("CompanyNo");

        final TextField allHeaderT = new TextField(); //PLATZHALTER
        allHeaderT.setVisible(false);
        final TextField nameUF = new TextField();
        final TextField vornameUF = new TextField();
        final TextField nachnameUF = new TextField();
        final TextField strasseF = new TextField();
        final TextField ortF = new TextField();
        final TextField plzF = new TextField();
        final TextField landF = new TextField();
        final TextField telefonF = new TextField();
        final TextField faxF = new TextField();
        final TextField bankHeaderT = new TextField(); //PLATZHALTER
        bankHeaderT.setVisible(false);
        final TextField bankF = new TextField();
        final TextField kontonrT = new TextField();
        final TextField bicF = new TextField();
        final TextField ibanF = new TextField();
        final TextField blzT = new TextField();
        final TextField finanzT = new TextField(); // PLATZHALTER
        finanzT.setVisible(false);
        final TextField amtT = new TextField();
        final TextField hrbT = new TextField();
        final TextField steuernummerT = new TextField();
        final TextField ustIDT = new TextField();
        final TextField gbT = new TextField(); //PLATZHALTER
        gbT.setVisible(false);
        final TextField streetT = new TextField();
        final TextField townT = new TextField();
        final TextField countryT = new TextField();
        final TextField companyNoT = new TextField();

        try {
            sql.checkUserConfig();
            if (sql.getCheckUserConfig()) {
                user = sql.loadUser();
                nameUF.setText(user.getCompany());
                vornameUF.setText(user.getPreName());
                nachnameUF.setText(user.getLastName());
                strasseF.setText(user.getaStreet());
                ortF.setText(user.getaOrt());
                plzF.setText(user.getaPlz());
                landF.setText(user.getaLand());
                telefonF.setText(user.getaTel());
                faxF.setText(user.getaFax());
                bankF.setText(user.getBankName());
                kontonrT.setText(user.getKontoNr());
                bicF.setText(user.getaBic());
                ibanF.setText(user.getaIban());
                blzT.setText(user.getBlz());
                amtT.setText(user.getaAmt());
                hrbT.setText(user.getaHrb());
                steuernummerT.setText(user.getSteuerNummer());
                ustIDT.setText(user.getUstId());
                streetT.setText(user.getStreet());
                townT.setText(user.getTown());
                countryT.setText(user.getCountry());
                companyNoT.setText(user.getCompanyNo());
            }
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler beim Laden der Nutzerdaten");
        }

        Button confirm = new Button("Bestätigen");
        confirm.setOnAction(e -> {
            User user2 = new User(bankF.getText(), kontonrT.getText(), blzT.getText(), steuernummerT.getText(), ustIDT.getText(),
                    nameUF.getText(), streetT.getText(), townT.getText(), countryT.getText(), companyNoT.getText(),
                    vornameUF.getText(), nachnameUF.getText(), strasseF.getText(), plzF.getText(), landF.getText(), ortF.getText(), telefonF.getText(),
                    faxF.getText(), bankF.getText(), bicF.getText(), ibanF.getText(), amtT.getText(), hrbT.getText());
            try {
                if (!sql.getCheckUserConfig()) {
                    sql.createUserConfig(user2);
                    popupStage.close();
                } else {
                    sql.updateUserConfig(user2);
                    popupStage.close();
                }
            } catch (SQLException exc) {
                ConfirmBox.display2("Fehler", "Fehler beim Erzeugen eines Users");
                System.out.println(exc.getMessage());
            }
            popupStage.close();
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

        left.getChildren().addAll(allgemeinheader, nameUnternehmen, vornameUnternehmer, nachnameUnternehmer, strasse, ort, plz, land, telefon, fax, bankheader, bank, kontonummer, bic, iban, blz, finanz, amt, hrb, steuernummer, ustID, gb, street, town, country, companyNo);
        right.getChildren().addAll(allHeaderT, vornameUF, nachnameUF, strasseF, ortF, plzF, landF, telefonF, faxF, bankHeaderT, bankF, kontonrT, bicF, ibanF, blzT, finanzT, amtT, hrbT, steuernummerT, ustIDT, gbT, streetT, townT, countryT, companyNoT);
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
