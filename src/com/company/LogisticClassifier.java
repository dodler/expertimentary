package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by lyan on 26.10.16.
 */
public class LogisticClassifier {

    public static List<Instance> readDataSet(String fileName) throws FileNotFoundException{
        List<Instance> result = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(fileName))){
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();

                if (line.startsWith("#")){
                    continue;
                }

                String[] columns = line.split("\\s+");

                int i = 1;
                int[] data = new int[columns.length -2];

                for(i = 1; i < columns.length-1; i++){
                    data[i-1] = Integer.parseInt(columns[i]);
                }
                int label = Integer.parseInt(columns[i]);

                Instance instance = new Instance(label, data);
                result.add(instance);
            }
        }

        return result;
    }

    private static class Instance{
        public int label;
        public int[] x;

        public Instance(int label, int[] x){
            this.label = label;
            this.x = x;
        }
    }

    private double rate;
    private double[] weights;

    private final double ITERATIONS = 3000;

    public LogisticClassifier(int n){
        this.rate = 0.0001;
        weights = new double[n];
    }

    private static double sigmoid(double z){
        return 1.0/(1.0 + Math.exp(-z));
    }

    public void train(List<Instance> instances){
        for (int i = 0; i<ITERATIONS; i++){
            double lik = 0.0;
            for (Instance instance:instances){
                double predicted = classify(instance.x);
                for(int j = 0; j<weights.length; j++){
                    weights[j] += rate * (instance.label - predicted) * instance.x[j];
                }
                lik += instance.label * Math.log(classify(instance.x)
                 + (1-instance.label) * Math.log(1-classify(instance.x)));
            }
        }
    }

    private double classify(int[] x) {
        double logit = 0.0;
        for(int i = 0; i<weights.length; i++){
            logit += weights[i] * x[i];
        }
        return sigmoid(logit);
    }
}
