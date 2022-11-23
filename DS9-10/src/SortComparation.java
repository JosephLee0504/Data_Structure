import java.util.Scanner;

public class SortComparation {
    private static <T extends Comparable<T>> void swapReferences(T[] a, int i, int j) // 交换函数
    {
        if (a == null || a.length <= 1 || i == j) {
        }
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static <T extends Comparable<T>> void bubbleSort(T[] a) { // 冒泡排序
        T temp;
        for (int p = a.length - 1; p >= 0; p--) {
            int flag = 0; // 标识，如果发生交换，则标识为1，结束时若仍为0，则表示排序已完成，直接退出循环
            for (int i = 0; i < p; i++) {
                if (a[i].compareTo(a[i + 1]) > 0) {
                    swapReferences(a, i, i + 1);
                    flag = 1;
                }
            }
            if (flag == 0) // 全程无交换
                break;
        }
    }

    public static <T extends Comparable<T>> void insertSort(T[] a) { // 插入排序
        int j;
        for (int p = 1; p < a.length; p++) {
            T temp = a[p];
            for (j = p; j > 0 && a[j - 1].compareTo(temp) > 0; j--)
                a[j] = a[j - 1];
            a[j] = temp;
        }
    }

    public static <T extends Comparable<T>> void shellSort(T[] a) { // 希尔排序
        int j;
        for (int gap = a.length / 2; gap > 0; gap /= 2) {
            for (int p = gap; p < a.length; p++) {
                T temp = a[p];
                for (j = p; j >= gap && a[j - gap].compareTo(temp) > 0; j -= gap)
                    a[j] = a[j - gap];
                a[j] = temp;
            }
        }
    }

    public static <T extends Comparable<T>> void selectSort(T[] a) { // 选择排序
        int j;
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (j = i + 1; j < a.length; j++) {
                if (a[j].compareTo(a[min]) < 0)
                    min = j;
            }
            if (min != i)
                swapReferences(a, i, min);
        }
    }

    /*
    ---堆排序实现，其中需包含堆的下滤方法
     */
    private static <T extends Comparable<T>> void percDown(T[] a, int hole, int n) { // 下滤获取大根堆：
        // hole--下滤位置, n--heap大小
        int child;
        T temp = a[hole];
        for (; 2 * hole + 1 < n; hole = child) {
            child = 2 * hole + 1;
            // child指向左右子结点的较大者
            if (child != n - 1 && a[child].compareTo(a[child + 1]) < 0)
                child++;
            // 如果比儿子大，则把hole放入儿子中，同时在儿子位置继续(下滤)
            if (temp.compareTo(a[child]) < 0)
                a[hole] = a[child];
            else // 找到了合适的位置
                break;
        }
        a[hole] = temp;
    }

    public static <T extends Comparable<T>> void heapSort(T[] a) { // 堆排序
        T temp;
        for (int i = a.length / 2 - 1; i >= 0; i--) // 建立大根堆
            percDown(a, i, a.length);
        for (int i = a.length - 1; i > 0; i--) {
            swapReferences(a, 0, i);
            percDown(a, 0, i);
        }
    }

    /*
    ---归并排序实现
     */
    private static <T extends Comparable<T>> void merge(T[] a, T[] tempArr, int leftPos, int rightPos, int rightEnd) {
        /* leftPos--左边起始位置, rightPos--右边起始位置, rightEnd--右边终点位置 */
        int leftEnd = rightPos - 1;
        int tempPos = leftPos; // 存放结果数组的初始位置
        int numElements = rightEnd - leftPos + 1;
        while (leftPos <= leftEnd && rightPos <= rightEnd) {
            if (a[leftPos].compareTo(a[rightPos]) <= 0)
                tempArr[tempPos++] = a[leftPos++];
            else
                tempArr[tempPos++] = a[rightPos++];
        }
        while (leftPos <= leftEnd) // 左边有剩余，则直接复制左边剩余部分
            tempArr[tempPos++] = a[leftPos++];
        while (rightPos <= rightEnd)
            tempArr[tempPos++] = a[rightPos++];
        for (int i = 0; i < numElements; i++, rightEnd--)
            a[rightEnd] = tempArr[rightEnd];
    }

