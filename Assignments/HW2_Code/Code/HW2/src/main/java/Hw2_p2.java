import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Arrays;
import java.util.Random;

public class Hw2_p2 {
    public static class ListNode{
        public int value;
        public ListNode next;
        public ListNode(int value){
            this.value = value;
            next = null;
        }
    }

    public static void main(String[] args){
        int n1 = 10000;
        int n2 = 20000;
        int n3 = 30000;
        int n4 = 40000;
        int n5 = 50000;
        int n6 = 60000;
        int n7 = 70000;
        int n8 = 80000;
        int n9 = 90000;
        int n10 = 100000;
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
        dataset.addValue(result[0],"time", "10000");
        dataset.addValue(result[1],"time", "20000");
        dataset.addValue(result[2],"time", "30000");
        dataset.addValue(result[3],"time", "40000");
        dataset.addValue(result[4],"time", "50000");
        dataset.addValue(result[5],"time", "60000");
        dataset.addValue(result[6],"time", "70000");
        dataset.addValue(result[7],"time", "80000");
        dataset.addValue(result[8],"time", "90000");
        dataset.addValue(result[9],"time", "100000");

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

    public static int[] Random_array(int n){
        Random rand = new Random(); // c1, 1 time
        int[] rand_number = new int[n]; // c2, 1 time
        for (int i = 0; i < n; i++){ // c3, (n + 1) times
            rand_number[i] = rand.nextInt(n+1);
            // rand.nextInt is constant, c4, and called n times
        }
        return rand_number; // c4, 1 times
        // A function representing time, f(n)= c1 * 1 + c2 * 1 + c3*(n+1) + c4*n;
        // f(n) = $\Theta(n)$
    }

    public static int[] sort(int[] array){
        for (int j = 1; j < array.length; j++){ // c1, n time
            int key = array[j]; // c2, n-1 times
            int i = j-1; // c3, n-1 times
            while(i >= 0 && array[i] > key){ // c4, \sum_{j=1}^{n}tj
                array[i+1] = array[i]; // c5, \sum_{j=1}^{n}(tj-1)
                i = i - 1; // c6, \sum_{j=1}^{n}(tj-1)
            }
            array[i+1] = key; // c7, n-1
        }
        return array; // c8, 1 time
        // A function representing time, f(n)= c1 * n + c2 * (n-1) + c3*(n-1) + c4*\sum_{j=1}^{n}tj
        // + c5*\sum_{j=1}^{n}(tj-1) + c6*\sum_{j=1}^{n}(tj-1) + c7 * (n-1);
        // f(n) = $\Theta(n^2)$
    }

    public static ListNode linkedlist(int[] array){
        ListNode root = new ListNode(array[0]); // c1, 1 time;
        ListNode ptr; // c2, 1 time
        ptr = root; // c3, 1 time
        int len = array.length; // c4, 1 time
        for (int i = 1; i < len; i++){ // c5, n times
            ptr.next = new ListNode(array[i]); // c6, n-1 times
            ptr = ptr.next; // c7, n-1 times
        }
        return root;// c8, n-1
        // A function representing time, f(n) = c1*1+ c2*2 + c3*2 +c4*3+ c5*n + c6*(n-1)+ c7*(n-1)+c8*(n-1)
        // f(n) = $\Theta(n)$
    }
    // f(n)= $\Theta(n) + \Theta(n^2)+\Theta(n)$ =\Theta(n^2)

    public static void display(ListNode root){
        while(root != null){
            System.out.print(root.value + " ");
            root = root.next;
        }
    }

    public static long time_calculate(int n){
        long startTime = System.nanoTime();
        int[] array = Random_array(n);
        int[] sortarray = sort(array);
        ListNode listNode = linkedlist(sortarray);
        long endTime = System.nanoTime();
        long time = endTime - startTime;
        return time;
    }
}
