
import java.util.ArrayList;
import java.io.File;
import java.io.PrintStream;
public class mergesort {
    private static int num = 0;
    public static void mergeSort(int[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);
        merge(a, l, r, mid, n - mid);
    }

    public static void merge(int[] a, int[] l, int[] r, int left, int right) {
        System.out.println("---------------------------------recursive loop " + num++ + " starts here -----------------------------");
        System.out.println("Currently Merging : " + "Left List : " + toString(l) + '\t' + " Right List : " + toString(r));
        System.out.println("Merging Process: ");
        int i = 0, j = 0, k = 0;
        ArrayList<Integer> result = new ArrayList<Integer>() ;
        while (i < left && j < right) {
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
                result.add(l[i-1]);
                System.out.println( i + "th element from list 1 has been sorted , current result list: " + result.toString());
            } else {
                a[k++] = r[j++];
                result.add(r[j-1]);
                System.out.println( j + "th element from list 2 has been sorted , current result list: " + result.toString());
            }
        }
        while (i < left) {
            a[k++] = l[i++];
            result.add(l[i-1]);
            System.out.println( i + "th element from list 1 has been sorted , current result list: " + result.toString());

        }
        while (j < right) {
            a[k++] = r[j++];
            result.add(r[j-1]);
            System.out.println( j + "th element from list 2 has been sorted , current result list: " + result.toString());

        }
        System.out.println("Merging Result : " + toString(a));
//        System.out.println("---------------------------------Current recursive loop ends here -----------------------------");
    }
    public static String toString(int[] arr)
    {
        String result = "[ ";
        for(int i =0; i< arr.length-1 ; i++)
        {
            result += arr[i] + ", ";
        }
        result += arr[arr.length-1];
        result += " ]";
        return result;
    }

    public static void main(String[] args) throws Exception{
        int[] actual = {38, 27, 43, 3, 9, 82,10};
        PrintStream o = new PrintStream(new File("mergesort Tracing.txt"));
        System.setOut(o);
        mergeSort(actual, actual.length);
        System.out.println(toString(actual));
        o.close();
    }

}