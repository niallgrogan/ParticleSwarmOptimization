
public interface Constants {
    int numIterations = 10000;
    int initialSwarmSize = 40;
    int finalSwarmSize = 80;
    //May want to change this to 0
    int lowestSwarmSize = 1;
    Double c1 = 2.05;
    Double c2 = 2.05;
    Double constriction = 0.72984;
    Double Vmax = 10000.0;

    int[] functions = {8};//{8,9,10,11,12,13,14,15,16,17,18,19,20,21};//{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32};
    Double[] alphaSwings = {100.0,500.0,1000.0,2000.0,3000.0,5000.0,10000.0,20000.0,50000.0,100000.0};
    Double[] betaSwings = {100.0, 500.0, 1000.0, 3000.0, 5000.0, 10000.0, 20000.0, 50000.0, 100000.0};
    int[] iterationSwings = {0, 10, 100, 500, 1000};
    Double defaultAlpha = 20000000.0;
    Double defaultBeta = 1000.0;
}