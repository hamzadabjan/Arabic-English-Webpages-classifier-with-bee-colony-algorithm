/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Training;

import FileTools.DatasetFileTools;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author Hamza_Dabjan
 */
public class TrainDataSet {

    public Map<String, Integer> trainingCategoryBag;
    public static TrainDataSet trDs;
    public static DatasetFileTools datasetFileTools;
    public static List<String> categoryList;
    public static String trainingDatasetPath;
    private static JTextArea jTextArea1;

    public void setFilePath(String trainingDatasetPath) {
        TrainDataSet.trainingDatasetPath = trainingDatasetPath;
    }
    
    public void setJTextArea(JTextArea jTextArea1) {
        this.jTextArea1=jTextArea1;
        }

    public void train() throws IOException, InvalidFormatException {
        datasetFileTools = new DatasetFileTools();
        // Extract the list of categories from the training dataset file
        categoryList = datasetFileTools.ExtractCategories(trainingDatasetPath);
        // Iterate over each category in the list
        for (String categoryList1 : categoryList) {
            // Generate the bag of words for the current category and create a corresponding file
            createBagOfWordsFile(datasetFileTools.GenerateBagOfWords(categoryList1, trainingDatasetPath), categoryList1);
        }
        

    }

    public void trainArabic() throws IOException, ClassNotFoundException, InvalidFormatException {
        datasetFileTools = new DatasetFileTools();
        // Extract the list of categories from the training dataset file
        categoryList = datasetFileTools.ExtractCategories(trainingDatasetPath);
        // Iterate over each category in the list
        for (String categoryList1 : categoryList) {
            // Generate the bag of words for the current category and create a corresponding file
            createBagOfWordsFile(datasetFileTools.GenerateBagOfWords_Arabic(categoryList1, trainingDatasetPath), categoryList1);
        }
        

    }

    public static void createBagOfWordsFile(Map<String, Integer> data, String name) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Bag of Words");
            int rownum = 0;
            for (String testKey : data.keySet()) {
                Row row = sheet.createRow(rownum++);
                Cell cell_1 = row.createCell(0);
                cell_1.setCellValue(testKey);
                Cell cell_2 = row.createCell(1);
                cell_2.setCellValue(data.get(testKey));
            }
            File datasetNameFolder = new File(  trainingDatasetPath.substring(0, trainingDatasetPath.lastIndexOf('.')));
            if (!datasetNameFolder.exists()) {
                datasetNameFolder.mkdirs(); // Create folder if it doesn't exist
            }
            String filePath = datasetNameFolder.getAbsolutePath() + File.separator + name + ".xlsx";
            try (FileOutputStream out = new FileOutputStream(new File(filePath))) {
                workbook.write(out);
            }
            jTextArea1.append(name + ".xlsx has been created successfully");
            jTextArea1.append("\n");
        }
    }    
}