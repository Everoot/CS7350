import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class PartOne {
    public static class Graph {

        // Add edge
        static void addEdge(ArrayList<ArrayList<Integer>> am, int s, int d) {
            am.get(s).add(d);  // for a directed graph with an edge pointing from s d
            am.get(d).add(s);
        }
    }

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
            //long startTime = System.nanoTime();
            for (int i = 0; i < V; i++) {
                if (i == V - 1) {
                    addEdge(am, i, 0);   // head -- tail
                } else {
                    addEdge(am, i, i + 1);
                }
            }
            //long endTime = System.nanoTime();
            //time = endTime - startTime;
        }
    }

    //    static class Random{
//
//    }
    // DIST
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

        // ------------------------ graph----------------------------
        // if want to see the graph please remove the annotation

        private int ySize;
        private int dataNumber;
        private int xSize;
        ArrayList<Double> list = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();


        public void uniformDistribution(int xSize, int ySize, int dataNumber) {
            //int ySize = 8;
            //int xSize = 12;
            //int dataNumber = v; //
            this.ySize = ySize > 3 ? ySize : 3;
            this.xSize = xSize > 3 ? xSize : 3;
            this.dataNumber = dataNumber > 1000 ? dataNumber : 1000;
            init();
        }

        private void init() {
            for (int i = 0; i < dataNumber; i++) {
                list.add( Math.random() * dataNumber);
            }

            for (int i = 1; i <= ySize; i++) {
                map.put(i, 0);
            }
        }

        public void analysis() {

            Supplier<Stream<Double>> supp = () -> list.stream();

            Comparator<Double> comp = (e1, e2) -> e1 > e2 ? 1 : -1;

            double max = supp.get().max(comp).get();
            double min = supp.get().min(comp).get();
            double range = (max - min) / this.ySize;

            for (int i = 1; i <= this.ySize; i++) {
                double start = min + (i - 1) * range;
                double end = min + i * range;
                Stream<Double> stream = supp.get()
                        .filter((e) -> e >= start).filter((e) -> e < end);
                map.put(i, (int) stream.count());
            }
        }

        public void grawValue() {
            int ScaleSize = 14;
            int avgScale = this.dataNumber / xSize;
            int printSize = ScaleSize - String.valueOf(avgScale).length();

            for (int i = 0; i <= xSize; i++) {
                printChar(' ', printSize);
                System.out.print(i * avgScale);
            }
            System.out.println("");
            for (int i = 0; i <= xSize; i++) {
                if (i == 0) {
                    printChar(' ', printSize);
                } else {
                    printChar('-', ScaleSize);
                }
            }
            System.out.println();

            for (int i = 1; i <= ySize; i++) {
                printChar(' ', printSize - 1 - String.valueOf(i).length());
                System.out.print(i + ":");
                int scaleValue = map.get(i);
                double grawSize = scaleValue / (avgScale * 1.0 / ScaleSize);
                grawSize = (grawSize > 0 && grawSize < 1) ? 1 : grawSize;
                printChar('█', (int) grawSize);
                System.out.println(" " + scaleValue + "\n");
            }


        }
        // ---------------------------------------------------------------------
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

        // ------------------------ graph----------------------------
        // if want to see the graph please remove the annotation

        // draw
        private int ySize;
        private int dataNumber;
        private int xSize;
        ArrayList<Double> list = new ArrayList<>(); //
        Map<Integer, Integer> map = new HashMap<>(); //


        public void skewedDistribution(int xSize, int ySize, int dataNumber) {
            //int ySize = 8;
            //int xSize = 12;
            //int dataNumber = v; //
            this.ySize = ySize > 3 ? ySize : 3;
            this.xSize = xSize > 3 ? xSize : 3;
            this.dataNumber = dataNumber > 1000 ? dataNumber : 1000;
            init();
        }

        private void init() {
            double source;
            int a = 0;
            int b = dataNumber;
            int c = 0;
            double F = (c - a) / (b - a);

            for (int i = 0; i < dataNumber; i++) {
                double rand = Math.random();
                if (rand < F) {
                    source = (a + Math.sqrt(rand * (b - a) * (c - a)));
                } else {
                    source = (b - Math.sqrt((1 - rand) * (b - a) * (b - c)));
                }
                list.add(source);
            }

            for (int i = 1; i <= ySize; i++) {
                map.put(i, 0);
            }
        }

        public void analysis() {

            Supplier<Stream<Double>> supp = () -> list.stream();

            Comparator<Double> comp = (e1, e2) -> e1 > e2 ? 1 : -1;

            double max = supp.get().max(comp).get();
            double min = supp.get().min(comp).get();
            double range = (max - min) / this.ySize;

            for (int i = 1; i <= this.ySize; i++) {
                double start = min + (i - 1) * range;
                double end = min + i * range;
                Stream<Double> stream = supp.get()
                        .filter((e) -> e >= start).filter((e) -> e < end);
                map.put(i, (int) stream.count());
            }
        }

        public void grawValue() {
            int ScaleSize = 14;
            int avgScale = this.dataNumber / xSize;
            int printSize = ScaleSize - String.valueOf(avgScale).length();

            for (int i = 0; i <= xSize; i++) {
                printChar(' ', printSize);
                System.out.print(i * avgScale);
            }
            System.out.println("");
            for (int i = 0; i <= xSize; i++) {
                if (i == 0) {
                    printChar(' ', printSize);
                } else {
                    printChar('-', ScaleSize);
                }
            }
            System.out.println();

            for (int i = 1; i <= ySize; i++) {
                printChar(' ', printSize - 1 - String.valueOf(i).length());
                System.out.print(i + ":");
                int scaleValue = map.get(i);
                double grawSize = scaleValue / (avgScale * 1.0 / ScaleSize);
                grawSize = (grawSize > 0 && grawSize < 1) ? 1 : grawSize;
                printChar('█', (int) grawSize);
                System.out.println(" " + scaleValue + "\n");
            }

        }
        //----------------------------------------------------------------------------------
    }


    // YOURS
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

                if (source == dest || am.get(source).contains(dest)) {
                    continue;
                } else {
                    addEdge(am, source, dest);
                    e--;
                }
            }
        }


        // ------------------------ graph----------------------------
        // if want to see the graph please remove the annotation

        private int ySize;
        private int dataNumber;
        private int xSize;
        ArrayList<Double> list = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();

        public void gaussDistribution(int xSize, int ySize, int dataNumber) {
            //int ySize = 8;
            //int xSize = 12;
            //int dataNumber = v; //
            this.ySize = ySize > 3 ? ySize : 3;
            this.xSize = xSize > 3 ? xSize : 3;
            this.dataNumber = dataNumber > 1000 ? dataNumber : 1000;
            init();
        }

        private void init() {
            Random rand = new Random();
            for (int i = 0; i < dataNumber; i++) {
                list.add(dataNumber/10 * rand.nextGaussian() +dataNumber/2);
            }

            for (int i = 1; i <= ySize; i++) {
                map.put(i, 0);
            }
        }

        public void analysis() {

            Supplier<Stream<Double>> supp = () -> list.stream();
            Comparator<Double> comp = (e1, e2) -> e1 > e2 ? 1 : -1;
            double max = supp.get().max(comp).get();
            double min = supp.get().min(comp).get();
            double range = (max - min) / this.ySize;
            for (int i = 1; i <= this.ySize; i++) {
                double start = min + (i - 1) * range;
                double end = min + i * range;
                Stream<Double> stream = supp.get()
                        .filter((e) -> e >= start).filter((e) -> e < end);
                map.put(i, (int) stream.count());
            }
        }

        public void grawValue() {
            int ScaleSize = 14;
            int avgScale = this.dataNumber / xSize;
            int printSize = ScaleSize - String.valueOf(avgScale).length();
            for (int i = 0; i <= xSize; i++) {
                printChar(' ', printSize);
                System.out.print(i * avgScale);
            }
            System.out.println("");
            for (int i = 0; i <= xSize; i++) {
                if (i == 0) {
                    printChar(' ', printSize);
                } else {
                    printChar('-', ScaleSize);
                }
            }
            System.out.println();
            for (int i = 1; i <= ySize; i++) {
                printChar(' ', printSize - 1 - String.valueOf(i).length());
                System.out.print(i + ":");
                int scaleValue = map.get(i);
                double grawSize = scaleValue / (avgScale * 1.0 / ScaleSize);
                grawSize = (grawSize > 0 && grawSize < 1) ? 1 : grawSize;
                printChar('█', (int) grawSize);
                System.out.println(" " + scaleValue + "\n");
            }


        }
        // -----------------------------------------------------------------------------

    }

    public static void printChar(char c, int number) {
        for (int i = 0; i < number; i++) {
            System.out.print(c);
        }
    }





    public static void printGraph(ArrayList<ArrayList<Integer>> am) {
        for (int i = 0; i < am.size(); i++) {
            System.out.print("\nVertex " + i + ":");
            for (int j = 0; j < am.get(i).size(); j++) {
                System.out.print(" -> " + am.get(i).get(j));
            }
            System.out.println();
        }
    }

    public static void input(int V, int E, int G) {
        // V: Number of vertices. (Max 10,000)
        // E: Number of conflicts between pairs of vertices for random graphs. (MAX - 2,000,000)
        // G: COMPTLETE| CYCLE | RANDOM (with DIST below)
        // DIST = UNIFORM | SKEWED |YOURS
        // 1: COMPTLETE
        // 2: CYCLE
        // 3: UNIFORM
        // 4: SKEWED
        // 5: YOURS

        if (G == 1) {
            comptele(V);
        } else if (G == 2) {
            cycle(V);
        } else if (G == 3) {
            uniform(V, E);
        } else if (G == 4) {
            skewed(V, E);
        } else if (G == 5) {
            gauss(V, E);
        }
    }

    public static void comptele(int v) {
        //System.out.println("Comptele graph:");
        ArrayList<ArrayList<Integer>> completegraph = new ArrayList<ArrayList<Integer>>(v);
        for (int i = 0; i < v; i++) {
            completegraph.add((new ArrayList<Integer>()));
        }

        Complete.allEdge(completegraph, v);
        //printGraph(completegraph);
    }

    public static void cycle(int v) {
        System.out.println("Cycle graph:");
        ArrayList<ArrayList<Integer>> cyclegraph = new ArrayList<ArrayList<Integer>>(v);
        for (int i = 0; i < v; i++) {
            cyclegraph.add(new ArrayList<Integer>());
        }
        Cycle.allEdgeCycle(cyclegraph, v);

        printGraph(cyclegraph);


    }

    public static void uniform(int v, int e) {
        System.out.println("Uniform graph:");
        ArrayList<ArrayList<Integer>> uniformgraph = new ArrayList<ArrayList<Integer>>(v);
        for (int i = 0; i < v; i++) {
            uniformgraph.add(new ArrayList<>());
        }
        Uniform.uniformRandom(uniformgraph, v, e);
        printGraph(uniformgraph);

        //Draw picture
        Uniform u = new Uniform();
        u.uniformDistribution(8,12,10000);
        u.analysis();
        u.grawValue();

    }

    public static void skewed(int v, int e) {
        System.out.println("Skewed graph:");
        ArrayList<ArrayList<Integer>> skewedgraph = new ArrayList<ArrayList<Integer>>(v);
        for (int i = 0; i < v; i++) {
            skewedgraph.add(new ArrayList<>());
        }
        Skewed.skewedRandom(skewedgraph, v, e);
        printGraph(skewedgraph);

//         Draw picture
        Skewed s = new Skewed();
        s.skewedDistribution(8, 12, 10000);
        s.analysis();
        s.grawValue();
    }

    public static void gauss(int v, int e) {
        System.out.println("Gauss graph:");
        ArrayList<ArrayList<Integer>> gaussgraph = new ArrayList<ArrayList<Integer>>(v);
        for (int i = 0; i < v; i++) {
            gaussgraph.add(new ArrayList<>());
        }

        Gauss.gaussRandom(gaussgraph, v, e);
        printGraph(gaussgraph);


        // draw picture
        Gauss g = new Gauss();
        g.gaussDistribution(8, 12, 10000);
        g.analysis();
        g.grawValue();

    }


    public static long time_calculate(int V, int G, int E) {
        long time = 0;
        if (G == 1) {
            long startTime = System.nanoTime();
            comptele(V);
            long endTime = System.nanoTime();
            time = endTime - startTime;

        } else if (G == 2) {
            long startTime = System.nanoTime();
            cycle(V);
            long endTime = System.nanoTime();
            time = endTime - startTime;

        } else if (G == 3) {
            long startTime = System.nanoTime();
            uniform(V, E);
            long endTime = System.nanoTime();
            time = endTime - startTime;

        } else if (G == 4) {
            long startTime = System.nanoTime();
            skewed(V, E);
            long endTime = System.nanoTime();
            time = endTime - startTime;

        } else if (G == 5) {
            long startTime = System.nanoTime();
            gauss(V, E);
            long endTime = System.nanoTime();
            time = endTime - startTime;
        }
        return time;

    }

    public static void time(int G){
        int n0 = 1000;
        int n1 = 2000;
        int n2 = 3000;
        int n3 = 4000;
        int n4 = 5000;
        int n5 = 6000;
        int n6 = 7000;
        int n7 = 8000;
        int n8 = 9000;
        int n9 = 10000;

        long[] result = new long[10];
        result[0] = time_calculate(n0,G,n0-1);
        result[1] = time_calculate(n1, G, n1-1);
        result[2] = time_calculate(n2,G,n2-1);
        result[3] = time_calculate(n3, G, n3-1);
        result[4] = time_calculate(n4, G, n4-1);
        result[5] = time_calculate(n5,G,n5-1);
        result[6] = time_calculate(n6,G,n6-1);
        result[7] = time_calculate(n7,G,n7-1);
        result[8] = time_calculate(n8,G,n8-1);
        result[9] = time_calculate(n9,G,n9-1);

        System.out.println(Arrays.toString(result));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(result[0],"time", "1000");
        dataset.addValue(result[1],"time", "2000");
        dataset.addValue(result[2],"time", "3000");
        dataset.addValue(result[3],"time", "4000");
        dataset.addValue(result[4],"time", "5000");
        dataset.addValue(result[5],"time", "6000");
        dataset.addValue(result[6],"time", "7000");
        dataset.addValue(result[7],"time", "8000");
        dataset.addValue(result[8],"time", "9000");
        dataset.addValue(result[9],"time", "10000");

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

    public static void main(String[] args) {
        // V: Number of vertices. (Max 10,000)
        // E: Number of conflicts between pairs of vertices for random graphs. (MAX - 2,000,000)
        // G: COMPTLETE| CYCLE | RANDOM (with DIST below)
        // DIST = UNIFORM | SKEWED |YOURS
        // 1: COMPTLETE
        // 2: CYCLE
        // 3: UNIFORM
        // 4: SKEWED
        // 5: YOURS
//
        int v = 10;
        int e = 20;

        input(v, e, 1);
//
        input(v, e, 2);
//
        input(v, e, 3);
        input(v, e, 4);
        input(v, e, 5);


//        //Draw picture
        Uniform u = new Uniform();
        u.uniformDistribution(8,12,10000);
        u.analysis();
        u.grawValue();

        Skewed s = new Skewed();
        s.skewedDistribution(8, 12, 10000);
        s.analysis();
        s.grawValue();

        Gauss g = new Gauss();
        g.gaussDistribution(8, 12, 10000);
        g.analysis();
        g.grawValue();

    }
}