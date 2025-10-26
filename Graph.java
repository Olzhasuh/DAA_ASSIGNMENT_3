import java.util.*;

public class Graph {
    public List<String> vertices;
    public List<Edge> edges;

    public Graph(List<String> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public Map<String, List<Edge>> getAdjacencyList() {
        Map<String, List<Edge>> adjList = new HashMap<>();
        for (String vertex : vertices) {
            adjList.put(vertex, new ArrayList<>());
        }
        for (Edge edge : edges) {
            adjList.get(edge.from).add(edge);
            adjList.get(edge.to).add(new Edge(edge.to, edge.from, edge.weight));
        }
        return adjList;
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        return edges.size();
    }
}