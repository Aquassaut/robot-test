package robot;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Created with IntelliJ IDEA.
 * User: aquassaut
 * Date: 12/2/13
 * Time: 9:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class LandTest {
    @Rule
    public ExpectedException e = ExpectedException.none();

    public void testGetLandByOrdinal() throws Exception {
        Assert.assertEquals("On devrait avoir de l'infranchissable",Land.Infranchissable, Land.getLandByOrdinal(0));
        Assert.assertEquals("On devrait avoir de la terre",Land.Terre, Land.getLandByOrdinal(1));
        Assert.assertEquals("On devrait avoir de la roche",Land.Roche, Land.getLandByOrdinal(2));
        Assert.assertEquals("On devrait avoir de la boue",Land.Boue, Land.getLandByOrdinal(3));
        Assert.assertEquals("On devrait avoir du sable",Land.Sable, Land.getLandByOrdinal(4));

        e.expect(TerrainNonRepertorieException.class);
        Land.getLandByOrdinal(-1);
        Land.getLandByOrdinal(18);
    }

    @Test
    public void testCountLand() throws Exception {
        Assert.assertEquals("Ca devrait être 5", 5, Land.CountLand());
    }
}
