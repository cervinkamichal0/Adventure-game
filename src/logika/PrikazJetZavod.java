package logika;

import java.util.concurrent.TimeUnit;

public class PrikazJetZavod implements IPrikaz {
    private static final String NAZEV = "jetZavod";
    private final Hra hra;

    public PrikazJetZavod(Hra hra){
        this.hra = hra;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        if (hra.getHerniPlan().getBatoh().najdiVBatohu("helma").jePouzito() == false || hra.getHerniPlan().getBatoh().najdiVBatohu("zavodniKombineza").jePouzito() == false){
            return "Takhle tě na závod nepustí. Musíš používát ochranné prvky.";
        }

        if (hra.getHerniPlan().getAktualniProstor().getNazev() == "závod"){
            System.out.println("Závod se odstratoval..." + "\n");
            try {
                System.out.println("." + "\n");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("....." + "\n");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("......" + "\n");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("........" + "\n");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("........." + "\n");
                TimeUnit.SECONDS.sleep(1);
                System.out.println(".........." + "\n");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            hra.setKonecHry(true);
            return "Vyhrál jsi závod! Dědeček by na tebe byl pyšný.";

        }
        else{
            return "Tento příkaz můžeš použit jen na závodě";
        }


    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
