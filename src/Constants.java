
public interface Constants {
    int numIterations = 10000;
    int initialSwarmSize = 50;
    int finalSwarmSize = 70;
    double c1 = 2.05;
    double c2 = 2.05;
    double constriction = 0.72984;
    double Vmax = 10000;
    String[] functions = {"Sphere", "Rosenbrock", "Ackley", "Griewank", "Rastrigin", "Schaffer(2D)", "Griewank(10D)"};
    double[] alphaSwings = {100,500,1000,2000,3000,5000,10000,20000};
}
