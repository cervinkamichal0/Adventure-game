package logika;

public class PrikazSeber implements IPrikaz{
    private static final String NAZEV = "seber";

    private final HerniPlan plan;

    public PrikazSeber(HerniPlan plan){
        this.plan = plan;
    }


    @Override
    public String provedPrikaz(String... parametry) {
        if(parametry.length == 0){
            return "Co mám sebrat? Musíš zadat název předmětu.";
        }

        if(parametry.length > 1){
            return "Moc věcí k sebrání. Prosím vyber si jednu";
        }

        String nazevVeci = parametry[0];

        if (plan.getAktualniProstor().obsahujeVec(nazevVeci)){
            Vec pozadovanaVec = plan.getAktualniProstor().vyberVec(nazevVeci);
            if (pozadovanaVec == null){
                return  nazevVeci + " nedá se sebrat";
            }

            boolean povedloSeVlozit = plan.getBatoh().vlozDoBatohu(pozadovanaVec);
            if(povedloSeVlozit){
                return "Sebral jsi " + nazevVeci;
            }

            plan.getAktualniProstor().vlozVec(pozadovanaVec);
            return nazevVeci + " se snažíš nacpat do plného košíčku";


        }
        return nazevVeci + " není v prostoru";
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
