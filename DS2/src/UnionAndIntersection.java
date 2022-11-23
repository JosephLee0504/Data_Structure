import java.util.ArrayList;
import java.util.Collections;

// 求两个线性表的交并差集
public class UnionAndIntersection {
    public static void ListIntersection(ArrayList<Integer> L1, ArrayList<Integer> L2) { // 交集
        int i = 0, j = 0;
        while (i < L1.size() && j < L2.size()) {
            if (L1.get(i).equals(L2.get(j))) {
                System.out.println(L1.get(i));
                i++;
                j++;
            }
            else if (L1.get(i) < L2.get(j))
                i++;
            else if (L1.get(i) > L2.get(j))
                j++;
        }
    }

    public static void ListUnion(ArrayList<Integer> L1, ArrayList<Integer> L2) { // 并集
        int i = 0, j = 0;
        while (i < L1.size() && j < L2.size()) {
            if (L1.get(i).equals(L2.get(j))) {
                System.out.println(L1.get(i));
                i++;
                j++;
            }
            else if (L1.get(i) < L2.get(j)) {
                System.out.println(L1.get(i));
                i++;
            }
            else if (L1.get(i) > L2.get(j)) {
                System.out.println(L2.get(j));
                j++;
            }
        }
        while (i != L1.size()) {
            System.out.println(L1.get(i));
            i++;
        }
        while (j != L2.size()) {
            System.out.println(L2.get(j));
            j++;
        }
    }

    public static void ListDifferentset(ArrayList<Integer> L1, ArrayList<Integer> L2) { // 差集
        int i = 0, j = 0;
        while (i < L1.size() && j < L2.size()) {
            if (L1.get(i).equals(L2.get(j))) {
                i++;
                j++;
            }
            if (L1.get(i) < L2.get(j)) {
                System.out.println(L1.get(i));
                i++;
            }
            if (L1.get(i) > L2.get(j)) {
                System.out.println(L2.get(j));
                j++;
            }
        }
        while (i != L1.size()) {
            System.out.println(L1.get(i));
            i++;
        }
        while (j != L2.size()) {
            System.out.println(L2.get(j));
            j++;
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> L1 = new ArrayList<>();
        L1.add(3);
        L1.add(5);
        L1.add(2);
        L1.add(7);
        L1.add(6);
        ArrayList<Integer> L2 = new ArrayList<>();
        L2.add(2);
        L2.add(3);
        L2.add(1);
        L2.add(4);

        // 保证两个线性表的元素从小到大排列
        Collections.sort(L1);
        Collections.sort(L2);

        System.out.println("交集:");
        ListIntersection(L1, L2);
        System.out.println("并集:");
        ListUnion(L1, L2);
        System.out.println("差集:");
        ListDifferentset(L1, L2);
    }
}
