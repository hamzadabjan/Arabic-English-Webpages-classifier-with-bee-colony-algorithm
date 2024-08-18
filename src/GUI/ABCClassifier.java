/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import ABC.BeeColonyAlgorithm;
import Farasa_nlp.LemmatizationArabic;
import Farasa_nlp.RemoveStopWordsArabic;
import FileTools.DatasetFileTools;
import FileTools.ExtractArticleFromWebpage;
import Stanford_nlp.BagOfWords;
import Stanford_nlp.Lemmatization;
import Stanford_nlp.TokenizeAndRemoveStopWords;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hamza
 */
public class ABCClassifier extends javax.swing.JFrame {

    // Class-wide static instances
    private static final BeeColonyAlgorithm beeColonyAlgorithm = new BeeColonyAlgorithm();
    private static DatasetFileTools fileTools = new DatasetFileTools();
    private static final Training.TrainDataSet trainDataSet = new Training.TrainDataSet();

    // Paths for datasets
    private static String trainingDatasetFilePath = "";
    private static String testingDatasetFilePath = "";

    // Excel handling variables
    private FileInputStream file;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    // Algorithm parameters
    private static int NP;
    private static int limit;
    private static int maxCycle;
    private static int D;
    private static int runtime;
    private static List<String> categoryList;

    // NLP tools
    private Stanford_nlp.TokenizeAndRemoveStopWords tokenizeAndRemoveStopWords;
    private Stanford_nlp.Lemmatization lemmatize;
    private Stanford_nlp.BagOfWords bagOfWords;
    
    // Bag of words maps
    private List<Map<String, Integer>> testTextBagList;
    private Map<String, String> categoryBagOfWords;
    private final Map<String, Map<String, String>> categoryBagOfWordsList = new HashMap<>();

    // Classification settings
    private static String typeOfClassification = "file";
    private static String languageOfClassification = "arabic";

    // URL for classification
    private String url;

    // Actual class list
    private static ArrayList<String> actualClassList;
    private double totalAccu; // Tracks total accuracy over all runs
    String classOverAllRuns; // Stores the best class across all runs

    Image icon = Toolkit.getDefaultToolkit().getImage("bee.png");

    public List<Map<String, Integer>> createBagListForTestFile(String testDatasetPath, int D) throws IOException {
        List<Map<String, Integer>> bagOfWordsList = new ArrayList<>();
        tokenizeAndRemoveStopWords = new TokenizeAndRemoveStopWords();
        lemmatize = new Lemmatization();
        bagOfWords = new BagOfWords();
        fileTools = new DatasetFileTools();
        // Loop through each document in the dataset
        for (int j = 0; j < D; j++) {
            // Extract text for the current document
            String singleTestText = fileTools.extractSingleTextFromTestFile(j + 1, testDatasetPath);
            // Process the text: tokenize, remove stopwords, lemmatize, and convert to Bag of Words
            Map<String, Integer> singleTestTextBag = bagOfWords.createBag(
                    lemmatize.lemmatize(
                            tokenizeAndRemoveStopWords.removeStopWords(singleTestText)
                    )
            );
            // Add the resulting Bag of Words to the list
            bagOfWordsList.add(singleTestTextBag);
        }
        return bagOfWordsList;
    }

    public List<Map<String, Integer>> createBagListForTestFileArabic(String testDatasetPath, int documentCount) throws IOException, ClassNotFoundException {
        List<Map<String, Integer>> bagOfWordsList = new ArrayList<>(documentCount);

        // Initialize necessary tools for processing
        LemmatizationArabic lemmatizeArabic = new LemmatizationArabic();
        BagOfWords bagOfWords = new BagOfWords();
        DatasetFileTools fileTools = new DatasetFileTools();

        // Process each document
        for (int i = 0; i < documentCount; i++) {
            // Extract text for the current document
            String singleTestText = fileTools.extractSingleTextFromTestFile(i + 1, testDatasetPath);

            // Apply stopword removal and lemmatization, then create Bag of Words
            Map<String, Integer> singleTestTextBag = bagOfWords.createBag(
                    RemoveStopWordsArabic.removeStopsArabic(
                            lemmatizeArabic.lemmatization(singleTestText)
                    )
            );
            // Add the processed Bag of Words to the list
            bagOfWordsList.add(singleTestTextBag);
        }
        return bagOfWordsList;
    }

