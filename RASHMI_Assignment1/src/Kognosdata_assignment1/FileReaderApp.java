package Kognosdata_assignment1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class FileReaderApp {
	public static void main(String[] args) {
        JFrame frame = new JFrame("File Reader UI");
        JPanel panel = new JPanel();

        JLabel filePathLabel = new JLabel("File Path:");
        JTextField filePathTextField = new JTextField(20);
        JButton browseButton = new JButton("Browse");
        JTable table = new JTable();
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileReaders.clearTable(table);
            }
        });

        panel.add(clearButton);
        
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    filePathTextField.setText(filePath);
                    try {
                        List<List<String>> columns = FileReaders.readTextFile(filePath);
                        displayDataInTable(columns, table);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        /*
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    filePathTextField.setText(filePath);
                    try {
                        List<String> lines = FileReaders.readTextFile(filePath);
                        displayDataInTable(lines, table);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });*/

        panel.add(filePathLabel);
        panel.add(filePathTextField);
        panel.add(browseButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
	private static void displayDataInTable(List<List<String>> data, JTable table) {
        // Assuming data is a list of lists of strings, representing columns
        // You may need to adjust the table model based on your specific requirements.
        String[][] rowData = new String[data.get(0).size()][data.size()];
        for (int i = 0; i < data.size(); i++) {
            List<String> columnData = data.get(i);
            for (int j = 0; j < columnData.size(); j++) {
                rowData[j][i] = columnData.get(j);
            }
        }

        String[] columnNames = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            columnNames[i] = "Column " + (i + 1);
        }

        DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
        table.setModel(model);
    }
   /*
    private static void displayDataInTable(List<String> data, JTable table) {
        // Assuming data is a list of strings, you may need to adjust the data structure
        // and table model based on your specific file format.
        String[][] rowData = new String[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            rowData[i][0] = data.get(i);
        }

        String[] columnNames = {"Data"};
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
        table.setModel(model);
    }*/
}

