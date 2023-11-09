package gui;

import logika.HerniPlan;
import util.Observer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class HerniPlocha implements Observer {

    private AnchorPane anchorPane = new AnchorPane();

    private Circle tecka = new Circle(10.0, Paint.valueOf("RED"));

    private HerniPlan herniPlan;

    private ImageView imageView;

    public HerniPlocha(HerniPlan plan) {
        this.herniPlan = plan;

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
    }

    private void nastavPoziciHrace() {
        AnchorPane.setLeftAnchor(tecka, herniPlan.getAktualniProstor().getPosLeft());
        AnchorPane.setTopAnchor(tecka, herniPlan.getAktualniProstor().getPosTop());
    }
}