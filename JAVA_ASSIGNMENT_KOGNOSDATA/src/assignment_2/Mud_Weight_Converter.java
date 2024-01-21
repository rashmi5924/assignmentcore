package assignment_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
// 2nd assignment
public class Mud_Weight_Converter {
	public static void main(String[] args) {
        String inputFile = "C:\\Users\\RASHMI\\eclipse-workspace\\JAVA_ASSIGNMENT_KOGNOSDATA\\src\\resources\\Mud_Weight.csv";
        String outputFile = "C:\\Users\\RASHMI\\eclipse-workspace\\JAVA_ASSIGNMENT_KOGNOSDATA\\src\\resources\\Mud_Weight_Converted.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            int count=0;
            bw.write("MD, Mud_Weight \n");
            bw.write("m, (kg/m3)\n");
            while ((line = br.readLine()) != null) {
            	if(count>2) {
                String[] values = line.split(",");
                
                // Assuming the second column is in "lbm/galUS"
                double depth = Double.parseDouble(values[0]);
                double mudWeightLbmPerGal = Double.parseDouble(values[1]);

                
                double mudWeightKgPerM3 = mudWeightLbmPerGal * 119.826427;

                // Write converted data to the new CSV file
                bw.write(String.format("%.2f, %.2f%n", depth, mudWeightKgPerM3));
            	}
            	count++;
            	count++;
            }

            System.out.println("Conversion completed. Converted data saved to: " + outputFile);
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
}
