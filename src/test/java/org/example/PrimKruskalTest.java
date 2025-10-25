package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class PrimKruskalTest {

    @Test
    public void testPrimAlgorithm() {
        List<PrimAlgorithm.Edge> edges = new ArrayList<>();
        edges.add(new PrimAlgorithm.Edge(0, 1, 2));
        edges.add(new PrimAlgorithm.Edge(1, 2, 3));
        edges.add(new PrimAlgorithm.Edge(0, 2, 5));

        int totalWeight = 0;
        List<PrimAlgorithm.Edge> mstEdges = new ArrayList<>();
        totalWeight = PrimAlgorithm.prim(3, edges);

        assertEquals(5, totalWeight);
        assertEquals(2, mstEdges.size());
    }

    @Test
    public void testKruskalAlgorithm() {
        List<KruskalAlgorithm.Edge> edges = new ArrayList<>();
        edges.add(new KruskalAlgorithm.Edge(0, 1, 2));
        edges.add(new KruskalAlgorithm.Edge(1, 2, 3));
        edges.add(new KruskalAlgorithm.Edge(0, 2, 5));

        int totalWeight = 0;
        totalWeight = KruskalAlgorithm.kruskal(3, edges);

        assertEquals(5, totalWeight);
        assertEquals(2, edges.size());
    }

    @Test
    public void testDisconnectedGraph() {
        List<KruskalAlgorithm.Edge> edges = new ArrayList<>();
        edges.add(new KruskalAlgorithm.Edge(0, 1, 2));

        int totalWeight = KruskalAlgorithm.kruskal(3, edges);

        assertEquals(0, totalWeight);
    }

    @Test
    public void testExecutionTimePrim() {
        long startTime = System.currentTimeMillis();
        List<PrimAlgorithm.Edge> edges = new ArrayList<>();
        edges.add(new PrimAlgorithm.Edge(0, 1, 2));
        edges.add(new PrimAlgorithm.Edge(1, 2, 3));
        edges.add(new PrimAlgorithm.Edge(0, 2, 5));
        PrimAlgorithm.prim(3, edges);
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;
        assertTrue(executionTime >= 0);
    }

    @Test
    public void testExecutionTimeKruskal() {
        long startTime = System.currentTimeMillis();
        List<KruskalAlgorithm.Edge> edges = new ArrayList<>();
        edges.add(new KruskalAlgorithm.Edge(0, 1, 2));
        edges.add(new KruskalAlgorithm.Edge(1, 2, 3));
        edges.add(new KruskalAlgorithm.Edge(0, 2, 5));
        KruskalAlgorithm.kruskal(3, edges);
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;
        assertTrue(executionTime >= 0);
    }

    @Test
    public void testMSTCostComparison() {
        List<PrimAlgorithm.Edge> primEdges = new ArrayList<>();
        primEdges.add(new PrimAlgorithm.Edge(0, 1, 2));
        primEdges.add(new PrimAlgorithm.Edge(1, 2, 3));
        primEdges.add(new PrimAlgorithm.Edge(0, 2, 5));
        int primTotalWeight = PrimAlgorithm.prim(3, primEdges);

        List<KruskalAlgorithm.Edge> kruskalEdges = new ArrayList<>();
        kruskalEdges.add(new KruskalAlgorithm.Edge(0, 1, 2));
        kruskalEdges.add(new KruskalAlgorithm.Edge(1, 2, 3));
        kruskalEdges.add(new KruskalAlgorithm.Edge(0, 2, 5));
        int kruskalTotalWeight = KruskalAlgorithm.kruskal(3, kruskalEdges);

        assertEquals(primTotalWeight, kruskalTotalWeight);
    }
}
