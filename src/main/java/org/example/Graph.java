package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Graph {

    public static DefaultCategoryDataset createDataset(String filename) {
        DefaultCategoryDataset dataset = new org.example.DefaultCategoryDataset();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    String algorithm = data[0];
                    String graphId = data[1];
                    int totalWeight = Integer.parseInt(data[2]);
                    long executionTime = Long.parseLong(data[3]);
                    int operationCount = Integer.parseInt(data[4]);

                    dataset.addValue(executionTime, "Execution Time", graphId);
                    dataset.addValue(totalWeight, "Total Weight", graphId);
                    dataset.addValue(operationCount, "Operation Count", graphId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataset;
    }

    public static JFreeChart createChart(DefaultCategoryDataset dataset) {
        return ChartFactory.createBarChart(
                "Graph Algorithm Performance",
                "Graph ID",
                "Values",
                dataset
        );
    }

    public static void createChartPanel(JFreeChart chart) {
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new java.awt.Dimension(800, 600));
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        DefaultCategoryDataset dataset = createDataset("results.csv");
        JFreeChart chart = createChart(dataset);
        createChartPanel(chart);
    }
}
