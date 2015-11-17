import java.util.Vector;

/**
 * Created by Niall on 17/11/2015.
 */
public class PSOProcess {

    Vector<Particle> swarm = new Vector<Particle>(50);
    private Position[] bestPositions = new Position[50];
    private int globalBestIndex;
    private double globalFitness=0;

    public PSOProcess() {}

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
            if(findFit(bestPositions[i]) > globalFitness)
            {
                globalFitness = findFit(bestPositions[i]);
                globalBestIndex = i;
            }
        }
    }

    //Not sustainable work-around
    private double findFit(Position p)
    {
        double x = p.getX();
        double y = p.getY();
        double fitness = -1*((x*x)+(y*y))+4;
        return fitness;
    }

    private double evaluateFit(double x, double y) {
        double fitness = -1*((x*x)+(y*y))+4;
        return fitness;
    }

    public void execute() {
        double c1 = 2.05;
        double c2 = 2.05;
        this.initialise();

        for (int j=0; j<1000; j++)
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

                //PSO Equations
                double newVelX = p.getV().getX() + r1*c1*(pbestX - p.getP().getX()) + r2*c2*(gbestX - p.getP().getX());
                double newVelY = p.getV().getY() + r1*c1*(pbestY - p.getP().getY()) + r2*c2*(gbestY - p.getP().getY());

                //if(newVelX > 2.5 | newVelX < -2.5) {newVelX = 2.5;}
                //if(newVelY > 2.5 | newVelY < -2.5) {newVelY = 2.5;}

                double newPosX = p.getP().getX() + newVelX;
                double newPosY = p.getP().getY() + newVelY;

                //if(newPosX > 10 | newPosY < -10) {newPosX = Math.random();}
                //if(newPosY > 10 | newPosY < -10) {newPosY = Math.random();}

                //Setting new particle velocity and position
                p.setP(newPosX, newPosY);
                p.setV(newVelX, newVelY);

                if(evaluateFit(newPosX, newPosY) > evaluateFit(pbestX, pbestY))
                {
                    bestPositions[i] = new Position(newPosX, newPosY);
                    //Global best will only change when individual best changes too???
                    findGBest();
                }
            }
        }
        System.out.println("***");
        for(int k=0; k<bestPositions.length; k++) {
            System.out.print(findFit(bestPositions[k]) + "[" + k + "],\n");
        }
        findGBest();
        System.out.println(globalBestIndex);
        System.out.println(globalFitness);
    }
}
