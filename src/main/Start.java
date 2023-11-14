/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package main;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import logika.*;
import uiText.TextoveRozhrani;
import gui.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Random;

/*******************************************************************************
 * Třída  Start je hlavní třídou projektu,
 * který představuje jednoduchou textovou adventuru určenou k dalším úpravám a rozšiřování
 *

 */
public class Start extends Application
{
    private  final IHra hra = new Hra();
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

        Menu soubor = new Menu("Soubor");
        MenuItem novaHra = new MenuItem("Nová hra");
        Menu tajne = new Menu("Tajné");
        tajne.setVisible(false);
        MenuItem teleport = new MenuItem("Teleport");



        novaHra.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        novaHra.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage newStage = new Stage();
                try {
                    Start start = new Start();
                    start.start(newStage);
                    primaryStage.close();
                } catch (Exception e) {
                    e.printStackTrace();
            }

        }});

        soubor.getItems().add(novaHra);
        SeparatorMenuItem separator = new SeparatorMenuItem();
        soubor.getItems().add(separator);
        MenuItem konecHry = new MenuItem("Konec hry");
        konecHry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        soubor.getItems().add(konecHry);
        Menu mNapoveda = new Menu("Nápověda");
        MenuItem mINapoveda = new MenuItem("Nápověda");
        MenuItem mIOHre = new MenuItem("O hře");
        Alert aNapoveda = new Alert(Alert.AlertType.INFORMATION);
        aNapoveda.setContentText("Můžeš zadat tyto příkazy:\n"
                + "konec; jdi; pouzij; seber; namontuj; mluv; jetZavod; poloz; pomoc"
                +"\n"
                +"Nacháziš se v: " + hra.getHerniPlan().getAktualniProstor().dlouhyPopis());

        mINapoveda.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                aNapoveda.show();
            }
        });
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        Stage webStage = new Stage();
        Scene webviewScene = new Scene(webView,600,600);
        mIOHre.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                webEngine.load(getClass().getResource("/OHre.html").toString());
                webStage.setScene(webviewScene);
                webStage.setTitle("O hře");
                webStage.show();

            }
        });

        mNapoveda.getItems().add(mINapoveda);
        mNapoveda.getItems().add(mIOHre);
        tajne.getItems().add(teleport);


        MenuBar menuBar = new MenuBar(soubor, mNapoveda);


        HerniPlocha herniPlocha = new HerniPlocha(hra.getHerniPlan(),tajne);
        VBox herniPlochaAndMenuBar = new VBox(menuBar,herniPlocha.getAnchorPane());
        herniPlochaAndMenuBar.setAlignment(Pos.CENTER);
        menuBar.getMenus().add(tajne);
        HashSet<Prostor> seznamProstoru = hra.getHerniPlan().getSeznamProstoru();

        teleport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int random = new Random().nextInt(seznamProstoru.size()); // Random
                hra.getHerniPlan().setAktualniProstor(seznamProstoru.toArray(new Prostor[0])[random]);
            }
        });


        borderPane.setTop(herniPlochaAndMenuBar);



        TextArea konzole = new TextArea();
        PanelVychodu panelVychodu = new PanelVychodu(hra,konzole);
        ListView<String> listView = panelVychodu.getListView();
        borderPane.setRight(listView);



        borderPane.setCenter(konzole);
        konzole.setText(hra.vratUvitani());
        konzole.setEditable(false);

        Label popisekKonzole = new Label("ZADEJTE PŘÍKAZ:");


        TextField uzivatelskyVstup = new TextField();
        HBox spodniBox = new HBox(popisekKonzole, uzivatelskyVstup);
        spodniBox.setSpacing(5);

        spodniBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        HBox.setHgrow(popisekKonzole, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(uzivatelskyVstup, javafx.scene.layout.Priority.ALWAYS);

        spodniBox.setPadding(new javafx.geometry.Insets(5));


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

        Scene scene = new Scene(borderPane, 600, 900);
        uzivatelskyVstup.requestFocus();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

