package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class KruskalAlgorithm {

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

    public static void writeResultsToCSV(String algorithm, String graphId, int totalWeight, long executionTime, int operationCount) {
        try {
            FileWriter writer = new FileWriter("results.csv", true);
            writer.append(algorithm)
                    .append(",")
                    .append(graphId)
                    .append(",")
                    .append(String.valueOf(totalWeight))
                    .append(",")
                    .append(String.valueOf(executionTime))
                    .append(",")
                    .append(String.valueOf(operationCount))
                    .append("\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void kruskal(int nodes, List<Edge> edges) {
        long startTime = System.currentTimeMillis();
        int totalWeight = 0;
        int operationCount = 0;

        Collections.sort(edges, Comparator.comparingInt(e -> e.weight));

        int[] parent = new int[nodes];
        int[] rank = new int[nodes];

        for (int i = 0; i < nodes; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        List<Edge> mstEdges = new ArrayList<>();

        for (Edge edge : edges) {
            int rootFrom = find(parent, edge.from);
            int rootTo = find(parent, edge.to);

            if (rootFrom != rootTo) {
                mstEdges.add(edge);
                totalWeight += edge.weight;
                union(parent, rank, rootFrom, rootTo);
            }

            operationCount++;
        }

        long endTime = System.currentTimeMillis();
        writeResultsToCSV("Kruskal", graphId, totalWeight, endTime - startTime, operationCount);

        System.out.println("Kruskal's MST for " + graphId + ":");
        for (Edge edge : mstEdges) {
            System.out.println("Edge: " + edge.from + " - " + edge.to + " Weight: " + edge.weight);
        }
        System.out.println("Total weight of MST: " + totalWeight);
    }

    public static int find(int[] parent, int i) {
        if (parent[i] != i) {
            parent[i] = find(parent, parent[i]);
        }
        return parent[i];
    }

    public static void union(int[] parent, int[] rank, int x, int y) {
        int rootX = find(parent, x);
        int rootY = find(parent, y);

        if (rootX != rootY) {
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }

    public static void main(String[] args) {
        try {
            GraphInput graphInput = GraphInput.fromJson("src/main/resources/input_data.json");
            for (GraphInput.Graph graph : graphInput.graphs) {
                System.out.println("Processing graph ID: " + graph.id);
                kruskal(graph.nodes, graph.edges);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

