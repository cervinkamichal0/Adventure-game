package logika;

import java.util.ArrayList;
import java.util.List;

public class PrikazMluv implements IPrikaz {
    private static final String NAZEV = "mluv";
    private final HerniPlan plan;
    private final Hra hra;

    public PrikazMluv(HerniPlan plan, Hra hra){
        this.hra = hra;
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Musíš zadat, s kýmy chceš mluvit";
        }
        if (parametry.length > 1) {
            return "Musíš si vybrat pouze jednu postavu, s kterou chceš mluvit";
        }
        String postava = parametry[0];

        if (plan.getAktualniProstor().containsPostava(postava)) {
            Postava aktualniPostava = plan.getAktualniProstor().najdiPostavu(postava);
            List<Vec> seznamPotrebnychVeci = aktualniPostava.getPotrebneVeci();

            List<Vec> seznamNamontovanychVeci = new ArrayList<>();
            for (Vec vec : plan.getAktualniProstor().getSeznamVeci())
            {
                if(vec.jeNamontovano()){
                    seznamNamontovanychVeci.add(vec);
                }

            }
            for(Vec vec : seznamPotrebnychVeci) {
                if (seznamNamontovanychVeci.contains(vec) == false) {
                    return aktualniPostava.getUvitani();
                }
            }
            for (Vec vec : aktualniPostava.getVeciProHrace()) {
                plan.getBatoh().vlozDoBatohu(vec);
            }
            aktualniPostava.getProstorKOdemknuti().setZamceno(false);
            return aktualniPostava.getMonolog();
        }

        return postava + " neni v prostoru";

    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
