/**
 * Created by Niall on 18/11/2015.
 */
public class Functions implements Constants{

    private String activeFunction;
    public int dimensions;
    public double upperBound;
    public double lowerBound;
    public double goal;

    public Functions(String functionName) {
        activeFunction = functionName;
        switch (activeFunction) {
            case "Sphere":  dimensions = 30;
                upperBound = 5.12;
                lowerBound = -5.12;
                goal = 0.01;
                break;
            case "Rosenbrock":  dimensions = 30;
                upperBound = 2.048;
                lowerBound = -2.048;
                goal = 100;
                break;
            case "Ackley":  dimensions = 30;
                upperBound = 32;
                lowerBound = -32;
                goal = 0.01;
                break;
            case "Griewank":  dimensions = 30;
                upperBound = 600;
                lowerBound = -600;
                goal = 0.05;
                break;
            case "Rastrigin":  dimensions = 30;
                upperBound = 5.12;
                lowerBound = -5.12;
                goal = 100;
                break;
            case "Schaffer(2D)":  dimensions = 2;
                upperBound = 100;
                lowerBound = -100;
                goal = 0.00001;
                break;
            case "Griewank(10D)":  dimensions = 10;
                upperBound = 600;
                lowerBound = -600;
                goal = 0.00001;
                break;
        }
    }

    public double findFitness(double[] p) {
        double fitness = 0.0;
        double sumOne = 0.0;
        double sumTwo = 0.0;
        double sum = 0.0;
        //Need to start product as 1
        double product = 1.0;
        switch(activeFunction) {
            case "Sphere":  for (int i=0; i<p.length; i++)
                            {
                                fitness = fitness + Math.pow(p[i],2);
                            }
                break;

            case "Rosenbrock":  for (int i=0; i<p.length-1; i++)
                                {
                                    fitness = fitness + (100*Math.pow(p[i+1] - Math.pow(p[i],2),2)+Math.pow((1-p[i]),2));
                                }
                break;
            case "Ackley":  double c = 2*Math.PI;
                            for (int i=0; i<dimensions; i++)
                            {
                                sumOne = sumOne + Math.pow(p[i],2);
                                sumTwo = sumTwo + Math.cos(c*p[i]);
                            }
                            //May be a problem dividing double by int
                            fitness = -20*Math.exp(-0.2*Math.sqrt(sumOne/dimensions)) - Math.exp(sumTwo/dimensions) + 20 + Math.exp(1);
                break;
            case "Griewank":    for (int i=0; i<dimensions; i++)
                                {
                                    sum += ((p[i]*p[i])/4000.0);
                                    //Note the plus one
                                    product *= Math.cos(p[i]/Math.sqrt(i+1));
                                }
                                //May be a problem dividing double by int
                                fitness = (sum - product + 1.0);
                break;
            case "Rastrigin":   for (int i=0; i<dimensions; i++)
                                {
                                    sum += (p[i]*p[i] - 10.0*Math.cos(2*Math.PI*p[i]));
                                }
                                fitness = 10*dimensions + sum;
                break;
            case "Schaffer(2D)":    double numer = Math.pow(Math.sin(p[0]*p[0] - p[1]*p[1]),2)-0.5;
                                    double denom = Math.pow((1.0 + 0.001*(p[0]*p[0] + p[1]*p[1])),2);
                                    fitness = 0.5 + (numer/denom);
                break;
            case "Griewank(10D)":   for (int i=0; i<dimensions; i++)
                                    {
                                        sum += ((p[i]*p[i])/4000.0);
                                        //Note the plus one
                                        product *= Math.cos(p[i]/Math.sqrt(i+1));
                                    }
                                    //May be a problem dividing double by int
                                    fitness = (sum - product + 1.0);
                break;
        }
        return fitness;

    }

//    //f1 Shifted Sphere
//    double fitness = 0;
//    for (int i=0; i<dimensions; i++)
//    {
//        fitness = fitness + Math.pow((p[i] - optimum[i]),2);
//    }
//    return fitness - 450;
//    double upperBound = 100;
//    double lowerBound = -100;

//    f9 - Shifted Rastrigin Function
//    double fitness = 0.0;
//    double sum = 0.0;
//    for (int i=0; i<dimensions; i++)
//    {
//        sum += (p[i]*p[i] - 10.0*Math.cos(2*Math.PI*p[i]));
//    }
//    fitness = 10*dimensions + sum;
//    return fitness - 330;
//    double upperBound = 5;
//    double lowerBound = -5;

}
