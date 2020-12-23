package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a very simple GUI class to present a
 * game on a graph - you are welcome to use this class - yet keep in mind
 * that the code is not well written in order to force you improve the
 * code and not to take it "as is".
 *
 */
public class MyFrame extends JFrame {
	private Mypanel pani;
	private Arena _ar;

	public MyFrame(Arena ar,String s) {
		super(s);
		this._ar=ar;
		initFrame();
	}

	private void initFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int height = dimension.height;
		int width = dimension.width;
		this.setSize(width, height);
		this.setVisible(true);

		initPanel();
		this.add(pani);
	}

	public void initPanel(){
		pani = new Mypanel(_ar);
	}

	public Mypanel getPanel(){
		return pani;
	}
}
