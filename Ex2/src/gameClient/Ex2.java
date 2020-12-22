package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Ex2 implements Runnable{
	private static MyFrame _win;
	private static Arena _ar;
	private static login logi;
	private static int level;
	private static int ID;

	public static void main(String[] a) {
		if(a.length==0){
		logi = new login();}
		else{
			level = Integer.parseInt(a[1]);
			ID = Integer.parseInt(a[0]);
			Thread client = new Thread(new Ex2());
			client.start();
		}
	}

	/**
	 * sending the game for initialize and than start the game.
	 */
	@Override
	public void run() {
		if(logi!=null){
			level = logi.getLevel();
			ID = logi.getID();
		}
		int move=1;
		game_service game = Game_Server_Ex2.getServer(level); // you have [0,23] games
		game.login(ID);
		init(game);
		game.startGame();
		int ind=0;
		long dt=100;
		ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
		for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),_ar.getGraph());}
		_ar.setPokemons(cl_fs);
		while(game.isRunning()) {
			Iterator <CL_Pokemon> itr = _ar.getPokemons().iterator();
			moveAgants(game, _ar.getGraph());
			if (move%1==0) {
				game.move();
				move=1;

			}
			else {
				move++;
			}
			try {
				if(ind%1==0) {_win.repaint();}
				Thread.sleep(dt);
				ind++;
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		String res = game.toString();

		System.out.println(res);
		System.exit(0);
	}
	/**
	 * choose for each of the agents targets by the function getTarget() according to his location
	 * and than send them to the next node in the path with some help from the function nextNode().
	 * in case the agent is on the src node of the pokemon edge the agent will turn to the dest of this edge.
	 * @param game
	 * @param gg
	 * @param
	 */
	private static void moveAgants(game_service game, directed_weighted_graph gg) {
		List<CL_Agent> log = Arena.getAgents(game.getAgents(), gg);
		List<CL_Pokemon> catchem = new ArrayList<CL_Pokemon>();
		_ar.setAgents(log);
		String fs =  game.getPokemons();
		List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
		_ar.setPokemons(ffs);
		ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
		for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
		_ar.setPokemons(cl_fs);
		for(int i=0;i<log.size();i++) {
			CL_Agent ag = log.get(i);
			int id = ag.getID();
			int src = ag.getSrcNode();
			double v = ag.getValue();
			if(ag.get_curr_fruit()==null) {
				CL_Pokemon c = getTarget(gg, id, log, catchem);
				ag.set_curr_fruit(c);
			}
			if(ag.get_curr_fruit().get_edge().getSrc()!=ag.getSrcNode()) {
				game.chooseNextEdge(id, nextNode(gg, src, ag.get_curr_fruit()));
				System.out.println("Agent: " + id + ", val: " + v + "   turned to node: "+ nextNode(gg, src, ag.get_curr_fruit()));
			}
			else{
				game.chooseNextEdge(id, ag.get_curr_fruit().get_edge().getDest());
				System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + ag.get_curr_fruit().get_edge().getDest());
				if(v>ag.getPrevious_value()){
					catchem.remove(ag.get_curr_fruit());
					ag.set_curr_fruit(null);
					ag.setPrevious_value(v);
				}
			}
		}
	}
	/**
	 * check on the list of the shortest path to which node should the agent go forward.
	 * @param g
	 * @param src
	 * @return the node id(key)
	 */
	private static int nextNode(directed_weighted_graph g, int src, CL_Pokemon c) {
		DWGraph_Algo ga = new DWGraph_Algo();
		ga.init(g);
		List <node_data> next = ga.shortestPath(src,c.get_edge().getSrc());
		if(next!=null) {
			Iterator<node_data> itr = next.iterator();
			node_data moshe = itr.next();
			return itr.next().getKey();
		}
		return src;
	}

	/**
	 * this function checking for the mode with the shortest path from the agent node
	 * to each of the src node of the pokemon's edge, and than set this pokemon as the
	 * next target of each agent.
	 * if there is an agent on his way to catch this pokemon this function will choose
	 * the next pokemon with the shortest path.
	 * @param gg
	 * @param id
	 * @param log
	 * @param catchem
	 * @return
	 */
	private static CL_Pokemon getTarget(directed_weighted_graph gg, int id, List<CL_Agent> log, List<CL_Pokemon> catchem){
		double min=Integer.MAX_VALUE;
		CL_Agent ag = log.get(id);
		DWGraph_Algo ga = new DWGraph_Algo();
		ga.init(gg);
		CL_Pokemon pok = null;
		Iterator<CL_Pokemon> itr = _ar.getPokemons().iterator();
		while (itr.hasNext()){
			CL_Pokemon poke = itr.next();
			Arena.updateEdge(poke,gg);
			if(min>(ga.shortestPathDist(ag.getSrcNode(),poke.get_edge().getSrc())+poke.get_edge().getWeight())&&!catchem.contains(poke)){
				min=(ga.shortestPathDist(ag.getSrcNode(),poke.get_edge().getSrc())+poke.get_edge().getWeight());
				pok=poke;
			}
		}
		catchem.add(pok);
		return pok;
	}

	/**
	 * Init the game according to the server instructions.
	 * This function set the arena and the frame of the game, load a dw_graph from the server,
	 * making a priority queue that saves the pokemons by values (the pokemon with
	 * the greatest value will be first) and than locate the agents on the src node of the pokemon's edge.
	 * @param game
	 */
	private void init(game_service game) {
		String g = game.getGraph();
		String fs = game.getPokemons();
		dw_graph_algorithms ga = new DWGraph_Algo();
		Path path = Paths.get("output.txt");
		String contents = game.getGraph();
		try {
			Files.writeString(path, contents, StandardCharsets.UTF_8);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		ga.load("output.txt");
		directed_weighted_graph gg = ga.getGraph();
		_ar = new Arena();
		_ar.setGraph(gg);
		_ar.setPokemons(Arena.json2Pokemons(fs));
		_ar.setthisgame(game);
		_win = new MyFrame(_ar,"Pokemon Challenge");
		_win.show();

		String info = game.toString();
		JSONObject line;
		try {
			PriorityQueue<CL_Pokemon> maxValue = new PriorityQueue<CL_Pokemon>();
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("agents");
			System.out.println(info);
			System.out.println(game.getPokemons());
			int src_node = 0;  // arbitrary node, you should start at one of the pokemon
			ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
			for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
			for(int a = 0;a<cl_fs.size();a++) {
				CL_Pokemon c = cl_fs.get(a);
				maxValue.add(c);
			}
			for(int i=0; i<rs; i++){
				game.addAgent(maxValue.poll().get_edge().getSrc());
			}
		}
		catch (JSONException e) {e.printStackTrace();}
	}
}