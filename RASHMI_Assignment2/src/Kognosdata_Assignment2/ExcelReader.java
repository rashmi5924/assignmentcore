package Kognosdata_Assignment2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {
	public static Map<String, List<String>> readExcel(String filePath, List<String> keywords) throws IOException {
        
        Map<String, List<String>> results = new HashMap<>();
        for (String keyword : keywords) {
            List<String> positions = new ArrayList<>();
            // Replace the following line with actual logic to find keyword positions in Excel
            positions.add("Row5,Column7");
            positions.add("Row6,Column10");
            results.put(keyword, positions);
        }
        return results;
    }
}
