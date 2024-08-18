/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Farasa_nlp;

import com.qcri.farasa.segmenter.Farasa;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Hamza_Dabjan
 */
public class LemmatizationArabic {

    Farasa farasa;

    public ArrayList<String> lemmatization(String text) throws IOException, ClassNotFoundException {
        farasa = new Farasa();
        ArrayList output = farasa.lemmatizeLine(text);
        while (output.contains("\n")) {
            output.remove("\n");
        }
        return output;
    }
}
