import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.io.*;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;
import java.math.BigDecimal;

public class BigFibs {
    private static BigDecimal goldenRatio = new BigDecimal((1 + Math.sqrt(5)) / 2);
    private static BigDecimal goldenRatioMin1 = goldenRatio.subtract(BigDecimal.ONE);
    private static BigDecimal sqrt5 = new BigDecimal(Math.sqrt(5));


    static ThreadMXBean bean = ManagementFactory.getThreadMXBean();
    // defining variables
    static int numberOfTrials = 1000; //  100000 loop and matrix
    static int MAXINPUTSIZE = 8193; // 41 for recur. dp and matrix = 92 = 7540113804746346429
    static int MININPUTSIZE = 1; // first number in fibonacci sequence
    static int inputSize = 0;

    static String ResultsFolderPath = "/home/ryan/Results/"; // pathname to results folder
    static FileWriter resultsFile;
    static PrintWriter resultsWriter;

    public static void main(String[] args){
        // loop for testing each function
        //for ( inputSize = MININPUTSIZE; inputSize <= MAXINPUTSIZE; inputSize++){
            //System.out.println(inputSize  + "        " + fibLoopBig(inputSize));
            //System.out.println(inputSize  + "        " + fibMatrixBig(inputSize));
            //System.out.println(inputSize  + "        " + fibFormula(inputSize)); // got to x = 70 accurately
            //System.out.println(inputSize  + "        " + fibFormulaBig(inputSize)); // got to x = 71 accurately
            //verifyFibLoopAndMatrix(inputSize);
        //}

        // print set up for each function test
        /* **********************************************UNCOMMENT ONE******************************************/
/*        System.out.println("Running first full experiment ...");
        runFullExperiment("FibLoopBig-Exp1-ThrowAway.txt");
        System.out.println("Running second full experiment ...");
        runFullExperiment("FibLoopBig-Exp2.txt");
        System.out.println("Running third full experiment ...");
        runFullExperiment("FibLoopBig-Exp3.txt");


        System.out.println("Running first full experiment ...");
        runFullExperiment("FibMatrixBig-Exp1-ThrowAway.txt");
        System.out.println("Running second full experiment ...");
        runFullExperiment("FibMatrixBig-Exp2.txt");
        System.out.println("Running third full experiment ...");
        runFullExperiment("FibMatrixBig-Exp3.txt");
*/

//       System.out.println("Running first full experiment ...");
//        runFullExperiment("FibFormula-Exp1-ThrowAway.txt");
//        System.out.println("Running second full experiment ...");
//        runFullExperiment("FibFormula-Exp2.txt");
 //       System.out.println("Running third full experiment ...");
 //       runFullExperiment("FibFormula-Exp3.txt");

/*
        System.out.println("Running first full experiment ...");
        runFullExperiment("FibFormulaBig-Exp1-ThrowAway.txt");
        System.out.println("Running second full experiment ...");
        runFullExperiment("FibFormulaBig-Exp2.txt");
        System.out.println("Running third full experiment ...");
        runFullExperiment("FibFormulaBig-Exp3.txt");

//        System.out.println("Running first full experiment ...");
        runFullExperiment("FibMatrixBigVals-Exp1-ThrowAway.txt");
//        System.out.println("Running second full experiment ...");
        runFullExperiment("FibMatrixBigVals-Exp2.txt");
  //      System.out.println("Running third full experiment ...");
        runFullExperiment("FibMatrixBigVals-Exp3.txt");

        System.out.println("Running first full experiment ...");
        runFullExperiment("FibLoopBigVals-Exp1-ThrowAway.txt");
        System.out.println("Running second full experiment ...");
        runFullExperiment("FibLoopBigVals-Exp2.txt");
        System.out.println("Running third full experiment ...");
        runFullExperiment("FibLoopBigVals-Exp3.txt");
    */
        System.out.println("Running first full experiment ...");
        runFullExperiment("FibFormulaBigVals-Exp1-ThrowAway.txt");
        System.out.println("Running second full experiment ...");
        runFullExperiment("FibFormulaBigVals-Exp2.txt");
        System.out.println("Running third full experiment ...");
        runFullExperiment("FibFormulaBigVals-Exp3.txt");


        /* **********************************************UNCOMMENT ONE******************************************/
    }

    // function to run each of the experiments on the functions
    static void runFullExperiment(String resultsFileName) {

        // making sure that we have results files available or can create new
        try {
            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);
            resultsWriter = new PrintWriter(resultsFile);
        } catch (Exception e) {
            System.out.println("*****!!!!!  Had a problem opening the results file " + ResultsFolderPath + resultsFileName);
            return;
        }

        ThreadCPUStopWatch BatchStopwatch = new ThreadCPUStopWatch(); // for timing an entire set of trials
        //ThreadCPUStopWatch TrialStopwatch = new ThreadCPUStopWatch(); // for timing an individual trial

        resultsWriter.println("#InputValue(x)    AverageTime     InputSize(n)"); // # marks a comment in gnuplot data
        resultsWriter.flush();

        // for each size of input we want to test: in this case incrementing by 1

