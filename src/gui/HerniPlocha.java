package gui;

import logika.HerniPlan;
import util.Observer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
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

public class HerniPlocha implements Observer {

    private AnchorPane anchorPane = new AnchorPane();

    private Circle tecka = new Circle(10.0, Paint.valueOf("RED"));

    private HerniPlan herniPlan;

    private ImageView imageView;
    private  Menu tajnyItem;

    public HerniPlocha(HerniPlan plan, Menu tajnyItem) {
        this.herniPlan = plan;
        this.tajnyItem = tajnyItem;
        Image image = new Image(HerniPlocha.class.getClassLoader().getResourceAsStream("herniPlan.png"), 476, 458,
                false, false);
        imageView = new ImageView(image);


        nastavPoziciHrace();

        anchorPane.getChildren().addAll(imageView, tecka);
        herniPlan.registerObserver(this);
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    @Override
    public void update() {
        nastavPoziciHrace();
        if (herniPlan.isUkazMenu()) {
            tajnyItem.setVisible(true);
        }
    }
    private void nastavPoziciHrace() {
        AnchorPane.setLeftAnchor(tecka, herniPlan.getAktualniProstor().getPosLeft());
        AnchorPane.setTopAnchor(tecka, herniPlan.getAktualniProstor().getPosTop());
    }
}