/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stanford_nlp;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hamza_Dabjan
 */
public class Lemmatization {

    public static ArrayList<String> output;

    public ArrayList<String> lemmatize(String text) {

        output = new ArrayList<>();
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

        CoreDocument coreDocument = new CoreDocument(text);

        stanfordCoreNLP.annotate(coreDocument);

        List<CoreLabel> coreLabelList = coreDocument.tokens();

        coreLabelList.stream().map((coreLabel) -> coreLabel.lemma()).forEach((lemma) -> {
            output.add(lemma);
        });
        return output;
    }

}