    public ABCClassifier() {
        // Initialize GUI components
        initComponents();

        // Set the JTextArea for displaying output
        trainDataSet.setJTextArea(jTextArea1);

        // Set the window icon image
        setIconImage(icon);

        // Redirect standard output and error streams to the JTextArea
        //PrintStream printStream = new PrintStream(new CustomOutputStream(jTextArea1));
        //System.setOut(printStream);
        //System.setErr(printStream);

        // Load the original icon image
        ImageIcon originalIcon = new ImageIcon("bee.png");

        // Define the dimensions for scaling the icon
        int scaledWidth = 20;
        int scaledHeight = 20;

        // Scale the original icon to the desired dimensions
        Image scaledImage = originalIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

        // Create a new ImageIcon with the scaled image
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Set the scaled icon on the startClassification button
        startClassification.setIcon(scaledIcon);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        functionSelector = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        trainingPathField = new javax.swing.JTextField();
        startTraining = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        npField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        foodNumberField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        limitField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        maxCycleField = new javax.swing.JTextField();
        startClassification = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        testingPathField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        dField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        urlField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jRadioURL = new javax.swing.JRadioButton();
        jRadiofile = new javax.swing.JRadioButton();
        jLabel12 = new javax.swing.JLabel();
        runTimesField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jRadioArabic = new javax.swing.JRadioButton();
        jRadioEnglish = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Muti Language ABC Classifier");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImages(null);

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setToolTipText("");

        jLabel3.setText("NP: The number of colony size (employed bees+onlooker bees)");

        jLabel4.setText("Food Numper = The number of food sources equals the half of the colony size NP/2");

        jLabel13.setText("D: Number of uints to be tested");

        jLabel15.setText("Max Cycle: The number of cycles for foraging");

        jLabel18.setText("Limit: A food source which could not be improved through \"limit\" trials is abandoned by its employed bee");
        jLabel18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel19.setText("Run Times: The number of times the algorithm is executed ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel19))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel16.setText("Training Dataset File Path:");

        trainingPathField.setText("arabic_train_dataset_SANAD.xlsx");
        trainingPathField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trainingPathFieldActionPerformed(evt);
            }
        });

        startTraining.setText("Start Training");
        startTraining.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTrainingActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Training Phase");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainingPathField))
                    .addComponent(startTraining, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trainingPathField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(startTraining)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setText("Control Parameters For Testing Phase");

        jLabel1.setText("NP:");

        npField.setText("50");
        npField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                npFieldActionPerformed(evt);
            }
        });
        npField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                npFieldKeyTyped(evt);
            }
        });

        jLabel6.setText("Food Number");
        jLabel6.setEnabled(false);

        foodNumberField.setEditable(false);
        foodNumberField.setEnabled(false);
        foodNumberField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foodNumberFieldActionPerformed(evt);
            }
        });

        jLabel7.setText("Limit:");

        limitField.setText("100");
        limitField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limitFieldActionPerformed(evt);
            }
        });
        limitField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                limitFieldKeyTyped(evt);
            }
        });

        jLabel8.setText("Max Cycle:");

        maxCycleField.setText("200");
        maxCycleField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maxCycleFieldActionPerformed(evt);
            }
        });
        maxCycleField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                maxCycleFieldKeyTyped(evt);
            }
        });

        startClassification.setText("Start Calssifying");
        startClassification.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                startClassificationMouseClicked(evt);
            }
        });
        startClassification.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startClassificationActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel9.setText("Testing Dataset File Path:");

        testingPathField.setText("SANAD_test.xlsx");
        testingPathField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testingPathFieldActionPerformed(evt);
            }
        });

        jLabel10.setText("D");
        jLabel10.setEnabled(false);

        dField.setEditable(false);
        dField.setEnabled(false);
        dField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dFieldActionPerformed(evt);
            }
        });

        jLabel11.setText("Enter A Web page URL:");

        urlField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlFieldActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel2.setText("OR Test a predefined texts:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Testing Phase");

        functionSelector.add(jRadioURL);
        jRadioURL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioURLActionPerformed(evt);
            }
        });

        functionSelector.add(jRadiofile);
        jRadiofile.setSelected(true);
        jRadiofile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadiofileActionPerformed(evt);
            }
        });

        jLabel12.setText("Run Times:");

        runTimesField.setText("1");
        runTimesField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runTimesFieldActionPerformed(evt);
            }
        });
        runTimesField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                runTimesFieldKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(startClassification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(maxCycleField, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                                .addComponent(npField, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(limitField, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(runTimesField, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(foodNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dField, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel14)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jRadioURL)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jRadiofile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(testingPathField, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(urlField))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(urlField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jRadioURL))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(testingPathField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jRadiofile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(npField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(foodNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(limitField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(dField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(maxCycleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(runTimesField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator1))
                .addGap(18, 18, 18)
                .addComponent(startClassification)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton2.setText("Clear");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        jLabel20.setText("Select Language:");

        buttonGroup1.add(jRadioArabic);
        jRadioArabic.setSelected(true);
        jRadioArabic.setText("Arabic");
        jRadioArabic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioArabicActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioEnglish);
        jRadioEnglish.setText("English");
        jRadioEnglish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioEnglishActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addGap(18, 18, 18)
                .addComponent(jRadioArabic)
                .addGap(18, 18, 18)
                .addComponent(jRadioEnglish)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jRadioArabic)
                    .addComponent(jRadioEnglish))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void trainingPathFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trainingPathFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_trainingPathFieldActionPerformed

    private void startTrainingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startTrainingActionPerformed
        long startTime = System.nanoTime();
        // Check if the training data path is provided
        if (trainingPathField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please provide the training data set path.",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if the path is not provided
        }

        // Set the training dataset file path
        trainingDatasetFilePath = trainingPathField.getText();
        trainDataSet.setFilePath(trainingDatasetFilePath);

        try {
            // Choose training method based on language
            switch (languageOfClassification.toLowerCase()) {
                case "english" -> trainDataSet.train();
                case "arabic" -> trainDataSet.trainArabic();
                default -> {
                    JOptionPane.showMessageDialog(this,
                            "Unsupported language for classification.",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        } catch (IOException | InvalidFormatException ex) {
            Logger.getLogger(ABCClassifier.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,
                    "An error occurred during training.",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ABCClassifier.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,
                    "Class not found during training.",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Calculate and display the time taken for training
        long endTime = System.nanoTime();
        long durationInMillis = (endTime - startTime) / 1_000_000;
        jTextArea1.append("Training took " + durationInMillis + " milliseconds\n");

    }//GEN-LAST:event_startTrainingActionPerformed

    private void jRadioArabicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioArabicActionPerformed
        languageOfClassification = "arabic";
        trainingPathField.setText("arabic_train_dataset_SANAD.xlsx");
        testingPathField.setText("SANAD_test.xlsx");
    }//GEN-LAST:event_jRadioArabicActionPerformed

    private void jRadioEnglishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioEnglishActionPerformed
        // Set language and dataset paths
        languageOfClassification = "english";
        trainingPathField.setText("english_train_dataset_BBC News Archive.xlsx");
        testingPathField.setText("BBC News Archive_test.xlsx");
    }//GEN-LAST:event_jRadioEnglishActionPerformed

    private void npFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_npFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_npFieldActionPerformed

    private void npFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_npFieldKeyTyped

        char c = evt.getKeyChar();
        if (!((c >= '0') && (c <= '9')
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE))) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_npFieldKeyTyped

    private void foodNumberFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foodNumberFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_foodNumberFieldActionPerformed

    private void limitFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limitFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_limitFieldActionPerformed

    private void limitFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_limitFieldKeyTyped
        char c = evt.getKeyChar();
        if (!((c >= '0') && (c <= '9')
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE))) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_limitFieldKeyTyped

    private void maxCycleFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxCycleFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maxCycleFieldActionPerformed

    private void maxCycleFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_maxCycleFieldKeyTyped
        char c = evt.getKeyChar();
        if (!((c >= '0') && (c <= '9')
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE))) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_maxCycleFieldKeyTyped

    private void startClassificationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startClassificationMouseClicked

    }//GEN-LAST:event_startClassificationMouseClicked

    private void startClassificationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startClassificationActionPerformed
        totalAccu = 0;  // Initialize total accuracy to 0
        long startTime = System.nanoTime();  // Record the start time for the entire process
        long processingTimeStart, processingTimeEnd;  // Variables to store the start and end time for processing

        trainingDatasetFilePath = trainingPathField.getText();  // Get the training dataset file path from the input field
        String datasetName = trainingDatasetFilePath;  // Assign the file path to a variable for easier reference
        File datasetNameFolder = new File(datasetName.substring(0, datasetName.lastIndexOf('.')));  // Create a File object representing the folder of the dataset

        // Check if required fields are empty, and show an error message if they are
        if (areRequiredFieldsEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;  // Exit the method if fields are empty
        }

        // Handle the classification based on the type selected
        switch (typeOfClassification.toLowerCase()) {
            case "url" ->
                handleURLClassification(startTime);  // Handle URL classification
            case "file" ->
                handleFileClassification();  // Handle file classification
            default -> {
                JOptionPane.showMessageDialog(this, "Unsupported classification type.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;  // Exit if the classification type is unsupported
            }
        }

        processingTimeStart = System.nanoTime();  // Record the start time for processing

        initializeBeeColonyAlgorithmParameters();  // Initialize the parameters for the Bee Colony Algorithm
        extractCategoriesAndBagOfWords(datasetNameFolder);  // Extract categories and create the bag of words from the dataset

        beeColonyAlgorithm.setCategoryBagOfWordsList(categoryBagOfWordsList);  // Set the bag of words list for the algorithm

        try {
            // Create a bag list for the test file based on the selected language
            testTextBagList = switch (languageOfClassification.toLowerCase()) {
                case "english" ->
                    createBagListForTestFile(testingDatasetFilePath, D);  // For English
                case "arabic" ->
                    createBagListForTestFileArabic(testingDatasetFilePath, D);  // For Arabic
                default ->
                    throw new IllegalArgumentException("Unsupported language: " + languageOfClassification);  // Throw an error if the language is unsupported
            };
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ABCClassifier.class.getName()).log(Level.SEVERE, null, ex);  // Log any exceptions that occur
        }

        beeColonyAlgorithm.setTestTextBagList(testTextBagList);  // Set the test text bag list for the algorithm
        processingTimeEnd = System.nanoTime();  // Record the end time for processing

        // Append the time taken for text mining and NLP to the text area
        jTextArea1.append("\nTime for text mining and NLP: " + ((processingTimeEnd - processingTimeStart) / 1000000) + " milliseconds\n");

        try {
            doABC();  // Perform the ABC (Artificial Bee Colony) algorithm
        } catch (IOException ex) {
            Logger.getLogger(ABCClassifier.class.getName()).log(Level.SEVERE, null, ex);  // Log any exceptions that occur during ABC execution
        }

        long endTime = System.nanoTime();  // Record the end time for the entire process

    // Append the total execution time to the text area
        jTextArea1.append("\nTime to execute: " + ((endTime - startTime) / 1000000) + " milliseconds\n");
    }

    private boolean areRequiredFieldsEmpty() {
        return testingPathField.getText().isEmpty() || trainingPathField.getText().isEmpty()
                || npField.getText().isEmpty() || limitField.getText().isEmpty()
                || maxCycleField.getText().isEmpty() || runTimesField.getText().isEmpty();
    }

    private void handleURLClassification(long startTime) {
        url = urlField.getText();
        ExtractArticleFromWebpage extractArticle = new ExtractArticleFromWebpage();

        try {
            extractArticle.Extract(url);
            testingDatasetFilePath = "url_test.xlsx";
            long extractTime = System.nanoTime();
            jTextArea1.append("Time to extract text: " + ((extractTime - startTime) / 1000000) + "ms\n");
        } catch (IOException ex) {
            Logger.getLogger(ABCClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleFileClassification() {
        try {
            testingDatasetFilePath = testingPathField.getText();
            fileTools = new DatasetFileTools();
            actualClassList = fileTools.GetActualClass(testingDatasetFilePath);
        } catch (IOException ex) {
            Logger.getLogger(ABCClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initializeBeeColonyAlgorithmParameters() {
        try {
            file = new FileInputStream(new File(testingDatasetFilePath));
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheetAt(0);
            D = sheet.getLastRowNum();

            dField.setText(String.valueOf(D));

            NP = Integer.parseInt(npField.getText());
            foodNumberField.setText(String.valueOf(NP / 2));

            limit = Integer.parseInt(limitField.getText());
            maxCycle = Integer.parseInt(maxCycleField.getText());
            runtime = Integer.parseInt(runTimesField.getText());

            beeColonyAlgorithm.setTestDatasetPath(testingDatasetFilePath);
            beeColonyAlgorithm.setFoodNumber(NP / 2);
            beeColonyAlgorithm.setLimit(limit);
            beeColonyAlgorithm.setMaxCycle(maxCycle);
            beeColonyAlgorithm.setD(D);
            beeColonyAlgorithm.setRuntime(runtime);
        } catch (IOException ex) {
            Logger.getLogger(ABCClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void extractCategoriesAndBagOfWords(File datasetNameFolder) {
        try {
            // Extract the list of categories from the training dataset file
            categoryList = fileTools.ExtractCategories(trainingDatasetFilePath);

            // Set the extracted category list in the Bee Colony Algorithm
            beeColonyAlgorithm.setCategoryList(categoryList);

            // Iterate through each category in the list
            for (String category : categoryList) {
                fileTools = new DatasetFileTools();  // Reinitialize the file tools for each category

                // Fetch the bag of words for the current category from the corresponding Excel file
                categoryBagOfWords = fileTools.FetchFileIntoBagOfWords(datasetNameFolder + File.separator + category + ".xlsx");

                // Add the fetched bag of words to the category bag of words list
                categoryBagOfWordsList.put(category, categoryBagOfWords);
            }
        } catch (IOException | InvalidFormatException ex) {
            // Log any IO or format-related exceptions that occur
            Logger.getLogger(ABCClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_startClassificationActionPerformed

    private void testingPathFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testingPathFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_testingPathFieldActionPerformed

    private void dFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dFieldActionPerformed

    private void urlFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_urlFieldActionPerformed

    private void jRadioURLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioURLActionPerformed
        typeOfClassification = "URL";
    }//GEN-LAST:event_jRadioURLActionPerformed

    private void jRadiofileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadiofileActionPerformed
        typeOfClassification = "file";
    }//GEN-LAST:event_jRadiofileActionPerformed

    private void runTimesFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runTimesFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_runTimesFieldActionPerformed

    private void runTimesFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_runTimesFieldKeyTyped
        char c = evt.getKeyChar();
        if (!((c >= '0') && (c <= '9')
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE))) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_runTimesFieldKeyTyped

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // clears the text area
        try {
            jTextArea1.getDocument().remove(0,
                    jTextArea1.getDocument().getLength());
            System.out.println();
        } catch (BadLocationException ex) {
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ABCClassifier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ABCClassifier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ABCClassifier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ABCClassifier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ABCClassifier().setVisible(true);
        });
    }

    public void doABC() throws IOException {
        double maxOptimumFitnessOverAllRuns = 0; // Tracks the highest fitness value across all runs
        
        String[] classOverAllRunsInCaseOfFile = new String[D]; // Stores the best class for each instance in case of file classification

        long allRunsTime = 0; // Tracks the total time taken for all runs

        beeColonyAlgorithm.setVariable(); // Initialize variables required for the algorithm

        // Loop over the number of runs specified by the user
        for (int run = 0; run < runtime; run++) {
            long runTimeStart = System.nanoTime(); // Start timer for the current run
            System.out.println("*** Run # " + (run + 1) + " ***");

            runABC(); // Execute the ABC algorithm for this run
            // Update the best fitness and class found so far
            maxOptimumFitnessOverAllRuns = updateMaxFitnessAndClass(run, maxOptimumFitnessOverAllRuns, classOverAllRunsInCaseOfFile);

            long runTimeEnd = System.nanoTime(); // End timer for the current run
            System.out.println("*** This Run Took " + ((runTimeEnd - runTimeStart) / 1000000) + " milliseconds ***");
            allRunsTime += (runTimeEnd - runTimeStart); // Accumulate the total time taken
        }

        // Display the final results after all runs have been completed
        displayFinalResults(maxOptimumFitnessOverAllRuns, classOverAllRuns, classOverAllRunsInCaseOfFile, totalAccu, allRunsTime);
    }

    private void runABC() throws IOException {
        beeColonyAlgorithm.initial(); // Initialize the food sources (solutions)
        beeColonyAlgorithm.MemorizeBestSource(); // Memorize the best food source found so far

        // Perform the main optimization loop
        for (int iter = 0; iter < maxCycle; iter++) {
            System.out.println("Cycle#:" + (iter + 1));

            // Perform the employed bees phase
            beeColonyAlgorithm.SendEmployedBees();

            // Calculate the probabilities for onlooker bees
            beeColonyAlgorithm.CalculateProbabilities();

            // Perform the onlooker bees phase
            beeColonyAlgorithm.SendOnlookerBees();

            // Memorize the best food source again after onlooker phase
            beeColonyAlgorithm.MemorizeBestSource();

            // Perform the scout bees phase
            beeColonyAlgorithm.SendScoutBees();

            System.out.println("*** Cycle#:" + (iter + 1) + " Ends ***");
        }
    }

    private double updateMaxFitnessAndClass(int run, double maxOptimumFitnessOverAllRuns, String[] classOverAllRunsInCaseOfFile) {
        double trueClasses = 0; // Track the number of correctly classified instances
        classOverAllRuns = null; // Stores the best class across all runs
        // Handle different types of classification (URL or file-based)
        switch (typeOfClassification.toLowerCase()) {
            case "url" -> {
                double maxFitness = beeColonyAlgorithm.getOptimumMax(); // Get the maximum fitness from this run
                System.out.println("*** Max Fitness Value In This Run Is: " + maxFitness + " ***");
                String currentClass = beeColonyAlgorithm.getOptimumParams()[0]; // Get the class corresponding to the best solution

                // Update the best class if this run's fitness is the highest so far
                if (maxFitness > maxOptimumFitnessOverAllRuns) {
                    maxOptimumFitnessOverAllRuns = maxFitness;
                    classOverAllRuns = currentClass;
                }
            }

            case "file" -> trueClasses = evaluateFileClassification(run, trueClasses, classOverAllRunsInCaseOfFile);
        }

        return maxOptimumFitnessOverAllRuns; // Return the updated maximum fitness
    }

    private double evaluateFileClassification(int run, double trueClasses, String[] classOverAllRunsInCaseOfFile) {
        System.out.println("Max Fitness Value In This Run Is: " + beeColonyAlgorithm.getOptimumMax());
        jTextArea1.append("Actual Class------Predicted Class");

        // Compare predicted classes to actual classes and count the correct classifications
        for (int j = 0; j < D; j++) {
            String actualClass = actualClassList.get(j);
            String predictedClass = beeColonyAlgorithm.getOptimumParams()[j];
            System.out.println(actualClass + "-----" + predictedClass);

            if (actualClass.equals(predictedClass)) {
                trueClasses++;
            }
        }

        double singleRunAccu = (trueClasses / D) * 100; // Calculate accuracy for this run
        System.out.println("Percentage is: " + singleRunAccu + "%");
        totalAccu += singleRunAccu; // Accumulate total accuracy
        double maxOptimumFitnessOverAllRuns = 0;

        // Update the best classes if this run's fitness is the highest so far
        if (beeColonyAlgorithm.getOptimumMax() > maxOptimumFitnessOverAllRuns) {
            System.arraycopy(beeColonyAlgorithm.getOptimumParams(), 0, classOverAllRunsInCaseOfFile, 0, D);
        }

        return trueClasses; // Return the number of correct classifications
    }

    private void displayFinalResults(double maxOptimumFitnessOverAllRuns, String classOverAllRuns, String[] classOverAllRunsInCaseOfFile, double totalAccu, long allRunsTime) {
        // Display the results for URL classification
        if (typeOfClassification.equalsIgnoreCase("URL")) {
            System.out.println("-------------- ");
            System.out.println("Max Fitness Value Over All Runs Is: " + maxOptimumFitnessOverAllRuns);
            jTextArea1.append("\nThe URL's Class is: " + classOverAllRuns);
            jTextArea1.append("\nEach Run in ABC Took on Average " + ((allRunsTime / runtime) / 1000000) + " milliseconds\n");
            jTextArea1.append("-------------- ");
        }

        // Display the results for file-based classification
        if (typeOfClassification.equalsIgnoreCase("file")) {
            for (int j = 0; j < D; j++) {
                jTextArea1.append("\n#" + (j + 1) + "-> " + actualClassList.get(j) + "-----" + classOverAllRunsInCaseOfFile[j]);
            }
            jTextArea1.append("\nAverage Accuracy is: " + totalAccu / runtime + "%");
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField dField;
    private javax.swing.JTextField foodNumberField;
    private javax.swing.ButtonGroup functionSelector;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioArabic;
    private javax.swing.JRadioButton jRadioEnglish;
    private javax.swing.JRadioButton jRadioURL;
    private javax.swing.JRadioButton jRadiofile;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField limitField;
    private javax.swing.JTextField maxCycleField;
    private javax.swing.JTextField npField;
    private javax.swing.JTextField runTimesField;
    private javax.swing.JButton startClassification;
    private javax.swing.JButton startTraining;
    private javax.swing.JTextField testingPathField;
    private javax.swing.JTextField trainingPathField;
    private javax.swing.JTextField urlField;
    // End of variables declaration//GEN-END:variables
}
