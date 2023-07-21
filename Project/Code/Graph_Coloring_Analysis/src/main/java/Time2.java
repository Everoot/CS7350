import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.*;

public class Time2 {
    // Part One

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

    public static int color(ArrayList<ArrayList<Integer>> am, ArrayList<Integer> Order){
        int[] colorassign = new int[am.size()];
        for(int i = 0; i < am.size(); i++){
            colorassign[i] = -1;
        }
        
        int max = 0;
        int count = 0;

        // The first
        colorassign[0] = 0;

        // A temporary array to store the available colors. False
        // value of available[cr] would mean that the color cr is
        // assigned to one of its adjacent vertices
        boolean available[] = new boolean[am.size()];

        // Initially, all colors are available
        for (int i = 0; i < am.size(); i++){
            available[i] = true;
        }

        // Assign colors to remaining V-1 vertices
        for (int i = 1; i < am.size(); i++){
            // Process all adjacent vertices and flag their colors
            // as unavailable
            //System.out.println("Accessing Order.get(" + i + ")");
//            System.out.println("Size of am: " + am.size());
//            System.out.println("Size of Order: " + Order.size());
            Iterator<Integer> it = am.get(Order.get(i)).iterator();
            while (it.hasNext()){
                int k = it.next();
                if (colorassign[Order.indexOf(k)] != -1){
                    available[colorassign[Order.indexOf(k)]] = false;

                }
            }

            // Find the first available color
            int cr;
            for (cr = 0; cr < am.size(); cr++) {
                if (available[cr]) {
                    break;
                }
            }

            // Assign the found color;
            colorassign[i] = cr;

            if (cr > max){
                max = cr;
            }

            // Reset the values back to true for the next iteration
            for (int j = 0; j < am.size(); j++) {
                available[j] = true;
            }

        }
        max = max+1;

//        // print the result
//        System.out.println("The color result:");
//        for (int i = 0; i < am.size(); i++)
//            System.out.println("Vertex " + Order.get(i) + " --->  Color "
//                    + colorassign[i]);
//
//
//        //return colorassign;
//        System.out.println("Need " + max + " colors.");
        return max;
    }

    private static void plotResults(String title, int[] vertexCounts, long[] executionTimes) {
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title(title)
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

    // Color
    public static void plotResults2(String title, int[] vertexCounts, List<int[]> colorsList, List<String> labels) {
        if (colorsList.size() != labels.size()) {
            throw new IllegalArgumentException("Colors and labels must have the same size.");
        }

        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title(title)
                .xAxisTitle("Number of Vertices (V)")
                .yAxisTitle("Color")
                .build();

        for (int i = 0; i < colorsList.size(); i++) {
            int[] colors = colorsList.get(i);
            XYSeries series = chart.addSeries(labels.get(i), vertexCounts, colors);
            series.setMarker(SeriesMarkers.CIRCLE);
        }

        new SwingWrapper<>(chart).displayChart();
    }

    // Time
    public static void plotResults3(String title, int[] vertexCounts, List<long[]> executionTimesList, List<String> labels) {
        if (executionTimesList.size() != labels.size()) {
            throw new IllegalArgumentException("Execution times list and labels must have the same size.");
        }

        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title(title)
                .xAxisTitle("Number of Vertices (V)")
                .yAxisTitle("Execution Time (nanoseconds)")
                .build();

        for (int i = 0; i < executionTimesList.size(); i++) {
            long[] executionTimes = executionTimesList.get(i);

            int[] executionTimeCopy = new int[executionTimes.length];
            for (int j = 0; j < executionTimes.length; j++) {
                executionTimeCopy[j] = (int) executionTimes[j];
            }

            XYSeries series = chart.addSeries(labels.get(i), vertexCounts, executionTimeCopy);
            series.setMarker(SeriesMarkers.CIRCLE);
        }

        new SwingWrapper<>(chart).displayChart();
    }


    public static int[] getDegrees(ArrayList<ArrayList<Integer>> am) {
        int[] degrees = new int[am.size()];
        for (int i = 0; i < am.size(); i++) {
            degrees[i] = am.get(i).size();
        }
        return degrees;
    }

    public static void plotHistogram(String title, List<int[]> datasets, List<String> labels) {
        if (datasets.size() != labels.size()) {
            throw new IllegalArgumentException("Datasets and labels must have the same size.");
        }

        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title(title)
                .xAxisTitle("Degree")
                .yAxisTitle("Frequency")
                .build();

        for (int i = 0; i < datasets.size(); i++) {
            List<Integer> data = new ArrayList<>();
            for (int degree : datasets.get(i)) {
                data.add(degree);
            }
            Histogram histogram = new Histogram(data, 10);
            chart.addSeries(labels.get(i), histogram.getxAxisData(), histogram.getyAxisData());
        }

        new SwingWrapper<>(chart).displayChart();
    }





    // -----------------------------------------------------------------------------------------------
    // Method 1: Smallest Last Vertex (SLV) Ordering:
    public static int terminalclique;
    public static int maxdegree;
    public static void removeVertex(int vertex, ArrayList<ArrayList<Integer>> am) {
        for (int i = 0; i < am.size(); i++) {
            am.get(i).remove((Integer) vertex);
        }
        am.set(vertex, new ArrayList<>());
    }

    public static int findSmallestDegreeVertex(ArrayList<ArrayList<Integer>> am) {
        int minDegree = Integer.MAX_VALUE;
        int minDegreeVertex = -1;

        for (int i = 0; i < am.size(); i++) {
            int degree = am.get(i).size();

            //System.out.println("The degree of Vertex " + i + ": " + degree);
            if (degree < minDegree && degree > 0) {
                minDegree = degree;
                minDegreeVertex = i;

            }
        }

        return minDegreeVertex;
    }

    public static ArrayList<ArrayList<Integer>> copy(ArrayList<ArrayList<Integer>> am){
        ArrayList<ArrayList<Integer>> am2 = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < am.size(); i++){
            am2.add(am.get(i));
        }

        return am2;
    }

