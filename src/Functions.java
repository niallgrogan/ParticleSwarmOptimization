/**
 * Created by Niall on 18/11/2015.
 */
public class Functions {
    //Sphere Function (2D) ???
    //Min=0 (x1, x2.... xn)=0
    //Search -inf < x < inf, 1<i<n
    //double fitness = x*x + x*x;

    //Rosenbrock Function (a=1 and b=100) ???
    //Min = (n=2): f(1,1) = 0
    //Search -inf < x < inf, 1<i<n
    //double fitness = Math.pow((1-p[0]),2) + 100*Math.pow((p[1]-p[0]*p[0]),2);

    //Griewank Function
    //Min = f(x*) = 0 for x*=(0,....0)
    //Search in Hypercube (-600,600)
    //Not yet implemented

    //Rastrigin Function (2D???)
    //Min = f(0) = 0
    //Search (x) -> (-5.12, 5.12)
    //double fitness = 10*2 + (x*x - 10*Math.cos(2*Math.PI*x)) + (x*x - 10*Math.cos(2*Math.PI*x));

    //Schaffer 2D
    //Min = f(0,0) = 0
    //Search (x,y) -> (-100,100)
    //double fitness = 0.5 + (Math.pow((Math.sin(x*x - y*y)),2) - 0.5)/Math.pow((1 + 0.001*(x*x + y*y)),2);

    //Ackley's Function
    //Min=0 (x=0,y=0)
    //Search x,y -> (-5,5)
    //double fitness = -20*Math.exp(-0.2*(Math.sqrt(0.5*(x*x + y*y)))) - Math.exp(0.5*(Math.cos(2*x*Math.PI)+Math.cos(2*y*Math.PI))) + Math.E + 20;

    //Booths Function
    //Min = f(1,3) = 0
    //Search (x,y) -> (-10,10)
    //double fitness = Math.pow((x+2*y-7),2) + Math.pow((2*x+y-5),2);

    //Matyas Function
    //Min = f(0,0) = 0
    //Search (x,y) -> (-10,10)
    //double fitness = 0.26*(x*x + y*y) - 0.48*x*y;
}
