
public interface Constants {
    int numIterations = 10000;
    int initialSwarmSize = 50;
    int finalSwarmSize = 70;
    double c1 = 2.05;
    double c2 = 2.05;
    double constriction = 0.72984;
    double Vmax = 10000;

    String[] functions = {"Sphere", "Rosenbrock", "Ackley", "Griewank", "Rastrigin", "Schaffer(2D)", "Griewank(10D)"};
    double alpha = 50;
    double beta = 1000;
}
