package logika;

import java.util.Set;

/**
 *  Třída PrikazJdi implementuje pro hru příkaz jdi.
 *  Tato třída je součástí jednoduché textové hry.
 *  

 */
public class PrikazJdi implements IPrikaz {
    private static final String NAZEV = "jdi";
    private HerniPlan plan;
    private Hra hra;
    private Boolean jeZamceno;
    
    /**
    *  Konstruktor třídy
    *  
    *  @param hra, abychom věděli kam chodit a mohli hru ukončit
    */    
    public PrikazJdi(Hra hra) {
        this.hra = hra;
        this.plan = hra.getHerniPlan();
    }

    /**
     *  Provádí příkaz "jdi". Zkouší se vyjít do zadaného prostoru. Pokud prostor
     *  existuje, vstoupí se do nového prostoru. Pokud zadaný sousední prostor
     *  (východ) není, vypíše se chybové hlášení.
     *
     *@param parametry - jako  parametr obsahuje jméno prostoru (východu),
     *                         do kterého se má jít.
     *@return zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Kam mám jít? Musíš zadat jméno východu";
        }

        String smer = parametry[0];

        // zkoušíme přejít do sousedního prostoru
        Prostor sousedniProstor = plan.getAktualniProstor().vratSousedniProstor(smer);

        if (sousedniProstor == null) {
            return "Tam se odsud jít nedá!";
        }
        else {
            if (sousedniProstor.jeZamceno()) {
                return "dveře do místnosti "+sousedniProstor.getNazev()
                        +" jsou zamčené";
            }

            String obsahBatohu = plan.getBatoh().dlouhyPopis();
            boolean maKlice = obsahBatohu.contains("kliceOdAuta");
            if(smer.equals("dedeckovoAuto") && maKlice == false){
                return "Měl by sis s sebou nejdřív vzít klíče od auta";
            }
            plan.setAktualniProstor(sousedniProstor);
            if (sousedniProstor.getSeznamPostav().isEmpty() == false){
                String uvitaniPostav = "";
                for (Postava postava : sousedniProstor.getSeznamPostav()){
                    uvitaniPostav += postava.getJmeno() +": " +postava.getUvitani() + "\n";
                }
                return "Vstoupil jsi do prostoru " + sousedniProstor.dlouhyPopis() +" \n\n" +uvitaniPostav;
            }

            return sousedniProstor.dlouhyPopis();
        }
    }

    
    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

    public Boolean getJeZamceno() {
        return jeZamceno;
    }

    public void setJeZamceno(Boolean jeZamceno) {
        this.jeZamceno = jeZamceno;
    }
}
