import java.util.Vector;

/**
 * Created by Niall on 17/11/2015.
 */
public class PSOProcess {

    Vector<Particle> swarm = new Vector<Particle>(50);
    Vector<Position> bestPositions = new Vector<Position>(50);
    int globalBestIndex;
    double fitness=0;

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
            if(swarm.elementAt(i).getP().getX() > fitness)
            {
                fitness = swarm.elementAt(i).getP().getX();
                globalBestIndex = i;
            }
        }
    }

    public void execute() {
        this.initialise();
        for(int i=0; i<swarm.capacity(); i++)
        {
            System.out.print(swarm.elementAt(i).getP().getX()+"["+i+"],\n");
        }
        System.out.println(globalBestIndex);


        Particle p = new Particle();
        Particle q = new Particle();
        double newPosX, newPosY, newVelX, newVelY;
        double r1,r2,c1,c2;
        r1 = Math.random();
        r2 = Math.random();
        c1 = 1.05;
        c2 = 1.05;

        Position[] bestPositions = new Position[2];
        bestPositions[0] = p.getP();
        bestPositions[1] = q.getP();
        double pbestX = bestPositions[0].getX();
        double pbestY = bestPositions[1].getY();
        double gbestX = 1;
        double gbestY = 1;

        newVelX = p.getV().getX() + r1*c1*(pbestX - p.getP().getX()) + r2*c2*(gbestX - p.getP().getX());
        newVelY = p.getV().getY() + r1*c1*(pbestY - p.getP().getY()) + r2*c2*(gbestY - p.getP().getY());
        newPosX = p.getP().getX() + p.getV().getX();
        newPosY = p.getP().getY() + p.getV().getY();
        p.setP(newPosX,newPosY);
        p.setV(newVelX, newVelY);
    }
}
