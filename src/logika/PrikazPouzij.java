package logika;

import java.util.HashSet;
import java.util.Set;
public class PrikazPouzij implements IPrikaz {
    private static final String NAZEV = "pouzij";
    private final HerniPlan plan;

    public PrikazPouzij(HerniPlan plan){
        this.plan = plan;
    }
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Musíš zadat, co chceš použít";
        }
        if (parametry.length > 1) {
            return "Musíš si vybrat pouze jednu věc k použití";
        }
        String nazevVeci = parametry[0];
        String obsahBatohu = plan.getBatoh().dlouhyPopis();

        if (plan.getAktualniProstor().obsahujeVec(nazevVeci) || obsahBatohu.contains(nazevVeci)) {


            switch (nazevVeci){
                case "kartacek":
                    plan.odemkni("jídelna");
                    return "Vyčistil jsi si zuby, ale pořád ti kručí v břiše :(.";
                case "snidane":
                    plan.odemkni("dedeckovoGaraz");
                    return "Snědl jsi snídani, teď jsi připravený vyrazit!";
                case "kliceOdAuta":
                    if(plan.jeFunkcni("dedeckovoAuto") == false){
                        return "Jejda. auto se neodemyká. Asi je něco špatně.";
                    }
                    else {
                        plan.odemkni("dedeckovoAuto");
                        return "Už to funguje! Auto se odemklo, můžeš nastoupit.";
                    }
                case"zavodniSimulator":
                    plan.odemkni("závod");
                    return "Natrénoval jsi si trať na závod. Teď už jen zbývá vydat se zkusit své štěstí na opravdový závod.";
                case "helma":
                    plan.getBatoh().najdiVBatohu("helma").setPouzito(true);
                    return "nasadil jsi si helmu.";
                case "zavodniKombineza":
                    plan.getBatoh().najdiVBatohu("zavodniKombineza").setPouzito(true);
                    return "oblékl jsi si zavodni kombinázu.";
            }

                return nazevVeci + " se nedá použít";
        }
        return "Tato věc tu není a ani ji nemáš v batohu.";
    }
        @Override
        public String getNazev () {
            return NAZEV;
        }
    }

