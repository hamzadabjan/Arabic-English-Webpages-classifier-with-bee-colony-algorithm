/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Farasa_nlp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *
 * @author Hamza_Dabjan
 */
public class RemoveStopWordsArabic {

    public static ArrayList<String> removeStopsArabic(ArrayList<String> tokens) throws IOException {
        // Read stop words from the file and store them in a set for efficient lookup
        Set<String> stopWordsSet = readStopWords();

        // Regular expression pattern to match tokens containing numbers, special characters, or Latin letters
        String regex = ".*[0-9\\p{Punct}\\p{Alpha}].*";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Iterate over the tokens and remove stop words and tokens matching the pattern
        Iterator<String> iterator = tokens.iterator();
        while (iterator.hasNext()) {
            String token = iterator.next();
            if (stopWordsSet.contains(token) || pattern.matcher(token).matches()) {
                iterator.remove(); // Remove stop words and tokens matching the pattern
            }
        }

        return tokens;
    }

    private static Set<String> readStopWords() throws IOException {
        Set<String> stopWordsSet = new HashSet<>();
        File file = new File("stop_words_arabic.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stopWordsSet.add(line.trim()); // Add stop words to the set
            }
        }
        return stopWordsSet;
    }
}
