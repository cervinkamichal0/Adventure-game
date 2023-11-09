package logika;

public class Vec {

    private String nazev;
    private boolean prenositelnost;
    private boolean pouzitelnost;
    private boolean montovatelnost;
    public boolean namontovano = false;
    public boolean pouzito = false;

    /**
     *
     * @param nazev
     * @param prenositelnost
     */
    public Vec(String nazev, boolean prenositelnost, boolean pouzitelnost,boolean montovatelnost) {
        this.nazev = nazev;
        this.prenositelnost = prenositelnost;
        this.pouzitelnost = pouzitelnost;
        this.montovatelnost = montovatelnost;
    }

    public String getNazev(){
        return  nazev;
    }

    public boolean jePrenositelna(){
        return prenositelnost;
    }
    public boolean jePouzitelna(){return pouzitelnost;}
    public boolean jeMontovatelna(){return montovatelnost;}

    public boolean jeNamontovano() {
        return namontovano;
    }
    public void setNamontovano(boolean namontovano) {
        this.namontovano = namontovano;
    }

    public boolean jePouzito() {
        return pouzito;
    }

    public void setPouzito(boolean pouzito) {
        this.pouzito = pouzito;
    }
}