        for (int inputSize = MININPUTSIZE; inputSize <= MAXINPUTSIZE; inputSize *= 2) {
            /* repeat for desired number of trials (for a specific size of input)... */
            System.out.println("Running test for input size " + inputSize + " ... ");
            // will hold total amount of time
            // will reset after each batch of trials
            long batchElapsedTime = 0;

            /* force garbage collection before each batch of trials run so it is not included in the time */
            System.gc();
            System.out.print("    Running trial batch...");
            BatchStopwatch.start(); // comment this line if timing trials individually

            // run the trials
            for (int trial = 0; trial < numberOfTrials; trial++) {

                //actual beginning of trial
                /* **********************************************UNCOMMENT ONE******************************************/
                //fibLoopBig(inputSize);
                //fibMatrixBig(inputSize);
                fibFormulaBig(inputSize);
                //fibFormula(inputSize);
                /* **********************************************UNCOMMENT ONE^^^******************************************/
                //System.out.println("....done.");// *** uncomment this line if timing trials individually
            }

            batchElapsedTime = BatchStopwatch.elapsedTime(); // *** comment this line if timing trials individually

            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double) numberOfTrials; // calculate the average time per trial in this batch
            /* print data for this size of input */

            // this block of code determines the number of bits necessary for each input
            // log(x)/log(2) gives us log base 2
            // floor and add 1 gives us the min number of bits necessary to store the value
            double n = Math.floor(Math.log(inputSize)/Math.log(2))+1;

            resultsWriter.printf("%12d  %15.2f %12.2f\n", inputSize, (double) averageTimePerTrialInBatch, n); // might as well make the columns look nice
            resultsWriter.flush();
            System.out.println(" ....done.");
        }
    }




    public static BigInteger fibLoopBig(int x)
    {
        // base cases
        BigInteger fib0 = new BigInteger("0");
        BigInteger fib1 = new BigInteger("1");
        // loop to arrive at fib x
        for (int i = 1; i <= x; i++) {
            // adding the previous two fibs to get the next
            BigInteger nextFib = fib0.add(fib1);
            // swapping for the next iteration
            fib0 = fib1;
            fib1 = nextFib;
        }
        // fib0 will be the desired x
        return fib0;
    }

    // Calling method for fibMatrixBig
    public static BigInteger fibMatrixBig(int x)
    {
        BigInteger A[][] = new BigInteger[][]{{BigInteger.ONE,BigInteger.ONE},{BigInteger.ONE,BigInteger.ZERO}};
        if (x == 0)
            return BigInteger.ZERO;
        power(A, x-1);

        return A[0][0];
    }

    // method to perform matrix multiplication with BigIntegers
    public static void multiply(BigInteger A[][], BigInteger B[][])
    {
        BigInteger j = A[0][0].multiply(B[0][0]).add(A[0][1].multiply(B[1][0]));
        BigInteger k = A[0][0].multiply(B[0][1]).add(A[0][1].multiply(B[1][1]));
        BigInteger l = A[1][0].multiply(B[0][0]).add(A[1][1].multiply(B[1][0]));
        BigInteger m = A[1][0].multiply(B[0][1]).add(A[1][1].multiply(B[1][1]));

        A[0][0] = j;
        A[0][1] = k;
        A[1][0] = l;
        A[1][1] = m;
    }
    // method to determine the power for matrix multiplication
    public static void power ( BigInteger A[][], int x)
    {
        if ( x == 0 || x == 1)
            return;
        BigInteger Z[][] = new BigInteger[][]{{BigInteger.ONE, BigInteger.ONE},{BigInteger.ONE, BigInteger.ZERO}};

        power (A, x/2);
        multiply(A, A);

        if ( x%2 != 0)
            multiply(A, Z);
    }
    // fib formula for longs
    public static long fibFormula(long x)
    {
        double phi = (1 + Math.sqrt(5)) / 2;
        return Math.round(Math.pow(phi, x) / Math.sqrt(5));
    }

    // source: https://stackoverflow.com/questions/35822235/fibonacci-sequence-for-n-46-java
    // fib formula for BigIntegers using a BigDecimal
    public static BigInteger fibFormulaBig(int x)
    {
        BigInteger xthTerm = new BigInteger("0");
        if ( x == 2)
            xthTerm = BigInteger.ONE;
        else {
            BigDecimal minResult = goldenRatio.pow(x).subtract(goldenRatioMin1.pow(x));
            xthTerm = minResult.divide(sqrt5, RoundingMode.FLOOR).toBigInteger();

            if ( x % 2 == 1 && x < 45)
            {
                xthTerm = xthTerm.add(BigInteger.ONE);
            }
        }
        return xthTerm;
    }

    public void verifyFibFormula()
    {

    }

    public static void verifyFibLoopAndMatrix(int x)
    {
        BigInteger fibMat = fibMatrixBig(x);
        BigInteger fibLoop = fibLoopBig(x);

        System.out.println("fibMatrixBig and fibLoopBig verification for fib " + x +" : " + fibMat.toString().compareTo(fibLoop.toString()));

    }


}
