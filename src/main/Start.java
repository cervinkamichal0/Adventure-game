/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package main;

import javafx.scene.control.*;
import logika.*;
import uiText.TextoveRozhrani;
import gui.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/*******************************************************************************
 * Třída  Start je hlavní třídou projektu,
 * který představuje jednoduchou textovou adventuru určenou k dalším úpravám a rozšiřování
 *

 */
public class Start extends Application
{
    private final IHra hra = new Hra();
    /***************************************************************************
     * Metoda, prostřednictvím níž se spouští celá aplikace.
     *
     * @param args Parametry příkazového řádku
     */
    public static void main(String[] args)
    {
        if (args.length > 0 && args[0].equals("-gui")) {
            Application.launch();
        } else {
            IHra hra = new Hra();
            TextoveRozhrani ui = new TextoveRozhrani(hra);
            ui.hraj();
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        MenuBar menuBar = new MenuBar();

        Menu menu = new Menu("Soubor");

        MenuItem novaHraMenuItem = new MenuItem("Nová hra");
        MenuItem napovedaMenuItem = new MenuItem("Nápověda");
        menu.getItems().addAll(novaHraMenuItem, napovedaMenuItem);
        menuBar.getMenus().add(menu);
        VBox herniPlochaAndMenuBar = new VBox();

        HerniPlocha herniPlocha = new HerniPlocha(hra.getHerniPlan());
        herniPlochaAndMenuBar.getChildren().addAll(menuBar,herniPlocha.getAnchorPane());

        borderPane.setTop(herniPlochaAndMenuBar);
        TextArea konzole = new TextArea();
        PanelVychodu panelVychodu = new PanelVychodu(hra,konzole);
        ListView<String> listView = panelVychodu.getListView();
        borderPane.setRight(listView);



        borderPane.setCenter(konzole);
        konzole.setText(hra.vratUvitani());
        konzole.setEditable(false);

        Label popisek = new Label("Zadej příkaz: ");

        popisek.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        TextField uzivatelskyVstup = new TextField();
        HBox spodniBox = new HBox(popisek, uzivatelskyVstup);

        spodniBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        HBox.setHgrow(popisek, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(uzivatelskyVstup, javafx.scene.layout.Priority.ALWAYS);

        spodniBox.setPadding(new javafx.geometry.Insets(5));
        spodniBox.setStyle("-fx-background-color: lightgray;");

        borderPane.setBottom(spodniBox);

        PanelBatohu panelBatohu = new PanelBatohu(hra.getBatoh());
        borderPane.setLeft(panelBatohu.getPanel());
        uzivatelskyVstup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String prikaz = uzivatelskyVstup.getText();
                konzole.appendText("\n" + prikaz + "\n");
                String odpoved = hra.zpracujPrikaz(prikaz);
                konzole.appendText("\n" + odpoved + "\n");

                uzivatelskyVstup.clear();

                if (hra.konecHry()) {
                    uzivatelskyVstup.setEditable(false);
                }
            }
        });

        Scene scene = new Scene(borderPane, 600, 450);
        uzivatelskyVstup.requestFocus();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

