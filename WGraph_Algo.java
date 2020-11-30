package ex1;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;


public class WGraph_Algo implements weighted_graph_algorithms {
	HashMap<Integer, Double> next = new HashMap<>();
	HashMap<Integer, Integer> prev = new HashMap<>();
	weighted_graph gr;
	/* Init the graph on which this set of algorithms operates on.*/

	public void init(weighted_graph g) {
		this.gr = g;
	}
	@Override
	public weighted_graph getGraph() {
		return this.gr;
	}
	@Override
	public weighted_graph copy() {
		weighted_graph ans = new WGraph_DS();
		for (node_info n : gr.getV()) {
			ans.addNode(n.getKey());
			ans.getNode(n.getKey()).setTag(n.getTag());
			ans.getNode(n.getKey()).setInfo(n.getInfo());
		}
		Collection<node_info> cl= gr.getV();
		for (node_info e :cl) {
			for (node_info ne : gr.getV(e.getKey())) {
				gr.connect(e.getKey(), ne.getKey(), gr.getEdge(e.getKey(), ne.getKey()));
			}

		}

		return gr;
	}

	@Override
	public boolean isConnected() {
		if(this.gr==null){
			return true;
		}
		if(this.gr.nodeSize() ==0){
			return true;
		}
		if(this.gr.edgeSize()==0 && this.gr.nodeSize()==1){
			return true;
		}
		if(this.gr.edgeSize()==0 && this.gr.nodeSize()>1) {
			return false;
		}
		ArrayList<node_info> nodes = new ArrayList<node_info>(this.gr.getV());
		Distance(nodes.get(0).getKey());
		boolean a = (next.containsValue(Double.MAX_VALUE));
		return a;
	}

	private void Distance(int src) {
		PriorityQueue<node_info> queue = new PriorityQueue<>();
		Collection<node_info> cl = this.gr.getV();
		double temp = Double.MAX_VALUE;
		for (node_info n : cl) {
			if (n.getKey() != src) {
				n.setTag(temp);
				next.put(n.getKey(), temp);
				prev.put(n.getKey(), null);
			}
			else {
				next.put(n.getKey(), 0.0);
				n.setTag(0);
			}
			queue.add(n);
		}
		while (!queue.isEmpty()) {
			node_info i = queue.poll();
			Collection<node_info> ne = gr.getV(i.getKey());
			for (node_info v : ne) {
				double ans = next.get(i.getKey()) + gr.getEdge(i.getKey(), v.getKey());
				if(ans > 0.0) {
					if (ans < next.get(v.getKey())) {
						next.put(v.getKey(), ans);
						prev.put(v.getKey(), i.getKey());
						v.setTag(ans); 
						queue.remove(v);
						queue.add(v);
					}

				}
				break;
			}
		}

	}

	@Override
	public double shortestPathDist(int src, int dest) {
		Distance(src);
		double ans = next.get(dest);
		return ans;
	}

	@Override
	public List<node_info> shortestPath(int src, int dest) {
		List<node_info> ans = new LinkedList<>();
		if(src == 0 || dest == 0) {
			return ans;
		}
		boolean flag = true;
		Distance(src);
		ans.add(gr.getNode(dest));
		while (flag== true) {
			if (dest == src) 
				break;
			ans.add(gr.getNode(prev.get(dest)));
			dest = prev.get(dest);
		}
		List<node_info> result = new LinkedList<>();
		for (int i = 0; i < result.size() - 1 ; i++) {
			result.add(result.get(i));
		}
		Collections.reverse(ans);
		return ans;
	}


	@Override
	public boolean save(String file) {
		return false;
	}

	@Override
	public boolean load(String file) {
		// TODO Auto-generated method stub
		return false;
	}
}