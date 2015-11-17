import java.util.Vector;

/**
 * Created by Niall on 17/11/2015.
 */
public class PSOProcess {

    Vector<Particle> swarm = new Vector<Particle>(50);
    private Position[] bestPositions = new Position[50];
    private int globalBestIndex;
    private double globalFitness=4;
    private double constriction = 0.72984;
    private int iterations = 1;

    public PSOProcess(int i) {
        iterations = i;
    }

    private void initialise() {
        for(int i=0; i<swarm.capacity(); i++)
        {
            Particle p = new Particle();
            swarm.add(p);
            bestPositions[i] = new Position(p.getP().getX(), p.getP().getY());
        }
        findGBest();
    }

    private void findGBest() {
        for(int i=0; i<bestPositions.length; i++)
        {
            double bestFitness = evaluateFit(bestPositions[i].getX(),bestPositions[i].getY());
            if(bestFitness < globalFitness)
            {
                globalFitness = bestFitness;
                globalBestIndex = i;
            }
        }
    }

    private double evaluateFit(double x, double y) {
        double fitness = 0.26*(x*x + y*y) - 0.48*x*y;
        return fitness;
    }

    public void execute() {
        double c1 = 2.05;
        double c2 = 2.05;
        this.initialise();

        for (int j=0; j<iterations; j++)
        {
            for(int i=0; i<swarm.capacity(); i++)
            {
                double r1 = Math.random();
                double r2 = Math.random();

                Particle p = swarm.elementAt(i);

                //Getting our pBest and gBest
                double pbestX = bestPositions[i].getX();
                double pbestY = bestPositions[i].getY();
                double gbestX = bestPositions[globalBestIndex].getX();
                double gbestY = bestPositions[globalBestIndex].getY();

                //PSO Equations with Constriction Factor
                double newVelX = constriction*(p.getV().getX() + r1*(pbestX - p.getP().getX()) + r2*(gbestX - p.getP().getX()));
                double newVelY = constriction*(p.getV().getY() + r1*(pbestY - p.getP().getY()) + r2*(gbestY - p.getP().getY()));

                double newPosX = p.getP().getX() + newVelX;
                double newPosY = p.getP().getY() + newVelY;

                //Setting new particle velocity and position
                p.setP(newPosX, newPosY);
                p.setV(newVelX, newVelY);

                if(evaluateFit(newPosX, newPosY) < evaluateFit(pbestX, pbestY))
                {
                    bestPositions[i] = new Position(newPosX, newPosY);
                    findGBest();
                }
            }
        }
        System.out.println("***");
        for(int k=0; k<bestPositions.length; k++) {
            System.out.print(evaluateFit(bestPositions[k].getX(), bestPositions[k].getY()) + "[" + k + "],\n");
        }
        findGBest();
        System.out.println(globalBestIndex);
        System.out.println(globalFitness);
    }
}
