/**
 * Created by Niall on 13/11/2015.
 */
public class Particle implements Constants{
    //Defined both as velocitites for simplicity atm
    private Position p;
    private Velocity v;
    private double fitness;

    public Particle()
    {
        p = new Position((initUpBoundX-initLowBoundX)*Math.random()+initLowBoundX, (initUpBoundY-initLowBoundY)*Math.random()+initLowBoundY);
        v = new Velocity(Math.random(), Math.random());
    }

    private void calculateFitness() {
        double x = p.getX();
        double y = p.getY();
        fitness = x+y;
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
