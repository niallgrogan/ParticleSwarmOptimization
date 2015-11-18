/**
 * Created by Niall on 13/11/2015.
 */
public class Particle implements Constants{
    //Defined both as velocitites for simplicity atm
    private Position pos;
    private double[] positions;
    private Velocity vel;
    private double[] velocities;
    private double fitness;

    public Particle(int dimension)
    {
        for(int i=0; i<dimension; i++)
        {
            positions[i] = (initUpBound-initLowBound)*Math.random()+initLowBound;
            velocities[i] = Math.random();
        }
        pos = new Position(positions);
        vel = new Velocity(velocities);
    }

    public void setP(double[] p) {pos.setPos(p);}

    public Position getP()
    {
        return pos;
    }

    public void setV(double[] v) {vel.setVel(v);}

    public Velocity getV()
    {
        return vel;
    }
}
