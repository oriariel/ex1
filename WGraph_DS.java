package ex1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.io.Serializable;

public class WGraph_DS implements weighted_graph {
	private HashMap<Integer, node_info> map;
	public HashMap<Integer, node_info> getMap() {
		return map;
	}

	public void setMap(HashMap<Integer, node_info> map) {
		this.map = map;
	}

	private HashMap<Integer,HashMap<Integer, Double>> edges;
	private int MC;
	private double Weight;
	private int nodeSize;
	private int edgeSize;


	public WGraph_DS() {
		this.map = new HashMap<>();
		this.edges = new HashMap<>();
		this.nodeSize = 0;
		this.edgeSize = this.edges.size();
		this.MC = 0;
		this.Weight=0;
	}
	/**
	 * return the node_data by the node_id,
	 * @param key - the node_id
	 * @return the node_data by the node_id, null if none.
	 */

	public class NodeInfo implements node_info ,Comparable<node_info> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int key;
		private String info;
		private double tag;

		private NodeInfo(int key){
			this.key = key;
			this.info = "";
			this.tag = 0.0;
		}
		private NodeInfo(int key,String s){
			this.key = key;
			this.info = s;
			this.tag = 0.0;
		}

		/**
		 * Return the key (id) associated with this node.
		 * Note: each node_data should have a unique key.
		 * @return
		 */
		public int getKey() {
			return this.key;
		};

		/**
		 * return the remark (meta data) associated with this node.
		 * @return
		 */
		public String getInfo() {
			return this.info;
		}
		/**
		 * Allows changing the remark (meta data) associated with this node.
		 * @param s
		 */
		public void setInfo(String s) {
			this.info=s;
		}
		/**
		 * Temporal data (aka distance, color, or state)
		 * which can be used be algorithms
		 * @return
		 */
		public double getTag() {
			return this.tag;
		}
		/**
		 * Allow setting the "tag" value for temporal marking an node - common
		 * practice for marking by algorithms.
		 * @param t - the new value of the tag
		 */
		public void setTag(double t) {
			this.tag=t;
		}
		public int compareTo(node_info n) {
			double a = getTag();
            double b = n.getTag();
            if (a > b) {
                return 1;
            }
            if (a < b) {
                return -1;
            }
            return 0;
        }
		}

		public node_info getNode(int key) {
			return map.get(key);
		}
		
		/**
		 * return true iff (if and only if) there is an edge between node1 and node2
		 * Note: this method should run in O(1) time.
		 * @param node1
		 * @param node2
		 * @return
		 */
		public boolean hasEdge(int node1, int node2) {
			NodeInfo n1= (NodeInfo)map.get(node1);
			NodeInfo n2= (NodeInfo)map.get(node2);
			if(n1!=null && n2!=null) {
			if(getV(n1.getKey()).contains(n2.getKey())||getV(n2.getKey()).contains(n1.getKey())) {
				return true;
			}
			else return false;
			}
			return false;
		}

	public double getWeight() {
		return Weight;
	}
	public void setWeight(double weight) {
	Weight = weight;
	}
	/**
	 * return the weight if the edge (node1, node1). In case
	 * there is no such edge - should return -1
	 * Note: this method should run in O(1) time.
	 * @param node1
	 * @param node2
	 * @return
	 */
	public double getEdge(int node1, int node2) {
		if (map.get(node1)==map.get(node2)) {//Not sure needed
			return Weight;
		}
		if(hasEdge(node1,node2)==false) {
			return -1;
		}
		return edges.get(node1).get(node2);

	}
	/**
	 * add a new node to the graph with the given key.
	 * Note: this method should run in O(1) time.
	 * Note2: if there is already a node with such a key -> no action should be performed.
	 * @param key
	 */
	public void addNode(int key) {
		if(!(map.containsKey(key))) {
			map.put(key, new NodeInfo(key));
			edges.put(key, new HashMap<>());
			nodeSize++;
		}
		
	}
	/**
	 * Connect an edge between node1 and node2, with an edge with weight >=0.
	 * Note: this method should run in O(1) time.
	 * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
	 */
	public void connect(int node1, int node2, double w) {
		if(node1==node2){
			return;
		}
		if(hasEdge(node1, node2) || hasEdge(node2, node1) ){
		edges.get(node1).put(node2, w);
		edges.get(node2).put(node1, w);
		}
		NodeInfo n1= (NodeInfo)map.get(node1);
		NodeInfo n2= (NodeInfo)map.get(node2);	
		if(n1!=null&&n2!=null) {
			edges.get(node1).put(node2, w);
			edges.get(node2).put(node1, w);
			edgeSize++;
			MC++;
		}
		else {
			return;
		}
		
		
	}
	/**
	 * This method return a pointer (shallow copy) for a
	 * Collection representing all the nodes in the graph.
	 * Note: this method should run in O(1) tim
	 * @return Collection<node_data>
	 */
	public Collection<node_info> getV() {
		return map.values();
	}
	/**
	 *
	 * This method returns a Collection containing all the
	 * nodes connected to node_id
	 * Note: this method can run in O(k) time, k - being the degree of node_id.
	 * @return Collection<node_data>
	 */
	public Collection<node_info> getV(int node_id) {
		List<node_info> list = new ArrayList<>();
		Iterator<Integer> iter = edges.keySet().iterator();
		while(iter.hasNext()) {
            list.add(getNode((int) iter.next()));
        }
        return list;
    }

	/**
	 * Delete the node (with the given ID) from the graph -
	 * and removes all edges which starts or ends at this node.
	 * This method should run in O(n), |V|=n, as all the edges should be removed.
	 * @return the data of the removed node (null if none).
	 * @param key
	 */
	public node_info removeNode(int key) {
		if(map.containsKey(key)){
			Collection<node_info> col= getV(key);
			//Iterator ite=col.iterator();
			for(node_info temp: col) {
				removeEdge(temp.getKey(),key);
			}
			node_info t=map.get(key);
			map.remove(key);
			nodeSize--;
			return t;
		}
		return null;
	}

	/**
	 * Delete the edge from the graph,
	 * Note: this method should run in O(1) time.
	 * @param node1
	 * @param node2
	 */
	public void removeEdge(int node1, int node2) {
		if(hasEdge(node1, node2) || hasEdge(node2,node1)){
			edges.get(node1).remove(node2);
			edges.get(node2).remove(node1);
			edgeSize--;
			MC++;
		}
		else {
			return;
		}
	}

	@Override
	public int nodeSize() {
		return this.nodeSize;
	}

	@Override
	public int edgeSize() {
		return this.edgeSize;
	}

	@Override
	public int getMC() {
		return this.MC;
	}

}

