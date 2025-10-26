import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> inputData = mapper.readValue(new File("input.json"),
                    new TypeReference<Map<String, Object>>() {});

            List<Map<String, Object>> graphsData = (List<Map<String, Object>>) inputData.get("graphs");
            List<Map<String, Object>> results = new ArrayList<>();

            for (Map<String, Object> graphData : graphsData) {
                int graphId = (Integer) graphData.get("id");
                List<String> nodes = (List<String>) graphData.get("nodes");
                List<Map<String, Object>> edgesData = (List<Map<String, Object>>) graphData.get("edges");

                List<Edge> edges = new ArrayList<>();
                for (Map<String, Object> edgeData : edgesData) {
                    String from = (String) edgeData.get("from");
                    String to = (String) edgeData.get("to");
                    int weight = (Integer) edgeData.get("weight");
                    edges.add(new Edge(from, to, weight));
                }

                Graph graph = new Graph(nodes, edges);

                PrimMST prim = new PrimMST();
                prim.findMST(graph);

                KruskalMST kruskal = new KruskalMST();
                kruskal.findMST(graph);

                Map<String, Object> result = new HashMap<>();
                result.put("graph_id", graphId);

                Map<String, Object> inputStats = new HashMap<>();
                inputStats.put("vertices", graph.getVertexCount());
                inputStats.put("edges", graph.getEdgeCount());
                result.put("input_stats", inputStats);

                result.put("prim", createAlgorithmResult(prim));
                result.put("kruskal", createAlgorithmResult(kruskal));

                results.add(result);
            }

            Map<String, Object> output = new HashMap<>();
            output.put("results", results);

            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("output.json"), output);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> createAlgorithmResult(Object algorithm) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> mstEdges = new ArrayList<>();

        if (algorithm instanceof PrimMST) {
            PrimMST prim = (PrimMST) algorithm;
            for (Edge edge : prim.mstEdges) {
                mstEdges.add(createEdgeMap(edge));
            }
            result.put("mst_edges", mstEdges);
            result.put("total_cost", prim.totalCost);
            result.put("operations_count", prim.operations);
            result.put("execution_time_ms", prim.executionTime);
        } else {
            KruskalMST kruskal = (KruskalMST) algorithm;
            for (Edge edge : kruskal.mstEdges) {
                mstEdges.add(createEdgeMap(edge));
            }
            result.put("mst_edges", mstEdges);
            result.put("total_cost", kruskal.totalCost);
            result.put("operations_count", kruskal.operations);
            result.put("execution_time_ms", kruskal.executionTime);
        }

        return result;
    }

    private static Map<String, Object> createEdgeMap(Edge edge) {
        Map<String, Object> edgeMap = new HashMap<>();
        edgeMap.put("from", edge.from);
        edgeMap.put("to", edge.to);
        edgeMap.put("weight", edge.weight);
        return edgeMap;
    }
}