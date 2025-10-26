import java.util.*;

public class PrimMST {
    public List<Edge> mstEdges;
    public int totalCost;
    public int operations;
    public long executionTime;

    public void findMST(Graph graph) {
        long startTime = System.nanoTime();
        operations = 0;
        mstEdges = new ArrayList<>();
        totalCost = 0;

        if (graph.vertices.isEmpty()) return;

        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        Map<String, List<Edge>> adjList = graph.getAdjacencyList();

        String startVertex = graph.vertices.get(0);
        visited.add(startVertex);
        pq.addAll(adjList.get(startVertex));
        operations += adjList.get(startVertex).size();

        while (!pq.isEmpty() && visited.size() < graph.vertices.size()) {
            operations++;
            Edge edge = pq.poll();

            if (visited.contains(edge.from) && visited.contains(edge.to)) {
                continue;
            }

            String newVertex = visited.contains(edge.from) ? edge.to : edge.from;
            if (!visited.contains(newVertex)) {
                visited.add(newVertex);
                mstEdges.add(edge);
                totalCost += edge.weight;

                for (Edge nextEdge : adjList.get(newVertex)) {
                    operations++;
                    if (!visited.contains(nextEdge.to)) {
                        pq.add(nextEdge);
                    }
                }
            }
        }

        executionTime = (System.nanoTime() - startTime) / 1000000;
    }
}