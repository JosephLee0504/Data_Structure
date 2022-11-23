public class ProbingHashTable<T> {
    private static final int DEFAULT_TABLE_SIZE = 11;
    private HashEntry<T>[] array;
    private int currentSize; // 已经被占用的散列表空间大小

    // 定义一个类用来标记每个位置的情况
    private static class HashEntry<T> {
        public T element; // 当前位置的元素值
        public boolean isActive; // 当前位置是否存在该元素
        public HashEntry(T element) {
            this(element, true);
        }
        public HashEntry(T element, boolean i) {
            this.element = element;
            isActive = i;
        }
    }

    public ProbingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    public ProbingHashTable(int size) {
        allocateArray(size);
        makeEmpty();
    }

    public void makeEmpty() {
        currentSize = 0;
        for (int i = 0; i < array.length; i++)
            array[i] = null;
    }

    public boolean contains(T x) { //判断是否存在,必须先找到该元素位置,然后再通过位置判断是否懒惰删除了(标记为删除)
        int currentPos = findPos(x);
        return isActive(currentPos);
    }

    public void insert(T x) {
        int currentPos = findPos(x);
        if (isActive(currentPos)) // 如果currentPos被占，则直接退出函数
            return;
        array[currentPos] = new HashEntry<>(x, true);
        if (++currentSize > array.length / 2) // 装填因子大于0.5,则需执行再散列
            rehash();
    }

    public void remove(T x) {
        int currentPos = findPos(x);
        if (isActive(currentPos)) // 将currentPos标记为deleted,懒惰删除不影响散列表其他位置的查找
            array[currentPos].isActive = false;
    }

    private void allocateArray(int arraySize) {
        array = new HashEntry[nextPrime(arraySize)];
    }

    private boolean isActive(int currentPos) { // 如果currentPos存在关键字且没有被标记为deleted,则返回true,否则为false
        return array[currentPos] != null && array[currentPos].isActive;
    }

    /*
    private int findPos(T x) // 线性探测法: f(i) = i
    {
        int offset = 1;
        int currentPos = myhash(x);
        while (array[currentPos] != null && !array[currentPos].element.equals(x))
        {
            currentPos += offset;
            offset++;
            if (currentPos >= array.length)
                currentPos -= array.length;
        }
        return currentPos;
    }
    */

    private int findPos(T x) { // 平方探测法: f(i) = f(i-1) + 2i-1
        int offset = 1;
        int currentPos = myhash(x);
        int i = 1;
        //如果该位置里面有值,并且是这个hash没出现过的,那么就要继续寻找其他位置;如果出现了重复的,直接返回
        while (array[currentPos] != null && !array[currentPos].element.equals(x)) {
            currentPos += offset;
            offset = offset + 2 * (i + 1) - 1;
            i++;
            if (currentPos >= array.length)
                currentPos -= array.length;
        }
        return currentPos;
    }

    /*
    private int findPos(T x) //双散列: h2(x) = 7 – ( X % 7 ), f(i) = i * h2(x)
    {
        int offset = 1;
        int currentPos = myhash(x);
        int i = 1, h;
        h = 7 - currentPos % 7;
        while (array[currentPos] != null && !array[currentPos].element.equals(x)) {
            offset = i * h;
            currentPos += offset;
            i++;
            if (currentPos >= array.length)
                currentPos = currentPos - array.length;
        }
        return currentPos;
    }
    */

    private void rehash() {
        HashEntry<T>[] oldArray = array;
        allocateArray(nextPrime(2 * oldArray.length));
        currentSize = 0;
        //把原来的元素复制到新的数组中,注意是把集合中的元素复制进去
        for (int i = 0; i < oldArray.length; i++) {
            if (oldArray[i] != null && oldArray[i].isActive)
                insert(oldArray[i].element);
        }
    }

    private int myhash(T x)//哈希算法
    {
        int hashVal = x.hashCode();
        hashVal %= array.length;
        if(hashVal < 0)
            hashVal += array.length;
        return hashVal;
    }

    public static boolean isPrime(int n)
    {
        for(int i = 2; i < Math.sqrt(n); i++)
        {
            int k = n % i;
            if (k == 0)
                return false;
        }
        return true;
    }

    public static int nextPrime(int n)
    {
        while(!isPrime(n))
            n = n + 1;
        return n;
    }

    public static void main(String[] args) {
        ProbingHashTable<Integer> sc=new ProbingHashTable<Integer>();
        System.out.println(sc.contains(10));
        sc.insert(10);
        System.out.println(sc.contains(10));
        sc.remove(10);
        System.out.println(sc.contains(10));
    }
}
