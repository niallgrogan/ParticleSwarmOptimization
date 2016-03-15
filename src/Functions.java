import java.util.Random;

public class Functions implements Constants{

    public String activeFunction;
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
                goal = 0.05;
                break;
            case "f1": dimensions = 30;
                upperBound = 100;
                lowerBound = -100;
                goal = -450 + 0.000001;
                break;
            case "f2": dimensions = 30;
                upperBound = 100;
                lowerBound = -100;
                goal = -450 + 0.000001;
                break;
            case "f3": dimensions = 30;
                upperBound = 100;
                lowerBound = -100;
                goal = -450 + 0.000001;
                break;
            case "f4": dimensions = 30;
                upperBound = 100;
                lowerBound = -100;
                goal = -450 + 0.000001;
                break;
            case "f5": dimensions = 30;
                upperBound = 100;
                lowerBound = -100;
                goal = -310 + 0.000001;
                break;
            case "f6": dimensions = 30;
                upperBound = 100;
                lowerBound = -100;
                goal = 390 + 100;
                break;
            case "f9": dimensions = 30;
                upperBound = 5;
                lowerBound = -5;
                goal = -330 + 0.01;
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
                fitness = fitness + (100*Math.pow(p[i] - Math.pow(p[i+1],2),2)+Math.pow((1-p[i]),2));
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
            //Not sure if anything below working correctly
            //f1 seems to be working correctly
            case "f1":
                for (int i=0; i<dimensions; i++)
                {
                    fitness = fitness + Math.pow((p[i]-450.0),2);
                }
                fitness = fitness - 450.0;
                break;
            //f2 does NOT appear to be working correctly
            case "f2":
                for (int i=1; i<=dimensions; i++)
                {
                    for(int j=1; j<i; j++) {
                        sum = sum + (p[j-1]-450);
                    }
                    fitness = fitness + Math.pow(sum,2);
                }
                fitness = fitness-450;
                break;
            //f3 appears to be working correctly
            case "f3":
                for (int i=1; i<=dimensions; i++)
                {
                    fitness = fitness + Math.pow(10^6,(i-1)/(dimensions-1))*Math.pow((p[i-1]-450.0),2);
                }
                fitness = fitness - 450.0;
                break;
            //Will come back to this once f2 is solved (both Schwefel)
            case "f4":
                for (int i=0; i<dimensions; i++)
                {
                    for(int j=0; j<i; j++) {
                        sum = sum + (p[j] - 450.0);
                    }
                    fitness = fitness + Math.pow(sum,2);
                }
                fitness = fitness*0.4*Math.abs(getGaussian(0,1)) - 450.0;
                break;
            //More Schwefel....
            case "f5":
                break;
            //Unsure whether working correctly, seems to find optimum, but should Rosenbrock converge so easily?
            case "f6":
                for (int i=0; i<dimensions-1; i++)
                {
                    fitness = fitness + (100*(Math.pow((Math.pow((p[i]-390.0+1),2))-(p[i+1]-390.0+1),2)+Math.pow(((p[i]-390.0+1)-1),2)));
                }
                fitness = fitness + 390.0;
                break;
            case "f9":
                for (int i=0; i<dimensions; i++)
                {
                    sum += ((p[i]-330)*(p[i]-330) - 10.0*Math.cos(2*Math.PI*(p[i])-330));
                }
                fitness = 10*dimensions + sum;
                fitness = fitness - 330;
                break;
        }
        return fitness;

    }

    private double getGaussian(double aMean, double aVariance) {
        Random r = new Random();
        return aMean + r.nextGaussian()*aVariance;
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
