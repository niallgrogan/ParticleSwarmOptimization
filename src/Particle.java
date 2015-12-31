/**
 * Created by Niall on 13/11/2015.
 */
public class Particle implements Constants{

    private Position pos;
    private Velocity vel;

    public Particle(int dimension)
    {
        double[] positions = new double[dimensions];
        double[] velocities = new double[dimensions];
        for(int i=0; i<dimension; i++)
        {
            positions[i] = (upperBound-lowerBound)*Math.random()+lowerBound;
            velocities[i] = Math.random();
        }
        pos = new Position(positions);
        vel = new Velocity(velocities);
    }

    public void setP(double[] p) {pos.setPos(p);}

    public Position getP() {return pos;}

    public void setV(double[] v) {vel.setVel(v);}

    public Velocity getV() {return vel;}
}
