import api.geo_loc;
import api.geo_location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class geo_locTest {

    @Test
    void testConstractor() {
        geo_location g = new geo_loc (1, 2, 3);
        assertEquals(1, g.x());
        assertEquals(2, g.y());
        assertEquals(3, g.z());
    }

    @Test
    void testCopyConstractor() {
        geo_location g = new geo_loc(1, 2, 3);
        geo_location g1 = new geo_loc (g);
        assertEquals(g.x(), g1.x());
    }

    @Test
    void testDistance() {
        geo_location g = new geo_loc (1, 2, 3);
        geo_location g1 = new geo_loc (4, 5, 6);
        assertEquals(5.196152422706632, g.distance(g1));
    }

}