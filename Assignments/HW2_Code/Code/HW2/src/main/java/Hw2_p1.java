import java.util.Arrays;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Hw2_p1 {
    public static void main(String[] args) {
        int n1 = 1000000;
        int n2 = 2000000;
        int n3 = 3000000;
        int n4 = 4000000;
        int n5 = 5000000;
        int n6 = 6000000;
        int n7 = 7000000;
        int n8 = 8000000;
        int n9 = 9000000;
        int n10 = 10000000;
        long[] result = new long[10];
        result[0] = time_calculate(n1);
        result[1] = time_calculate(n2);
        result[2] = time_calculate(n3);
        result[3] = time_calculate(n4);
        result[4] = time_calculate(n5);
        result[5] = time_calculate(n6);
        result[6] = time_calculate(n7);
        result[7] = time_calculate(n8);
        result[8] = time_calculate(n9);
        result[9] = time_calculate(n10);

        System.out.println(Arrays.toString(result));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(result[0],"time", "1000000");
        dataset.addValue(result[1],"time", "2000000");
        dataset.addValue(result[2],"time", "3000000");
        dataset.addValue(result[3],"time", "4000000");
        dataset.addValue(result[4],"time", "5000000");
        dataset.addValue(result[5],"time", "6000000");
        dataset.addValue(result[6],"time", "7000000");
        dataset.addValue(result[7],"time", "8000000");
        dataset.addValue(result[8],"time", "9000000");
        dataset.addValue(result[9],"time", "10000000");

        JFreeChart chart = ChartFactory.createLineChart(
                "Result",
                "n",
                "time(ns)",
                dataset,
                PlotOrientation.VERTICAL,
                false,true, false
        );

        ChartFrame chartFrame = new ChartFrame("Test", chart);
        chartFrame.pack();;
        chartFrame.setVisible(true);

    }

    public static void hello(int n){
        for (int i = 0; i < n; i++){
            // println is constant, c, and called n times
            System.out.println("Hello world");
        }
    }
    // A function representing time, f(n) = c * n;
    // f(n) is $\theta(n)$

    public static long time_calculate(int n){
        long startTime = System.nanoTime();
        hello(n);
        long endTime = System.nanoTime();
        long time = endTime - startTime;
        return time;
    }
}