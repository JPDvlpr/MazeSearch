package structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * @param <V> vertex
 * includes the edge inner class, node, bfs, and dfs
 * @author Jhakon Pappoe
 * @version 0.1
 */
public class DirectedAL<V> {

    private Map<V, Node<V>> adjacencyLists;
    private int vertexSize;
    private int edgeSize;

    private DirectedAL() {
        adjacencyLists = new HashMap<>();
    }

    //pass any number of vertices indestination the graph
    private void addVertex(V... vertices) {
        for (V vertex : vertices) {
            addVertex(vertex);
            vertexSize++;
        }
    }

    private boolean addVertex(V vertex) {
        if (containsVertex(vertex)) {
            return false;
        } else {
            adjacencyLists.put(vertex, null);
            return true;
        }
    }

    private void addEdge(Edge<V>... edges) {
        for (Edge<V> edge : edges) {
            addEdge(edge.source, edge.destination, edge.weight);
            edgeSize++;
        }
    }

    private boolean addEdge(V source, V destination, double weight) {
        //make sure the vertices exist, make sure this is not a duplicate edge
        if (!adjacencyLists.containsKey(source) || !adjacencyLists.containsKey(destination) ||
            containsEdge(source, destination)) {
            return false;
        }

        if (adjacencyLists.get(source) == null) {
            adjacencyLists.put(source, new Node<V>(destination, weight));
            return true;
        }
        //create a new head of the linked list of adjacent vertices
        Node<V> newNode = new Node<>(destination, weight);
        newNode.next = adjacencyLists.get(source);

        adjacencyLists.put(source, newNode);
        return true;
    }

    private void addUndirectedEdge(V first, V second, double weight) {
        addEdge(first, second, weight);
        addEdge(second, first, weight);
    }

    private void addUndirectedEdge(Edge<V>... edges) {
        for (Edge<V> edge : edges) {
            addUndirectedEdge(edge.source, edge.destination, edge.weight);
        }
    }

    private boolean containsVertex(V vertex) {
        return adjacencyLists.containsKey(vertex);
    }

    private boolean containsEdge(V source, V destination) {
        if (!adjacencyLists.containsKey(source) || !adjacencyLists.containsKey(destination)) {
            return false;
        }

        Node<V> current = adjacencyLists.get(source);
        while (current != null) {
            //is this the "destination" vertex
            if (current.vertex.equals(destination)) {
                return true;
            }

            //otherwise move destination the next adjacent vertex
            current = current.next;
        }

        return false; //we never found the vertex
    }

    private void clear() {
        adjacencyLists = new HashMap<>();
        vertexSize = 0;
        edgeSize = 0;
    }

    private int getVertexSize() {
        return vertexSize;
    }

    private int getEdgeSize() {
        return edgeSize;
    }

    private Set<V> vertices() {
        return Collections.unmodifiableSet(adjacencyLists.keySet());
    }

    private Set<Edge<V>> edges() {
        Set<Edge<V>> edges = new HashSet<>();

        for (V vertex : adjacencyLists.keySet()) {
            Node<V> current = adjacencyLists.get(vertex);
            while (current != null) {
                edges.add(new Edge<>(vertex, current.vertex, current.weight));
                current = current.next;
            }
        }
        return edges;
    }

    private boolean removeVertex(V vertex) {
        if (!adjacencyLists.containsKey(vertex)) {
            return false;
        }

        //remove outgoing edges
        adjacencyLists.put(vertex, null);

        //remove incoming edges
        for (V other : adjacencyLists.keySet()) {
            Node<V> current = adjacencyLists.get(other);
            if (current != null) {
                if (current.vertex.equals(vertex)) {
                    adjacencyLists.put(other, current.next);
                } else {
                    while (current.next != null && !current.next.vertex.equals(vertex)) {
                        current = current.next;
                    }

                    if (current.next != null) {
                        current.next = current.next.next;
                    }
                }
            }
        }

        return true;
    }

    private boolean removeEdge(V source, V destination) {
        if (!adjacencyLists.containsKey(source) || !adjacencyLists.containsKey(destination)) {
            return false;
        }

        Node<V> startingPosition = adjacencyLists.get(source);
        if (startingPosition == null) {
            return false;
        } else if (startingPosition.vertex.equals(destination)) {
            adjacencyLists.put(source, startingPosition.next);
        } else {
            Node<V> before = findEdgeTo(adjacencyLists.get(source), destination);

            if (before.next == null) {
                return false;
            } else {
                before.next = before.next.next;
            }
        }
        return true;
    }

