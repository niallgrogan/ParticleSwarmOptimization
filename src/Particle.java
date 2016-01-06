/**
 * Created by Niall on 13/11/2015.
 */
public class Particle implements Constants{

    private double[] pos = new double[dimensions];
    private double[] vel = new double[dimensions];

    public Particle(int dimension)
    {
        for(int i=0; i<dimension; i++) {
            //Set randomly inside problem bounds
            pos[i] = (upperBound - lowerBound) * Math.random() + lowerBound;
            //Set using half diff method
            vel[i] = (((upperBound - lowerBound) * Math.random() + lowerBound) - pos[i]) / 2;
        }
    }

    public void setP(double[] p) {pos = p;}

    public double[] getP() {return pos;}

    public void setV(double[] v) {vel = v;}

    public double[] getV() {return vel;}
}
