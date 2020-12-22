import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import api.DWGraph_DS;
import api.EdgeData;
import api.NodeData;
import api.directed_weighted_graph;
import api.edge_data;
import api.node_data;
import api.*;
import org.junit.jupiter.api.Test;

class DWGraph_DSTest {

        void testConstracor () {
            DWGraph_DS g = new DWGraph_DS();
            assertEquals(0, g.getMC());
            assertEquals(0, g.edgeSize());
        }

        @Test
        void testGetNode() {
            directed_weighted_graph g = new DWGraph_DS();
            node_data n1 = new NodeData(1);
            node_data n2 = new NodeData();
            node_data n3 = new NodeData(3);
            g.addNode(n1);
            g.addNode(n2);
            g.addNode(n3);
            assertNotEquals(null, g.getNode(1));
            assertEquals(n1, g.getNode(1));
            assertEquals(null, g.getNode(2));
            assertNotEquals(null, g.getNode(3));

            node_data n4 = new NodeData(4);
            g.addNode(n4);
            assertEquals(4, g.getNode(4).getKey());

            node_data n = g.getNode(5);
            assertTrue(n == null, "node 5 doesn't exsist");
        }


        @Test
        void testGetEdge() {
            directed_weighted_graph g = new DWGraph_DS();
            g.addNode(new NodeData(1));
            g.addNode(new NodeData(2));

            edge_data e = g.getEdge(1, 2);
            assertTrue(e == null, "edge (1, 2) doesn't exsist");

            g.connect(1, 2, 0.2);
            e = g.getEdge(1, 2);
            assertFalse(e == null, "edge (1, 2) does exsist");

            g.removeEdge(1, 2);
            assertTrue(g.getEdge(1, 2) == null, "edge (1, 2) doesn't exsist");
        }

        //
        @Test
        void testGetEdge2() {
            directed_weighted_graph g = new DWGraph_DS();
            node_data n1 = new NodeData(1);
            node_data n2 = new NodeData(2);
            g.addNode(n1);
            g.addNode(n2);
            g.connect(1, 2, 0.2);

            edge_data e = g.getEdge(n1.getKey(), n2.getKey());
            assertFalse(e == null);

            g.removeEdge(n1.getKey(), n2.getKey());
            assertTrue(g.getEdge(n1.getKey(), n2.getKey()) == null);
            //assertTrue(g.getEdge(1, 2) == null);
        }

        @Test
        void testAddNode () {
            directed_weighted_graph g = new DWGraph_DS();
            node_data n1 = new NodeData(1);
            node_data n2 = new NodeData(2);
            g.addNode(n1);
            assertEquals(1, g.nodeSize());
            g.addNode(n2);
            assertEquals(2, g.nodeSize());

            //trying to add a new node with an unavailable key
            node_data n3 = new NodeData(2);
            try {
                g.addNode(n3);
                fail("the graph can't contains different nodes with the same key");
            }
            catch(RuntimeException e) {
            }
        }

        @Test
        void testConnect() {
            directed_weighted_graph g = new DWGraph_DS();
            g.addNode(new NodeData(1));
            g.addNode(new NodeData(2));
            g.connect(1, 2, 3.0);

            try {
                g.connect(1, 1, 3.0);
                fail("can't connect the node to himself");
            }
            catch (RuntimeException e) {}

            try {
                g.connect(2, 1, -2.5);
                fail("edge weight can't be a negetive number");
            }
            catch (RuntimeException e) {}

            try {
                g.connect(1, 3, 2.5);
                fail("can't connect an unexist node (3)");
            }
            catch (RuntimeException e) {}
        }


        @Test
        void testGetV() {
            directed_weighted_graph g = new DWGraph_DS();
            g.addNode(new NodeData(1));
            g.addNode(new NodeData(2));
            assertEquals(2, g.getV().size());
        }


        @Test
        void testgetE() {
            directed_weighted_graph g = new DWGraph_DS();
            node_data n1 = new NodeData(1);
            node_data n2 = new NodeData(2);
            g.addNode(n1);
            g.addNode(n2);
            edge_data e = new EdgeData(1, 2);
            g.connect(1, 2, 0.2);
            //edge_data e = g.getEdge(n1.getKey(), n2.getKey());
            assertEquals(1, g.getE(e.getSrc()).size());
        }


        @Test
        void testRemoveNode () {
            directed_weighted_graph g = new DWGraph_DS();
            g.addNode(new NodeData(1));
            g.removeNode(1);

            node_data n = g.getNode(1);
            assertTrue(n == null, "node 1 doesn't exsist");
        }

        @Test
        void testRemoveEdge () {
            directed_weighted_graph g = new DWGraph_DS();
            g.addNode(new NodeData(1));
            g.addNode(new NodeData(2));
            g.connect(1, 2, 0.2);
            g.connect(2, 1, 0.2);
            g.removeEdge(1, 2);

            edge_data e = g.getEdge(1, 2);
            assertTrue(e == null, "edge (1, 2) doesn't exsist");

            edge_data e1 = g.getEdge(2, 1);
            assertFalse(e1 == null);
        }


        @Test
        void testNodeSize () {
            directed_weighted_graph g = new DWGraph_DS();
            g.addNode(new NodeData(1));
            g.addNode(new NodeData(2));
            assertEquals(2, g.nodeSize());
            g.removeNode(2);
            assertEquals(1, g.nodeSize());
        }


        @Test
        void testEdgeSize () {
            directed_weighted_graph g = new DWGraph_DS();
            g.addNode(new NodeData(1));
            g.addNode(new NodeData(2));
            g.connect(1, 2, 0.5);
            g.connect(2, 1, 0.5);
            assertEquals(2, g.edgeSize());

            g.removeEdge(1, 2);
            assertEquals(1, g.edgeSize());

            g.addNode(new NodeData(3));
            g.addNode(new NodeData(4));
            edge_data e = new EdgeData(3, 4);
            edge_data e1 = new EdgeData(4, 3);
            g.connect(3, 4, 0.5);
            g.connect(4, 3, 0.5);
            assertEquals(3, g.edgeSize());
            g.removeEdge(3, 4);
            assertEquals(2, g.edgeSize());
        }


        @Test
        void testGetMc() {
            directed_weighted_graph g = new DWGraph_DS();
            g.addNode(new NodeData(1));
            g.addNode(new NodeData(2));
            assertEquals(2, g.getMC());

            g.removeNode(1);
            assertEquals(3, g.getMC());
        }

}