    //finds the position where a node should be located
    private Node<V> findEdgeTo(Node<V> current, V vertex) {
        while (current.next != null && !current.next.vertex.equals(vertex)) {
            current = current.next;
        }

        return current;
    }


    private List<V> dfs(V start) {
        Set<V> seen = new HashSet<>();
        List<V> traversal = new ArrayList<>();

        //make sure we have that starting vertex
        if (!containsVertex(start)) {
            return traversal;
        }

        dfs(start, seen, traversal);

        return traversal;
    }

    private void dfs(V element, Set<V> seen, List<V> traversal) {
        if (!seen.contains(element)) {
            seen.add(element);
            traversal.add(element);

            Node<V> current = adjacencyLists.get(element);
            while (current != null) {
                dfs(current.vertex, seen, traversal);
                current = current.next;
            }
        }
    }

//     private void dfs(int vertex)
//    {
//        marked[vertex] = true;
//        LinkedList<Edge> neighbors = getEdges(vertex);
//        for (Edge edge : neighbors)
//        {
//            if (!marked[edge.getDest()])
//            {
//                edgeTo[edge.getDest()] = vertex;
//                dfs(edge.getDest());
//            }
//        }
//    }

    private List<V> iterativeDfs(V start) {
        Set<V> seen = new HashSet<>();
        List<V> traversal = new ArrayList<>();
        Stack<V> nextVertex = new Stack<>();

        //make sure we have that starting vertex
        if (!containsVertex(start)) {
            return traversal;
        }
        nextVertex.push(start);

        while (!nextVertex.isEmpty()) {
            V next = nextVertex.pop();

            if (!seen.contains(next)) {
                seen.add(next);
                traversal.add(next);

                Node<V> current = adjacencyLists.get(next);
                while (current != null) {
                    nextVertex.push(current.vertex);
                    current = current.next;
                }
            }
        }

        return traversal;
    }

    private List<V> allComponentsDfs() {
        List<V> allComponents = new ArrayList<>();
        Set<V> seen = new HashSet<>();

        for (V vertex : vertices()) {
            dfs(vertex, seen, allComponents);
        }

        return allComponents;
    }

    private List<V> bfs(V start) {
        Set<V> seen = new HashSet<>();
        List<V> traversal = new ArrayList<>();
        Queue<V> bfsQueue = new LinkedList<>();

        //add the first element
        bfsQueue.add(start);

        while (!bfsQueue.isEmpty()) {
            V nextElement = bfsQueue.poll();
            seen.add(nextElement);
            traversal.add(nextElement);

            //add adjacent vertices
            Node<V> current = adjacencyLists.get(nextElement);
            while (current != null) {
                if (!seen.contains(current.vertex)) {
                    bfsQueue.add(current.vertex);
                }
                current = current.next;
            }
        }

        return traversal;
    }

    private void visit(V vertex, Set<V> known, Queue<Edge<V>> heap) {
        known.add(vertex);
        Node<V> current = adjacencyLists.get(vertex);

        while (current != null) {
            if (!known.contains(current.vertex)) {
                heap.add(new Edge<>(vertex, current.vertex, current.weight));
            }
            current = current.next;
        }
    }

    public static class Edge<V> implements Comparable<Edge<V>> {

        private V source;
        private V destination;
        private double weight;

        private Edge(V source, V destination, double weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge<V> other) {
            if (source.equals(other.source) && destination.equals(other.destination)) {
                return 0;
            }
            return -1;
        }

        private V getsource() {
            return source;
        }

        private V getTo() {
            return destination;
        }

        private double getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return source +
                "->" + destination;
        }

    }

    private class Node<V> {

        private V vertex;
        private Node<V> next;
        private double weight;

        private Node(V vertex) {
            this(vertex, null, 0.0);
        }

        private Node(V vertex, double weight) {
            this(vertex, null, weight);
        }

        private Node(V vertex, Node<V> next, double weight) {
            this.vertex = vertex;
            this.next = next;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Node{" +
                "vertex=" + vertex +
                ", next=" + next +
                ", weight=" + weight +
                '}';
        }
    }

    @Override
    public String toString() {
        return "DirectedAL{" +
            "adjacencyLists=" + adjacencyLists +
            ", vertexSize=" + vertexSize +
            ", edgeSize=" + edgeSize +
            '}';
    }
}