public class Particle implements Constants{

    private Double[] pos;
    private Double[] vel;
    private Double[] bestPosition;
    private Double[] neighbourhoodBest;

    public Particle(int dimension, double upperBound, double lowerBound)
    {
        pos = new Double[dimension];
        vel = new Double[dimension];
        for(int i=0; i<dimension; i++) {
            //Set randomly inside problem bounds
            pos[i] = (upperBound - lowerBound) * Math.random() + lowerBound;
            //Set using half diff method
            vel[i] = (((upperBound - lowerBound) * Math.random() + lowerBound) - pos[i]) / 2;
        }
        bestPosition = pos;
    }

    public void setP(Double[] p) {pos = p;}

    public Double[] getP() {return pos;}

    public void setV(Double[] v) {vel = v;}

    public Double[] getV() {return vel;}

    public void setBestPosition(Double[] p) {bestPosition = p;}

    public Double[] getBestPosition() {return bestPosition;}

    public void setNeighbourhoodBest(Double[] p) {neighbourhoodBest = p;}

    public Double[] getNeighbourhoodBest() {return neighbourhoodBest;}


}
