package logika;

public class PrikazIdkfa  implements IPrikaz {
    private static final String NAZEV = "idkfa";

    private final HerniPlan plan;

    public PrikazIdkfa(HerniPlan plan){
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        for (Vec vec : plan.getSeznamVeci()) {
            if (vec.jePrenositelna()){
                plan.getBatoh().vlozDoBatohu(vec);
            }
        }
        return "Sebral jsi všechny věci ve hře";
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
