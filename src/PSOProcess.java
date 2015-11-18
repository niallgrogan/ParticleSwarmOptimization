import java.util.Vector;

/**
 * Created by Niall on 17/11/2015.
 */
public class PSOProcess implements Constants{

    Vector<Particle> swarm = new Vector<Particle>(swarmSize);
    private Position[] bestPositions = new Position[swarmSize];
    private int globalBestIndex;
    //Not good to have this here
    private double globalFitness=4;
    private int iterations = numIterations;

    public PSOProcess() {}

    public PSOProcess(int i) {
        iterations = i;
    }

    private void initialise() {
        for(int i=0; i<swarm.capacity(); i++)
        {
            Particle p = new Particle(dimensions);
            swarm.add(p);
            bestPositions[i] = new Position(p.getP().getPos());
        }
        findGBest();
    }

    private void findGBest() {
        for(int i=0; i<bestPositions.length; i++)
        {
            double bestFitness = evaluateFit(bestPositions[i].getPos());
            if(bestFitness < globalFitness)
            {
                globalFitness = bestFitness;
                globalBestIndex = i;
            }
        }
    }

    private double evaluateFit(double[] p) {
        //Sphere function
        double fitness = 0;
        for (int i=0; i<dimensions; i++)
        {
            fitness = fitness + Math.pow(p[i],2);
        }
        return fitness;
    }

    public void execute() {
        this.initialise();

        for (int j=0; j<iterations; j++)
        {
            for(int i=0; i<swarm.capacity(); i++)
            {
                double r1 = Math.random();
                double r2 = Math.random();

                Particle p = swarm.elementAt(i);

                //Getting our pBest and gBest
                double[] pbest = bestPositions[i].getPos();
                double[] gbest = bestPositions[globalBestIndex].getPos();
                double[] newVel = new double[dimensions];
                double[] newPos = new double[dimensions];
                boolean insideBound = true;

                //PSO Equations with Constriction Factor
                for(int k=0; k<dimensions; k++)
                {
                    newVel[k] = constriction*(p.getV().getVel()[k] + r1*c1*(pbest[k] - p.getP().getPos()[k]) + r2*c2*(gbest[k] - p.getP().getPos()[k]));

                    //Limiting velocity
                    if(newVel[k] > Vmax) {newVel[k] = Vmax;}
                    else if(newVel[k] > Vmax) {newVel[k] = Vmax;}

                    newPos[k] = p.getP().getPos()[k] + newVel[k];
                    if(newPos[k] > upperBound | newPos[k] < lowerBound) {insideBound = false;}
                }

                //Setting new particle velocity and position
                p.setP(newPos);
                p.setV(newVel);

                if(evaluateFit(newPos) < evaluateFit(pbest))
                {
                    if (insideBound) {
                        bestPositions[i] = new Position(newPos);
                        findGBest();
                    }
                }
            }
        }
        System.out.println("***");
        for(int k=0; k<bestPositions.length; k++) {
            System.out.print(evaluateFit(bestPositions[k].getPos()) + "[" + k + "],\n");
        }
        findGBest();
        System.out.println(bestPositions[globalBestIndex].toString());
        System.out.println(globalFitness);
    }
}
