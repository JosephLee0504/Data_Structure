public class BinaryHeap<T extends Comparable<T>> {
    private static final int DEFAULT_CAPACITY = 1000;
    private int currentSize; // 堆中元素所对应的数字(A-J: 1-10)
    private T[] array; // 堆数组

    public BinaryHeap(int capacity) {
        currentSize = 0;
        array = (T[]) new Comparable[currentSize + 1];
    }

    public BinaryHeap() {
        this(DEFAULT_CAPACITY);
    }

    public BinaryHeap(T[] items) {
        currentSize = items.length;
        array = (T[]) new Comparable[(currentSize + 2) * 11 / 10];
        int i = 1;
        for (T item: items)
            array[i++] = item;
        buildHeap();
    }

    private void buildHeap() {
        for (int i = currentSize / 2; i > 0; i--)
            percolateDown(i);
    }

    private void percolateDown(int hole) { // 下滤方法
        int child;
        T tmp = array[hole];
        for ( ; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            // child指向左右子结点的较小者
            if (child != currentSize && array[child + 1].compareTo(array[child]) < 0)
                child++;
            //如果比儿子小，则把hole放入儿子中，同时在儿子位置继续
            if (array[child].compareTo(tmp) < 0)
                array[hole] = array[child];
            else
                break;
        }
        array[hole] = tmp;
    }

    private void enlargeArray(int newSize) {
        T[] old = array;
        array = (T[]) new Comparable[newSize];
        for (int i = 0; i < old.length; i++)
            array[i] = old[i];
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public void makeEmpty() {
        currentSize = 0;
    }

    public void insert(T x) {
        if (currentSize == array.length - 1)
            enlargeArray(array.length * 2 + 1);
        int hole = ++currentSize; // hole指向插入后堆中的最后一个元素的位置
        // array[0]=x作为哨兵，这样当hole=1时，x.compareTo(array[hole / 2]) < 0这个条件不满足，可以跳出循环
        for (array[0] = x; x.compareTo(array[hole / 2]) < 0; hole /= 2)
            array[hole] = array[hole / 2]; // 上滤x
        array[hole] = x; // 将x插入
    }

    public T findMin() throws Exception {
        if (isEmpty())
            throw new Exception();
        return array[1];
    }

    public T deleteMin() throws Exception {
        if (isEmpty())
            throw new Exception();
        T minItem = findMin();
        array[1] = array[currentSize--]; // 当前堆得规模要减小
        percolateDown(1);
        return minItem;
    }

    public static void main(String[] args) throws Exception {
        int numItems = 100;
        BinaryHeap<Integer> h = new BinaryHeap<>();
        int i;
        for (i = 100; i > 0; i--)
            h.insert(i);
        System.out.println(h.findMin());
    }
}
