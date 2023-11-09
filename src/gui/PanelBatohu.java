package gui;

import logika.Batoh;
import util.Observer;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.InputStream;
import java.util.Set;

public class PanelBatohu implements Observer {

    private final VBox vbox = new VBox();
    private final FlowPane panelVeci = new FlowPane();
    private final Batoh batoh;
    public Node getPanel() {
        return vbox;
    };

    public PanelBatohu(Batoh batoh) {
        this.batoh = batoh;
        init();
        batoh.registerObserver(this);
    }

    private void init() {

        vbox.setPrefWidth(100);
        Label label = new Label("Seznam věcí:");
        vbox.getChildren().addAll(label, panelVeci);

        nactiObrazkyVeci();
    }

    @Override
    public void update() {
        nactiObrazkyVeci();
    }

    private void nactiObrazkyVeci() {
        panelVeci.getChildren().clear();

        Set<String> mnozinaVeci = batoh.getMnozinaVeci();
        for (String vec : mnozinaVeci) {
            String nazevObrazku = "/zdroje/" + vec + ".jpg";
            InputStream inputStream = PanelBatohu.class.getResourceAsStream(nazevObrazku);
            Image image = new Image(inputStream, 100, 100, false, false);
            ImageView imageView = new ImageView(image);
            panelVeci.getChildren().add(imageView);
        }
    }
}
