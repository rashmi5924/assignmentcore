package assignment_1;
import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class SwingsUI extends JFrame {
    private JTextArea logTextArea;
    private XYSeries mudWeightData;

    public SwingsUI() {
        // Set up the main frame
        setTitle("Mud Weight Data Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        logTextArea = new JTextArea();
        JButton displayButton = new JButton("Display");
        JButton clearButton = new JButton("Clear");

        // Set up the mud weight data series for the chart
        mudWeightData = new XYSeries("Mud Weight");

        // Set up the chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Mud Weight Data", "Depth", "Mud Weight",
                new XYSeriesCollection(mudWeightData)
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 400));

        // Set up layout
        setLayout(new BorderLayout());
        add(new JScrollPane(logTextArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(displayButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);

        add(chartPanel, BorderLayout.EAST);

        // Add action listeners
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMudWeightData();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearLog();
            }
        });
    }

    private void displayMudWeightData() {
        // Replace this path with the actual path to your CSV file
        String filePath = "C:\\Users\\RASHMI\\eclipse-workspace\\JAVA_ASSIGNMENT_KOGNOSDATA\\src\\resources\\Mud_Weight.csv";

        logTextArea.append("Reading data from: " + filePath + "\n");

        // Read data from CSV file and update the chart
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int count=0;
            while ((line = br.readLine()) != null) {
            	if(count>2) {
                String[] values = line.split(",");
                System.out.println(values[1]);
                double depth = Double.parseDouble(values[0]);
                
                double mudWeight = Double.parseDouble(values[1]);
                mudWeightData.add(depth, mudWeight);
            	}
            	count++;
            }
            logTextArea.append("Data loaded successfully.\n");
        } catch (IOException | NumberFormatException ex) {
            logTextArea.append("Error reading data: " + ex.getMessage() + "\n");
        }
    }

    private void clearLog() {
        logTextArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SwingsUI().setVisible(true);
            }
        });
    }
}
