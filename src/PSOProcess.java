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
            //May want to introduce initialisation range here
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
        //Rosenbrock Function (a=1 and b=100) ???
        //Min = (n=2): f(1,1) = 0
        //Search -inf < x < inf, 1<i<n
        double fitness = Math.pow((1-x),2) + 100*Math.pow((y-x*x),2);
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
                double pbestX = bestPositions[i].getX();
                double pbestY = bestPositions[i].getY();
                double gbestX = bestPositions[globalBestIndex].getX();
                double gbestY = bestPositions[globalBestIndex].getY();

                //PSO Equations with Constriction Factor
                double newVelX = constriction*(p.getV().getX() + r1*c1*(pbestX - p.getP().getX()) + r2*c2*(gbestX - p.getP().getX()));
                double newVelY = constriction*(p.getV().getY() + r1*c1*(pbestY - p.getP().getY()) + r2*c2*(gbestY - p.getP().getY()));

                //Limiting velocity
                if(newVelX > Vmax) {newVelX = Vmax;}
                else if(newVelY > Vmax) {newVelY = Vmax;}

                double newPosX = p.getP().getX() + newVelX;
                double newPosY = p.getP().getY() + newVelY;

                //Setting new particle velocity and position
                p.setP(newPosX, newPosY);
                p.setV(newVelX, newVelY);

                if(evaluateFit(newPosX, newPosY) < evaluateFit(pbestX, pbestY))
                {
                    if (newPosX > upperBound || newPosX < lowerBound || newPosY > upperBound || newPosY < lowerBound) {}
                    else {
                        bestPositions[i] = new Position(newPosX, newPosY);
                        findGBest();
                    }
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
