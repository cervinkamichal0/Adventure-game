package logika;

import java.util.HashSet;
import java.util.Set;

public class Batoh {
    private final int omezeniBatohu;
    private Set<Vec> obsahBatohu;

    public Batoh(int omezeniBatohu){
        this.omezeniBatohu = omezeniBatohu;
        obsahBatohu = new HashSet<>();
    }

    public boolean vlozDoBatohu(Vec neco){
        if(this.obsahBatohu.size()< omezeniBatohu && neco.jePrenositelna()) {
            obsahBatohu.add(neco);
            return true;
        }
        return false;
    }

    public Vec vyndejZBatohu(String nazevVeci){
        for (Vec neco: obsahBatohu){
            if (neco.getNazev().equals(nazevVeci)){
                this.obsahBatohu.remove(neco);
                return neco;
            }
        }
        return null;
    }
    public Vec najdiVBatohu(String nazevVeci){
        for (Vec neco: obsahBatohu){
            if (neco.getNazev().equals(nazevVeci)){
                return neco;
            }
        }
        return null;
    }
    public String dlouhyPopis(){
        String vypis = "Obsah batohu: ";
        for(Vec neco: obsahBatohu){
            vypis += neco.getNazev() + " ";
        }
        return vypis;
    }

    public Set<Vec> getObsahBatohu() {
        return obsahBatohu;
    }
}
