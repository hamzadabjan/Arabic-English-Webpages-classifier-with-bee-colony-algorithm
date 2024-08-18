/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileTools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Hamza_Dabjan
 */
public class ExtractArticleFromWebpage {

    /**
     * @param url
     * @throws java.io.IOException
     */
    public void Extract(String url) throws IOException {
        String text = "";

        // Connect to the specified URL and fetch the HTML document
        Document doc = Jsoup.connect(url).get();

        // Get all <p> (paragraph) elements from the HTML document
        Elements element = doc.getElementsByTag("p");

        // Iterate over the first 10 <p> elements (or fewer if there are less than 10)
        for (Element el : element.subList(0, Math.min(10, element.size()))) {
            text += el.text().replace("\r\n", " ").replace("\n", " ");
        }
        // Call a method to create a bag of words file using the accumulated text
        createBagOfWordsFile(text);
    }

    public static void createBagOfWordsFile(String text) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Test");
        Row row1 = sheet.createRow(0);
        Cell cell_1_1 = row1.createCell(0);
        cell_1_1.setCellValue("News");
        Row row2 = sheet.createRow(1);
        Cell cell_2_1 = row2.createCell(0);
        cell_2_1.setCellValue(text);
        try {
            try (
                    FileOutputStream out = new FileOutputStream(new File("url_test" + ".xlsx"))) {
                workbook.write(out);
            }
        } catch (IOException e) {
        } finally {
            workbook.close();
        }
    }
}
