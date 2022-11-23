import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class SearchTest {
    public static int BinarySearch(int[] arr,int key){
        int low = 0;
        int high = arr.length - 1;
        int middle = 0;			//定义middle
        if(key < arr[low] || key > arr[high]){
            return -1;
        }
        while(low <= high){
            middle = (low + high) / 2;
            if(arr[middle] > key){
                //比关键字大则关键字在左区域
                high = middle - 1;
            }
            else if(arr[middle] < key){
                //比关键字小则关键字在右区域
                low = middle + 1;
            }
            else{
                return middle;
            }
        }
        return -1;		//最后仍然没有找到，则返回-1
    }

    public static void main(String[] args) {
        System.out.println("请输入随机数数量:");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int t = 0;
        LinkedList<Integer> list = new LinkedList<>(); // 链表
        int[] a = new int[n]; // 二分查找有序数组
        Arrays.sort(a);
        AvlTree<Integer> avl = new AvlTree<>(); // Avl树
        ProbingHashTable<Integer> hash = new ProbingHashTable<>(n); // 散列表: 平方探测
        for (int i = 0; i < n; i++) {
            int m = new Random().nextInt(n);
            if (i == n - 1)
                t = m;
            list.add(m);
            a[i] = m;
            avl.insert(m);
            hash.insert(m);
        }

        long start1=System.nanoTime();
        list.contains(t);
        long end1=System.nanoTime();
        System.out.println("Sequential list time:"+(end1-start1)+"ns");

        long start2=System.nanoTime();
        BinarySearch(a,t);
        long end2=System.nanoTime();
        System.out.println("Binary search time:"+(end2-start2)+"ns");

        long start3=System.nanoTime();
        avl.contains(t);
        long end3=System.nanoTime();
        System.out.println("Avl tree time:"+(end3-start3)+"ns");

        long start4=System.nanoTime();
        hash.contains(t);
        long end4=System.nanoTime();
        System.out.println("Hash table time:"+(end4-start4)+"ns");
    }
}
