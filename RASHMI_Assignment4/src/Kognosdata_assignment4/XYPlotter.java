package Kognosdata_assignment4;


import javafx.geometry.Insets;

import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.AbstractAction;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class XYPlotter extends Application{
	private LineChart<Number, Number> lineChart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    
    private XYChart.Series<Number, Number> series;
    private ObservableList<String[]> textData = FXCollections.observableArrayList();
    private TableView<String[]> tableView = new TableView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Create North Panel
        HBox northPanel = createNorthPanel();

        // Create XY Plot
        xAxis = new NumberAxis(0, 90, 10);
        yAxis = new NumberAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100); // Assuming maximum depth value is 100
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                return String.format("%1.0f", object.doubleValue());
            }
        });

        lineChart = new LineChart<>(xAxis, yAxis);
        series = new XYChart.Series<>();
        lineChart.getData().add(series);

     // Add TableView to the Center
        TableColumn<String[], String> dataColumn = new TableColumn<>("Data");
        dataColumn.setCellValueFactory(param -> {
            int index = param.getTableView().getColumns().indexOf(param.getTableColumn());
            return new SimpleStringProperty(param.getValue()[index]);
        });
        tableView.getColumns().add(dataColumn);
        tableView.setItems(textData);
        
        root.setTop(northPanel);
        root.setCenter(lineChart);
        root.setBottom(tableView);

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setTitle("Text File Reader With XY Plot App");
        primaryStage.show();
    }

    private HBox createNorthPanel() {
        HBox panel = new HBox(10);
        panel.setPadding(new Insets(10, 10, 10, 10));
        Button browseButton = new Button("Browse");
        Button plotButton = new Button("Plot");
        Button clearButton = new Button("Clear Rows");
        browseButton.setOnAction(e -> browseFile());
        plotButton.setOnAction(e -> plotData());
        clearButton.setOnAction(e -> clearTable());

        //panel.getChildren().addAll(browseButton, plotButton, clearButton);
        return panel;
    }
   
    private void browseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                textData.clear(); // Clear existing data
                while ((line = br.readLine()) != null) {
                    String[] columns = line.split("\t"); // Assuming tab-separated data
                    textData.add(columns);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void plotData() {
        series.getData().clear();
        for (String[] data : textData) {
            double xValue = Double.parseDouble(data[0]);
            double yValue = Double.parseDouble(data[1]);
            series.getData().add(new XYChart.Data<>(xValue, yValue));
        }
    }

    private void clearTable() {
        textData.clear();
        series.getData().clear();
    }
}
