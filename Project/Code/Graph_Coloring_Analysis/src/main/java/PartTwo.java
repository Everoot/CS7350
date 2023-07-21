import java.util.*;

public class PartTwo {
    public static class Graph{
        static void addEdge(ArrayList<ArrayList<Integer>> am, int s, int d){
            am.get(s).add(d);
            am.get(d).add(s);
        }
    }

    public static void printAdjacencyList(ArrayList<ArrayList<Integer>> am){
        for (int i = 0; i < am.size(); i++){
            System.out.print("Vertex " + i + " is connected to: ");
            for (int j = 0; j < am.get(i).size(); j++){
                System.out.print(am.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    public static int[] color(ArrayList<ArrayList<Integer>> am, ArrayList<Integer> Order){
        int[] colorassign = new int[am.size()];
        for(int i = 0; i < am.size(); i++){
            colorassign[i] = -1;
        }

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

            // Reset the values back to true for the next iteration
            for (int j = 0; j < am.size(); j++) {
                available[j] = true;
            }

        }

        // print the result
        System.out.println("The color result:");
        for (int i = 0; i < am.size(); i++)
            System.out.println("Vertex " + Order.get(i) + " --->  Color "
                    + colorassign[i]);


        return colorassign;
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

            System.out.println("The degree of Vertex " + i + ": " + degree);
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
            printAdjacencyList(am);
            int minDegreeVertex = findSmallestDegreeVertex(am);

            if (minDegreeVertex == -1) {
                slvOrder.add(0, res);
                break;
            }

            System.out.println("The min degree Vertex is (will be deleted): " + minDegreeVertex);
            System.out.println();

            if (am.get(minDegreeVertex).size() > maxdegree){
                maxdegree = am.get(minDegreeVertex).size();
            }

            if (terminalclique < am.get(minDegreeVertex).size() &&
                    ((am.size()-slvOrder.size()-1) == am.get(minDegreeVertex).size())){
                terminalclique = am.get(minDegreeVertex).size();
            }


            slvOrder.add(0, minDegreeVertex);
            System.out.println(slvOrder);
            if (am.get(minDegreeVertex).size() == 1){
                res = am.get(minDegreeVertex).get(0);
            }
            removeVertex(minDegreeVertex,am);

        }
        System.out.println("The order to delete (from right to left): " + slvOrder);
        return slvOrder;
    }

    // -----------------------------------------------------------------------------------------------
    // Method 2: Smallest Original Degree Last (SODL) Ordering:
    public static int findMaxDegreeVertex(ArrayList<ArrayList<Integer>> am, ArrayList<Integer> exit){
        int maxDegree = Integer.MIN_VALUE;
        int maxDegreeVertex = -1;

        for (int i = 0; i < am.size(); i++){
            int degree = am.get(i).size();

            System.out.println("The degree of Vertex " + i + ": " + degree);
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

            System.out.println("The vertex add to solOrder "+ minDegreeVertex + "\n");

            solOrder.add(0, minDegreeVertex);

        }

        System.out.println("Smallest orginal degree Last ORder (from right to left): " + "\n" + solOrder);
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


        System.out.println("Uniform random Order is : " + unOrder +"\n");


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
        System.out.println("BFSOrder is : " + bfsOrder +"\n");

        return bfsOrder;
    }

    // -----------------------------------------------------------------------------------------------
    // Method 5: Depth-First Search (DFS) Ordering:
    public static ArrayList<Integer> DFSOrder(ArrayList<ArrayList<Integer>> am, int startNode){
        ArrayList<Integer> dfsOrder = new ArrayList<>();
        boolean[] visited = new boolean[am.size()];

        dfsRecursive(am, startNode, visited, dfsOrder);
        System.out.println("DFSOrder is : " + dfsOrder +"\n");
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
        for (int i = 0; i < n; i++){
            Set<Integer> neighbors = new HashSet<>();
            for (int neighbor:am.get(i)){
                neighbors.add(neighbor);
            }
            nodeToNeighbors.put(i, neighbors);
        }

        // Perform lexicographic BFS starting from node 0
        Queue<Integer> bfsQueue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        bfsQueue.add(startnode);
        visited.add(startnode);

        while(!bfsQueue.isEmpty()){
            int node = bfsQueue.poll();
            lexOrder.add(node);

            ArrayList<Integer> unvisitedNeighbors = new ArrayList<>();
            for (int neihbor: nodeToNeighbors.get(node)){
                if (!visited.contains(neihbor)){
                    unvisitedNeighbors.add(neihbor);
                }
            }
            Collections.sort(unvisitedNeighbors);

            for (int neighbor: unvisitedNeighbors){
                bfsQueue.add(neighbor);
                visited.add(neighbor);
            }
        }

        System.out.println("LexOrder is : " + lexOrder +"\n");


        return lexOrder;
    }

    public static long time_calculate(){
        long startTime = System.nanoTime();


        long endTime = System.nanoTime();
        long Time = endTime - startTime;
        return Time;
    }



    public static void main(String[] args){
        //---------------------- Example 1 init-----------------------------------
        int v = 5;
        ArrayList<ArrayList<Integer>> adjacencyList1 = new ArrayList<ArrayList<Integer>>(v);
        for (int i = 0; i < v; i++) {
            adjacencyList1.add((new ArrayList<Integer>()));
        }

        // Complete.allEdge(adjacencyList, v);
        PartTwo.Graph.addEdge(adjacencyList1,0,1);
        PartTwo.Graph.addEdge(adjacencyList1, 0,2);
        PartTwo.Graph.addEdge(adjacencyList1,0,3);
        PartTwo.Graph.addEdge(adjacencyList1,1,4);
        PartTwo.Graph.addEdge(adjacencyList1,1,3);
        PartTwo.Graph.addEdge(adjacencyList1,1,2);
        PartTwo.Graph.addEdge(adjacencyList1,2,3);
        PartTwo.Graph.addEdge(adjacencyList1,2,4);

        //--------------------- Example 2 init -----------------------------------
        int v2 = 5;
        ArrayList<ArrayList<Integer>> adjacencyList2 = new ArrayList<ArrayList<Integer>>(v2);
        for (int i = 0; i < v2; i++) {
            adjacencyList2.add((new ArrayList<Integer>()));
        }

        PartTwo.Graph.addEdge(adjacencyList2,0,1);
        PartTwo.Graph.addEdge(adjacencyList2, 0,2);
        PartTwo.Graph.addEdge(adjacencyList2,1,4);
        PartTwo.Graph.addEdge(adjacencyList2,1,3);
        PartTwo.Graph.addEdge(adjacencyList2,2,3);
        PartTwo.Graph.addEdge(adjacencyList2,3,4);


        //------------------- Example 3 init -----------------------------------
        int v3 = 10;
        ArrayList<ArrayList<Integer>> adjacencyList3 = new ArrayList<ArrayList<Integer>>(v3);
        for (int i = 0; i < v3; i++) {
            adjacencyList3.add((new ArrayList<Integer>()));
        }

        PartTwo.Graph.addEdge(adjacencyList3,0,1);
        PartTwo.Graph.addEdge(adjacencyList3, 0,6);
        PartTwo.Graph.addEdge(adjacencyList3,0,7);
        PartTwo.Graph.addEdge(adjacencyList3, 0, 8);
        PartTwo.Graph.addEdge(adjacencyList3,1,6);
        PartTwo.Graph.addEdge(adjacencyList3,1,2);
        PartTwo.Graph.addEdge(adjacencyList3,1,9);
        PartTwo.Graph.addEdge(adjacencyList3,1,5);
        PartTwo.Graph.addEdge(adjacencyList3,2,9);
        PartTwo.Graph.addEdge(adjacencyList3,2,5);
        PartTwo.Graph.addEdge(adjacencyList3,2,3);
        PartTwo.Graph.addEdge(adjacencyList3, 2, 4);
        PartTwo.Graph.addEdge(adjacencyList3,2,6);
        PartTwo.Graph.addEdge(adjacencyList3, 3,4);
        PartTwo.Graph.addEdge(adjacencyList3,3,9);
        PartTwo.Graph.addEdge(adjacencyList3,4,5);
        PartTwo.Graph.addEdge(adjacencyList3,5,6);
        PartTwo.Graph.addEdge(adjacencyList3, 6, 7);

        long startTime, endTime, Time;

        // Method 1: (SLV)---------------------- test -------------------
        System.out.println("---------------------Example 1---------------------------------");

        ArrayList<ArrayList<Integer>> adjacencyList1copy = copy(adjacencyList1);

        System.out.println("The original adjacencyList:");
        printAdjacencyList(adjacencyList1copy);
        System.out.println();
        color(adjacencyList1,smallestLastVertexOrdering(adjacencyList1copy));
        System.out.println("The size of terminal clique degree: " + terminalclique);
        System.out.println("The max degree during removing order: " + maxdegree);
        System.out.println();


        System.out.println("---------------------Example 2---------------------------------");
        ArrayList<ArrayList<Integer>> adjacencyList2copy = copy(adjacencyList2);

        System.out.println("The original adjacencyList:");
        printAdjacencyList(adjacencyList2copy);
        System.out.println();
        color(adjacencyList2,smallestLastVertexOrdering(adjacencyList2copy));
        System.out.println("The size of terminal clique degree: " + terminalclique);
        System.out.println("The max degree during removing order: " + maxdegree);
        System.out.println();

        System.out.println("---------------------Example 3---------------------------------");

        ArrayList<ArrayList<Integer>> adjacencyList3copy = copy(adjacencyList3);

        System.out.println("The original adjacencyList:");
        printAdjacencyList(adjacencyList3copy);
        System.out.println();
        color(adjacencyList3,smallestLastVertexOrdering(adjacencyList3copy));
        System.out.println("The size of terminal clique degree: " + terminalclique);
        System.out.println("The max degree during removing order: " + maxdegree);


//        // Method 2: (SODL) ------------------ test -------------------
//        System.out.println("---------------------Example 1---------------------------------");
//        System.out.println("The original adjacencyList:");
//        printAdjacencyList(adjacencyList1);
//        System.out.println();
//
//        System.out.println("The Method for graph order result(from right to left):");
//        startTime = System.nanoTime();
//        color(adjacencyList1,smallestOriginalDegreeLast(adjacencyList1));
//        endTime = System.nanoTime();
//        System.out.println();
//
//        Time = endTime -startTime;
//        System.out.println("The running time: " + Time);
//        System.out.println();
//
//        System.out.println("----------------------Example 2--------------------------------");
//        System.out.println("The orignial graph:");
//
//        printAdjacencyList(adjacencyList2);
//        System.out.println();
//        System.out.println("The Method for graph order result(from right to left):");
//        startTime = System.nanoTime();
//        color(adjacencyList2,smallestOriginalDegreeLast(adjacencyList2));
//        endTime = System.nanoTime();
//        System.out.println();
//
//        Time = endTime -startTime;
//        System.out.println("The running time: " + Time);
//        System.out.println();
//
//
//        System.out.println("----------------------Example 3--------------------------------");
//        System.out.println("The original adjacencyList:");
//        printAdjacencyList(adjacencyList3);
//        System.out.println();
//        System.out.println("The Method for graph order result(from right to left):");
//        startTime = System.nanoTime();
//        color(adjacencyList3,smallestOriginalDegreeLast(adjacencyList3));
//        endTime = System.nanoTime();
//        Time = endTime -startTime;
//        System.out.println("The running time: " + Time);
//        System.out.println();


        // Method 3: (URO) ------------------ test -------------------
//        System.out.println("---------------------Example 1---------------------------------");
//        System.out.println("The original adjacencyList:");
//        printAdjacencyList(adjacencyList1);
//        System.out.println();
//
//        System.out.println("The Method for graph order result(from right to left):");
//        long startTime1 = System.nanoTime();
//        color(adjacencyList1,uniformrandom(adjacencyList1));
//        long endTime1 = System.nanoTime();
//        System.out.println();
//
//        long Time1 = endTime1 -startTime1;
//        System.out.println("The running time: " + Time1);
//        System.out.println();
//
//        System.out.println("----------------------Example 2--------------------------------");
//        System.out.println("The orignial graph:");
//
//        printAdjacencyList(adjacencyList2);
//        System.out.println();
//        System.out.println("The Method for graph order result(from right to left):");
//        long startTime2 = System.nanoTime();
//        color(adjacencyList2,uniformrandom(adjacencyList2));
//        long endTime2 = System.nanoTime();
//        System.out.println();
//
//        long Time2 = endTime2 -startTime2;
//        System.out.println("The running time: " + Time2);
//        System.out.println();
//
//
//        System.out.println("----------------------Example 3--------------------------------");
//        System.out.println("The original adjacencyList:");
//        printAdjacencyList(adjacencyList3);
//        System.out.println();
//        System.out.println("The Method for graph order result(from right to left):");
//        long startTime3 = System.nanoTime();
//        color(adjacencyList3,uniformrandom(adjacencyList3));
//        long endTime3 = System.nanoTime();
//        long Time3 = endTime3 -startTime3;
//        System.out.println("The running time: " + Time3);
//        System.out.println();

        // Method 4: (BFS) ------------------ test -------------------
//        System.out.println("---------------------Example 1---------------------------------");
//        System.out.println("The original adjacencyList:");
//        printAdjacencyList(adjacencyList1);
//        System.out.println();
//
//        System.out.println("The Method for graph order result(from right to left):");
//        long startTime1 = System.nanoTime();
//        color(adjacencyList1,BFSOrder(adjacencyList1,2));
//        long endTime1 = System.nanoTime();
//        System.out.println();
//
//        long Time1 = endTime1 -startTime1;
//        System.out.println("The running time: " + Time1);
//        System.out.println();
//
//        System.out.println("----------------------Example 2--------------------------------");
//        System.out.println("The orignial graph:");
//
//        printAdjacencyList(adjacencyList2);
//        System.out.println();
//        System.out.println("The Method for graph order result(from right to left):");
//        long startTime2 = System.nanoTime();
//        color(adjacencyList2,BFSOrder(adjacencyList2,2));
//        long endTime2 = System.nanoTime();
//        System.out.println();
//
//        long Time2 = endTime2 -startTime2;
//        System.out.println("The running time: " + Time2);
//        System.out.println();
//
//
//        System.out.println("----------------------Example 3--------------------------------");
//        System.out.println("The original adjacencyList:");
//        printAdjacencyList(adjacencyList3);
//        System.out.println();
//        System.out.println("The Method for graph order result(from right to left):");
//        long startTime3 = System.nanoTime();
//        color(adjacencyList3,BFSOrder(adjacencyList3,2));
//        long endTime3 = System.nanoTime();
//        long Time3 = endTime3 -startTime3;
//        System.out.println("The running time: " + Time3);
//        System.out.println();


        // Method 5: (DFS) ------------------ test -------------------
//        System.out.println("---------------------Example 1---------------------------------");
//        System.out.println("The original adjacencyList:");
//        printAdjacencyList(adjacencyList1);
//        System.out.println();
//
//        System.out.println("The Method for graph order result(from right to left):");
//        long startTime1 = System.nanoTime();
//        color(adjacencyList1,DFSOrder(adjacencyList1,2));
//        long endTime1 = System.nanoTime();
//        System.out.println();
//
//        long Time1 = endTime1 -startTime1;
//        System.out.println("The running time: " + Time1);
//        System.out.println();
//
//        System.out.println("----------------------Example 2--------------------------------");
//        System.out.println("The orignial graph:");
//
//        printAdjacencyList(adjacencyList2);
//        System.out.println();
//        System.out.println("The Method for graph order result(from right to left):");
//        long startTime2 = System.nanoTime();
//        color(adjacencyList2,DFSOrder(adjacencyList2,2));
//        long endTime2 = System.nanoTime();
//        System.out.println();
//
//        long Time2 = endTime2 -startTime2;
//        System.out.println("The running time: " + Time2);
//        System.out.println();
//
//
//        System.out.println("----------------------Example 3--------------------------------");
//        System.out.println("The original adjacencyList:");
//        printAdjacencyList(adjacencyList3);
//        System.out.println();
//        System.out.println("The Method for graph order result(from right to left):");
//        long startTime3 = System.nanoTime();
//        color(adjacencyList3,DFSOrder(adjacencyList3,2));
//        long endTime3 = System.nanoTime();
//        long Time3 = endTime3 -startTime3;
//        System.out.println("The running time: " + Time3);
//        System.out.println();


        // Method 6: lexBFS ----------------- test -------------------
//        System.out.println("---------------------Example 1---------------------------------");
//        System.out.println("The original adjacencyList:");
//        printAdjacencyList(adjacencyList1);
//        System.out.println();
//
//        System.out.println("The Method for graph order result(from right to left):");
//        long startTime1 = System.nanoTime();
//        color(adjacencyList1,lexBFS(adjacencyList1,2));
//        long endTime1 = System.nanoTime();
//        System.out.println();
//
//        long Time1 = endTime1 -startTime1;
//        System.out.println("The running time: " + Time1);
//
//        System.out.println("----------------------Example 2--------------------------------");
//        System.out.println("The orignial graph:");
//
//        printAdjacencyList(adjacencyList2);
//        System.out.println();
//        System.out.println("The Method for graph order result(from right to left):");
//        long startTime2 = System.nanoTime();
//        color(adjacencyList2,lexBFS(adjacencyList2,2));
//        long endTime2 = System.nanoTime();
//        System.out.println();
//
//        long Time2 = endTime2 -startTime2;
//        System.out.println("The running time: " + Time2);
//
//
//        System.out.println("----------------------Example 3--------------------------------");
//        System.out.println("The original adjacencyList:");
//        printAdjacencyList(adjacencyList3);
//        System.out.println();
//        System.out.println("The Method for graph order result(from right to left):");
//        long startTime3 = System.nanoTime();
//        color(adjacencyList3,lexBFS(adjacencyList3,2));
//        long endTime3 = System.nanoTime();
//        long Time3 = endTime3 -startTime3;
//        System.out.println("The running time: " + Time3);






    }
}
