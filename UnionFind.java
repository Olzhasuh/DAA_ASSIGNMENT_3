public class UnionFind {
    private Map<String, String> parent;
    private Map<String, Integer> rank;
    public int operations;

    public UnionFind(List<String> vertices) {
        parent = new HashMap<>();
        rank = new HashMap<>();
        operations = 0;
        for (String vertex : vertices) {
            parent.put(vertex, vertex);
            rank.put(vertex, 0);
        }
    }

    public String find(String x) {
        operations++;
        if (!parent.get(x).equals(x)) {
            parent.put(x, find(parent.get(x)));
        }
        return parent.get(x);
    }

    public boolean union(String x, String y) {
        operations += 2;
        String rootX = find(x);
        String rootY = find(y);

        if (rootX.equals(rootY)) {
            return false;
        }

        operations++;
        if (rank.get(rootX) < rank.get(rootY)) {
            parent.put(rootX, rootY);
        } else if (rank.get(rootX) > rank.get(rootY)) {
            parent.put(rootY, rootX);
        } else {
            parent.put(rootY, rootX);
            rank.put(rootX, rank.get(rootX) + 1);
            operations++;
        }
        return true;
    }
}