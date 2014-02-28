import java.util.*;
public class MyUndirectedGraph<ANY> implements UndirectedGraph<ANY> {
    private ArrayList<Node<ANY>> nodes = new ArrayList<>();
    private ArrayList<Edge<ANY>> edges = new ArrayList<>();
    public MyUndirectedGraph() {
    }
    private boolean contains(ANY find){
        for (Node<ANY> node :  nodes){
            if(node.getElement().equals(find))
                return true;
        }
        return false;
    }
    private Edge<ANY> getEdge(Node<ANY> node1, Node<ANY> node2){
        for (Edge<ANY> edge :  edges){
            if( (edge.start.equals(node1) && edge.dest.equals(node2)) || (edge.start.equals(node2) && edge.dest.equals(node1)) )
                return edge;
        }
        return null;
    }
    private Node<ANY> getNode(ANY find){
        for (Node<ANY> node : nodes) {
            if(node.getElement().equals(find))
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
        Node<ANY> nodeA = getNode(element1);
        Node<ANY> nodeB = getNode(element2);
        Edge<ANY> current = getEdge(nodeA, nodeB);
        if(current !=null){
            current.cost = cost;
            return true;
        }
        Edge<ANY> newEdge = new Edge<>(cost,nodeA,nodeB);
        edges.add(newEdge);
        return true;
    }

    @Override
    public int getCost(ANY element1, ANY element2) {
        Edge<ANY> current;
        current = getEdge(getNode(element1), getNode(element2));
        if (current != null) {
            return current.getCost();
        }
        return -1;
    }
    @Override
    public UndirectedGraph<ANY> minimumSpanningTree() {
        //klassen DsjSets är tagen från boken (DisjSetsFast exemplet)
        DisjSets ds = new DisjSets(nodes.size());
        //initiera en ny graf som vi bygger upp och returnerar
        MyUndirectedGraph<ANY> minSpanTree = new MyUndirectedGraph<>();
        //Ranka alla edges
        PriorityQueue<Edge<ANY>> queue = new PriorityQueue<>(edges);
        //Vi mappar varje nod mot en siffra (för DisjSets skull) och lägger in den i nya grafen
        HashMap<Node<ANY>,Integer> nodeNumbers = new HashMap<>();
        for(int i=0; i<nodes.size();i++){
            nodeNumbers.put(nodes.get(i), i);
            minSpanTree.add(nodes.get(i).getElement());
        }
        /*
            ett minSpanTree har alltid en edge mindre än antalet noder
            sålänge vårt nya träd har färre edges än antalet noder-1
            i det ursprungliga kan vi inte vara klara
        */
        int set1,set2;
        while(minSpanTree.edges.size() != nodes.size()-1){
            //Edgen med minst vikt
            Edge<ANY> current = queue.poll();
            if(current!=null && !minSpanTree.hasEdge(current)){
                //kolla vilken grupp de är associerade med
                set1 = ds.find(nodeNumbers.get(current.dest));
                set2 = ds.find(nodeNumbers.get(current.start));
                if(set1!=set2){
                    ds.union(set1,set2);
                    minSpanTree.connect((ANY)current.start.getElement(),(ANY)current.dest.getElement(),current.getCost());
                }
             }
        }
        return minSpanTree;
    }

    private boolean hasEdge(Edge<ANY> current) {
        for(Edge<ANY> edge:edges){
            if(edge.equals(current))
                return true;
        }
        return false;
    }

    private class Node<ANY>{
        private ANY element;
        public Node(ANY element) {
            this.element = element;
        }

        public String toString(){
            return element.toString();
        }
        public ANY getElement(){
            return element;
        }
    }
    private class Edge<ANY> implements Comparable<ANY> {
        private int cost;
        private Node dest;
        private Node start;
        private Edge(int cost, Node<ANY> start,Node<ANY> dest) {
            this.cost = cost;
            this.dest = dest;
            this.start= start;
        }
        public int getCost() {
            return cost;
        }
        @Override
        public int compareTo(ANY object) {
            Edge<ANY> that = (Edge<ANY>) object;
            if(this.cost==that.cost) return 0;
            else if(this.cost <that.cost) return -1;
            else return 1;
        }
    }
}
