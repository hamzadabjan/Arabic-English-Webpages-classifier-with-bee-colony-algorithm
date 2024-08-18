/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stanford_nlp;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hamza_Dabjan
 */
public class TokenizeAndRemoveStopWords {

    public static ArrayList<String> tokens;
    public static ArrayList<String> stopWords;
    public String output;

    public String removeStopWords(String text) throws IOException {
        output = "";
        tokens = new ArrayList<>();
        stopWords = new ArrayList<>();
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

        CoreDocument coreDocument = new CoreDocument(text);

        stanfordCoreNLP.annotate(coreDocument);

        List<CoreLabel> coreLabelList = coreDocument.tokens();
        coreLabelList.stream().forEach((coreLabel) -> {
            tokens.add(coreLabel.originalText());
        });
        String s;
        File file = new File("stop_words_english.txt");

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        while ((s = br.readLine()) != null) {
            String[] st = s.split("\n");
            String id = st[0];
            while (tokens.contains(id)) {
                tokens.remove(id);
            }
        }

        tokens.stream().forEach((token) -> {
            output = output + token + " ";
        });
        return output;
    }
}
