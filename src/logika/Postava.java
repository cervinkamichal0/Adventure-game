package logika;

import java.util.ArrayList;
import java.util.List;

public class Postava {
    private String jmeno;
    private String uvitani;
    private List<Vec> potrebneVeci = new ArrayList<>();
    private List<Vec> veciProHrace = new ArrayList<>();
    private String monolog;
    private Prostor prostorKOdemknuti;


    public Postava(String jmeno, String uvitani, String monolog) {
        this.jmeno = jmeno;
        this.uvitani = uvitani;
        this.monolog = monolog;
    }

    public String getJmeno(){
        return jmeno;
    }
    public String getUvitani(){
        return uvitani;
    }
    public String getMonolog(){
        return monolog;
    }
    public void vlozPotrebnouVec(Vec vec){
        potrebneVeci.add(vec);
    }

    public List<Vec> getPotrebneVeci() {
        return potrebneVeci;
    }

    public List<Vec> getVeciProHrace() {
        return veciProHrace;
    }

    public void vlozVecProHrace(Vec vecProHrace) {
        veciProHrace.add(vecProHrace);
    }
    public void setProstorKOdemknuti(Prostor prostor){
        prostorKOdemknuti = prostor;
    }

    public Prostor getProstorKOdemknuti() {
        return prostorKOdemknuti;
    }
}
