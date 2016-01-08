//import java.awt.RenderingHints;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.NumberAxis;
//import org.jfree.chart.plot.FastScatterPlot;
//import org.jfree.ui.ApplicationFrame;
//import org.jfree.ui.RefineryUtilities;
//
//
//public class FastScatterPlotDemo extends ApplicationFrame implements Constants {
//
//    /** A constant for the number of items in the sample dataset. */
//    private static final int COUNT = swarmSize;
//
//    /** The data. */
//    public float[][] data = new float[2][COUNT];
//    static FastScatterPlot plot;
//
//
//    public FastScatterPlotDemo(final String title) {
//
//        super(title);
//        populateData(10000);
//        final NumberAxis domainAxis = new NumberAxis("X");
//        domainAxis.setRange(lowerBound,upperBound);
//        final NumberAxis rangeAxis = new NumberAxis("Y");
//        rangeAxis.setRange(lowerBound, upperBound);
//        plot = new FastScatterPlot(this.data, domainAxis, rangeAxis);
//        final JFreeChart chart = new JFreeChart("PSO Plot", plot);
//
//        // force aliasing of the rendered content..
//        chart.getRenderingHints().put
//                (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        final ChartPanel panel = new ChartPanel(chart, true);
//        panel.setPreferredSize(new java.awt.Dimension(500, 270));
//        //      panel.setHorizontalZoom(true);
//        //    panel.setVerticalZoom(true);
//        panel.setMinimumDrawHeight(10);
//        panel.setMaximumDrawHeight(2000);
//        panel.setMinimumDrawWidth(20);
//        panel.setMaximumDrawWidth(2000);
//
//        setContentPane(panel);
//    }
//
//
//    /**
//     * Populates the data array with random values.
//     */
//    public void populateData(int mul) {
//
//        PSOProcess p = new PSOProcess(mul);
//        p.execute();
//        for(int i=0; i<p.swarm.capacity(); i++) {
//            this.data[0][i] = (float)p.swarm.elementAt(i).getP()[0];
//            this.data[1][i] = (float)p.swarm.elementAt(i).getP()[1];
//        }
//
//    }
//
//    /**
//     * Starting point for the demonstration application.
//     *
//     * @param args  ignored.
//     */
//    public static void main(final String[] args) {
//
//        final FastScatterPlotDemo demo = new FastScatterPlotDemo("PSO Plot");
//        demo.pack();
//        RefineryUtilities.centerFrameOnScreen(demo);
//        demo.setVisible(true);
//
//        for(int i=0; i<100; i++)
//        {
//            try {
//                Thread.sleep(100);                 //1000 milliseconds is one second.
//            } catch(InterruptedException ex) {
//                Thread.currentThread().interrupt();
//            }
//
//            demo.populateData(i);
//            plot.setData(demo.data);
//        }
//    }
//}
