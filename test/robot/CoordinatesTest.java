package robot;

import org.junit.Test;
import org.junit.Assert;

/**
 * Created with IntelliJ IDEA.
 * User: aquassaut
 * Date: 12/2/13
 * Time: 9:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class CoordinatesTest {

    @Test
    public void testGetX() throws Exception {
        Coordinates c = new Coordinates(5, 10);
        Assert.assertEquals("Le x devrait valoir 5", 5, c.getX());
    }
    @Test
    public void testGety() {
        Coordinates c = new Coordinates(5, 10);
        Assert.assertEquals("Le y devrait valoir 10", 10, c.getY());
    }
}
