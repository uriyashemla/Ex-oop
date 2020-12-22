import api.EdgeData;
import api.edge_data;
import org.junit.jupiter.api.Test;
import api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EdgeDataTest {

        @Test
        void testGetSrc() {
            edge_data e = new EdgeData(1,2);
            assertEquals(1,e.getSrc());
        }

        @Test
        void testGetDest() {
            edge_data e = new EdgeData(1,2);
            assertEquals(2,e.getDest());
        }

        @Test
        void testGetWeight() {
            edge_data e = new EdgeData(1,2, 1.5);
            assertEquals(1.5,e.getWeight());
        }

        @Test
        void testGetInfo() {
            edge_data e = new EdgeData(1,2, 1.5, "black", -1);
            assertEquals("black",e.getInfo());
        }

        @Test
        void testSetInfo() {
            edge_data e = new EdgeData(1,2);
            assertNotEquals("white", e.getInfo());
            e.setInfo("white");
            assertEquals("white",e.getInfo());
        }

        @Test
        void testGetTag() {
            edge_data e = new EdgeData(1,2, 1.5, "black", 2);
            assertEquals(2, e.getTag());
        }

        @Test
        void testSetTag() {
            edge_data e = new EdgeData(1,2);
            assertNotEquals(2, e.getTag());
            e.setTag(2);
            assertEquals(2, e.getTag());
        }

    }