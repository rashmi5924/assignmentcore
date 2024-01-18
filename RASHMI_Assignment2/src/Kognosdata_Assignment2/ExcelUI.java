package Kognosdata_Assignment2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ExcelUI {
	private static JTable resultsTable;
	public static void main(String[] args) {
        JFrame frame = new JFrame("Excel Reader UI");
        JPanel northPanel = createNorthPanel();
        resultsTable = new JTable();

        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static JPanel createNorthPanel() {
        JPanel panel = new JPanel();
        JLabel filePathLabel = new JLabel("Excel File Path:");
        JTextField filePathTextField = new JTextField(10);
        JButton browseButton = new JButton("Browse");
        JTextField searchField = new JTextField(10);
        JButton searchButton = new JButton("Search");

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    filePathTextField.setText(filePath);
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String filePath = filePathTextField.getText();
                    String keywords = searchField.getText();
                    List<String> keywordList = extractKeywords(keywords);

                    if (!filePath.isEmpty() && !keywordList.isEmpty()) {
                        Map<String, List<String>> results = ExcelReader.readExcel(filePath, keywordList);
                        displayResults(results, resultsTable);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        panel.add(filePathLabel);
        panel.add(filePathTextField);
        panel.add(browseButton);
        panel.add(new JLabel("Search Keyword(s):"));
        panel.add(searchField);
        panel.add(searchButton);

        return panel;
    }

    private static List<String> extractKeywords(String keywords) {
        List<String> keywordList = new ArrayList<>();
        String[] keywordsArray = keywords.split(",");
        for (String keyword : keywordsArray) {
            keywordList.add(keyword.trim());
        }
        return keywordList;
    }

    private static void displayResults(Map<String, List<String>> results, JTable table) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Keyword", "Row", "Column"}, 0);
        for (Map.Entry<String, List<String>> entry : results.entrySet()) {
            String keyword = entry.getKey();
            List<String> positions = entry.getValue();
            for (String position : positions) {
                String[] positionArray = position.split(",");
                model.addRow(new Object[]{keyword, positionArray[0], positionArray[1]});
            }
        }
        table.setModel(model);
    }
}

