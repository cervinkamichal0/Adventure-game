package gui;

import javafx.scene.control.TextArea;
import logika.HerniPlan;
import logika.Prostor;
import util.Observer;
import logika.IHra;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class PanelVychodu implements Observer {

    final HerniPlan herniPlan;
    private IHra hra;
    ListView<String> listView = new ListView<>();
    ObservableList<String> vychody = FXCollections.observableArrayList();

    public PanelVychodu(IHra hra, TextArea konzole) {

        this.herniPlan = hra.getHerniPlan();
        this.hra = hra;

        herniPlan.registerObserver(this);
        nactiVychodyAktualnihoProstoru();

        listView.setItems(vychody);
        listView.setPrefWidth(120);
        listView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                String vychod = listView.getSelectionModel().getSelectedItem();
                String prikaz = hra.zpracujPrikaz("jdi " + vychod);
                konzole.appendText("\n" + prikaz + "\n");
            }
        });


        konzole.setEditable(false);
    }

    public ListView<String> getListView() {
        return listView;
    }

    @Override
    public void update() {
        nactiVychodyAktualnihoProstoru();
    }

    private void nactiVychodyAktualnihoProstoru() {
        vychody.clear();
        Prostor aktualniProstor = herniPlan.getAktualniProstor();
        for (Prostor prostor : aktualniProstor.getVychody()) {
            vychody.add(prostor.getNazev());
        }
    }
}