    public static ArrayList<Integer> smallestLastVertexOrdering(ArrayList<ArrayList<Integer>> originalam) {
        ArrayList<ArrayList<Integer>> am = copy(originalam);
        ArrayList<Integer> slvOrder = new ArrayList<>();
        int res = -1;
        maxdegree = -1;
        terminalclique = -1;

        while (slvOrder.size() < am.size()) {
            //printAdjacencyList(am);
            int minDegreeVertex = findSmallestDegreeVertex(am);

            if (minDegreeVertex == -1) {
                slvOrder.add(0, res);
                break;
            }

            //System.out.println("The min degree Vertex is (will be deleted): " + minDegreeVertex);
            //System.out.println();

            if (am.get(minDegreeVertex).size() > maxdegree){
                maxdegree = am.get(minDegreeVertex).size();
            }

            if (terminalclique < am.get(minDegreeVertex).size() &&
                    ((am.size()-slvOrder.size()-1) == am.get(minDegreeVertex).size())){
                terminalclique = am.get(minDegreeVertex).size();
            }


            slvOrder.add(0, minDegreeVertex);
            //System.out.println(slvOrder);
            if (am.get(minDegreeVertex).size() == 1){
                res = am.get(minDegreeVertex).get(0);
            }
            removeVertex(minDegreeVertex,am);

        }
        //System.out.println("The order to delete (from right to left): " + slvOrder);
        return slvOrder;
    }


    // -----------------------------------------------------------------------------------------------
    // Method 2: Smallest Original Degree Last (SODL) Ordering:
    public static int findMaxDegreeVertex(ArrayList<ArrayList<Integer>> am, ArrayList<Integer> exit){
        int maxDegree = Integer.MIN_VALUE;
        int maxDegreeVertex = 0;

        for (int i = 0; i < am.size(); i++){
            int degree = am.get(i).size();

            //System.out.println("The degree of Vertex " + i + ": " + degree);
            if (degree > maxDegree && degree > 0 && !exit.contains(i)){
                maxDegree = degree;
                maxDegreeVertex = i;
            }
        }

        return maxDegreeVertex;
    }


    private static ArrayList<Integer> smallestOriginalDegreeLast(ArrayList<ArrayList<Integer>> am){
        ArrayList<Integer> solOrder = new ArrayList<>();
        int V = am.size();
        //System.out.println(V);


        while(solOrder.size() < am.size()){
            int minDegreeVertex = findMaxDegreeVertex(am, solOrder);

            //System.out.println("The vertex add to solOrder "+ minDegreeVertex + "\n");

            solOrder.add(0, minDegreeVertex);

        }

        //System.out.println("Smallest orginal degree Last ORder (from right to left): " + "\n" + solOrder);
        return solOrder;
    }

