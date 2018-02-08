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
public class InfoBox {

    static User user = null;

    public static void display(ISql sql) {
        Stage popupStage = new Stage();

        //blockieren von aktionen
        popupStage.initModality(Modality.APPLICATION_MODAL);
        try {
            sql.checkUserConfig();
            if (!sql.getCheckUserConfig()) {
                popupStage.setTitle("Erstanmeldung");
            } else {
                popupStage.setTitle("Informationen ändern");
            }
        } catch (SQLException exc) {
            ConfirmBox.display2("Fehler", "Fehler bei Überprüfung der Erstanmeldung");
        }
        popupStage.setMinWidth(300);

        Label allgemeinheader = new Label("Allgemeine Informationen"); //HEADLINE
        allgemeinheader.setUnderline(true);
        Label nameUnternehmen = new Label("Name des Unternehmens");
        nameUnternehmen.setPrefWidth(200);
        Label vornameUnternehmer = new Label("Vorname");
        vornameUnternehmer.setPrefWidth(200);
        Label nachnameUnternehmer = new Label("Nachname");
        nachnameUnternehmer.setPrefWidth(200);
        Label strasse = new Label("Straße");
        strasse.setPrefWidth(200);
        Label ort = new Label("Ort");
        ort.setPrefWidth(200);
        Label hausnummer = new Label("Hausnummer");
        hausnummer.setPrefWidth(200);
        Label plz = new Label("Postleitzahl");
        plz.setPrefWidth(200);
        Label land = new Label("Land");
        land.setPrefWidth(200);
        Label telefon = new Label("Telefon");
        telefon.setPrefWidth(200);
        Label fax = new Label("Fax");
        fax.setPrefWidth(200);
        Label bankheader = new Label("Bankinformationen");//HEADLINE
        bankheader.setUnderline(true);
        Label bank = new Label("Bankinstitut");
        bank.setPrefWidth(200);
        Label kontonummer = new Label("Kontonummer");
        kontonummer.setPrefWidth(200);
        Label bic = new Label("BIC");
        bic.setPrefWidth(200);
        Label iban = new Label("IBAN");
        iban.setPrefWidth(200);
        Label blz = new Label("Bankleitzahl");
        blz.setPrefWidth(200);
        Label finanz = new Label("Finanzdaten"); // HEADLINE
        finanz.setUnderline(true);
        Label amt = new Label("Steueramt");
        amt.setPrefWidth(200);
        Label hrb = new Label("Handelsregisternummer");
        hrb.setPrefWidth(200);
        Label steuernummer = new Label("Steuernummer");
        steuernummer.setPrefWidth(200);
        Label ustID = new Label("UmsatzsteuerID");
        ustID.setPrefWidth(200);
        Label gb = new Label("Daten zum Rechtstyp: Limited"); //HEADLINE
        gb.setUnderline(true);
        Label street = new Label("Street");
        street.setPrefWidth(200);
        Label town = new Label("Town");
        town.setPrefWidth(200);
        Label country = new Label("Country");
        country.setPrefWidth(200);
        Label companyNo = new Label("CompanyNo");
        companyNo.setPrefWidth(200);

        final TextField allHeaderT = new TextField(); //PLATZHALTER
        allHeaderT.setVisible(false);
        final TextField nameUF = new TextField();
        nameUF.setPrefWidth(300);
        final TextField vornameUF = new TextField();
        vornameUF.setPrefWidth(300);
        final TextField nachnameUF = new TextField();
        nachnameUF.setPrefWidth(300);
        final TextField strasseF = new TextField();
        strasseF.setPrefWidth(300);
        final TextField ortF = new TextField();
        ortF.setPrefWidth(300);
        final TextField hausnummerF = new TextField();
        hausnummerF.setPrefWidth(300);
        final TextField plzF = new TextField();
        plzF.setPrefWidth(300);
        final TextField landF = new TextField();
        landF.setPrefWidth(300);
        final TextField telefonF = new TextField();
        telefonF.setPrefWidth(300);
        final TextField faxF = new TextField();
        faxF.setPrefWidth(300);
        final TextField bankHeaderT = new TextField(); //PLATZHALTER
        bankHeaderT.setVisible(false);
        final TextField bankF = new TextField();
        bankF.setPrefWidth(300);
        final TextField kontonrT = new TextField();
        kontonrT.setPrefWidth(300);
        final TextField bicF = new TextField();
        bicF.setPrefWidth(300);
        final TextField ibanF = new TextField();
        ibanF.setPrefWidth(300);
        final TextField blzT = new TextField();
        blzT.setPrefWidth(300);
        final TextField finanzT = new TextField(); // PLATZHALTER
        finanzT.setVisible(false);
        final TextField amtT = new TextField();
        amtT.setPrefWidth(300);
        final TextField hrbT = new TextField();
        hrbT.setPrefWidth(300);
        final TextField steuernummerT = new TextField();
        steuernummerT.setPrefWidth(300);
        final TextField ustIDT = new TextField();
        ustIDT.setPrefWidth(300);
        final TextField gbT = new TextField(); //PLATZHALTER
        gbT.setVisible(false);
        final TextField streetT = new TextField();
        streetT.setPrefWidth(300);
        final TextField townT = new TextField();
        townT.setPrefWidth(300);
        final TextField countryT = new TextField();
        countryT.setPrefWidth(300);
        final TextField companyNoT = new TextField();
        companyNoT.setPrefWidth(300);

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
        HBox laTe20 = new HBox();
        laTe20.setPadding(new Insets(10));
        laTe20.setSpacing(8);
        HBox laTe21 = new HBox();
        laTe21.setPadding(new Insets(10));
        laTe21.setSpacing(8);
        HBox laTe22 = new HBox();
        laTe22.setPadding(new Insets(10));
        laTe22.setSpacing(8);
        HBox laTe23 = new HBox();
        laTe23.setPadding(new Insets(10));
        laTe23.setSpacing(8);
        HBox laTe24 = new HBox();
        laTe24.setPadding(new Insets(10));
        laTe24.setSpacing(8);
        HBox laTe25 = new HBox();
        laTe25.setPadding(new Insets(10));
        laTe25.setSpacing(8);
        HBox laTe26 = new HBox();
        laTe26.setPadding(new Insets(10));
        laTe26.setSpacing(8);
        HBox laTe27 = new HBox();
        laTe27.setPadding(new Insets(10));
        laTe27.setSpacing(8);

        laTe1.getChildren().addAll(allgemeinheader, allHeaderT);
        laTe2.getChildren().addAll(nameUnternehmen, nameUF);
        laTe3.getChildren().addAll(vornameUnternehmer, vornameUF);
        laTe4.getChildren().addAll(nachnameUnternehmer, nachnameUF);
        laTe5.getChildren().addAll(strasse, strasseF);
        laTe6.getChildren().addAll(hausnummer, hausnummerF);
        laTe7.getChildren().addAll(ort, ortF);
        laTe8.getChildren().addAll(plz, plzF);
        laTe9.getChildren().addAll(land, landF);
        laTe10.getChildren().addAll(telefon, telefonF);
        laTe11.getChildren().addAll(fax, faxF);
        laTe12.getChildren().addAll(bankheader, bankHeaderT);
        laTe13.getChildren().addAll(bank, bankF);
        laTe14.getChildren().addAll(kontonummer, kontonrT);
        laTe15.getChildren().addAll(bic, bicF);
        laTe16.getChildren().addAll(iban, ibanF);
        laTe17.getChildren().addAll(blz, blzT);
        laTe18.getChildren().addAll(finanz, finanzT);
        laTe19.getChildren().addAll(amt, amtT);
        laTe20.getChildren().addAll(hrb, hrbT);
        laTe21.getChildren().addAll(steuernummer, steuernummerT);
        laTe22.getChildren().addAll(ustID, ustIDT);
        laTe23.getChildren().addAll(gb, gbT);
        laTe24.getChildren().addAll(street, streetT);
        laTe25.getChildren().addAll(town, townT);
        laTe26.getChildren().addAll(country, countryT);
        laTe27.getChildren().addAll(companyNo, companyNoT);

        VBox sum = new VBox();
        sum.getChildren().addAll(laTe1, laTe2, laTe3, laTe4, laTe5, laTe6, laTe7, laTe8, laTe9, laTe10, laTe11, laTe12, laTe13, laTe14, laTe15, laTe16, laTe17, laTe18, laTe19, laTe20, laTe21, laTe22, laTe23, laTe24, laTe25, laTe26, laTe27);

        try {
            sql.checkUserConfig();
            if (sql.getCheckUserConfig()) {
                user = sql.loadUser();
                nameUF.setText(user.getCompany());
                vornameUF.setText(user.getPreName());
                nachnameUF.setText(user.getLastName());
                strasseF.setText(user.getaStreet());
                hausnummerF.setText(user.getaHausnummer());
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
                    vornameUF.getText(), nachnameUF.getText(), strasseF.getText(), hausnummerF.getText(), plzF.getText(), landF.getText(), ortF.getText(), telefonF.getText(),
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
        HBox bottom = new HBox();
        bottom.setPadding(new Insets(10, 10, 10, 10));
        bottom.setSpacing(8);
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().addAll(back, confirm);
        border.setCenter(sum);
        border.setBottom(bottom);

        ScrollPane scroll = new ScrollPane();
        scroll.setContent(border);
        scroll.setPadding(new Insets(10));

        Scene scene = new Scene(scroll, 600, 700);
        popupStage.setScene(scene);
        popupStage.show();

    }

}
