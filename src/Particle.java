/**
 * Created by Niall on 13/11/2015.
 */
public class Particle {
    //Defined both as velocitites for simplicity atm
    private Position p;
    private Velocity v;
    private double fitness;

    public Particle()
    {
        //Initialise everything between 0 and 100
        p = new Position(Math.random(), Math.random());
        v = new Velocity(Math.random(), Math.random());
    }

    private void calculateFitness() {
        double x = p.getX();
        double y = p.getY();
        fitness = -(x*x + y*y)+4;
    }

    public double getFitness() {
        calculateFitness();
        return fitness;
    }

    public void setP(double x, double y)
    {
        p.setX(x);
        p.setY(y);
    }

    public Position getP()
    {
        return p;
    }

    public void setV(double x, double y)
    {
        v.setX(x);
        v.setY(y);
    }

    public Velocity getV()
    {
        return v;
    }
}
