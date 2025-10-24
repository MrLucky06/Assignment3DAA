package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;
import java.util.*;

public class PrimAlgorithm {

    static class Edge {
        @JsonProperty("from")
        int from;

        @JsonProperty("to")
        int to;

        @JsonProperty("weight")
        int weight;

        public Edge() {}

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

            public Graph() {}

            @JsonCreator
            public Graph(@JsonProperty("id") String id, @JsonProperty("nodes") int nodes, @JsonProperty("edges") List<Edge> edges) {
                this.id = id;
                this.nodes = nodes;
                this.edges = edges;
            }
        }
    }

    public static void prim(int nodes, List<Edge> edges) {
        List<Edge>[] adjList = new ArrayList[nodes];
        for (int i = 0; i < nodes; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (Edge edge : edges) {
            adjList[edge.from].add(edge);
            adjList[edge.to].add(new Edge(edge.to, edge.from, edge.weight));
        }

        int[] minEdge = new int[nodes];
        Arrays.fill(minEdge, Integer.MAX_VALUE);
        minEdge[0] = 0;

        boolean[] inMST = new boolean[nodes];

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        pq.add(new Edge(-1, 0, 0));
        List<Edge> mstEdges = new ArrayList<>();

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            int u = edge.to;

            if (inMST[u]) continue;
            inMST[u] = true;

            if (edge.from != -1) {
                mstEdges.add(edge);
            }

            for (Edge neighbor : adjList[u]) {
                int v = neighbor.to;
                if (!inMST[v] && neighbor.weight < minEdge[v]) {
                    minEdge[v] = neighbor.weight;
                    pq.add(new Edge(u, v, neighbor.weight));
                }
            }
        }

        int totalWeight = 0;
        for (Edge mstEdge : mstEdges) {
            totalWeight += mstEdge.weight;
            System.out.println("Edge: " + mstEdge.from + " - " + mstEdge.to + " Weight: " + mstEdge.weight);
        }
        System.out.println("Total weight of MST: " + totalWeight);
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
