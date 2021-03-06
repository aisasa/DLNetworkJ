package dlnetwork;

import java.util.*;                         // ArrayList, Random

/**
 * A collection of math and ancillary functions to serve procedures in network
 * and learning code.
 * 
 * @author  Agustin Isasa Cuartero
 * @version 0.9
 */
public class DLMath {
    /**
     * A matrix dot product.
     * 
     * @param a     First matrix to multiply.
     * @param b     Second matrix to multiply.
     * @return      A matrix containing the result of the dot product.
     */
    public static double[][] dotProd(double[][] a, double[][] b){
        int nRowsA = a.length;
        int nColsA = a[0].length;
        int nRowsB = b.length;
        int nColsB = b[0].length;
        if(nColsA != nRowsB) throw new RuntimeException("No matching matrices' dimensions");
        double[][] dp = new double[a.length][b[0].length];
        for(int i=0; i<nRowsA; i++)
            for(int j=0; j<nColsB; j++)
                for(int k=0; k<nColsA; k++)
                    dp[i][j] += a[i][k]*b[k][j];
        return dp;
    }
    
    /**
     * Computes the first derivative of the quadratic cost function;
     * this cost function is C = 1/2n·(summ_x|y(x)-realResult|^2), and its
     * derivative is thus equal to the subtraction of the real result in
     * each examples from the computed result for that example.
     * 
     * @param y     Vector of computed result for each example.
     * @param real  Vector of real result of each example.
     * @return      A vector containing the computed first derivative of the
     *              quadratic cost function.
     */
    public static double[] costQuadDeriv(double[] y, double[] real){
        double[] cd = new double[y.length];
        for(int i=0; i<y.length; i++){
            cd[i] = y[i]-real[i];  
        }
        return cd;
    }
    
    /**
     * Computes the first derivative of the cross-entropy cost 
     * function, C = -1/n·(summ_x summ_j[y_j·ln a_j + (1-y_j)·ln(1-a_j)]), 
     * and its derivative is thus equal to the subtraction of the real result in
     * each example from the computed result for that example.
     * 
     * @param y     Vector of computed result for each example.
     * @param real  Vector of real result of each example.
     * @return      A vector containing the computed first derivative of the
     *              cross-entropy cost function.
     */
    public static double[] costCrossEntrDeriv(double y[], double real[]){
        double[] cd = new double[y.length];
        for(int i=0; i<y.length; i++){
            cd[i] = y[i]-real[i];  
        }
        return cd;
    }
    
    /**
     * Implements the sigmoid function of each neuron.
     * 
     * @param z     Vector of inputs to a layer of neurons.
     * @return      A vector containing the computed sigmoid output of each
     *              neuron in a layer of neurons.
     */
    public static double[][] sigmoid(double[][] z){
        double[][] sigm = new double[z.length][z[0].length];
        for(int i=0; i<z.length; i++)
            for(int j=0; j<z[0].length; j++)
                sigm[i][j] = 1.0/(1.0 + Math.exp(-z[i][j])); 
        return sigm;
    }
    
    /**
     * Computes the first derivative of the sigmoid function for each element
     * in a vector.
     * 
     * @param z     Vector of inputs to a layer of neurons.
     * @return      A vector containing the computed sigmoid first derivative
     *              of each element from the input vector.
     */
    public static double[][] sigmoidDeriv(double[][] z){
        double[][] sigm_d = sigmoid(z);
        for(int i=0; i<z.length; i++)
            for(int j=0; j<z[0].length; j++)
                sigm_d[i][j] = sigm_d[i][j]*(1-sigm_d[i][j]);
        return sigm_d;
    }
    
    /**
     * Computes the transposed vector.
     * 
     * @param v     A vector in matrix form to be transposed.
     * @return      The input vector once transposed.
     */
    public static double[] vTranspose(double[][] v){
        double[] vT = new double[v.length];
        for(int i=0; i<v.length; i++)
            vT[i] = v[i][0]; 
        return vT;
    }
    
    /**
     * Computes the transposed matrix.
     * 
     * @param m     A matrix to be transposed.
     * @return      The input matrix once transposed.
     */
    public static double[][] mTranspose(double[][] m){
        double[][] mT = new double[m[0].length][m.length];
        for(int i=0; i<m[0].length; i++)
            for(int j=0; j<m.length; j++)
                mT[i][j] = m[j][i]; 
        return mT;
    }
    
    /**
     * An auxiliary method that computes the addition of matrices enclosed in 
     * array lists. Structure in number of enclosed matrices and in the 
     * elements of each matrix must be equal.
     * 
     * @param a     First array list whose contained matrices will be added.
     * @param b     Second array list whose contained matrices will be added.
     * @return      A new array list whose contained matrices tally with the
     *              addition of each matrix in each array list.
     */
    public static ArrayList<double[][]> addMatrInLists(ArrayList<double[][]> a, ArrayList<double[][]> b){
        ArrayList<double[][]> listSum = new ArrayList(a.size());
        double[][] sum; 
        for(int i=0; i<a.size(); i++){
            sum = new double[a.get(i).length][a.get(i)[0].length];
            for(int j=0; j<a.get(i).length; j++)
                for(int k=0; k<a.get(i)[j].length; k++)
                    sum[j][k] = a.get(i)[j][k] + b.get(i)[j][k];
            listSum.add(sum);
        }
        return listSum;        
    }
    
    /**
     * A method that returns a vector representative of a specific result of a
     * example (an integer between 0 to 9).
     * 
     * @param output    Integer in the 0 to 9 range that indicates position.
     * @return          A zeroed 10 elements vector, except in the position
     *                  passed as argument (that must be in [0,1,...,9]) in 
     *                  which a '1' will appear.
     */
    public static double[] getOutputVector(int output){
        double[] ov = new double[10];
        ov[output] = 1.0;
        return ov;
    }
    
    /**
     * Shuffles two sets of related data (inputs and respective outputs, for 
     * example) in the same way and so the correspondence between the elements 
     * is not lost.
     * 
     * @param aList An array list containing two elements: a bidimensional 
     *              array containing the input data from MNIST training data set
     *              and an array containing the real output of each input or 
     *              example in MNIST training data set.
     */
    //public static void shuffle(double[][] a, double[] b){
    public static void shuffle(ArrayList aList){
        double[][] a = (double[][])aList.get(0);
        double[] b = (double[])aList.get(1);
        // A basic Fisher-Yates shuffle
        Random rand = new Random();
        for(int i=a.length-1; i>0; --i){
            int j = rand.nextInt(i+1);
            double[] tempA = a[i];
            double tempB = b[i];
            a[i] = a[j];
            b[i] = b[j];
            a[j] = tempA;
            b[j] = tempB;
        }
    }

}