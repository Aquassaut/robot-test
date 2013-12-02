package robot;

import org.junit.Test;
import org.junit.Rule;
import org.junit.Assert;
import org.junit.rules.ExpectedException;


/**
 * Created with IntelliJ IDEA.
 * User: aquassaut
 * Date: 11/27/13
 * Time: 1:35 PM
 */
public class BatteryTest {

    //PARTIE STRUCTURELLE

    @Rule
    public ExpectedException e = ExpectedException.none();

    @Test
    public void testGetChargeLevel() throws Exception {
        Battery b = new Battery();
        Assert.assertEquals(
                "A l'état initial, le niveau de charge devrait être 100",
                100,
                b.getChargeLevel(),
                0.001
        );
    }

    @Test
    public void testCharge() throws Exception {
        Battery b = new Battery();
        b.charge();
        Assert.assertEquals(
            "Après une charge, le niveau de charge devrait être 110",
            110,
            b.getChargeLevel(),
            0.001
        );
    }

    @Test
    public void testSetUp() throws Exception {
        //je sais pas trop comment tester le timer, ça a l'air tendu
    }

    @Test
    public void testUse() throws Exception {
        Battery b = new Battery();
        b.use(100);
        Assert.assertEquals(
            "Après avoir utilisé 100 de charge, le niveau devrait être 0",
            0,
            b.getChargeLevel(),
            0.001
        );
        e.expect(InsufficientChargeException.class);
        b.use(150);
    }

    @Test
    public void testTimeToSufficientCharge() throws Exception {
        Battery b = new Battery();
        Assert.assertEquals(
            "Il ne devrait pas falloir de temps de chargement pour " +
            "arriver au niveau actuel",
            0,
            b.timeToSufficientCharge(100)
        );
        Assert.assertEquals(
            "Il devrait falloir 10000 unités de temps pour arriver " +
            "a une charge de 200, en partant de 100",
            10000,
            b.timeToSufficientCharge(200)
        );
    }

    @Test
    public void testCanDeliver() throws Exception {
        Battery b = new Battery();
        Assert.assertFalse(
            "On ne devrait pas pouvoir fournir plus de 100 de charge",
            b.canDeliver(101)
        );
        Assert.assertTrue(
            "On devrait pouvoir fournir moins de 100 de charge",
            b.canDeliver(99)
        );
    }
    //FIN PARTIE STRUCTURELLE
}