import java.util.ArrayList;

/**
 * Created by Daniel on 2014-02-26.
 */
public class MyUndirectedGraph<ANY> implements UndirectedGraph<ANY> {
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();

    public MyUndirectedGraph() {
    }

    private boolean contains(ANY find){
        for (Node node :  nodes){
            if(node.element.equals(find))
                return true;
        }
        return false;
    }
    private Edge getEdge(Node node1,Node node2){
        for (Edge<ANY> edge :  edges){
            if((edge.start.equals(node1) && edge.dest.equals(node2))){
                    return edge;
            }else if(edge.start.equals(node2) && edge.dest.equals(node1)){
                return edge;
            }
        }
        return null;
    }
    private Node getNode(ANY find){
        for (Node node : nodes) {
            if(node.element.equals(find))
                return node;
        }
        return null;
    }
    @Override
    public boolean add(ANY newNodeElement) {
        if(contains(newNodeElement))
            return false;
        Node<ANY> newNode = new Node<>(newNodeElement);
        nodes.add(newNode);
        return true;
    }
    @Override
    public boolean connect(ANY element1, ANY element2, int cost) {
        if(!contains(element1) || !contains(element2))
            return false;
        Node nodeA = getNode(element1);
        Node nodeB = getNode(element2);
        Edge current = getEdge(nodeA,nodeB);
        if(current !=null){
            current.cost = cost;
            return true;
        }
        Edge<ANY> newEdge = new Edge<ANY>(cost,nodeA,nodeB);
        edges.add(newEdge);
        return true;
    }

    @Override
    public int getCost(ANY element1, ANY element2) {
        Edge current = getEdge(getNode(element1), getNode(element2));
        if (current != null) {
            return current.getCost();
        }
        return -1;
    }

    @Override
    public UndirectedGraph<ANY> minimumSpanningTree() {
        return null;
    }
    private class Node<ANY>{
        private ANY element;
        private Node(ANY element) {
            this.element = element;
        }
        public String toString(){
            return element.toString();
        }
        public ANY getElement(){
            return element;
        }
    }
    private class Edge<ANY>{
        private int cost;
        private Node dest;
        private Node start;

        @Override
        public String toString() {
            return "Edge{" +
                    "cost=" + cost +
                    ", dest=" + dest +
                    ", start=" + start +
                    '}';
        }

        private Edge(int cost, Node<ANY> start,Node<ANY> dest) {
            this.cost = cost;
            this.dest = dest;
            this.start= start;
        }

        public int getCost() {
            return cost;
        }
    }
}
