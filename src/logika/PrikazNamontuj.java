package logika;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PrikazNamontuj implements IPrikaz{
    private static final String NAZEV = "namontuj";
    private final HerniPlan plan;

    public PrikazNamontuj(HerniPlan plan){
        this.plan = plan;
    }
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Musíš zadat, co chceš namontovat";
        }
        if (parametry.length > 1) {
            return "Musíš si vybrat pouze jednu věc k namontování";
        }
        String nazevVeci = parametry[0];
        try {
            boolean povedloSeMontovat = plan.getAktualniProstor().najdiVec(nazevVeci).jeMontovatelna();

            if (plan.getAktualniProstor().obsahujeVec(nazevVeci) && povedloSeMontovat) {

                switch (nazevVeci) {
                    case "autobaterie":
                        plan.zprovozni("dedeckovoAuto");
                        Vec vec = plan.getAktualniProstor().najdiVec("autobaterie");

                        return "Namontoval jsi do auta autobaterii! Teď už by mělo nastartovat";
                    case "spoiler":
                        plan.getAktualniProstor().najdiVec("spoiler").setNamontovano(true);
                        return "Namontoval jsi na auto spoiler.";
                    case "sportovniPodvozek":
                        plan.getAktualniProstor().najdiVec("sportovniPodvozek").setNamontovano(true);
                        return "Namontoval jsi na auto sportovní podvozek.";
                    case "zavodniPneumatiky":
                        plan.getAktualniProstor().najdiVec("zavodniPneumatiky").setNamontovano(true);
                        return "Namontoval jsi na auto zavodní pneumatiky.";
                }
            }
            return nazevVeci + " se nedá namontovat";
        }
        catch (NullPointerException e){
            return nazevVeci + " se nedá namontovat";
        }
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }


}