    private static <T extends Comparable<T>> void mergeSort(T[] a, T[] tempArr, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(a, tempArr, left, center);
            mergeSort(a, tempArr, center + 1, right);
            merge(a, tempArr, left, center + 1, right);
        }
    }

    public static <T extends Comparable<T>> void mergeSort(T[] a) {
        T[] tempArr = (T[]) new Comparable[a.length];
        mergeSort(a, tempArr, 0, a.length - 1);
    }

    /*
    ---快速排序实现
     */
    private static <T extends Comparable<T>> T median3(T[] a, int left, int right) { // 三数中值分割获取枢纽元
        int center = (left + right) / 2;
        if (a[center].compareTo(a[left]) < 0)
            swapReferences(a, left, center);
        if (a[right].compareTo(a[left]) < 0)
            swapReferences(a, left, right);
        if (a[right].compareTo(a[center]) < 0)
            swapReferences(a, center, right);
        // 将枢纽元放到最后, 之后只需要考虑a[left+1]...a[right-2]
        swapReferences(a, center, right - 1);
        return a[right - 1];
    }

    private static <T extends Comparable<T>> void quickSort(T[] a, int left, int right) { // 快速排序
        int cutOff = 10; // 截止范围, 小数组快排效率不如插入排序
        if (left + cutOff <= right) { // 序列元素过多，用快排
            T pivot = median3(a, left, right);
            int i = left, j = right - 1;
            for (; ; ) { // 将序列中比枢纽元小的移到枢纽元左边，大的移到右边
                while (a[++i].compareTo(pivot) < 0) {
                }
                while (a[--j].compareTo(pivot) > 0) {
                }
                if (i < j)
                    swapReferences(a, i, j);
                else
                    break;
            }
            // 将枢纽元移到正确位置
            swapReferences(a, i, right - 1);
            quickSort(a, left, i - 1); // 递归解决左边
            quickSort(a, i + 1, right); // 递归解决右边
        }
        else
            insertSort(a);
    }

    public static <T extends Comparable<T>> void quickSort(T[] a) {
        quickSort(a, 0, a.length - 1);
    }

    public static void bucketSort(Integer[] a, int max) { // 桶排序: max--数组中a的最大值
        int[] buckets = new int[max];
        if (a == null || max < 1)
            return ;
        for (int i = 0; i < a.length; i++) // 计数
            buckets[a[i]]++;
        for (int i = 0, j = 0; i < max; i++) { // 排序
            while ((buckets[i]--) > 0)
                a[j++] = i;
        }
    }

    /*
    ---基数排序实现
     */
    private static void countSort(Integer[] a, int exp) {
        /*
        对数组按“某个位数”进行排序(桶排序),exp--指数, exp=1即按个位对数组进行排序
         */
        int[] output = new int[a.length]; // 存储被排序数据的临时数组
        int[] buckets = new int[10];
        for (int i = 0; i < a.length; i++) // 将数据出现的次数存储在buckets中
            buckets[(a[i] / exp) % 10]++;
        for (int i = 1; i < 10; i++) // 将各个桶中的数字个数，转化成各个桶中最后一个数字的下标索引
            buckets[i] += buckets[i - 1];
        for (int i = a.length - 1; i >= 0; i--) { // 将数组存储到临时数组output中
            output[buckets[(a[i] / exp) % 10] - 1] = a[i];
            buckets[(a[i] / exp) % 10]--;
        }
        for (int i = 0; i < a.length; i++) // 将排序好的数据赋值给a[]
            a[i] = output[i];
        output = null;
        buckets = null;
    }

    public static void radixSort(Integer[] a) { // 基数排序
        int exp; // 指数, exp=1--按个位进行排序
        int max = a[0];
        for (int i = 1; i < a.length; i++)
            if (a[i] > max)
                max = a[i];
        for (exp = 1; max / exp > 0; exp *= 10)
            countSort(a, exp);
    }

    public static void main(String[] args) {
        System.out.println("请输入随机数序列的数量:");
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        Integer[] temp = new Integer[N];
        Integer[] a1 = new Integer[N];
        Integer[] a2 = new Integer[N];
        Integer[] a3 = new Integer[N];
        Integer[] a4 = new Integer[N];
        Integer[] a5 = new Integer[N];
        Integer[] a6 = new Integer[N];
        Integer[] a7 = new Integer[N];
        Integer[] a8 = new Integer[N];
        Integer[] a9 = new Integer[N];
        for (int i = 0; i < temp.length; i++) {
            int t = (int) (Math.random() * N + 1);
            temp[i] = t;
        }
        for (int i = 0; i < a1.length; i++) {
            a1[i] = temp[i];
        }
        for (int i = 0; i < a2.length; i++) {
            a2[i] = temp[i];
        }
        for (int i = 0; i < a3.length; i++) {
            a3[i] = temp[i];
        }
        for (int i = 0; i < a4.length; i++) {
            a4[i] = temp[i];
        }
        for (int i = 0; i < a5.length; i++) {
            a5[i] = temp[i];
        }
        for (int i = 0; i < a6.length; i++) {
            a6[i] = temp[i];
        }
        for (int i = 0; i < a7.length; i++) {
            a7[i] = temp[i];
        }
        for (int i = 0; i < a8.length; i++) {
            a8[i] = temp[i];
        }
        for (int i = 0; i < a9.length; i++) {
            a9[i] = temp[i];
        }
        long start1 = System.currentTimeMillis();
        selectSort(a1);
        long end1 = System.currentTimeMillis();
        System.out.println("选择排序时间:" + (end1 - start1) + "ms");

        long start2 = System.currentTimeMillis();
        bubbleSort(a2);
        long end2 = System.currentTimeMillis();
        System.out.println("冒泡排序时间:" + (end2 - start2) + "ms");

        long start3 = System.currentTimeMillis();
        insertSort(a3);
        long end3 = System.currentTimeMillis();
        System.out.println("插入排序时间:" + (end3 - start3) + "ms");

        long start4 = System.currentTimeMillis();
        shellSort(a4);
        long end4 = System.currentTimeMillis();
        System.out.println("希尔排序时间:" + (end4 - start4) + "ms");

        long start5 = System.currentTimeMillis();
        heapSort(a5);
        long end5 = System.currentTimeMillis();
        System.out.println("堆排序时间:" + (end5 - start5) + "ms");

        long start6 = System.currentTimeMillis();
        mergeSort(a6);
        long end6 = System.currentTimeMillis();
        System.out.println("归并排序时间:" + (end6 - start6) + "ms");

        long start7 = System.currentTimeMillis();
        quickSort(a7);
        long end7 = System.currentTimeMillis();
        System.out.println("快速排序时间:" + (end7 - start7) + "ms");

        long start8 = System.currentTimeMillis();
        bucketSort(a8, N + 1);
        long end8 = System.currentTimeMillis();
        System.out.println("桶排序时间:" + (end8 - start8) + "ms");

        long start9 = System.currentTimeMillis();
        radixSort(a9);
        long end9 = System.currentTimeMillis();
        System.out.println("基数排序时间:" + (end9 - start9) + "ms");
    }
}