package gui;

import logika.HerniPlan;
import logika.Prostor;
import logika.IHra;
import org.w3c.dom.Text;
import util.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class PanelVychodu implements Observer {

    final HerniPlan herniPlan;
    ListView<String> listView = new ListView<>();
    ObservableList<String> vychody = FXCollections.observableArrayList();

    public PanelVychodu(HerniPlan herniPlan) {
        this.herniPlan = herniPlan;

        nactiVychodyAktualnihoProstoru();

        listView.setItems(vychody);
        listView.setPrefWidth(120);

        herniPlan.registerObserver(this);
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
