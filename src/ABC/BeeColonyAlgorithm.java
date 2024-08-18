/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ABC;

import FileTools.DatasetFileTools;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Hamza_Dabjan
 */
public class BeeColonyAlgorithm {

    /* Control Parameters of ABC algorithm*/
 /*The number of food sources equals the half of the colony size*/
    int FoodNumber;

    public void setFoodNumber(int FoodNumber) {
        this.FoodNumber = FoodNumber;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setMaxCycle(int maxCycle) {
    }

    public void setD(int D) {
        this.D = D;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    /*The number of cycles for foraging {a stopping criteria}*/
    int limit;


    /* Problem specific variables*/
 /*The number of parameters of the problem to be optimized*/
    int D;

    /*Algorithm can be run many times in order to see its robustness*/
    int runtime;

    /*Memorize best soulution*/
    int bestfoodnumber;

    public int getBestfoodnumber() {
        return bestfoodnumber;
    }

    /*Foods is the population of food sources. Each row of Foods matrix is a vector holding D parameters to be optimized. The number of rows of Foods matrix equals to the FoodNumber*/
    String[][] Foods;

    /*fitness is a vector holding fitness (quality) values associated with food sources*/
    double[] fitness = new double[FoodNumber];

    /*trial is a vector holding trial numbers through which solutions can not be improved*/
    double[] trial = new double[FoodNumber];

    /*prob is a vector holding probabilities of food sources (solutions) to be chosen*/
    double[] prob = new double[FoodNumber];

    /*Objective function value of new solution*/ /*New solution (neighbour) produced by v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) j is a randomly chosen parameter and k is a randomlu chosen solution different from i*/
    String[] solution = new String[D];

    /*Fitness value of new solution*/
    double FitnessSol;

    /*param2change corrresponds to j, neighbour corresponds to k in equation v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij})*/
    int neighbour, param2change;

    /*Optimum solution obtained by ABC algorithm*/
    double optimumMax;

    public double getOptimumMax() {
        return optimumMax;
    }

    public String[] getOptimumParams() {
        return optimumParams;
    }

    /*Parameters of the optimum solution*/
    String[] optimumParams = new String[D];

    /*a random number in the range [0,1)*/
    int r;

    String randomCategory;  // Variable to store a randomly selected category

    public static DatasetFileTools datasetFileTools;  // Static instance of DatasetFileTools for managing dataset files

    public String testDatasetPath;  // Path to the test dataset file

    public Map<String, Integer> singleTestTextBag;  // Map to hold a single test text bag of words and their frequencies

    public static Training.TrainDataSet trainDtaset;  // Static instance of TrainDataSet from the Training class

    public List<String> categoryList;  // List to hold the categories present in the dataset

    public static Map<String, Integer[]> freqBag;  // Static map to hold frequency bags for different categories

    List<Map<String, Integer>> TestTextBagList;  // List of maps, each representing a test text bag of words and their frequencies

    Map<String, Map<String, String>> categoryBagOfWordsList;  // Map to hold bag of words lists for each category, with each category having its own map of words and their string representations

    public void setTestTextBagList(List<Map<String, Integer>> TestTextBagList) {
        this.TestTextBagList = TestTextBagList;
    }

    public void setCategoryBagOfWordsList(Map<String, Map<String, String>> categoryBagOfWordsList) {
        this.categoryBagOfWordsList = categoryBagOfWordsList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    public void setTestDatasetPath(String testDatasetPath) {
        this.testDatasetPath = testDatasetPath;
    }

    public void setVariable() {
        Foods = new String[FoodNumber][D];
        fitness = new double[FoodNumber];
        trial = new double[FoodNumber];
        prob = new double[FoodNumber];
        solution = new String[D];
        optimumParams = new String[D];
    }

    /*Fitness function*/
    public double CalculateFitness(List<Map<String, Integer>> TestTextBagList, String[] solution) throws IOException {
        double fullResult = 0;  // Initialize the variable to accumulate the total result
        double result;  // Variable to store the result for each test text bag

        // Loop through each test text bag in the TestTextBagList
        for (int i = 0; i < TestTextBagList.size(); i++) {
            result = 0;  // Reset result for the current test text bag
            Map<String, String> trainingCategoryBag;  // Map to store the training category's bag of words
            freqBag = new HashMap<>();  // Initialize the frequency bag map
            singleTestTextBag = new HashMap<>();  // Initialize the map for the single test text bag

            // Get the training category's bag of words for the current solution
            trainingCategoryBag = categoryBagOfWordsList.get(solution[i]);

            // Get the current test text bag of words
            singleTestTextBag = TestTextBagList.get(i);

            // Loop through each word in the single test text bag
            singleTestTextBag.keySet().forEach((testKey) -> {
                int freq = 0;  // Initialize frequency to 0

                // If the training category's bag contains the test word, get its frequency
                if (trainingCategoryBag.containsKey(testKey)) {
                    freq = Integer.parseInt(trainingCategoryBag.get(testKey));
                }

                // Store the test word's frequency and its corresponding frequency in the training category bag
                freqBag.put(testKey, new Integer[]{singleTestTextBag.get(testKey), freq});
            });

            // Calculate the result by summing the products of the frequencies for each word
            for (String Key : freqBag.keySet()) {
                result += freqBag.get(Key)[0] * freqBag.get(Key)[1];
            }

            // Accumulate the result to the fullResult
            fullResult += result;
        }

        return fullResult;  // Return the accumulated full result
    }

    /*The best food source is memorized*/
    public void MemorizeBestSource() {
        int i, j;

        for (i = 0; i < FoodNumber; i++) {
            if (fitness[i] > optimumMax) {
                optimumMax = fitness[i];
                bestfoodnumber = i;
                for (j = 0; j < D; j++) {
                    optimumParams[j] = Foods[i][j];
                }
            }
        }
        System.out.println("optimumParams: " + optimumMax);
    }

    /*All food sources are initialized */
    public void initial() throws IOException {
        int i;
        // Loop through each food source and initialize it
        for (i = 0; i < FoodNumber; i++) {
            init(i);  // Initialize the food source at index i
        }

        // Set the initial optimum maximum fitness value to the fitness of the first food source
        optimumMax = fitness[0];

        // Store the parameters of the first food source as the initial optimum parameters
        for (i = 0; i < D; i++) {
            optimumParams[i] = Foods[0][i];
        }
    }

    public void init(int index) throws IOException {
        int j;
        // Initialize each parameter of the food source at the given index
        for (j = 0; j < D; j++) {
            // Randomly select a category from the category list
            r = ThreadLocalRandom.current().nextInt(0, categoryList.size());
            randomCategory = categoryList.get(r);

            // Assign the selected category to the current food source
            Foods[index][j] = randomCategory;
            solution[j] = Foods[index][j];  // Update the solution array with the selected category
        }

        // Print the solution for the current food source
        for (j = 0; j < D; j++) {
            System.out.println("solution " + "[" + index + "]: " + solution[j]);
        }

        // Calculate the fitness of the current solution and store it in the fitness array
        fitness[index] = CalculateFitness(TestTextBagList, solution);
        System.out.println("Fitness value for this solution is= " + fitness[index]);

        // Reset the trial counter for the current food source
        trial[index] = 0;
    }

    /*Employed Bee Phase*/
    public void SendEmployedBees() throws IOException {
        int i, j;

        for (i = 0; i < FoodNumber; i++) {
            /*The parameter to be changed is determined randomly*/
            r = ThreadLocalRandom.current().nextInt(0, D);
            param2change = r;
            /*A randomly chosen solution is used in producing a mutant solution of the solution i*/
            r = ThreadLocalRandom.current().nextInt(0, FoodNumber);
            neighbour = r;

            /*Randomly selected solution must be different from the solution i*/
            while (neighbour == i) {
                r = ThreadLocalRandom.current().nextInt(0, FoodNumber);
                neighbour = r;
            }
            for (j = 0; j < D; j++) {
                solution[j] = Foods[i][j];
            }
            System.out.println("Food Source index to be evaluate: " + i);
            solution[param2change] = Foods[neighbour][param2change];
            System.out.println("New solution Randomly Generated: " + solution[param2change]);
            FitnessSol = CalculateFitness(TestTextBagList, solution);
            System.out.println("Fitness for current solution = " + fitness[i]);
            System.out.println("Fitness for New solution Randomly Generated= " + FitnessSol);
            //Greedy Selection
            if (FitnessSol > fitness[i]) {
                System.out.println("The food source was optimized");
                trial[i] = 0;
                for (j = 0; j < D; j++) {
                    Foods[i][j] = solution[j];
                }
                fitness[i] = FitnessSol;
            } else {
                System.out.println("The food source was NOT optimized, so increase its trial counter");
                /*if the solution i can not be improved, increase its trial counter*/
                trial[i] = trial[i] + 1;

            }
        }

        /*end of employed bee phase*/
    }

    /* A food source is chosen with the probability which is proportioal to its quality*/
    public void CalculateProbabilities() {
        int i;
        double maxfit;
        maxfit = fitness[0];
        for (i = 1; i < FoodNumber; i++) {
            if (fitness[i] > maxfit) {
                maxfit = fitness[i];
            }
        }

        for (i = 0; i < FoodNumber; i++) {
            prob[i] = (fitness[i] / maxfit);
        }
    }

    /*onlooker Bee Phase*/
    public void SendOnlookerBees() throws IOException {

        int i, j, onLookerBee;
        i = 0;
        onLookerBee = 0;
        double s;

        while (onLookerBee < FoodNumber) {

            s = ThreadLocalRandom.current().nextDouble(0, 1);
            System.out.println("A Random number is generated= " + s);
            System.out.println("the prob [" + i + "] is= " + prob[i]);
            if (s < prob[i]) /*choose a food source depending on its probability to be chosen*/ {

                System.out.println("the (" + i + ") foodsource is choseen depending on his probability, So onlooker Bee will FLY to try optimize it "); ///////////// remove 
                onLookerBee++; // FLY an onLookerBee :)

                /*The parameter to be changed is determined randomly*/
                r = ThreadLocalRandom.current().nextInt(0, D);
                param2change = r;

                /*A randomly chosen solution is used in producing a mutant solution of the solution i*/
                r = ThreadLocalRandom.current().nextInt(0, FoodNumber);
                neighbour = r;

                /*Randomly selected solution must be different from the solution i*/
                while (neighbour == i) {
                    r = ThreadLocalRandom.current().nextInt(0, FoodNumber);
                    neighbour = r;
                }
                for (j = 0; j < D; j++) {
                    solution[j] = Foods[i][j];
                }

                solution[param2change] = Foods[neighbour][param2change];

                FitnessSol = CalculateFitness(TestTextBagList, solution);

                /*a greedy selection is applied between the current solution i and its mutant*/
                if (FitnessSol > fitness[i]) {
                    /*If the mutant solution is better than the current solution i, replace the solution with the mutant and reset the trial counter of solution i*/
                    trial[i] = 0;
                    for (j = 0; j < D; j++) {
                        Foods[i][j] = solution[j];
                    }
                    fitness[i] = FitnessSol;
                } else {
                    /*if the solution i can not be improved, increase its trial counter*/
                    trial[i] = trial[i] + 1;
                }
            } else {
                System.out.println("the solutions[" + i + "] doesn't chosen");
            }
            i++;
            if (i == FoodNumber) {
                i = 0;
            }
        }

        /*end of onlooker bee phase     */
    }

    /*determine the food sources whose trial counter exceeds the "limit" value. In Basic ABC, only one scout is allowed to occur in each cycle*/
    public void SendScoutBees() throws IOException {
        int maxtrialindex, i;
        maxtrialindex = 0;
        for (i = 1; i < FoodNumber; i++) {
            if (trial[i] > trial[maxtrialindex]) {
                maxtrialindex = i;
            }
        }
        if (trial[maxtrialindex] >= limit) {
            System.out.println("the food source #" + maxtrialindex + " is exceeding the limit to be improving, so it will be initilize agin"); ///////////// remove 
            init(maxtrialindex);
        }
    }

}
