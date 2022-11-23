import java.util.LinkedList;
import java.util.List;

public class SeparateChainingHashTable<T> {
    private static final int DEFAULT_TABLE_SIZE = 101;
    private List[] theLists;
    private int currentSize;

    public SeparateChainingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    public SeparateChainingHashTable(int size) {
        theLists = new LinkedList[nextPrime(size)];
        for (int i = 0; i < theLists.length; i++)
            theLists[i] = new LinkedList<>();
    }

    public void insert(T x) {
        List whichList = theLists[myhash(x)];
        if (!whichList.contains(x)) {
            whichList.add(x);
            if (++currentSize > theLists.length)
                rehash();
        }
    }

    public void remove(T x) {
        List<T> whichList = theLists[myhash(x)];
        if (whichList.contains(x)) {
            whichList.remove(x);
            currentSize--;
        }
    }

    public boolean contains(T x) {
        List whichList = theLists[myhash(x)];
        return whichList.contains(x);
    }

    public void makeEmpty() {
        for (int i = 0; i < theLists.length; i++)
            theLists[i].clear();
        currentSize = 0;
    }

    private int myhash(T x)//哈希算法
    {
        int hashVal = x.hashCode();
        hashVal %= theLists.length;
        if(hashVal < 0)
            hashVal += theLists.length;
        return hashVal;
    }

    private void rehash() {
        List<T>[] oldLists = theLists;
        // Create new double-sized, empty table
        theLists = new List[nextPrime(2 * theLists.length)];
        for (int j = 0; j < theLists.length; j++)
            theLists[j] = new LinkedList<>();
        // Copy table over
        currentSize = 0;
        for (int i = 0; i < oldLists.length; i++) {
            List<T> oldList = oldLists[i];
            for (int j = 0; j < oldList.size(); j++) {
                T item = oldList.get(j);
                insert(item);
            }
        }
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
        SeparateChainingHashTable<Integer> sc=new SeparateChainingHashTable<>();
        System.out.println(sc.contains(10));
        sc.insert(10);
        System.out.println(sc.contains(10));
        sc.remove(10);
        System.out.println(sc.contains(10));
    }
}
