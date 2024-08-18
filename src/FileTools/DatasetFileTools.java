package FileTools;

import Farasa_nlp.LemmatizationArabic;
import Farasa_nlp.RemoveStopWordsArabic;
import Stanford_nlp.BagOfWords;
import Stanford_nlp.Lemmatization;
import Stanford_nlp.TokenizeAndRemoveStopWords;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 * @author Hamza_Dabjan
 */
public class DatasetFileTools {

    public Map<String, Integer> trainingCategoryBag;
    FileInputStream file;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    Stanford_nlp.TokenizeAndRemoveStopWords tokenizeAndRemoveStopWords;
    Farasa_nlp.LemmatizationArabic lemmatize_arabic;
    Stanford_nlp.Lemmatization lemmatize;
    Stanford_nlp.BagOfWords bagOfWords;
    public static ArrayList<String> categoryList;

    public List<String> ExtractCategories(String filePath) throws IOException, InvalidFormatException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath); Workbook workbook = WorkbookFactory.create(fileInputStream)) {

            Set<String> categoriesSet = ConcurrentHashMap.newKeySet();

            // Use parallelStream to process rows concurrently
            workbook.sheetIterator().forEachRemaining(sheet -> {
                sheet.rowIterator().forEachRemaining(row -> {
                    if (row.getRowNum() > 0) {
                        Cell cell = row.getCell(row.getLastCellNum() - 1);
                        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            categoriesSet.add(cell.getStringCellValue());
                        }
                    }
                });
            });
            // Convert the set of categories to a list and return it
            return new ArrayList<>(categoriesSet);
        }
    }

    public String ExtractTextFromXlsX(String categoray, String filePath) throws IOException {
        file = new FileInputStream(new File(filePath));
        workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheetAt(0);
        String allCategoryText = "";
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            String vOutput = ReadCellData(i, 1, sheet);
            // Check if the cell value matches the specified category
            if (vOutput.equals(categoray)) {
                // If it matches, append the value from the first column (index 0) to allCategoryText
                allCategoryText += (ReadCellData(i, 0, sheet)) + " ";
            }
        }
        return allCategoryText;
    }

    public Map<String, String> FetchFileIntoBagOfWords(String filepath) throws IOException, InvalidFormatException {
        try (FileInputStream fileInputStream = new FileInputStream(filepath); Workbook workbook = WorkbookFactory.create(fileInputStream)) {
            // Create a concurrent map to store words and their frequencies
            ConcurrentMap<String, String> fetchCategoryBag = new ConcurrentHashMap<>();

            workbook.sheetIterator().forEachRemaining(sheet -> {
                sheet.rowIterator().forEachRemaining(row -> {
                    int vRow = row.getRowNum();
                    // Read the word from the first column (index 0) and its frequency from the second column (index 1)
                    String word = ReadCellData(vRow, 0, sheet);
                    String freq = ReadCellData(vRow, 1, sheet);
                    // Put the word and its frequency into the concurrent map
                    fetchCategoryBag.put(word, freq);
                });
            });

            return new HashMap<>(fetchCategoryBag);
        }
    }

    private String ReadCellData(int vRow, int vColumn, org.apache.poi.ss.usermodel.Sheet sheet) {
        Row row = sheet.getRow(vRow);
        if (row != null) {
            Cell cell = row.getCell(vColumn);
            if (cell != null) {
                DataFormatter formatter = new DataFormatter();
                return formatter.formatCellValue(cell).toLowerCase();
            }
        }
        return "";
    }

    public Map<String, Integer> GenerateBagOfWords(String category, String filePath) throws IOException {
        tokenizeAndRemoveStopWords = new TokenizeAndRemoveStopWords();
        lemmatize = new Lemmatization();
        bagOfWords = new BagOfWords();
        trainingCategoryBag = new HashMap<>();
        // Extract text for the specified category from the Excel file
        String categoryTexts = ExtractTextFromXlsX(category, filePath);
        // Process the extracted text: remove stop words, lemmatize, and create the bag of words
        trainingCategoryBag = bagOfWords.createBag(lemmatize.lemmatize(tokenizeAndRemoveStopWords.removeStopWords(categoryTexts)));
        return trainingCategoryBag;
    }

    public Map<String, Integer> GenerateBagOfWords_Arabic(String category, String filePath) throws IOException, ClassNotFoundException {

        lemmatize_arabic = new LemmatizationArabic();
        bagOfWords = new BagOfWords();
        trainingCategoryBag = new HashMap<>();
        // Extract text for the specified category from the Excel file
        String categoryTexts = ExtractTextFromXlsX(category, filePath);
        // Process the extracted Arabic text: lemmatize, remove stop words, and create the bag of words
        trainingCategoryBag = bagOfWords.createBag(RemoveStopWordsArabic.removeStopsArabic(lemmatize_arabic.lemmatization(categoryTexts)));
        return trainingCategoryBag;
    }

    public String extractSingleTextFromTestFile(int rowOrder, String filePath) throws IOException {
        file = new FileInputStream(new File(filePath));
        workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheetAt(0);
        String testText = (ReadCellData(rowOrder, 0, sheet)) + " ";
        return testText;
    }

    public ArrayList<String> GetActualClass(String testingDatasetFilePath) throws IOException {
        file = new FileInputStream(testingDatasetFilePath);
        workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheetAt(0);
        ArrayList<String> actualClassList = new ArrayList<>();

        for (Row row : sheet) {

            Cell c = row.getCell(row.getLastCellNum() - 1);

            if (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK || c.getRowIndex() == 0) {

            } else {
                actualClassList.add(c.getStringCellValue());
            }
        }
        return actualClassList;
    }
}
