import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.File;
import java.util.List;

public class PrimAlgorithm {

    static class Edge {
        int from, to, weight;

        public Edge() {
        }

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class GraphInput {
        public List<Graph> graphs;

        public static GraphInput fromJson(String filePath) throws Exception {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File(filePath), GraphInput.class);
        }

        static class Graph {
            public String id;
            public int nodes;
            public List<Edge> edges;

            public Graph() {
            }

            @JsonCreator
            public Graph(@JsonProperty("id") String id, @JsonProperty("nodes") int nodes, @JsonProperty("edges") List<Edge> edges) {
                this.id = id;
                this.nodes = nodes;
                this.edges = edges;
            }
        }
    }

    public static void prim(int nodes, List<Edge> edges) {
    }

    public static void main(String[] args) {
        try {
            GraphInput graphInput = GraphInput.fromJson("src/main/resources/input_data.json");
            for (GraphInput.Graph graph : graphInput.graphs) {
                System.out.println("Processing graph ID: " + graph.id);
                prim(graph.nodes, graph.edges);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
