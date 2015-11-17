import java.util.Vector;

/**
 * Created by Niall on 17/11/2015.
 */
public class PSOProcess {

    Vector<Particle> swarm = new Vector<Particle>(50);
    Vector<Position> bestPositions = new Vector<Position>(50);
    int globalBestIndex;
    double globalFitness=0;
    double newPosX, newPosY, newVelX, newVelY;
    double r1,r2;
    double c1, c2 = 1.05;
    double pbestX, pbestY, gbestX, gbestY;

    public PSOProcess() {}

    private void initialise() {
        for(int i=0; i<swarm.capacity(); i++)
        {
            Particle p = new Particle();
            swarm.add(p);
            bestPositions.add(p.getP());
        }
        findGBest();
    }

    private void findGBest() {
        for(int i=0; i<swarm.capacity(); i++)
        {
            if(swarm.elementAt(i).getP().getX() > globalFitness)
            {
                globalFitness = swarm.elementAt(i).getP().getX();
                globalBestIndex = i;
            }
        }
    }

    public void execute() {
        this.initialise();

        for (int j=0; j<10; j++) {
            for(int i=0; i<swarm.capacity(); i++)
            {
                r1 = Math.random();
                r2 = Math.random();

                Particle p = swarm.elementAt(i);

                //Getting our pBest and gBest
                pbestX = bestPositions.elementAt(i).getX();
                pbestY = bestPositions.elementAt(i).getY();
                gbestX = bestPositions.elementAt(globalBestIndex).getX();
                gbestY = bestPositions.elementAt(globalBestIndex).getY();

                //PSO Equations
                newVelX = p.getV().getX() + r1*c1*(pbestX - p.getP().getX()) + r2*c2*(gbestX - p.getP().getX());
                newVelY = p.getV().getY() + r1*c1*(pbestY - p.getP().getY()) + r2*c2*(gbestY - p.getP().getY());
                newPosX = p.getP().getX() + p.getV().getX();
                newPosY = p.getP().getY() + p.getV().getY();

                //Setting new particle velocity and position
                p.setP(newPosX,newPosY);
                p.setV(newVelX, newVelY);

                //updating pBest and gBest
                //This should be evaluated in terms of fitness rather than position
                if(newPosX > pbestX)
                {
                    bestPositions.insertElementAt(p.getP(), i);
                    //Global best will only change when individual best changes too???
                    findGBest();
                }
            }
        }
        for(int k=0; k<swarm.capacity(); k++) {
            System.out.print(swarm.elementAt(k).getP().getX()+"["+k+"],\n");
        }
        System.out.println(globalBestIndex);
    }
}
