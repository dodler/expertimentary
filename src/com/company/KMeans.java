package com.company;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.sql.SQLSyntaxErrorException;
import java.util.*;

import static java.lang.Math.*;

/**
 * Created by lyan on 11.09.16.
 */
public class KMeans {

    public static class Centroid{
        Map<ValueVector, List<ValueVector>> centroids;

        @Override
        public String toString(){
            return centroids.toString();
        }

        private Centroid(){
            centroids = new HashMap<>();
        }

        private Centroid(Map<ValueVector, List<ValueVector>> centroids){
            this.centroids = centroids;
        }

        private Centroid(Map<ValueVector, List<ValueVector>> centroids, double delta){
            this(centroids);
            this.delta = delta;
        }

        private Centroid(int capacity){
            centroids = new HashMap<>(capacity);
        }

        public static Centroid valueOf(ValueVector... centers){
            HashMap<ValueVector, List<ValueVector>> result = new HashMap<>();
            for (ValueVector center:centers) {
                result.put(center, new ArrayList<>());
            }
            return new Centroid(result);
        }

        public void addObservation(ValueVector obs) {
            double minDistance = Double.MAX_VALUE;
            ValueVector minCenterKey = null;

            for(ValueVector center:centroids.keySet()){
                double distance = ValueVector.euclidianDistance(center, obs);
                if (distance < minDistance){
                    minDistance = distance;
                    minCenterKey = center;
                }
            }
            List<ValueVector>values = centroids.get(minCenterKey);
            values.add(obs);
//            centroids.put(minCenterKey, values);

        }

        public Centroid calcNewMeans(){
            delta = 0.0;
            HashMap<ValueVector, List<ValueVector>> newCentroids = new HashMap<>();

            for (ValueVector k:centroids.keySet()){
                ValueVector mean = mean(centroids.get(k), k);
                newCentroids.put(mean, centroids.get(k));
                delta += ValueVector.euclidianDistance(k, mean);
            }

            delta /= centroids.size();
//            System.out.println(centroids);
            return new Centroid(newCentroids, delta);
        }

        double delta;

        public double getDelta(){
            return Math.abs(delta);
        }

        private static void devideVectorByScalar(double[] vector, double scalar){
            for(int i = 0; i<vector.length; i++){
                vector[i]/=scalar;
            }
        }

        private static void devideVectorByVector(double[] v1, double[]v2){
            for(int i = 0; i<v1.length; i++){
                v1[i] /= v2[i];
            }
        }

        public static ValueVector sum(ValueVector[] values){
            int valueVectorSize = values[0].getValues().length;
            double[] resultValues = new double[valueVectorSize];

            for(int i = 0; i<values.length; i++){
                for(int j = 0; j< valueVectorSize; j++){
                    resultValues[j] += values[i].getValues()[j];
                }
            }
            return ValueVector.valueOf(resultValues);
        }

        public static ValueVector mean(List<ValueVector> values, ValueVector currentMean){

            double[] meanValues = new double[values.size()];
            for(int i = 0; i<values.size(); i++){
                meanValues[i] = 0.0;
            }

            ValueVector[] valuesToSum = new ValueVector[values.size()];
            for(int i = 0; i<valuesToSum.length; i++){
                valuesToSum[i] = values.get(i);
            }

            ValueVector valuesSum = sum(valuesToSum);

            valuesSum.devideBySaclar(currentMean.getNorm());
            return valuesSum;
        }

        public void clear(){
            centroids.clear();
        }
    }

    public static class ValueVector{
        private double[] values;

        public void devideBySaclar(double scalar){
            for(int i = 0; i<values.length; i++){
                values[i] /= scalar;
            }
        }

        public double getNorm(){
            double result = 0;

            for(int i = 0; i<values.length; i++){
                result += Math.pow(values[i], 2);
            }

            return Math.sqrt(result);
        }

        @Override
        public String toString(){
            StringBuilder result = new StringBuilder("{");

            for (double v:values){
                result.append(v);
                result.append(":");
            }
            result.replace(result.length()-1, result.length(), "");

            result.append("}");
            return result.toString();
        }

        private ValueVector(double[] values){
            this.values = values;
        }

        public static ValueVector valueOf(double... values){
            return new ValueVector(values);
        }

        public static ValueVector zeroVector(int size){
            double[] values = new double[size];
            for(int i = 0; i<size; i++){
                values[i] = 0.0;
            }
            return valueOf(values);
        }

        public static double euclidianDistance(double v1, double v2) {
            return sqrt(pow(v1 - v2, 2));
        }

        public static double euclidianDistance(double[] v1, double[] v2){
            double result = 0;

            if (v1.length != v2.length){
                throw new IllegalArgumentException("Arrays must have same dimension.");
            }

            for(int i = 0; i<v1.length; i++){
                result += pow(v1[i] - v2[i], 2);
            }
            return sqrt(result);
        }

        public double[] getValues(){
            return values;
        }

        public static double euclidianDistance(ValueVector v1, ValueVector v2){
            if (v1.getValues().length  != v2.getValues().length){
                throw new IllegalArgumentException("Vectors must have same dimension.");
            }

            return euclidianDistance(v2.getValues(), v1.getValues());
        }
    }

    public static ValueVector[] getInitialRandomCenters(int centersNumber, int vectorSize){
        ValueVector[] result = new ValueVector[centersNumber];
        Random random = new Random(System.currentTimeMillis());

        for(int i = 0; i<centersNumber; i++){
            double[] values = new double[vectorSize];
            for(int j = 0; j<vectorSize; j++) {
                values[j] = random.nextDouble() * 100 - 50;
            }
            result[i] = ValueVector.valueOf(values);
        }
        return result;
    }

    public static Centroid kMeans(ValueVector[] observation, int centersNumber, double threshold, int maxIteration){
        int vectorSize = observation[0].getValues().length;
        ValueVector[] initialRandomCenters = getInitialRandomCenters(centersNumber, vectorSize);

        Centroid initialCentroid = Centroid.valueOf(initialRandomCenters);
        double delta = Double.MAX_VALUE;

        int iterator = 0;

        do {
            iterator++;
            for (ValueVector obs : observation) {
                initialCentroid.addObservation(obs);
            }

            initialCentroid = initialCentroid.calcNewMeans();
            delta = initialCentroid.getDelta();

            System.out.println(delta);
        }while(delta > threshold && iterator < maxIteration);

        System.out.println(iterator);
        return initialCentroid;
    }
}
