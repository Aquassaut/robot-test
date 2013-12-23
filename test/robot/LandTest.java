package robot;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Created by aquassaut on 12/2/13
 */
public class LandTest {
    @Rule
    public ExpectedException e = ExpectedException.none();

    @Test
    public void testGetLandByOrdinal() throws Exception {
        Land l;
        l = Land.getLandByOrdinal(0);
        Assert.assertEquals("On devrait avoir de la terre",Land.Terre, l);
        l = Land.getLandByOrdinal(1);
        Assert.assertEquals("On devrait avoir de la roche",Land.Roche, l);
        l = Land.getLandByOrdinal(2);
        Assert.assertEquals("On devrait avoir du sable",Land.Sable, l);
        l = Land.getLandByOrdinal(3);
        Assert.assertEquals("On devrait avoir de la boue",Land.Boue, l);
        l = Land.getLandByOrdinal(4);
        Assert.assertEquals("On devrait avoir de l'infranchissable",Land.Infranchissable, l);


        e.expect(TerrainNonRepertorieException.class);
        Land.getLandByOrdinal(-1);
        Land.getLandByOrdinal(18);
    }

    @Test
    public void testCountLand() throws Exception {
        Assert.assertEquals("Ca devrait être 5", 5, Land.CountLand());
    }
}
