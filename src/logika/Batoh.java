package logika;

import util.Observer;
import util.SubjectOfChange;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Batoh implements SubjectOfChange{
    private final int omezeniBatohu;
    private Set<Vec> obsahBatohu;
    private Map<String, Vec> seznamVeci ;
    private final Set<Observer> observers = new HashSet<>();

    public Batoh(int omezeniBatohu){
        this.omezeniBatohu = omezeniBatohu;
        obsahBatohu = new HashSet<>();
    }
    public Batoh () {
        seznamVeci = new HashMap<String, Vec>();
        omezeniBatohu = 5;
    }

    public boolean vlozDoBatohu(Vec neco){
        if(this.obsahBatohu.size()< omezeniBatohu && neco.jePrenositelna()) {
            obsahBatohu.add(neco);
            notifyObservers();
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
    public Set<String> getMnozinaVeci() {
        return seznamVeci.keySet();
    }
    @Override
    public void registerObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }

    }
}
