package assignment_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

public class CSV_READER {
	static String csvFile = "C:\\Users\\RASHMI\\eclipse-workspace\\JAVA_ASSIGNMENT_KOGNOSDATA\\src\\resources\\Mud_Weight.csv";
	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into fields based on the CSV delimiter (comma)
                String[] fields = line.split(",");
                
                // Access each field as needed
                String depth = fields[0];
                String mudWeight = fields[1];

                // Process the data as needed (e.g., convert to other units, etc.)
                System.out.println("Depth: " + depth + ", Mud Weight: " + mudWeight);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
