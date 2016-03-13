
public interface Constants {
    int numIterations = 10000;
    int initialSwarmSize = 60;
    int finalSwarmSize = 80;
    double c1 = 2.05;
    double c2 = 2.05;
    double constriction = 0.72984;
    double Vmax = 10000;

    String[] functions = {"Sphere", "Rosenbrock", "Ackley", "Griewank", "Rastrigin", "Schaffer(2D)", "Griewank(10D)"};
    double[] alphaSwings = {100,500,1000,2000,3000,5000,10000,20000,50000,100000};
    double[] betaSwings = {100.0, 500.0, 1000.0, 3000.0, 5000.0, 10000.0, 20000.0, 50000.0, 100000.0};
    int[] iterationSwings = {0, 10, 100, 500, 1000};
    double defaultAlpha = 50000.0;
    double defaultBeta = 1000.0;
}