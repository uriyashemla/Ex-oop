import api.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DWGraph_AlgoTest {

	@Test
	void testInit() {
		DWGraph_DS g = new DWGraph_DS();
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));
		DWGraph_Algo algo = new DWGraph_Algo();
		algo.init(g);

		directed_weighted_graph g1 = new DWGraph_DS();
		g1.addNode(new NodeData(1));
		g1.addNode(new NodeData(2));
		algo.init(g1);

		assertEquals(g1.getNode(2).getKey(), algo.getGraph().getNode(2).getKey());
		assertEquals(g.nodeSize(), algo.getGraph().nodeSize());
		assertEquals(g1.nodeSize(), algo.getGraph().nodeSize());
	}

	@Test
	void testCopy() {
		DWGraph_DS g = new DWGraph_DS();
		g.addNode(new NodeData(1));
		g.addNode(new NodeData(2));
		edge_data e = new EdgeData(1, 2);
		g.connect(e.getSrc(), e.getDest(), 0.5);
		DWGraph_Algo ga = new DWGraph_Algo();
		ga.init(g);
		directed_weighted_graph g1 = ga.copy();
		assertTrue(g.equals(g1));
		assertEquals(g, g1);
	}


    	@Test
    	void testIsConnected() {
    		DWGraph_Algo algo = new DWGraph_Algo();
    		DWGraph_DS g = new DWGraph_DS();
    		algo.init(g);
    		assertTrue(algo.isConnected());
    		g.addNode(new NodeData(1));
    		g.addNode(new NodeData(2));
    		g.addNode(new NodeData(3));
    		g.connect(1, 2, 1.1);
    		g.connect(2, 3, 1.2);
    		algo.init(g);
    		assertFalse(algo.isConnected());
    		g.connect(3, 1, 1.3);
    		algo.init(g);
    		assertTrue(algo.isConnected());
    	}


    	@Test
    	void testShortestPathDist() {
    		directed_weighted_graph g = new DWGraph_DS();
    		DWGraph_Algo g1 =  new DWGraph_Algo();
    		g1.init(g);
    		g.addNode(new NodeData(1));
    		g.addNode(new NodeData(2));
    		g.addNode(new NodeData(3));
    		g.addNode(new NodeData(4));

    		g.connect(1, 2, 3);
    		g.connect(2, 3, 2);
    		g.connect(3, 4, 3);
    		g.connect(4, 1, 2.5);
    		assertEquals(5.0,(g1.shortestPathDist(1, 3)));
    	}


    	@Test
    	void testShortestPath() {
    		directed_weighted_graph g = new DWGraph_DS();
    		DWGraph_Algo g1 =  new DWGraph_Algo();
    		g1.init(g);
    		g.addNode(new NodeData(1));
    		g.addNode(new NodeData(2));
    		g.addNode(new NodeData(3));
    		g.addNode(new NodeData(4));
    		g.connect(1, 2, 3);
    		g.connect(2, 3, 2);
    		g.connect(3, 4, 3);
    		g.connect(4, 1, 2.5);
    		//g1.init(g);
    		Collection<node_data> c = g1.shortestPath(1, 3);
    		Iterator<node_data> it = c.iterator();
    		for (int i = 1; i <= 3 ; i++) {
    			node_data n = it.next();
    			assertEquals(i, n.getKey());
    		}
    	}


    @Test
    void testSaveLoad() {
        directed_weighted_graph g1 = new DWGraph_DS();
        dw_graph_algorithms g = new DWGraph_Algo();
        g.init(g1);
        String str = "graph.json";
        g.save(str);
        g.load(str);
        directed_weighted_graph g2 = g.getGraph();
        assertEquals(g1,g2);
    }

}