    // -----------------------------------------------------------------------------------------------
    // Method 3: Uniform Random Ordering:
    public static ArrayList<Integer> uniformrandom(ArrayList<ArrayList<Integer>> am){
        ArrayList<Integer> unOrder = new ArrayList<Integer>();
        for (int i = 0; i < am.size(); i++){
            unOrder.add(i);
        }

        Collections.shuffle(unOrder,new Random());
        // the list to be shuffled
        // the source of randomness to use to shuffle the list.


        //System.out.println("Uniform random Order is : " + unOrder +"\n");


        return unOrder;
    }

    // -----------------------------------------------------------------------------------------------
    // Method 4: Breadth-First Search (BFS) Ordering:
    public static ArrayList<Integer> BFSOrder(ArrayList<ArrayList<Integer>> am, int startNode){
        // Create a queue for bfs;
        ArrayList<Integer> bfsOrder = new ArrayList<>();

        // Make all the vertices as not visited(By default set as false)
        boolean visited[] = new boolean[am.size()];
        Queue<Integer> queue = new LinkedList<>();

        // Mark the current node as visited and enqueue it
        // start from the first one.
        visited[startNode] = true;
        queue.offer(startNode);

        while(!queue.isEmpty()){
            int node = queue.poll();
            bfsOrder.add(node);
            //System.out.print(node + " ");

            for (int neighbor : am.get(node)){
                if (!visited[neighbor]){
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }

        }
        //System.out.println("BFSOrder is : " + bfsOrder +"\n");

        return bfsOrder;
    }

    // -----------------------------------------------------------------------------------------------
    // Method 5: Depth-First Search (DFS) Ordering:
    public static ArrayList<Integer> DFSOrder(ArrayList<ArrayList<Integer>> am, int startNode){
        ArrayList<Integer> dfsOrder = new ArrayList<>();
        boolean[] visited = new boolean[am.size()];

        dfsRecursive(am, startNode, visited, dfsOrder);
        //System.out.println("DFSOrder is : " + dfsOrder +"\n");
        return dfsOrder;
    }

    private static void dfsRecursive(ArrayList<ArrayList<Integer>> am, int startNode,
                                     boolean[] visited, ArrayList<Integer> dfsOrder){
        visited[startNode] = true;
        dfsOrder.add(startNode);

        for (int neighbor : am.get(startNode)){
            if(!visited[neighbor]){
                dfsRecursive(am,neighbor,visited,dfsOrder);
            }
        }
    }

    // -----------------------------------------------------------------------------------------------
    // Method 6: Lexicographic Breadth-First Search (LexBFS) Ordering
    public static ArrayList<Integer> lexBFS(ArrayList<ArrayList<Integer>> am, int startnode){
        int n = am.size();
        ArrayList<Integer> lexOrder = new ArrayList<>();

        // Create a mapping from node index to its neighbor set
        Map<Integer, Set<Integer>> nodeToNeighbors = new HashMap<>();
        for (int i = 0; i < n; i++) {
            Set<Integer> neighbors = new HashSet<>();
            for (int neighbor : am.get(i)) {
                neighbors.add(neighbor);
            }
            nodeToNeighbors.put(i, neighbors);
        }

        // Perform lexicographic BFS starting from the startnode
        Queue<Integer> bfsQueue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        bfsQueue.add(startnode);
        visited.add(startnode);

        while (!bfsQueue.isEmpty()) {
            int currentNode = bfsQueue.poll();
            lexOrder.add(currentNode);

            // Get unvisited neighbors sorted lexicographically
            ArrayList<Integer> unvisitedNeighbors = new ArrayList<>();
            for (int neighbor : nodeToNeighbors.get(currentNode)) {
                if (!visited.contains(neighbor)) {
                    unvisitedNeighbors.add(neighbor);
                }
            }
            Collections.sort(unvisitedNeighbors);

            // Add unvisited neighbors to the bfsQueue and mark them as visited
            for (int neighbor : unvisitedNeighbors) {
                bfsQueue.add(neighbor);
                visited.add(neighbor);
            }
        }

        //  If the graph is not connected, it's possible that not all nodes will be visited and added to lexOrder,
        //  which would result in lexOrder.size() being less than am.size().

        return lexOrder;
    }

    public static ArrayList<Integer> check(ArrayList<ArrayList<Integer>> am, ArrayList<Integer> order){
        if (am.size() != order.size()){
            for (int i = 0; i < am.size(); i++){
                if (!order.contains(i)){
                    order.add(i);
                }
            }
        }
        return order;
    }


    public static ArrayList<ArrayList<Integer>> initGraph(int v) {
        ArrayList<ArrayList<Integer>> am = new ArrayList<>(v);
        for (int i = 0; i < v; i++) {
            am.add(new ArrayList<>());
        }
        return am;
    }



    public static void main(String[] args){
//        int[] vertexCounts = new int[50];
//        vertexCounts[0] = 10;
//        for (int i = 1; i < vertexCounts.length; i++){
//            vertexCounts[i] = vertexCounts[i-1]+vertexCounts[0];
//        }
        int[] vertexCounts = {10, 50, 100, 200, 500, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};

        long[] executionTimesSLO = new long[vertexCounts.length];
        long[] executionTimesSODL = new long[vertexCounts.length];
        long[] executionTimeUNIF = new long[vertexCounts.length];
        long[] executionTimeBFS = new long[vertexCounts.length];
        long[] executionTimeDFS = new long[vertexCounts.length];
        long[] executionTimelexBFS = new long[vertexCounts.length];
        int[] colorsSLO = new int[vertexCounts.length];
        int[] colorsSODL = new int[vertexCounts.length];
        int[] colorsUNIF = new int[vertexCounts.length];
        int[] colorsBFS = new int[vertexCounts.length];
        int[] colorsDFS = new int[vertexCounts.length];
        int[] colorslexBFS = new int[vertexCounts.length];
        int colorsSLOmax = -1;
        int colorsSODLmax = -1;
        int colorsUNIFmax = -1;
        int colorsBFSmax = -1;
        int colorsDFSmax = -1;
        int colorslexBFSmax = -1;


        for (int i = 0; i < vertexCounts.length; i++) {
            int V = vertexCounts[i];
            ArrayList<ArrayList<Integer>> am = initGraph(V);
//            ArrayList<ArrayList<Integer>> am2 = initGraph(V);
//            ArrayList<ArrayList<Integer>> am3 = initGraph(V);
//            ArrayList<ArrayList<Integer>> am4 = initGraph(V);
//            ArrayList<ArrayList<Integer>> am5 = initGraph(V);
//            ArrayList<ArrayList<Integer>> am6 = initGraph(V);

//            ArrayList<Integer> order = new ArrayList<>();
//            for (int j = 0; j < V; j++) {
//                am.add(new ArrayList<>());
//                order.add(j);
//            }

            //Uniform.uniformRandom(am, V, V/8);
            //Uniform.uniformRandom(am, V, 2*V);

            //Complete.allEdge(am, V);
            //Cycle.allEdgeCycle(am, V);
            //Uniform.uniformRandom(am, V, V);
            //Skewed.skewedRandom(am, V, V/2);
            Gauss.gaussRandom(am, V, V/2);


            long startTime, endTime;

            startTime = System.nanoTime();
            ArrayList<Integer> order6 = lexBFS(am, 0);

//            colorslexBFS[i] = color(am, check(am,order6));
//            if (colorslexBFS[i] > colorslexBFSmax){
//                colorslexBFSmax = colorslexBFS[i];
//            }
            endTime = System.nanoTime();
            executionTimelexBFS[i] = endTime - startTime;


            startTime = System.nanoTime();
            //color(am, order);
            ArrayList<Integer> order5 = DFSOrder(am, 0);
//            colorsDFS[i] = color(am, check(am,order5));
//            if (colorsDFS[i] > colorsDFSmax){
//                colorsDFSmax = colorsDFS[i];
//            }
            endTime = System.nanoTime();
            executionTimeDFS[i] = endTime - startTime;

            startTime = System.nanoTime();
            //color(am, order);
            ArrayList<Integer> order4 = BFSOrder(am, 0);
//            colorsBFS[i] = color(am, check(am,order4));
//            if (colorsBFS[i] > colorsBFSmax){
//                colorsBFSmax = colorsBFS[i];
//            }
            endTime = System.nanoTime();
            executionTimeBFS[i] = endTime - startTime;

            startTime = System.nanoTime();
            //color(am, order);
            ArrayList<Integer> order3 = uniformrandom(am);
//            colorsUNIF[i] = color(am, check(am,order3));
//            if (colorsUNIF[i] > colorsUNIFmax){
//                colorsUNIFmax = colorsUNIF[i];
//            }
            endTime = System.nanoTime();
            executionTimeUNIF[i] = endTime - startTime;
//
            startTime = System.nanoTime();
            //color(am, order);
            ArrayList<Integer> order2 = smallestOriginalDegreeLast(am);
//            colorsSODL[i] = color(am, check(am,order2));
//            if (colorsSODL[i] > colorsSODLmax){
//                colorsSODLmax = colorsSODL[i];
//            }
            endTime = System.nanoTime();
            executionTimesSODL[i] = endTime - startTime;
//
//
            startTime = System.nanoTime();
            //color(am, order);
            ArrayList<Integer> order1 = smallestLastVertexOrdering(am);
//            colorsSLO[i] = color(am, check(am,order1));
//            if (colorsSLO[i] > colorsSLOmax){
//                colorsSLOmax = colorsSLO[i];
//            }
            endTime = System.nanoTime();
            executionTimesSLO[i] = endTime - startTime;



        }
        List<int[]> datastes = new ArrayList<>();
        datastes.add(colorslexBFS);
        datastes.add(colorsDFS);
        datastes.add(colorsBFS);
        datastes.add(colorsUNIF);
        datastes.add(colorsSODL);
        datastes.add(colorsSLO);
        List<String> labels = new ArrayList<>();
        labels.add("LesBFS");
        labels.add("DFS");
        labels.add("BFS");
        labels.add("UNIF");
        labels.add("SODL");
        labels.add("SLO");
//        System.out.println(Arrays.toString(executionTimes));
//        plotResults(vertexCounts, executionTimes);



        // Compare color

        System.out.println("colorsSLOmax: " + colorsSLOmax);
        System.out.println("colorsSODLmax: " + colorsSODLmax);
        System.out.println("colorsUNIFmax: " + colorsUNIFmax);
        System.out.println("colorsBFSmax: " + colorsBFSmax);
        System.out.println("colorsDFSmax: " + colorsDFSmax);
        System.out.println("colorslexBFSmax: " + colorslexBFSmax);

        //plotResults2("Color compare complete graph", vertexCounts, datastes, labels);
        //plotResults2("color compare circle graph", vertexCounts, datastes, labels);
        //plotResults2("Color compare uniform graph", vertexCounts, datastes,labels);
        //plotResults2("color compare skewed graph ", vertexCounts, datastes, labels);
        //plotResults2("color compare guass graph", vertexCounts, datastes, labels);



        // Time
        List<long[]> executionTimesList = new ArrayList<>();
        executionTimesList.add(executionTimesSLO);
        executionTimesList.add(executionTimesSODL);
        executionTimesList.add(executionTimeUNIF);
        executionTimesList.add(executionTimeBFS);
        executionTimesList.add(executionTimeDFS);
        executionTimesList.add(executionTimelexBFS);
        List<String> labels2 = new ArrayList<>();
        labels2.add("SLO");
        labels2.add("SODL");
        labels2.add("UNIF");
        labels2.add("BFS");
        labels2.add("DFS");
        labels2.add("lexBFS");

        //plotResults3("Complete graph with different order Time", vertexCounts,executionTimesList,labels2);
        //plotResults3("Circle graph with different order Time", vertexCounts, executionTimesList,labels2);
        //plotResults3("UNIForm graph with different order Time", vertexCounts, executionTimesList,labels2);
        //plotResults3("Skewed uniform graph with different order Time", vertexCounts, executionTimesList,labels2);
        //plotResults3("guass uniform graph with different order Time", vertexCounts, executionTimesList,labels2);
        plotResults3("Running time", vertexCounts,executionTimesList,labels2);

    }
}
