import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Time1 {
    public static class Complete {
        public static void addEdge(ArrayList<ArrayList<Integer>> am, int s, int d) {
            am.get(s).add(d);
            am.get(d).add(s);
        }

        public static void allEdge(ArrayList<ArrayList<Integer>> am, int V) {
            for (int i = 0; i < V; i++) {
                for (int j = i + 1; j < V; j++) {
                    addEdge(am, i, j);
                }
            }
        }
    }

    static class Cycle {
        //private static long time;
        static void addEdge(ArrayList<ArrayList<Integer>> am, int s, int d) {
            am.get(s).add(d);
            am.get(d).add(s);
        }
        static void allEdgeCycle(ArrayList<ArrayList<Integer>> am, int V) {

            for (int i = 0; i < V; i++) {
                if (i == V - 1) {
                    addEdge(am, i, 0);   // head -- tail
                } else {
                    addEdge(am, i, i + 1);
                }
            }

        }
    }

    static class Uniform {
        static void addEdge(ArrayList<ArrayList<Integer>> am, int s, int d) {
            am.get(s).add(d);
            am.get(d).add(s);
        }

        // If you provide an integer parameter to "nextInt",
        // it will return an integer from a uniform distribution between 0 and one less than the parameter.
        static void uniformRandom(ArrayList<ArrayList<Integer>> am, int v, int e) {
            //Random rand = new Random();
            while (e > 0) {
                int source = (int) (Math.random() * v); // Printing the random number between [0,v-1]
                int dest = (int) (Math.random() * v);
                // edge exit?
                if (source == dest || am.get(source).contains(dest)) {
                    continue;
                } else {
                    addEdge(am, source, dest);
                    e--;
                }
            }
        }
    }

    static class Skewed {
        static void addEdge(ArrayList<ArrayList<Integer>> am, int s, int d) {
            am.get(s).add(d);
            am.get(d).add(s);
        }

        static void skewedRandom(ArrayList<ArrayList<Integer>> am, int v, int e) {
//            Random rand = new Random();
            int source = -1;
            int dest = -1;
            int a = 0;
            int b = v;
            int c = 0;
            double F = (c - a) / (b - a);//
            while (e > 0) {
                double rand = Math.random();
                if (rand < F) {
                    source = (int) (a + Math.sqrt(rand * (b - a) * (c - a)));
                } else {
                    source = (int) (b - Math.sqrt((1 - rand) * (b - a) * (b - c)));
                }

                double rand2 = Math.random();
                if (rand2 < F) {
                    dest = (int) (a + Math.sqrt(rand2 * (b - a) * (c - a)));
                } else {
                    dest = (int) (b - Math.sqrt((1 - rand2) * (b - a) * (b - c)));
                }

                if (source == dest || am.get(source).contains(dest)) {
                    continue;
                } else {
                    addEdge(am, source, dest);
                    e--;
                }
            }

        }
    }

    static class Gauss {
        static void addEdge(ArrayList<ArrayList<Integer>> am, int s, int d) {
            am.get(s).add(d);
            am.get(d).add(s);
        }

        //Gauss
        static void gaussRandom(ArrayList<ArrayList<Integer>> am, int v, int e) {
            Random rand = new Random();
            while (e > 0) {

                int source = (int) (v / 10 * rand.nextGaussian() + v / 2);
                int dest = (int) (v / 10 * rand.nextGaussian() + v / 2);
//                source = Math.abs(source);
//                dest = Math.abs(dest);
                try{
                    if (source == dest || am.get(source).contains(dest)) {
                        continue;
                    } else {
                        addEdge(am, source, dest);
                        e--;
                    }
                }catch (Exception ignored){}

            }
        }
    }

    private static void plotResults(int[] vertexCounts, long[] executionTimes) {
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Time")
                .xAxisTitle("Number of Vertices (V)")
                .yAxisTitle("Execution Time (nanoseconds)")
                .build();

        int[] executionTimecopy = new int[executionTimes.length];
        for (int i = 0; i < executionTimes.length; i++){
            executionTimecopy[i] = (int) executionTimes[i];
        }
        XYSeries series = chart.addSeries("Execution Time", vertexCounts, executionTimecopy);
        series.setMarker(SeriesMarkers.CIRCLE);
        new SwingWrapper<>(chart).displayChart();
    }


    public static int[] countConflicts(ArrayList<ArrayList<Integer>> am){
        int[] conflicts = new int[am.size()];
        for (int i = 0; i < am.size(); i++){
            conflicts[i] = am.get(i).size();
        }
        return conflicts;
    }

    public static ArrayList<ArrayList<Integer>> initGraph(int v) {
        ArrayList<ArrayList<Integer>> am = new ArrayList<>(v);
        for (int i = 0; i < v; i++) {
            am.add(new ArrayList<>());
        }
        return am;
    }

    public static int[] getDegrees(ArrayList<ArrayList<Integer>> am) {
        int[] degrees = new int[am.size()];
        for (int i = 0; i < am.size(); i++) {
            degrees[i] = am.get(i).size();
        }
        return degrees;
    }

    public static void plotHistogram(String title, int[] degrees) {
        List<Integer> data = new ArrayList<Integer>();
        for (int i = 0; i < degrees.length; i++){
            data.add(degrees[i]);
        }

        Histogram histogram = new Histogram(data, 10);
        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title(title)
                .xAxisTitle("Degree")
                .yAxisTitle("Frequency")
                .build();

        chart.addSeries("Degree Distribution", histogram.getxAxisData(), histogram.getyAxisData());
        new SwingWrapper<>(chart).displayChart();
    }



    public static void main(String[] args) {
        // -------------------Part one different generate graph running time-------------------------------
//        int[] vertexCounts = new int[1000];
//        vertexCounts[0] = 100;
//        for (int i = 1; i < vertexCounts.length; i++){
//            vertexCounts[i] = vertexCounts[i-1]+vertexCounts[0];
//        }
        int[] vertexCounts = {10, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};

        long[] executionTimes = new long[vertexCounts.length];

        for (int i = 0; i < vertexCounts.length; i++) {
            int V = vertexCounts[i];
            ArrayList<ArrayList<Integer>> am = new ArrayList<>(V);
            for (int j = 0; j < V; j++) {
                am.add(new ArrayList<>());
            }

            long startTime = System.nanoTime();
            Complete.allEdge(am, V);
            //Cycle.allEdgeCycle(am,V);
            // Uniform.uniformRandom(am, V, V/2);
            // Uniform.uniformRandom(am, V, V/4);
            //Uniform.uniformRandom(am, V, V/8);
            // Uniform.uniformRandom(am, V, 2*V);
            //Uniform.uniformRandom(am, V, 4*V);
            //Uniform.uniformRandom(am, V, 8*V);

//            Skewed.skewedRandom(am, V, V/8);
//            Skewed.skewedRandom(am,V,V/4);
//            Skewed.skewedRandom(am, V, V/2);
//            Skewed.skewedRandom(am, V, V);
//            Skewed.skewedRandom(am, V, 2*V);
//            Skewed.skewedRandom(am, V, 4* V);
//            Skewed.skewedRandom(am, V, 8* V);
//            Gauss.gaussRandom(am,V, V/8);
//            Gauss.gaussRandom(am,V, V/4);
//            Gauss.gaussRandom(am, V, V/2);
//            Gauss.gaussRandom(am, V, V);
//            Gauss.gaussRandom(am,V, 2*V);
//            Gauss.gaussRandom(am,V, 4*V);
//            Gauss.gaussRandom(am, V, 8*V);
//
//
            long endTime = System.nanoTime();
            executionTimes[i] = endTime - startTime;
        }
        System.out.println(Arrays.toString(executionTimes));
        plotResults(vertexCounts, executionTimes);

        // ---------------------- conflict -------------------------------
            int v = 100;
            int Edges = 200;

            ArrayList<ArrayList<Integer>> amCycle = initGraph(v);
            ArrayList<ArrayList<Integer>> amComplete = initGraph(v);
            ArrayList<ArrayList<Integer>> amUniform = initGraph(v);
            ArrayList<ArrayList<Integer>> amSkewed = initGraph(v);
            ArrayList<ArrayList<Integer>> amGuass = initGraph(v);

            Cycle.allEdgeCycle(amCycle, v);
            Complete.allEdge(amComplete, v);
            Uniform.uniformRandom(amUniform, v,  Edges);
            Skewed.skewedRandom(amSkewed,v, Edges);
            Gauss.gaussRandom(amGuass,v, Edges);

            int[] cycleDegrees = getDegrees(amCycle);
            int[] completeDegrees = getDegrees(amComplete);
            int[] uniformDegrees = getDegrees(amUniform);
            int[] skewedDegrees = getDegrees(amSkewed);
            int[] guassDegrees = getDegrees(amGuass);

            plotHistogram("Cycle Graph", cycleDegrees);
            plotHistogram("Complete Graph", completeDegrees);
            plotHistogram("Uniform Random Graph", uniformDegrees);
            plotHistogram("Skewed Random Graph", skewedDegrees);
            plotHistogram("Guass Random Graph", guassDegrees);
        //

    }

}



