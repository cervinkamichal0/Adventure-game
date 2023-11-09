package logika;

import util.Observer;
import util.SubjectOfChange;

import java.util.HashSet;
import java.util.Set;

/**
 *  Class HerniPlan - třída představující mapu a stav adventury.
 * 
 *  Tato třída inicializuje prvky ze kterých se hra skládá:
 *  vytváří všechny prostory,
 *  propojuje je vzájemně pomocí východů 
 *  a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *

 */
public class HerniPlan implements SubjectOfChange{

    private Prostor aktualniProstor;

    private HashSet<Prostor> seznamProstoru = new HashSet<>();
    private final Set<Observer> observers = new HashSet<>();

    private Batoh batoh;
    private static final int OMEZENI_BATOHU = 4;

    /**
     * Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     * Jako výchozí aktuální prostor nastaví halu.
     */
    public HerniPlan() {
        zalozProstoryHry();

    }

    /**
     * Vytváří jednotlivé prostory a propojuje je pomocí východů.
     * Jako výchozí aktuální prostor nastaví domeček.
     */
    private void zalozProstoryHry() {
        // vytvářejí se jednotlivé prostory
        Prostor loznice = new Prostor("ložnice", "místnost, ve které se proubíš",60.0, 120.0);
        Prostor koupelna = new Prostor("koupelna", "koupelna v děděčkovo domě", 320.0, 80.0);
        Prostor jidelna = new Prostor("jídelna", "jídelna v dědečkovo domě", 230.0, 190.0);
        Prostor dedeckovoGaraz = new Prostor("dedeckovoGaraz", "Garáž, ve které se nachází dědečkovo auto", 145.0, 80.0);
        Prostor dedeckovoAuto = new Prostor("dedeckovoAuto", "Dědečkovo auto, se kterým musíš vyhrát závod",230.0, 120.0);
        Prostor simulator = new Prostor("simulátor", "Místo, kde můžeš trénovat na závod",230.0, 120.0);
        Prostor autodilna = new Prostor("autodílna", "studna a zde se můžeš napít",230.0, 120.0);
        Prostor zavod = new Prostor("závod", "Místo, kde se uskuteční závod a je možné zde vyhrát hru",230.0, 120.0);

        seznamProstoru.add(loznice);
        seznamProstoru.add(koupelna);
        seznamProstoru.add(jidelna);
        seznamProstoru.add(dedeckovoGaraz);
        seznamProstoru.add(dedeckovoAuto);
        seznamProstoru.add(simulator);
        seznamProstoru.add(autodilna);
        seznamProstoru.add(zavod);

        // přiřazují se průchody mezi prostory (sousedící prostory)
        loznice.setVychod(koupelna);
        loznice.setVychod(jidelna);
        loznice.setVychod(dedeckovoGaraz);
        koupelna.setVychod(loznice);
        koupelna.setVychod(dedeckovoGaraz);
        jidelna.setVychod(dedeckovoGaraz);
        jidelna.setVychod(loznice);
        dedeckovoGaraz.setVychod(dedeckovoAuto);
        dedeckovoAuto.setVychod(dedeckovoGaraz);
        dedeckovoAuto.setVychod(simulator);
        dedeckovoAuto.setVychod(autodilna);
        dedeckovoAuto.setVychod(zavod);
        simulator.setVychod(dedeckovoAuto);
        autodilna.setVychod(dedeckovoAuto);
        zavod.setVychod(dedeckovoAuto);


        //PŘIDÁNÍ VÝHERNÍHO PROSTORU
        jidelna.setZamceno(true);
        dedeckovoGaraz.setZamceno(true);
        dedeckovoAuto.setZamceno(true);
        simulator.setZamceno(true);
        zavod.setZamceno(true);
        dedeckovoAuto.setFunkcni(false);

        jidelna.vlozVec(new Vec("snidane", false, true, false));

        koupelna.vlozVec(new Vec("kartacek", false, true, false));

        dedeckovoGaraz.vlozVec(new Vec("kliceOdAuta", true, true, false));
        dedeckovoGaraz.vlozVec(new Vec("autobaterie", false, false, true));

        Vec spoiler = new Vec("spoiler", false, false, true);
        Vec sportovniPodvozek = new Vec("sportovniPodvozek", false, false, true);
        Vec zavodniPneumatiky = new Vec("zavodniPneumatiky", false, false, true);
        autodilna.vlozVec(spoiler);
        autodilna.vlozVec(sportovniPodvozek);
        autodilna.vlozVec(zavodniPneumatiky);

        simulator.vlozVec(new Vec("zavodniSimulator", false, true, false));

        Postava pablo = new Postava("Pablo", "Vítej v autodílně! Mám tu pro tebe připravené všechny díly, které na závod budeš potřebovat. Stači je namontovat. Až to budeš mít, ozvi se mi.", "To vypadá dobře. Do batohu jsem ti dal věci, které budeš potřebovat na závodění. Můžeš vyrazit trénovat na simulátor!");
        pablo.vlozPotrebnouVec(spoiler);
        pablo.vlozPotrebnouVec(sportovniPodvozek);
        pablo.vlozPotrebnouVec(zavodniPneumatiky);
        pablo.vlozVecProHrace(new Vec("helma", true, true, false));
        pablo.vlozVecProHrace(new Vec("zavodniKombineza",true,true,false));
        pablo.setProstorKOdemknuti(simulator);
        autodilna.vlozPostavu(pablo);



        batoh = new Batoh(OMEZENI_BATOHU);


        aktualniProstor = loznice;  // hra začíná v domečku

    }

    /**
     * Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     * @return aktuální prostor
     */

    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }

    /**
     * Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory
     *
     * @param prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
        aktualniProstor = prostor;
        notifyObservers();
    }



    public void odemkni(String nazevProstoru) {
        for (Prostor prostor : seznamProstoru) {
            if (prostor.getNazev().equals(nazevProstoru)) {
                prostor.setZamceno(false);
            }
        }

    }

    public void zprovozni(String nazevProstoru) {
        for (Prostor prostor : seznamProstoru) {
            if (prostor.getNazev().equals(nazevProstoru)) {
                prostor.setFunkcni(true);
            }
        }
    }

    public boolean jeFunkcni(String nazevProstoru) {
        for (Prostor prostor : seznamProstoru) {
            if (prostor.getNazev().equals(nazevProstoru)) {
                return prostor.jeFunkcni();
            }
        }
        throw new RuntimeException("Prostor " + nazevProstoru + " neexistuje");
    }

    public Batoh getBatoh() {
        return batoh;
    }

    public Vec najdiVecVBatohuAProstoru(String nazevVeci) {
        for (Vec vec : getBatoh().getObsahBatohu()) {
            if (vec.getNazev().equals(nazevVeci)) {
                return vec;
            }
        }
        for (Vec vec : aktualniProstor.getSeznamVeciVProstoru())
            if (vec.getNazev().equals(nazevVeci)) {
                return vec;
            }
        return null;
    }

    public void namontuj(String nazevVeci) {
        for (Vec vec : aktualniProstor.getSeznamVeciVProstoru()) {
            if (vec.getNazev().equals(nazevVeci)) {
                vec.setNamontovano(true);
            }
        }

    }
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }

    }
}
