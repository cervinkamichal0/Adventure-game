import logika.Hra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HraTest {
    private Hra hra;

    @BeforeEach
    public void setUp(){
        hra = new Hra();
    }

    @Test
    public void testHry(){

        //Úvod
        assertEquals("Vítejte!\n" +
                "Toto je příběh o Červené Karkulce, babičce a vlkovi.\n" +
                "Napište 'nápověda', pokud si nevíte rady, jak hrát dál.\n" +
                "\n" +
                "Jsi v mistnosti/prostoru domeček, ve kterém bydlí Karkulka.\n" +
                "východy: les",hra.vratUvitani());

        //1. krok JDI les
        assertEquals("Jsi v mistnosti/prostoru les s jahodami, malinami a pramenem vody.\n" +
                "východy: studna domeček hluboký_les", hra.zpracujPrikaz("jdi les"));
        // 2. - 18. - +++

        //konec Hry
        assertTrue(hra.konecHry());
        assertEquals("super vyhrál jsi", hra.vratEpilog());



        /*
        Úvod
        "Textace"
        "Aktuální prostor"
        "Aktuální předměty v místnosti"
        "Aktuální východy"
        "Aktuální předměty v batohu"
*/


    }
}
