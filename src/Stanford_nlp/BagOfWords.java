/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stanford_nlp;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 *
 * @author Hamza_Dabjan
 */
public class BagOfWords {

    /**
     * The mapping from feature words to indices.
     */
    public Map<String, Integer> words;

    /**
     * Constructor.
     *
     * @param wordsList
     * @return
     */
    public Map<String, Integer> createBag(List<String> wordsList) {
        Map<String, Integer> words = new ConcurrentHashMap<>();

        wordsList.parallelStream()
                // Group words by themselves and sum their occurrences
                .collect(Collectors.groupingByConcurrent(w -> w, Collectors.summingInt(w -> 1)))
                // Iterate over the grouped words and their frequencies
                .forEach((word, frequency) -> {
                    // Add words with a frequency greater than 1 to the map
                    if (frequency > 1) {
                        words.put(word, frequency);
                    }
                });

        return words;
    }
}
