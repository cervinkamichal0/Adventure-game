package logika;

public class PrikazPoloz implements IPrikaz{

    private static final String NAZEV = "poloz";

    private final HerniPlan plan;

    public PrikazPoloz(HerniPlan plan){
        this.plan = plan;
    }


    @Override
    public String provedPrikaz(String... parametry) {
        if(parametry.length == 0){
            return "Musíš zadat co chceš položit";
        }

        if (parametry.length > 1){
            return "Musíš si vybrat pouze jednu věc k položení";
        }

        String nazevVeci = parametry[0];
        Vec pozadovanaVec = plan.getBatoh().vyndejZBatohu(nazevVeci);

        if (pozadovanaVec != null){

            plan.getAktualniProstor().vlozVec(pozadovanaVec);
            return nazevVeci + " jsi položil v prostoru";
        }

        return  nazevVeci + " v košíku nemáš";
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
