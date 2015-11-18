/**
 * Created by Niall on 17/11/2015.
 */
public class Position implements Constants{

    private double[] pos;

    public Position(double[] p) {
        pos = p;
    }

    public void setPos(double[] p) {pos = p;}

    public double[] getPos()
    {
        return pos;
    }

    @Override
    public String toString() {
        String position = "Positions ";
        for(int i=0; i<dimensions; i++)
        {
            position = position + "["+i+"]"+pos[i]+", ";
        }
        return position;
    }
}
