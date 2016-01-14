/**
 * Created by Niall on 18/11/2015.
 */
public class Functions {
    //Sphere function
//    double fitness = 0;
//    for (int i=0; i<dimensions; i++)
//    {
//        fitness = fitness + Math.pow(p[i],2);
//    }
//    return fitness;
//    double upperBound = 5.12;
//    double lowerBound = -5.12;

//    //f1 Shifted Sphere
//    double fitness = 0;
//    for (int i=0; i<dimensions; i++)
//    {
//        fitness = fitness + Math.pow((p[i] - optimum[i]),2);
//    }
//    return fitness - 450;
//    double upperBound = 100;
//    double lowerBound = -100;

    //Rosenbrock function
//    double fitness = 0;
//    for (int i=0; i<dimensions-1; i++)
//    {
//        fitness = fitness + (100*Math.pow(p[i+1] - Math.pow(p[i],2),2)+Math.pow((1-p[i]),2));
//    }
//    return fitness;
//    double upperBound = 2.048;
//    double lowerBound = -2.048;

//    //Ackley function
//    double fitness = 0;
//    double sumOne = 0;
//    double sumTwo = 0;
//    double c = 2*Math.PI;
//    for (int i=0; i<dimensions; i++)
//    {
//        sumOne = sumOne + Math.pow(p[i],2);
//        sumTwo = sumTwo + Math.cos(c*p[i]);
//    }
//    //May be a problem dividing double by int
//    fitness = -20*Math.exp(-0.2*Math.sqrt(sumOne/dimensions)) - Math.exp(sumTwo/dimensions) + 20 + Math.exp(1);
//    return fitness;
//    double upperBound = 32;
//    double lowerBound = -32;

    //Griewank Function
//    double fitness = 0.0;
//    double sum = 0.0;
//    //Need to start product as 1
//    double product = 1.0;
//    for (int i=0; i<dimensions; i++)
//    {
//        sum += ((p[i]*p[i])/4000.0);
//        //Note the plus one
//        product *= Math.cos(p[i]/Math.sqrt(i+1));
//    }
//    //May be a problem dividing double by int
//    fitness = (sum - product + 1.0);
//    return fitness;
//    double upperBound = 600;
//    double lowerBound = -600;

//    //Rastrigin Function
//    double fitness = 0.0;
//    double sum = 0.0;
//    for (int i=0; i<dimensions; i++)
//    {
//        sum += (p[i]*p[i] - 10.0*Math.cos(2*Math.PI*p[i]));
//    }
//    fitness = 10*dimensions + sum;
//    return fitness;
//    double upperBound = 5.12;
//    double lowerBound = -5.12;

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

//    //Schaffer(2D) Function
//    double fitness = 0.0;
//    double numer = Math.pow(Math.sin(p[0]*p[0] - p[1]*p[1]),2)-0.5;
//    double denom = Math.pow((1.0 + 0.001*(p[0]*p[0] + p[1]*p[1])),2);
//    fitness = 0.5 + (numer/denom);
//    return fitness;
//    int dimensions = 2;
//    double upperBound = 100;
//    double lowerBound = -100;

    //Griewank(10D) Function
//    double fitness = 0.0;
//    double sum = 0.0;
//    //Need to start product as 1
//    double product = 1.0;
//    for (int i=0; i<dimensions; i++)
//    {
//        sum += ((p[i]*p[i])/4000.0);
//        //Note the plus one
//        product *= Math.cos(p[i]/Math.sqrt(i+1));
//    }
//    //May be a problem dividing double by int
//    fitness = (sum - product + 1.0);
//    return fitness;
//    int dimensions = 10;
//    double upperBound = 600;
//    double lowerBound = -600;

}
