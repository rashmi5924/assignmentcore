package Kognosdata_assignment1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FileReaders {
	public static List<List<String>> readTextFile(String filePath) throws IOException {
        List<List<String>> columns = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columnData = line.split("\\t"); // Assuming tab-separated data
                for (int i = 0; i < columnData.length; i++) {
                    if (columns.size() <= i) {
                        columns.add(new ArrayList<>());
                    }
                    columns.get(i).add(columnData[i]);
                }
            }
        }
        return columns;
    }
	static void clearTable(JTable table) {
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0);
	}
	/*
    public static List<String> readTextFile(String filePath) throws IOException {
    	FileReader fr = new FileReader(filePath);
    	BufferedReader br = new BufferedReader(fr);
        List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        return lines;
    }*/
}
