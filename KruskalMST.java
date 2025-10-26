import java.util.*;

public class KruskalMST {
    public List<Edge> mstEdges;
    public int totalCost;
    public int operations;
    public long executionTime;

    public void findMST(Graph graph) {
        long startTime = System.nanoTime();
        operations = 0;
        mstEdges = new ArrayList<>();
        totalCost = 0;

        List<Edge> sortedEdges = new ArrayList<>(graph.edges);
        Collections.sort(sortedEdges);
        operations += sortedEdges.size() * (int)(Math.log(sortedEdges.size()) / Math.log(2));

        UnionFind uf = new UnionFind(graph.vertices);

        for (Edge edge : sortedEdges) {
            operations++;
            if (uf.union(edge.from, edge.to)) {
                mstEdges.add(edge);
                totalCost += edge.weight;
                if (mstEdges.size() == graph.vertices.size() - 1) {
                    break;
                }
            }
        }

        operations += uf.operations;
        executionTime = (System.nanoTime() - startTime) / 1000000;
    }
}