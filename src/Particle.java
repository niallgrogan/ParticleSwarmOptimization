/**
 * Created by Niall on 13/11/2015.
 */
public class Particle implements Constants{

    private double[] pos = new double[dimensions];
    private double[] vel = new double[dimensions];
    private double fitness;
    private double bestFitness;
    private double[] bestLocation;
    private double localBestFitness;
    private double[] localBestLocation;

    public Particle(int dimension)
    {
        for(int i=0; i<dimension; i++) {
            //Set randomly inside problem bounds
            pos[i] = (upperBound - lowerBound) * Math.random() + lowerBound;
            //Set using half diff method
            vel[i] = (((upperBound - lowerBound) * Math.random() + lowerBound) - pos[i]) / 2;
        }
    }

    public void evaluateFitness(){
        //For sphere
        fitness = 0;
        for (int i=0; i<pos.length; i++)
        {
            fitness = fitness + Math.pow(pos[i],2);
        }
    }

    public void updateParticle() {
        double r1 = Math.random();
        double r2 = Math.random();
        for (int i = 0; i < pos.length; i++)
        {
            vel[i] = constriction*(pos[i] + r1*c1*(bestLocation[i] - pos[i]) + r2*c2*(localBestLocation[i] - pos[i]));
            pos[i] = pos[i] + vel[i];
        }
    }

    public void findBestNeighbour(Particle[] particles)
    {
        double[] fitness = new double[particles.length];
        for(int i=0; i<particles.length; i++)
        {
            fitness[i] = particles[i].fitness;
        }
        localBestLocation = particles[getMinIndex(fitness)].getP();
    }

    private int getMinIndex(double[] fitnesses)
    {
        int index = 0;
        localBestFitness = fitnesses[0];
        for(int i=0; i<fitnesses.length; i++)
        {
            if(fitnesses[i] < localBestFitness)
            {
                index = i;
                localBestFitness = fitnesses[i];
            }
        }
        return index;
    }

    public void setP(double[] p) {pos = p;}

    public double[] getP() {return pos;}

    public void setV(double[] v) {vel = v;}

    public double[] getV() {return vel;}
}
