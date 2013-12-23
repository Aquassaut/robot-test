package robot;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by aquassaut on 12/23/13.
 */
public class MapToolsTest {
    private Direction n, s, e, w;
    private Coordinates c;
    private MapTools m;

    public MapToolsTest() {
        m = new MapTools(); //Coverage, petit petit !
        Assert.assertTrue("L'instanciation se passe pas trop mal (PLUS DE COVERAGE)", m instanceof MapTools);
        n = Direction.NORTH;
        s = Direction.SOUTH;
        w = Direction.WEST;
        e = Direction.EAST;
        c = new Coordinates(0, 0);
    }

    @Test
    public void testNextForwardPosition() throws Exception {
        Coordinates d;
        d = MapTools.nextForwardPosition(c, n);
        Assert.assertEquals( "Pas de déplaceMapTools.nt vertical", d.getX(), c.getX());
        Assert.assertEquals( "On devrait MapTools.nter de 1", d.getY(), c.getY() - 1);

        d = MapTools.nextForwardPosition(c, s);
        Assert.assertEquals( "Pas de déplaceMapTools.nt vertical", d.getX(), c.getX());
        Assert.assertEquals( "On devrait descendre de 1", d.getY(), c.getY() + 1);

        d = MapTools.nextForwardPosition(c, w);
        Assert.assertEquals( "Pas de déplaceMapTools.nt horizontal", d.getY(), c.getY());
        Assert.assertEquals( "On devrait aller de 1 à gauche", d.getX(), c.getX() - 1);

        d = MapTools.nextForwardPosition(c, e);
        Assert.assertEquals( "Pas de déplaceMapTools.nt horizontal", d.getY(), c.getY());
        Assert.assertEquals( "On devrait aller de 1 à droite", d.getX(), c.getX() + 1);
    }

    @Test
    public void testNextBackwardPosition() throws Exception {
        Coordinates d;
        d = MapTools.nextBackwardPosition(c, s);
        Assert.assertEquals( "Pas de déplaceMapTools.nt vertical", d.getX(), c.getX());
        Assert.assertEquals( "On devrait MapTools.nter de 1", d.getY(), c.getY() - 1);

        d = MapTools.nextBackwardPosition(c, n);
        Assert.assertEquals( "Pas de déplaceMapTools.nt vertical", d.getX(), c.getX());
        Assert.assertEquals( "On devrait descendre de 1", d.getY(), c.getY() + 1);

        d = MapTools.nextBackwardPosition(c, e);
        Assert.assertEquals( "Pas de déplaceMapTools.nt horizontal", d.getY(), c.getY());
        Assert.assertEquals( "On devrait aller de 1 à gauche", d.getX(), c.getX() - 1);

        d = MapTools.nextBackwardPosition(c, w);
        Assert.assertEquals( "Pas de déplaceMapTools.nt horizontal", d.getY(), c.getY());
        Assert.assertEquals( "On devrait aller de 1 à droite", d.getX(), c.getX() + 1);

    }

    @Test
    public void testCounterclockwise() throws Exception {
        Direction d;
        d = MapTools.counterclockwise(n);
        Assert.assertEquals("On devrait être au ", Direction.WEST, d);

        d = MapTools.counterclockwise(s);
        Assert.assertEquals("On devrait être au ", Direction.EAST, d);

        d = MapTools.counterclockwise(e);
        Assert.assertEquals("On devrait être au ", Direction.NORTH, d);

        d = MapTools.counterclockwise(w);
        Assert.assertEquals("On devrait être au ", Direction.SOUTH, d);
    }

    @Test
    public void testClockwise() throws Exception {
        Direction d;
        d = MapTools.clockwise(n);
        Assert.assertEquals("On devrait être au ", Direction.EAST, d);

        d = MapTools.clockwise(s);
        Assert.assertEquals("On devrait être au ", Direction.WEST, d);

        d = MapTools.clockwise(e);
        Assert.assertEquals("On devrait être au ", Direction.SOUTH, d);

        d = MapTools.clockwise(w);
        Assert.assertEquals("On devrait être au ", Direction.NORTH, d);
    }
}
