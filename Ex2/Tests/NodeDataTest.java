import api.NodeData;
import api.node_data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NodeDataTest {

        @Test
        void testGetKey() {
            node_data n = new NodeData(1);
            assertEquals(1, n.getKey());
        }

        @Test
        void testGetWeight() {
            node_data n = new NodeData(1,1.5,"",-1);
            assertEquals(1.5,n.getWeight());
        }


        @Test
        void testSetWeight() {
            node_data n = new NodeData(1);
            n.setWeight(1.5);
            assertEquals(1.5, n.getWeight());
        }


        @Test
        void testGetInfo() {
            node_data n = new NodeData(1,1.5,"black",-1);
            assertEquals("black", n.getInfo());
        }


        @Test
        void testSetInfo() {
            node_data n = new NodeData(1);
            n.setInfo("black");
            assertEquals("black", n.getInfo());
        }


        @Test
        void testGetTag() {
            node_data n = new NodeData(1,1.5,"black",2);
            assertEquals(2, n.getTag());
        }


        @Test
        void testSetTag() {
            node_data n = new NodeData(1);
            n.setTag(2);
            assertEquals(2, n.getTag());
        }

    }
